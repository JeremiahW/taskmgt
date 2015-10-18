package com.bakery.helper;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bakery.taskmgt.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wangj on 10/18/15.
 */
public class TaskAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, Object>> _data;
    private LayoutInflater _layoutInflater;
    private Context _context;
    private FragmentManager _fragmentManager;

    public TaskAdapter(Context context, ArrayList<HashMap<String, Object>> data,FragmentManager fragmentManager){
        this._data = data;
        this._context = context;
        this._fragmentManager = fragmentManager;
        this._layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int position) {
        return _data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskItem item = null;
        if(convertView == null)
        {
            convertView = this._layoutInflater.inflate(R.layout.task_item, null);
            item = new TaskItem();
            item.TxtRequestTime = (TextView)convertView.findViewById(R.id.TxtTaskItemRequestTime);
            item.TxtAssignTo = (TextView)convertView.findViewById(R.id.TxtTaskItemAssignTo);
            item.TxtClientAddress = (TextView)convertView.findViewById(R.id.TxtTaskItemAddress);
            item.TxtClientContact = (TextView)convertView.findViewById(R.id.TxtTaskItemContact);
            item.TxtContactPhone = (TextView)convertView.findViewById(R.id.TxtTaskItemClientPhone);
            item.TxtClientName = (TextView)convertView.findViewById(R.id.TxtTaskItemClientName);
            item.TxtContent = (TextView)convertView.findViewById(R.id.TxtTaskItemContent);
            item.BtnTaskVisit = (ImageButton)convertView.findViewById(R.id.BtnTaskItemVisit);
            item.BtnTaskAssign = (ImageButton)convertView.findViewById(R.id.BtnTaskItemAssignTo);
            item.TxtHaveTax =(TextView)convertView.findViewById(R.id.TxtTaskItemTax);
            item.TxtFee = (TextView)convertView.findViewById(R.id.TxtTaskItemFee);
            item.TxtStatus = (TextView)convertView.findViewById(R.id.TxtTaskItemTaskStatus);
            convertView.setTag(item);
        }
        else
        {
            item = (TaskItem)convertView.getTag();
        }
        item.TxtRequestTime.setText((String) _data.get(position).get("requestTime"));
        item.TxtAssignTo.setText((String) _data.get(position).get("assignTo"));
        item.TxtClientAddress.setText((String) _data.get(position).get("clientAddress"));
        item.TxtClientContact.setText((String) _data.get(position).get("clientContact"));
        item.TxtContactPhone.setText((String) _data.get(position).get("contactPhone"));
        item.TxtClientName.setText((String) _data.get(position).get("clientName"));
        item.TxtContent.setText((String) _data.get(position).get("content"));
        item.TxtHaveTax.setText((String) _data.get(position).get("fax"));
        item.TxtFee.setText((String) _data.get(position).get("fee"));
        item.TxtStatus.setText((String) _data.get(position).get("status"));
        item.BtnTaskVisit.setTag(position);
        item.BtnTaskAssign.setTag(position);
        item.BtnTaskVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object obj = v.getTag();
                //TODO 显示回访信息
            }
        });

        item.BtnTaskVisit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //TODO 添加新的Fragement, 增加将任务分配给员工的功能.
            }
        });


        return convertView;
    }
}
