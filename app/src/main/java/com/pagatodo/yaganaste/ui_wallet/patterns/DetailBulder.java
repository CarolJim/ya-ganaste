package com.pagatodo.yaganaste.ui_wallet.patterns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE;
import com.pagatodo.yaganaste.ui_wallet.holders.TextDataViewHolder;


public class DetailBulder extends Builder {

    public DetailBulder(Context context, ViewGroup parent) {
        super(context, parent);
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
