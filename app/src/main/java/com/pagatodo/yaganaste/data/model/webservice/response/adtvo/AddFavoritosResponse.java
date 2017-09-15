package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.io.Serializable;

/**
 * Created by Francisco Manzo on 21/08/2017.
 */

public class AddFavoritosResponse extends GenericResponse implements Serializable {

    EstatusCuentaDataResponse Data;

    public EstatusCuentaDataResponse getData() {
        return Data;
    }
}
