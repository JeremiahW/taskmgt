package com.bakery.taskmgt;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.bakery.helper.GlobalHelper;
import com.bakery.helper.RequestTask;
import com.bakery.helper.RequestTaskParam;
import com.bakery.helper.URLHelper;
import com.bakery.model.EmployeeModel;
import com.bakery.model.ServiceTypeModel;

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
    private EditText _txtFee;
    private EditText _txtContent;
    private RadioButton _rbInTax;
    private RadioButton _rbNoTax;
    private Spinner _spinnerUsers;
    private Spinner _spinnerServiceType;
    private Button _btnSubmit;
    private String _cid;
    private String _uid;
    private String _typeId;
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
        this._txtFee = (EditText)rootView.findViewById(R.id.TxtAddTaskFee);
        this._txtContent = (EditText) rootView.findViewById(R.id.TxtAddTaskContent);
        this._rbInTax = (RadioButton)rootView.findViewById(R.id.RbAddTaskIncludeTax);
        this._spinnerUsers = (Spinner)rootView.findViewById(R.id.SpinnerTaskAddUser);
        this._spinnerServiceType = (Spinner)rootView.findViewById(R.id.SpinnerServiceType);
        this._btnPopClient = (Button)rootView.findViewById(R.id.BtnAddTaskClient);
        this._btnSubmit = (Button)rootView.findViewById(R.id.BtnAddTask);
        this._btnPopDate.setOnClickListener(btnClickListener);
        this._btnPopTime.setOnClickListener(btnClickListener);
        this._btnPopClient.setOnClickListener(btnClickListener);
        this._btnSubmit.setOnClickListener(btnClickListener);


        InitDateTimeParam();
        InitUserSelection();
        InitServiceTypeSelection();
        return rootView;
    }

    private void InitUserSelection()
    {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("page", "1");
        params.put("pageSize", "10");

        RequestTaskParam param = new RequestTaskParam();
        param.setUrl(URLHelper.UsersUrl);
        param.setPostData(params);

        RequestTask task = new RequestTask();
        task.SetRequestTaskCompletedListener(this);
        task.execute(param);
    }


    private void InitServiceTypeSelection()
    {
        ServiceTypeModel model = new ServiceTypeModel();
        ArrayList<ServiceTypeModel> models = model.get_serviceTypes();
        ArrayAdapter<ServiceTypeModel> adapter = new ArrayAdapter<ServiceTypeModel>(this.getContext(),
                android.R.layout.simple_spinner_item, models);
        _spinnerServiceType.setAdapter(adapter);
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

    public void SubmitTask()
    {
        String date = _txtRequestDate.getText().toString();
        String time = _txtReuqestTime.getText().toString();
        String fee = _txtFee.getText().toString();
        String content = _txtContent.getText().toString();
        String intax = _rbInTax.isChecked() ? "1" : "0";
        EmployeeModel user = (EmployeeModel)_spinnerUsers.getSelectedItem();
        ServiceTypeModel service = (ServiceTypeModel)_spinnerServiceType.getSelectedItem();
        if(!this._cid.isEmpty() && user != null
                && !date.isEmpty() && !time.isEmpty()
                && !fee.isEmpty() )
        {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("requestdate",date+" "+time);
            params.put("fee", fee);
            params.put("content", content);
            params.put("intax", intax);
            params.put("clientid", _cid);
            params.put("assignuserid", user.get_id());
            params.put("servicetypeid", service.get_id());
            params.put("uid", GlobalHelper.LoginUserId);

            RequestTaskParam param = new RequestTaskParam();
            param.setUrl(URLHelper.AddTaskUrl);
            param.setPostData(params);
            RequestTask task = new RequestTask();
            task.SetRequestTaskCompletedListener(this);
            task.execute(param);
        }


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
                case R.id.BtnAddTask:
                    SubmitTask();
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

        if(!response.isEmpty() && response.equals("AddSuccessful"))
        {
            new AlertDialog.Builder(getContext())
                    .setTitle("添加任务成功")
                    .setMessage("任务添加成功")
                    .show();
            return;
        }

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
            if(data == null) return;
            switch (requestCode)
            {
                case 0:
                    this._txtClient.setText(data.getExtras().getString("CName"));
                   this._cid = data.getExtras().getString("CID");
                    break;
            }
    }
}
