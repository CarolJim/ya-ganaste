package com.pagatodo.yaganaste.data.model.webservice.response.adq;

import java.io.Serializable;

/**
 * Created by icruz on 08/03/2018.
 */

public class ReembolsoResponse implements Serializable {
    private DataResultAdq result;

    public DataResultAdq getResult() {
        return result;
    }

    public void setResult(DataResultAdq result) {
        this.result = result;
    }
}
