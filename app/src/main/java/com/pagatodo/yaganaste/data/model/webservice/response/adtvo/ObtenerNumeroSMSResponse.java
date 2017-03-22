package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ObtenerNumeroSMSResponse extends GenericResponse {

    private DataObtenerNumeroSMS Data;

    public ObtenerNumeroSMSResponse() {
        Data = new DataObtenerNumeroSMS();
    }

    public DataObtenerNumeroSMS getData() {
        return Data;
    }

    public void setData(DataObtenerNumeroSMS data) {
        Data = data;
    }
}
