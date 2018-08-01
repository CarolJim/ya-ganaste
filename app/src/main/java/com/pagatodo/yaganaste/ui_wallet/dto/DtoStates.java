package com.pagatodo.yaganaste.ui_wallet.dto;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DtoStates {

    public String ClaveEntidad, ID_Estado, Nombre, Prefijo;

    public DtoStates() {
    }

    public DtoStates(String ID_Estado, String nombre,String Prefijo) {
        this.ID_Estado = ID_Estado;
        Nombre = nombre;
        this.Prefijo = Prefijo;
    }

    @Override
    public boolean equals(Object v) {
        boolean retVal = false;
        if (v instanceof DtoStates) {
            DtoStates ptr = (DtoStates) v;
            retVal = ptr.ID_Estado.equals(this.ID_Estado);
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
