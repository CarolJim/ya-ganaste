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

    public ResumenMovimientosAdqResponse() {
        movimientos = new ArrayList<DataMovimientoAdq>();
        result = new DataResultAdq();
    }

    public ResumenMovimientosAdqResponse(List<DataMovimientoAdq> movimientos, DataResultAdq result,
                                         String saldoActual, String saldoInicial, String totalAbonos, String totalCargos) {
        this.movimientos = movimientos;
        this.result = result;
    }

    public List<DataMovimientoAdq> getMovimientos() {
        return movimientos;
    }

    public DataResultAdq getResult() {
        return result;
    }
}
