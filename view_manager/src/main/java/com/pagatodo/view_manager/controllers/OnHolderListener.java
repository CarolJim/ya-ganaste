package com.pagatodo.view_manager.controllers;

import android.view.View;

public interface OnHolderListener<T> {
    void onClickView(T item,View view);
}
