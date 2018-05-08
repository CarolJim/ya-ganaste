package com.pagatodo.yaganaste.ui_wallet.adapters;


import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.utils.customviews.YaGanasteCard;


import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.FULL_NAME_USER;
import static com.pagatodo.yaganaste.utils.StringUtils.getCreditCardFormat;
import static com.pagatodo.yaganaste.utils.StringUtils.ocultarCardNumberFormat;

/**
 * Created by icruz on 11/12/2017.
 */

public class CardWalletAdpater extends PagerAdapter implements CardAdapter {


    private ArrayList<ElementWallet> elementViewList;
    private float mBaseElevation;
    private List<CardView> mViews;

    public CardWalletAdpater() {
        elementViewList = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(ElementWallet item) {
        mViews.add(null);
        elementViewList.add(item);
    }

    public ArrayList<ElementView> getElementWallet(int position) {
        return this.elementViewList.get(position).getElementViews();
    }

    @Override
    public int getCount() {
        return this.elementViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.wallet_element, container, false);
        container.addView(view);
        bind(elementViewList.get(position), view);
        CardView cardView = view.findViewById(R.id.cardView);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }
        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        cardView.setPreventCornerOverlap(false);
        mViews.set(position, cardView);
        return view;

    }

    private void bind(ElementWallet item, View view) {
        ImageView imageViewCard = view.findViewById(R.id.imageview_card);
        YaGanasteCard yaGanasteCard = view.findViewById(R.id.yg_card_tab_wallet);
        if (item.getResourceCard() == R.drawable.tarjeta_yg || item.getResourceCard() == R.mipmap.main_card_zoom_gray) {
            yaGanasteCard.setVisibility(View.VISIBLE);
            imageViewCard.setVisibility(View.GONE);
            String cardNumber = App.getInstance().getPrefs().loadData(CARD_NUMBER);
            yaGanasteCard.setCardNumber(getCreditCardFormat(cardNumber));
            yaGanasteCard.setImageResource(item.getResourceCard());
            yaGanasteCard.setCardName(App.getInstance().getPrefs().loadData(FULL_NAME_USER));
        } else {
            yaGanasteCard.setVisibility(View.GONE);
            imageViewCard.setVisibility(View.VISIBLE);
            imageViewCard.setImageResource(item.getResourceCard());
        }

    }

    public ElementWallet getElemenWallet(int position) {
        return this.elementViewList.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView((LinearLayout) object);
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    public void updateSaldo(int position, String saldo) {
        elementViewList.get(position).setSaldo(saldo);
    }


}
