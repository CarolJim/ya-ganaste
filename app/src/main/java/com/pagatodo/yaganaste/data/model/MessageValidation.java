package com.pagatodo.yaganaste.data.model;

/**
 * Created by flima on 28/03/2017.
 */

public class MessageValidation {

    private String phone;
    private String message;

    public MessageValidation() {
    }

    public MessageValidation(String phone, String message) {
        this.phone = phone;
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
