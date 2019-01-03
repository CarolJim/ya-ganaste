package com.pagatodo.view_manager.controllers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public abstract class GenericHolder<T> extends RecyclerView.ViewHolder implements LauncherHolder<T> {

    private View viewRoot;

    public GenericHolder(@NonNull View itemView) {
        super(itemView);
        this.viewRoot = itemView;
    }

    public abstract void init();


    public abstract void bind(final T item, final OnHolderListener<T> listener);


    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(this.viewRoot);
    }

    @Override
    public View getView() {
        return this.viewRoot;
    }
}
