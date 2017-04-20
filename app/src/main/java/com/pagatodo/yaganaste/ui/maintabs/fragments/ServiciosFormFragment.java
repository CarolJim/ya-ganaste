package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;

/**
 * Created by Jordan on 12/04/2017.
 */

public class ServiciosFormFragment extends PaymentFormBaseFragment {

    public static ServiciosFormFragment newInstance(){
        ServiciosFormFragment fragment = new ServiciosFormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_servicios_form, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void continuePayment() {
        super.continuePayment();
    }
}
