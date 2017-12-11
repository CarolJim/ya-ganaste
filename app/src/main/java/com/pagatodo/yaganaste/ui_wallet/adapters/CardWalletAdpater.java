package com.pagatodo.yaganaste.ui_wallet.adapters;


import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.customviews.YaGanasteCard;

import java.util.ArrayList;

/**
 * Created by icruz on 11/12/2017.
 */

public class CardWalletAdpater extends PagerAdapter {

    private ArrayList<YaGanasteCard> list;
    private YaGanasteCard cardBalanceAdq;

    public CardWalletAdpater(ArrayList<YaGanasteCard> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.card_wallet_adapter, container, false);
        container.addView(view);

        cardBalanceAdq = list.get(position);

        return view;

    }
}
