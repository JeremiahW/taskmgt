package com.bakery.helper;

import java.util.HashMap;

/**
 * Created by wangj on 10/18/15.
 */
public class RequestTaskParam {
    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    private String Url;

    public HashMap<String, String> getPostData() {
        return PostData;
    }

    public void setPostData(HashMap<String, String> postData) {
        PostData = postData;
    }

    private HashMap<String, String> PostData;

}
