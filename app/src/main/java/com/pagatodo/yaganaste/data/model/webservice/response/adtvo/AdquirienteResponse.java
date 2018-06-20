package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;
import java.util.ArrayList;

import com.pagatodo.yaganaste.data.room_db.entities.Agentes;

public class AdquirienteResponse implements Serializable {

    private ArrayList<Agentes> Agentes;

    public AdquirienteResponse() {
        Agentes = new ArrayList<>();
    }

    public ArrayList<Agentes> getAgentes() {
        return Agentes;
    }

    public void setAgentes(ArrayList<Agentes> agentes) {
        Agentes = agentes;
    }
}
