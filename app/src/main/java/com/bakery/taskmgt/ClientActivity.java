package com.bakery.taskmgt;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.bakery.adapter.ClientAdapter;
import com.bakery.helper.RequestTask;
import com.bakery.helper.RequestTaskParam;
import com.bakery.helper.URLHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientActivity extends Activity implements
        RequestTask.OnRequestTaskCompletedListener,
        SwipeRefreshLayout.OnRefreshListener {

    private String _tag = this.getClass().getSimpleName();
    private ListView _listView;
    private EditText _editCName;
    private Button _btnSearch;
    private ClientAdapter _adapter;
    private SwipeRefreshLayout _clientSwipeLayout;

    private ArrayList<HashMap<String, Object>> _listData;
    private Integer _page = 1;
    private String _pageSize = "10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        this._listView = (ListView) this.findViewById(R.id.ListClient);
        this._editCName = (EditText) this.findViewById(R.id.SearchClientName);
        this._clientSwipeLayout = (SwipeRefreshLayout) this.findViewById(R.id.ClientSwipeRefreshLayout);
        this._btnSearch = (Button)this.findViewById(R.id.SearchBtnClient);

        this._clientSwipeLayout.setOnRefreshListener(this);//设置刷新监听器
        this._clientSwipeLayout.setColorSchemeResources(R.color.blue, R.color.white, R.color.blue, R.color.white);//

        this._listView.setOnScrollListener(onScrollListener);

        //初始化Adapter数据. 绑定Adapter和ListView
        this._listData = new ArrayList<HashMap<String, Object>>();
        this._adapter = new ClientAdapter(this, _listData, this.getFragmentManager());
        this._listView.setAdapter(_adapter);

        this.RequestData();

        this._btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

        this._listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                HashMap<String, Object> item = (HashMap<String, Object>)arg0.getItemAtPosition(pos);
                //数据是使用Intent返回
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("CID", item.get("id").toString());
                intent.putExtra("CName", item.get("name").toString());
                //设置返回数据
                ClientActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                ClientActivity.this.finish();
                return true;
            }
        });
    }

    public void RequestData() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("page", _page.toString());
        params.put("pageSize", _pageSize);
        if (!this._editCName.getText().toString().isEmpty()) {
            params.put("cname", this._editCName.getText().toString());
        }

        RequestTaskParam param = new RequestTaskParam();
        param.setUrl(URLHelper.ClientsUrl);
        param.setPostData(params);

        RequestTask task = new RequestTask();
        task.SetRequestTaskCompletedListener(this);
        task.execute(param);
    }

    @Override
    public void ResponseDataReady(String response) {
        if (response.isEmpty()) {
            Log.i(_tag, "No response message from server.");
        }
        Log.i(_tag, response);

        JSONArray array = null;

        try {
            array = new JSONArray(response);
            for (int i = 0; i < array.length(); i++) {
                HashMap<String, Object> tempHashMap = new HashMap<String, Object>();
                JSONObject object = (JSONObject) array.getJSONObject(i);
                tempHashMap.put("name", object.getString("name"));
                tempHashMap.put("contact", object.getString("contact"));
                tempHashMap.put("address", object.getString("addr"));
                tempHashMap.put("phone", object.getString("mobile"));
                tempHashMap.put("id", object.getString("id"));
                _listData.add(tempHashMap);
            }
            _adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(this._tag, e.getMessage());
        }
        finally {
            _clientSwipeLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        _listData.clear();
        _page = 1;
        RequestData();
    }

    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case SCROLL_STATE_IDLE:

                    if (_listView.getLastVisiblePosition() == _listData.size() - 1) {
                        _page++;
                        RequestData();
                    }
                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }

    };
}
