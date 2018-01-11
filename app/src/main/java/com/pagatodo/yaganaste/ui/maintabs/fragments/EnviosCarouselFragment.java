package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.view.View;

import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;

/**
 * Created by Jordan on 10/04/2017.
 */

public class EnviosCarouselFragment extends PaymentsFragmentCarousel {

    private View rootView;

    public static EnviosCarouselFragment newInstance() {
        EnviosCarouselFragment fragmentCarousel = new EnviosCarouselFragment();
        Bundle args = new Bundle();
        args.putString("TAB", MovementsTab.TAB3.name());
        fragmentCarousel.setArguments(args);
        return fragmentCarousel;
    }


    @Override
    public void onResume() {
        super.onResume();
        super.resumeTest();
    }

    @Override
    public void setCarouselDataFavoritos(ArrayList<CarouselItem> response) {

    }
}
