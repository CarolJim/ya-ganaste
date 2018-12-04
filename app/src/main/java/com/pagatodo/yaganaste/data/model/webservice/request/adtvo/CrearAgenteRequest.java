package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import com.google.gson.annotations.SerializedName;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class CrearAgenteRequest implements Serializable {

    @SerializedName("TipoAgente")
    private int tipoAgente;
    @SerializedName("NombreComercio")
    private String nombreComercio = "";
    @SerializedName("NumeroTelefono")
    private String numeroTelefono = "";
    @SerializedName("RazonSocial")
    private String razonSocial = "";
    @SerializedName("Giro")
    private int giro;
    @SerializedName("SubGiro")
    private int subGiro;
    @SerializedName("RFC")
    private String rfc = "";
    @SerializedName("Latitud")
    private int latitud;
    @SerializedName("Longitud")
    private int longitud;
    @SerializedName("Cuestionario")
    private List<CuestionarioEntity> cuestionario;
    @SerializedName("Folio")
    private String folio;

    @SerializedName("DomicilioNegocio")
    private DataObtenerDomicilio domicilioNegocio;

    public CrearAgenteRequest() {
        cuestionario = new ArrayList<>();
    }

    public CrearAgenteRequest(RegisterAgent registerAgent, int tipoAgente, String folio) {
        nombreComercio = registerAgent.getNombre();
        giro = registerAgent.getGiro().getIdGiro();
        subGiro = registerAgent.getSubGiros().getIdSubgiro();
        numeroTelefono = registerAgent.getTelefono();
        this.tipoAgente = tipoAgente;
        cuestionario = registerAgent.getCuestionario();
        this.folio = folio;
        DataObtenerDomicilio dataObtenerDomicilio = new DataObtenerDomicilio();
        dataObtenerDomicilio.setCp(registerAgent.getCodigoPostal());
        dataObtenerDomicilio.setCalle(registerAgent.getCalle());
        dataObtenerDomicilio.setColonia(registerAgent.getColonia());
        dataObtenerDomicilio.setEstado(registerAgent.getEstadoDomicilio());
        dataObtenerDomicilio.setIdColonia(registerAgent.getIdColonia());
        dataObtenerDomicilio.setNumeroExterior(registerAgent.getNumExterior());
        dataObtenerDomicilio.setNumeroInterior(registerAgent.getNumInterior());
        dataObtenerDomicilio.setIdEstado(registerAgent.getIdEstado());
        domicilioNegocio = dataObtenerDomicilio;
    }

    /* Empleado para registro de agente agregador */
    public CrearAgenteRequest(int tipoAgente, String nombreComercio, String numeroTelefono, int giro, String folio) {
        this.tipoAgente = tipoAgente;
        this.nombreComercio = nombreComercio;
        this.numeroTelefono = numeroTelefono;
        this.giro = giro;
        this.folio = folio;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public int getTipoAgente() {
        return tipoAgente;
    }

    public void setTipoAgente(int tipoAgente) {
        this.tipoAgente = tipoAgente;
    }

    public String getNombreComercio() {
        return nombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        this.nombreComercio = nombreComercio;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public int getSubGiro() {
        return subGiro;
    }

    public void setSubGiro(int subGiro) {
        this.subGiro = subGiro;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public int getLatitud() {
        return latitud;
    }

    public void setLatitud(int latitud) {
        this.latitud = latitud;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public int getGiro() {
        return giro;
    }

    public void setGiro(int giro) {
        this.giro = giro;
    }

    public List<CuestionarioEntity> getCuestionario() {
        return cuestionario;
    }

    public void setCuestionario(List<CuestionarioEntity> cuestionario) {
        this.cuestionario = cuestionario;
    }

    public DataObtenerDomicilio getDomicilioNegocio() {
        return domicilioNegocio;
    }

    public void setDomicilioNegocio(DataObtenerDomicilio domicilioNegocio) {
        this.domicilioNegocio = domicilioNegocio;
    }

    @Override
    public String toString() {
        return "CrearAgenteRequest{" +
                "tipoAgente=" + tipoAgente +
                ", nombreComercio='" + nombreComercio + '\'' +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", giro=" + giro +
                ", subGiro=" + subGiro +
                ", rfc='" + rfc + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", cuestionario=" + cuestionario +
                ", domicilioNegocio=" + domicilioNegocio +
                '}';
    }
}
