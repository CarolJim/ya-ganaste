package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class LocalizarSucursalesResponse extends GenericResponse {

    private DataLocalizaSucursal Data;

    public LocalizarSucursalesResponse() {

        Data = new DataLocalizaSucursal();
    }

    public DataLocalizaSucursal getData() {
        return Data;
    }

    public void setData(DataLocalizaSucursal data) {
        Data = data;
    }
}
