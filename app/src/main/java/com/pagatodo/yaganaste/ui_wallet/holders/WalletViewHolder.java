package com.pagatodo.yaganaste.ui_wallet.holders;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.utils.customviews.YaGanasteCard;

import java.util.List;

import static com.pagatodo.yaganaste.ui_wallet.adapters.CardAdapter.MAX_ELEVATION_FACTOR;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.FULL_NAME_USER;
import static com.pagatodo.yaganaste.utils.StringUtils.ocultarCardNumber;

public class WalletViewHolder extends GenericHolder {

    private ImageView imageCard;
    private YaGanasteCard yaGanasteCard;
    private CardView cardView;
    private float mBaseElevation;

    public WalletViewHolder(View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        this.cardView = itemView.findViewById(R.id.cardView);
        this.imageCard = itemView.findViewById(R.id.imageview_card);
        this.yaGanasteCard = itemView.findViewById(R.id.yg_card_tab_wallet);
    }

    public void bind(Object item, OnClickItemHolderListener listener, List<CardView> list, float mBaseElevation){
        this.mBaseElevation = mBaseElevation;
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        this.bind(item,listener);
    }
    @Override
    public void bind(Object item, @Nullable OnClickItemHolderListener listener) {
        ElementWallet elementWallet = (ElementWallet) item;

        if (elementWallet.getResourceCard() == R.drawable.tarjeta_yg || elementWallet.getResourceCard() == R.mipmap.main_card_zoom_gray) {
            yaGanasteCard.setVisibility(View.VISIBLE);
            imageCard.setVisibility(View.GONE);
            String cardNumber = App.getInstance().getPrefs().loadData(CARD_NUMBER);
            yaGanasteCard.setCardNumber(ocultarCardNumber(cardNumber));
            yaGanasteCard.setImageResource(elementWallet.getResourceCard());
            yaGanasteCard.setCardName(App.getInstance().getPrefs().loadData(FULL_NAME_USER));
        } else {
            yaGanasteCard.setVisibility(View.GONE);
            imageCard.setVisibility(View.VISIBLE);
            imageCard.setImageResource(elementWallet.getResourceCard());
        }
        this.itemView.setOnClickListener(view -> listener.onClick(elementWallet));
    }

    @Override
    public void inflate(ViewGroup layout) {

    }

    @Override
    public View getView() {
        return null;
    }
}
