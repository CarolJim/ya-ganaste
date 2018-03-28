package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class UsuarioClienteResponse implements Serializable {

    private int IdUsuario;
    private String IdUsuarioAdquirente = "";
    private String NombreUsuario = "";
    private String Nombre = "";
    private String PrimerApellido = "";
    private String SegundoApellido = "";
    private String ImagenAvatarURL = "";
    private String TokenSesion = "";

    private String TokenSesionAdquirente = "";
    private List<CuentaResponse> Cuentas;
    private Boolean PasswordAsignado = null;
    @SerializedName("EsExtranjero")
    private boolean isExtranjero;

    public Boolean getPasswordAsignado() {
        return PasswordAsignado;
    }

    public void setPasswordAsignado(Boolean passwordAsignado) {
        PasswordAsignado = passwordAsignado;
    }

    public UsuarioClienteResponse() {
        Cuentas = new ArrayList<CuentaResponse>();
    }


    public UsuarioClienteResponse(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPrimerApellido() {
        return PrimerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        PrimerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return SegundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        SegundoApellido = segundoApellido;
    }

    public String getImagenAvatarURL() {
        return ImagenAvatarURL;
    }

    public void setImagenAvatarURL(String imagenAvatarURL) {
        ImagenAvatarURL = imagenAvatarURL;
    }

    public String getTokenSesion() {
        return TokenSesion;
    }

    public void setTokenSesion(String tokenSesion) {
        TokenSesion = tokenSesion;
    }

    public List<CuentaResponse> getCuentas() {
        return Cuentas;
    }

    public void setCuentas(List<CuentaResponse> cuentas) {
        Cuentas = cuentas;
    }

    public String getTokenSesionAdquirente() {
        return TokenSesionAdquirente;
    }

    public void setTokenSesionAdquirente(String tokenSesionAdquirente) {
        TokenSesionAdquirente = tokenSesionAdquirente;
    }

    public String getIdUsuarioAdquirente() {
        return IdUsuarioAdquirente;
    }

    public void setIdUsuarioAdquirente(String idUsuarioAdquirente) {
        IdUsuarioAdquirente = idUsuarioAdquirente;
    }

    public boolean isExtranjero() {
        return isExtranjero;
    }

    public void setExtranjero(boolean extranjero) {
        isExtranjero = extranjero;
    }
}
