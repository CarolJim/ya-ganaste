package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class EmvData implements Serializable{

    private String tlv = "";

    public EmvData() {
    }

    public String getTlv() {
        return tlv;
    }

    public void setTlv(String tlv) {
        this.tlv = tlv;
    }
}
