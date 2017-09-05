package com.pagatodo.yaganaste.data.model.webservice.request.cupo;

import com.pagatodo.yaganaste.data.model.Referencias;
import com.pagatodo.yaganaste.data.model.webservice.request.Request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ActualizarReferenciasCupoRequest extends Request implements Serializable {

    private List<CupoReferencia>  ListaReferencias;

    public ActualizarReferenciasCupoRequest() {
        ListaReferencias = new ArrayList<CupoReferencia>();
    }

    public List<CupoReferencia> getListaReferencias() {
        return ListaReferencias;
    }

    public void setListaReferencias(List<CupoReferencia> listaReferencias) {
        ListaReferencias = listaReferencias;
    }
}
