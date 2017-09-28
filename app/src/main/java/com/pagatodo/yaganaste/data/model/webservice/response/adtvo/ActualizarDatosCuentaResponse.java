package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francisco Manzo on 21/08/2017.
 */

public class ActualizarDatosCuentaResponse extends GenericResponse implements Serializable {
    private List<DatosCuentaResponse> Data;

    public List<DatosCuentaResponse> getData() {
        return Data;
    }
}
