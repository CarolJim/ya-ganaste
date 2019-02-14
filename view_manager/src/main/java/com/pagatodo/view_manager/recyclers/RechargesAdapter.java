package com.pagatodo.view_manager.recyclers;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;
import com.pagatodo.view_manager.holders.IconButtonHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class RechargesAdapter extends RecyclerView.Adapter<IconButtonHolder> {

    private ArrayList<IconButtonDataHolder> listData;

    RechargesAdapter() {
        this.listData = new ArrayList<>();
    }

    void setListData(ArrayList<IconButtonDataHolder> listData) {
        this.listData = listData;
    }

    void addItem(IconButtonDataHolder dataHolder){
        this.listData.add(dataHolder);
    }

    @NonNull
    @Override
    public IconButtonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 2:
                return new IconButtonHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_icon_button_sub,parent,false));
            case 1:
                return new IconButtonHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_icon_button,parent,false));
                default: return new IconButtonHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_icon_button,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull IconButtonHolder holder, int position) {
        holder.bind(this.listData.get(position),null);
    }

    @Override
    public int getItemCount() {
        return this.listData.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (listData.get(position).getType()){
            case ITEM_PAY:
                return 2;
            case ITEM_RECHARGE:
                return 1;
                default:
                    return 1;
        }
    }
}
