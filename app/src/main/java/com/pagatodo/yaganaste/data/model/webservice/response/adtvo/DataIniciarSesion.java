package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;

/**
 * Created by flima on 21/03/2017.
 */

public class DataIniciarSesion implements Serializable {

    private boolean EsUsuario;
    private boolean EsCliente;
    private boolean ConCuenta;

    private int IdEstatus;
    private boolean RequiereActivacionSMS=false;
    private String Semilla = "";
    private UsuarioClienteResponse Usuario;

    public DataIniciarSesion() {

        Usuario = new UsuarioClienteResponse();
    }

    public int getIdEstatus() {
        return IdEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        IdEstatus = idEstatus;
    }

    public boolean isEsUsuario() {
        return EsUsuario;
    }

    public void setEsUsuario(boolean esUsuario) {
        EsUsuario = esUsuario;
    }

    public boolean isEsCliente() {
        return EsCliente;
    }

    public void setEsCliente(boolean esCliente) {
        EsCliente = esCliente;
    }

    public boolean isConCuenta() {
        return ConCuenta;
    }

    public void setConCuenta(boolean conCuenta) {
        ConCuenta = conCuenta;
    }

    public boolean isRequiereActivacionSMS() {
        return RequiereActivacionSMS;
    }

    public void setRequiereActivacionSMS(boolean requiereActivacionSMS) {
        RequiereActivacionSMS = requiereActivacionSMS;
    }

    public String getSemilla() {
        return Semilla;
    }

    public void setSemilla(String semilla) {
        Semilla = semilla;
    }

    public UsuarioClienteResponse getUsuario() {
        return Usuario;
    }

    public void setUsuario(UsuarioClienteResponse usuario) {
        Usuario = usuario;
    }
}