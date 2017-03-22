package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataEstatusTarjeta implements Serializable {

    private int StatusId;

    public DataEstatusTarjeta() {
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }
}
