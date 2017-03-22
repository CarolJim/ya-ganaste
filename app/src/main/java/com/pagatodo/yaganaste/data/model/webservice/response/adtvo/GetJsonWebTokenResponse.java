package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class GetJsonWebTokenResponse extends GenericResponse {

    private DataJsonWebToken Data;

    public GetJsonWebTokenResponse() {
        Data = new DataJsonWebToken();
    }

    public DataJsonWebToken getData() {
        return Data;
    }

    public void setData(DataJsonWebToken data) {
        Data = data;
    }
}
