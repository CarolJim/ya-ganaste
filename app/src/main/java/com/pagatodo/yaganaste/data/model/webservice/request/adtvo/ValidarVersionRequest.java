package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

/**
 * Created by Omar on 07/02/2018.
 */

public class ValidarVersionRequest {

    private int idplatform;

    public ValidarVersionRequest(int idplatform) {
        this.idplatform = idplatform;
    }

    public int getIdplatform() {
        return idplatform;
    }

    public void setIdplatform(int idplatform) {
        this.idplatform = idplatform;
    }
}
