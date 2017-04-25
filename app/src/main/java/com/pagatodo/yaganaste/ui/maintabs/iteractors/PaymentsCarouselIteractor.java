package com.pagatodo.yaganaste.ui.maintabs.iteractors;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerCatalogoRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IPaymentsCarouselIteractor;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;

import java.util.List;

/**
 * Created by Jordan on 11/04/2017.
 */

public class PaymentsCarouselIteractor implements IPaymentsCarouselIteractor, IRequestResult {

    IPaymentsCarouselPresenter carouselPresenter;
    CatalogsDbApi catalogsDbApi;

    public PaymentsCarouselIteractor(IPaymentsCarouselPresenter paymentsCarouselPresenter, CatalogsDbApi dbApi) {
        carouselPresenter = paymentsCarouselPresenter;
        catalogsDbApi = dbApi;
    }

    @Override
    public void getCatalogosFromService() {
        try {
            ApiAdtvo.obtenerCatalogos(new ObtenerCatalogoRequest(), this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCatalogosFromDB(int tab_id) {

        List<ComercioResponse> catalogos = catalogsDbApi.getComerciosList(tab_id);
        if (catalogos.size() > 0) {
            carouselPresenter.onSuccesDBObtenerCatalogos(catalogos);
        } else {
            carouselPresenter.onErrorService();
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        switch (dataSourceResult.getWebService()) {
            case OBTENER_CATALOGOS:
                carouselPresenter.onSuccessWSObtenerCatalogos(dataSourceResult);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        carouselPresenter.onErrorService();
    }
}
