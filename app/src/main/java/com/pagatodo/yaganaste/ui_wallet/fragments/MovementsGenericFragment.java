package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui_wallet.presenter.WalletPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.WalletPresenterImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovementsGenericFragment extends SupportFragment{

    private WalletPresenter walletPresenter;

    public static MovementsGenericFragment newInstance() {
        return new MovementsGenericFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        walletPresenter = new WalletPresenterImpl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movements_generic, container, false);
        //walletPresenter.getMovementsAdq();
        return view;
    }

    @Override
    public void initViews() {

    }

}
