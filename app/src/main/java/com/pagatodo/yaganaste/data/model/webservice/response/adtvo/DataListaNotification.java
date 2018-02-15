package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

/**
 * Created by Francisco Manzo on 21/03/2017.
 * Encargada de obtener los datos de la respuesta de registro de Token
 */

public class DataListaNotification implements Serializable {

    int CodigoRespuesta;
    int Id;
    int Identificador;
    String MensajeRespuesta;
    int TipoRespuesta;
    String TituloRespuesta;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdentificador() {
        return Identificador;
    }

    public void setIdentificador(int identificador) {
        Identificador = identificador;
    }

    public String getMensajeRespuesta() {
        return MensajeRespuesta;
    }

    public void setMensajeRespuesta(String mensajeRespuesta) {
        MensajeRespuesta = mensajeRespuesta;
    }

    public int getTipoRespuesta() {
        return TipoRespuesta;
    }

    public void setTipoRespuesta(int tipoRespuesta) {
        TipoRespuesta = tipoRespuesta;
    }

    public String getTituloRespuesta() {
        return TituloRespuesta;
    }

    public void setTituloRespuesta(String tituloRespuesta) {
        TituloRespuesta = tituloRespuesta;
    }

    public int getCodigoRespuesta() {
        return CodigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        CodigoRespuesta = codigoRespuesta;
    }
}
