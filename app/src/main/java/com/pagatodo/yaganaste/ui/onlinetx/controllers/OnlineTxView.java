package com.pagatodo.yaganaste.ui.onlinetx.controllers;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.interfaces.IProgressView;

/**
 * @author Juan Guerra on 18/04/2017.
 */

public interface OnlineTxView extends IProgressView<Errors>{
    void onTxAproved();
    void loadTransactionData();
}
