package com.pagatodo.yaganaste.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gorro on 28/07/16.
 */
public class JsonManager {

    public static JSONObject madeJson(JSONObject json) {
        JSONObject deepJson = new JSONObject();
        try {
            deepJson.put("request", json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return deepJson;
    }

    public static JSONObject madeJsonAdquirente(JSONObject json) {
        JSONObject deepJson = new JSONObject();
        try {
            deepJson.put("data", json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return deepJson;
    }

    public static JSONObject madeJsonArr(JSONArray jsonArray) {
        JSONObject deepJson = new JSONObject();
        try {
            deepJson.put("request", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return deepJson;
    }

    public static JSONObject madeJsonFromObject(Object oRequest) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String tmp = gson.toJson(oRequest);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = new JSONObject(tmp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }

}
