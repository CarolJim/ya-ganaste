package com.pagatodo.view_manager.recyclers.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.GenericHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;
import com.pagatodo.view_manager.holders.IconButtonComFavHolder;
import com.pagatodo.view_manager.holders.IconButtonComHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RechargesAdapter extends RecyclerView.Adapter<GenericHolder<IconButtonDataHolder>> {

    private ArrayList<IconButtonDataHolder> listData;
    private OnHolderListener<IconButtonDataHolder> listener;

    public RechargesAdapter() {
        this.listData = new ArrayList<>();
    }

    public void setListener(OnHolderListener<IconButtonDataHolder> listener) {
        this.listener = listener;
    }

    public void setListData(ArrayList<IconButtonDataHolder> listData) {
        this.listData = listData;
    }

    public ArrayList<IconButtonDataHolder> getListData() {
        return this.listData;
    }


    public void addItem(IconButtonDataHolder dataHolder){
        this.listData.add(dataHolder);
    }

    @NonNull
    @Override
    public GenericHolder<IconButtonDataHolder> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 3: //Favorite

            case 2: //PDS
                return new IconButtonComFavHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_icon_button_com_fav,parent,false));
            case 1: //Recagar
                return new IconButtonComHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_icon_button_com,parent,false));
                default: return new IconButtonComHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_icon_button_com,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull GenericHolder<IconButtonDataHolder> holder, int position) {
        holder.bind(this.listData.get(position),this.listener);
    }

    @Override
    public int getItemCount() {
        return this.listData.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (listData.get(position).getType()){
            case ITEM_RECHARGE_FAV:
                return 2;
            case ITEM_RECHARGE:
                return 1;
            case ADD_RECHARGE:
            case ADD_PAY:
                return 2;
                default:
                    return 1;
        }
    }
}
