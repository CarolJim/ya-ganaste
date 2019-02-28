package com.pagatodo.view_manager.recyclers.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.dataholders.MovementDataHolder;
import com.pagatodo.view_manager.holders.MovementHolder;


import androidx.annotation.NonNull;

public class MovementAdapters extends GenericAdapter<MovementDataHolder,MovementHolder> {

    @NonNull
    @Override
    public MovementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovementHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movement_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovementHolder holder, int position) {

        if ( position % 2 == 0 ){
            holder.setColorBackgraund("#FFFFFF");
        } else {
            holder.setColorBackgraund("#FBFBFB");
        }
        holder.bind(this.itemlist.get(position),listener);
    }
}