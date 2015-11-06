package com.bakery.helper;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by wangj on 11/06/15.
 */
public class CusHttpRequest {
    private Context _context;
    private RequestQueue _requestQueue;
    private OnRequestTaskCompletedListener _listener;
    private String _tag = this.getClass().getSimpleName();
    Response.Listener resListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            _listener.ResponseDataReady(s);
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    };

    public CusHttpRequest(Context context, OnRequestTaskCompletedListener listener){
        this._context = context;
        this._requestQueue = Volley.newRequestQueue(this._context);
        this._listener = listener;
    }

    public void PostString(String url, final Map<String, String> params, File...files) {
        // StringRequest stringRequest = new StringRequest(Request.Method.POST, url, resListener, errorListener) ;
        List<File> mFiles = files == null ? null : Arrays.asList(files);
        MultipartRequest multipartRequest = new MultipartRequest(Request.Method.POST, url, resListener, errorListener,"file", mFiles, params);
        this._requestQueue.add(multipartRequest);
    }
    //定义一个Interface, 用于主界面回调数据.
    public interface OnRequestTaskCompletedListener{
        public void ResponseDataReady(String response);
    }
}
