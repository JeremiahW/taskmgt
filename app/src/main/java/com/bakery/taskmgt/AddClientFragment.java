package com.bakery.taskmgt;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import com.bakery.helper.GlobalHelper;
import com.bakery.helper.RequestTask;
import com.bakery.helper.RequestTaskParam;
import com.bakery.helper.URLHelper;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddClientFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddClientFragment extends Fragment implements RequestTask.OnRequestTaskCompletedListener {
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
     * @return A new instance of fragment AddClientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddClientFragment newInstance(String param1, String param2) {
        AddClientFragment fragment = new AddClientFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AddClientFragment() {
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

    private EditText _editCName;
    private EditText _editCAddress;
    private EditText _editCContact;
    private EditText _editCLandphone;
    private EditText _editCMobile;
    private EditText _editCFax;
    private Button _btnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_client, container, false);
        this._editCName = (EditText)rootView.findViewById(R.id.TxtAddClientName);
        this._editCAddress = (EditText)rootView.findViewById(R.id.TxtAddClientAddress);
        this._editCContact = (EditText)rootView.findViewById(R.id.TxtAddClientContact);
        this._editCLandphone = (EditText)rootView.findViewById(R.id.TxtAddClientLandPhone);
        this._editCMobile = (EditText)rootView.findViewById(R.id.TxtAddClientMobile);
        this._editCFax = (EditText)rootView.findViewById(R.id.TxtAddClientFax);
        this._btnSubmit = (Button)rootView.findViewById(R.id.BtnAddClient);
        this._btnSubmit.setOnClickListener(BtnClickListener);
        return rootView;
    }

    View.OnClickListener BtnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.BtnAddClient:
                    Add();
                    break;
            }
        }
    };

    public void Add()
    {
        if(!_editCName.getText().toString().isEmpty())
        {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("cname",_editCName.getText().toString());
            params.put("pinyin", "");
            params.put("address", _editCAddress.getText().toString());
            params.put("contact", _editCContact.getText().toString());
            params.put("mobile", _editCMobile.getText().toString());
            params.put("tel", _editCLandphone.getText().toString());
            params.put("fax",_editCFax.getText().toString());
            // params.put("uid", GlobalHelper.LoginUserId);

            RequestTaskParam param = new RequestTaskParam();
            param.setUrl(URLHelper.AddClientUrl);
            param.setPostData(params);
            RequestTask task = new RequestTask();
            task.SetRequestTaskCompletedListener(this);
            task.execute(param);
        }
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
        if(!response.isEmpty())
        {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("添加成功")
                    .setMessage("添加成功")
                    .show();
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

}
