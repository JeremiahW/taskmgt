package com.bakery.taskmgt;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
public class TaskFragment extends Fragment  implements RequestTask.OnRequestTaskCompletedListener,  SwipeRefreshLayout.OnRefreshListener {
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
    private SwipeRefreshLayout _taskSwipeLayout;
    private Integer _page = 1;
    private String _pageSize = "10";
    private String _cid = "-1";
    private String _uId="-1";
    private String _statusId ="-1";
    private String _tag = "TaskFragment";
    private ArrayList<HashMap<String, Object>> _listData;
    private TaskAdapter _adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);

        this._listView = (ListView)rootView.findViewById(R.id.LstTask);
        this._taskSwipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.TaskSwipLayout);
        this._taskSwipeLayout.setOnRefreshListener(this);//设置刷新监听器
        this._taskSwipeLayout.setColorSchemeResources(R.color.blue, R.color.white, R.color.blue, R.color.white);//

        this._listView.setOnScrollListener(onScrollListener);
        if(this.getArguments() != null)
        {
            this._uId = getArguments().getString("empId", "-1");
        }

        //初始化Adapter数据. 绑定Adapter和ListView
        _listData = new ArrayList<HashMap<String, Object>>();
        _adapter = new TaskAdapter(this.getActivity(), _listData, getFragmentManager());

        LoadData();
        //TODO 点击加载更多的分页功能.

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
        if(response.isEmpty())
        {
           Log.i(_tag, "No response message from server.");
        }
        Log.i(_tag, response);

        JSONArray array = null;

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
                _listData.add(tempHashMap);
            }

            this._listView.setAdapter(_adapter);
            _taskSwipeLayout.setRefreshing(false);
            _adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case SCROLL_STATE_IDLE:

                    if (_listView.getLastVisiblePosition() ==  _listData.size() -1) {
                        _page ++;
                        LoadData();
                    }
                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };
    @Override
    public void onRefresh() {
        this._page = 1;
        //清空列表数据. 重新调用LoadData进行获取
        _listData.clear();
        LoadData();
    }

    public void LoadData()
    {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("page", _page.toString());
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
    }
}
