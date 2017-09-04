package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jordan on 25/08/2017.
 */

public class EnviarTicketRequest implements Serializable {
    @SerializedName("CorreoElectronico")
    private String correoElectronico;

    @SerializedName("IdMovimiento")
    private String idMovimiento;

    public EnviarTicketRequest(String mail, String id) {
        this.correoElectronico = mail;
        this.idMovimiento = id;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(String idMovimiento) {
        this.idMovimiento = idMovimiento;
    }
}
