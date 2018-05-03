package com.pagatodo.yaganaste.ui_wallet.patterns;

import android.content.Context;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.ui_wallet.holders.GenericHolder;

public class ViewHolderContainer extends ViewHolderAdpater<GenericHolder> {

    private Context context;
    private GenericHolder holder;

    public ViewHolderContainer() {

    }

    @Override
    public GenericHolder onCreateViewHolder(ViewGroup parent) {
        return ContainerBuilder.getViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(GenericHolder holder) {

    }
}
