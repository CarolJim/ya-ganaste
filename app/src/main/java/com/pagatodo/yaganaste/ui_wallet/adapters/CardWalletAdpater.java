package com.pagatodo.yaganaste.ui_wallet.adapters;


import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.holders.WalletViewHolder;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ICardBalance;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;

import java.util.ArrayList;


/**
 * Created by icruz on 11/12/2017.
 */

public class CardWalletAdpater extends PagerAdapter {

    public static int LOOPS_COUNT = 100;
    private ArrayList<ElementWallet> elementViewList;
    private ArrayList<WalletViewHolder> listHolder;
    private ICardBalance listener;

    public CardWalletAdpater() {
        this.elementViewList = new ArrayList<>();
        this.listHolder = new ArrayList<>();

    }

    public void setListener(ICardBalance listener) {
        this.listener = listener;
    }

    public void addCardItem(ElementWallet item) {
        this.listHolder.add(null);
        this.elementViewList.add(item);

    }

    public int getSize() {
        return elementViewList.size();
    }
    public int getSizeHolder() {
        return this.listHolder.size();
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
        WalletViewHolder holder = new WalletViewHolder(view, this.listener);
        holder.bind(elementViewList.get(position % elementViewList.size()), null);
        container.addView(view);
        this.listHolder.add(holder);
        return view;

    }

    public void resetFlip() {
        for (int i = 0; i < this.listHolder.size(); i++) {
            if (this.listHolder.get(i) != null) {
                this.listHolder.get(i).resetFlip();
            }
        }
        this.notifyDataSetChanged();
    }

    public void disableOnlcik() {
        for (WalletViewHolder holder : this.listHolder) {
            if (holder != null) {
                holder.setEneable(false);
            }
        }

        this.notifyDataSetChanged();
    }

    public void eneableOnclick(int position){
        //if (this.listHolder.get(position) != null) {
            this.listHolder.get(position % elementViewList.size()).setEneable(true);
        //}
    }

    /*public void enenable(int position){
        for (int i = 0; i < this.listHolder.size(); i++) {
            if (i != position) {
                this.listHolder.get(i).setEneable(true);
            }
        }
    }*/

    public ElementWallet getElemenWallet(int position) {
        return this.elementViewList.get(position % elementViewList.size());
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);

    }

    public void updateSaldo(int position, String saldo) {
        elementViewList.get(position % elementViewList.size()).setSaldo(saldo);
    }
}
