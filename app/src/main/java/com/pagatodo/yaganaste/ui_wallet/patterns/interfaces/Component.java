package com.pagatodo.yaganaste.ui_wallet.patterns.interfaces;

import android.view.ViewGroup;

public interface Component {

    void add(Component component);
    void setContent(Object item);
    void inflate(ViewGroup layout);

}
