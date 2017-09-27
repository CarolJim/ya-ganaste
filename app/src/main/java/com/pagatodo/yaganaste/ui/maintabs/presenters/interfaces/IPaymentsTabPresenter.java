package com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

/**
 * Created by Jordan on 11/04/2017.
 */

public interface IPaymentsTabPresenter {
    CarouselItem getCarouselItem();
    ComercioResponse getComercioById(long idComercio);

    void setCarouselItem(CarouselItem carouselItem);
}
