package com.pagatodo.yaganaste.freja.transactions.presenter;

import android.support.annotation.Nullable;

/**
 * @author Juan Guerra on 11/04/2017.
 */

public interface TransactionsPresenter {
    void getTransactions();
    void aproveTransaction(String idFreja, String nip);
}
