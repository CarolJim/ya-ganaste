package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class RegistroDongleRequest extends AdqRequest implements Serializable{

    private String serial = "";

    public RegistroDongleRequest() {
    }

    public RegistroDongleRequest(String serial) {
        this.serial = serial;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
