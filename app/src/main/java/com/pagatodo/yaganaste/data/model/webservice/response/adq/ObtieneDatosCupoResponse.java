package com.pagatodo.yaganaste.data.model.webservice.response.adq;

import com.pagatodo.yaganaste.data.model.DatosCupo;

/**
 * Created by Jordan on 23/08/2017.
 */

public class ObtieneDatosCupoResponse extends DatosCupo {
    private DataResultAdq result;

    public DataResultAdq getResult() {
        return result;
    }

    public void setResult(DataResultAdq result) {
        this.result = result;
    }
}
