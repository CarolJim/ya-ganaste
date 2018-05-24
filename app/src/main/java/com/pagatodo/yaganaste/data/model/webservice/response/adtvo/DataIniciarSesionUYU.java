package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

public class DataIniciarSesionUYU implements Serializable{

    private AdquirienteResponse Adquirente;
    private ClienteResponse Cliente;
    private ControlResponse Control;
    private EmisorResponse Emisor;
    private UsuarioResponse Usuario;

    public AdquirienteResponse getAdquirente() {
        return Adquirente;
    }

    public void setAdquirente(AdquirienteResponse adquirente) {
        Adquirente = adquirente;
    }

    public ClienteResponse getCliente() {
        return Cliente;
    }

    public void setCliente(ClienteResponse cliente) {
        Cliente = cliente;
    }

    public ControlResponse getControl() {
        return Control;
    }

    public void setControl(ControlResponse control) {
        Control = control;
    }

    public EmisorResponse getEmisor() {
        return Emisor;
    }

    public void setEmisor(EmisorResponse emisor) {
        Emisor = emisor;
    }

    public UsuarioResponse getUsuario() {
        return Usuario;
    }

    public void setUsuario(UsuarioResponse usuario) {
        Usuario = usuario;
    }
}
