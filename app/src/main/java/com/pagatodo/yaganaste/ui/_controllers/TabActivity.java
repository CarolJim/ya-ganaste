package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.account.register.LandingFragment;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.TabPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.MainMenuPresenterImp;
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;

import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_EMISOR;


public class TabActivity extends SupportFragmentActivity implements TabsView {
    private Preferencias pref;

    private ViewPager mainViewPager;
    private TabLayout mainTab;
    private TabPresenter tabPresenter;

    public static Intent createIntent(Context from) {
        return new Intent(from, TabActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_tab);
        load();

        if(!pref.containsData(COUCHMARK_EMISOR)){
            pref.saveDataBool(COUCHMARK_EMISOR,true);
            Intent intent = new Intent(this, LandingFragment.class);
            startActivity(intent);
        }

    }

    private void load() {
        this.tabPresenter = new MainMenuPresenterImp(this);
        pref = App.getInstance().getPrefs();
        mainViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        mainTab = (TabLayout) findViewById(R.id.main_tab);
        tabPresenter.getPagerData(ViewPagerDataFactory.TABS.MAIN);
    }

    @Override
    public void loadViewPager(ViewPagerData viewPagerData) {
        mainViewPager.setAdapter(new GenericPagerAdapter<>(this, getSupportFragmentManager(), viewPagerData.getFragmentList(), viewPagerData.getTabData()));
        mainTab.setupWithViewPager(mainViewPager);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Glide.with(this).load(FlowUser.getImageProfileURL() + "?" + UtilsExtensions.getCurrentTime()).placeholder(R.mipmap.icon_user).dontAnimate().error(R.mipmap.icon_user).into(imgToolbarProfile);
    }

}
