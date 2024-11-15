package com.example.ungdungnhathuoc.API;

import okhttp3.Request;

public interface ApiInterface {
    public default Request getRequest(String url){
        return new Request.Builder()
                .url(url)
                .build();

    }

    public default Request postRequest(String url, String body){
        return new Request.Builder()
                .url(url)

                .build();

    }


}
