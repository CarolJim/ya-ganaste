package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 13/09/2017.
 */

public class ConsultarFavoritosResponse extends GenericResponse {

    private List<Favoritos> Data;

    private ConsultarFavoritosResponse() {
        Data = new ArrayList<Favoritos>();
    }

    public List<Favoritos> getData() {
        return Data;
    }

    public void setData(List<Favoritos> data) {
        Data = data;
    }
}
