package com.pagatodo.yaganaste.ui._controllers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
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
import com.pagatodo.yaganaste.modules.charge.ChargeActivity;
import com.pagatodo.yaganaste.modules.emisor.PaymentToQR.QrManagerFragment;
import com.pagatodo.yaganaste.modules.emisor.VirtualCardAccount.MyVirtualCardAccountFragment;
import com.pagatodo.yaganaste.modules.registerAggregator.AggregatorActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarPositionActivity;
import com.pagatodo.yaganaste.ui.account.AprovPresenter;
import com.pagatodo.yaganaste.ui.account.login.FingerprintAuthenticationDialogFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.GetMountFragment;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DocumentsContainerFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.EnviosFromFragmentNewVersion;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentFormBaseFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.DepositsFragment;
import com.pagatodo.yaganaste.ui.maintabs.presenters.MainMenuPresenterImp;
import com.pagatodo.yaganaste.ui.preferuser.interfases.ICropper;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.modules.emisor.WalletMainActivity;
import com.pagatodo.yaganaste.ui_wallet.fragments.NewPaymentFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.OperadorTabFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.SendWalletFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.WalletTabFragment;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.interactors.FBInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IFBView;
import com.pagatodo.yaganaste.ui_wallet.interfaces.NavigationDrawerPresenter;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.ui_wallet.presenter.FBPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.NavigationDrawerPresenterImpl;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.Picasso;
import com.steelkiwi.cropiwa.image.CropIwaResultReceiver;

import java.io.ByteArrayOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_DETALLE_PROMO;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment.CODE_CANCEL;
import static com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment.RESULT_CANCEL_OK;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU_AJUSTES;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU_CODE;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU_CONTACTO;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU_DATAUSER;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU_SEGURIDAD;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU_TERMINOS;
import static com.pagatodo.yaganaste.ui_wallet.fragments.WalletTabFragment.ITEM_OPERATION;
import static com.pagatodo.yaganaste.ui_wallet.patterns.builders.ContainerBuilder.MAINMENU;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_TUTORIALS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_ACERCA_DE;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_AJUSTES;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_CODE;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_CONTACTO;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_LOGOUT;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_MY_DATA;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_SEGURIDAD;
import static com.pagatodo.yaganaste.utils.Constants.BACK_FROM_PAYMENTS;
import static com.pagatodo.yaganaste.utils.Constants.CREDITCARD_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Constants.EDIT_FAVORITE;
import static com.pagatodo.yaganaste.utils.Constants.MESSAGE;
import static com.pagatodo.yaganaste.utils.Constants.NEW_FAVORITE_FROM_CERO;
import static com.pagatodo.yaganaste.utils.Constants.NEW_FAVORITE_FROM_OPERATION;
import static com.pagatodo.yaganaste.utils.Constants.RESULT;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_BACK_PRESS;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_FAIL;
import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.CUPO_COMPLETE;
import static com.pagatodo.yaganaste.utils.Recursos.GENERO;
import static com.pagatodo.yaganaste.utils.Recursos.IS_OPERADOR;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;
import static com.pagatodo.yaganaste.utils.Recursos.SIMPLE_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.URL_PHOTO_USER;
import static com.pagatodo.yaganaste.utils.camera.CameraManager.CROP_RESULT;
import static com.pagatodo.yaganaste.utils.camera.CameraManager.REQUEST_TAKE_PHOTO;
import static com.pagatodo.yaganaste.utils.camera.CameraManager.SELECT_FILE_PHOTO;


public class TabActivity extends ToolBarPositionActivity implements TabsView, OnEventListener,
        IAprovView<ErrorObject>, IResetNIPView<ErrorObject>, OnClickItemHolderListener,
        IListaOpcionesView, ICropper, CropIwaResultReceiver.Listener, IFBView, BottomNavigationView.OnNavigationItemSelectedListener, FingerprintAuthenticationDialogFragment.generateCodehuella{

    public static final String EVENT_INVITE_ADQUIRENTE = "1";
    public static final String EVENT_ERROR_DOCUMENTS = "EVENT_ERROR_DOCUMENTS";
    public static final String EVENT_SCAN_QR = "EVENT_SCAN_QR";
    public static final String EVENT_CARGA_DOCUMENTS = "EVENT_CARGA_DOCUMENTS";
    public static final String EVENT_DOCUMENT_APPROVED = "EVENT_DOCUMENT_APPROVED";
    public static final String EVENT_GO_HOME = "2";
    public static final String EVENT_CHANGE_MAIN_TAB_VISIBILITY = "3";
    public static final String EVENT_HIDE_MANIN_TAB = "eventhideToolbar";
    public static final String EVENT_SHOW_MAIN_TAB = "eventShowToolbar";
    public static final String EVENT_BLOCK_CARD_BACK = "EVENT_BLOCK_CARD_BACK";
    public static final String EVENT_LOGOUT = "EVENT_LOGOUT";
    public static final int RESULT_ADQUIRENTE_SUCCESS = 4573;
    public static final int RESUL_FAVORITES = 7894;
    public static final int MONEY_SEND = 7895;
    public static final int INTENT_FAVORITE = 46325;
    public static final int TYPE_DETAILS = 3;
    public static final int PICK_WALLET_TAB_REQUEST = 9630;  // The request code
    public static final int RESULT_CODE_SELECT_DONGLE = 9631;  // The result code code


    private AprovPresenter tabPresenter;
    private GenericPagerAdapter<IEnumTab> mainViewPagerAdapter;
    private ResetPinPresenter resetPinPresenter;
    private NavigationDrawerPresenter drawerPresenter;
    private CameraManager cameraManager;
    private CropIwaResultReceiver cropResultReceiver;

    @BindView(R.id.toolbarTest)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout navDrawer;
    @BindView(R.id.main_view_pager)
    ViewPager mainViewPager;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView navitaionBar;
    @BindView(R.id.imgLoginExistProfile)
    CircleImageView imgLoginExistProfile;
    @BindView(R.id.txtVersionApp)
    StyleTextView textViewversion;
    @BindView(R.id.content_linearlayout)
    ViewGroup mLinearLayout;
    @BindView(R.id.txt_name_user)
    TextView nameUser;
    private int lastFrag;



    private boolean disableBackButton = false;
    FBPresenter fbmPresenter;
    public boolean isDialogShowned = false;

    public static Intent createIntent(Context from) {
        return new Intent(from, TabActivity.class);
    }

    public static final String TAG = TabActivity.class.getSimpleName();
    public static final String TAG2 = "RegistroToken";
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 101;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);*/
        setContentView(R.layout.main_menu_drawer);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        init();
        showBack(false);
        drawerPresenter = new NavigationDrawerPresenterImpl(this);
        cameraManager = new CameraManager(this);
        cameraManager.initCamera(this, imgLoginExistProfile, this);
        cropResultReceiver = new CropIwaResultReceiver();
        fbmPresenter = new FBPresenter(this, new FBInteractor());
        this.tabPresenter = new MainMenuPresenterImp(this);
        this.resetPinPresenter = new ResetPinPresenterImp(false);
        this.resetPinPresenter.setResetNIPView(this);
        this.tabPresenter.getPagerData(ViewPagerDataFactory.TABS.MAIN_TABS);

    }

    private void init() {
        ContainerBuilder.builder(this, mLinearLayout, this, MAINMENU);
        textViewversion.setText("Ya Ganaste " + String.valueOf(BuildConfig.VERSION_NAME));
        nameUser.setText(App.getInstance().getPrefs().loadData(SIMPLE_NAME));
        imgLoginExistProfile.setOnClickListener(v -> setAvatar());
        navitaionBar.setOnNavigationItemSelectedListener(this);
        lastFrag = 0;
        navitaionBar.setSelectedItemId(R.id.navigation_wallet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                goToWalletMainActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToWalletMainActivity() {
        ElementView element = new ElementView();
        element.setIdOperacion(OPTION_TUTORIALS);

        Intent intent = new Intent(this, WalletMainActivity.class);
        intent.putExtra(ITEM_OPERATION, element);
        startActivityForResult(intent, PICK_WALLET_TAB_REQUEST);
    }

    public void setAvatar() {
        if (Utils.isDeviceOnline()) {
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
                try {
                    cropResultReceiver.setListener(this);
                    //cropResultReceiver.unregister(this);
                    cropResultReceiver.register(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                drawerPresenter.openMenuPhoto(1, cameraManager);
            }
        }
    }

    @Override
    public void loadViewPager(ViewPagerData viewPagerData) {
        mainViewPagerAdapter = new GenericPagerAdapter<>(this, getSupportFragmentManager(),
                viewPagerData.getFragmentList(), viewPagerData.getTabData());
        mainViewPager.setAdapter(mainViewPagerAdapter);
        mainViewPager.setOffscreenPageLimit(viewPagerData.getTabData().length - 1);

        if (!App.getInstance().getPrefs().containsData(IS_OPERADOR)) {
            mainViewPager.setCurrentItem(2);
        } else {
            mainViewPager.setCurrentItem(0);
        }
        //mainTab.setupWithViewPager(mainViewPager);




        if (tabPresenter.needsProvisioning() || tabPresenter.needsPush()) {
            tabPresenter.doProvisioning();
        } else if (SingletonUser.getInstance().needsReset()) {
            resetPinPresenter.doReseting(App.getInstance().getPrefs().loadData(SHA_256_FREJA));
        }
        Toolbar toolbar = findViewById(R.id.toolbarTest);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void finishAssociation() {
        hideLoader();
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

        if (App.getInstance().getPrefs().containsData(CUPO_COMPLETE) && App.getInstance().getPrefs().loadDataBoolean(CUPO_COMPLETE, false)) {
            App.getInstance().getPrefs().saveDataBool(CUPO_COMPLETE, false);
        }
        updatePhoto();

    }

    @Override
    public boolean requiresTimer() {
        return true;
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
        } else if (event.equals("DISABLE_BACK")) {
            if (data.toString().equals("true")) {
                disableBackButton = true;
            } else {
                disableBackButton = false;
            }
        } else if (event.equals(EVENT_BLOCK_CARD_BACK)) {

        } else if (event.equals(EVENT_LOGOUT)) {
            logOut(App.getContext().getResources().getString(R.string.reload_session));
        }
        else if (event.equals(EVENT_DETALLE_PROMO)) {
            Intent intentpromo = new Intent(this, PromoCodesActivity.class);
            this.startActivity(intentpromo);
        }

    }

    protected void hideMainTab() {
        /*if (mainTab.getVisibility() == View.VISIBLE) {
            mainTab.setVisibility(View.GONE);
        }*/
    }

    protected void showMainTab() {
        /*if (mainTab.getVisibility() == View.GONE) {
            mainTab.setVisibility(View.VISIBLE);
        }*/
    }

    private void onInviteAdquirente() {
        /*TabLayout.Tab current = mainTab.getTabAt(mainTab.getTabCount() - 1);
        if (current != null) {
            current.select();
        }*/
    }

    public void goHome() {
        /*TabLayout.Tab current = mainTab.getTabAt(0);
        if (current != null) {
            current.select();
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CONTACTS_CONTRACT
                || requestCode == Constants.BARCODE_READER_REQUEST_CODE
                || requestCode == BACK_FROM_PAYMENTS
                || requestCode == NEW_FAVORITE_FROM_CERO
                || requestCode == NEW_FAVORITE_FROM_OPERATION
                || requestCode == EDIT_FAVORITE
                || requestCode == CREDITCARD_READER_REQUEST_CODE) {
            Fragment childFragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.main_view_pager + ":" + mainViewPager.getCurrentItem());
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
                } else if (requestCode == BACK_FROM_PAYMENTS) {
                    childFragment.onActivityResult(requestCode, resultCode, data);
                } else {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        /*} else if (requestCode == DocumentosFragment.REQUEST_TAKE_PHOTO || requestCode == DocumentosFragment.SELECT_FILE_PHOTO
                && getFragment(1) != null) {
            getFragment(1).onActivityResult(requestCode, resultCode, data);*/
        } else if (requestCode == CODE_CANCEL && resultCode == RESULT_CANCEL_OK) {
            getFragment().onActivityResult(requestCode, resultCode, data);
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
        } else if (requestCode == CROP_RESULT) {
            onHideProgress();
            updatePhoto();
        } else if (resultCode == RESUL_FAVORITES) {
            //if (getCurrentFragment() instanceof EnviosFromFragmentNewVersion){
            //EnviosFromFragmentNewVersion.newInstance().onActivityResult(requestCode, resultCode, data);
            Fragment childFragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.main_view_pager + ":" + mainViewPager.getCurrentItem());
            childFragment.onActivityResult(requestCode, resultCode, data);

            //}
        } else if (resultCode == INTENT_FAVORITE) {
            Fragment childFragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.main_view_pager + ":" + mainViewPager.getCurrentItem());
            childFragment.onActivityResult(requestCode, resultCode, data);
        } else if (resultCode == PICK_WALLET_TAB_REQUEST || resultCode == RESULT_CODE_SELECT_DONGLE
                || resultCode == RESULT_ADQUIRENTE_SUCCESS) {
            Fragment childFragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.main_view_pager + ":" + mainViewPager.getCurrentItem());
            childFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void refreshAdquirenteMovements() {
        @SuppressLint("RestrictedApi") List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragmentList) {
            /*if (fragment instanceof HomeTabFragment) {
                goHome();
                ((HomeTabFragment) fragment).setCurrentItem(1);
                ((HomeTabFragment) fragment).getPaymentsFragment().onRefresh(SwipyRefreshLayoutDirection.TOP);
                ((HomeTabFragment) fragment).getPaymentsFragment().showLoader("");
            }*/
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

    protected Fragment getFragment() {
        @SuppressLint("RestrictedApi") List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            for (Fragment fragment : fragmentList) {
                /*if ((fragmentType == 1 && fragment instanceof DocumentsContainerFragment)
                        || (fragmentType == TYPE_DETAILS && fragment instanceof HomeTabFragment)) {
                    return fragment;
                }*/
                if ((TabActivity.TYPE_DETAILS == 1 && fragment instanceof DocumentsContainerFragment)
                        || (TabActivity.TYPE_DETAILS == TYPE_DETAILS)) {
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
            if (actualFragment instanceof DepositsFragment) {
                //imageNotification.setVisibility(View.GONE);
                //imageshare.setVisibility(View.VISIBLE);
                ((DepositsFragment) actualFragment).getDepositManager().onBtnBackPress();
            } else if (actualFragment instanceof GetMountFragment) {
                goHome();
            } else if (actualFragment instanceof WalletTabFragment) {
                showDialogOut();
            } else if (actualFragment instanceof OperadorTabFragment) {
                showDialogOut();
            } else if (actualFragment instanceof SendWalletFragment) {
                goHome();
            }else if (actualFragment instanceof QrManagerFragment) {
                goHome();
            } else if (actualFragment instanceof EnviosFromFragmentNewVersion) {
                goHome();
            } else if (actualFragment instanceof NewPaymentFragment) {
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
                        new App().cerrarAppsms();
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
        if (App.getInstance().getPrefs().containsData(COUCHMARK_EMISOR) && SingletonSession.getInstance().isFinish()) {
            SingletonSession.getInstance().setFinish(false);
            finish();
        }
    }

    public void showProgressLayout(String msg) {
        showLoader(msg);
    }

    public void hideProgresLayout() {

        hideLoader();
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
        if (App.getInstance().getPrefs().loadData(GENERO) == "H" || App.getInstance().getPrefs().loadData(GENERO) == "h") {
            String mUserImage = App.getInstance().getPrefs().loadData(URL_PHOTO_USER);
            Picasso.with(this).load(StringUtils.procesarURLString(mUserImage))
                    .placeholder(R.mipmap.icon_user_fail).error(R.drawable.avatar_el)
                    .into(imgLoginExistProfile);

        } else if (App.getInstance().getPrefs().loadData(GENERO) == "M" || App.getInstance().getPrefs().loadData(GENERO) == "m") {
            String mUserImage = App.getInstance().getPrefs().loadData(URL_PHOTO_USER);
            Picasso.with(this).load(StringUtils.procesarURLString(mUserImage))
                    .placeholder(R.mipmap.icon_user_fail).error(R.drawable.avatar_ella)
                    .into(imgLoginExistProfile);

        } else {
            String mUserImage = App.getInstance().getPrefs().loadData(URL_PHOTO_USER);
            Picasso.with(this).load(StringUtils.procesarURLString(mUserImage))
                    .placeholder(R.mipmap.icon_user_fail).error(R.mipmap.icon_user_fail)
                    .into(imgLoginExistProfile);
        }
    }

    @Override
    public void onItemClick(Object item) {
        OptionMenuItem optionMenuItem = (OptionMenuItem) item;
        switch (optionMenuItem.getIdItem()) {
            case ID_SEGURIDAD:
                actionMenu(MENU_SEGURIDAD);
                break;
                case ID_MY_DATA:
                actionMenu(MENU_DATAUSER);
                break;
            case ID_AJUSTES:
                actionMenu(MENU_AJUSTES);
                break;
            case ID_ACERCA_DE:
                actionMenu(MENU_TERMINOS);
                break;
            case ID_LOGOUT:
                logOut(App.getContext().getResources().getString(R.string.desea_cerrar_sesion));
                break;
            case ID_CODE:
                actionMenu(MENU_CODE);
                break;
            case ID_CONTACTO:
                actionMenu(MENU_CONTACTO);
                break;
            default:
                Toast.makeText(this, "PROXIMAMENTE", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void actionMenu(int opcionMenu) {
        Intent intent = new Intent(this, PreferUserActivity.class);
        intent.putExtra(MENU, opcionMenu);
        startActivityForResult(intent, CODE_LOG_OUT);
    }

    public void logOut(String message) {
        AlertDialog builder = new AlertDialog.Builder(new ContextThemeWrapper(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT))
                .setTitle(R.string.navigation_drawer_logout)
                .setMessage(message)
                .setPositiveButton(R.string.title_aceptar, (dialogInterface, i) -> {
                    Intent intent = new Intent(TabActivity.this, MainActivity.class);
                    intent.putExtra(SELECTION, MAIN_SCREEN);
                    startActivity(intent);
                    drawerPresenter.logOutSession();
                    finish();
                }).setCancelable(false)
                .setNegativeButton(R.string.title_cancelar, (dialogInterface, i) -> dialogInterface.dismiss()).create();
        builder.show();



        /*UI.createSimpleCustomDialog("",
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
                */
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
        drawerPresenter.sendPresenterActualizarAvatar(avatarRequest);

    }

    @Override
    public void showProgress(String mMensaje) {
        navDrawer.closeDrawers();
        //  showProgressLayout(getString(R.string.listaopciones_load_image_wait));
    }

    @Override
    public void showExceptionToView(String mMesage) {
        //ES un snackbar
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
        CameraManager.cleanBitmap();
        showDialogMesage(mensaje);
    }

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.navigation_charge){
            startActivity(ChargeActivity.createIntent(this));
            //startActivity(AggregatorActivity.createIntent(this));
        } else if (lastFrag != menuItem.getItemId()) {
            lastFrag = menuItem.getItemId();
            switch (menuItem.getItemId()) {
                case R.id.navigation_send:
                    mainViewPager.setCurrentItem(0);
                    return true;

                case R.id.navigation_pay:
                    //navitaionBar.setSelectedItemId(R.id.navigation_pay);
                    mainViewPager.setCurrentItem(1);
                    return true;

                case R.id.navigation_wallet:
                    //navitaionBar.setSelectedItemId(R.id.navigation_wallet);
                    mainViewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_my_qr:
                    //navitaionBar.setSelectedItemId(R.id.navigation_my_qr);
                    mainViewPager.setCurrentItem(3);
                    return true;
                //case R.id.navigation_charge:
                    //navitaionBar.setSelectedItemId(R.id.navigation_charge);
                    //mainViewPager.setCurrentItem(4);

                  //  return true;
            }
        }
        return false;
    }

    @Override
    public void generatecodehue(Fragment fm) {
        if (fm instanceof MyVirtualCardAccountFragment)
            ((MyVirtualCardAccountFragment) fm).loadOtpHuella();


    }
}
