package com.pagatodo.yaganaste.modules.management.request;

import java.io.Serializable;

public class QrRequest implements Serializable {

    private String plate;
    private String name;
    private String bank;
    private String account;

    public QrRequest(String plate, String name, String bank, String account) {
        this.plate = plate;
        this.name = name;
        this.bank = bank;
        this.account = account;
    }

  public QrRequest(String plate) {
        this.plate = plate;
    }

    public QrRequest() {
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
