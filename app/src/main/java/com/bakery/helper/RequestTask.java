package com.bakery.helper;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.TextView;

import com.bakery.taskmgt.EmployeeFragment;
import com.bakery.taskmgt.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by wangj on 10/18/15.
 */
public class RequestTask extends AsyncTask<RequestTaskParam, String, String> {

    private OnRequestTaskCompletedListener _listener;
    public void SetRequestTaskCompletedListener(OnRequestTaskCompletedListener listener)
    {
        this._listener = listener;
    }

    @Override
    protected String doInBackground(RequestTaskParam... params) {
        URL url;
        String response = "";
        try {
            url = new URL(params[0].getUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder();
            for (String key : params[0].getPostData().keySet())
            {
                String value = params[0].getPostData().get(key);
                builder.appendQueryParameter(key, value);
            }
            String query = builder.build().getEncodedQuery();
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }
            conn.connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
    @Override
    protected void onPostExecute(String response){
        super.onPostExecute(response);
        _listener.ResponseDataReady(response);
    }

    public interface OnRequestTaskCompletedListener{
        public void ResponseDataReady(String response);
    }
}

