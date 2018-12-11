package com.pagatodo.yaganaste.data.model;

import java.io.Serializable;

public class QRs implements Serializable {

    String alias,plate;

    public QRs(String alias, String plate) {
        this.alias = alias;
        this.plate = plate;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
