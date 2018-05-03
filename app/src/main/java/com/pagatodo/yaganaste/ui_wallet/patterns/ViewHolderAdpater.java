package com.pagatodo.yaganaste.ui_wallet.patterns;

import android.view.ViewGroup;

import com.pagatodo.yaganaste.ui_wallet.holders.GenericHolder;

public abstract class ViewHolderAdpater<VH extends GenericHolder> {

    public abstract VH  onCreateViewHolder(ViewGroup parent);

    public abstract void onBindViewHolder(VH holder);


}
