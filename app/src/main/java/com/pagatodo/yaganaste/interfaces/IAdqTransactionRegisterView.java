package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqTransactionRegisterView extends IAccountView2 {

    public void showInsertDongle();
    public void showInsertCard();
    public void dongleValidated();
    public void verifyDongle(String ksn);
    public void transactionResult(String message);
}
