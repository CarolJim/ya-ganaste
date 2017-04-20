package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.TransactionAdqResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;

import java.util.List;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqTransactionRegisterView extends IAccountView2 {

    public void showInsertDongle();
    public void showInsertCard();
    public void dongleValidated();
    public void transactionResult(String message);
}
