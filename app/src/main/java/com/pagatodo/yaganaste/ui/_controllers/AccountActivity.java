package com.pagatodo.yaganaste.ui._controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.FrameLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.SingletonUser;
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
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.ForcedUpdateChecker;
import com.pagatodo.yaganaste.utils.UI;
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
import static com.pagatodo.yaganaste.utils.Recursos.ADQUIRENTE_APPROVED;
import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.DEBUG;
import static com.pagatodo.yaganaste.utils.Recursos.PHONE_NUMBER;

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
    //Nuevo diseño-flujo
    public final static String EVENT_DATA_USER = "EVENT_GO_DATA_USER";
    public final static String EVENT_DATA_USER_BACK = "EVENT_GO_DATA_USER_BACK";
    public final static String EVENT_PERSONAL_DATA = "EVENT_GO_PERSONAL_DATA";
    public final static String EVENT_PERSONAL_DATA_BACK = "EVENT_GO_PERSONAL_DATA_BACK";

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

    public final static String EVENT_COUCHMARK = "EVENT_GO_COUCHMARK";
    public final static String EVENT_GO_REGISTER_COMPLETE = "EVENT_GO_REGISTER_COMPLETE";
    public final static String EVENT_GO_MAINTAB = "EVENT_GO_MAINTAB";
    public final static String EVENT_RECOVERY_PASS = "EVENT_RECOVERY_PASS";
    public final static String EVENT_RECOVERY_PASS_BACK = "EVENT_RECOVERY_PASS_BACK";
    public final static String EVENT_BLOCK_CARD = "EVENT_BLOCK_CARD";
    public final static String EVENT_BLOCK_CARD_BACK = "EVENT_BLOCK_CARD_BACK";
    public final static String EVENT_SECURE_CODE = "EVENT_SECURE_CODE";
    public final static String EVENT_SECURE_CODE_BACK = "EVENT_SECURE_CODE_BACK";
    public final static String EVENT_QUICK_PAYMENT = "EVENT_QUICK_PAYMENT";
    public final static String EVENT_QUICK_PAYMENT_BACK = "EVENT_QUICK_PAYMENT_BACK";
    public final static String EVENT_RETRY_PAYMENT = "EVENT_RETRY_PAYMENT";
    public final static String EVENT_REWARDS = "EVENT_REWARDS";
    public final static String EVENT_STORES = "EVENT_STORES";
    FrameLayout container;
    //private String TAG = getClass().getSimpleName();
    private Preferencias pref;
    private LoginManagerContainerFragment loginContainerFragment;
    private static AccountPresenterNew presenterAccount;
    App aplicacion;
    Boolean back = false;

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
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
            UI.showAlertDialog(this, getString(R.string.app_name), getString(R.string.close_sesion_bodynuevo), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
        }
        switch (action) {
            case GO_TO_LOGIN:
                //App.getInstance().getPrefs().saveData(FIREBASE_KEY, FirebaseInstanceId.getInstance().getToken());
                loadFragment(loginContainerFragment);
                break;

            case GO_TO_REGISTER:
                //App.getInstance().getPrefs().saveData(FIREBASE_KEY, FirebaseInstanceId.getInstance().getToken());
                loadFragment(DatosUsuarioFragment.newInstance());

                // TODO: 28/04/2017
                resetRegisterData();
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
            case EVENT_GO_LOGIN:
                loadFragment(loginContainerFragment, Direction.FORDWARD, false);
                break;

            case EVENT_RECOVERY_PASS:
                loginContainerFragment.loadRecoveryFragment();
                break;

            case EVENT_RECOVERY_PASS_BACK:
                loadFragment(loginContainerFragment, Direction.BACK, false);
                break;

            case EVENT_DATA_USER:
                loadFragment(DatosUsuarioFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_DATA_USER_BACK:
                loadFragment(DatosUsuarioFragment.newInstance(), Direction.BACK, false);
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
                back = false;
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
                changeToolbarVisibility(true);
                showBack(false);
                loadFragment(AsociatePhoneAccountFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case EVENT_GO_REGISTER_COMPLETE:
            case EVENT_APROV_FAILED:
            case EVENT_APROV_SUCCES:
                pref.clearPreference(COUCHMARK_EMISOR);
                pref.clearPreference(COUCHMARK_ADQ);
                loadFragment(RegisterCompleteFragment.newInstance(EMISOR), Direction.FORDWARD, false);
                break;
            case EVENT_COUCHMARK:
                loadFragment(Couchmark.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_BLOCK_CARD:
                loginContainerFragment.loadBlockFragment();
                break;
            case EVENT_BLOCK_CARD_BACK:
                onBackPressed();
                break;
            case EVENT_SECURE_CODE:
                loginContainerFragment.loadSecureCodeContainer();
                break;
            case EVENT_QUICK_PAYMENT:
                loginContainerFragment.loadQuickPayment();
                break;
            case EVENT_REWARDS:
                loginContainerFragment.loadRewards();
                break;
            case EVENT_STORES:
                loginContainerFragment.loadStores();
                break;
            case EVENT_SECURE_CODE_BACK:
                loadFragment(loginContainerFragment, Direction.BACK, false);
                break;
            case EVENT_GO_MAINTAB:
                resetRegisterData();
                /*
                 * Verificamos si las condiciones de Adquirente ya han sido cumplidas para mostrar pantalla
                 */
                SingletonUser user = SingletonUser.getInstance();
                DataIniciarSesion dataUser = user.getDataUser();

                String tokenSesionAdquirente = dataUser.getUsuario().getTokenSesionAdquirente();

                Preferencias prefs = App.getInstance().getPrefs();
                prefs.saveData(PHONE_NUMBER, SingletonUser.getInstance().getDataUser().getUsuario().getCuentas().get(0).getTelefono());
                prefs.saveData(CLABE_NUMBER, SingletonUser.getInstance().getDataUser().getUsuario().getCuentas().get(0).getCLABE());
                boolean isAdquirente = prefs.containsData(ADQUIRENTE_APPROVED);

                // Lineas de prueba, comentar al tener version lista para pruebas
                if (!DEBUG) {
                    //Answers.getInstance().logLogin(new LoginEvent());
                }
                Intent intent = new Intent(AccountActivity.this, TabActivity.class);
                startActivity(intent);
                finish();
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
                resetRegisterData();// Eliminamos la información de registro almacenada.
                showDialogOut();
            } else if (currentFragment instanceof RecoveryFragment) {
                onEvent(EVENT_RECOVERY_PASS_BACK, null);
            } else if (currentFragment instanceof BlockCardFragment) {
                //Toast.makeText(this, "Click Back. Main Responde", Toast.LENGTH_SHORT).show();
                onEvent(EVENT_BLOCK_CARD_BACK, null);

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
        if (requestCode == Constants.PAYMENTS_ADQUIRENTE && resultCode == Activity.RESULT_OK) {
            loginContainerFragment.onActivityResult(requestCode, resultCode, data);
        } else {
            try {
                SelfieFragment mFragment = (SelfieFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                CameraManager cameraManager = mFragment.getCameraManager();
                // Enviamos datos recibidos al CameraManager
                cameraManager.setOnActivityResult(requestCode, resultCode, data);
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
    public void onUpdateNeeded(String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(getString(R.string.title_update))
                .setMessage(getString(R.string.text_update_forced))
                .setPositiveButton("Actualizar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse("market://details?id=" + App.getContext().getPackageName()));
                                startActivity(i);
                                killProcess(myPid());
                            }
                        })
                .setNegativeButton("No gracias",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                killProcess(myPid());
                            }
                        }).create();
        dialog.show();
    }
}

