package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ObtenerColoniasPorCPResponse extends GenericResponse {

    private DataActualizarAvatar Data;

    public ObtenerColoniasPorCPResponse() {
        Data = new DataActualizarAvatar();
    }

    public DataActualizarAvatar getData() {
        return Data;
    }

    public void setData(DataActualizarAvatar data) {
        Data = data;
    }
}
