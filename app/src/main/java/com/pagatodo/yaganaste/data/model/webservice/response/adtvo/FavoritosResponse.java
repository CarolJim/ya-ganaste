package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class FavoritosResponse implements Serializable{

    private int IdTipoComercio;
    private int IdComercio;
    private String ImagenURL = "";
    private String Alias = "";
    private String Referencia = "";
    private String Nombre = "";
    private Double MontoMaximo;
    private String FechaUltimoMovimiento = "";

    public FavoritosResponse() {
    }

    public int getIdTipoComercio() {
        return IdTipoComercio;
    }

    public void setIdTipoComercio(int idTipoComercio) {
        IdTipoComercio = idTipoComercio;
    }

    public int getIdComercio() {
        return IdComercio;
    }

    public void setIdComercio(int idComercio) {
        IdComercio = idComercio;
    }

    public String getImagenURL() {
        return ImagenURL;
    }

    public void setImagenURL(String imagenURL) {
        ImagenURL = imagenURL;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Double getMontoMaximo() {
        return MontoMaximo;
    }

    public void setMontoMaximo(Double montoMaximo) {
        MontoMaximo = montoMaximo;
    }

    public String getFechaUltimoMovimiento() {
        return FechaUltimoMovimiento;
    }

    public void setFechaUltimoMovimiento(String fechaUltimoMovimiento) {
        FechaUltimoMovimiento = fechaUltimoMovimiento;
    }
}
