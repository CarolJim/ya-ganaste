package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by Francisco Manzo on 15/09/2017.
 */

public class FavoritosNewFotoDatosResponse extends GenericResponse {

    private AddFavoritosResponse Data;

    public FavoritosNewFotoDatosResponse() {
        Data = new AddFavoritosResponse();
    }


    public AddFavoritosResponse getData() {
        return Data;
    }
}