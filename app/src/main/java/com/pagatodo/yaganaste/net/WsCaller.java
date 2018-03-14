package com.pagatodo.yaganaste.net;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.interfaces.IServiceConsumer;
import com.pagatodo.yaganaste.interfaces.enums.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import static com.android.volley.Request.Method.POST;

/**
 * Created on 16/02/2017.
 *
 * @author flima
 * @version 1.0
 *          Clase que definir el cliente http.
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

        VolleySingleton volleySingleton = VolleySingleton.getInstance(App.getInstance().getApplicationContext());
        Log.d(TAG, "Request : " + request.get_url_request());
        if (request.getHeaders() != null && request.getHeaders().size() > 0) {
            Log.d(TAG, "Headers : ");
            for (String name : request.getHeaders().keySet()) {
                String key = name.toString();
                String value = request.getHeaders().get(name).toString();
                Log.d(TAG, key + " : " + value);
            }
        }

        if (request.getBody() != null)
            Log.d(TAG, "Body Request : " + request.getBody().toString());
        CustomJsonObjectRequest jsonRequest = new CustomJsonObjectRequest (
                request.getMethod(),
                request.getMethod() == POST ? request.get_url_request() : parseGetRequest(request.get_url_request(), request.getBody()),
                request.getMethod() == POST ? request.getBody() : null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Response Success : " + response.toString());
                        if (request.getRequestResult() != null) {
                            request.getRequestResult().onSuccess(new DataSourceResult(request.getMethod_name(), DataSource.WS, UtilsNet.jsonToObject(response.toString(), request.getTypeResponse())));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                        Log.d(TAG, "Request Failed : " + error.getMessage());
                        if (request.getRequestResult() != null) {
                            if (error.networkResponse != null ) {
                                Log.d(TAG, "Request Failed : " + error.networkResponse.statusCode);
                            }

                            request.getRequestResult().onFailed(new DataSourceResult(request.getMethod_name(), DataSource.WS, CustomErrors.getError(error)));
                        }
                    }
                }, request.getHeaders());

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                request.getTimeOut(),
                0,//Se quitan los reintentos para validar si esto arroja SocketException
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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
