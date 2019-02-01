package com.pagatodo.yaganaste.modules.management.response;

import com.pagatodo.yaganaste.modules.data.webservices.PlateResponse;

import java.io.Serializable;

public class QrDataSimpleResponse implements Serializable {

    private String plate,alias,bank,account;

    public QrDataSimpleResponse(String plate, String alias, String bank, String account) {
        this.plate = plate;
        this.alias = alias;
        this.bank = bank;
        this.account = account;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
