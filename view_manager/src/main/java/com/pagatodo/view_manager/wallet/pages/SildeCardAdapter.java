package com.pagatodo.view_manager.wallet.pages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.LauncherView;
import com.pagatodo.view_manager.wallet.data.ReaderDeviceData;
import com.pagatodo.view_manager.wallet.data.Wallet;
import com.pagatodo.view_manager.wallet.data.WalletFactory;
import com.pagatodo.view_manager.wallet.data.YaGanasteCardData;
import com.pagatodo.view_manager.wallet.holders.ReaderDeviceCardView;
import com.pagatodo.view_manager.wallet.holders.YaGanastaCardHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SildeCardAdapter extends PagerAdapter {

    private ArrayList<Wallet> wallets;
    private WalletFactory walletFactory;

    SildeCardAdapter() {
        this.walletFactory = new WalletFactory();
        this.wallets = new ArrayList<>();
    }

    void setWallets(ArrayList<Wallet> wallets) {
        this.wallets = wallets;
    }

    @Override
    public int getCount() {
        return wallets.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        //LauncherHolder<Object> holder;
        LauncherView launcherView;
        switch (wallets.get(position).getWalletType()){
            case BANK:
                //walletFactory.getWalletView()
                View rootView = inflater.inflate(R.layout.ya_ganaste_card,container,false);
                //(YaGanastaCardHolder<>) holder = new YaGanastaCardHolder(rootView);
                YaGanastaCardHolder holder = new YaGanastaCardHolder(rootView);
                holder.bind((YaGanasteCardData) wallets.get(position).getObject(),null);
                holder.inflate(container);
                return rootView;
            case CARD_READER:
                rootView = inflater.inflate(R.layout.reader_card,container,false);
                launcherView = new ReaderDeviceCardView((ReaderDeviceData) wallets.get(position)
                                .getObject(),rootView);
                launcherView.bind();
                launcherView.inflate(container);
                return rootView;
                default:
                    rootView = inflater.inflate(R.layout.ya_ganaste_card,container,false);
                    return rootView;
        }

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


}
