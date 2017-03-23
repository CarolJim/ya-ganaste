package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class TransaccionesTAERequest  extends AdqRequest implements Serializable{

    private DataPago TaeData;
    private ImplicitData implicitData;

    public TransaccionesTAERequest() {
        TaeData = new DataPago();
        implicitData = new ImplicitData();
    }

    public DataPago getTaeData() {
        return TaeData;
    }

    public void setTaeData(DataPago taeData) {
        TaeData = taeData;
    }

    public ImplicitData getImplicitData() {
        return implicitData;
    }

    public void setImplicitData(ImplicitData implicitData) {
        this.implicitData = implicitData;
    }
}
