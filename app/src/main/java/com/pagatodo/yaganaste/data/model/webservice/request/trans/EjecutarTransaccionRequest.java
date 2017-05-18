package com.pagatodo.yaganaste.data.model.webservice.request.trans;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by flima on 21/03/2017.
 */

public class EjecutarTransaccionRequest implements Serializable {

    private int IdTipoTransaccion;
    private String Ticket;
    private String Referencia;
    private Double Monto;
    private int IdComercioAfectado;
    private String NombreBeneficiario;
    private String Concepto;
    private String ReferenciaNumerica;


    public EjecutarTransaccionRequest() {
        super();
    }

    public EjecutarTransaccionRequest(int idTipoTransaccion, String referencia, Double monto, int idComercioAfectado) {
        super();
        this.IdTipoTransaccion = idTipoTransaccion;
        this.Ticket = createTicket();
        this.Referencia = referencia;
        this.Monto = monto;
        this.IdComercioAfectado = idComercioAfectado;
        this.NombreBeneficiario = "";
        this.Concepto = "";
        this.ReferenciaNumerica = "";
    }

    public EjecutarTransaccionRequest(int idTipoTransaccion, String referencia, Double monto,
                                      int idComercioAfectado, String nombreBeneficiario, String concepto, String referenciaNumerica) {
        super();
        this.IdTipoTransaccion = idTipoTransaccion;
        this.Ticket = createTicket();
        this.Referencia = referencia;
        this.Monto = monto;
        this.IdComercioAfectado = idComercioAfectado;
        this.NombreBeneficiario = nombreBeneficiario;
        this.Concepto = concepto;
        this.ReferenciaNumerica = referenciaNumerica;

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

    public String getNombreBeneficiario() {
        return NombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        NombreBeneficiario = nombreBeneficiario;
    }

    public String getConcepto() {
        return Concepto;
    }

    public void setConcepto(String concepto) {
        Concepto = concepto;
    }

    public String getReferenciaNumerica() {
        return ReferenciaNumerica;
    }

    public void setReferenciaNumerica(String referenciaNumerica) {
        ReferenciaNumerica = referenciaNumerica;
    }

    public String createTicket() {
        /*Calendar c = Calendar.getInstance();
        final int startYear = c.get(Calendar.YEAR);
        final int startMonth = c.get(Calendar.MONTH);
        final int startDay = c.get(Calendar.DAY_OF_MONTH);*/
        return String.format("%1$tY%1$tM%1$tS%2$02d", Calendar.getInstance(), (int) (Math.random() * 90));
        //startYear + startMonth + startDay +  + "";
    }
}
