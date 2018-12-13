package com.pagatodo.yaganaste.modules.register;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class RegInteractor implements RegContracts.Iteractor{

    private static final String URL_VALIDATE_PLATE = "https://us-central1-frigg-1762c.cloudfunctions.net/vldtLnkPltYG";
    private RegContracts.Listener listener;
    private RequestQueue requestQueue;

    RegInteractor(RegContracts.Listener listener, Context context) {
        this.listener = listener;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void onValidateQr(String plate) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("plate", plate);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = jsonBody.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, URL_VALIDATE_PLATE, null, response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (success){
                            listener.onSuccessValidatePlate(plate);
                        } else {
                            listener.onErrorValidatePlate(response.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
                    // TODO: Handle error
                    Log.e("VOLLEY", error.toString());
                    listener.onErrorValidatePlate(App.getInstance().getString(R.string.no_internet_access));
                }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody(){
                try {
                    return requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}