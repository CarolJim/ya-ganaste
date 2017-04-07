package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

/**
 * Created by Jordan on 06/04/2017.
 */

public class PaymentsFragmentCarousel extends GenericFragment {

    private View rootView;

    public static PaymentsFragmentCarousel newInstance(){
        PaymentsFragmentCarousel paymentsFragmentCarousel = new PaymentsFragmentCarousel();
        Bundle args = new Bundle();
        paymentsFragmentCarousel.setArguments(args);

        return paymentsFragmentCarousel;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }
}
