package com.pagatodo.yaganaste.modules.data.webservices;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RenapoDataCurpRequest implements Serializable {
    @SerializedName("curp")
    private String curp;

    public RenapoDataCurpRequest(){}

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }
}
