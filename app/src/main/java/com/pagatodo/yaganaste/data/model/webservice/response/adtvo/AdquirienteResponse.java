package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;
import java.util.ArrayList;

public class AdquirienteResponse implements Serializable{

    private ArrayList<AgentesRespose> Agentes;

    public AdquirienteResponse(){
        Agentes = new ArrayList<>();
    }
    public ArrayList<AgentesRespose> getAgentes() {
        return Agentes;
    }
    public void setAgentes(ArrayList<AgentesRespose> agentes) {
        Agentes = agentes;
    }
}
