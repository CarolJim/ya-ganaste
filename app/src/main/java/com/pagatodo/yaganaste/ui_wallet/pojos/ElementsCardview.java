package com.pagatodo.yaganaste.ui_wallet.pojos;

import android.support.v7.widget.CardView;

/**
 * Created by icruz on 18/12/2017.
 */

public class ElementsCardview {

    private CardView cardView;
    private String saldos;

    public ElementsCardview(CardView cardView, String saldos) {
        this.cardView = cardView;
        this.saldos = saldos;
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public String getSaldos() {
        return saldos;
    }

    public void setSaldos(String saldos) {
        saldos = saldos;
    }
}
