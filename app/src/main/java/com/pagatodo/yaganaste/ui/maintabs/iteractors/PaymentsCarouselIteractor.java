package com.pagatodo.yaganaste.ui.maintabs.iteractors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerBancoBinRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerCatalogoRequest;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IPaymentsCarouselIteractor;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.utils.Recursos.CATALOG_VERSION;

/**
 * Created by Jordan on 11/04/2017.
 */

public class PaymentsCarouselIteractor implements IPaymentsCarouselIteractor, IRequestResult {

    IPaymentsCarouselPresenter carouselPresenter;

    public PaymentsCarouselIteractor(IPaymentsCarouselPresenter paymentsCarouselPresenter) {
        carouselPresenter = paymentsCarouselPresenter;
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
            request.setVersion(preferencias.loadData(CATALOG_VERSION).isEmpty() ? "1" : preferencias.loadData(CATALOG_VERSION));
            ApiAdtvo.obtenerCatalogos(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCatalogosFromDB(int tab_id) {
        List<Comercio> catalogos = new ArrayList<>();
        try {
            catalogos = new DatabaseManager().getComerciosByType(tab_id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        List<Favoritos> catalogos = new ArrayList<>();
        try {
            catalogos = new DatabaseManager().getListFavoritosByIdComercio(tabID);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
