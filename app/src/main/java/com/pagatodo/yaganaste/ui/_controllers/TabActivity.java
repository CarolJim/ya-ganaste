package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonSession;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarPositionActivity;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DocumentosFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.GetMountFragment;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DocumentsContainerFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.HomeTabFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentFormBaseFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsTabFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.DepositsFragment;
import com.pagatodo.yaganaste.ui.maintabs.presenters.MainMenuPresenterImp;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.TabPresenter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import java.util.List;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment.CODE_CANCEL;
import static com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment.RESULT_CANCEL_OK;
import static com.pagatodo.yaganaste.utils.Constants.BACK_FROM_PAYMENTS;
import static com.pagatodo.yaganaste.utils.Constants.MESSAGE;
import static com.pagatodo.yaganaste.utils.Constants.REGISTER_ADQUIRENTE_CODE;
import static com.pagatodo.yaganaste.utils.Constants.RESULT;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_BACK_PRESS;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_FAIL;
import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.PTH_DOCTO_APROBADO;


public class TabActivity extends ToolBarPositionActivity implements TabsView, OnEventListener {
    public static final String EVENT_INVITE_ADQUIRENTE = "1";
    public static final String EVENT_DOCUMENT_APPROVED = "EVENT_DOCUMENT_APPROVED";
    public static final String EVENT_GO_HOME = "2";
    public static final String EVENT_CHANGE_MAIN_TAB_VISIBILITY = "3";
    public static final String EVENT_HIDE_MANIN_TAB = "eventhideToolbar";
    public static final String EVENT_SHOW_MAIN_TAB = "eventShowToolbar";
    public static final int RESULT_ADQUIRENTE_SUCCESS = 4573;
    public static final int TYPE_DETAILS = 3;
    private Preferencias pref;
    private ViewPager mainViewPager;
    private TabLayout mainTab;
    private TabPresenter tabPresenter;
    private Animation animShow, animHide;
    private GenericPagerAdapter<IEnumTab> mainViewPagerAdapter;
    private ProgressLayout progressGIF;

    final int[] drawablesEmisor = {R.drawable.img_couch_em_1, R.drawable.img_couch_em_2,
            R.drawable.img_couch_em_3, R.drawable.img_couch_em_4, R.drawable.img_couch_em_5, R.drawable.img_couch_em_6};
    final int[] drawablesAdquirente = {R.drawable.coachmark_adquirente_1, R.drawable.coachmark_adquirente_2};

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
            startActivityForResult(LandingActivity.createIntent(this, R.drawable.img_couch_em_back, drawablesEmisor), Constants.ACTIVITY_LANDING);
        }

        System.gc();
    }

    private void load() {
        this.tabPresenter = new MainMenuPresenterImp(this);
        pref = App.getInstance().getPrefs();
        mainViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        mainTab = (TabLayout) findViewById(R.id.main_tab);
        progressGIF = (ProgressLayout) findViewById(R.id.progressGIF);
        progressGIF.setVisibility(View.GONE);

        tabPresenter.getPagerData(ViewPagerDataFactory.TABS.MAIN_TABS);
        animHide = AnimationUtils.loadAnimation(this, R.anim.view_hide);
        animShow = AnimationUtils.loadAnimation(this, R.anim.view_show);
    }

    @Override
    public void loadViewPager(ViewPagerData viewPagerData) {
        mainViewPagerAdapter = new GenericPagerAdapter<>(this, getSupportFragmentManager(),
                viewPagerData.getFragmentList(), viewPagerData.getTabData());

        mainViewPager.setAdapter(mainViewPagerAdapter);
        mainViewPager.setOffscreenPageLimit(viewPagerData.getTabData().length - 1);
        mainTab.setupWithViewPager(mainViewPager);
        mainTab.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        LinearLayout linearLayout = (LinearLayout) mainTab.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(ContextCompat.getColor(this, R.color.colorGrayMenuDivider));
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(0);
        linearLayout.setDividerDrawable(drawable);
        mainTab.setSelectedTabIndicatorHeight(4);
        mainTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                /*if (tab.getPosition() == 3) {
                    hideMainTab();
                } else {
                    showMainTab();
                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!pref.containsData(COUCHMARK_EMISOR)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pref.saveDataBool(COUCHMARK_EMISOR, true);
                    startActivityForResult(LandingActivity.createIntent(TabActivity.this, R.drawable.img_couch_em_back, drawablesEmisor), Constants.ACTIVITY_LANDING);
                }
            }, 500);
        }
    }


    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        if (event.equals(EVENT_INVITE_ADQUIRENTE)) {
            onInviteAdquirente();
        } else if (event.equals(ToolBarActivity.EVENT_CHANGE_TOOLBAR_VISIBILITY)) {
            changeToolbarVisibility((boolean) data);
        } else if (event.equals(EVENT_GO_HOME)) {
            goHome();
        } else if (event.equals(EVENT_CHANGE_MAIN_TAB_VISIBILITY)) {
            if ((boolean) data) {
                showMainTab();
            } else {
                hideMainTab();
            }
        } else if (event.equals(EVENT_HIDE_MANIN_TAB)) {
            hideMainTab();
        } else if (event.equals(EVENT_SHOW_MAIN_TAB)) {
            showMainTab();
        } else if (event.equals(EVENT_DOCUMENT_APPROVED)) {
            // Toast.makeText(getApplicationContext(), "Load FRagment New", Toast.LENGTH_SHORT).show();
            //DocumentsContainerFragment mFragment = mainViewPager.findViewById(R.id)
            DocumentsContainerFragment mFragment = (DocumentsContainerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.main_view_pager);
            mFragment.loadApprovedFragment();

        }
    }

    protected void hideMainTab() {
        if (mainTab.getVisibility() == View.VISIBLE) {
            mainTab.setVisibility(View.GONE);
        }
    }

    protected void showMainTab() {
        if (mainTab.getVisibility() == View.GONE) {
            mainTab.setVisibility(View.VISIBLE);
        }
    }

    private void onInviteAdquirente() {
        TabLayout.Tab current = mainTab.getTabAt(mainTab.getTabCount() - 1);
        if (current != null) {
            current.select();
        }
    }

    public void goHome() {
        TabLayout.Tab current = mainTab.getTabAt(0);
        if (current != null) {
            current.select();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CONTACTS_CONTRACT
                || requestCode == Constants.BARCODE_READER_REQUEST_CODE
                || requestCode == BACK_FROM_PAYMENTS) {

            Fragment childFragment = getFragment(0);
            if (childFragment != null && requestCode != BACK_FROM_PAYMENTS) {
                childFragment.onActivityResult(requestCode, resultCode, data);
            } else if (childFragment != null) {
                if (resultCode == RESULT_CODE_BACK_PRESS) {
                    clearSeekBar(childFragment);
                } else if (resultCode == RESULT_CODE_FAIL && data != null
                        && data.getStringExtra(RESULT) != null
                        && data.getStringExtra(RESULT).equals(Constants.RESULT_ERROR)) {

                    clearSeekBar(childFragment);

                    UI.createSimpleCustomDialog("Error!", data.getStringExtra(MESSAGE), getSupportFragmentManager(),
                            new DialogDoubleActions() {
                                @Override
                                public void actionConfirm(Object... params) {
                                    // Toast.makeText(getContext(), "Click CERRAR SESSION", Toast.LENGTH_SHORT).show();
                                    if (data.getStringExtra(MESSAGE).equals(Recursos.MESSAGE_OPEN_SESSION)) {
                                        onEvent(EVENT_SESSION_EXPIRED, 1);
                                    }
                                }

                                @Override
                                public void actionCancel(Object... params) {

                                }
                            },
                            true, false);
                } else {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }
            }
        } else if (requestCode == DocumentosFragment.REQUEST_TAKE_PHOTO || requestCode == DocumentosFragment.SELECT_FILE_PHOTO
                && getFragment(1) != null) {
            getFragment(1).onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == REGISTER_ADQUIRENTE_CODE && resultCode == RESULT_ADQUIRENTE_SUCCESS) {
            showMainTab();
            tabPresenter.getPagerData(ViewPagerDataFactory.TABS.MAIN_TABS);
        } else if (requestCode == CODE_CANCEL && resultCode == RESULT_CANCEL_OK) {
            getFragment(TYPE_DETAILS).onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == Constants.ACTIVITY_LANDING) {
            if (SingletonUser.getInstance().getDataUser().isEsAgente() &&
                    SingletonUser.getInstance().getDataUser().getEstatusAgente() == PTH_DOCTO_APROBADO &&
                    !pref.containsData(COUCHMARK_ADQ)) {
                pref.saveDataBool(COUCHMARK_ADQ, true);
                startActivity(LandingActivity.createIntent(this, 0, drawablesAdquirente));
            }
        }
    }

    protected void clearSeekBar(Fragment childFragment) {
        PaymentFormBaseFragment paymentFormBaseFragment = getVisibleFragment(childFragment.getChildFragmentManager().getFragments());
        if (paymentFormBaseFragment != null) {
            paymentFormBaseFragment.setSeekBarProgress(0);
        }
    }


    protected PaymentFormBaseFragment getVisibleFragment(List<Fragment> fragmentList) {
        for (Fragment fragment2 : fragmentList) {
            if (fragment2 instanceof PaymentFormBaseFragment && fragment2.isVisible()) {
                return (PaymentFormBaseFragment) fragment2;
            }
        }
        return null;
    }

    protected Fragment getFragment(int fragmentType) {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            for (Fragment fragment : fragmentList) {
                if ((fragmentType == 0 && fragment instanceof PaymentsTabFragment)
                        || (fragmentType == 1 && fragment instanceof DocumentsContainerFragment)
                        || (fragmentType == TYPE_DETAILS && fragment instanceof HomeTabFragment)) {
                    return fragment;
                }
            }
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        Fragment actualFragment = mainViewPagerAdapter.getItem(mainViewPager.getCurrentItem());
        if (actualFragment instanceof PaymentsTabFragment) {
            PaymentsTabFragment paymentsTabFragment = (PaymentsTabFragment) actualFragment;
            if (paymentsTabFragment.isOnForm) {
                paymentsTabFragment.onBackPresed(paymentsTabFragment.getCurrenTab());
            } else {
                goHome();
            }
        } else if (actualFragment instanceof DepositsFragment) {
            ((DepositsFragment) actualFragment).getDepositManager().onBtnBackPress();
        } else if (actualFragment instanceof GetMountFragment) {
            goHome();
        } else if (actualFragment instanceof HomeTabFragment) {
            showDialogOut();
        } else {
            goHome();
        }
    }

    private void showDialogOut() {
        UI.createSimpleCustomDialog("", getString(R.string.desea_cacelar), getSupportFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        SingletonSession.getInstance().setFinish(true);//Terminamos CupoStatusFragment si va a background
                        Intent intent = new Intent(TabActivity.this, MainActivity.class);
                        intent.putExtra(SELECTION, MAIN_SCREEN);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                }, true, true);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (pref.containsData(COUCHMARK_EMISOR) && SingletonSession.getInstance().isFinish()) {
            SingletonSession.getInstance().setFinish(false);
            finish();
        }
    }

    public void showProgressLayout(String msg) {
        progressGIF.setTextMessage(msg);
        progressGIF.setVisibility(View.VISIBLE);
    }

    public void hideProgresLayout() {
        progressGIF.setVisibility(View.GONE);
    }

}
