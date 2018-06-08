package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

public class DetalleMovimientoRequest implements Serializable {

    private String NoSecUnicoPT, IdTransaction;

    public String getNoSecUnicoPT() {
        return "NoSecUnicoPT=" + NoSecUnicoPT;
    }

    public void setNoSecUnicoPT(String noSecUnicoPT) {
        NoSecUnicoPT = noSecUnicoPT;
    }

    public String getIdTransaction() {
        return "IdTransaction=" + IdTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        IdTransaction = idTransaction;
    }
}
