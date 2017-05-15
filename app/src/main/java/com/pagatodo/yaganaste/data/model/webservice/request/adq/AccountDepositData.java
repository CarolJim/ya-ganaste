package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class AccountDepositData implements Serializable{

    private String Account = "";
    private String Reference = "";

    public AccountDepositData() {
    }

    public AccountDepositData(String account, String reference){
        this.Account = account;
        this.Reference = reference;
    }
    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }
}
