package com.pagatodo.yaganaste.modules.qr;

import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.QRUser;
import com.pagatodo.yaganaste.modules.data.QrItems;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_FIREBASE_SESSION;

public class QrManagerIteractor implements QrManagerContracts.Iteractor {

    private static final String URL_VERIFY_QR_USER = "https://us-central1-frigg-1762c.cloudfunctions.net/gtQRSYG";
    private QrManagerContracts.Listener listener;
    private RequestQueue requestQueue;
    private String tokenId;


    public QrManagerIteractor(QrManagerContracts.Listener listener, Context context) {
        this.listener = listener;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    private ArrayList<QrItems> list;

    @Override
    public void getMyQrs() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        Log.d("QR_MANAGER", App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION));
        //prefs.saveData(TOKEN_FIREBASE_AUTH, user.getUid());
        //prefs.saveData(TOKEN_FIREBASE_AUTH, user.getUid());
        /*Objects.requireNonNull(auth.getCurrentUser()).getIdToken(false).addOnSuccessListener(getTokenResult -> {
            tokenId = getTokenResult.getToken();
            Log.d("QR_MANAGER", tokenId);
        });*/

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, URL_VERIFY_QR_USER, null, response -> {

                    list = new ArrayList<>();
                    try {
                        JSONArray data = response.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);
                            object.getString("name");
                            JSONObject qr = object.getJSONObject("qr");
                            JSONObject plateJson = qr.getJSONObject("Aux");
                            list.add(new QrItems(new QRUser(object.getString("name"),plateJson.getString("Pl")),plateJson.toString(),2));
                        }
                        listener.onSuccessQRs(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> listener.onErrorQRs()) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Yg-" + App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION));
                //headers.put("Authorization", "Yg-" + tokenId);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }


}
