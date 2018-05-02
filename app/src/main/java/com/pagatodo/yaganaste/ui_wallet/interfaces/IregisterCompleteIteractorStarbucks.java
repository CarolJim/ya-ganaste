package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.RegisterStarbucksCompleteRequest;
import com.pagatodo.yaganaste.interfaces.enums.WebService;

/**
 * Created by asandovals on 26/04/2018.
 */

public interface IregisterCompleteIteractorStarbucks<T> {

    void onSucces(WebService ws, T msgSuccess);

    void onError(WebService ws, T error);

    void hideLoader();

    void consultaInfoPersona();

    void registerStarBucks(RegisterStarbucksCompleteRequest request);

    void getNeighborhoodByZipCode(String zipCode);
}
