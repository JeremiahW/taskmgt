package com.bakery.taskmgt;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bakery.helper.RequestTask;
import com.bakery.helper.RequestTaskParam;
import com.bakery.helper.TaskAdapter;
import com.bakery.helper.URLHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment  implements RequestTask.OnRequestTaskCompletedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TaskFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private TextView _txtView;
    private ListView _listView;
    private String _page = "1";
    private String _pageSize = "10";
    private String _cid = "-1";
    private String _uId="-1";
    private String _statusId ="-1";
    private String _tag = "TaskFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);

        this._listView = (ListView)rootView.findViewById(R.id.LstTask);
        if(this.getArguments() != null)
        {
            this._uId = getArguments().getString("empId", "-1");
        }

        //TODO 下拉刷新
        //TODO 点击加载更多的分页功能.
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("page", _page);
        params.put("pageSize", _pageSize);
        params.put("uid", _uId);
        params.put("statusid", _statusId);
        params.put("cid",_cid);

        RequestTaskParam param = new RequestTaskParam();
        param.setUrl(URLHelper.TasksUrl);
        param.setPostData(params);

        RequestTask task = new RequestTask();
        task.SetRequestTaskCompletedListener(this);
        task.execute(param);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void ResponseDataReady(String response) {
        Log.i(_tag, "Request Completed.");
        JSONArray array = null;
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        try {
            array = new JSONArray(response);
            for(int i=0;i<array.length();i++) {
                HashMap<String, Object> tempHashMap = new HashMap<String, Object>();
                JSONObject object = (JSONObject)array.getJSONObject(i);
                tempHashMap.put("requestTime",object.getString("RequestTime"));
                tempHashMap.put("assignTo",object.getString("UName"));
                tempHashMap.put("clientAddress", object.getString("ClientAddress"));
                tempHashMap.put("clientContact",object.getString("Contact"));
                tempHashMap.put("contactPhone",object.getString("ClientMobile"));
                tempHashMap.put("clientName", object.getString("ClientName"));
                tempHashMap.put("content", object.getString("Content"));
                tempHashMap.put("fax", object.getString("Tax"));
                tempHashMap.put("fee", object.getString("Fee"));
                tempHashMap.put("status", object.getString("StatusId"));
                tempHashMap.put("taskId", object.getString("TaskId"));
                arrayList.add(tempHashMap);
            }

            TaskAdapter adapter = new TaskAdapter(this.getActivity(), arrayList, getFragmentManager());
            this._listView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
