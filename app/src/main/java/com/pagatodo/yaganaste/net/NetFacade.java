package com.pagatodo.yaganaste.net;

import android.util.Log;

import com.android.volley.Request;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.AdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.AdqRequestNoTag;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.interfaces.enums.HttpMethods;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.utils.JsonManager;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.pagatodo.yaganaste.utils.Recursos.CONNECTION_TIMEOUT;

/**
 * Created on 16/02/2017.
 *
 * @author flima
 * @version 1.0
 * <p>
 * Clase Facade para el cliente Http
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


    public static void consumeWSnotag(WebService method_name, HttpMethods method, String urlService,
                                      Map<String, String> headers, Object oRequest, Type responseType,
                                      IRequestResult requestResult, boolean needPins) throws OfflineException {
        if (UtilsNet.isOnline(App.getContext())) {
            if (!needPins)
                disableSSLValidation();
            WsCaller wsCaller = new WsCaller();
            wsCaller.sendJsonPost(createRequestnorequesttag(method_name, method, urlService, oRequest,
                    getMethodType(method) == POST, headers, responseType, requestResult), needPins);
        } else {
            throw new OfflineException();
        }
    }

    /**
     * Create a trust manager for Web Services that include SSL Certificates
     * WARNING: ONLY USED WITH WS THAT AREN'T PART OF BANKING PROJECT
     */
    private static void disableSSLValidation() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };
        // Install the all-trusting trust manager
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            Log.d("test_disable", "OK========");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
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
        request.setTimeOut(App.getInstance().getPrefs().loadDataInt(CONNECTION_TIMEOUT));
        return request;
    }

    public static WsRequest createRequestnorequesttag(WebService method_name, HttpMethods method, String urlService, Object oRequest, boolean envolve, Map<String, String> headers, Type responseType, IRequestResult requestResult) {

        WsRequest request = new WsRequest();
        request.setMethod_name(method_name);
        request.setHeaders(headers);
        request.setMethod(getMethodType(method));
        request.set_url_request(urlService);
        request.setBody(createParamsnotag(envolve, oRequest));
        request.setRequestResult(requestResult);
        request.setTypeResponse(responseType);
        request.setTimeOut(App.getInstance().getPrefs().loadDataInt(CONNECTION_TIMEOUT));
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
                }
                if (oRequest instanceof AdqRequestNoTag) {
                    return tmp;
                } else {
                    return JsonManager.madeJson(tmp);
                }
            } else {
                return tmp;
            }
        }
        return null;
    }


    private static JSONObject createParamsnotag(boolean envolve, Object oRequest) {

        if (oRequest != null) {
            JSONObject tmp = JsonManager.madeJsonFromObject(oRequest);
            if (envolve) {
                return (tmp);
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
