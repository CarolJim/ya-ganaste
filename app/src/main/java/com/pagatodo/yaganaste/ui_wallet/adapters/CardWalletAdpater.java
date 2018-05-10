package com.pagatodo.yaganaste.ui_wallet.adapters;


import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.holders.WalletViewHolder;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ICardBalance;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.utils.customviews.YaGanasteCard;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.FULL_NAME_USER;
import static com.pagatodo.yaganaste.utils.StringUtils.ocultarCardNumber;


/**
 * Created by icruz on 11/12/2017.
 */

public class CardWalletAdpater extends PagerAdapter  {

    public static int LOOPS_COUNT = 100;
    private ArrayList<ElementWallet> elementViewList;

    public CardWalletAdpater() {
        this.elementViewList = new ArrayList<>();

    }

    public void addCardItem(ElementWallet item) {
        elementViewList.add(item);
    }

    public int getSize(){
        return elementViewList.size();
    }

    @Override
    public int getCount() {
        return LOOPS_COUNT;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.wallet_element, container, false);

        WalletViewHolder holder = new WalletViewHolder(view);
        holder.bind(elementViewList.get(position % elementViewList.size()),null);
        container.addView(view);
        return view;

    }

    /*private void bind(ElementWallet item, View view) {
        ImageView imageViewCard = view.findViewById(R.id.imageview_card);
        YaGanasteCard yaGanasteCard = view.findViewById(R.id.yg_card_tab_wallet);
        if (item.getResourceCard() == R.drawable.tarjeta_yg || item.getResourceCard() == R.mipmap.main_card_zoom_gray) {
            yaGanasteCard.setVisibility(View.VISIBLE);
            imageViewCard.setVisibility(View.GONE);
            String cardNumber = App.getInstance().getPrefs().loadData(CARD_NUMBER);
            yaGanasteCard.setCardNumber(ocultarCardNumber(cardNumber));
            yaGanasteCard.setImageResource(item.getResourceCard());
            yaGanasteCard.setCardName(App.getInstance().getPrefs().loadData(FULL_NAME_USER));
        } else {
            yaGanasteCard.setVisibility(View.GONE);
            imageViewCard.setVisibility(View.VISIBLE);
            imageViewCard.setImageResource(item.getResourceCard());
        }
    }*/

    public ElementWallet getElemenWallet(int position) {
        return this.elementViewList.get(position%elementViewList.size());
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);

    }
/*
    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position%mViews.size());
    }
*/
    public void updateSaldo(int position, String saldo) {
        elementViewList.get(position%elementViewList.size()).setSaldo(saldo);
    }


}
