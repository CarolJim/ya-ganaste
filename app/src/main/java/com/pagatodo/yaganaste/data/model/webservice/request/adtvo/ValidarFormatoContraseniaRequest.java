package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class ValidarFormatoContraseniaRequest implements Serializable {

    private String Contrasena = "";

    public ValidarFormatoContraseniaRequest() {
    }

    public ValidarFormatoContraseniaRequest(String contrasena) {
        Contrasena = contrasena;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }
}
