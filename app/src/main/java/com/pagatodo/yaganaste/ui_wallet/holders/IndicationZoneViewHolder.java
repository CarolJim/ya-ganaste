package com.pagatodo.yaganaste.ui_wallet.holders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

/**
 * Created by icruz on 26/02/2018.
 */

public class IndicationZoneViewHolder extends OptionsViewHolder {

    private Context context;
    public StyleButton button;
    private StyleTextView mas_info;

    public IndicationZoneViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.button = itemView.findViewById(R.id.btn_begin_register);
        this.mas_info = itemView.findViewById(R.id.mas_info);
    }

    @Override
    public void bind(final ElementView elementView, final OnItemClickListener listener) {
        if (elementView.getResource() != 0) {

        }
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(elementView);
            }
        });
        this.mas_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yaganaste.com"));
                context.startActivity(browserIntent);
            }
        });
    }
}
