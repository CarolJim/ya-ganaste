package com.pagatodo.yaganaste.ui.maintabs.managers;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 24/04/2017.
 */

public interface PaymentsCarrouselManager extends PaymentsManager{
    void setCarouselData(ArrayList<CarouselItem> response);
}
