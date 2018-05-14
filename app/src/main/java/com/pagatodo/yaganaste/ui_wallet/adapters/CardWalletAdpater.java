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
import com.pagatodo.yaganaste.interfaces.IBalanceView;
import com.pagatodo.yaganaste.ui_wallet.holders.WalletViewHolder;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ICardBalance;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.utils.customviews.YaGanasteCard;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flipview.FlipView;

import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.FULL_NAME_USER;
import static com.pagatodo.yaganaste.utils.StringUtils.ocultarCardNumber;


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
        //this.listHolder.add(null);
        this.elementViewList.add(item);

    }

    public int getSize() {
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
        WalletViewHolder holder = new WalletViewHolder(view, this.listener);
        holder.bind(elementViewList.get(position % elementViewList.size()), null);
        container.addView(view);
        this.listHolder.add(holder);
        return view;

    }

    public void resetFlip() {
        for (WalletViewHolder holder : this.listHolder) {
            holder.resetFlip();
        }
        this.notifyDataSetChanged();
    }

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
