package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.login.FingerprintAuthenticationDialogFragment;
import com.pagatodo.yaganaste.ui.account.login.FingerprintHandler;
import com.pagatodo.yaganaste.ui.payments.fragments.PaymentAuthorizeFragment;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentAuthorizeManager;
import com.pagatodo.yaganaste.ui.payments.presenters.PaymentAuthorizePresenter;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentAuthorizePresenter;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.EVENT_SEND_PAYMENT;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.PASSWORD_CHANGE;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;

/**
 * Created by Armando Sandoval on 10/01/2018.
 */

public class PaymentAuthorizeFragmentWallwt extends GenericFragment implements View.OnClickListener,
        PaymentAuthorizeManager, FingerprintHandler.generateCode {

    private static final String KEY_NAME = "yourKey";
    private static final String KEY_NAME_NOT_INVALIDATED = "key_not_invalidated";
    private static final String DIALOG_FRAGMENT_TAG = "myFragment";
    static final String DEFAULT_KEY_NAME = "default_key";

    IPaymentAuthorizePresenter paymentAuthorizePresenter;

    @BindView(R.id.txt_importe)
    MontoTextView importe;
    @BindView(R.id.btn_continueEnvio)
    StyleButton btnContinueEnvio;

    @BindView(R.id.txtReferencia)
    StyleTextView txtReferencia;

    @BindView(R.id.txt_data)
    StyleTextView txtnombredestinatario;
    @BindView(R.id.editUserPassword)
    EditText editPassword;

    String password;
    private Envios envio;
    private View rootview;

    private CustomErrorDialog customErrorDialog;
    private KeyguardManager keyguardManager;
    private FingerprintManager fingerprintManager;
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;
    private String texto;
    private String titulo;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private static Preferencias preferencias = App.getInstance().getPrefs();
    private SharedPreferences mSharedPreferences;
    static PaymentAuthorizeFragment fragmentCode;

    private String nip = "";
    private Keyboard keyboard;
    ImageView asignar_iv1;
    private static int PIN_LENGHT = 6;
    private Preferencias prefs = App.getInstance().getPrefs();

    public static PaymentAuthorizeFragment newInstance(Payments envio) {
        fragmentCode = new PaymentAuthorizeFragment();
        //PaymentAuthorizeFragment fragment = new PaymentAuthorizeFragment();
        Bundle args = new Bundle();
        args.putSerializable("envios", envio);
        fragmentCode.setArguments(args);
        return fragmentCode;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        envio = (Envios) getArguments().getSerializable("envios");
        paymentAuthorizePresenter = new PaymentAuthorizePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_authorize_payment_wallet, container, false);
        titulo = getString(R.string.titulo_dialogo_huella_generacion_codigo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
            keyguardManager = getActivity().getSystemService(KeyguardManager.class);
            fingerprintManager = getActivity().getSystemService(FingerprintManager.class);
        }
        texto = getString(R.string.authorize_payment_title);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        if (prefs.loadDataBoolean(PASSWORD_CHANGE, false)) {
            editPassword.setVisibility(View.GONE);
            btnContinueEnvio.setVisibility(View.GONE);
            editPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() == 6) {
                        UI.hideKeyBoard(getActivity());
                        //  Servicio para consumir usuario y contraseña
                        validateForm();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            setValidationRules();
            editPassword.requestFocus();
        } else {
            editPassword.setVisibility(View.VISIBLE);
        }

        txtnombredestinatario.setText(envio.getNombreDestinatario());
        importe.setText(StringUtils.getCurrencyValue(envio.getMonto()));


        String ref = envio.getReferencia();
        setValidationRules();
        btnContinueEnvio.setOnClickListener(this);

        //Huella
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!fingerprintManager.isHardwareDetected()) {
            } else if (!keyguardManager.isKeyguardSecure()) {
                return;
            } else {
                try {
                    keyStore = KeyStore.getInstance("AndroidKeyStore");
                } catch (KeyStoreException e) {
                    throw new RuntimeException("Failed to get an instance of KeyStore", e);
                }
                try {
                    keyGenerator = KeyGenerator
                            .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
                } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                    throw new RuntimeException("Failed to get an instance of KeyGenerator", e);
                }
                Cipher defaultCipher;
                Cipher cipherNotInvalidated;
                try {
                    defaultCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
                    cipherNotInvalidated = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
                } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                    throw new RuntimeException("Failed to get an instance of Cipher", e);
                }
                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
                createKey(DEFAULT_KEY_NAME, true);
                createKey(KEY_NAME_NOT_INVALIDATED, false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && App.getInstance().getPrefs().loadDataBoolean(USE_FINGERPRINT, true)) {
                    if (initCipher(cipherNotInvalidated, KEY_NAME_NOT_INVALIDATED)) {

                        // Show the fingerprint dialog. The user has the option to use the fingerprint with
                        // crypto, or you can fall back to using a server-side verified password.
                        FingerprintAuthenticationDialogFragment fragment
                                = new FingerprintAuthenticationDialogFragment();
                        fragment.setCryptoObject(new FingerprintManager.CryptoObject(cipherNotInvalidated));
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
                        fragment.setFragmentInstance(fragmentCode);
                        fragment.show(getActivity().getFragmentManager(), DIALOG_FRAGMENT_TAG);
                    } else {
                        // This happens if the lock screen has been disabled or or a fingerprint got
                        // enrolled. Thus show the dialog to authenticate with their password first
                        // and ask the user if they want to authenticate with fingerprints in the
                        // future
                        FingerprintAuthenticationDialogFragment fragment
                                = new FingerprintAuthenticationDialogFragment();
                        fragment.setCryptoObject(new FingerprintManager.CryptoObject(cipherNotInvalidated));
                        fragment.setStage(
                                FingerprintAuthenticationDialogFragment.Stage.NEW_FINGERPRINT_ENROLLED);
                        fragment.setFragmentInstance(fragmentCode);
                        fragment.show(getActivity().getFragmentManager(), DIALOG_FRAGMENT_TAG);
                    }

                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean initCipher(Cipher cipher, String keyName) {
        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(keyName, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            //throw new RuntimeException("Failed to init Cipher", e);
            return false;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
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
            keyStore.load(null);
            // Set the alias of the entry in Android KeyStore where the key will appear
            // and the constrains (purposes) in the constructor of the Builder

            KeyGenParameterSpec.Builder builder = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                builder = new KeyGenParameterSpec.Builder(keyName,
                        KeyProperties.PURPOSE_ENCRYPT |
                                KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        // Require the user to authenticate with a fingerprint to authorize every use
                        // of the key
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            }

            // This is a workaround to avoid crashes on devices whose API level is < 24
            // because KeyGenParameterSpec.Builder#setInvalidatedByBiometricEnrollment is only
            // visible on API level +24.
            // Ideally there should be a compat library for KeyGenParameterSpec.Builder but
            // which isn't available yet.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setInvalidatedByBiometricEnrollment(invalidatedByBiometricEnrollment);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyGenerator.init(builder.build());
            }
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    public void stopautentication() {
        customErrorDialog.setTitleMessageNotification(getString(R.string.fingerprint_verification));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_continueEnvio) {
            validateForm();
        }
    }

    @Override
    public void setValidationRules() {
        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editPassword.getId());
                }
            }
        });
    }

    @Override
    public void validateForm() {
        getDataForm();
        if (TextUtils.isEmpty(password)) {
            showValidationError(0, getString(R.string.datos_usuario_pass));
        } else {
            paymentAuthorizePresenter.validatePasswordFormat(password);
        }
    }

    @Override
    public void showValidationError(int id, Object error) {
        // Se elemina el mostrar errors debajo del campo de contrasenia ahora sera un PopUp
        //errorPasswordMessage.setMessageText(error.toString());
        showError(error.toString());
    }

    @Override
    public void hideValidationError(int id) {
    }

    @Override
    public void onValidationSuccess() {
        onEventListener.onEvent(EVENT_SEND_PAYMENT, envio);
    }

    @Override
    public void getDataForm() {
        password = editPassword.getText().toString().trim();
    }

    @Override
    public void showError(Object error) {
        hideLoader();

        /**
         * Comparamos la cadena que entrega el Servicio o el Presentes, con los mensajes que
         * tenemos en el archivo de Strings, dependiendo del mensaje, hacemos un set al errorTittle
         * para mostrarlo en el UI.createSimpleCustomDialog
         */
        String errorTittle = "";
        String errorBody = "";
        errorTittle = "Contraseña Inválida";
        errorBody = "La Contraseña Ingresada no es Válida, Verifícala";

        if (error.toString().equals(getString(R.string.error_codigo_de_seguridad))) {
            errorTittle = "Contraseña Inválida";
            errorBody = "La Contraseña Ingresada no es Válida, Verifícala";
            //UI.createSimpleCustomDialog(errorTittle, errorBody, getActivity().getSupportFragmentManager(), getFragmentTag());
            UI.showAlertDialog(getContext(), errorTittle, errorBody, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

        } else if (error.toString().equals(getString(R.string.no_internet_access))) {
            errorTittle = "Ya Ganaste";
            errorBody = getString(R.string.no_internet_access);
            //UI.createSimpleCustomDialog(errorTittle, errorBody, getActivity().getSupportFragmentManager(), getFragmentTag());
            UI.showAlertDialog(getContext(), errorTittle, errorBody, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });


        } else if (!TextUtils.isEmpty(error.toString())) {
            //UI.createSimpleCustomDialog(errorTittle, errorBody, getActivity().getSupportFragmentManager(), getFragmentTag());
            UI.showAlertDialog(getContext(), errorTittle, errorBody, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
        }
    }

    @Override
    public void validationPasswordSucces() {
        paymentAuthorizePresenter.generateOTP(Utils.getSHA256(password));
    }

    @Override
    public void validationPasswordFailed(String error) {
        hideLoader();
        showValidationError(0, error);
    }

    @Override
    public void showLoader(String title) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, title);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void onOtpGenerated(String otp) {
        RequestHeaders.setTokenFreja(otp);
        onEventListener.onEvent(EVENT_SEND_PAYMENT, envio);
    }

    @Override
    public void showError(final Errors error) {
        hideLoader();

        DialogDoubleActions actions = new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                if (error.allowsReintent()) {
                    paymentAuthorizePresenter.generateOTP(Utils.getSHA256(password));
                } else {
                    getActivity().onBackPressed();
                }
            }

            @Override
            public void actionCancel(Object... params) {
                getActivity().onBackPressed();
            }
        };

        UI.createSimpleCustomDialog("", error.getMessage(), getActivity().getSupportFragmentManager(),
                actions, true, error.allowsReintent());
    }


    @Override
    public void generatecode() {
        loadOtpHuella();
    }

    @Override
    public void generatecode(String mensaje) {
        Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(100);
        customErrorDialog.setTitleMessageNotification(mensaje);
    }

    @Override
    public void generatecode(String mensaje, int errors) {
        if (errors == 4) {
            Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(100);
            customErrorDialog.setTitleMessageNotification(getString(R.string.fingerprint_failed));
        }
    }

    public void loadOtpHuella() {
        paymentAuthorizePresenter.generateOTP(preferencias.loadData("SHA_256_FREJA"));

    }

    private class FingerprintException extends Exception {

        public FingerprintException(Exception e) {
            super(e);
        }
    }
}
