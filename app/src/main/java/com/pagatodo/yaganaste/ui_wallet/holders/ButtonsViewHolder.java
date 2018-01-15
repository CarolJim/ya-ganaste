package com.pagatodo.yaganaste.ui_wallet.holders;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class ButtonsViewHolder extends RecyclerView.ViewHolder implements Runnable{

    public ImageView button;
    public TextView textOption;
    public View itemView;
    private Animation animSlideUp;


    public ButtonsViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.button = (ImageView) itemView.findViewById(R.id.button_item);
        this.textOption = (TextView) itemView.findViewById(R.id.text_option);



    }

    public void bind(final ElementView elementView, final ElementsWalletAdpater.OnItemClickListener listener) {
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
