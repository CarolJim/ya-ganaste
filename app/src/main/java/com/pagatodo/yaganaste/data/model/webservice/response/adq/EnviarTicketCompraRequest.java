package com.pagatodo.yaganaste.data.model.webservice.response.adq;

import com.pagatodo.yaganaste.data.model.webservice.request.adq.ImplicitData;

/**
 * Created by jvazquez on 28/10/2016.
 */

public class EnviarTicketCompraRequest {
    public String idTransaction = "";
    public String email = "";
    public ImplicitData implicitData;
    public String name = "";

    public EnviarTicketCompraRequest(){
        implicitData = new ImplicitData();
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ImplicitData getImplicitData() {
        return implicitData;
    }

    public void setImplicitData(ImplicitData implicitData) {
        this.implicitData = implicitData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
