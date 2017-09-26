package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Francisco Manzo on 15/09/2017.
 */

public class FavoritosDatosResponse extends GenericResponse {

//    private List<AddFavoritosResponse> Data;
//
////    public FavoritosDatosResponse(){
////        Data = new AddFavoritosResponse();
////    }
//
//    public List<AddFavoritosResponse> getData() {
//        return Data;
//    }

    private AddFavoritosResponse Data;

    public FavoritosDatosResponse() {
        Data = new AddFavoritosResponse();
    }


    public AddFavoritosResponse getData() {
        return Data;
    }
}