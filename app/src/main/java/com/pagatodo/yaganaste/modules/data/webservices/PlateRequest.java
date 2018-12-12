package com.pagatodo.yaganaste.modules.data.webservices;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlateRequest implements Serializable {

    @SerializedName("plate")
    public String plate;

    public PlateRequest(String plate) {
        this.plate = plate;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
