package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataConsultarAsignacion implements Serializable {

    private boolean ConCliente;
    private String Alias = "";
    private String NombreUsuario = "";
    private int IdCuenta;


    public DataConsultarAsignacion() {
    }

    public boolean isConCliente() {
        return ConCliente;
    }

    public void setConCliente(boolean conCliente) {
        ConCliente = conCliente;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public int getIdCuenta() {
        return IdCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        IdCuenta = idCuenta;
    }
}
