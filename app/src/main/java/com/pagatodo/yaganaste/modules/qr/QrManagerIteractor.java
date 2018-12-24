package com.pagatodo.yaganaste.modules.qr;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pagatodo.yaganaste.data.model.QRUser;
import com.pagatodo.yaganaste.modules.data.QrItems;
import com.pagatodo.yaganaste.modules.management.ApisFriggs;
import com.pagatodo.yaganaste.modules.management.apis.FriggsHeaders;
import com.pagatodo.yaganaste.modules.management.apis.FrigsMethod;
import com.pagatodo.yaganaste.modules.management.apis.ListenerFriggs;
import com.pagatodo.yaganaste.modules.management.response.QrsResponse;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.pagatodo.yaganaste.utils.Recursos.FRIGGS_GET_QR;

public class QrManagerIteractor implements QrManagerContracts.Iteractor, ListenerFriggs {

    private QrManagerContracts.Listener listener;
    private RequestQueue requestQueue;

    QrManagerIteractor(QrManagerContracts.Listener listener, Context context) {
        this.listener = listener;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    private ArrayList<QrItems> list;

    @Override
    public void getMyQrs() {
        ApisFriggs apisFriggs = new ApisFriggs(this);
        requestQueue.add(apisFriggs.sendRequest(FrigsMethod.GET,FRIGGS_GET_QR,
                FriggsHeaders.getHeadersBasic()));
        /*
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, URL_VERIFY_QR_USER, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        list = new ArrayList<>();
                        try {
                            JSONArray data = response.getJSONArray("data");

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject object = data.getJSONObject(i);
                                object.getString("name");
                                JSONObject qr = object.getJSONObject("qr");
                                JSONObject plateJson = qr.getJSONObject("Aux");

                                //list.add(new QrItems(new QRUser(object.getString("name"),,plateJson.getString("Pl")), 2));
                                list.add(new QrItems(new QRUser(object.getString("name"),plateJson.getString("Pl")),plateJson.toString(),2));
                            }
                            listener.onSuccessQRs(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onErrorQRs();
                    }
                }) {
            @Override

            public Map<String, String> getHeaders() {

                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                //headers.put("Authorization", "Yg-" + FirebaseInstanceId.getInstance().getToken());

                headers.put("Authorization", "Yg-" + App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION));


                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);*/
    }


    @Override
    public void onSuccess(JSONObject response) {

            Gson gson = new Gson();
            QrsResponse qrResponse = gson.fromJson(response.toString(),QrsResponse.class);
            Log.d("WSC",qrResponse.getData().size() + "");
            list = new ArrayList<>();
            //list.add(new QrItems(new QRUser()))
            //list.add(new QrItems(new QRUser(object.getString("name"),plateJson.getString("Pl")),plateJson.toString(),2));
    }

    @Override
    public void onError() {

    }
}
