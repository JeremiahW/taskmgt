package com.bakery.taskmgt;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.bakery.helper.ClientAdapter;
import com.bakery.helper.RequestTask;
import com.bakery.helper.RequestTaskParam;
import com.bakery.helper.URLHelper;
import com.bakery.model.EmployeeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddTaskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskFragment extends Fragment implements RequestTask.OnRequestTaskCompletedListener {
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
     * @return A new instance of fragment AddTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTaskFragment newInstance(String param1, String param2) {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AddTaskFragment() {
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

    private String _tag = this.getClass().getSimpleName();
    private Button _btnPopDate;
    private Button _btnPopTime;
    private Button _btnPopClient;
    private EditText _txtRequestDate;
    private EditText _txtReuqestTime;
    private EditText _txtClient;
    private Spinner _spinnerUsers;
    private int _year;
    private int _month;
    private int _day;
    private int _hour;
    private int _minute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_task, container, false);
        this._btnPopDate = (Button)rootView.findViewById(R.id.BtnAddRequestDate);
        this._txtRequestDate = (EditText)rootView.findViewById(R.id.TxtAddTaskRequestDate);
        this._btnPopTime = (Button)rootView.findViewById(R.id.BtnAddRequestTime);
        this._txtReuqestTime = (EditText)rootView.findViewById(R.id.TxtAddTaskRequestTime);
        this._txtClient = (EditText)rootView.findViewById(R.id.TxtAddTaskClient);
        this._spinnerUsers = (Spinner)rootView.findViewById(R.id.SpinnerTaskAddUser);
        this._btnPopClient = (Button)rootView.findViewById(R.id.BtnAddTaskClient);
        this._btnPopDate.setOnClickListener(btnClickListener);
        this._btnPopTime.setOnClickListener(btnClickListener);
        this._btnPopClient.setOnClickListener(btnClickListener);

        InitDateTimeParam();
        InitUserSelection();
        return rootView;
    }

    private void InitUserSelection()
    {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("page","1");
        params.put("pageSize", "10");

        RequestTaskParam param = new RequestTaskParam();
        param.setUrl(URLHelper.UsersUrl);
        param.setPostData(params);

        RequestTask task = new RequestTask();
        task.SetRequestTaskCompletedListener(this);
        task.execute(param);
    }

    private void InitDateTimeParam()
    {
        final Calendar c = Calendar.getInstance();
        _year = c.get(Calendar.YEAR);
        _month = c.get(Calendar.MONTH);
        _day = c.get(Calendar.DAY_OF_MONTH);
        _hour = c.get(Calendar.HOUR_OF_DAY);
        _minute = c.get(Calendar.MINUTE);
    }
    public void DateSelection()
    {
        DatePickerDialog dpd = new DatePickerDialog(this.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        _txtRequestDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, _year, _month, _day);
        dpd.show();
    }

    public void TimeSelection()
    {
        TimePickerDialog tpd = new TimePickerDialog(this.getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        _txtReuqestTime.setText(hourOfDay + ":" + minute);
                    }
                }, _hour, _minute, false);
        tpd.show();
    }

    public void ClientSelection()
    {
        Intent intent = new Intent();
        intent.setClass(this.getContext(), ClientActivity.class);
        startActivityForResult(intent, 0);
     }

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.BtnAddRequestDate:
                    DateSelection();
                    break;
                case R.id.BtnAddRequestTime:
                    TimeSelection();
                    break;
                case R.id.BtnAddTaskClient:
                    ClientSelection();
                    break;
            }
        }
    };

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
           List<EmployeeModel> models = new ArrayList<EmployeeModel>();
            array = new JSONArray(response);
            for(int i=0;i<array.length();i++) {
                EmployeeModel model = new EmployeeModel();
                JSONObject object = (JSONObject)array.getJSONObject(i);
                model.set_phone(object.getString("mobile"));
                model.set_chinese(object.getString("name"));
                model.set_id(object.getString("id"));
                models.add(model);
            }

            ArrayAdapter<EmployeeModel> dataAdapter = new ArrayAdapter<EmployeeModel>(this.getContext(),
                    android.R.layout.simple_spinner_item, models);
            _spinnerUsers.setAdapter(dataAdapter);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case 0:
                Log.i(_tag,data.getExtras().getString("CID"));
                Log.i(_tag,data.getExtras().getString("CName"));
                _txtClient.setText(data.getExtras().getString("CName"));
                break;
        }
    }
}
