package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.utils.customviews.carousel.Carousel;

import butterknife.ButterKnife;

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
}
