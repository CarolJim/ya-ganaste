package com.pagatodo.yaganaste.modules.emisor.PaymentToQR.operations.AgregarQRVirtual;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pagatodo.yaganaste.App;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_FIREBASE_SESSION;

public class AgregarVirtQRIteractor implements  AgregarVirtContracts.Iteractor{


    private static final String URL_NEW_QR = "https://us-central1-frigg-1762c.cloudfunctions.net/nwQRYG";
    private static final String URL_LINK_QR = "https://us-central1-frigg-1762c.cloudfunctions.net/lnkQRYG";

    AgregarVirtContracts.Listener listener;

    Context context ;

    public AgregarVirtQRIteractor(AgregarVirtContracts.Listener iteractor, Context context) {
        this.listener = iteractor;
        this.context = context;
    }

    @Override
    public void asociaQRValido(String plate ,String alias) {
        RequestQueue requestQueue = Volley.newRequestQueue(App.getContext());

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("plate",plate);
            jsonBody.put("name",alias);
            jsonBody.put("bank", "148");
            jsonBody.put("account", App.getInstance().getPrefs().loadData(CLABE_NUMBER).replace(" ",""));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String requestBody = jsonBody.toString();
        Log.d("TOKEN_SESION", App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, URL_LINK_QR, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccessQRs();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY OK", error.toString());
                        listener.onErrorQRs();
                    }
                }) {

            @Override
            public byte[] getBody() {
                try {
                    return requestBody.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Yg-" + App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION));
                return headers;
            }

        };
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void validarQRValido(String alias) {
        RequestQueue requestQueue = Volley.newRequestQueue(App.getContext());

        JSONObject jsonBody = new JSONObject();
        try {
//            jsonBody.put("plate",plate);
            jsonBody.put("name",alias);
            jsonBody.put("bank", "148");
            jsonBody.put("account", App.getInstance().getPrefs().loadData(CLABE_NUMBER).replace(" ",""));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String requestBody = jsonBody.toString();
        Log.d("TOKEN_SESION", App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, URL_NEW_QR, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccessQRs();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY OK", error.toString());
                        listener.onErrorQRs();
                    }
                }) {

            @Override
            public byte[] getBody() {
                try {
                    return requestBody.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Yg-" + App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION));
                return headers;
            }

        };
        requestQueue.add(jsonObjectRequest);
    }
}
