package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.Giros;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;

import java.util.ArrayList;
import java.util.List;

public class GetoperadoresResponse extends GenericResponse {


    private List<Operadores> Data;

    public  GetoperadoresResponse(){
        Data = new ArrayList<Operadores>();
    }

    public List<Operadores> getData() {
        return Data;
    }

    public void setData(List<Operadores> data) {
        Data = data;
    }
}
