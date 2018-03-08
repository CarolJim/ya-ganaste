package com.pagatodo.yaganaste.net;

import com.android.volley.Request;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.AdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.AdqRequestNoTag;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.enums.HttpMethods;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.utils.JsonManager;
import com.pagatodo.yaganaste.utils.UI;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.pagatodo.yaganaste.utils.Recursos.TIMEOUT;


/**
 * Created on 16/02/2017.
 *
 * @author flima
 * @version 1.0
 *          <p>
 *          Clase Facade para el cliente Http
 */
public class NetFacade {


    /**
     * Método para consumir un ws a partir de los parámetros enviados
     *
     * @param method        {@link HttpMethods} enum que indica el tipo de método
     * @param urlService    {@link String} url del servicio a consumir
     * @param requestResult {@link IRequestResult} interface para obtener el resultado
     *                      de la petición.
     */
    public static void consumeWS(WebService method_name, HttpMethods method, String urlService, Map<String, String> headers, Object oRequest, Type responseType, IRequestResult requestResult) throws OfflineException {

        if (UtilsNet.isOnline(App.getContext())) {
            WsCaller wsCaller = new WsCaller();
            wsCaller.sendJsonPost(createRequest(method_name, method, urlService, oRequest,
                    getMethodType(method) == POST, headers, responseType, requestResult));
        } else {
            throw new OfflineException();
        }
    }

    public static void consumeWS(WebService method_name, HttpMethods method, String urlService, Map<String, String> headers, Object oRequest, boolean envolve, Type responseType, IRequestResult requestResult) throws OfflineException {

        if (UtilsNet.isOnline(App.getContext())) {
            WsCaller wsCaller = new WsCaller();
            wsCaller.sendJsonPost(createRequest(method_name, method, urlService, oRequest, envolve, headers, responseType, requestResult));
        } else {
            //UI.showToastShort(App.getContext().getString(R.string.no_internet_access),App.getContext());
            throw new OfflineException();
        }
    }

    /**
     * Método para crear la {@link WsRequest} al servicio web
     *
     * @param method        {@link HttpMethods} enum que indica el tipo de método
     * @param urlService    {@link String} url del servicio a consumir
     * @param requestResult {@link IRequestResult} interface para obtener el resultado
     *                      de la petición.
     * @return WsRequest petición para el servicio
     */

    public static WsRequest createRequest(WebService method_name, HttpMethods method, String urlService, Object oRequest, boolean envolve, Map<String, String> headers, Type responseType, IRequestResult requestResult) {

        WsRequest request = new WsRequest();
        request.setMethod_name(method_name);
        request.setHeaders(headers);
        request.setMethod(getMethodType(method));
        request.set_url_request(urlService);
        request.setBody(createParams(envolve, oRequest));
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
    private static JSONObject createParams(boolean envolve, Object oRequest) {

        if (oRequest != null) {
            JSONObject tmp = JsonManager.madeJsonFromObject(oRequest);
            if (envolve) {
                if (oRequest instanceof AdqRequest) {
                    return JsonManager.madeJsonAdquirente(tmp);
                } if (oRequest instanceof AdqRequestNoTag) {
                    return tmp;
                }else{
                    return JsonManager.madeJson(tmp);
                }
            } else {
                return tmp;
            }
        }
        return null;
    }


    /**
     * Método para obtener el tipo de método para Volley
     *
     * @param method {@link HttpMethods}
     * @return Tipo de método
     */
    private static int getMethodType(HttpMethods method) {

        switch (method) {

            case METHOD_GET:
                return GET;
            case METHOD_POST:
                return Request.Method.POST;
        }

        return 0;

    }

}
