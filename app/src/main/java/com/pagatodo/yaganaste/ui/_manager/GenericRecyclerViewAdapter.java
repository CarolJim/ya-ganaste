package com.pagatodo.yaganaste.ui._manager;

import android.support.v7.widget.RecyclerView;

import com.pagatodo.yaganaste.interfaces.OnEventListener;

import java.util.List;

/**
 * Created by jvazquez on 09/02/2017.
 */

public abstract class GenericRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> items;
    protected OnEventListener onEventListener;

    public GenericRecyclerViewAdapter(List<T> items, OnEventListener onEventListener) {
        this.items = items;
        this.onEventListener = onEventListener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<T> getItems() {
        return items;
    }

    public Boolean updateList(List<T> items) {
        if (items != null && items.size() > 0) {
            this.items.addAll(items);
            notifyDataSetChanged();
            return items.size() > 0;
        }
        return false;
    }
}