package com.pagatodo.yaganaste.ui_wallet.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class GenericHolder extends RecyclerView.ViewHolder implements LauncherHolder{

    public View itemView;

    public GenericHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    @Override
    public abstract void init();

    @Override
    public abstract void bind(Object item, OnClickItemHolderListener listener);

    public abstract View getView();
}
