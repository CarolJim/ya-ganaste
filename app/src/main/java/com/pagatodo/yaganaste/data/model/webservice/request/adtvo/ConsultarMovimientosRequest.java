package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class ConsultarMovimientosRequest implements Serializable{

    private int Mes;
    private String Anio = "";
    private String IdMovimiento = "";
    private String Direccion = "";

    public ConsultarMovimientosRequest() {
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int mes) {
        Mes = mes;
    }

    public String getAnio() {
        return Anio;
    }

    public void setAnio(String anio) {
        Anio = anio;
    }

    public String getIdMovimiento() {
        return IdMovimiento;
    }

    public void setIdMovimiento(String idMovimiento) {
        IdMovimiento = idMovimiento;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }
}
