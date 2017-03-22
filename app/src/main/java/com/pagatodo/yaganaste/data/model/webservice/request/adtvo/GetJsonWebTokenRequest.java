package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class GetJsonWebTokenRequest implements Serializable{

    private int IdTipoTransaccion;
    private String Ticket = "";
    private String Referencia = "";
    private Double Monto;
    private int IdComercioAfectado;


    public GetJsonWebTokenRequest() {
    }

    public int getIdTipoTransaccion() {
        return IdTipoTransaccion;
    }

    public void setIdTipoTransaccion(int idTipoTransaccion) {
        IdTipoTransaccion = idTipoTransaccion;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    public Double getMonto() {
        return Monto;
    }

    public void setMonto(Double monto) {
        Monto = monto;
    }

    public int getIdComercioAfectado() {
        return IdComercioAfectado;
    }

    public void setIdComercioAfectado(int idComercioAfectado) {
        IdComercioAfectado = idComercioAfectado;
    }
}
