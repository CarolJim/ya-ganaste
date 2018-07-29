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

    void transactionResult(String message, String tlv);

    void showSimpleDialogError(String message, DialogDoubleActions actions);

    void onErrorConsultSaldo(String message);

    void onErrorTransaction();

    void cancelTransactionChip();

    void onDongleSelected(int position);

    void onSearchCancel();
}
