package com.pagatodo.yaganaste.modules.qr.operations.AgregarQRVirtual;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.modules.management.ApisFriggs;
import com.pagatodo.yaganaste.modules.management.apis.FriggsHeaders;
import com.pagatodo.yaganaste.modules.management.apis.FrigsMethod;
import com.pagatodo.yaganaste.modules.management.apis.ListenerFriggs;
import com.pagatodo.yaganaste.modules.management.request.QrRequest;

import org.json.JSONObject;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.NEW_QR;
import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.URL_FRIGGS;

public class AgregarVirtQRIteractor implements  AgregarVirtContracts.Iteractor, ListenerFriggs {

    private AgregarVirtContracts.Listener listener;
    private RequestQueue requestQueue;

    AgregarVirtQRIteractor(AgregarVirtContracts.Listener iteractor) {
        this.listener = iteractor;
        this.requestQueue = Volley.newRequestQueue(App.getContext());
    }

    @Override
    public void validarQRValido(String alias) {
        ApisFriggs apisFriggs = new ApisFriggs(this);
        QrRequest qrRequest = new QrRequest("",alias,"148", App.getInstance().getPrefs()
                .loadData(CLABE_NUMBER).replace(" ",""));
        requestQueue.add(apisFriggs.sendRequest(FrigsMethod.POST,URL_FRIGGS +
                        App.getContext().getResources().getString(R.string.newQr),
                FriggsHeaders.getHeadersBasic(),qrRequest,NEW_QR));

        /*JSONObject jsonBody = new JSONObject();
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
        requestQueue.add(jsonObjectRequest);*/
    }

    @Override
    public void onSuccess(WebService webService, JSONObject response) {
        listener.onSuccessQRs();
    }

    @Override
    public void onError() {
        listener.onErrorQRs();
    }
}
