package com.pagatodo.yaganaste.modules.wallet_emisor.PaymentToQR.operations.AgregarQRVirtual;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_FIREBASE_SESSION;
import static com.pagatodo.yaganaste.utils.Recursos.URL_FRIGGS;

public class AgregarVirtQRIteractor implements  AgregarVirtContracts.Iteractor{

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
                (Request.Method.POST, URL_FRIGGS + App.getContext().getResources().getString(R.string.linkedQr), null, (Response.Listener<JSONObject>)
                        response -> listener.onSuccessQRs(), (Response.ErrorListener) error -> {
                    Log.d("VOLLEY OK", error.toString());
                    listener.onErrorQRs();
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
            public Map<String, String> getHeaders() {
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, URL_FRIGGS + App.getContext().getResources().getString(R.string.newQr),
                        null, response -> listener.onSuccessQRs(), error -> {
                            Log.d("VOLLEY OK", error.toString());
                            listener.onErrorQRs();
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
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Yg-" + App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION));
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
