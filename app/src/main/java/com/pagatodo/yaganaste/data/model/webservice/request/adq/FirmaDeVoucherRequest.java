package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

public class FirmaDeVoucherRequest extends AdqRequest implements Serializable {
    private String idTransaction = "";
    private SignatureData signaruteData;

    public FirmaDeVoucherRequest() {
        signaruteData = new SignatureData();
    }

    public SignatureData getSignaruteData() {
        return signaruteData;
    }

    public void setSignaruteData(SignatureData signaruteData) {
        this.signaruteData = signaruteData;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }
}
