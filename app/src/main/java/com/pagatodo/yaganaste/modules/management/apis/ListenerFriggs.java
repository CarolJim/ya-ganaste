package com.pagatodo.yaganaste.modules.management.apis;

import org.json.JSONObject;

import java.io.Serializable;

public interface ListenerFriggs {
    void onSuccess(JSONObject response);
    void onError();

}
