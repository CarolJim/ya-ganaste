package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

/**
 * Created by jvazquez on 28/10/2016.
 */

public class FirmaDeVoucherRequest extends AdqRequest  implements Serializable{
    public String idTransaction = "";
    public SignatureData signaruteData;

    public FirmaDeVoucherRequest(){
        signaruteData = new SignatureData();
    }
}
