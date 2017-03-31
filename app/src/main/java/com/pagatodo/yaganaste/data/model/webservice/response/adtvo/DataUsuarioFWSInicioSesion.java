package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataUsuarioFWSInicioSesion implements Serializable{

    private boolean RequiereActivacionSMS;
    private String Semilla = "";
    private UsuarioClienteResponse Usuario;

    public DataUsuarioFWSInicioSesion() {

        Usuario = new UsuarioClienteResponse();
    }


    public boolean isRequiereActivacionSMS() {
        return RequiereActivacionSMS;
    }

    public void setRequiereActivacionSMS(boolean requiereActivacionSMS) {
        RequiereActivacionSMS = requiereActivacionSMS;
    }

    public String getSemilla() {
        return Semilla;
    }

    public void setSemilla(String semilla) {
        Semilla = semilla;
    }

    public UsuarioClienteResponse getUsuario() {
        return Usuario;
    }

    public void setUsuario(UsuarioClienteResponse usuario) {
        Usuario = usuario;
    }
}
