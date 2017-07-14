package com.pagatodo.yaganaste.freja.transactions.iteractor;

import com.pagatodo.yaganaste.freja.general.FmcIteractor;

/**
 * @author Juan Guerra on 11/04/2017.
 */

public interface TransactionsIteractor extends FmcIteractor {
    void getTransactions();

    void aproveTransaction(String idFreja, byte[] nip);
}
