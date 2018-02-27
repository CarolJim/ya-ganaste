package com.pagatodo.yaganaste.ui_wallet.holders;

import android.content.Context;
import android.view.View;

import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;

/**
 * Created by icruz on 26/02/2018.
 */

public class IndicationZoneViewHolder extends OptionsViewHolder{

    private Context context;

    public IndicationZoneViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
    }

    @Override
    public void bind(ElementView elementView, OnItemClickListener listener) {

    }
}
