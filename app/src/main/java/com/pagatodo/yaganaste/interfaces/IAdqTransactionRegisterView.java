package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqTransactionRegisterView extends INavigationView {

    void showInsertDongle();

    void showInsertCard();

    void showInsertPin();

    void dongleValidated();

    void verifyDongle(String ksn);

    void transactionResult(String message);

    void showSimpleDialogError(String message, DialogDoubleActions actions);

}
