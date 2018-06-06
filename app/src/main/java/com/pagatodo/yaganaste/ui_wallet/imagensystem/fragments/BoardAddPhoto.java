package com.pagatodo.yaganaste.ui_wallet.imagensystem.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardAddPhoto extends SupportFragment {

    private View rootview;

    public BoardAddPhoto() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootview = inflater.inflate(R.layout.fragment_board_add_photo, container, false);;
        this.initViews();
        return this.rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
    }
}
