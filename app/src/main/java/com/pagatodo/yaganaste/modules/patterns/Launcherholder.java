package com.pagatodo.yaganaste.modules.patterns;

import android.view.View;
import android.view.ViewGroup;

public interface Launcherholder<T> {
    void init();

    void bind(T item, OnHolderListener<T> listener);

    void inflate(ViewGroup layout);

    View getView();
}
