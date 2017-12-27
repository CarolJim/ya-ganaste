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
    private String FechaUltimoAcceso = "";
    private List<CuentaResponse> Cuentas;

    public Boolean getPasswordAsignado() {
        return PasswordAsignado;
    }

    public void setPasswordAsignado(Boolean passwordAsignado) {
        PasswordAsignado = passwordAsignado;
    }

    private Boolean PasswordAsignado = null;
    private String NumeroAgente = "";
    private String PetroNumero = "72851001";
    private String ClaveAgente = "7320";
    private int TipoAgente;
    @SerializedName("EsExtranjero")
    private boolean isExtranjero;

    private String NombreNegocio;


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

    public String getFechaUltimoAcceso() {
        return FechaUltimoAcceso;
    }

    public void setFechaUltimoAcceso(String fechaUltimoAcceso) {
        FechaUltimoAcceso = fechaUltimoAcceso;
    }

    public List<CuentaResponse> getCuentas() {
        return Cuentas;
    }

    public void setCuentas(List<CuentaResponse> cuentas) {
        Cuentas = cuentas;
    }

    public String getNumeroAgente() {
        return NumeroAgente;
    }

    public void setNumeroAgente(String numeroAgente) {
        NumeroAgente = numeroAgente;
    }

    public String getPetroNumero() {
        return PetroNumero;
    }

    public void setPetroNumero(String petroNumero) {
        PetroNumero = petroNumero;
    }

    public String getClaveAgente() {
        return ClaveAgente;
    }

    public void setClaveAgente(String claveAgente) {
        ClaveAgente = claveAgente;
    }

    public int getTipoAgente() {
        return TipoAgente;
    }

    public void setTipoAgente(int tipoAgente) {
        TipoAgente = tipoAgente;
    }

    public String getNombreNegocio() {
        return NombreNegocio;
    }

    public void setNombreNegocio(String NombreNegocio) {
        this.NombreNegocio = NombreNegocio;
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
