package com.pagatodo.yaganaste.data.model.webservice.response.adq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 22/02/2018.
 */

public class ObtieneTiposReembolsoResponse implements Serializable {

    private List<TiposReembolsoResponse> reembolsos;
    private DataResultAdq result;

    public ObtieneTiposReembolsoResponse() {
        reembolsos = new ArrayList<>();
        result = new DataResultAdq();
    }

    public List<TiposReembolsoResponse> getReembolsos() {
        return reembolsos;
    }

    public void setReembolsos(List<TiposReembolsoResponse> reembolsos) {
        this.reembolsos = reembolsos;
    }

    public DataResultAdq getResult() {
        return result;
    }

    public void setResult(DataResultAdq result) {
        this.result = result;
    }
}
