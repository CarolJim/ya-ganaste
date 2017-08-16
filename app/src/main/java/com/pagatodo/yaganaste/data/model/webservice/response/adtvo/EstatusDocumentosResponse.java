package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class EstatusDocumentosResponse implements Serializable {

    private int TipoDocumento;
    private String Estatus = "";
    private String Comentario = "";
    private int IdEstatus;
    private String Motivo = "";

    public EstatusDocumentosResponse(int tipoDocumento, String estatus, String comentario, int idEstatus, String motivo) {
        this.TipoDocumento = tipoDocumento;
        this.Estatus = estatus;
        this.Comentario = comentario;
        this.IdEstatus = idEstatus;
        this.Motivo = motivo;
    }

    public int getTipoDocumento() {
        return TipoDocumento;
    }

    public void setTipoDocumento(int tipoDocumento) {
        TipoDocumento = tipoDocumento;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String estatus) {
        Estatus = estatus;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }

    public int getIdEstatus() {
        return IdEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        IdEstatus = idEstatus;
    }

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String motivo) {
        Motivo = motivo;
    }
}
