package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class AsignarCuentaDisponibleResponse extends GenericResponse {

    private DataCuentaDisponible Data;

    public AsignarCuentaDisponibleResponse() {

        Data = new DataCuentaDisponible();
    }

    public DataCuentaDisponible getData() {
        return Data;
    }

    public void setData(DataCuentaDisponible data) {
        Data = data;
    }
}
