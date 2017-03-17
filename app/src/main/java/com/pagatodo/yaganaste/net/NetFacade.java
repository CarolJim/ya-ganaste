package com.pagatodo.yaganaste.net;

import android.content.Context;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pagatodo.yaganaste.interfaces.enums.HttpMethods;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created on 16/02/2017.
 *
 * @author flima
 * @version 1.0
 *
 * Clase Facade para el cliente Http
 *
 */
public class NetFacade {

    /**
     * Método para consumir un ws a partir de los parámetros enviados
     *
     * @param  method {@link HttpMethods} enum que indica el tipo de método
     * @param  urlService {@link String} url del servicio a consumir
     * @param  headers {@link HashMap} map de headers de la solicitud
     * @param  oRequest {@link Object} con los datos de la solicitud
     * @param  responseType {@link Type} con el tipo de Objeto que regresa el WS
     * @param  requestResult {@link IRequestResult} interface para obtener el resultado
     *                                              de la petición.
     */
    public static void  consumeWS(HttpMethods method, String urlService, HashMap<String,String> headers, Object oRequest, Type responseType, IRequestResult requestResult){

        WsCaller wsCaller = new WsCaller();
        switch (method){

            case METHOD_GET:
                wsCaller.sendJsonPost(createRequest(method, urlService,oRequest ,headers,responseType, requestResult));
                break;

            case METHOD_POST:
                wsCaller.sendJsonPost(createRequest(method, urlService,oRequest ,headers,responseType, requestResult));
                break;
        }
    }


    /**
     * Método para crear la {@link WsRequest} al servicio web
     *
     * @param  method {@link HttpMethods} enum que indica el tipo de método
     * @param  urlService {@link String} url del servicio a consumir
     * @param  params {@link HashMap} map de parámetros de la solicitud
     * @param  requestResult {@link IRequestResult} interface para obtener el resultado
     *                                              de la petición.
     *
     *@return WsRequest petición para el servicio
     */

    public static WsRequest createRequest(HttpMethods method, String urlService, Object oRequest, HashMap<String,String> headers, Type responseType, IRequestResult requestResult){

        if (oRequest == null)
            return null;

        WsRequest request = new WsRequest();
        request.setHeaders(headers);
        request.setMethod(getMethodType(method));
        request.set_url_request(urlService);
        request.setParams(createParams(oRequest));
        request.setRequestResult(requestResult);
        request.setTypeResponse(responseType);
        request.setTimeOut(15000);

        return request;
    }

    /**
     * Método para mapear los parámetros como json
     *
     * @param  paramsMap {@link Context}
     *
     * @return JSONObject con parámetros de la solicitud
     */
    private static String createParams(Object oRequest){


        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String tmp = "{\"request\": " + gson.toJson(oRequest) + "}";


      /*  JSONObject params = new JSONObject();

        try {

        for(String paramKey : paramsMap.keySet())
            params.put(paramKey,paramsMap.get(paramKey));

        } catch (JSONException e) {
                e.printStackTrace();
        }*/

        return  tmp;

    }

    /**
     * Método para obtener el tipo de método para Volley
     *
     * @param  method {@link HttpMethods}
     *
     * @return Tipo de método
     */
    private static int getMethodType(HttpMethods method){

        switch (method){

            case METHOD_GET:

                return Request.Method.GET;

            case METHOD_POST:

                return Request.Method.POST;

        }

        return 0;

    }

}
