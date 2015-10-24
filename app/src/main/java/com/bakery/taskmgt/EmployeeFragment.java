package com.bakery.taskmgt;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bakery.adapter.EmployeeAdapter;
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
 * {@link EmployeeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeFragment extends Fragment implements RequestTask.OnRequestTaskCompletedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    IFragementInteractionListener.OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployeeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeeFragment newInstance(String param1, String param2) {
        EmployeeFragment fragment = new EmployeeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private ListView _listView;
    private TextView _txtView;
    public EmployeeFragment() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_employee2, container, false);

        this._txtView = (TextView)rootView.findViewById(R.id.TxtViewEmp);
        this._listView = (ListView)rootView.findViewById(R.id.LstEmployee);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("page","1");
        params.put("pageSize", "10");

        RequestTaskParam param = new RequestTaskParam();
        param.setUrl(URLHelper.UsersUrl);
        param.setPostData(params)
        ;
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
        JSONArray array = null;
        try {
            ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
            array = new JSONArray(response);
            for(int i=0;i<array.length();i++) {
                HashMap<String, Object> tempHashMap = new HashMap<String, Object>();
                JSONObject object = (JSONObject)array.getJSONObject(i);
                tempHashMap.put("empName",object.getString("name"));
                tempHashMap.put("empPhone",object.getString("mobile"));
                tempHashMap.put("empId", object.getString("id"));
                arrayList.add(tempHashMap);
            }

            EmployeeAdapter adapter = new EmployeeAdapter(this.getActivity(), arrayList, getFragmentManager());
            this._listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
      //  this._txtView.setText(array.length());
    }


}
