package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;

import butterknife.ButterKnife;

/**
 * Created by asandovals on 22/05/2018.
 */

public class ContactoFragment extends SupportFragment {
    @Override
    public void initViews() {
    }
    public  static  ContactoFragment newInstance(){
        return  new ContactoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmentcontacto, container, false);
        ButterKnife.bind(this, rootView);
        initViews();
        return rootView;


    }
}
