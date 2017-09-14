package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 13/09/2017.
 */

public class ConsultarFavoritosResponse extends GenericResponse {

    private List<DataFavoritos> Data;

    private ConsultarFavoritosResponse() {
        Data = new ArrayList<DataFavoritos>();
    }

    public List<DataFavoritos> getData() {
        return Data;
    }

    public void setData(List<DataFavoritos> data) {
        Data = data;
    }
}
