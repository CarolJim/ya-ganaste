package com.pagatodo.yaganaste.freja.transactions.manager;

import com.pagatodo.yaganaste.freja.general.ErrorFmcManager;
import com.verisec.freja.mobile.core.wsHandler.beans.transaction.response.fromV1_5.FmcTransactionListResponse;

/**
 * @author Juan Guerra on 11/04/2017.
 */

public interface TransactionManager extends ErrorFmcManager {

    void setTransactions(FmcTransactionListResponse txs);

    void onTransactionAproved();

    void onTransactionCanceled();
}
