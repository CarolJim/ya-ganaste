package com.pagatodo.yaganaste.data.model.webservice.request.cupo;

import com.google.gson.annotations.SerializedName;
import com.pagatodo.yaganaste.data.model.webservice.request.Request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Horacio on 09/08/17.
 */

public class CrearCupoSolicitudRequest extends Request implements Serializable {

    // Datos Cuentanos Mas Fragment

    private int     IdEstadoCivil = 0;
    private String  NumeroHijos = "";
    private boolean TieneCreditoBancario = false;
    private boolean TieneCreditoAuto = false;
    private boolean TieneTarjetaCredito = false;
    private String  NumeroTarjeta = "";

    // Datos Referencia Familiar

    private List<CupoReferencia> Referencias;
    private Domicilio DomicilioPersonal;



    public CrearCupoSolicitudRequest() {
        Referencias = new ArrayList<CupoReferencia>();
    }

    public int getIdEstadoCivil() {
        return IdEstadoCivil;
    }

    public void setIdEstadoCivil(int idEstadoCivil) {
        IdEstadoCivil = idEstadoCivil;
    }

    public String getNumeroHijos() {
        return NumeroHijos;
    }

    public void setNumeroHijos(String numeroHijos) {
        NumeroHijos = numeroHijos;
    }

    public boolean isTieneCreditoBancario() {
        return TieneCreditoBancario;
    }

    public void setTieneCreditoBancario(boolean tieneCreditoBancario) {
        TieneCreditoBancario = tieneCreditoBancario;
    }

    public boolean isTieneCreditoAuto() {
        return TieneCreditoAuto;
    }

    public void setTieneCreditoAuto(boolean tieneCreditoAuto) {
        TieneCreditoAuto = tieneCreditoAuto;
    }

    public boolean isTieneTarjetaCredito() {
        return TieneTarjetaCredito;
    }

    public void setTieneTarjetaCredito(boolean tieneTarjetaCredito) {
        TieneTarjetaCredito = tieneTarjetaCredito;
    }

    public String getNumeroTarjeta() {
        return NumeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        NumeroTarjeta = numeroTarjeta;
    }

    public List<CupoReferencia> getReferencias() {
        return Referencias;
    }

    public void setReferencias(List<CupoReferencia> referencias) {
        Referencias = referencias;
    }

    public Domicilio getDomicilioPersonal() {
        return DomicilioPersonal;
    }

    public void setDomicilioPersonal(Domicilio domicilioPersonal) {
        DomicilioPersonal = domicilioPersonal;
    }
}
