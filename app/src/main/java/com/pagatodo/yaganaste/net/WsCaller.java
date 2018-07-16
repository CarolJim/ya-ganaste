package com.pagatodo.yaganaste.net;

import android.os.Trace;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.interfaces.IServiceConsumer;
import com.pagatodo.yaganaste.interfaces.enums.DataSource;
import com.pagatodo.yaganaste.ui_wallet.trace.Tracer;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import static com.android.volley.Request.Method.POST;
import static com.pagatodo.yaganaste.ui_wallet.trace.Tracer.FAILED;
import static com.pagatodo.yaganaste.ui_wallet.trace.Tracer.SUCESS;
import static com.pagatodo.yaganaste.utils.ForcedUpdateChecker.TRACE_SUCCESS_WS;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;

/**
 * Created on 16/02/2017.
 *
 * @author flima
 * @version 1.0
 * Clase que definir el cliente http.
 */
public class WsCaller implements IServiceConsumer {

    private final String TAG = this.getClass().getSimpleName();

    /**
     * Método para realizar la petición http segun los
     * datos de la petición pasada como parámetro.
     *
     * @param request {@link WsRequest}
     */

    @Override
    public void sendJsonPost(final WsRequest request) {
        Tracer tracer = new Tracer(RequestHeaders.getUsername(),request.getMethod_name().name());

        VolleySingleton volleySingleton = VolleySingleton.getInstance(App.getInstance().getApplicationContext());
        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
            Log.d(TAG, "Request : " + request.get_url_request());
        }
        if (request.getHeaders() != null && request.getHeaders().size() > 0) {
            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                Log.d(TAG, "Headers : ");
            }
            for (String name : request.getHeaders().keySet()) {
                String key = name.toString();
                String value = request.getHeaders().get(name).toString();
                if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                    Log.d(TAG, key + " : " + value);
                }
            }
        }

        if (request.getBody() != null)
            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                Log.d(TAG, "Body Request : " + request.getBody().toString());
            }
        CustomJsonObjectRequest jsonRequest = new CustomJsonObjectRequest(
                request.getMethod(),
                request.getMethod() == POST ? request.get_url_request() : parseGetRequest(request.get_url_request(), request.getBody()),
                request.getMethod() == POST ? request.getBody() : null,
                response -> {
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.d(TAG, "Response Success : " + response.toString());
                    }
                    tracer.End();
                    if (request.getRequestResult() != null) {
                        DataSourceResult dataSourceResult = new DataSourceResult(request.getMethod_name(), DataSource.WS, UtilsNet.jsonToObject(response.toString(), request.getTypeResponse()));
                        GenericResponse genericResponse  = (GenericResponse) dataSourceResult.getData();
                        if (genericResponse.getCodigoRespuesta() != Recursos.CODE_OK){


                                tracer.setStatusRequest(SUCESS);
                                tracer.setStatusWS(String.valueOf(genericResponse.getCodigoRespuesta()));
                                tracer.setConnection(Utils.getTypeConnection());
                                tracer.setDuration(tracer.getFinalTime()-tracer.getStartTime());
                                tracer.getTracerSucess();

                        } else {

                            tracer.setStatusRequest(FAILED);
                            tracer.setStatusWS(String.valueOf(genericResponse.getCodigoRespuesta()));
                            tracer.setConnection(Utils.getTypeConnection());
                            tracer.setDuration(tracer.getFinalTime() - tracer.getStartTime());
                            tracer.getTracerError();
                        }

                        request.getRequestResult().onSuccess(dataSourceResult);


                    } else {

                        tracer.setStatusRequest(FAILED);
                        tracer.setStatusWS("Null");
                        tracer.setConnection(Utils.getTypeConnection());
                        tracer.setDuration(tracer.getFinalTime() - tracer.getStartTime());
                        tracer.getTracerError();
                    }

                },
                error -> {
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.d(TAG, error.toString());
                        Log.d(TAG, "Request Failed : " + error.getMessage());
                    }
                    if (request.getRequestResult() != null) {
                        if (error.networkResponse != null) {
                            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                                Log.d(TAG, "Request Failed : " + error.networkResponse.statusCode);
                            }
                        }
                        request.getRequestResult().onFailed(new DataSourceResult(request.getMethod_name(), DataSource.WS, CustomErrors.getError(error)));
                    }
                    tracer.End();
                    tracer.setStatusRequest(FAILED);
                    tracer.setStatusWS("Time out");
                    tracer.setConnection(Utils.getTypeConnection());
                    tracer.setDuration(tracer.getFinalTime() - tracer.getStartTime());
                    tracer.getTracerError();

                }, request.getHeaders());

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                request.getTimeOut(),
                0,//Se quitan los reintentos para validar si esto arroja SocketException
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //TRACER

        tracer.Start();
        volleySingleton.addToRequestQueue(jsonRequest);
    }

    private String parseGetRequest(String mUrl, JSONObject request) {
        if (request == null) {
            return mUrl;
        }
        StringBuilder stringBuilder = new StringBuilder(mUrl);
        int i = 1;

        Iterator<String> iterator = request.keys();
        String key, keyToAdd, value;
        while (iterator.hasNext()) {
            key = iterator.next();

            try {
                keyToAdd = URLEncoder.encode(key, "UTF-8");
                value = URLEncoder.encode(request.get(key).toString(), "UTF-8");
                if (i == 1) {
                    stringBuilder.append("?" + keyToAdd + "=" + value);
                } else {
                    stringBuilder.append("&" + keyToAdd + "=" + value);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }

        return stringBuilder.toString();
    }

}
