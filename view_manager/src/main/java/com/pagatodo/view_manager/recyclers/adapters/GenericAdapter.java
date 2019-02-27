package com.pagatodo.view_manager.recyclers.adapters;

import android.view.ViewGroup;

import com.pagatodo.view_manager.controllers.GenericHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class GenericAdapter<T,P extends GenericHolder<T>> extends RecyclerView.Adapter<P>
        implements AdaptersLauncher<T>{

    ArrayList<T> itemlist;
    OnHolderListener<T> listener;

    GenericAdapter() {
        this.itemlist = new ArrayList<>();
    }

    @Override
    public void setItemlist(ArrayList<T> itemlist) {
        this.itemlist = itemlist;
    }

    @Override
    public ArrayList<T> getItemlist() {
        return this.itemlist;
    }

    @Override
    public void addItem(T item) {
        this.itemlist.add(item);
    }

    @Override
    public T getItem(int position) {
        return this.itemlist.get(position);
    }

    @Override
    public void setListener(OnHolderListener<T> listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public abstract P onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(@NonNull P holder, int position);

    @Override
    public int getItemCount() {
        return itemlist.size();
    }
}
