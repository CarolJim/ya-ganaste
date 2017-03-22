package com.pagatodo.yaganaste.data.model.webservice.response.adq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class ResumenMovimientosAdqResponse implements Serializable{

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


}
