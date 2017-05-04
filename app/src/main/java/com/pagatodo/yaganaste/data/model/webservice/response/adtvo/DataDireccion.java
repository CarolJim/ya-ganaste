package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataDireccion implements Serializable{

    private String CP = "";
    private String Calle = "";
    private String Colonia = "";
    private String Estado = "";
    private String IdColonia = "";
    private int IdDomicilio = 0;
    private String IdEstado = "";
    private String IdMunicipio = "";
    private String IdPais = "";
    private int IdTipoDomicilio = 0;
    private String Municipio = "";
    private String NumeroExterior = "";
    private String NumeroInterior = "";
    private String Pais = "";
    private String Referencia = "";

    public DataDireccion() {

    }


    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String calle) {
        Calle = calle;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        Colonia = colonia;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getIdColonia() {
        return IdColonia;
    }

    public void setIdColonia(String idColonia) {
        IdColonia = idColonia;
    }

    public int getIdDomicilio() {
        return IdDomicilio;
    }

    public void setIdDomicilio(int idDomicilio) {
        IdDomicilio = idDomicilio;
    }

    public String getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(String idEstado) {
        IdEstado = idEstado;
    }

    public String getIdMunicipio() {
        return IdMunicipio;
    }

    public void setIdMunicipio(String idMunicipio) {
        IdMunicipio = idMunicipio;
    }

    public String getIdPais() {
        return IdPais;
    }

    public void setIdPais(String idPais) {
        IdPais = idPais;
    }

    public int getIdTipoDomicilio() {
        return IdTipoDomicilio;
    }

    public void setIdTipoDomicilio(int idTipoDomicilio) {
        IdTipoDomicilio = idTipoDomicilio;
    }

    public String getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(String municipio) {
        Municipio = municipio;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        NumeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        NumeroInterior = numeroInterior;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String pais) {
        Pais = pais;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }
}
