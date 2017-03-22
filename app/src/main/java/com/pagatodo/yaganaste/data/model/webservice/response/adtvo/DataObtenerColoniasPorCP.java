package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class DataObtenerColoniasPorCP implements Serializable {

    private List<ColoniasResponse> ListaColonias;

    public DataObtenerColoniasPorCP() {
        ListaColonias = new ArrayList<ColoniasResponse>();
    }

    public List<ColoniasResponse> getListaColonias() {
        return ListaColonias;
    }

    public void setListaColonias(List<ColoniasResponse> listaColonias) {
        ListaColonias = listaColonias;
    }
}
