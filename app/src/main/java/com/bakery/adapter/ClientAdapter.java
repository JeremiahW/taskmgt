package com.bakery.adapter;

import android.content.Context;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bakery.model.ClientItem;
import com.bakery.model.EmployeeItem;
import com.bakery.taskmgt.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wangj on 10/23/15.
 */
public class ClientAdapter extends BaseAdapter{
    private ArrayList<HashMap<String, Object>> _data;
    private LayoutInflater _layoutInflater;
    private Context _context;
    private FragmentManager _fragmentManager;
    public ClientAdapter(Context context, ArrayList<HashMap<String, Object>> data, FragmentManager fragmentManager){
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
        ClientItem item = null;
        if(convertView == null)
        {
            item = new ClientItem();
            convertView = this._layoutInflater.inflate(R.layout.client_item, null);
            item.TxtCName =(TextView)convertView.findViewById(R.id.ClientItemCName);
            item.TxtCContact =(TextView)convertView.findViewById(R.id.ClientItemContact);
            item.TxtCAddress =(TextView)convertView.findViewById(R.id.ClientItemAddress);
            item.TxtCPhone =(TextView)convertView.findViewById(R.id.ClientItemPhone);
            item.CID = "";
            convertView.setTag(item);
        }
        else
        {
            item = (ClientItem)convertView.getTag();
        }

        item.TxtCName.setText((String) _data.get(position).get("name"));
        item.TxtCContact.setText((String) _data.get(position).get("contact"));
        item.TxtCAddress.setText((String) _data.get(position).get("address"));
        item.TxtCPhone.setText((String) _data.get(position).get("phone"));
        item.CID = (String)_data.get(position).get("id");
        return convertView;
    }
}
