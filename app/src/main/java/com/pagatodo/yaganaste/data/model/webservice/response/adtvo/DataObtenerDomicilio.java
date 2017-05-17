package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Guerra on 04/05/2017.
 */

public class DataObtenerDomicilio implements Serializable {

    @SerializedName("CP")
    private String cp;
    @SerializedName("Calle")
    private String calle;
    @SerializedName("Colonia")
    private String colonia;
    @SerializedName("Estado")
    private String estado;
    @SerializedName("IdColonia")
    private String idColonia;
    @SerializedName("IdDomicilio")
    private String idDomicilio;
    @SerializedName("IdEstado")
    private String idEstado;
    @SerializedName("IdMunicipio")
    private String idMunicipio;
    @SerializedName("IdPais")
    private String idParis;
    @SerializedName("IdTipoDomicilio")
    private String idTipoDomicilio;
    @SerializedName("Municipio")
    private String municipio;
    @SerializedName("NumeroExterior")
    private String numeroExterior;
    @SerializedName("NumeroInterior")
    private String numeroInterior;
    @SerializedName("Pais")
    private String pais;
    @SerializedName("Referencia")
    private String referencia;

    //No viene en el servicio
    private List<ColoniasResponse> coloniasDomicilio;


    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdColonia() {
        return idColonia;
    }

    public void setIdColonia(String idColonia) {
        this.idColonia = idColonia;
    }

    public String getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(String idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    public String getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(String idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getIdParis() {
        return idParis;
    }

    public void setIdParis(String idParis) {
        this.idParis = idParis;
    }

    public String getIdTipoDomicilio() {
        return idTipoDomicilio;
    }

    public void setIdTipoDomicilio(String idTipoDomicilio) {
        this.idTipoDomicilio = idTipoDomicilio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public List<ColoniasResponse> getColoniasDomicilio() {
        return coloniasDomicilio;
    }

    public void setColoniasDomicilio(List<ColoniasResponse> coloniasToAdd) {
        this.coloniasDomicilio = new ArrayList<>();
        for (ColoniasResponse coloniasResponse : coloniasToAdd) {
            coloniasDomicilio.add(new ColoniasResponse(coloniasResponse.getColoniaId(), coloniasResponse.getColonia(),
                    coloniasResponse.getIdMunicipio(), coloniasResponse.getMunicipio(), coloniasResponse.getIdEstado(),
                    coloniasResponse.getEstado()));
        }
    }
}
