package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Juan Guerra on 21/08/2017.
 */

public class DataGenerarCodigoRecuperacion implements Serializable{

    @SerializedName("CodigoRecuperacion")
    private String codigoRecuperacion;

    public String getCodigoRecuperacion() {
        return codigoRecuperacion;
    }
}
