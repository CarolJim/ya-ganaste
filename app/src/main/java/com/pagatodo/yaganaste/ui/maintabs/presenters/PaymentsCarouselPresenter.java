package com.pagatodo.yaganaste.ui.maintabs.presenters;

import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerCatalogosResponse;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.PaymentsCarouselIteractor;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IPaymentsCarouselIteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by Jordan on 11/04/2017.
 */

public class PaymentsCarouselPresenter implements IPaymentsCarouselPresenter {
    MovementsTab current_tab;
    IPaymentsCarouselIteractor paymentsTabIteractor;
    PaymentsCarrouselManager paymentsManager;
    Context mContext;
    private CatalogsDbApi api;

    public PaymentsCarouselPresenter(MovementsTab current_tab, PaymentsCarrouselManager paymentsManager, Context context) {
        this.current_tab = current_tab;
        this.paymentsManager = paymentsManager;
        this.mContext = context;
        this.api = new CatalogsDbApi(context);
        this.paymentsTabIteractor = new PaymentsCarouselIteractor(this, api);
    }

    @Override
    public MovementsTab getCurrenTab() {
        return this.current_tab;
    }

    @Override
    public ArrayList<CarouselItem> getCarouselArray() {
        ArrayList<CarouselItem> carouselItems = new ArrayList<>();
        carouselItems.add(0, new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null));
        List<ComercioResponse> comercios = paymentsTabIteractor.getCatalogos().getData().getComercios();
        for (int i = 0; i < comercios.size(); i++) {
            ComercioResponse comercio = comercios.get(i);
            if (comercio.getIdTipoComercio() == current_tab.getId()) {
                if (comercio.getIdComercio() != 0) {
                    if (comercio.getColorMarca().isEmpty()) {
                        carouselItems.add(new CarouselItem(App.getContext(), comercio.getLogoURL(), "#10B2E6", CarouselItem.DRAG, comercio));
                    } else {
                        carouselItems.add(new CarouselItem(App.getContext(), comercio.getLogoURL(), comercio.getColorMarca().toUpperCase(), CarouselItem.DRAG, comercio));
                    }
                } else {
                    carouselItems.add(new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, comercio));
                }
            }
        }
        return carouselItems;
    }

    @Override
    public void getCarouselItems() {
        //Revisar que la tabla no esté
        if (api.isCatalogTableEmpty()) {
            paymentsTabIteractor.getCatalogosFromService();
        } else {
            paymentsTabIteractor.getCatalogosFromDB(current_tab.getId());
        }
    }

    @Override
    public void onErrorService() {
        paymentsManager.showError();
    }

    @Override
    public void onSuccessWSObtenerCatalogos(DataSourceResult result) {
        ObtenerCatalogosResponse response = (ObtenerCatalogosResponse) result.getData();

        if (response.getCodigoRespuesta() == CODE_OK) {
            try {
                api.insertComercios(response.getData().getComercios());
                paymentsManager.setCarouselData(getCarouselItems(response.getData().getComercios()));
            } catch (Exception e) {
                e.printStackTrace();
                paymentsManager.showError();
            }
        } else {
            paymentsManager.showError();
        }
    }

    @Override
    public void onSuccesDBObtenerCatalogos(List<ComercioResponse> comercios) {
        paymentsManager.setCarouselData(getCarouselItems(comercios));
    }

    private ArrayList<CarouselItem> getCarouselItems(List<ComercioResponse> comercios) {
        ArrayList<CarouselItem> carouselItems = new ArrayList<>();
        carouselItems.add(0, new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null));
        for (ComercioResponse comercio : comercios) {
            if (comercio.getIdTipoComercio() == current_tab.getId()) {
                if (comercio.getIdComercio() != 0) {
                    if (comercio.getColorMarca().isEmpty()) {
                        carouselItems.add(new CarouselItem(App.getContext(), comercio.getLogoURL(), "#10B2E6", CarouselItem.DRAG, comercio));
                    } else {
                        carouselItems.add(new CarouselItem(App.getContext(), comercio.getLogoURL(), comercio.getColorMarca().toUpperCase(), CarouselItem.DRAG, comercio));
                    }
                } else {
                    carouselItems.add(new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, comercio));
                }
            }
        }
        return carouselItems;
    }
}
