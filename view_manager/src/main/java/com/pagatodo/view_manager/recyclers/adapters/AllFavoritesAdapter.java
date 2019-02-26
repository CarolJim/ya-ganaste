package com.pagatodo.view_manager.recyclers.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.RowFavDataHolder;
import com.pagatodo.view_manager.holders.FavoriteHorizontalHolder;
import com.pagatodo.view_manager.recyclers.interfaces.PencilListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllFavoritesAdapter extends RecyclerView.Adapter<FavoriteHorizontalHolder> {

    private ArrayList<RowFavDataHolder> listData;
    private OnHolderListener<RowFavDataHolder> listener;
    private PencilListener<RowFavDataHolder> listenerPencil;

    public AllFavoritesAdapter() {
        this.listData = new ArrayList<>();
    }

    public void setListener(OnHolderListener<RowFavDataHolder> listener) {
        this.listener = listener;
    }

    public void setListenerPencil(PencilListener<RowFavDataHolder> listener) {
        this.listenerPencil = listener;
    }

    public void setListData(ArrayList<RowFavDataHolder> listData) {
        this.listData = listData;
    }

    public void addItem(RowFavDataHolder dataHolder){
        this.listData.add(dataHolder);
    }

    public ArrayList<RowFavDataHolder> getListData() {
        return listData;
    }

    @NonNull
    @Override
    public FavoriteHorizontalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteHorizontalHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.favorite_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHorizontalHolder holder, int position) {
        holder.bind(listData.get(position),listener);
        if (this.listenerPencil != null) {
            holder.getPencil().setOnClickListener(v ->
                    listenerPencil.pencilOnClick(listData.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
