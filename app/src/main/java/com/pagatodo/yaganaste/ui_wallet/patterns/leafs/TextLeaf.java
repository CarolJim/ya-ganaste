package com.pagatodo.yaganaste.ui_wallet.patterns.leafs;

import android.view.ViewGroup;

import com.pagatodo.yaganaste.ui_wallet.holders.TextDataViewHolder;
import com.pagatodo.yaganaste.ui_wallet.patterns.interfaces.Component;

public class TextLeaf implements Component {

    public TextDataViewHolder holder;

    public TextLeaf(TextDataViewHolder holder, Object item) {
        this.holder = holder;
        setContent(item);
    }

    @Override
    public void add(Component component) {

    }

    @Override
    public void setContent(Object item) {
        this.holder.bind(item,null);
    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(this.holder.getView());
    }
}
