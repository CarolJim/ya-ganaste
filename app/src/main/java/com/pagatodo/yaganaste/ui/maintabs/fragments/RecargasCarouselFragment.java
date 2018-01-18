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

public class RecargasCarouselFragment extends PaymentsFragmentCarousel {

    private View rootView;

    public static RecargasCarouselFragment newInstance() {
        RecargasCarouselFragment fragmentCarousel = new RecargasCarouselFragment();
        Bundle args = new Bundle();
        args.putString("TAB", MovementsTab.TAB1.name());
        fragmentCarousel.setArguments(args);
        return fragmentCarousel;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setDataBank(String idcomercio, String nombrebank) {

    }

    @Override
    public void errorgetdatabank() {

    }

    @Override
    public void setCarouselDataFavoritos(ArrayList<CarouselItem> response) {

    }

    @Override
    public void setFavolist(List<DataFavoritos> lista) {

    }
}
