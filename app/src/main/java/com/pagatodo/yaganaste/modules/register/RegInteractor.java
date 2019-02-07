package com.pagatodo.yaganaste.modules.register;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.QRs;
import com.pagatodo.yaganaste.data.model.RegisterAggregatorNew;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearAgenteRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarInformacionSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CrearAgenteResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.modules.data.QrItem;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.utils.Recursos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.PHONE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_FIREBASE_SESSION;

public class RegInteractor implements RegContracts.Iteractor, IRequestResult {

    private RegContracts.Listener listener;
    private RequestQueue requestQueue;

    public RegInteractor(RegContracts.Listener listener, Context context) {
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
                (Request.Method.POST, Recursos.URL_FRIGGS +
                        App.getContext().getResources().getString(R.string.validateQr), null, response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
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
                    listener.onErrorValidatePlate("QR Invalid");
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void assignmentQrs() {

        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        HashMap<String, String> map = new HashMap();
        map.put("Mbl", App.getInstance().getPrefs().loadData(PHONE_NUMBER).replace(" ", ""));
        map.put("DvcId", FirebaseInstanceId.getInstance().getToken());
        String userToken = App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION);


        for (QRs qrs : RegisterAggregatorNew.getInstance().getqRs()) {
            if (!qrs.isDigital()) {
                setAsignQrPhysical(qrs);
            } else {
                setAsignQrDigital(qrs);
            }
        }

    }

    @Override
    public void createAgent() {
        listener.showLoader(App.getContext().getString(R.string.creating_agent));
        RegisterAggregatorNew registerUserSingleton = RegisterAggregatorNew.getInstance();
        CrearAgenteRequest request = new CrearAgenteRequest(22, registerUserSingleton.getNombreNegocio(), "5555555555",
                registerUserSingleton.getIdGiro(), 245, null);
        try {
            ApiAdtvo.crearAgenteWallet(request, this);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onErrorService(App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void updateSession() {
        listener.showLoader("");
        try {
            ApiAdtvo.actualizarInformacionSesion(this);
        } catch ( OfflineException e) {
            e.printStackTrace();
            listener.onErrorService(App.getContext().getString(R.string.no_internet_access));
        }


    }

    private void setAsignQrDigital(QRs qrs) {
        RequestQueue requestQueue = Volley.newRequestQueue(App.getContext());
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", qrs.getAlias());
            jsonBody.put("bank", "148");
            jsonBody.put("account", SingletonUser.getInstance().getDataUser().getEmisor().getCuentas().get(0).getCLABE());
            Log.d("REQUEST", jsonBody.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String requestBody = jsonBody.toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Recursos.URL_FRIGGS + App.getContext().getResources().getString(R.string.newQr), null,
                response -> {
                    try {
                        Boolean success = response.getBoolean("success");
                        if (success) {
                            //presenter.onAsignQrPhysical();
                        } else {
                            //presenter.onErrorService(response.getString("message"))
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error ->
                Log.e("VOLLEY", error.toString())) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                return requestBody.getBytes(Charset.forName("utf-8"));
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headersQR = new HashMap<>();
                headersQR.put("Content-Type", "application/json");
                headersQR.put("Authorization", "Yg-" + App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION));
                return headersQR;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }


    private void setAsignQrPhysical(QRs qrs) {
        RequestQueue requestQueue = Volley.newRequestQueue(App.getContext());
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("plate", qrs.getPlate());
            jsonBody.put("name", qrs.getAlias());
            jsonBody.put("bank", "148");
            jsonBody.put("account", SingletonUser.getInstance().getDataUser().getEmisor().getCuentas().get(0).getCLABE());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String requestBody = jsonBody.toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Recursos.URL_FRIGGS +
                        App.getContext().getResources().getString(R.string.linkedQr), null,
                response -> {
                    try {
                        Boolean success = response.getBoolean("success");
                        if (success) {
                            //presenter.onAsignQrPhysical();
                        } else {
                            //presenter.onErrorService(response.getString("message"))
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.e("VOLLEY", error.toString())) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                return requestBody.getBytes(Charset.forName("utf-8"));
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headersQR = new HashMap<>();
                headersQR.put("Content-Type", "application/json");
                headersQR.put("Authorization", "Yg-" + App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION));

                return headersQR;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        if (dataSourceResult.getData() instanceof CrearAgenteResponse) {
            CrearAgenteResponse response = (CrearAgenteResponse) dataSourceResult.getData();
            if (response.getCodigoRespuesta() == CODE_OK) {
                listener.onSuccessValidatePlate("");
               // assignmentQrs();


            } else {
                listener.onErrorService(response.getMensaje());
            }
        }

        if (dataSourceResult.getData() instanceof ActualizarInformacionSesionResponse) {
            ActualizarInformacionSesionResponse response = (ActualizarInformacionSesionResponse) dataSourceResult.getData();
            if (response.getCodigoRespuesta() == CODE_OK) {
            listener.onSuccesupdateSession();

            } else {
                listener.onSErrorupdateSession();
            }
        }


    }

    @Override
    public void onFailed(DataSourceResult error) {
        listener.onErrorService(error.getData().toString());
    }
}

