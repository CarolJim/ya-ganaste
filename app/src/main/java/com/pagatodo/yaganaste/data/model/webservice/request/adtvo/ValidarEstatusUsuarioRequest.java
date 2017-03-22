package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class ValidarEstatusUsuarioRequest implements Serializable{

    private String Usuario = "";

    public ValidarEstatusUsuarioRequest() {
    }

    public ValidarEstatusUsuarioRequest(String usuario) {
        Usuario = usuario;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }
}
