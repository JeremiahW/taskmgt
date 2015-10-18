package com.bakery.helper;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bakery.taskmgt.R;
import com.bakery.taskmgt.TaskFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wangj on 10/18/15.
 */
public class EmployeeAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> _data;
    private LayoutInflater _layoutInflater;
    private Context _context;
    private FragmentManager _fragmentManager;
    public EmployeeAdapter(Context context, ArrayList<HashMap<String, Object>> data, FragmentManager fragmentManager){
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
        EmployeeItem item = null;
        if(convertView == null)
        {
            item = new EmployeeItem();
            convertView = this._layoutInflater.inflate(R.layout.employee_item, null);
            item.BtnDetails = (ImageButton)convertView.findViewById(R.id.ItemBtnDetail);
            item.TxtEmpName = (TextView)convertView.findViewById(R.id.ItemEmpName);
            item.TxtEmpPhone = (TextView)convertView.findViewById(R.id.ItemEmpPhone);
            item.TxtEmpId = (TextView)convertView.findViewById(R.id.ItemEmpId);
            convertView.setTag(item);
        }
        else
        {
            item = (EmployeeItem)convertView.getTag();
        }

        item.TxtEmpName.setText((String) _data.get(position).get("empName"));
        item.TxtEmpPhone.setText((String) _data.get(position).get("empPhone"));
        item.TxtEmpId.setText((String) _data.get(position).get("empId"));
        item.BtnDetails.setTag(position); //set current position, the will be handled when button this clicked
        item.BtnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_fragmentManager != null) {
                   Integer row = (Integer)v.getTag();
                    HashMap<String, Object> item = (HashMap<String, Object>)getItem(row);

                    TaskFragment fragment = new TaskFragment();
                    Bundle args = new Bundle();
                    args.putString("empId",item.get("empId").toString());
                    fragment.setArguments(args);
                    _fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                }
            }
        });
        return convertView;
    }
}
