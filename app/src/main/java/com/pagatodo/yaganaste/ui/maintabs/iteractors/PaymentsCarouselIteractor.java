package com.pagatodo.yaganaste.ui.maintabs.iteractors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerBancoBinRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerCatalogoRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IPaymentsCarouselIteractor;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.utils.StringConstants;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_BANCOSBIN;

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
    public void getdatabank(String pbusqueda, String cob) {
        try {
            ObtenerBancoBinRequest request = new ObtenerBancoBinRequest();
            request.setPbusqueda(pbusqueda);
            request.setTipoConsulta(cob);
            ApiAdtvo.obtenerBancoBin(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCatalogosFromService() {
        try {
            ObtenerCatalogoRequest request = new ObtenerCatalogoRequest();
            Preferencias preferencias = App.getInstance().getPrefs();
            request.setVersion(preferencias.loadData(StringConstants.CATALOG_VERSION).isEmpty() ? "1" : preferencias.loadData(StringConstants.CATALOG_VERSION));
            ApiAdtvo.obtenerCatalogos(request, this);
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
    public void getFavoritesFromService() {
        try {
            ApiAdtvo.consultarFavoritos(this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getFavoritesFromDB(int tabID) {
        List<DataFavoritos> catalogos = catalogsDbApi.getFavoritesList(tabID);
        carouselPresenter.onSuccessDBFavorites(catalogos);
    }

    /*@Override
    public ObtenerCatalogosResponse getCatalogos() {
        Gson gson = new Gson();
        String catalogosJSONStringResponse = Utils.getJSONStringFromAssets(App.getContext(), "files/catalogos.json");
        return gson.fromJson(catalogosJSONStringResponse, ObtenerCatalogosResponse.class);
    }*/

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        switch (dataSourceResult.getWebService()) {
            case OBTENER_CATALOGOS:
                carouselPresenter.onSuccessWSObtenerCatalogos(dataSourceResult);
                break;
            case OBTENER_FAVORITOS:
                carouselPresenter.onSuccessWSFavorites(dataSourceResult);
                break;

            case OBTENER_BANCOSBIN:
                carouselPresenter.onSuccessWSBankBin(dataSourceResult);
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
