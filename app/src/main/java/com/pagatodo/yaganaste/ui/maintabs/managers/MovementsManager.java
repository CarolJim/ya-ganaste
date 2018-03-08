package com.pagatodo.yaganaste.ui.maintabs.managers;

import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneDatosCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ReembolsoResponse;

public interface MovementsManager<T, G> {

    void onSuccesResponse(T response);

    void onSuccesBalance(G response);

    void onSuccessDataCupo(ObtieneDatosCupoResponse response);

    void onSuccesreembolso(ReembolsoResponse response);

    void onFailed(int errorCode, int action, String error);
}
