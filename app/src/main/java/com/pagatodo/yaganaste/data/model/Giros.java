package com.pagatodo.yaganaste.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Omar on 16/10/2017.
 */

public class Giros implements Serializable {

    private int IdGiro = 0;
    private String Giro = "";
    private List<SubGiro> ListaSubgiros;

    public int getIdGiro() {
        return IdGiro;
    }

    public void setIdGiro(int idGiro) {
        IdGiro = idGiro;
    }

    public String getGiro() {
        return Giro;
    }

    public void setGiro(String giro) {
        Giro = giro;
    }

    public List<SubGiro> getListaSubgiros() {
        return ListaSubgiros;
    }

    public void setListaSubgiros(List<SubGiro> listaSubgiros) {
        ListaSubgiros = listaSubgiros;
    }
}
