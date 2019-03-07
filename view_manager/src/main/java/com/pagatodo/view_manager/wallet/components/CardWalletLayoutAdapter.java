package com.pagatodo.view_manager.wallet.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.wallet.holders.BankCardHolderView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.pagatodo.view_manager.wallet.components.CardWalletLinearLayout.BIG_SCALE;
import static com.pagatodo.view_manager.wallet.components.CardWalletLinearLayout.DIFF_SCALE;

public class CardWalletLayoutAdapter extends PagerAdapter implements ViewPager.PageTransformer{


    private ArrayList<CardData> listWallet;
    private OnHolderListener<CardData> listener;

    public CardWalletLayoutAdapter() {
        this.listWallet = new ArrayList<>();
    }

    public void setListWallet(ArrayList<CardData> listWallet) {
        this.listWallet = listWallet;
        this.notifyDataSetChanged();
    }

    public CardData getWallet(int position){
        return listWallet.get(position);
    }

    public void setListener(OnHolderListener<CardData> listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return this.listWallet.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(collection.getContext());
        boolean isZero = true;
        if (position != 0) isZero = false;
        View layout  = inflater.inflate(R.layout.card_layout,collection,false);
        BankCardHolderView bankCardHolderView = new BankCardHolderView(layout,isZero);
        bankCardHolderView.bind(this.listWallet.get(position),listener);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        CardWalletLinearLayout cardLinearLayout = page.findViewById(R.id.root);
        float scale = BIG_SCALE;
        if (position > 0) {
            scale = scale - position * DIFF_SCALE;
        } else {
            scale = scale + position * DIFF_SCALE;
        }
        if (scale < 0) scale = 0;
        cardLinearLayout.setScaleBoth(scale);
    }

}
