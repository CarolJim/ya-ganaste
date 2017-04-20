package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class UsuarioClienteResponse  implements Serializable {

    private int IdUsuario;
    private String NombreUsuario = "";
    private String Nombre = "";
    private String PrimerApellido = "";
    private String SegundoApellido = "";
    private String ImagenAvatarURL = "";
    private String TokenSesion = "";
    private String FechaUltimoAcceso = "";
    private List<CuentaResponse> Cuentas;
    private String NumeroAgente = "";
    private String PetroNumero = "";
    private String ClaveAgente = "";
    private int TipoAgente;

    private String nombreComercio;


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

    public String getNombreComercio() {
        return nombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        this.nombreComercio = nombreComercio;
    }
}
