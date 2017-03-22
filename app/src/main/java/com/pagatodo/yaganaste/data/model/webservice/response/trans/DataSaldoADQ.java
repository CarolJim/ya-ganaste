package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataSaldoADQ implements Serializable {

    private Double SumaComision;
    private Double SumaDeposito;
    private Double SumaIVAComision;
    private Double SumaImporte;


    public DataSaldoADQ() {
    }


    public Double getSumaComision() {
        return SumaComision;
    }

    public void setSumaComision(Double sumaComision) {
        SumaComision = sumaComision;
    }

    public Double getSumaDeposito() {
        return SumaDeposito;
    }

    public void setSumaDeposito(Double sumaDeposito) {
        SumaDeposito = sumaDeposito;
    }

    public Double getSumaIVAComision() {
        return SumaIVAComision;
    }

    public void setSumaIVAComision(Double sumaIVAComision) {
        SumaIVAComision = sumaIVAComision;
    }

    public Double getSumaImporte() {
        return SumaImporte;
    }

    public void setSumaImporte(Double sumaImporte) {
        SumaImporte = sumaImporte;
    }
}
