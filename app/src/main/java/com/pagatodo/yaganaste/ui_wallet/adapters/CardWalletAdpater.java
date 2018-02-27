package com.pagatodo.yaganaste.ui_wallet.adapters;


import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by icruz on 11/12/2017.
 */

public class CardWalletAdpater extends PagerAdapter implements CardAdapter{


    private ArrayList<ElementWallet> elementViewList;
    private float mBaseElevation;
    private List<CardView> mViews;

    public CardWalletAdpater() {
        elementViewList = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void setEmptyList(){
        this.elementViewList = new ArrayList<>();
        this.mViews = new ArrayList<>();
    }
    public void addCardItem(ElementWallet item) {
        mViews.add(null);
        elementViewList.add(item);
    }

    public ArrayList<ElementView> getElementWallet(int position){
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
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
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
        imageViewCard.setImageResource(item.getResourceCard());

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

    public void updateSaldo(int position, String saldo){
        elementViewList.get(position).setSaldo(saldo);
    }
}
