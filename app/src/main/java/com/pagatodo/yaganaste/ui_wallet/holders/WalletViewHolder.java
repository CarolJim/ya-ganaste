package com.pagatodo.yaganaste.ui_wallet.holders;

import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ICardBalance;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import eu.davidea.flipview.FlipView;

public class WalletViewHolder extends GenericHolder {

    private FlipView flipView;
    private ICardBalance listener;
    private RelativeLayout cardLayout;
    private int position;
    private StyleTextView nameBusiness;

    public WalletViewHolder(View itemView, ICardBalance listener) {
        super(itemView);
        this.listener = listener;
        init();
    }

    public WalletViewHolder(View itemView, ICardBalance listener, int position) {
        super(itemView);
        this.listener = listener;
        this.position = position;
        init();
    }

    @Override
    public void init() {
        this.flipView = itemView.findViewById(R.id.cardflip_element);
        this.cardLayout = itemView.findViewById(R.id.card_layout);
        this.nameBusiness = itemView.findViewById(R.id.title_negocio);
        FlipView.enableLogs(BuildConfig.DEBUG);
    }

    @Override
    public void bind(Object elementWallet, @Nullable OnClickItemHolderListener listener) {
        ElementWallet item = (ElementWallet) elementWallet;
        if (this.listener != null) {
            if (item.getFrontBitmap() != null) {
                this.cardLayout.setVisibility(View.GONE);
                flipView.setFrontImageBitmap(item.getFrontBitmap());
                if (item.getRearBitmap() != null) {
                    flipView.setRearImageBitmap(item.getRearBitmap());

                }
                flipView.setOnClickListener(view -> {
                    this.listener.onCardClick(view, this.position);
                });
            }

        } else {
            if (item.getFrontBitmap() != null) {
                this.cardLayout.setVisibility(View.GONE);
                flipView.setFrontImageBitmap(item.getFrontBitmap());
                if (item.getRearBitmap() != null) {
                    flipView.setRearImageBitmap(item.getRearBitmap());
                    flipView.setOnClickListener(view -> {
                        if (!flipView.isFlipped()) {
                            flipView.flip(true);
                        } else {
                            flipView.flip(false);
                        }
                /*if (!flipView.isEnabled()){
                    flipView.setEnabled(true);
                } else {
                    flipView.setEnabled(false);
                }*/
                /*if (!flipView.isClickable()){
                    flipView.setClickable(true);
                } else {
                    flipView.setClickable(false);
                }*/
                    });
                }
                flipView.flip(false);
            } else {
                this.cardLayout.setVisibility(View.VISIBLE);
                this.nameBusiness.setText(item.getAgentes().getNombreNegocio());
            }
        }

    }

    public void setStatus(Bitmap front ){
        flipView.setFrontImageBitmap(front);
    }

    public void resetFlip() {
        flipView.flip(false);
    }

    public void setEneable(boolean check) {
        flipView.setEnabled(check);
    }


    @Override
    public void inflate(ViewGroup layout) {

    }

    @Override
    public View getView() {
        return null;
    }
}
