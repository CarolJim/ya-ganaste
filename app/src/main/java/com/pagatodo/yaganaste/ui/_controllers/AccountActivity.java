package com.pagatodo.yaganaste.ui._controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.widget.FrameLayout;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.PageResult;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesionUYU;
import com.pagatodo.yaganaste.interfaces.Command;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.login.AccessCodeGenerateFragment;
import com.pagatodo.yaganaste.ui.account.login.BlockCardFragment;
import com.pagatodo.yaganaste.ui.account.login.FingerprintAuthenticationDialogFragment;
import com.pagatodo.yaganaste.ui.account.login.LoginManagerContainerFragment;
import com.pagatodo.yaganaste.ui.account.login.NewConfirmPasswordLogin;
import com.pagatodo.yaganaste.ui.account.login.NewPasswordLoginChange;
import com.pagatodo.yaganaste.ui.account.login.RecoveryFragment;
import com.pagatodo.yaganaste.ui.account.register.AsignarNIPFragment;
import com.pagatodo.yaganaste.ui.account.register.AsociatePhoneAccountFragment;
import com.pagatodo.yaganaste.ui.account.register.ConfirmarNIPFragment;
import com.pagatodo.yaganaste.ui.account.register.Couchmark;
import com.pagatodo.yaganaste.ui.account.register.DatosPersonalesFragment;
import com.pagatodo.yaganaste.ui.account.register.DatosUsuarioFragment;
import com.pagatodo.yaganaste.ui.account.register.DomicilioActualFragment;
import com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment;
import com.pagatodo.yaganaste.ui.account.register.SelfieFragment;
import com.pagatodo.yaganaste.ui.account.register.TienesTarjetaFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.TransactionResultFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.PairBluetoothFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.PayQRFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.SelectDongleFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.TutorialsFragment;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.ForcedUpdateChecker;
import com.pagatodo.yaganaste.utils.qrcode.MyQrCommerce;
import com.pagatodo.yaganaste.utils.qrcode.QrcodeGenerator;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

import java.security.KeyStore;

import javax.crypto.KeyGenerator;

import static android.os.Process.killProcess;
import static android.os.Process.myPid;
import static com.pagatodo.yaganaste.freja.provisioning.presenter.ProvisioningPresenterAbs.EVENT_APROV_FAILED;
import static com.pagatodo.yaganaste.freja.provisioning.presenter.ProvisioningPresenterAbs.EVENT_APROV_SUCCES;
import static com.pagatodo.yaganaste.ui._controllers.DetailsActivity.MY_PERMISSIONS_REQUEST_SEND_SMS;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.GO_TO_LOGIN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.GO_TO_REGISTER;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.IS_FROM_TIMER;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment.COMPLETE_MESSAGES.EMISOR;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_CONFIG_DONGLE;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_SELECT_DONGLE;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE_COMERCE;
import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.PHONE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;

public class AccountActivity extends LoaderActivity implements OnEventListener, FingerprintAuthenticationDialogFragment.generateCodehuella,
        ForcedUpdateChecker.OnUpdateNeededListener {
    public final static String EVENT_GO_LOGIN = "EVENT_GO_LOGIN";
    public final static String EVENT_GO_GET_CARD = "EVENT_GO_GET_CARD";
    public final static String EVENT_GO_BASIC_INFO = "EVENT_GO_BASIC_INFO";
    public final static String EVENT_GO_REGISTER_ADDRESS = "EVENT_GO_REGISTER_ADDRESS";
    public final static String EVENT_GO_GET_PAYMENTS = "EVENT_GO_GET_PAYMENTS";
    public final static String EVENT_GO_ASOCIATE_PHONE = "EVENT_GO_ASOCIATE_PHONE";
    public final static String EVENT_GO_PIN_CONFIRMATION = "EVENT_GO_PIN_CONFIRMATION";
    public final static String EVENT_GO_MAIN_TAB_ACTIVITY = "EVENT_GO_MAIN_TAB_ACTIVITY";
    public final static String EVENT_GO_HELP = "EVENT_GO_HELP";

    //Nuevo diseño-flujo
    public final static String EVENT_DATA_USER = "EVENT_GO_DATA_USER";
    public final static String EVENT_DATA_USER_BACK = "EVENT_GO_DATA_USER_BACK";
    public final static String EVENT_PERSONAL_DATA = "EVENT_GO_PERSONAL_DATA";
    public final static String EVENT_PERSONAL_DATA_BACK = "EVENT_GO_PERSONAL_DATA_BACK";

    public final static String EVENT_PAYMENTQR = "EVENT_PAYMENTQR";

    public final static String EVENT_SELFIE = "EVENT_SELFIE";
    public final static String EVENT_SELFIE_BACK = "EVENT_SELFIE_BACK";

    public final static String EVENT_ADDRESS_DATA = "EVENT_GO_ADDRESS_DATA";
    public final static String EVENT_ADDRESS_DATA_BACK = "EVENT_GO_ADDRESS_DATA_BACK";
    public final static String EVENT_GO_ASSIGN_PIN = "EVENT_GO_ASSIGN_PIN";
    public final static String EVENT_GO_ASSIGN_NEW_CONTRASE = "EVENT_GO_ASSIGN_NEW_CONTRASE";
    public final static String EVENT_GO_CONFIRM_NEW_CONTRASE = "EVENT_GO_CONFIRM_NEW_CONTRASE";
    public final static String EVENT_GO_CONFIRM_PIN = "EVENT_GO_CONFIRM_PIN";
    public final static String EVENT_GO_CONFIRM_PIN_BACK = "EVENT_GO_CONFIRM_PIN_BACK";
    public final static String EVENT_GO_CONFIRM_CONTRA_BACK = "EVENT_GO_CONFIRM_CONTRA_BACK";
    public final static String EVENT_PAYMENT_SUCCES_QR = "EVENT_PAYMENT_SUCCES_QR";

    public final static String EVENT_COUCHMARK = "EVENT_GO_COUCHMARK";
    public final static String EVENT_GO_REGISTER_COMPLETE = "EVENT_GO_REGISTER_COMPLETE";
    public final static String EVENT_GO_MAINTAB = "EVENT_GO_MAINTAB";
    public final static String EVENT_GO_VENTAS = "EVENT_GO_VENTAS";
    public final static String EVENT_RECOVERY_PASS = "EVENT_RECOVERY_PASS";
    public final static String EVENT_RECOVERY_PASS_BACK = "EVENT_RECOVERY_PASS_BACK";
    public final static String EVENT_BLOCK_CARD = "EVENT_BLOCK_CARD";
    public final static String EVENT_BLOCK_CARD_BACK = "EVENT_BLOCK_CARD_BACK";
    public final static String EVENT_SECURE_CODE = "EVENT_SECURE_CODE";
    public final static String EVENT_SECURE_CODE_BACK = "EVENT_SECURE_CODE_BACK";
    public final static String EVENT_PAYMENT = "EVENT_PAYMENT";
    public final static String EVENT_QUICK_PAYMENT_BACK = "EVENT_QUICK_PAYMENT_BACK";
    public final static String EVENT_CONFIG_DONGLE = "EVENT_CONFIG_DONGLE";
    public final static String EVENT_RETRY_PAYMENT = "EVENT_RETRY_PAYMENT";
    public final static String EVENT_REWARDS = "EVENT_REWARDS";
    public final static String EVENT_STORES = "EVENT_STORES";
    public final static String EVENT_ADMIN_ADQ = "EVENT_ADMIN_ADQ";
    public final static String EVENT_OPERADOR_DETALLE = "EVENT_OPERADOR_DETALLE";
    public final static String EVENT_DETALLE_PROMO = "EVENT_DETALLE_PROMO";
    public final static String EVENT_CHECK_MONEY_CARD = "EVENT_CHECK_MONEY_CARD";
    public final static String SUCCES_CHANGE_STATUS_OPERADOR = "SUCCES_CHANGE_STATUS_OPERADOR";
    FrameLayout container;
    //private String TAG = getClass().getSimpleName();
    private Preferencias pref;
    private LoginManagerContainerFragment loginContainerFragment;
    private static AccountPresenterNew presenterAccount;
    App aplicacion;
    Boolean back = false;
    private boolean ayuda = false;

    private String action = "";

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String DIALOG_FRAGMENT_TAG = "myFragment";
    private static final String SECRET_MESSAGE = "Very secret message";
    private static final String KEY_NAME_NOT_INVALIDATED = "key_not_invalidated";
    static final String DEFAULT_KEY_NAME = "default_key";
    private FingerprintAuthenticationDialogFragment acceso;
    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;
    private SharedPreferences mSharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);*/
        setContentView(R.layout.activity_fragment_container);
        action = getIntent().getExtras().getString(SELECTION);
        pref = App.getInstance().getPrefs();
        resetRegisterData();
        setUpActionBar();
        ForcedUpdateChecker.with(this).onUpdateNeeded(this).check();
        App aplicacion = new App();
        presenterAccount = new AccountPresenterNew(this);
        loginContainerFragment = LoginManagerContainerFragment.newInstance();

        container = (FrameLayout) findViewById(R.id.container);

        if (getIntent().getExtras().getBoolean(IS_FROM_TIMER, false)) {
            //UI.createSimpleCustomDialog(getString(R.string.app_name), getString(R.string.close_sesion_bodynuevo),
            //      this.getSupportFragmentManager(), CustomErrorDialog.class.getSimpleName());
            UI.showAlertDialog(this, getString(R.string.app_name), getString(R.string.close_sesion_bodynuevo),
                    R.string.title_aceptar, (dialogInterface, i) -> {
                    });
        }
        switch (action) {
            case GO_TO_LOGIN:
                //App.getInstance().getPrefs().saveData(FIREBASE_KEY, FirebaseInstanceId.getInstance().getToken());
                showToolbarHelp(true);
                loadFragment(loginContainerFragment);
                break;

            case GO_TO_REGISTER:
                //App.getInstance().getPrefs().saveData(FIREBASE_KEY, FirebaseInstanceId.getInstance().getToken());
                loadFragment(DatosUsuarioFragment.newInstance());

                // TODO: 28/04/2017
                resetRegisterData();
                break;

            case EVENT_GO_HELP:
                showToolbarHelp(false);
                loadFragment(TutorialsFragment.newInstance(), Direction.FORDWARD);
                ayuda = true;
                break;
        }
    }

    public AccountPresenterNew getPresenter() {
        return this.presenterAccount;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVisibilityPrefer(false);
        // setVisibilityBack(back);
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }


    @Override
    public void onEvent(String event, Object o) {
        super.onEvent(event, o);
        Log.e(TAG, "onEvent - - " + event);
        switch (event) {

            case EVENT_PAYMENT_SUCCES_QR:
                PageResult pageResult = new PageResult(R.drawable.ic_check_success, this.getString(R.string.cancelation_success),
                        this.getString(R.string.succespaymentqr), false);
                pageResult.setNamerBtnPrimary(this.getString(R.string.continuar));
                //pageResult.setNamerBtnSecondary("Llamar");
                pageResult.setActionBtnPrimary(new Command() {
                    @Override
                    public void action(Context context, Object... params) {
                        onBackPressed();
                    }
                });
                pageResult.setBtnPrimaryType(PageResult.BTN_DIRECTION_NEXT);

                loadFragment(TransactionResultFragment.newInstance(pageResult), Direction.FORDWARD, false);

                break;

            case EVENT_GO_LOGIN:
                showToolbarHelp(true);
                loadFragment(loginContainerFragment, Direction.FORDWARD, false);
                break;

            case EVENT_RECOVERY_PASS:
                showToolbarHelp(false);
                loginContainerFragment.loadRecoveryFragment();
                break;

            case EVENT_RECOVERY_PASS_BACK:
                showToolbarHelp(true);
                loadFragment(loginContainerFragment, Direction.BACK, false);
                break;

            case EVENT_DATA_USER:
                loadFragment(DatosUsuarioFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_DATA_USER_BACK:
                loadFragment(DatosUsuarioFragment.newInstance(), Direction.BACK, false);
                break;
            case EVENT_DETALLE_PROMO:
                Intent intentpromo = new Intent(this, PromoCodesActivity.class);
                this.startActivity(intentpromo);
                break;

            case EVENT_PAYMENTQR:
                Intent intentqr = new Intent(this, ScannVisionActivity.class);
                intentqr.putExtra(ScannVisionActivity.QRObject, true);
                this.startActivityForResult(intentqr, BARCODE_READER_REQUEST_CODE_COMERCE);
                break;

            case EVENT_PERSONAL_DATA:
                RegisterUser registerUser = RegisterUser.getInstance();
                registerUser.setGenero("");
                registerUser.setNombre("");
                registerUser.setApellidoMaterno("");
                registerUser.setApellidoPaterno("");
                registerUser.setFechaNacimiento("");
                registerUser.setFechaNacimientoToShow("");
                registerUser.setLugarNacimiento("");
                loadFragment(DatosPersonalesFragment.newInstance(), Direction.FORDWARD, false);
                // loadFragment(LoginStarbucksFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case EVENT_SELFIE_BACK:
                registerUser = RegisterUser.getInstance();
                registerUser.setCalle("");
                registerUser.setNumExterior("");
                registerUser.setNumInterior("");
                registerUser.setCodigoPostal("");
                registerUser.setEstadoDomicilio("");
                registerUser.setColonia("");
                registerUser.setIdColonia("");
                loadFragment(DatosPersonalesFragment.newInstance(), Direction.BACK, false);
                showOmitir(false);
                break;

            case EVENT_PERSONAL_DATA_BACK:
                loadFragment(SelfieFragment.newInstance(), Direction.BACK, false);
                break;
            case EVENT_ADDRESS_DATA:
                ///Ahora cargamos la selfie
                loadFragment(DomicilioActualFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case EVENT_SELFIE:
                loadFragment(SelfieFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case EVENT_ADDRESS_DATA_BACK:
                ///aHORA CARGAMOS LA SELFIE
                registerUser = RegisterUser.getInstance();
                registerUser.setCalle("");
                registerUser.setNumExterior("");
                registerUser.setNumInterior("");
                registerUser.setCodigoPostal("");
                registerUser.setEstadoDomicilio("");
                registerUser.setColonia("");
                registerUser.setIdColonia("");
                loadFragment(DatosPersonalesFragment.newInstance(), Direction.BACK, false);
                break;
            case EVENT_GO_GET_CARD:
                back = true;
                setVisibilityBack(back);
                loadFragment(TienesTarjetaFragment.newInstance(), Direction.FORDWARD, true);

                break;
            case EVENT_GO_ASSIGN_NEW_CONTRASE:
                back = false;
                setVisibilityBack(back);
                loadFragment(NewConfirmPasswordLogin.newInstance(), Direction.FORDWARD, true);
                break;
            case EVENT_GO_CONFIRM_NEW_CONTRASE:
                back = false;
                setVisibilityBack(back);
                loadFragment(NewConfirmPasswordLogin.newInstance(), Direction.FORDWARD, true);
                break;
            case EVENT_GO_ASSIGN_PIN:
                changeToolbarVisibility(true);
                loadFragment(ConfirmarNIPFragment.newInstance("nada"), Direction.FORDWARD, false);
                break;
            case EVENT_GO_CONFIRM_PIN:
                loadFragment(ConfirmarNIPFragment.newInstance(o.toString()), Direction.FORDWARD, false);
                break;
            case EVENT_GO_CONFIRM_CONTRA_BACK:
                loadFragment(NewPasswordLoginChange.newInstance(), Direction.BACK, false);
                break;

            case EVENT_GO_CONFIRM_PIN_BACK:
                loadFragment(AsignarNIPFragment.newInstance(), Direction.BACK, false);
                break;
            case EVENT_GO_ASOCIATE_PHONE:
                showToolbarHelp(false);
                changeToolbarVisibility(true);
                showBack(false);
                loadFragment(AsociatePhoneAccountFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case EVENT_GO_REGISTER_COMPLETE:
                pref.clearPreference(COUCHMARK_EMISOR);
                pref.clearPreference(COUCHMARK_ADQ);
                showBack(false);
                loadFragment(RegisterCompleteFragment.newInstance(EMISOR), Direction.FORDWARD, false);
                break;
            case EVENT_APROV_FAILED:
            case EVENT_APROV_SUCCES:
                pref.clearPreference(COUCHMARK_EMISOR);
                pref.clearPreference(COUCHMARK_ADQ);
                showBack(false);
                loadFragment(RegisterCompleteFragment.newInstance(EMISOR), Direction.FORDWARD, false);
                break;
            case EVENT_COUCHMARK:
                loadFragment(Couchmark.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_BLOCK_CARD:
                showToolbarHelp(false);
                loginContainerFragment.loadBlockFragment();
                break;
            case EVENT_BLOCK_CARD_BACK:
                onBackPressed();
                break;
            case EVENT_SECURE_CODE:
                showToolbarHelp(false);
                loginContainerFragment.loadSecureCodeContainer();
                break;
            case EVENT_PAYMENT:
                showToolbarHelp(false);
                loginContainerFragment.loadQuickPayment();
                break;
            case EVENT_CHECK_MONEY_CARD:
                showToolbarHelp(false);
               /* if (App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal()
                        && App.getInstance().getPrefs().loadData(BT_PAIR_DEVICE).equals("")) {
                    loginContainerFragment.loadConfigDongle();
                } else {*/
                loginContainerFragment.loadGetBalanceClosedLoop();
                //}
                break;
            case EVENT_CONFIG_DONGLE:
                showToolbarHelp(false);
                loginContainerFragment.loadConfigDongle();
                break;
            case EVENT_REWARDS:
                showToolbarHelp(false);
                loginContainerFragment.loadRewards();
                break;
            case EVENT_STORES:
                showToolbarHelp(false);
                loginContainerFragment.loadStores();
                break;
            case EVENT_ADMIN_ADQ:
                showToolbarHelp(false);
                loginContainerFragment.loadConfigDongle();
                break;
            case EVENT_SECURE_CODE_BACK:
                showToolbarHelp(false);
                loadFragment(loginContainerFragment, Direction.BACK, false);
                break;
            case EVENT_GO_VENTAS:
                showToolbarHelp(false);
                loginContainerFragment.loadVentas();
                break;
            case EVENT_GO_MAINTAB:
                showToolbarHelp(false);
                resetRegisterData();
                /*
                 * Verificamos si las condiciones de Adquirente ya han sido cumplidas para mostrar pantalla
                 */
                SingletonUser user = SingletonUser.getInstance();
                DataIniciarSesionUYU dataUser = user.getDataUser();

                Preferencias prefs = App.getInstance().getPrefs();
                prefs.saveData(PHONE_NUMBER, SingletonUser.getInstance().getDataUser().getEmisor().getCuentas().get(0).getTelefono());
                prefs.saveData(CLABE_NUMBER, SingletonUser.getInstance().getDataUser().getEmisor().getCuentas().get(0).getCLABE());

                Intent intent = new Intent(AccountActivity.this, TabActivity.class);
                startActivity(intent);
                finish();
                break;
            case EVENT_GO_SELECT_DONGLE:
                showToolbarHelp(false);
                loadFragment(SelectDongleFragment.newInstance(), Direction.FORDWARD);
                break;
            case EVENT_GO_CONFIG_DONGLE:
                showToolbarHelp(false);
               /* if (App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                    loadFragment(PairBluetoothFragment.newInstance(), Direction.FORDWARD);
                } else {*/
                onBackPressed();
                //}
                break;
            case EVENT_GO_HELP:
                showToolbarHelp(false);
                loadFragment(TutorialsFragment.newInstance(), Direction.FORDWARD);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if (!isLoaderShow) {
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment instanceof LoginManagerContainerFragment) {
                loginContainerFragment.onBackActions();
                //finish();
            } else if (currentFragment instanceof DatosUsuarioFragment) {
                resetRegisterData();// Eliminamos la información de registro almacenada.
                showDialogOut();
            } else if (currentFragment instanceof DatosPersonalesFragment) {
                onEvent(EVENT_DATA_USER_BACK, null);
            } else if (currentFragment instanceof SelfieFragment) {
                onEvent(EVENT_SELFIE_BACK, null);
            } else if (currentFragment instanceof DomicilioActualFragment) {
                onEvent(EVENT_PERSONAL_DATA_BACK, null);
            } else if (currentFragment instanceof TienesTarjetaFragment) {
                resetRegisterData();// Eliminamos la información de registro almacenada.
                showDialogOut();
                /*if (((TienesTarjetaFragment) currentFragment).isCustomKeyboardVisible()) {
                    ((TienesTarjetaFragment) currentFragment).hideKeyboard();
                } else {
                    resetRegisterData();// Eliminamos la información de registro almacenada.
                    showDialogOut();
                }*/
            } else if (currentFragment instanceof AsignarNIPFragment) {
                if (((AsignarNIPFragment) currentFragment).isCustomKeyboardVisible()) {
                    //((AsignarNIPFragment) currentFragment).hideKeyboard();
                } else {
                    resetRegisterData();// Eliminamos la información de registro almacenada.
                    showDialogOut();
                }

            } else if (currentFragment instanceof ConfirmarNIPFragment) {
                resetRegisterData();// Eliminamos la información de registro almacenada.
                showDialogOut();

            } else if (currentFragment instanceof NewPasswordLoginChange) {
                if (((NewPasswordLoginChange) currentFragment).isCustomKeyboardVisible()) {
                    //((AsignarNIPFragment) currentFragment).hideKeyboard();
                } else {
                    resetRegisterData();// Eliminamos la información de registro almacenada.
                    showDialogOut();
                }

            } else if (currentFragment instanceof ConfirmarNIPFragment) {
                if (((ConfirmarNIPFragment) currentFragment).isCustomKeyboardVisible()) {
                    ((ConfirmarNIPFragment) currentFragment).hideKeyboard();
                } else {
                    //onEvent(EVENT_GO_CONFIRM_PIN_BACK, null);
                    showDialogOut();
                }
            } else if (currentFragment instanceof NewConfirmPasswordLogin) {
                if (((NewConfirmPasswordLogin) currentFragment).isCustomKeyboardVisible()) {
                    UI.hideKeyBoard(this);
                } else {
                    //onEvent(EVENT_GO_CONFIRM_CONTRA_BACK, null);
                    showDialogOut();
                }
            } else if (currentFragment instanceof AsociatePhoneAccountFragment) {
                showToolbarHelp(true);
                resetRegisterData();// Eliminamos la información de registro almacenada.
                showDialogOut();
            } else if (currentFragment instanceof RecoveryFragment) {
                showToolbarHelp(true);
                onEvent(EVENT_RECOVERY_PASS_BACK, null);
            } else if (currentFragment instanceof BlockCardFragment) {
                showToolbarHelp(true);
                //Toast.makeText(this, "Click Back. Main Responde", Toast.LENGTH_SHORT).show();
                onEvent(EVENT_BLOCK_CARD_BACK, null);
            } else if (currentFragment instanceof PairBluetoothFragment
                    || currentFragment instanceof SelectDongleFragment) {
                showToolbarHelp(true);
                loadFragment(loginContainerFragment, Direction.BACK, false);
            } else if (currentFragment instanceof TutorialsFragment && ayuda) {
                super.onBackPressed();
            } else if (currentFragment instanceof TutorialsFragment) {
                showToolbarHelp(true);
                loadFragment(loginContainerFragment, Direction.BACK, false);
            } else {
                resetRegisterData();// Eliminamos la información de registro almacenada.
                super.onBackPressed();
            }
        }
    }

    private void showDialogOut() {
        UI.showAlertDialog(this, getString(R.string.desea_cacelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
    }


    /*Se eliminan los datos almacenados en Memoria*/
    private void resetRegisterData() {
        RegisterUser.resetRegisterUser();
        Card.resetCardData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BARCODE_READER_REQUEST_CODE_COMERCE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                    if (barcode.displayValue.contains("reference") &&
                            barcode.displayValue.contains("commerce") && barcode.displayValue.contains("codevisivility")) {
                        MyQrCommerce myQr = new Gson().fromJson(barcode.displayValue, MyQrCommerce.class);
                        Log.d("Ya codigo qr", myQr.getCommerce());
                        Log.d("Ya codigo qr", myQr.getReference());
                        showBack(true);
                        loadFragment(PayQRFragment.newInstance(myQr.getCommerce(), myQr.getReference(), Boolean.parseBoolean(myQr.getCodevisivility())), Direction.FORDWARD);
                    } else {
                        UI.showErrorSnackBar(this, getString(R.string.transfer_qr_invalid), Snackbar.LENGTH_SHORT);
                    }
                }
            }
        }

        if (requestCode == Constants.PAYMENTS_ADQUIRENTE && resultCode == Activity.RESULT_OK) {
            loginContainerFragment.onActivityResult(requestCode, resultCode, data);
        } else {
            try {
                if (requestCode != BARCODE_READER_REQUEST_CODE_COMERCE) {
                    SelfieFragment mFragment = (SelfieFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                    CameraManager cameraManager = mFragment.getCameraManager();
                    // Enviamos datos recibidos al CameraManager
                    cameraManager.setOnActivityResult(requestCode, resultCode, data);
                }
            } catch (Exception e) {

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void showError(ErrorObject error) {

        UI.createSimpleCustomDialog("", error.getErrorMessage(), getSupportFragmentManager(), error.getErrorActions(), error.hasConfirm(), error.hasCancel());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AsociatePhoneAccountFragment myFragment = (AsociatePhoneAccountFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                    myFragment.onRequestPermissionsResult();
                    return;
                } else {
                    return;
                }
            }
        }
    }

    @Override
    public void generatecodehue(Fragment fm) {
        if (fm instanceof AccessCodeGenerateFragment)
            ((AccessCodeGenerateFragment) fm).loadOtpHuella();
        if (fm instanceof BlockCardFragment)
            ((BlockCardFragment) fm).loadOtpHuella();

    }

    @Override
    public void onUpdateNeeded() {
        App.getDatabaseReference().child("Version/Size_ADT").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long size_app = dataSnapshot.getValue(Long.class);
                    AlertDialog dialog = new AlertDialog.Builder(AccountActivity.this)
                            .setCancelable(false)
                            .setTitle(getString(R.string.title_update))
                            .setMessage(getString(R.string.text_update_forced))
                            .setPositiveButton("Actualizar",
                                    (dialog1, which) -> {
                                        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                                            Log.e(getString(R.string.app_name), "Tamaño App: " + (size_app * 1024)
                                                    + "  Tamaño Disponible: " + Long.valueOf(Utils.getAvailableInternalMemorySize()));
                                        }
                                        /* Validar el tamaño necesario para actualizar la App */
                                        if ((size_app * 1024) < Long.valueOf(Utils.getAvailableInternalMemorySize())) {
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse("market://details?id=" + App.getContext().getPackageName()));
                                            startActivity(i);
                                            killProcess(myPid());
                                        } else {
                                            showDialogUninstallApps();
                                        }
                                    })
                            .setNegativeButton("No gracias",
                                    (dialog12, which) -> killProcess(myPid())).create();
                    dialog.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showDialogUninstallApps() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(getString(R.string.title_free_space))
                .setMessage(getString(R.string.desc_free_space))
                .setPositiveButton("Liberar espacio",
                        (dialog1, which) -> {
                            Intent intent = new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
                            startActivity(intent);
                            killProcess(myPid());
                        })
                .setNegativeButton("Cancelar",
                        (dialog12, which) -> killProcess(myPid())).create();
        dialog.show();
    }
}

