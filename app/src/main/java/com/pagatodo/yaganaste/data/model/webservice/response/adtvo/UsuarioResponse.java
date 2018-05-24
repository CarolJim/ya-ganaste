package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;
import java.util.ArrayList;

public class UsuarioResponse implements Serializable{
    private boolean EsExtranjero;
    private int IdEstatus;
    private int IdUsuario;
    private String IdUsuarioAdquirente = "";
    private String ImagenAvatarURL = "";
    private String NombreUsuario = "";
    private Boolean PasswordAsignado;
    private ArrayList<RolesResponse> Roles = new ArrayList<>();
    private String Semilla = "";
    private String TokenSesion = "";
    private String TokenSesionAdquirente = "";

    public boolean isEsExtranjero() {
        return EsExtranjero;
    }

    public void setEsExtranjero(boolean esExtranjero) {
        EsExtranjero = esExtranjero;
    }

    public int getIdEstatus() {
        return IdEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        IdEstatus = idEstatus;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getIdUsuarioAdquirente() {
        return IdUsuarioAdquirente;
    }

    public void setIdUsuarioAdquirente(String idUsuarioAdquirente) {
        IdUsuarioAdquirente = idUsuarioAdquirente;
    }

    public String getImagenAvatarURL() {
        return ImagenAvatarURL;
    }

    public void setImagenAvatarURL(String imagenAvatarURL) {
        ImagenAvatarURL = imagenAvatarURL;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public Boolean getPasswordAsignado() {
        return PasswordAsignado;
    }

    public void setPasswordAsignado(Boolean passwordAsignado) {
        PasswordAsignado = passwordAsignado;
    }

    public ArrayList<RolesResponse> getRoles() {
        return Roles;
    }

    public void setRoles(ArrayList<RolesResponse> roles) {
        Roles = roles;
    }

    public String getSemilla() {
        return Semilla;
    }

    public void setSemilla(String semilla) {
        Semilla = semilla;
    }

    public String getTokenSesion() {
        return TokenSesion;
    }

    public void setTokenSesion(String tokenSesion) {
        TokenSesion = tokenSesion;
    }

    public String getTokenSesionAdquirente() {
        return TokenSesionAdquirente;
    }

    public void setTokenSesionAdquirente(String tokenSesionAdquirente) {
        TokenSesionAdquirente = tokenSesionAdquirente;
    }

    /*
    "IdEstatus": 5,
            "IdUsuario": 4016,
            "IdUsuarioAdquirente": "",
            "ImagenAvatarURL": "http://10.10.45.11:8033/RecursosApp/RecursosYaGanaste/Avatar/bd1e98dcf6560edd7e707efb8eaff96044c63a6732a2c6a1cdfe516ee5d8ef4b_{0}.png",
            "NombreUsuario": "Maria Monserrat",
            "PasswordAsignado": true,
            "Roles": [
                {
                    "IdRol": 0,
                    "NombreRol": null
                }
            ],
            "Semilla": "",
            "TokenSesion": "FD2ECA0AB9CDB2E229ADD86609D9B6548F5013A893E5A983F45BA8BAFF93C49949B4AC789D7B2956A2BB4E1D0BE2EDCE",
            "TokenSesionAdquirente": ""
     */
}
