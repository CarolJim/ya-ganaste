package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class CrearAgenteRequest implements Serializable{

    private int TipoAgente;
    private String NombreComercio = "";
    private String NumeroTelefono = "";
    private String RazonSocial = "";
    //private int Giro;
    private int SubGiro;
    private String RFC = "";
    private int Latitud;
    private int Longitud;
    private List<CuestionarioEntity> Cuestionario;

    public CrearAgenteRequest() {

        Cuestionario = new ArrayList<CuestionarioEntity>();
    }

    public int getTipoAgente() {
        return TipoAgente;
    }

    public void setTipoAgente(int tipoAgente) {
        TipoAgente = tipoAgente;
    }

    public String getNombreComercio() {
        return NombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        NombreComercio = nombreComercio;
    }

    public String getNumeroTelefono() {
        return NumeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        NumeroTelefono = numeroTelefono;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        RazonSocial = razonSocial;
    }

    /*public int getGiro() {
        return Giro;
    }

    public void setGiro(int giro) {
        Giro = giro;
    }*/

    public int getSubGiro() {
        return SubGiro;
    }

    public void setSubGiro(int subGiro) {
        SubGiro = subGiro;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public int getLatitud() {
        return Latitud;
    }

    public void setLatitud(int latitud) {
        Latitud = latitud;
    }

    public int getLongitud() {
        return Longitud;
    }

    public void setLongitud(int longitud) {
        Longitud = longitud;
    }

    public List<CuestionarioEntity> getCuestionario() {
        return Cuestionario;
    }

    public void setCuestionario(List<CuestionarioEntity> cuestionario) {
        Cuestionario = cuestionario;
    }
}
