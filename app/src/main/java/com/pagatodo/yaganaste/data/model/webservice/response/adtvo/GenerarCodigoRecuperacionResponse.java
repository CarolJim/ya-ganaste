package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.google.gson.annotations.SerializedName;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.io.Serializable;

/**
 * @author Juan Guerra
 */

public class GenerarCodigoRecuperacionResponse extends GenericResponse implements Serializable{

    @SerializedName("Data")
    private DataGenerarCodigoRecuperacion data;

    public DataGenerarCodigoRecuperacion getData() {
        return data;
    }
}
