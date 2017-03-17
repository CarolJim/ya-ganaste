package com.pagatodo.yaganaste.net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.interfaces.enums.DataSource;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 16/02/2017.
 *
 * @author flima
 * @version 1.0
 * Clase que definir el cliente http.
 *
 */
public class WsCaller implements IServiceConsumer {

    private final String TAG = this.getClass().getName();

    /**
     * Método para realizar la petición http segun los
     * datos de la petición pasada como parámetro.
     *
     * @param  request {@link WsRequest}
     */

    @Override
    public void sendJsonPost(final WsRequest request){

        VolleySingleton volleySingleton = VolleySingleton.getInstance(App.getInstance().getApplicationContext());
        Log.d(TAG, "Request Success: " + request.get_url_request());
        Log.d(TAG, "Params Request: " + request.getParams()!= null ? request.getParams() : "No Params" );
        JsonObjectRequest jsonRequest =  new JsonObjectRequest(
                request.getMethod(),
                request.get_url_request(),
                request.getParams(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Response Success: " + response.toString());
                        request.getRequestResult().onSuccess(new DataSourceResult(DataSource.WS,UtilsNet.jsonToObject(response.toString(),request.getTypeResponse())));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Request Failed: " + error.getMessage());
                        request.getRequestResult().onFailed(new DataSourceResult(DataSource.WS,null));
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-type", "application/json");
                /*Seteamos headers*/
                if(request.getHeaders()!=null){
                    for (Map.Entry<String,String> entry : request.getHeaders().entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        params.put(key, value);
                        Log.d(TAG, "Header Request: " + String.format("%s:%s",key,value));
                    }
                }

                return params;
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                request.getTimeOut(),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        volleySingleton.addToRequestQueue(jsonRequest);

    }

}
