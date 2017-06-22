package com.pagatodo.yaganaste.data.model.webservice.response.adq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class ResumenMovimientosAdqResponse implements Serializable {

    private List<DataMovimientoAdq> movimientos;
    private DataResultAdq result;
    private String saldoActual = "";
    private String saldoInicial = "";
    private String totalAbonos = "";
    private String totalCargos = "";

    public ResumenMovimientosAdqResponse() {
        movimientos = new ArrayList<DataMovimientoAdq>();
        result = new DataResultAdq();
    }

    public ResumenMovimientosAdqResponse(List<DataMovimientoAdq> movimientos, DataResultAdq result,
                                         String saldoActual, String saldoInicial, String totalAbonos, String totalCargos) {
        this.movimientos = movimientos;
        this.result = result;
        this.saldoActual = saldoActual;
        this.saldoInicial = saldoInicial;
        this.totalAbonos = totalAbonos;
        this.totalCargos = totalCargos;
    }

    public List<DataMovimientoAdq> getMovimientos() {
        return movimientos;
    }

    public DataResultAdq getResult() {
        return result;
    }

    public String getSaldoActual() {
        return saldoActual;
    }

    public String getSaldoInicial() {
        return saldoInicial;
    }

    public String getTotalAbonos() {
        return totalAbonos;
    }

    public String getTotalCargos() {
        return totalCargos;
    }
}
