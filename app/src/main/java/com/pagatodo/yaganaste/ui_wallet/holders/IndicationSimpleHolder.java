package com.pagatodo.yaganaste.ui_wallet.holders;

import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

public class IndicationSimpleHolder extends GenericHolder {

    public IndicationSimpleHolder(@NonNull View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {

    }

    @Override
    public void bind(Object item, OnClickItemHolderListener listener) {

    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(this.itemView);
    }

    @Override
    public View getView() {
        return this.itemView;
    }
}
