package com.pagatodo.yaganaste.net;

import com.pagatodo.yaganaste.interfaces.enums.WebService;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created on 16/02/2017.
 *
 * @author flima
 * @version 1.0
 */
public class WsRequest {

    private int method;

    private Map<String,String> headers;

    private String _url_request;

    private JSONObject body;

    private IRequestResult requestResult;

    private int timeOut;

    private Type typeResponse;

    private WebService method_name;


    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String get_url_request() {
        return _url_request;
    }

    public void set_url_request(String _url_request) {
        this._url_request = _url_request;
    }

    public JSONObject getBody() {
        return body;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

    public IRequestResult getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(IRequestResult requestResult) {
        this.requestResult = requestResult;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }


    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }


    public Type getTypeResponse() {
        return typeResponse;
    }

    public void setTypeResponse(Type typeResponse) {
        this.typeResponse = typeResponse;
    }

    public WebService getMethod_name() {
        return method_name;
    }

    public void setMethod_name(WebService method_name) {
        this.method_name = method_name;
    }
}
