package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class IniciarSesionRequest implements Serializable{

    private String UsuarioCorreo = "";
    private String Contrasena = "";
    private String Telefono = "";

    public IniciarSesionRequest() {
    }

    public IniciarSesionRequest(String usuarioCorreo, String contrasena,String telefono) {
        UsuarioCorreo = usuarioCorreo;
        Contrasena = contrasena;
        Telefono = telefono;
    }

    public String getUsuarioCorreo() {
        return UsuarioCorreo;
    }

    public void setUsuarioCorreo(String usuarioCorreo) {
        UsuarioCorreo = usuarioCorreo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }
}
