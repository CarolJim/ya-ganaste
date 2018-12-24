package com.pagatodo.yaganaste.modules.management.apis;

import com.pagatodo.yaganaste.interfaces.enums.WebService;

import org.json.JSONException;
import org.json.JSONObject;


public interface ListenerFriggs {
    void onSuccess(WebService webService, JSONObject response)throws JSONException;
    void onError();

}
