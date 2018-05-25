package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;

public class DataIniciarSesion implements Serializable {

    private boolean EsUsuario;
    private boolean EsCliente;
    private boolean EsAgente = false;//EsAdquirente
    private boolean EsAgenteRechazado;
    private boolean ConCuenta;

    private int IdEstatus;
    private int EstatusDocumentacion; // TODO validar, ya que no se encuentra en la documentacion
    private int EstatusAgente = CRM_DOCTO_APROBADO;//CRM_DOCTO_APROBADO; // TODO validar, ya que no se encuentra en la documentacion
    private boolean RequiereActivacionSMS=false;
    private String Semilla = "";
    private UsuarioClienteResponse Usuario;

    public DataIniciarSesion() {

        Usuario = new UsuarioClienteResponse();
    }

    public DataIniciarSesion(boolean esUsuario, boolean esCliente, boolean esAgente, boolean conCuenta, UsuarioClienteResponse dataUser,int idestatus) {
        EsUsuario = esUsuario;
        EsCliente = esCliente;
        EsAgente = esAgente;
        ConCuenta = conCuenta;
        Usuario = dataUser;
        IdEstatus=idestatus;
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

    public boolean isEsAgente() {
        return EsAgente;
    }

    public void setEsAgente(boolean esAgente) {
        EsAgente = esAgente;
    }

    public boolean isEsAgenteRechazado() {
        return EsAgenteRechazado;
    }

    public void setEsAgenteRechazado(boolean esAgenteRechazado) {
        EsAgenteRechazado = esAgenteRechazado;
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

    public int getEstatusDocumentacion() {
        return EstatusDocumentacion;
    }

    public void setEstatusDocumentacion(int estatusDocumentacion) {
        EstatusDocumentacion = estatusDocumentacion;
    }

    public int getEstatusAgente() {
        return EstatusAgente;
    }

    public void setEstatusAgente(int estatusAgente) {
        EstatusAgente = estatusAgente;
    }
}
