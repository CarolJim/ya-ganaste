package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class EnviarTicketCompraRequest extends AdqRequest implements Serializable{

    private String email;
    private ImplicitData implicitData;
    private String name = "";

    public EnviarTicketCompraRequest() {
        implicitData = new ImplicitData();
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
