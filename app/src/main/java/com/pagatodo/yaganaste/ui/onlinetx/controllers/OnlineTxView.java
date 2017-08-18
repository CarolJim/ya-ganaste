package com.pagatodo.yaganaste.ui.onlinetx.controllers;

import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.dto.OnlineTxData;
import com.pagatodo.yaganaste.interfaces.IProgressView;

/**
 * @author Juan Guerra on 18/04/2017.
 */

public interface OnlineTxView extends IProgressView<ErrorObject> {
    void onTxAproved();

    void onTxFailed(String message);

    void loadTransactionData(OnlineTxData onlineTxData);
}
