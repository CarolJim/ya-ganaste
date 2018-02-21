package com.pagatodo.yaganaste.ui_wallet.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsBalanceAdapter;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsWalletAdapter;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;

/**
 * Created by icruz on 11/12/2017.
 */

public class ButtonsViewHolder extends RecyclerView.ViewHolder implements Runnable {

    public ImageView button;
    public TextView textOption;
    public View itemView;
    private Animation animSlideUp;
    private Context context;

    public ButtonsViewHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        this.button = (ImageView) itemView.findViewById(R.id.button_item);
        this.textOption = itemView.findViewById(R.id.text_option);
    }

    public void bind(final ElementView elementView, final ElementsWalletAdapter.OnItemClickListener listener) {
        this.button.setBackgroundResource(elementView.getResource());
        this.textOption.setText(context.getString(elementView.getTitle()));
        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(elementView);
            }
        });
    }

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

    @Override
    public void run() {
        itemView.setVisibility(View.VISIBLE);
        itemView.startAnimation(animSlideUp);
    }
}
