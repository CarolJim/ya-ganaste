package com.pagatodo.yaganaste.modules.qr.operations.EditQr;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.modules.data.QrItems;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_FIREBASE_SESSION;

public class EditQrInteractor implements EditQrContracts.Interactor{

    private static final String URL_UPDATE = "https://us-central1-frigg-1762c.cloudfunctions.net/pdtQR";

    private EditQrContracts.Listener listener;

    EditQrInteractor(EditQrContracts.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onEditQr(QrItems item) {

        RequestQueue requestQueue = Volley.newRequestQueue(App.getContext());
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name",item.getQrUser().getAlias());
            jsonBody.put("bank", "148");
            jsonBody.put("account", App.getInstance().getPrefs().loadData(CLABE_NUMBER)
                    .replace(" ",""));
            jsonBody.put("plate",item.getQrUser().getPlate());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String requestBody = jsonBody.toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, URL_UPDATE, null, response -> {
                    Log.d("QR_MANAGER",response.toString());
                    listener.onSuccessEdit();
                }, error -> listener.onErrorEdit("No se pudo hacer la operación")) {

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
            public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Yg-" + App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION));
                return headers;
            }

        };
        requestQueue.add(jsonObjectRequest);
    }
}
