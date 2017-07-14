package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class RecibirPagosRequest extends AdqRequest implements Serializable {

    private DataPago data;
    private ImplicitData implicitData;
    private String TipoSevicio = "";

    public RecibirPagosRequest() {
        data = new DataPago();
        implicitData = new ImplicitData();
    }

    public DataPago getData() {
        return data;
    }

    public void setData(DataPago data) {
        this.data = data;
    }

    public ImplicitData getImplicitData() {
        return implicitData;
    }

    public void setImplicitData(ImplicitData implicitData) {
        this.implicitData = implicitData;
    }

    public String getTipoSevicio() {
        return TipoSevicio;
    }

    public void setTipoSevicio(String tipoSevicio) {
        TipoSevicio = tipoSevicio;
    }
}
