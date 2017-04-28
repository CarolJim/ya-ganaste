package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.adquirente.Documentos;
import com.pagatodo.yaganaste.ui.account.register.LandingFragment;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.fragments.HomeTabFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsTabFragment;
import com.pagatodo.yaganaste.ui.maintabs.presenters.MainMenuPresenterImp;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.TabPresenter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;

import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_EMISOR;


public class TabActivity extends ToolBarActivity implements TabsView, OnEventListener {
    private Preferencias pref;

    private ViewPager mainViewPager;
    private TabLayout mainTab;
    private TabPresenter tabPresenter;

    public static final String EVENT_INVITE_ADQUIRENTE = "1";
    public static final String EVENT_GO_HOME = "2";
    public static final String EVENT_CHANGE_MAIN_TAB_VISIBILITY = "3";

    public static final String EVENT_HIDE_MANIN_TAB = "eventhideToolbar";
    public static final String EVENT_SHOW_MAIN_TAB = "eventShowToolbar";
    private Animation animShow, animHide;
    private GenericPagerAdapter<IEnumTab> mainViewPagerAdapter;

    public static Intent createIntent(Context from) {
        return new Intent(from, TabActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        load();

        if (!pref.containsData(COUCHMARK_EMISOR)) {
            pref.saveDataBool(COUCHMARK_EMISOR, true);
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
        animHide = AnimationUtils.loadAnimation(this, R.anim.view_hide);
        animShow = AnimationUtils.loadAnimation(this, R.anim.view_show);
    }

    @Override
    public void loadViewPager(ViewPagerData viewPagerData) {
        mainViewPagerAdapter = new GenericPagerAdapter<>(this, getSupportFragmentManager(), viewPagerData.getFragmentList(), viewPagerData.getTabData());
        mainViewPager.setAdapter(mainViewPagerAdapter);
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
        if (event.equals(EVENT_INVITE_ADQUIRENTE)) {
            onInviteAdquirente();
        } else if (event.equals(ToolBarActivity.EVENT_CHANGE_TOOLBAR_VISIBILITY)) {
            changeToolbarVisibility((boolean) data);
        } else if (event.equals(EVENT_GO_HOME)) {
            goHome();
        } else if (event.equals(EVENT_CHANGE_MAIN_TAB_VISIBILITY)) {
            mainTab.setVisibility((boolean) data ? View.VISIBLE : View.GONE);
        } else if (event.equals(EVENT_HIDE_MANIN_TAB)) {
            if (mainTab.getVisibility() == View.VISIBLE) {
                mainTab.startAnimation(animHide);
                mainTab.setVisibility(View.GONE);
            }
        } else if (event.equals(EVENT_SHOW_MAIN_TAB)) {
            if (mainTab.getVisibility() == View.GONE) {
                mainTab.setVisibility(View.VISIBLE);
                mainTab.startAnimation(animShow);
            }
        }
    }

    private void onInviteAdquirente() {
        TabLayout.Tab current = mainTab.getTabAt(mainTab.getTabCount() - 1);
        if (current != null) {
            current.select();
        }
    }

    private void goHome() {
        TabLayout.Tab current = mainTab.getTabAt(0);
        if (current != null) {
            current.select();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        if (requestCode == Constants.CONTACTS_CONTRACT || requestCode == Constants.BARCODE_READER_REQUEST_CODE) {
            if (fragmentList != null) {
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof PaymentsTabFragment) {
                        fragment.onActivityResult(requestCode, resultCode, data);
                        break;
                    }
                }
            }
        }else if(requestCode == Documentos.REQUEST_TAKE_PHOTO || requestCode == Documentos.SELECT_FILE_PHOTO){
            if (fragmentList != null) {
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof Documentos) {
                        fragment.onActivityResult(requestCode, resultCode, data);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(mainViewPagerAdapter.getItem(mainViewPager.getCurrentItem()) instanceof  PaymentsTabFragment){
            PaymentsTabFragment paymentsTabFragment = (PaymentsTabFragment)mainViewPagerAdapter.getItem(mainViewPager.getCurrentItem());
            if(paymentsTabFragment.isOnForm){
                paymentsTabFragment.onBackPresed(paymentsTabFragment.getCurrenTab());
            }else{
                goHome();
            }
        }else {
            if(mainViewPagerAdapter.getItem(mainViewPager.getCurrentItem()) instanceof HomeTabFragment){
                super.onBackPressed();
            }else{
                goHome();
            }
        }

    }
}
