package com.pagatodo.yaganaste.ui_wallet.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;

/**
 * Created by icruz on 26/02/2018.
 */

public abstract class OptionsViewHolder extends RecyclerView.ViewHolder {

    public OptionsViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(final ElementView elementView, final OnItemClickListener listener);

}
