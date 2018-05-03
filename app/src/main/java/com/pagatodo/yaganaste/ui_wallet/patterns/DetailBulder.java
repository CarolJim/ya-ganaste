package com.pagatodo.yaganaste.ui_wallet.patterns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.ui_wallet.holders.DetailMovementHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.TextDataViewHolder;


public class DetailBulder extends Builder {

    DetailBulder(Context context, ViewGroup parent) {
        super(context, parent);
    }

    public void createDetailMov(ItemMovements item){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.item_mov_head, parent, false);
        DetailMovementHolder holder = new DetailMovementHolder(layout);
        holder.bind(item,null);
        this.parent.addView(layout);
    }

    @Override
    public void createLeaf(Object item) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.item_detail_mov, parent, false);
        TextDataViewHolder holder = new TextDataViewHolder(layout);
        holder.bind(item,null);
        this.parent.addView(layout);
    }
}
