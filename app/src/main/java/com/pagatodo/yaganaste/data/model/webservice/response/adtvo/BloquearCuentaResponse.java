package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Francisco Manzo on 21/08/2017.
 */

public class BloquearCuentaResponse extends GenericResponse implements Serializable {

    BloquearCuentaDataResponse Data;

    public BloquearCuentaDataResponse getData() {
        return Data;
    }
}
