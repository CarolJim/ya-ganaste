package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

/**
 * Created by Omar on 23/02/2018.
 */

public class TypeRepaymentRequest implements Serializable {

    private int ID_TipoReembolso;

    public TypeRepaymentRequest(int ID_TipoReembolso) {
        this.ID_TipoReembolso = ID_TipoReembolso;
    }

    public int getID_TipoReembolso() {
        return ID_TipoReembolso;
    }

    public void setID_TipoReembolso(int ID_TipoReembolso) {
        this.ID_TipoReembolso = ID_TipoReembolso;
    }
}