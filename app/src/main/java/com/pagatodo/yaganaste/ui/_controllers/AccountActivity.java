package com.pagatodo.yaganaste.ui._controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.LoginEvent;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesion;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.login.AccessCodeGenerateFragment;
import com.pagatodo.yaganaste.ui.account.login.BlockCardFragment;
import com.pagatodo.yaganaste.ui.account.login.FingerprintAuthenticationDialogFragment;
import com.pagatodo.yaganaste.ui.account.login.LoginManagerContainerFragment;
import com.pagatodo.yaganaste.ui.account.login.RecoveryFragment;
import com.pagatodo.yaganaste.ui.account.register.AsignarNIPFragment;
import com.pagatodo.yaganaste.ui.account.register.AsociatePhoneAccountFragment;
import com.pagatodo.yaganaste.ui.account.register.ConfirmarNIPFragment;
import com.pagatodo.yaganaste.ui.account.register.Couchmark;
import com.pagatodo.yaganaste.ui.account.register.DatosPersonalesFragment;
import com.pagatodo.yaganaste.ui.account.register.DatosUsuarioFragment;
import com.pagatodo.yaganaste.ui.account.register.DomicilioActualFragment;
import com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment;
import com.pagatodo.yaganaste.ui.account.register.TienesTarjetaFragment;
import com.pagatodo.yaganaste.ui_wallet.WalletMainActivity;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static com.pagatodo.yaganaste.freja.provisioning.presenter.ProvisioningPresenterAbs.EVENT_APROV_FAILED;
import static com.pagatodo.yaganaste.freja.provisioning.presenter.ProvisioningPresenterAbs.EVENT_APROV_SUCCES;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.GO_TO_LOGIN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.GO_TO_REGISTER;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.IS_FROM_TIMER;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.ui.account.register.AsociatePhoneAccountFragment.MY_PERMISSIONS_REQUEST_SEND_SMS;
import static com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment.COMPLETE_MESSAGES.EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.DEBUG;
import static com.pagatodo.yaganaste.utils.StringConstants.ADQUIRENTE_APPROVED;


public class AccountActivity extends LoaderActivity implements OnEventListener, FingerprintAuthenticationDialogFragment.generateCodehuella {
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
    public final static String EVENT_ADDRESS_DATA = "EVENT_GO_ADDRESS_DATA";
    public final static String EVENT_ADDRESS_DATA_BACK = "EVENT_GO_ADDRESS_DATA_BACK";
    public final static String EVENT_GO_ASSIGN_PIN = "EVENT_GO_ASSIGN_PIN";
    public final static String EVENT_GO_CONFIRM_PIN = "EVENT_GO_CONFIRM_PIN";
    public final static String EVENT_GO_CONFIRM_PIN_BACK = "EVENT_GO_CONFIRM_PIN_BACK";
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
    FrameLayout container;
    //private String TAG = getClass().getSimpleName();
    private Preferencias pref;
    private LoginManagerContainerFragment loginContainerFragment;
    private static AccountPresenterNew presenterAccount;
    App aplicacion;
    Boolean back = false;

    private String action = "";
    AccessCodeGenerateFragment obj;

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
        setContentView(R.layout.activity_fragment_conainer);
        action = getIntent().getExtras().getString(SELECTION);
        pref = App.getInstance().getPrefs();
        resetRegisterData();
        setUpActionBar();
        obj = new AccessCodeGenerateFragment();

        App aplicacion = new App();
        presenterAccount = new AccountPresenterNew(this);
        loginContainerFragment = LoginManagerContainerFragment.newInstance();

        container = (FrameLayout) findViewById(R.id.container);

        if (getIntent().getExtras().getBoolean(IS_FROM_TIMER, false)) {
            UI.createSimpleCustomDialog(getString(R.string.app_name), getString(R.string.close_sesion_bodynuevo),
                    this.getSupportFragmentManager(), CustomErrorDialog.class.getSimpleName());
        }
        switch (action) {
            case GO_TO_LOGIN:
                loadFragment(loginContainerFragment, Direction.FORDWARD, false);
                break;

            case GO_TO_REGISTER:
                loadFragment(DatosUsuarioFragment.newInstance(), Direction.FORDWARD, false);

                // TODO: 28/04/2017
                resetRegisterData();
                break;
        }
/*
        /*Validamos Permisos
        ValidatePermissions.checkPermissions(this, new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);*/
    }

    /**
     * Creates a symmetric key in the Android Key Store which can only be used after the user has
     * authenticated with fingerprint.
     *
     * @param keyName                          the name of the key to be created
     * @param invalidatedByBiometricEnrollment if {@code false} is passed, the created key will not
     *                                         be invalidated even if a new fingerprint is enrolled.
     *                                         The default value is {@code true}, so passing
     *                                         {@code true} doesn't change the behavior
     *                                         (the key will be invalidated if a new fingerprint is
     *                                         enrolled.). Note that this parameter is only valid if
     *                                         the app works on Android N developer preview.
     */
    public void createKey(String keyName, boolean invalidatedByBiometricEnrollment) {
        // The enrolling flow for fingerprint. This is where you ask the user to set up fingerprint
        // for your flow. Use of keys is necessary if you need to know if the set of
        // enrolled fingerprints has changed.
        try {
            mKeyStore.load(null);
            // Set the alias of the entry in Android KeyStore where the key will appear
            // and the constrains (purposes) in the constructor of the Builder

            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(keyName,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    // Require the user to authenticate with a fingerprint to authorize every use
                    // of the key
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);

            // This is a workaround to avoid crashes on devices whose API level is < 24
            // because KeyGenParameterSpec.Builder#setInvalidatedByBiometricEnrollment is only
            // visible on API level +24.
            // Ideally there should be a compat library for KeyGenParameterSpec.Builder but
            // which isn't available yet.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setInvalidatedByBiometricEnrollment(invalidatedByBiometricEnrollment);
            }
            mKeyGenerator.init(builder.build());
            mKeyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Proceed the purchase operation
     *
     * @param withFingerprint {@code true} if the purchase was made by using a fingerprint
     * @param cryptoObject    the Crypto object
     */
    public void onPurchased(boolean withFingerprint,
                            @Nullable FingerprintManager.CryptoObject cryptoObject) {
        if (withFingerprint) {
            // If the user has authenticated with fingerprint, verify that using cryptography and
            // then show the confirmation message.
            assert cryptoObject != null;
            tryEncrypt(cryptoObject.getCipher());

        } else {
            // Authentication happened with backup password. Just show the confirmation message.

            showConfirmation(null);
        }
    }

    // Show confirmation, if fingerprint was used show crypto information.
    private void showConfirmation(byte[] encrypted) {
        //findViewById(R.id.confirmation_message).setVisibility(View.VISIBLE);
        if (encrypted != null) {
            TextView v = (TextView) findViewById(R.id.encrypted_message);
          //  v.setVisibility(View.VISIBLE);
            v.setText(Base64.encodeToString(encrypted, 0 /* flags */));
        }
    }

    /**
     * Tries to encrypt some data with the generated key in {@link #createKey} which is
     * only works if the user has just authenticated via fingerprint.
     */
    private void tryEncrypt(Cipher cipher) {
        try {
            byte[] encrypted = cipher.doFinal(SECRET_MESSAGE.getBytes());
            showConfirmation(encrypted);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            Toast.makeText(this, "Failed to encrypt the data with the generated key. "
                    + "Retry the purchase", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Failed to encrypt the data with the generated key." + e.getMessage());
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
                //loadFragment(DocumentosFragment.newInstance(), Direction.FORDWARD, false);
                //loadFragment(RecoveryFragment.newInstance(), Direction.FORDWARD, false);
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
                break;

            case EVENT_PERSONAL_DATA_BACK:
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
            case EVENT_ADDRESS_DATA:
                loadFragment(DomicilioActualFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case EVENT_ADDRESS_DATA_BACK:
                loadFragment(DomicilioActualFragment.newInstance(), Direction.BACK, false);
                break;
            case EVENT_GO_GET_CARD:
                back = false;
                setVisibilityBack(back);
                loadFragment(TienesTarjetaFragment.newInstance(), Direction.FORDWARD, true);

                break;
            case EVENT_GO_ASSIGN_PIN:
                loadFragment(AsignarNIPFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_GO_CONFIRM_PIN:
                loadFragment(ConfirmarNIPFragment.newInstance(o.toString()), Direction.FORDWARD, false);
                break;
            case EVENT_GO_CONFIRM_PIN_BACK:
                loadFragment(AsignarNIPFragment.newInstance(), Direction.BACK, false);
                break;
            case EVENT_GO_ASOCIATE_PHONE:
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
                //loadFragment(BlockCardFragment.newInstance(), Direction.FORDWARD, false);
                loginContainerFragment.loadBlockFragment();
                break;

            case EVENT_BLOCK_CARD_BACK:
                // loadFragment(loginContainerFragment.newInstance(), Direction.BACK, false);
                //loginContainerFragment.loadLoginBackFragment();
                onBackPressed();
                break;

            case EVENT_SECURE_CODE:
                //loadFragment(OtpContainerFratgment.newInstance(), Direction.FORDWARD, false);
                loginContainerFragment.loadSecureCodeContainer();
                break;

            case EVENT_QUICK_PAYMENT:
                //loadFragment(OtpContainerFratgment.newInstance(), Direction.FORDWARD, false);
                loginContainerFragment.loadQuickPayment();
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
                boolean isAdquirente = prefs.containsData(ADQUIRENTE_APPROVED);

                // Lineas de prueba, comentar al tener version lista para pruebas
                //tokenSesionAdquirente = "MiSuperTokenAdquirente";
                //isAdquirente = "";
                if (!DEBUG) {
                    Answers.getInstance().logLogin(new LoginEvent());
                }
                if (tokenSesionAdquirente != null && !tokenSesionAdquirente.isEmpty() && !isAdquirente) {
                    // getActivity().finish();
                    Intent intent = new Intent(AccountActivity.this, LandingApprovedActivity.class);
                    startActivity(intent);
                } else {
                    //Intent intent = new Intent(AccountActivity.this, TabActivity.class);
                    Intent intent = new Intent(AccountActivity.this, WalletMainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;


        }
    }

    private boolean initCipher(Cipher cipher, String keyName) {
        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(keyName, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
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
                if (((ConfirmarNIPFragment) currentFragment).isCustomKeyboardVisible()) {
                    ((ConfirmarNIPFragment) currentFragment).hideKeyboard();
                } else {
                    onEvent(EVENT_GO_CONFIRM_PIN_BACK, null);
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
        UI.createSimpleCustomDialog("", getString(R.string.desea_cacelar), getSupportFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        //aplicacion.cerrarAppsms();
                        finish();

                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                }, true, true);
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
            ((AccessCodeGenerateFragment)fm).loadOtpHuella();
        if (fm instanceof BlockCardFragment)
            ((BlockCardFragment)fm).loadOtpHuella();

    }

    private class PurchaseButtonClickListener implements View.OnClickListener {

        Cipher mCipher;
        String mKeyName;

        PurchaseButtonClickListener(Cipher cipher, String keyName) {
            mCipher = cipher;
            mKeyName = keyName;
        }

        @Override
        public void onClick(View view) {
            findViewById(R.id.confirmation_message).setVisibility(View.GONE);
            findViewById(R.id.encrypted_message).setVisibility(View.GONE);

            // Set up the crypto object for later. The object will be authenticated by use
            // of the fingerprint.
            if (initCipher(mCipher, mKeyName)) {

                // Show the fingerprint dialog. The user has the option to use the fingerprint with
                // crypto, or you can fall back to using a server-side verified password.
                FingerprintAuthenticationDialogFragment fragment
                        = new FingerprintAuthenticationDialogFragment();

                fragment.setCryptoObject(new FingerprintManager.CryptoObject(mCipher));
                boolean useFingerprintPreference = mSharedPreferences
                        .getBoolean(getString(R.string.use_fingerprint_to_authenticate_key),
                                true);
                if (useFingerprintPreference) {
                    fragment.setStage(
                            FingerprintAuthenticationDialogFragment.Stage.FINGERPRINT);
                } else {
                    fragment.setStage(
                            FingerprintAuthenticationDialogFragment.Stage.PASSWORD);
                }
                fragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
            } else {
                // This happens if the lock screen has been disabled or or a fingerprint got
                // enrolled. Thus show the dialog to authenticate with their password first
                // and ask the user if they want to authenticate with fingerprints in the
                // future
                FingerprintAuthenticationDialogFragment fragment
                        = new FingerprintAuthenticationDialogFragment();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    fragment.setCryptoObject(new FingerprintManager.CryptoObject(mCipher));
                }
                fragment.setStage(
                        FingerprintAuthenticationDialogFragment.Stage.NEW_FINGERPRINT_ENROLLED);
                fragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
            }
        }
    }
}

