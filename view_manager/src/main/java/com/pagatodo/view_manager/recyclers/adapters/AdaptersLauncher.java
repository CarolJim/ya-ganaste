package com.pagatodo.view_manager.recyclers.adapters;

import com.pagatodo.view_manager.controllers.OnHolderListener;

import java.util.ArrayList;

public interface AdaptersLauncher<T> {

    void setItemlist(ArrayList<T> itemlist);

    ArrayList<T> getItemlist();

    void addItem(T item);

    T getItem(int position);

    void setListener(OnHolderListener<T> listener);
}
