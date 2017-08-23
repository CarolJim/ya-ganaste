package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.io.Serializable;

/**
 * Created by Francisco Manzo on 21/08/2017.
 */

public class EstatusCuentaDataResponse extends GenericResponse implements Serializable {

    String StatusId = "";

    public String getStatusId() {
        return StatusId;
    }

    public EstatusCuentaDataResponse() {
    }

}
