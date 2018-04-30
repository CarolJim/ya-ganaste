package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asandovals on 26/04/2018.
 */

public class ConsultaDatosPersonaStarbucks extends GenericResponse {

    private DatosPersonaRegistroStarbucks Data;

    public ConsultaDatosPersonaStarbucks() {
        Data = new DatosPersonaRegistroStarbucks();
    }

    public DatosPersonaRegistroStarbucks getData() {
        return Data;
    }

    public void setData(DatosPersonaRegistroStarbucks data) {
        Data = data;
    }
}
