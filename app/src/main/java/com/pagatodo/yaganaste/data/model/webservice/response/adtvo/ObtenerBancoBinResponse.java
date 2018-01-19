package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by Armando Sandoval on 17/01/2018.
 */

public class ObtenerBancoBinResponse extends GenericResponse {
    private DataObtenerDataBin Data;
    public ObtenerBancoBinResponse() {
        Data = new DataObtenerDataBin();
    }

    public DataObtenerDataBin getData() {
        return Data;
    }

    public void setData(DataObtenerDataBin data) {
        Data = data;
    }
}
