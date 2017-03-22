package com.pagatodo.yaganaste.data.model.webservice.request.adq;

/**
 * Created by jvazquez on 28/10/2016.
 */

public class FirmaDeVoucherRequest {
    public String idTransaction = "";
    public SignatureData signaruteData;

    public FirmaDeVoucherRequest(){
        signaruteData = new SignatureData();
    }
}
