package com.bakery.taskmgt;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bakery.adapter.AssignTaskAdapter;
import com.bakery.helper.RequestTask;
import com.bakery.helper.RequestTaskParam;
import com.bakery.helper.URLHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AssignTaskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AssignTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssignTaskFragment extends Fragment implements RequestTask.OnRequestTaskCompletedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private IFragementInteractionListener.OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AssignTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AssignTaskFragment newInstance(String param1, String param2) {
        AssignTaskFragment fragment = new AssignTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AssignTaskFragment() {
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

    private ListView _list;
    private String _taskId;
    private AssignTaskAdapter _adapter;
    private FragmentManager _fragementManger;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_assign_task, container, false);
        this._list = (ListView)rootView.findViewById(R.id.LstEmpAssign);

        if(this.getArguments() != null)
        {
            this._taskId = getArguments().getString("taskId", "-1");
        }

        this._fragementManger = getFragmentManager();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("page","1");
        params.put("pageSize", "10");

        RequestTaskParam param = new RequestTaskParam();
        param.setUrl(URLHelper.UsersUrl);
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
            mListener = (IFragementInteractionListener.OnFragmentInteractionListener) activity;
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
        if(!response.isEmpty() && response.toString().equals("AssignSuccessful"))
        {
            _fragementManger.popBackStack();
        }

        JSONArray array = null;
        try {
            ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
            array = new JSONArray(response);
            for(int i=0;i<array.length();i++) {
                HashMap<String, Object> tempHashMap = new HashMap<String, Object>();
                JSONObject object = (JSONObject)array.getJSONObject(i);
                tempHashMap.put("empName",object.getString("name"));
                tempHashMap.put("empId", object.getString("id"));
                arrayList.add(tempHashMap);
            }

            _adapter = new AssignTaskAdapter(this.getActivity(), arrayList, getFragmentManager());
            this._list.setAdapter(_adapter);
            this._list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    _adapter.set_selectedIndex(position);
                    _adapter.notifyDataSetChanged();

                    HashMap<String, Object> item = (HashMap<String, Object>)  _list.getItemAtPosition(position);
                    String empId = item.get("empId").toString();
                    //TODO Call Api to Assign Task and then popBackStack();

                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("uid", empId);
                    params.put("taskid", _taskId);

                    RequestTaskParam param = new RequestTaskParam();
                    param.setUrl(URLHelper.AssignTaskUrl);
                    param.setPostData(params)
                    ;
                    RequestTask task = new RequestTask();
                    task.SetRequestTaskCompletedListener(AssignTaskFragment.this);
                    task.execute(param);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
