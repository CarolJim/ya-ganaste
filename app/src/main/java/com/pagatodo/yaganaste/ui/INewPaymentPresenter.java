package com.pagatodo.yaganaste.ui;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;

import java.util.List;

/**
 * Created by FranciscoManzo on 28/12/2017.
 */

public interface INewPaymentPresenter {
    void getRecargarItems(int typeReload);

    void resToPresenter();

    void onSuccesDBObtenerCatalogos(List<ComercioResponse> catalogos);

    void onErrorService();
}
