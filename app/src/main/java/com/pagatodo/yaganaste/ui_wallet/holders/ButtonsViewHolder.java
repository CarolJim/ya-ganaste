package com.pagatodo.yaganaste.ui_wallet.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsWalletAdpater;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ElementView;

/**
 * Created by icruz on 11/12/2017.
 */

public class ButtonsViewHolder extends RecyclerView.ViewHolder{

    public ImageView button;
    public TextView textOption;
    private View itemView;


    public ButtonsViewHolder(View itemView) {
        super(itemView);
        this.button = (ImageView) itemView.findViewById(R.id.button_item);
        this.textOption = (TextView) itemView.findViewById(R.id.text_option);
        this.itemView = itemView;
    }

    public void bind(final ElementView elementView, final ElementsWalletAdpater.OnItemClickListener listener){
        this.button.setBackgroundResource(elementView.getResource());
        this.textOption.setText(elementView.getTitle());
        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(elementView);
            }
        });
    }
}
