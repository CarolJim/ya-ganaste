package com.pagatodo.view_manager.wallet.pages;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.wallet.data.ReaderDeviceData;
import com.pagatodo.view_manager.wallet.data.Wallet;
import com.pagatodo.view_manager.wallet.data.WalletType;
import com.pagatodo.view_manager.wallet.data.YaGanasteCardData;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class SlideCard extends LinearLayout {

    public SlideCard(Context context) {
        super(context);
        init();
    }

    public SlideCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View viewItem = inflater.inflate(R.layout.slide_card,this,false);
        ViewPager viewPager = viewItem.findViewById(R.id.view_page);
        //int pagerPadding = 100;
        viewPager.setClipToPadding(false);
        viewPager.setOffscreenPageLimit(2);
        //viewPager.setPadding(pagerPadding, 0, pagerPadding, 0);*/
        SildeCardAdapter adapter = new SildeCardAdapter();
        ArrayList<Wallet> wallets = new ArrayList<>();
        wallets.add(Wallet.create(YaGanasteCardData.createDeafult(), WalletType.BANK));
        wallets.add(Wallet.create(ReaderDeviceData.create(getContext()
                .getResources().getDrawable(R.drawable.main_card_zoom_blue)),WalletType.CARD_READER));
        viewPager.setAdapter(adapter);
        adapter.setWallets(wallets);
        adapter.notifyDataSetChanged();
        this.addView(viewItem);
    }
}
