package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsTabPresenter;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

/**
 * Created by Jordan on 11/04/2017.
 */

public class PaymentsTabPresenter implements IPaymentsTabPresenter {
    private CarouselItem carouselItem;

    public PaymentsTabPresenter() {

    }


    @Override
    public CarouselItem getCarouselItem() {
        return carouselItem;
    }

    @Override
    public void setCarouselItem(CarouselItem carouselItem) {
        this.carouselItem = carouselItem;
    }
}