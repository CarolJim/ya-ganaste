package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class DataConsultarMovimientos implements Serializable {

    private List<MovimientosResponse> ListaMovimientos;

    public DataConsultarMovimientos() {
        ListaMovimientos = new ArrayList<MovimientosResponse>();
    }

    public List<MovimientosResponse> getListaMovimientos() {
        return ListaMovimientos;
    }

    public void setListaMovimientos(List<MovimientosResponse> listaMovimientos) {
        ListaMovimientos = listaMovimientos;
    }
}
