package com.pagatodo.yaganaste.modules.management;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.pagatodo.yaganaste.modules.management.apis.FrigsMethod;
import com.pagatodo.yaganaste.modules.management.apis.ListenerFriggs;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class ApisFriggs {

    private ListenerFriggs listener;

    public ApisFriggs(ListenerFriggs listner) {
        this.listener = listner;
    }

    public JsonObjectRequest sendRequest(FrigsMethod method, String urlRequest,
                                         HashMap<String, String> headers, Serializable bodyRequest, Class<?> resClass){

        Gson gson = new Gson();
        int metthodRequest = Request.Method.GET;
        if (method == FrigsMethod.POST) {
            metthodRequest = Request.Method.POST;
        }

        return new JsonObjectRequest
                (metthodRequest, urlRequest, null, response -> {

                    this.listener.onSuccess(response);
                }, error ->
                        this.listener.onError()) {
            @Override
            public byte[] getBody() {
                try {
                    return gson.toJson(bodyRequest).getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };
    }
    public JsonObjectRequest sendRequest(FrigsMethod method, String urlRequest,
                                         HashMap<String, String> headers){
        //WsCaller
        Log.d("WSC", urlRequest);
        Log.d("WSC",headers.get("Authorization"));
        int metthodRequest = Request.Method.GET;
        if (method == FrigsMethod.POST) {
            metthodRequest = Request.Method.POST;
        }

        return new JsonObjectRequest
                (metthodRequest, urlRequest, null, response -> {
                    this.listener.onSuccess(response);
                    Log.d("WSC Request",response.toString());
                }, error -> this.listener.onError())
        {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };
    }

}
