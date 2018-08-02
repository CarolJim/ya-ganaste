package com.pagatodo.yaganaste.ui_wallet.dto;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DtoStates {

    public String ClaveEntidad, ID_Estado, Nombre, Prefijo;

    public DtoStates() {
    }

    public DtoStates(String ClaveEntidad, String nombre,String Prefijo) {
        this.ClaveEntidad = ClaveEntidad;
        Nombre = nombre;
        this.Prefijo = Prefijo;
    }

    @Override
    public boolean equals(Object v) {
        boolean retVal = false;
        if (v instanceof DtoStates) {
            DtoStates ptr = (DtoStates) v;
            retVal = ptr.ClaveEntidad.equals(this.ClaveEntidad);
        }
        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.ClaveEntidad != null ? this.ClaveEntidad.hashCode() : 0);
        return hash;
    }
}
