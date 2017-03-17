package com.pagatodo.yaganaste.net;

import java.lang.reflect.Type;
import java.util.HashMap;
/**
 * Created on 16/02/2017.
 *
 * @author flima
 * @version 1.0
 */
public class WsRequest {

    private int method;

    private HashMap<String,String> headers;

    private String _url_request;

    private String params;

    private IRequestResult requestResult;

    private int timeOut;

    private Type typeResponse;


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

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
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


    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }


    public Type getTypeResponse() {
        return typeResponse;
    }

    public void setTypeResponse(Type typeResponse) {
        this.typeResponse = typeResponse;
    }
}
