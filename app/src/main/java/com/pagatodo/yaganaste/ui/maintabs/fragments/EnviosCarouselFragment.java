package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;

import butterknife.ButterKnife;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pagos_carousel, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        layoutCarouselMain.setVisibility(View.VISIBLE);
        setCarouselAdapter(this.paymentsCarouselPresenter.getCarouselItems());
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
