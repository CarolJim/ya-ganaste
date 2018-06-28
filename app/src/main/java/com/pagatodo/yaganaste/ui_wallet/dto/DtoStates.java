package com.pagatodo.yaganaste.ui_wallet.dto;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DtoStates {

    private String ClaveEntidad, ID_Estado, Nombre, Prefijo;

    public DtoStates() {
    }

    public DtoStates(String ID_Estado, String nombre) {
        this.ID_Estado = ID_Estado;
        Nombre = nombre;
    }

    public String getClaveEntidad() {
        return ClaveEntidad;
    }

    public void setClaveEntidad(String claveEntidad) {
        ClaveEntidad = claveEntidad;
    }

    public String getID_Estado() {
        return ID_Estado;
    }

    public void setID_Estado(String ID_Estado) {
        this.ID_Estado = ID_Estado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPrefijo() {
        return Prefijo;
    }

    public void setPrefijo(String prefijo) {
        Prefijo = prefijo;
    }

    @Override
    public boolean equals(Object v) {
        boolean retVal = false;
        if (v instanceof DtoStates) {
            DtoStates ptr = (DtoStates) v;
            retVal = ptr.getID_Estado().equals(this.ID_Estado);
        }
        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.ID_Estado != null ? this.ID_Estado.hashCode() : 0);
        return hash;
    }
}
