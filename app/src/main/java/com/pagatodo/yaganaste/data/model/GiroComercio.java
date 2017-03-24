package com.pagatodo.yaganaste.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jordan on 23/03/2017.
 */

public class GiroComercio implements Serializable{
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

    public String getIdSubgiroCRM() {
        return idSubgiroCRM;
    }

    public int getIdGiro() {
        return idGiro;
    }

    public String getIdGiroCRM() {
        return idGiroCRM;
    }

    public String getnGiro() {
        return nGiro;
    }

    public String getnSubgiro() {
        return nSubgiro;
    }
}
