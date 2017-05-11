package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.request.Request;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class IniciarSesionRequest extends Request implements Serializable{

    private String UsuarioCorreo = "";
    private String Contrasena = "";

    public IniciarSesionRequest() {
    }

    public IniciarSesionRequest(String usuarioCorreo, String contrasena,String telefono) {
        UsuarioCorreo = usuarioCorreo;
        Contrasena = contrasena;
    }

    public String getUsuarioCorreo() {
        return UsuarioCorreo;
    }

    public void setUsuarioCorreo(String usuarioCorreo) {
        UsuarioCorreo = usuarioCorreo;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }
}
