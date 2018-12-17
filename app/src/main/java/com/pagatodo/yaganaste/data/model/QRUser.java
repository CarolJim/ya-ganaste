package com.pagatodo.yaganaste.data.model;

import java.io.Serializable;

public class QRUser implements Serializable {
    private String alias,plate;

    public QRUser(String alias, String plate) {
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
