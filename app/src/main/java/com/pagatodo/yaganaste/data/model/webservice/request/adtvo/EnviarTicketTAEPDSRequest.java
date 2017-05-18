package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jordan on 18/05/2017.
 */

public class EnviarTicketTAEPDSRequest  implements Serializable{
    @SerializedName("Email")
    private String email;
    @SerializedName("IdTransaction")
    private String idTransaction;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }
}
