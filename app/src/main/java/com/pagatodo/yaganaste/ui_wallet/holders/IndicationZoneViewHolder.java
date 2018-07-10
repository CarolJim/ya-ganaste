package com.pagatodo.yaganaste.ui_wallet.holders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

/**
 * Created by icruz on 26/02/2018.
 */

public class IndicationZoneViewHolder extends GenericHolder {

    private Context context;
    public StyleButton button;
    private StyleTextView mas_info;

    public IndicationZoneViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        init();
    }

    @Override
    public void init() {
        this.button = itemView.findViewById(R.id.btn_begin_register);
        this.mas_info = itemView.findViewById(R.id.mas_info);
    }

    @Override
    public void bind(Object item, OnClickItemHolderListener listener)  {
        ElementView elementView = (ElementView) item;
        if (elementView.getResource() != -1) {

        }
        this.button.setText(elementView.getTextbutton());
        // TODO: HABILITAR BOTON DE REGISTRO
        this.button.setOnClickListener(view -> listener.onItemClick(elementView));
        this.mas_info.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yaganaste.com"));
            context.startActivity(browserIntent);
        });
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
