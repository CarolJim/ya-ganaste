package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class TransaccionesPDSRequest implements Serializable{

    private DataPago pdsData;
    private ImplicitData implicitData;

    public TransaccionesPDSRequest() {
        pdsData = new DataPago();
        implicitData = new ImplicitData();
    }

    public DataPago getPdsData() {
        return pdsData;
    }

    public void setPdsData(DataPago pdsData) {
        this.pdsData = pdsData;
    }

    public ImplicitData getImplicitData() {
        return implicitData;
    }

    public void setImplicitData(ImplicitData implicitData) {
        this.implicitData = implicitData;
    }
}
