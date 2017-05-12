package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonSession;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.account.register.LandingFragment;
import com.pagatodo.yaganaste.ui.adquirente.Documentos;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.fragments.HomeTabFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentFormBaseFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsTabFragment;
import com.pagatodo.yaganaste.ui.maintabs.presenters.MainMenuPresenterImp;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.TabPresenter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;

import java.util.List;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_GET_CARD;
import static com.pagatodo.yaganaste.utils.Constants.BACK_FROM_PAYMENTS;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;
import static com.pagatodo.yaganaste.utils.Constants.MESSAGE;
import static com.pagatodo.yaganaste.utils.Constants.RESULT;
import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;


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
        if (!pref.containsData(COUCHMARK_ADQ) && SingletonUser.getInstance().getDataUser().isEsAgente()) {
            pref.saveDataBool(COUCHMARK_ADQ, true);
       /*     if (SingletonUser.getInstance().getDataUser().isEsAgente()
                    && SingletonUser.getInstance().getDataUser().getEstatusAgente() == CRM_DOCTO_APROBADO) {*/
                Intent intent = new Intent(this, LandingAdqFragment.class);
                startActivity(intent);
         /*   }*/
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

        Log.e("TabActivity", "indicator position " + mainTab.getSelectedTabPosition());
        mainTab.setSelectedTabIndicatorHeight(2);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Glide.with(this).load(FlowUser.getImageProfileURL() + "?" + UtilsExtensions.getCurrentTime()).placeholder(R.mipmap.icon_user).dontAnimate().error(R.mipmap.icon_user).into(imgToolbarProfile);
    }

    @Override
    protected void onStart() {
        super.onStart();

         if (!pref.containsData(COUCHMARK_EMISOR)) {

             new Handler().postDelayed(new Runnable() {
                 public void run() {
                     pref.saveDataBool(COUCHMARK_EMISOR, true);
                     Intent intent = new Intent(TabActivity.this, LandingFragment.class);
                     startActivity(intent);
                 }
             }, 500);


        }
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
        if (requestCode == Constants.CONTACTS_CONTRACT
                || requestCode == Constants.BARCODE_READER_REQUEST_CODE
                || requestCode == BACK_FROM_PAYMENTS) {

            Fragment childFragment = getFragment(0);
            if (childFragment != null && requestCode != BACK_FROM_PAYMENTS) {
                childFragment.onActivityResult(requestCode, resultCode, data);
            } else if (childFragment != null && requestCode == BACK_FROM_PAYMENTS) {
                if (data != null && data.getStringExtra(RESULT) != null && data.getStringExtra(RESULT).equals(Constants.RESULT_ERROR)) {
                    if (childFragment != null) {
                        UI.createSimpleCustomDialog("Error!", data.getStringExtra(MESSAGE), getSupportFragmentManager(), getLocalClassName());
                        PaymentFormBaseFragment paymentFormBaseFragment = getVisibleFragment(childFragment.getChildFragmentManager().getFragments());
                        if (paymentFormBaseFragment != null) {
                            paymentFormBaseFragment.setSeekBarProgress(0);
                        }
                    }
                } else {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        } else if (requestCode == Documentos.REQUEST_TAKE_PHOTO || requestCode == Documentos.SELECT_FILE_PHOTO) {
            if (getFragment(1) != null) {
                getFragment(1).onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    protected PaymentFormBaseFragment getVisibleFragment(List<Fragment> fragmentList) {
        for (Fragment fragment2 : fragmentList) {
            if (fragment2 instanceof PaymentFormBaseFragment) {
                if (fragment2.isVisible()) {
                    return ((PaymentFormBaseFragment) fragment2);
                }
            }
        }
        return null;
    }

    protected Fragment getFragment(int fragmentType) {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        Fragment response = null;
        if (fragmentList != null) {
            for (Fragment fragment : fragmentList) {
                if (fragmentType == 0) {
                    if (fragment instanceof PaymentsTabFragment) {
                        response = fragment;
                        break;
                    }
                } else if (fragmentType == 1) {
                    if (fragment instanceof Documentos) {
                        response = fragment;
                        break;
                    }
                }
            }
        }
        return response;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (mainViewPagerAdapter.getItem(mainViewPager.getCurrentItem()) instanceof PaymentsTabFragment) {
            PaymentsTabFragment paymentsTabFragment = (PaymentsTabFragment) mainViewPagerAdapter.getItem(mainViewPager.getCurrentItem());
            if (paymentsTabFragment.isOnForm) {
                paymentsTabFragment.onBackPresed(paymentsTabFragment.getCurrenTab());
            } else {
                goHome();
            }
        } else {
            if (mainViewPagerAdapter.getItem(mainViewPager.getCurrentItem()) instanceof HomeTabFragment) {
                super.onBackPressed();
                SingletonSession.getInstance().setFinish(true);//Terminamos Activity si va a background
            } else {
                goHome();
            }
        }

    }


    @Override
    protected void onPause() {
        super.onResume();
        if (pref.containsData(COUCHMARK_EMISOR) && SingletonSession.getInstance().isFinish()) {
            SingletonSession.getInstance().setFinish(false);
            finish();
        }
    }

}
