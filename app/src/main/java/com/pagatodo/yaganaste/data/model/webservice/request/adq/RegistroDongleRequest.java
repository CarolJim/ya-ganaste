package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class RegistroDongleRequest extends AdqRequest implements Serializable{

    private String Serial = "";

    public RegistroDongleRequest() {
    }

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String serial) {
        Serial = serial;
    }
}
