package com.pagatodo.yaganaste.data.model.webservice.response.starbucks;

/**
 * Created by asandovals on 15/05/2018.
 */

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class CustomResult implements Serializable {
    private static final long serialVersionUID = -2545433658186960395L;
    @SerializedName("codigoRespuesta")
    public int responseCode = 100;
    @SerializedName("mensaje")
    public String mesagge = "";

    public CustomResult() {
    }
}
