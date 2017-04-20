package com.pagatodo.yaganaste.ui.onlinetx.iteractors;

import com.pagatodo.yaganaste.freja.transactions.iteractor.TransactionsIteractorImp;
import com.pagatodo.yaganaste.freja.transactions.manager.TransactionManager;

import java.util.concurrent.Executor;

/**
 * @author Juan Guerra on 18/04/2017.
 */

public class OnlineTxIteractor extends TransactionsIteractorImp {

    public OnlineTxIteractor(TransactionManager transactionManager, Executor mExecutor) {
        super(transactionManager, mExecutor);
    }

}
