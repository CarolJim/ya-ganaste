package com.pagatodo.yaganaste.modules.management.request;

import java.io.Serializable;

public class NotificationPayRequest implements Serializable {

    private String plate;
    private String amount;
    private String concept;

    public NotificationPayRequest() {
    }

    public NotificationPayRequest(String plate, String amount, String concept) {
        this.plate = plate;
        this.amount = amount;
        this.concept = concept;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

}
