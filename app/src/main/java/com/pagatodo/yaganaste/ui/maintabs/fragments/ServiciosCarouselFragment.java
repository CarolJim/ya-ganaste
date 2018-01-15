package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.view.View;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 10/04/2017.
 */

public class ServiciosCarouselFragment extends PaymentsFragmentCarousel {

    private View rootView;

    public static ServiciosCarouselFragment newInstance() {
        ServiciosCarouselFragment fragmentCarousel = new ServiciosCarouselFragment();
        Bundle args = new Bundle();
        args.putString("TAB", MovementsTab.TAB2.name());
        fragmentCarousel.setArguments(args);
        return fragmentCarousel;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setCarouselDataFavoritos(ArrayList<CarouselItem> response) {

    }

    @Override
    public void setFavolist(List<DataFavoritos> lista) {

    }
}
