package com.pagatodo.yaganaste.data.model;

import java.io.Serializable;

public class QRs implements Serializable {

    private String alias,plate;
    private boolean isDigital;

    public QRs(String alias, String plate, boolean isDigital) {
        this.alias = alias;
        this.plate = plate;
        this.isDigital = isDigital;
    }

    public boolean isDigital() {
        return isDigital;
    }

    public void setDigital(boolean digital) {
        isDigital = digital;
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
