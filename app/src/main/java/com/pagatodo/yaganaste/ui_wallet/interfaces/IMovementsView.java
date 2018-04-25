package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosSbResponse;

import java.util.List;

public interface IMovementsView extends IMainWalletView {
    void loadMovementsResult(List<MovimientosSbResponse> movementsList);
    void onFailed(String error);
}
