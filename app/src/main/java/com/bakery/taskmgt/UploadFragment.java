package com.bakery.taskmgt;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bakery.helper.CusHttpRequest;
import com.bakery.helper.GlobalHelper;
import com.bakery.helper.PublicEnum;
import com.bakery.helper.RequestTask;
import com.bakery.helper.RequestTaskParam;
import com.bakery.helper.URLHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UploadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UploadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadFragment extends Fragment implements CusHttpRequest.OnRequestTaskCompletedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final String IMAGE_TYPE = "image/*";

    IFragementInteractionListener.OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadFragment newInstance(String param1, String param2) {
        UploadFragment fragment = new UploadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public UploadFragment() {
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
    private ArrayList<File> _files;
    private String _cameraImgPath;
    @Bind(R.id.BtnUploadCamera) Button BtnCamera;
    @Bind(R.id.BtnUploadAlbum) Button BtnAlbum;
    @Bind(R.id.BtnUpload) Button BtnUpload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_upload, container, false);
        ButterKnife.bind(this, rootView);
        this._files = new ArrayList<>();
        return rootView;
    }

    @OnClick(R.id.BtnUploadAlbum) public void SelectFromAlbum(){
        Intent getAlbum = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getAlbum.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(getAlbum, PublicEnum.RequestCode.Album);
    }

    @OnClick(R.id.BtnUploadCamera) public void SelectFromCamera(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        _cameraImgPath = Environment.getExternalStorageState() + "/"+GlobalHelper.getImageName(PublicEnum.ImageType.JPG);
        Log.d(_tag, _cameraImgPath);
        Uri imageUri = Uri.fromFile(new File(_cameraImgPath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        Log.i(_tag, imageUri.getPath());
        startActivityForResult(intent, PublicEnum.RequestCode.Camera);
    }

    @OnClick(R.id.BtnUpload) public void Upload()
    {
        Log.i(_tag, "Upload");

        File[] tempFile = new File[_files.size()];
        tempFile = _files.toArray(tempFile);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("user", "123");
        CusHttpRequest request = new CusHttpRequest(this.getContext(), this);
        request.PostString(URLHelper.UploadUrl, params, tempFile);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK) return;

         switch (requestCode)
        {
            case PublicEnum.RequestCode.Album:
                getPhotoFromAlbum(data);
                break;
            case PublicEnum.RequestCode.Camera:
                File file = new  File(this._cameraImgPath);
                this._files.add(file);
                Log.i(_tag, file.getAbsolutePath());
                break;
        }
    }
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = this.getContext().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void getPhotoFromAlbum(Intent data)
    {
        ClipData clipData = data.getClipData();
        if(clipData == null)
        {
            Uri selectedImage = data.getData(); //这是单选的情况下获取图片.
            File imageFile = new File(getRealPathFromURI(selectedImage));
        }
        else
        {
            for (int i=0;i<clipData.getItemCount();i++)
            {
                Uri selectedImage = clipData.getItemAt(i).getUri();
                File imageFile = new File(getRealPathFromURI(selectedImage));
                this._files.add(imageFile);
                Log.i(_tag, imageFile.getPath());
            }
            Log.i(_tag, String.valueOf(_files.size()));
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
        Log.i(_tag, response);
        Snackbar.make(this.getView(),response, Snackbar.LENGTH_LONG).show();
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
