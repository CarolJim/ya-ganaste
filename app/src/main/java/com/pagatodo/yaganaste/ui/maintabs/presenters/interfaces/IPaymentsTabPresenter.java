package com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces;

import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

/**
 * Created by Jordan on 11/04/2017.
 */

public interface IPaymentsTabPresenter {
    CarouselItem getCarouselItem();
    void setCarouselItem(CarouselItem carouselItem);
}
