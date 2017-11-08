package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by icruz on 07/11/2017.
 */

public class ObtenerCobrosMensualesResponse extends GenericResponse {

    private List<CobrosMensualesResponse> Data;
    //private List<MontosResponse> DataMontos;

    public ObtenerCobrosMensualesResponse(){

        this.Data = new ArrayList<>();
        //this.DataMontos = new ArrayList<>();
    }

    public List<CobrosMensualesResponse> getData() {
        return Data;
    }

    public void setData(List<CobrosMensualesResponse> data) {
        Data = data;
    }

    /*public List<MontosResponse> getDataMontos() {
        return DataMontos;
    }

    public void setDataMontos(List<MontosResponse> dataMontos) {
        DataMontos = dataMontos;
    }*/
}
