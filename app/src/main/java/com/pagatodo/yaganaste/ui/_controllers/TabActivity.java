package com.pagatodo.yaganaste.ui._controllers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonSession;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.freja.reset.managers.IResetNIPView;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenter;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenterImp;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IAprovView;
import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarPositionActivity;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.AprovPresenter;
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
import com.pagatodo.yaganaste.ui.preferuser.interfases.ICropper;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.ui_wallet.adapters.MenuAdapter;
import com.pagatodo.yaganaste.ui_wallet.dialog.DialogQrProfile;
import com.pagatodo.yaganaste.ui_wallet.fragments.SendWalletFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.WalletTabFragment;
import com.pagatodo.yaganaste.ui_wallet.interactors.FBInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IFBView;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.ui_wallet.presenter.FBPresenter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.steelkiwi.cropiwa.image.CropIwaResultReceiver;

import java.io.ByteArrayOutputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment.CODE_CANCEL;
import static com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment.RESULT_CANCEL_OK;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU_AJUSTES;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU_LOGOUT;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU_SEGURIDAD;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU_TERMINOS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_ACERCA_DE;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_AJUSTES;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_LOGOUT;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_SEGURIDAD;
import static com.pagatodo.yaganaste.utils.Constants.BACK_FROM_PAYMENTS;
import static com.pagatodo.yaganaste.utils.Constants.MESSAGE;
import static com.pagatodo.yaganaste.utils.Constants.REGISTER_ADQUIRENTE_CODE;
import static com.pagatodo.yaganaste.utils.Constants.RESULT;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_BACK_PRESS;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_FAIL;
import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.CUPO_COMPLETE;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;
import static com.pagatodo.yaganaste.utils.Recursos.URL_PHOTO_USER;
import static com.pagatodo.yaganaste.utils.StringConstants.TOKEN_FIREBASE;
import static com.pagatodo.yaganaste.utils.StringConstants.TOKEN_FIREBASE_STATUS;
import static com.pagatodo.yaganaste.utils.StringConstants.TOKEN_FIREBASE_SUCCESS;
import static com.pagatodo.yaganaste.utils.camera.CameraManager.CROP_RESULT;
import static com.pagatodo.yaganaste.utils.camera.CameraManager.REQUEST_TAKE_PHOTO;
import static com.pagatodo.yaganaste.utils.camera.CameraManager.SELECT_FILE_PHOTO;


public class TabActivity extends ToolBarPositionActivity implements TabsView, OnEventListener,
        IAprovView<ErrorObject>, IResetNIPView<ErrorObject>, MenuAdapter.OnItemClickListener,
        IListaOpcionesView, ICropper, CropIwaResultReceiver.Listener, IFBView {

    public static final String EVENT_INVITE_ADQUIRENTE = "1";
    public static final String EVENT_ERROR_DOCUMENTS = "EVENT_ERROR_DOCUMENTS";
    public static final String EVENT_CARGA_DOCUMENTS = "EVENT_CARGA_DOCUMENTS";
    public static final String EVENT_DOCUMENT_APPROVED = "EVENT_DOCUMENT_APPROVED";
    public static final String EVENT_GO_HOME = "2";
    public static final String EVENT_CHANGE_MAIN_TAB_VISIBILITY = "3";
    public static final String EVENT_HIDE_MANIN_TAB = "eventhideToolbar";
    public static final String EVENT_SHOW_MAIN_TAB = "eventShowToolbar";
    public static final String EVENT_BLOCK_CARD_BACK = "EVENT_BLOCK_CARD_BACK";
    public static final int RESULT_ADQUIRENTE_SUCCESS = 4573;
    public static final int TYPE_DETAILS = 3;
    private Preferencias pref;
    private ViewPager mainViewPager;
    private TabLayout mainTab;
    private AprovPresenter tabPresenter;
    private GenericPagerAdapter<IEnumTab> mainViewPagerAdapter;
    private ProgressLayout progressGIF;
    private ResetPinPresenter resetPinPresenter;
    private PreferUserPresenter mPreferPresenter;
    private CameraManager cameraManager;
    private CropIwaResultReceiver cropResultReceiver;
    private DrawerLayout navDrawer;

    CircleImageView imgLoginExistProfile;
    TextView nameUser;
    ImageView imageNotification;
    ImageView imageshare;
    ImageButton btnQrProfile;
    App aplicacion;
    private boolean disableBackButton = false;

    FBPresenter fbmPresenter;
    private ListView listView;
    public boolean isDialogShowned = false;

    public static Intent createIntent(Context from) {
        return new Intent(from, TabActivity.class);
    }

    public static final String TAG = TabActivity.class.getSimpleName();
    public static final String TAG2 = "RegistroToken";
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_drawer);
        aplicacion = new App();
        load();
        imgLoginExistProfile = (CircleImageView) findViewById(R.id.imgLoginExistProfile);
        imgLoginExistProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAvatar();
            }
        });
        imageNotification = (ImageView) findViewById(R.id.imgNotifications);
        imageNotification.setVisibility(View.GONE);
        imageshare = (ImageView) findViewById(R.id.deposito_Share);
        btnQrProfile = (ImageButton) findViewById(R.id.btn_qr_profile);
        btnQrProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQrProfile();
            }
        });

        showBack(false);
        /*if (!pref.containsData(COUCHMARK_EMISOR)) {
            pref.saveDataBool(COUCHMARK_EMISOR, true);
            startActivityForResult(LandingActivity.createIntent(this, PANTALLA_PRINCIPAL_EMISOR), ACTIVITY_LANDING);
        }*/
        mPreferPresenter = new PreferUserPresenter(this);
        cameraManager = new CameraManager(this);
        cameraManager.initCamera(this, imgLoginExistProfile, this);

        cropResultReceiver = new CropIwaResultReceiver();
        cropResultReceiver.setListener(this);
        cropResultReceiver.register(this);

        fbmPresenter = new FBPresenter(this, new FBInteractor());
        String tokenFBExist = pref.loadData(TOKEN_FIREBASE_STATUS);
        String tokenFB = pref.loadData(TOKEN_FIREBASE);
        //Log.d(TAG2, "tokenFB " + tokenFB);
        //Log.d(TAG2, "tokenFBExist " + tokenFBExist);
        if (!tokenFBExist.equals(TOKEN_FIREBASE_SUCCESS) && !tokenFB.isEmpty()) {
            //  Log.d(TAG2, "FBPresenter " + tokenFB);
            fbmPresenter.registerFirebaseToken(tokenFB);
        }
    }

    public void setAvatar() {
        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            boolean isValid = true;
            int permissionCamera = ContextCompat.checkSelfPermission(App.getContext(),
                    Manifest.permission.CAMERA);
            int permissionStorage = ContextCompat.checkSelfPermission(App.getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            // Si no tenemos el permiso lo solicitamos y pasamos la bandera a falso
            if (permissionCamera == -1) {
                ValidatePermissions.checkPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
                isValid = false;
            }
            // Si no tenemos el permiso lo solicitamos y pasamos la bandera a falso
            if (permissionStorage == -1) {
                ValidatePermissions.checkPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
                isValid = false;
            }
            if (isValid) {
                mPreferPresenter.openMenuPhoto(1, cameraManager);
            }
        } else {
            //showDialogMesage(getResources().getString(R.string.no_internet_access));
        }
    }

    private void openQrProfile() {
        if (!isDialogShowned) {
            isDialogShowned = true;
            DialogQrProfile dialogQrProfile = new DialogQrProfile();
            dialogQrProfile.show(getSupportFragmentManager(), "Dialog Qr Profile");
        }
    }

    private void load() {
        this.tabPresenter = new MainMenuPresenterImp(this);
        pref = App.getInstance().getPrefs();
        mainViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        mainTab = (TabLayout) findViewById(R.id.main_tab);
        progressGIF = (ProgressLayout) findViewById(R.id.progressGIF);
        progressGIF.setVisibility(View.GONE);
        resetPinPresenter = new ResetPinPresenterImp(false);
        resetPinPresenter.setResetNIPView(this);
        tabPresenter.getPagerData(ViewPagerDataFactory.TABS.MAIN_TABS);
        nameUser = (TextView) findViewById(R.id.txt_name_user);
        if (pref.loadData(StringConstants.SIMPLE_NAME).contains(" ")) {
            String name[] = pref.loadData(StringConstants.SIMPLE_NAME).split(" ");
            nameUser.setText(name[0]);
        } else {
            nameUser.setText(pref.loadData(StringConstants.SIMPLE_NAME));
        }
        navDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

    }

    @Override
    public void loadViewPager(ViewPagerData viewPagerData) {
        mainViewPagerAdapter = new GenericPagerAdapter<>(this, getSupportFragmentManager(),
                viewPagerData.getFragmentList(), viewPagerData.getTabData());
        mainViewPager.setAdapter(mainViewPagerAdapter);
        mainViewPager.setOffscreenPageLimit(viewPagerData.getTabData().length - 1);
        mainViewPager.setCurrentItem(1);
        mainTab.setupWithViewPager(mainViewPager);
        //mainTab.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        LinearLayout linearLayout = (LinearLayout) mainTab.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(ContextCompat.getColor(this, R.color.colorGrayMenuDivider));
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(0);
        linearLayout.setDividerDrawable(drawable);

        if (tabPresenter.needsProvisioning() || tabPresenter.needsPush()) {
            tabPresenter.doProvisioning();
        } else if (SingletonUser.getInstance().needsReset()) {
            resetPinPresenter.doReseting(pref.loadData(SHA_256_FREJA));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTest);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.hamburguesita);

        listView = (ListView) findViewById(R.id.lst_menu_items);
        MenuAdapter menuAdapter = new MenuAdapter(this, new OptionMenuItem(this).MAINMENU(), this);
        listView.setAdapter(menuAdapter);
    }

    @Override
    public void finishAssociation() {
        /*if (SingletonUser.getInstance().needsReset()) {
            resetPinPresenter.doReseting(pref.loadData(SHA_256_FREJA));
        } else {*/
        hideLoader();
        //}
    }


    @Override
    public void finishReseting() {
        hideLoader();
    }

    @Override
    public void onResetingFailed() {
        hideLoader();
        tabPresenter.doProvisioning();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (pref.containsData(CUPO_COMPLETE) && pref.loadDataBoolean(CUPO_COMPLETE, false)) {
            pref.saveDataBool(CUPO_COMPLETE, false);
        }

        updatePhoto();
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
       /* if (!pref.containsData(COUCHMARK_EMISOR)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pref.saveDataBool(COUCHMARK_EMISOR, true);
                    startActivityForResult(LandingActivity.createIntent(TabActivity.this, PANTALLA_PRINCIPAL_EMISOR), ACTIVITY_LANDING);
                }
            }, 500);
        }*/
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
            DocumentsContainerFragment mFragment = (DocumentsContainerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.main_view_pager);
            mFragment.loadApprovedFragment();
        } else if (event.equals(EVENT_ERROR_DOCUMENTS)) {
            showMainTab();
            tabPresenter.getPagerData(ViewPagerDataFactory.TABS.MAIN_ELECTION);
            onInviteAdquirente();
        } else if (event.equals(EVENT_CARGA_DOCUMENTS)) {
            showMainTab();
            tabPresenter.getPagerData(ViewPagerDataFactory.TABS.MAIN_TABS);
            onInviteAdquirente();
        } else if (event.equals(EVENT_CARGA_DOCUMENTS)) {
            showMainTab();
            tabPresenter.getPagerData(ViewPagerDataFactory.TABS.MAIN_TABS);
            onInviteAdquirente();
        } else if (event.equals("DISABLE_BACK")) {
            if (data.toString().equals("true")) {
                disableBackButton = true;
            } else {
                disableBackButton = false;
            }
        } else if (event.equals(EVENT_BLOCK_CARD_BACK)) {

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
        TabLayout.Tab current = mainTab.getTabAt(1);
        if (current != null) {
            current.select();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CONTACTS_CONTRACT
                || requestCode == Constants.BARCODE_READER_REQUEST_CODE
                || requestCode == BACK_FROM_PAYMENTS
                || requestCode == Constants.NEW_FAVORITE
                || requestCode == Constants.EDIT_FAVORITE) {
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

                    UI.createSimpleCustomDialog(App.getContext().getResources().getString(R.string.new_tittle_error_interno), data.getStringExtra(MESSAGE), getSupportFragmentManager(),
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
        /*} else if (requestCode == Constants.ACTIVITY_LANDING) {
            if (SingletonUser.getInstance().getDataUser().isEsAgente() &&
                    SingletonUser.getInstance().getDataUser().getEstatusAgente() == PTH_DOCTO_APROBADO &&
                    !pref.containsData(COUCHMARK_ADQ)) {
                pref.saveDataBool(COUCHMARK_ADQ, true);
                startActivity(LandingActivity.createIntent(this, PANTALLA_COBROS));
            }*/
        } else if (requestCode == Constants.PAYMENTS_ADQUIRENTE && resultCode == Activity.RESULT_OK) {
            refreshAdquirenteMovements();
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            cameraManager.setOnActivityResult(requestCode, resultCode, data);
        } else if (requestCode == SELECT_FILE_PHOTO && resultCode == RESULT_OK && null != data) {
            cameraManager.setOnActivityResult(requestCode, resultCode, data);
        } else if (resultCode == CROP_RESULT) {

            onHideProgress();
        }
    }

    protected void refreshAdquirenteMovements() {
        @SuppressLint("RestrictedApi") List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment instanceof HomeTabFragment) {
                goHome();
                ((HomeTabFragment) fragment).setCurrentItem(1);
                ((HomeTabFragment) fragment).getPaymentsFragment().onRefresh(SwipyRefreshLayoutDirection.TOP);
                ((HomeTabFragment) fragment).getPaymentsFragment().showLoader("");
            }
        }
    }

    protected void clearSeekBar(Fragment childFragment) {
        @SuppressLint("RestrictedApi") PaymentFormBaseFragment paymentFormBaseFragment = getVisibleFragment(childFragment.getChildFragmentManager().getFragments());
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
        //outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        //super.onSaveInstanceState(outState);
    }

    protected Fragment getFragment(int fragmentType) {
        @SuppressLint("RestrictedApi") List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
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
        if (!disableBackButton) {
            if (actualFragment instanceof PaymentsTabFragment) {
                PaymentsTabFragment paymentsTabFragment = (PaymentsTabFragment) actualFragment;
                if (paymentsTabFragment.isOnForm) {
                    paymentsTabFragment.onBackPresed(paymentsTabFragment.getCurrenTab());
                } else {
                    goHome();
                }
            } else if (actualFragment instanceof DepositsFragment) {
                imageNotification.setVisibility(View.GONE);
                imageshare.setVisibility(View.VISIBLE);
                ((DepositsFragment) actualFragment).getDepositManager().onBtnBackPress();
            } else if (actualFragment instanceof GetMountFragment) {
                goHome();
            } else if (actualFragment instanceof HomeTabFragment) {
                showDialogOut();
            } else if (actualFragment instanceof WalletTabFragment) {
                showDialogOut();
            } else if (actualFragment instanceof SendWalletFragment) {
                goHome();
            } else {
                goHome();
            }
        }
    }

    private void showDialogOut() {
        UI.createSimpleCustomDialog("", getString(R.string.desea_cacelar), getSupportFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        SingletonSession.getInstance().setFinish(true);//Terminamos CupoStatusFragment si va a background
                        aplicacion.cerrarAppsms();
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

    @Override
    public void showErrorAprov(ErrorObject error) {

    }

    @Override
    public void showErrorReset(ErrorObject error) {

    }

    /**
     * Codigo para hacer Set en la imagen de preferencias con la foto actual
     */
    private void updatePhoto() {
        String mUserImage = pref.loadData(URL_PHOTO_USER);
        Glide.with(this).load(StringUtils.procesarURLString(mUserImage))
                .placeholder(R.mipmap.icon_user).error(R.mipmap.icon_user)
                .dontAnimate().into(imgLoginExistProfile);
    }

    @Override
    public void onItemClick(OptionMenuItem optionMenuItem) {
        switch (optionMenuItem.getIdItem()) {
            case ID_SEGURIDAD:
                actionMenu(MENU_SEGURIDAD);
                break;
            case ID_AJUSTES:
                actionMenu(MENU_AJUSTES);
                break;
            case ID_ACERCA_DE:
                actionMenu(MENU_TERMINOS);
                break;
            case ID_LOGOUT:
                logOut();
                break;
            default:
                Toast.makeText(this, "PROXIMAMENTE", Toast.LENGTH_SHORT).show();
                break;
        }

    }
    public void actionMenu(int opcionMenu){
        Intent intent = new Intent(this, PreferUserActivity.class);
        intent.putExtra(MENU, opcionMenu);
        startActivityForResult(intent, CODE_LOG_OUT);
    }

    public void logOut(){
        UI.createSimpleCustomDialog("",
                App.getContext().getResources().getString(R.string.desea_cerrar_sesion),
                getSupportFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        Intent intent = new Intent(TabActivity.this, MainActivity.class);
                        intent.putExtra(SELECTION, MAIN_SCREEN);
                        startActivity(intent);
                        mPreferPresenter.logOutSession();
                        finish();
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                }, true, true);
    }

    //Avatar
    @Override
    public void setPhotoToService(Bitmap bitmap) {
        // Guardamos en bruto nuestro Bitmap.
        cameraManager.setBitmap(bitmap);

        // Procesamos el Bitmap a Base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // Creamos el objeto ActualizarAvatarRequest
        ActualizarAvatarRequest avatarRequest = new ActualizarAvatarRequest(encoded, "png");

        //onEventListener.onEvent("DISABLE_BACK", true);
        // Enviamos al presenter
        mPreferPresenter.sendPresenterActualizarAvatar(avatarRequest);

    }

    @Override
    public void showProgress(String mMensaje) {
        navDrawer.closeDrawers();
        showProgressLayout(getString(R.string.listaopciones_load_image_wait));
    }

    @Override
    public void showExceptionToView(String mMesage) {
        showDialogMesage(mMesage);
    }

    @Override
    public void sendSuccessAvatarToView(String mensaje) {
        showDialogMesage(getString(R.string.change_avatar_success));
        mUserImage = SingletonUser.getInstance().getDataUser().getUsuario().getImagenAvatarURL();
        updatePhoto();
        CameraManager.cleanBitmap();
        hideLoader();
    }

    @Override
    public void sendErrorAvatarToView(String mensaje) {
        hideLoader();
        //onEventListener.onEvent("DISABLE_BACK", false);
        CameraManager.cleanBitmap();
        showDialogMesage(mensaje);
    }

    //Crope

    @Override
    public void onCropper(Uri uri) {
        showProgress("");
        startActivityForResult(CropActivity.callingIntent(this, uri), CROP_RESULT);
    }

    @Override
    public void onHideProgress() {
        hideProgresLayout();
    }

    @Override
    public void failLoadJpg() {
        showDialogMesage(getString(R.string.msg_format_image_warning));
    }

    private void showDialogMesage(final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getSupportFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        hideProgresLayout();
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }

    @Override
    public void onCropSuccess(Uri croppedUri) {
        cameraManager.setCropImage(croppedUri);
    }

    @Override
    public void onCropFailed(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void setLogOutSession() {

    }
}
