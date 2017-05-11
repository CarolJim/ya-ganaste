package com.pagatodo.yaganaste.data.model.webservice.response.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataMovimientoAdq implements Serializable {

    private String compania;
    private String descripcionRechazo;
    private boolean esAprobada;
    private boolean esCargo;
    private boolean esPendiente;
    private String fecha;
    private String idTransaction;
    private String lat;
    private String lon;
    private String monto;
    private String montoAdicional;
    private String noAutorizacion;
    private String noSecUnicoPT;
    private String nombre;
    private String operacion;
    private String referencia;
    private String urlMapa;
    private String Afiliacion;
    private String BancoEmisor;
    private String MarcaTarjetaBancaria;
    private String NoAgente;
    private String RazonSocialAgente;
    private String TipoTarjetaBancaria;


    public DataMovimientoAdq() {
    }



    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public String getDescripcionRechazo() {
        return descripcionRechazo;
    }

    public void setDescripcionRechazo(String descripcionRechazo) {
        this.descripcionRechazo = descripcionRechazo;
    }

    public boolean isEsAprobada() {
        return esAprobada;
    }

    public void setEsAprobada(boolean esAprobada) {
        this.esAprobada = esAprobada;
    }

    public boolean isEsCargo() {
        return esCargo;
    }

    public void setEsCargo(boolean esCargo) {
        this.esCargo = esCargo;
    }

    public boolean isEsPendiente() {
        return esPendiente;
    }

    public void setEsPendiente(boolean esPendiente) {
        this.esPendiente = esPendiente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getMontoAdicional() {
        return montoAdicional;
    }

    public void setMontoAdicional(String montoAdicional) {
        this.montoAdicional = montoAdicional;
    }

    public String getNoAutorizacion() {
        return noAutorizacion;
    }

    public void setNoAutorizacion(String noAutorizacion) {
        this.noAutorizacion = noAutorizacion;
    }

    public String getNoSecUnicoPT() {
        return noSecUnicoPT;
    }

    public void setNoSecUnicoPT(String noSecUnicoPT) {
        this.noSecUnicoPT = noSecUnicoPT;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getUrlMapa() {
        return urlMapa;
    }

    public void setUrlMapa(String urlMapa) {
        this.urlMapa = urlMapa;
    }

    public String getAfiliacion() {
        return Afiliacion;
    }

    public void setAfiliacion(String afiliacion) {
        Afiliacion = afiliacion;
    }

    public String getBancoEmisor() {
        return BancoEmisor;
    }

    public void setBancoEmisor(String bancoEmisor) {
        BancoEmisor = bancoEmisor;
    }

    public String getMarcaTarjetaBancaria() {
        return MarcaTarjetaBancaria;
    }

    public void setMarcaTarjetaBancaria(String marcaTarjetaBancaria) {
        MarcaTarjetaBancaria = marcaTarjetaBancaria;
    }

    public String getNoAgente() {
        return NoAgente;
    }

    public void setNoAgente(String noAgente) {
        NoAgente = noAgente;
    }

    public String getRazonSocialAgente() {
        return RazonSocialAgente;
    }

    public void setRazonSocialAgente(String razonSocialAgente) {
        RazonSocialAgente = razonSocialAgente;
    }

    public String getTipoTarjetaBancaria() {
        return TipoTarjetaBancaria;
    }

    public void setTipoTarjetaBancaria(String tipoTarjetaBancaria) {
        TipoTarjetaBancaria = tipoTarjetaBancaria;
    }
}
