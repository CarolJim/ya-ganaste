package com.pagatodo.yaganaste.ui.maintabs.presenters;

import android.content.Context;

import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsTabPresenter;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

/**
 * Created by Jordan on 11/04/2017.
 */

public class PaymentsTabPresenter implements IPaymentsTabPresenter {
    private CarouselItem carouselItem;
    private CatalogsDbApi catalogsDbApi;

    public PaymentsTabPresenter(Context context) {
        catalogsDbApi = new CatalogsDbApi(context);
    }

    @Override
    public CarouselItem getCarouselItem() {
        return carouselItem;
    }

    @Override
    public ComercioResponse getComercioById(long idComercio) {
        return catalogsDbApi.getComercioById(idComercio);
    }

    @Override
    public void setCarouselItem(CarouselItem carouselItem) {
        this.carouselItem = carouselItem;
    }
}