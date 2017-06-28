package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class ObtenerCatalogoRequest implements Serializable {

    private String version;

    public ObtenerCatalogoRequest() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
