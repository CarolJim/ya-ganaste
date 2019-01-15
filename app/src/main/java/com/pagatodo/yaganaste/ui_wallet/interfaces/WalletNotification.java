package com.pagatodo.yaganaste.ui_wallet.interfaces;

import androidx.annotation.Nullable;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarMovimientosMesResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataInfoAgente;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by icruz on 26/12/2017.
 */

public interface WalletNotification {
    void onFailed(int errorCode, int action, String error);
    void onFailedSaldo(String error);
    void onSuccess(boolean error);
    void onSuccesSaldo(@Nullable int typeWallet, @Nullable String response);
    void onSuccessResponse(GenericResponse response);
    //void onSuccessInfoAgente(DataInfoAgente response);

    void onSuccess(@Nullable Integer typeWallet, @Nullable Object result);
}
