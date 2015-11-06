package com.bakery.helper;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangj on 11/06/15.
 */
public class MultipartRequest extends Request<String> {

    private HttpEntity mEntity;

    private final Response.Listener<String> mListener;
    private List<File> mFileParts;
    private String mFilePartName;
    private Map<String, String> mParams;
    private String _tag = this.getClass().getSimpleName();

    public MultipartRequest(int method, String url,
                            Response.Listener<String> listener,
                            Response.ErrorListener errorListener,
                            String filePartName,
                            List<File> files,
                            Map<String, String> params) {
        super(method, url, errorListener);

        mFilePartName = filePartName;
        mFileParts = files;
        mListener = listener;
        mParams = params;
        buildMultipartEntity();

    }

    private void buildMultipartEntity(){
        MultipartEntityBuilder entity = MultipartEntityBuilder.create();
        if(mFileParts != null && mFileParts.size() >0){
            for(File file : mFileParts){
                entity.addPart(mFilePartName, new FileBody(file));
            }

            Log.i(_tag, String.valueOf(mFileParts.size()));
        }

        if(mParams != null && mParams.size()>0){
            for (Map.Entry<String, String> entry : mParams.entrySet()) {
                entity.addPart(
                        entry.getKey(),
                         new StringBody(entry.getValue(), ContentType.TEXT_PLAIN));
            }
        }
        mEntity = entity.build();

    }
    @Override
    public String getBodyContentType() {
        return mEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mEntity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        if (VolleyLog.DEBUG) {
            if (response.headers != null) {
                for (Map.Entry<String, String> entry : response.headers
                        .entrySet()) {
                    VolleyLog.d(entry.getKey() + "=" + entry.getValue());
                }
            }
        }

        String parsed;
        try {
            parsed = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed,
                HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        VolleyLog.d("getHeaders");
        Map<String, String> headers = super.getHeaders();

        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }

        return headers;
    }
    @Override
    protected void deliverResponse(String s) {
        mListener.onResponse(s);
    }
}
