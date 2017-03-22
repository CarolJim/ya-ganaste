package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ConsultarSaldoADQResponse extends GenericResponse {

    private DataSaldoADQ Data;
    public ConsultarSaldoADQResponse() {
        Data = new DataSaldoADQ();
    }

    public DataSaldoADQ getData() {
        return Data;
    }

    public void setData(DataSaldoADQ data) {
        Data = data;
    }
}
