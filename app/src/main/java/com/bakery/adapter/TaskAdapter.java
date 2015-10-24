package com.bakery.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bakery.helper.Converter;
import com.bakery.model.TaskItem;
import com.bakery.taskmgt.AssignTaskFragment;
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
    private String _tag = "TaskAdapter";

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
            item.TxtTaskId =(TextView)convertView.findViewById(R.id.TxtTaskItemId);

            //设置初始值
            item.VisitTime = "";
            item.VisitResult = "";
            item.VisitUser = "";
            item.VisitRemark = "";
            convertView.setTag(item);
        }
        else
        {
            item = (TaskItem)convertView.getTag();
        }

        item.VisitTime = (String) _data.get(position).get("visitTime");
        item.VisitResult = (String) _data.get(position).get("visitResult");
        item.VisitUser = (String) _data.get(position).get("visitUser");
        item.VisitRemark =(String) _data.get(position).get("visitRemark");

        item.TxtRequestTime.setText( Converter.FormatStringDate((String) _data.get(position).get("requestTime")));
        item.TxtAssignTo.setText((String) _data.get(position).get("assignTo"));
        item.TxtClientAddress.setText((String) _data.get(position).get("clientAddress"));
        item.TxtClientContact.setText((String) _data.get(position).get("clientContact"));
        item.TxtContactPhone.setText((String) _data.get(position).get("contactPhone"));
        item.TxtClientName.setText((String) _data.get(position).get("clientName"));
        item.TxtContent.setText((String) _data.get(position).get("content"));
        item.TxtHaveTax.setText((String) _data.get(position).get("fax"));
        item.TxtFee.setText((String) _data.get(position).get("fee"));
        item.TxtStatus.setText((String) _data.get(position).get("status"));
        item.TxtTaskId.setText((String) _data.get(position).get("taskId"));
        item.BtnTaskVisit.setTag(position);
        item.BtnTaskAssign.setTag(position);
        item.BtnTaskVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(_tag, "Visit Clicked");
                Integer row = (Integer) v.getTag();
                HashMap<String, Object> item = (HashMap<String, Object>)getItem(row);


                View dialogLayout = _layoutInflater.inflate(R.layout.visit_dialog, null);

                TextView txtTime = (TextView) dialogLayout.findViewById(R.id.TxtVisitTime);
                TextView txtUser = (TextView) dialogLayout.findViewById(R.id.TxtVisitUser);
                TextView txtRemark = (TextView) dialogLayout.findViewById(R.id.TxtVisitRemark);
                TextView txtResult = (TextView) dialogLayout.findViewById(R.id.TxtVisitResult);

                txtTime.setText(Converter.FormatStringDate((String)item.get("visitTime")));
                txtUser.setText((String)item.get("visitUser"));
                txtRemark.setText((String)item.get("visitRemark"));
                txtResult.setText((String)item.get("visitResult"));

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setView(dialogLayout);
                builder.show();
            }
        });

        item.BtnTaskAssign.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.i(_tag, "TaskAssign Clicked");
                Integer row = (Integer) v.getTag();
                HashMap<String, Object> item = (HashMap<String, Object>)getItem(row);

                AssignTaskFragment fragment = new AssignTaskFragment();
                Bundle args = new Bundle();
                args.putString("taskId",item.get("taskId").toString());
                fragment.setArguments(args);
                _fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            }
        });
        return convertView;
    }
}
