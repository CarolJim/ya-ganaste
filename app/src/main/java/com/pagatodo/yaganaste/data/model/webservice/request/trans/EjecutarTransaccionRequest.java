package com.pagatodo.yaganaste.data.model.webservice.request.trans;

import android.util.Log;

import com.pagatodo.yaganaste.BuildConfig;

import java.io.Serializable;

import static com.pagatodo.yaganaste.utils.StringUtils.createTicket;

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
        if (BuildConfig.DEBUG)
            Log.d(getClass().getSimpleName(), "TICKET C1");
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
        if (BuildConfig.DEBUG)
            Log.d(getClass().getSimpleName(), "TICKET C2");
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

}
