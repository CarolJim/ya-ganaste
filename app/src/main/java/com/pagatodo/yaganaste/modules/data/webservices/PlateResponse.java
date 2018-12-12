package com.pagatodo.yaganaste.modules.data.webservices;

import java.io.Serializable;
import java.util.ArrayList;

public class PlateResponse implements Serializable {

    String message;
    boolean success;
    ArrayList<String> errorList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(ArrayList<String> errorList) {
        this.errorList = errorList;
    }
}
