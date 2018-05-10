package com.pagatodo.yaganaste.ui_wallet.holders;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IBalanceView;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ICardBalance;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;

import eu.davidea.flipview.FlipView;

import static com.pagatodo.yaganaste.utils.Recursos.DEBUG;

public class WalletViewHolder extends GenericHolder {

    private FlipView flipView;
    private ICardBalance listener;

    public WalletViewHolder(View itemView,ICardBalance listener) {
        super(itemView);
        this.listener = listener;
        init();
    }

    @Override
    public void init() {
        this.flipView = itemView.findViewById(R.id.cardflip_element);
        FlipView.enableLogs(DEBUG);
    }

    @Override
    public void bind(Object elementWallet, @Nullable OnClickItemHolderListener listener) {
        ElementWallet item = (ElementWallet) elementWallet;
        this.flipView.setFrontImage(item.getResourceCard());
        if (item.getBitmap() != null) {
            this.flipView.setRearImageBitmap(item.getBitmap());
            this.flipView.setOnClickListener(view -> {

                if (!this.flipView.isFlipped()) {
                    this.flipView.flip(true);
                } else {
                    this.flipView.flip(false);
                }

            });

        }
        this.flipView.flip(false);

    }

    public void resetFlip(){
        this.flipView.flip(false);
    }

    @Override
    public void inflate(ViewGroup layout) {

    }

    @Override
    public View getView() {
        return null;
    }
}
