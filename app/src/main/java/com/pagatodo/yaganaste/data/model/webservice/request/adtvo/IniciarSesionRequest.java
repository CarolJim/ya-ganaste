package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.request.Request;

import java.io.Serializable;


public class IniciarSesionRequest extends Request implements Serializable {

    private String UsuarioCorreo = "";
    private String Contrasena = "";
    private String Version = "2.0.0";

    public IniciarSesionRequest() {
    }

    public IniciarSesionRequest(String usuarioCorreo, String contrasena) {
        UsuarioCorreo = usuarioCorreo;
        Contrasena = contrasena;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
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
