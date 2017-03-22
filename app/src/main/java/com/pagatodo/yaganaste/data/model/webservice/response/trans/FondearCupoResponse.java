package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class FondearCupoResponse extends GenericResponse {

    private DataFondeoCupo Data;

    public FondearCupoResponse() {
        Data = new DataFondeoCupo();
    }

    public DataFondeoCupo getData() {
        return Data;
    }

    public void setData(DataFondeoCupo data) {
        Data = data;
    }
}
