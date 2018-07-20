package com.pagatodo.yaganaste.ui_wallet.holders;

import android.view.View;
import android.view.ViewGroup;

public interface LauncherHolder {
    void init();
    void bind(Object item, OnClickItemHolderListener listener);
    void inflate(ViewGroup layout);
    View getView();

}
