package com.pagatodo.yaganaste.net;

import com.android.volley.Request;
import com.pagatodo.yaganaste.interfaces.enums.HttpMethods;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.utils.JsonManager;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.Map;

import static com.pagatodo.yaganaste.utils.Recursos.TIMEOUT;


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
     * @param  requestResult {@link IRequestResult} interface para obtener el resultado
     *                                              de la petición.
     */
    public static void  consumeWS(WebService method_name, HttpMethods method, String urlService, Map<String,String> headers, Object oRequest, Type responseType, IRequestResult requestResult){

        WsCaller wsCaller = new WsCaller();
        wsCaller.sendJsonPost(createRequest(method_name, method, urlService,oRequest ,headers,responseType, requestResult));
        /*switch (method){

            case METHOD_POST:
                wsCaller.sendJsonPost(createRequest(method, urlService,oRequest ,headers,responseType, requestResult));
                break;
        }*/
    }

    /**
     * Método para crear la {@link WsRequest} al servicio web
     *
     * @param  method {@link HttpMethods} enum que indica el tipo de método
     * @param  urlService {@link String} url del servicio a consumir
     * @param  requestResult {@link IRequestResult} interface para obtener el resultado
     *                                              de la petición.
     *
     *@return WsRequest petición para el servicio
     */

    public static WsRequest createRequest(WebService method_name, HttpMethods method, String urlService, Object oRequest, Map<String,String> headers, Type responseType, IRequestResult requestResult){

        if (oRequest == null)
            return null;

        WsRequest request = new WsRequest();
        request.setMethod_name(method_name);
        request.setHeaders(headers);
        request.setMethod(getMethodType(method));
        request.set_url_request(urlService);
        request.setBody(createParams(oRequest));
        request.setRequestResult(requestResult);
        request.setTypeResponse(responseType);
        request.setTimeOut(TIMEOUT);
        return request;
    }

    /**
     * Método para mapear los parámetros como json
     *
     * @return JSONObject con parámetros de la solicitud
     */
    private static JSONObject createParams(Object oRequest){

        return   JsonManager.madeJsonFromObject(oRequest);

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
