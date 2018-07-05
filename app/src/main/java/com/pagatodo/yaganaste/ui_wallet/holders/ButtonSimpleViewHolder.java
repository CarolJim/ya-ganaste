package com.pagatodo.yaganaste.ui_wallet.holders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;

public class ButtonSimpleViewHolder extends GenericHolder {

    public ImageView button;
    public TextView textOption;


    public ButtonSimpleViewHolder(View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        this.button = itemView.findViewById(R.id.button_item);
        this.textOption = itemView.findViewById(R.id.text_option);
    }

    @Override
    public void bind(Object item, OnClickItemHolderListener listener) {
        ElementView elementView = (ElementView) item;
        this.button.setBackgroundResource(elementView.getResource());
        this.textOption.setText(elementView.getTitle());
        if (listener != null) {
            this.itemView.setOnClickListener(v -> listener.onClick(elementView));
        }
    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(this.itemView);
    }

    @Override
    public View getView() {
        return this.itemView;
    }
}
