package com.pagatodo.yaganaste.ui.maintabs.managers;

import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneDatosCupoResponse;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public interface MovementsManager<T, G> {

    void onSuccesResponse(T response);

    void onSuccesBalance(G response);

    void onSuccessDataCupo(ObtieneDatosCupoResponse response);

    void onFailed(int errorCode, int action, String error);
}
