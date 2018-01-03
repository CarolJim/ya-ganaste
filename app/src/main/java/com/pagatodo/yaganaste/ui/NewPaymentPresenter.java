package com.pagatodo.yaganaste.ui;

import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FranciscoManzo on 28/12/2017.
 */

public class NewPaymentPresenter implements INewPaymentPresenter {
    MovementsTab current_tab;
    NewPaymentFragment mView;
    INewPaymentInteractor mInteractor;
    Context mContext;
    private CatalogsDbApi api;
    private int typeData;

    public NewPaymentPresenter(NewPaymentFragment mView, Context context) {
        this.mView = mView;
        mInteractor = new NewPaymentInteractor(this);
        this.mContext = context;
        this.api = new CatalogsDbApi(context);
    }

    @Override
    public void getRecargarItems(int typeData) {
        this.typeData = typeData;
        //Revisar que la tabla no esté
        if (api.isCatalogTableEmpty()) {
            mInteractor.getCatalogosRecargarFromService();
        } else {
            mInteractor.getCatalogosFromDB(typeData);
        }
    }

    @Override
    public void resToPresenter() {
        mView.resToView();
    }

    @Override
    public void onSuccesDBObtenerCatalogos(List<ComercioResponse> comercios) {
        mView.setCarouselData(comercios, typeData);
    }

    @Override
    public void onErrorService() {

    }


    /**
     * METODO AUXILIARES
     */
    private ArrayList<CarouselItem> getCarouselItems(List<ComercioResponse> comercios) {
        ArrayList<CarouselItem> carouselItems = new ArrayList<>();

        CarouselItem carouselItemSearch = new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null, null);
        carouselItemSearch.setSearchImageViewMargin();

        //carouselItems.add(0, new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null));
        carouselItems.add(0, carouselItemSearch);
        CarouselItem carouselItemCommerce;
        for (ComercioResponse comercio : comercios) {
            if (comercio.getIdTipoComercio() == typeData) {
                if (comercio.getIdComercio() != 0) {
                    if (comercio.getColorMarca().isEmpty()) {
                        carouselItemCommerce = new CarouselItem(App.getContext(), comercio.getLogoURL(), "#10B2E6", CarouselItem.DRAG, comercio);
                        carouselItemCommerce.setCommerceImageViewMargin();
                        carouselItems.add(carouselItemCommerce);
                    } else {
                        carouselItemCommerce = new CarouselItem(App.getContext(), comercio.getLogoURL(), comercio.getColorMarca().toUpperCase(), CarouselItem.DRAG, comercio);
                        carouselItemCommerce.setCommerceImageViewMargin();
                        carouselItems.add(carouselItemCommerce);
                    }
                } else {
                    //carouselItems.add(new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, comercio));
                    CarouselItem carouselItemSearch2 = new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, comercio, null);
                    carouselItemSearch2.setSearchImageViewMargin();
                    carouselItems.add(carouselItemSearch2);
                }
            }
        }
        return carouselItems;
    }
}
