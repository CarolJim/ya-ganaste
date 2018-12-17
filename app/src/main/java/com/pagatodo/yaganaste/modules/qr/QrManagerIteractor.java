package com.pagatodo.yaganaste.modules.qr;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.QRUser;
import com.pagatodo.yaganaste.modules.data.QrItems;
import com.pagatodo.yaganaste.utils.qrcode.QrcodeGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_FIREBASE_SESSION;

public class QrManagerIteractor implements QrManagerContracts.Iteractor {

    private static final String URL_VERIFY_QR_USER = "https://us-central1-frigg-1762c.cloudfunctions.net/gtQRSYG";
    private QrManagerContracts.Listener listener;
    private RequestQueue requestQueue;


    public QrManagerIteractor(QrManagerContracts.Listener listener, Context context) {
        this.listener = listener;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    private ArrayList<QrItems> list;

    @Override
    public void getMyQrs() throws JSONException {

        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("name","213213");


        } catch (JSONException e) {
            e.printStackTrace();
        }

            /*jsonBody.put("name", qrs.alias);
            jsonBody.put("bank", "148");
            jsonBody.put("account", SingletonUser.getInstance().dataUser.emisor.cuentas[0].clabe)*/

        String requestBody = jsonBody.toString();
        Log.d("TOKEN_SESION", App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, URL_VERIFY_QR_USER, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VOLLEY_OK", response.toString());
                        Log.d("TOKEN_SESION", App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION));
                        list = new ArrayList<>();
                        try {
                            JSONArray data = response.getJSONArray("data");

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject object = data.getJSONObject(i);
                                object.getString("name");
                                JSONObject qr = object.getJSONObject("qr");
                                JSONObject plateJson = qr.getJSONObject("Aux");

                                list.add(new QrItems(new QRUser(object.getString("name"),plateJson.getString("Pl")),2));
                            }
                            listener.onSuccessQRs(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY OK", error.toString());
                    }
                }) {
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
