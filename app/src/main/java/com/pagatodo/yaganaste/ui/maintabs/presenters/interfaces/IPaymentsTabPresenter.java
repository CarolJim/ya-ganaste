package com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces;

import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

/**
 * Created by Jordan on 11/04/2017.
 */

public interface IPaymentsTabPresenter {
    CarouselItem getCarouselItem();
    Comercio getComercioById(int idComercio);

    void setCarouselItem(CarouselItem carouselItem);
}
