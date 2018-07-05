package com.pagatodo.yaganaste.ui_wallet.holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;

/**
 * Created by icruz on 11/12/2017.
 */

public class ButtonsViewHolder extends OptionsViewHolder{

    public ImageView button;
    public TextView textOption;
    public View itemView;
    private Context context;

    public ButtonsViewHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        this.button = itemView.findViewById(R.id.button_item);
        this.textOption = itemView.findViewById(R.id.text_option);
    }

    @Override
    public void bind(final ElementView elementView, final OnItemClickListener listener) {
        this.button.setBackgroundResource(elementView.getResource());
        this.textOption.setText(context.getString(elementView.getTitle()));
        if (listener != null) {
            this.itemView.setOnClickListener(v -> listener.onItemClick(elementView));
        }
    }

    public View getItemView(){
        return this.itemView;
    }


/*
    public void bind(final ElementView elementView, final ElementsBalanceAdapter.OnItemClickListener listener) {
        this.button.setBackgroundResource(elementView.getResource());
        this.textOption.setText(elementView.getTitle());
        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(elementView);
            }
        });
    }
 */


}
