package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataLocalizaSucursal implements Serializable {

    private String Direccion1 = "";
    private String Direccion2 = "";
    private String Horario = "";
    private Float Latitud;
    private Float Longitud;
    private String Nombre = "";
    private String NumTelefonico = "";


    public DataLocalizaSucursal() {
    }

    public String getDireccion1() {
        return Direccion1;
    }

    public void setDireccion1(String direccion1) {
        Direccion1 = direccion1;
    }

    public String getDireccion2() {
        return Direccion2;
    }

    public void setDireccion2(String direccion2) {
        Direccion2 = direccion2;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String horario) {
        Horario = horario;
    }

    public Float getLatitud() {
        return Latitud;
    }

    public void setLatitud(Float latitud) {
        Latitud = latitud;
    }

    public Float getLongitud() {
        return Longitud;
    }

    public void setLongitud(Float longitud) {
        Longitud = longitud;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getNumTelefonico() {
        return NumTelefonico;
    }

    public void setNumTelefonico(String numTelefonico) {
        NumTelefonico = numTelefonico;
    }
}
