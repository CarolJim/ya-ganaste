package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.LandingFragment;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.MainMenuPresenterImp;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.TabPresenter;
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;

import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_EMISOR;


public class TabActivity extends ToolBarActivity implements TabsView, OnEventListener<Object> {
    private Preferencias pref;

    private ViewPager mainViewPager;
    private TabLayout mainTab;
    private TabPresenter tabPresenter;

    public static final String EVENT_ADQUIRENTE_SELECTED = "1";
    public static final String EVENT_GO_HOME = "2";
    public static final String EVENT_CHANGE_MAIN_TAB_VISIBILITY = "3";

    public static Intent createIntent(Context from) {
        return new Intent(from, TabActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mainViewPager.setOffscreenPageLimit(viewPagerData.getTabData().length - 1);
        mainTab.setupWithViewPager(mainViewPager);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Glide.with(this).load(FlowUser.getImageProfileURL() + "?" + UtilsExtensions.getCurrentTime()).placeholder(R.mipmap.icon_user).dontAnimate().error(R.mipmap.icon_user).into(imgToolbarProfile);
    }

    @Override
    public void onEvent(String event, Object data) {
        if (event.equals(EVENT_ADQUIRENTE_SELECTED)) {
            onAdquirenteSelected((boolean)data);
        } else if (event.equals(ToolBarActivity.EVENT_CHANGE_TOOLBAR_VISIBILITY)) {
            changeToolbarVisibility((boolean)data);
        } else if (event.equals(EVENT_GO_HOME)) {
            goHome();
        } else if (event.equals(EVENT_CHANGE_MAIN_TAB_VISIBILITY)) {
            mainTab.setVisibility((boolean)data ? View.VISIBLE : View.GONE);
        }
    }

    private void onAdquirenteSelected(boolean isAdquirente) {
        if (!isAdquirente) {
            TabLayout.Tab current = mainTab.getTabAt(mainTab.getTabCount() - 1);
            if (current != null) {
                current.select();
            }
        }
    }

    private void goHome() {
        TabLayout.Tab current = mainTab.getTabAt(0);
        if (current != null) {
            current.select();
        }
    }
}
