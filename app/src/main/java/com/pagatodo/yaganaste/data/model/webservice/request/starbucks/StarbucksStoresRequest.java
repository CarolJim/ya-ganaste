package com.pagatodo.yaganaste.data.model.webservice.request.starbucks;

import java.io.Serializable;

public class StarbucksStoresRequest implements Serializable {

    private String busqueda;
    private double latitudBusqueda, latitudOrigen, longitudBusqueda, longitudOrigen;

    public StarbucksStoresRequest(String busqueda, double latitudBusqueda, double latitudOrigen, double longitudBusqueda, double longitudOrigen) {
        this.busqueda = busqueda;
        this.latitudBusqueda = latitudBusqueda;
        this.latitudOrigen = latitudOrigen;
        this.longitudBusqueda = longitudBusqueda;
        this.longitudOrigen = longitudOrigen;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public Double getLatitudBusqueda() {
        return latitudBusqueda;
    }

    public void setLatitudBusqueda(Double latitudBusqueda) {
        this.latitudBusqueda = latitudBusqueda;
    }

    public Double getLatitudOrigen() {
        return latitudOrigen;
    }

    public void setLatitudOrigen(Double latitudOrigen) {
        this.latitudOrigen = latitudOrigen;
    }

    public Double getLongitudBusqueda() {
        return longitudBusqueda;
    }

    public void setLongitudBusqueda(Double longitudBusqueda) {
        this.longitudBusqueda = longitudBusqueda;
    }

    public Double getLongitudOrigen() {
        return longitudOrigen;
    }

    public void setLongitudOrigen(Double longitudOrigen) {
        this.longitudOrigen = longitudOrigen;
    }
}
