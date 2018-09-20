package com.pagatodo.yaganaste.ui_wallet.dto;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DtoStates {

    public String clave, ID_Estado, Nombre, IdEstadoFirebase, ID_Estatus, ID_EntidadNacimiento;

    public DtoStates() {
    }

    public DtoStates(String ID_EntidadNacimiento, String nombre, String clave) {
        this.ID_EntidadNacimiento = ID_EntidadNacimiento;
        Nombre = nombre;
        this.clave = clave;
    }

    @Override
    public boolean equals(Object v) {
        boolean retVal = false;
        if (v instanceof DtoStates) {
            DtoStates ptr = (DtoStates) v;
            retVal = ptr.clave.equals(this.clave);
        }
        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.clave != null ? this.clave.hashCode() : 0);
        return hash;
    }
}
