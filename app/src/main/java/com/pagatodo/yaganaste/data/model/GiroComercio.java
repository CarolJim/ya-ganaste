package com.pagatodo.yaganaste.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jordan on 23/03/2017.
 */

public class GiroComercio implements Serializable {
    @SerializedName("ID_SubGiro")
    public int idSubgiro;
    @SerializedName("ID_SubGiroCRM")
    public String idSubgiroCRM;
    @SerializedName("ID_Giro")
    public int idGiro;
    @SerializedName("ID_GiroCRM")
    public String idGiroCRM;
    @SerializedName("Giro")
    public String nGiro;
    @SerializedName("SubGiro")
    public String nSubgiro;

    public GiroComercio() {
        super();
    }

    public int getIdSubgiro() {
        return idSubgiro;
    }

    public void setIdSubgiro(int idSubgiro) {
        this.idSubgiro = idSubgiro;
    }

    public String getIdSubgiroCRM() {
        return idSubgiroCRM;
    }

    public void setIdSubgiroCRM(String idSubgiroCRM) {
        this.idSubgiroCRM = idSubgiroCRM;
    }

    public int getIdGiro() {
        return idGiro;
    }

    public void setIdGiro(int idGiro) {
        this.idGiro = idGiro;
    }

    public String getIdGiroCRM() {
        return idGiroCRM;
    }

    public void setIdGiroCRM(String idGiroCRM) {
        this.idGiroCRM = idGiroCRM;
    }

    public String getnGiro() {
        return nGiro;
    }

    public void setnGiro(String nGiro) {
        this.nGiro = nGiro;
    }

    public String getnSubgiro() {
        return nSubgiro;
    }

    public void setnSubgiro(String nSubgiro) {
        this.nSubgiro = nSubgiro;
    }
}
