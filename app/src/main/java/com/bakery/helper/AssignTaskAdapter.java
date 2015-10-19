package com.bakery.helper;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bakery.AssignTaskItem;
import com.bakery.taskmgt.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wangj on 10/19/15.
 */
public class AssignTaskAdapter extends BaseAdapter{
    private ArrayList<HashMap<String, Object>> _data;
    private LayoutInflater _layoutInflater;
    private Context _context;
    private FragmentManager _fragmentManager;
    private String _tag = "AssignTaskAdapter";

    private int _selectedIndex;
    public void set_selectedIndex(int _selectedIndex) {
        this._selectedIndex = _selectedIndex;
    }

    public AssignTaskAdapter(Context context, ArrayList<HashMap<String, Object>> data, FragmentManager fragmentManager)
    {
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
        AssignTaskItem item = null;
        if(convertView == null){
            item = new AssignTaskItem();
            convertView = this._layoutInflater.inflate(R.layout.employee_assign_item, null);
            item.TxtId = (TextView)convertView.findViewById(R.id.TxtEmpAssignItemId);
            item.TxtName = (TextView)convertView.findViewById(R.id.TxtEmpAssignItemName);
            item.RbIsSelected = (RadioButton)convertView.findViewById(R.id.RbEmpAssignItem);
            convertView.setTag(item);
        }
        else {
            item = (AssignTaskItem)convertView.getTag();
        }

        if(position == this._selectedIndex) {
            item.RbIsSelected.setChecked(true);
        }
        else {
            item.RbIsSelected.setChecked(false);
        }

        item.TxtId.setText((String) _data.get(position).get("empId"));
        item.TxtName.setText((String) _data.get(position).get("empName"));
        item.RbIsSelected.setTag(position);
        item.RbIsSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Integer row = (Integer) buttonView.getTag();
                    Log.i(_tag, row.toString());
                }
            }
        });

        return convertView;
    }
}
