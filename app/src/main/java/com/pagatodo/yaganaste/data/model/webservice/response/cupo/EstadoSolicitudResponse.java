package com.pagatodo.yaganaste.data.model.webservice.response.cupo;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerCatalogos;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by Tato on 17/08/17.
 */

public class EstadoSolicitudResponse extends GenericResponse {


    private DataEstadoSolicitud Data;

    public EstadoSolicitudResponse() {
        Data = new DataEstadoSolicitud();
    }

    public DataEstadoSolicitud getData() {
        return Data;
    }

    public void setData(DataEstadoSolicitud data) {
        Data = data;
    }


}
