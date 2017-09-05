package com.pagatodo.yaganaste.data.model.webservice.response.cupo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tato on 21/08/17.
 */

public class DataEstadoDocumentos implements Serializable{

    private String Comentario;
    private String Estatus;
    private int    IdEstatus;
    private String Motivo;
    private int    TipoDocumento;
    private String Titulo;


    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String estatus) {
        Estatus = estatus;
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

    public int getTipoDocumento() {
        return TipoDocumento;
    }

    public void setTipoDocumento(int tipoDocumento) {
        TipoDocumento = tipoDocumento;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }
}
