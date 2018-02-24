package com.pagatodo.yaganaste.data.model.webservice.response.adq;

import java.io.Serializable;

/**
 * Created by Omar on 22/02/2018.
 */

public class TiposReembolsoResponse implements Serializable {

    private float Comision;
    private boolean Configurado, Visible;
    private int ID_TipoReembolso;
    private String TipoReembolso;

    public TiposReembolsoResponse(float comision, boolean configurado, boolean visible, int ID_TipoReembolso, String tipoReembolso) {
        Comision = comision;
        Configurado = configurado;
        Visible = visible;
        this.ID_TipoReembolso = ID_TipoReembolso;
        TipoReembolso = tipoReembolso;
    }

    public float getComision() {
        return Comision;
    }

    public void setComision(float comision) {
        Comision = comision;
    }

    public boolean isConfigurado() {
        return Configurado;
    }

    public void setConfigurado(boolean configurado) {
        Configurado = configurado;
    }

    public boolean isVisible() {
        return Visible;
    }

    public void setVisible(boolean visible) {
        Visible = visible;
    }

    public int getID_TipoReembolso() {
        return ID_TipoReembolso;
    }

    public void setID_TipoReembolso(int ID_TipoReembolso) {
        this.ID_TipoReembolso = ID_TipoReembolso;
    }

    public String getTipoReembolso() {
        return TipoReembolso;
    }

    public void setTipoReembolso(String tipoReembolso) {
        TipoReembolso = tipoReembolso;
    }
}
