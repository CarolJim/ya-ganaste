package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataEstatusUsuario implements Serializable {

    private boolean EsUsuario;
    private int IdUsuario;
    private boolean EsCliente;
    private boolean EsAgente;
    private boolean EsAgenteRechazado;
    private boolean ConCuenta;
    private String ImagenAvatarURL = "";

    public DataEstatusUsuario() {

    }

    public boolean isEsUsuario() {
        return EsUsuario;
    }

    public void setEsUsuario(boolean esUsuario) {
        EsUsuario = esUsuario;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
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

    public String getImagenAvatarURL() {
        return ImagenAvatarURL;
    }

    public void setImagenAvatarURL(String imagenAvatarURL) {
        ImagenAvatarURL = imagenAvatarURL;
    }
}
