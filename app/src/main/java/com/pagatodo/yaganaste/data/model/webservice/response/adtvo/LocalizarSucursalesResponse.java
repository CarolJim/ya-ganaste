package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class LocalizarSucursalesResponse extends GenericResponse {

    private List<DataLocalizaSucursal> Data;

    public LocalizarSucursalesResponse() {

        this.Data = new ArrayList<DataLocalizaSucursal>();
    }

    public List<DataLocalizaSucursal> getData() {
        return Data;
    }

    public void setData(List<DataLocalizaSucursal> data) {
        Data = data;
    }
}
