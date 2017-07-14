package com.pagatodo.yaganaste.data.model.webservice.response.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class TransaccionesPDSResponse implements Serializable {

    private String fecha = "";
    private DataResultAdq result;
    private int idTransaccion;
    private int noAutorizacion;

    public TransaccionesPDSResponse() {
        result = new DataResultAdq();
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public DataResultAdq getResult() {
        return result;
    }

    public void setResult(DataResultAdq result) {
        this.result = result;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public int getNoAutorizacion() {
        return noAutorizacion;
    }

    public void setNoAutorizacion(int noAutorizacion) {
        this.noAutorizacion = noAutorizacion;
    }
}
