package com.pagatodo.yaganaste.ui_wallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._adapters.CoachMarkAdpater;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarPositionActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.CardWalletAdpater;
import com.pagatodo.yaganaste.utils.customviews.YaGanasteCard;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletMainActivity extends ToolBarPositionActivity {

    @BindView(R.id.wallet_zone)
    LinearLayout walletZone;
    @BindView(R.id.swipe_zone)
    LinearLayout swipeZone;
    @BindView(R.id.viewpager_wallet)
    ViewPager viewPagerWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        walletZone.setVisibility(View.VISIBLE);
        swipeZone.setVisibility(View.GONE);
        viewPagerWallet.setAdapter(new CoachMarkAdpater(this,getList()));
        viewPagerWallet.setCurrentItem(0);
        viewPagerWallet.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private int[] getList(){
        int[] list = {R.drawable.card_yaganaste,R.drawable.img_couch_em_1,R.drawable.img_couch_em_2};

        return list;
    }
    @Override
    public boolean requiresTimer() {
        return false;
    }
}
