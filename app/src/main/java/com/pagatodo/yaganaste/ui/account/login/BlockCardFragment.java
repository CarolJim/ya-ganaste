package com.pagatodo.yaganaste.ui.account.login;


import android.app.KeyguardManager;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.ILoginView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
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
import butterknife.OnClick;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;
import static com.pagatodo.yaganaste.utils.Recursos.PSW_CPR;

/**
 * Encargada de bloquear la Card por medio de nuestra contraseña.
 * 1 - Pedimos contraseña
 * 2 - Iniciamos session rapida
 * 3 - Actualizamos el estado de la session
 * 4 - Cerramos session
 */
public class BlockCardFragment extends GenericFragment implements ValidationForms,
        ILoginView, IMyCardView {

    ///////////////
    private static Preferencias preferencias = App.getInstance().getPrefs();
    private Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private FingerprintManager.CryptoObject cryptoObject;
    private Preferencias prefs = App.getInstance().getPrefs();
    FingerprintAuthenticationDialogFragment fragment;
    ///////////////7

    public static final int BLOQUEO = 1;
    public static final int DESBLOQUEO = 2;

    @BindView(R.id.img_block_card)
    ImageView imgBlockCard;
    @BindView(R.id.rly_unblock_card)
    RelativeLayout rlyUnblockCard;
    @BindView(R.id.txt_title_block)
    StyleTextView txtTitle;
    @BindView(R.id.txtMessageCard)
    StyleTextView txtMessageCard;
    @BindView(R.id.edt_block_password)
    EditText edtPin;
    @BindView(R.id.btn_block_card)
    StyleButton btnBlockCard;

    @BindView(R.id.text_password)
    TextInputLayout text_password;

    private String password;
    private View rootview;
    private AccountPresenterNew accountPresenter;
    private PreferUserPresenter mPreferPresenter;
    private String cardStatusId;
    private int statusBloqueo;
    ////////////

    private static final String TAG = AccessCodeGenerateFragment.class.getSimpleName();

    private static final String DIALOG_FRAGMENT_TAG = "myFragment";
    private static final String SECRET_MESSAGE = "Very secret message";
    private static final String KEY_NAME_NOT_INVALIDATED = "key_not_invalidated";
    static final String DEFAULT_KEY_NAME = "default_key";

    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;
    private SharedPreferences mSharedPreferences;

    KeyguardManager keyguardManager;
    FingerprintManager fingerprintManager;

    static BlockCardFragment fragmentCode;


    public BlockCardFragment() {
        // Required empty public constructor
    }

    public static BlockCardFragment newInstance() {
        fragmentCode = new BlockCardFragment();
        Bundle args = new Bundle();
        fragmentCode.setArguments(args);
        return fragmentCode;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
            keyguardManager = getActivity().getSystemService(KeyguardManager.class);
            fingerprintManager = getActivity().getSystemService(FingerprintManager.class);
        }
        if (getArguments() != null) {
            accountPresenter = ((AccountActivity) getActivity()).getPresenter();
            accountPresenter.setIView(this);
            mPreferPresenter = new PreferUserPresenter(this);
            mPreferPresenter.setIView(this);

            // Consultamos el estado del Singleton, que tiene el estado de nuestra tarjeta
            cardStatusId = App.getInstance().getStatusId();
            // cardStatusId = "1"; // Linea de TEst, eliminamos cuando el anterior funcione en actualizar
            if (cardStatusId == null) {
                cardStatusId = "1";
            }

            if (cardStatusId.equals("2")) {
                // Significa que la card esta bloqueada, despues de la operacion pasa a desbloqueada
                statusBloqueo = BLOQUEO;
            } else {
                // Significa que la card esta desbloqueada, despues de la operacion pasa a bloqueada
                statusBloqueo = DESBLOQUEO;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_block_card, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        setValidationRules();


        edtPin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    text_password.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    text_password.setBackgroundResource(R.drawable.inputtext_normal);
                }
            }
        });

        if (cardStatusId.equals("1")) {
            // La tarjeta esta DESBLOQUEADA, mostramos la cCard Azul
            rlyUnblockCard.setVisibility(View.GONE);
            imgBlockCard.setVisibility(View.VISIBLE);

            // Cambiamos las palabras a Bloquear
            txtTitle.setText(App.getContext().getResources().getString(R.string.block_your_card_title));
            txtMessageCard.setText(App.getContext().getResources().getString(R.string.block_your_card_subtitle));
            btnBlockCard.setText(App.getContext().getResources().getString(R.string.txt_bloquear));
        } else {
            // La tarjeta esta BLOQUEADA, mostramos la cCard Gray
            rlyUnblockCard.setVisibility(View.VISIBLE);
            imgBlockCard.setVisibility(View.GONE);

            // Cambiamos las palabras a Desboquear
            txtTitle.setText(App.getContext().getResources().getString(R.string.unblock_your_card_title));
            txtMessageCard.setText(App.getContext().getResources().getString(R.string.unblock_your_card_subtitle));
            btnBlockCard.setText(App.getContext().getResources().getString(R.string.txt_desbloquear));
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !preferencias.loadData(PSW_CPR).equals("")) {
            if (!fingerprintManager.isHardwareDetected()) {

            } else {
                try {
                    mKeyStore = KeyStore.getInstance("AndroidKeyStore");
                } catch (KeyStoreException e) {
                    throw new RuntimeException("Failed to get an instance of KeyStore", e);
                }
                try {
                    mKeyGenerator = KeyGenerator
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
                        fragment
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

                if (!keyguardManager.isKeyguardSecure()) {
                    return;
                }

                // Now the protection level of USE_FINGERPRINT permission is normal instead of dangerous.
                // See http://developer.android.com/reference/android/Manifest.permission.html#USE_FINGERPRINT
                // The line below prevents the false positive inspection from Android Studio
                // noinspection ResourceType
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    // This happens when no fingerprints are registered.
                    return;
                }
            }
        }


    }


    public void loadOtpHuella() {
        //// Succes de la validación de huella digital
        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            Preferencias preferencias = App.getInstance().getPrefs();
            String[] pass = Utils.cipherAES(preferencias.loadData(PSW_CPR), false).split("-");
            accountPresenter.login(RequestHeaders.getUsername(), pass[0]);
            onEventListener.onEvent(EVENT_SHOW_LOADER, getContext().getString(R.string.update_data));
        } else {
            UI.showErrorSnackBar(getActivity(), getResources().getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
        }

    }


    /**
     * Initialize the {@link Cipher} instance with the created key in the
     * {@link #createKey(String, boolean)} method.
     *
     * @param keyName the key name to init the cipher
     * @return {@code true} if initialization is successful, {@code false} if the lock screen has
     * been disabled or reset after the key was generated, or if a fingerprint got enrolled after
     * the key was generated.
     */
    private boolean initCipher(Cipher cipher, String keyName) {
        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(keyName, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            //throw new RuntimeException("Failed to init Cipher", e);
            return false;
        }
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

            KeyGenParameterSpec.Builder builder = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                builder = new KeyGenParameterSpec.Builder(keyName,
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
            }
            mKeyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_block_card)
    public void validatePass() {
        validateForm();
    }

    @Override
    public void setValidationRules() {
        edtPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 6) {
                    //  Servicio para consumir usuario y contraseña
                    validateForm();
                    edtPin.setText("");
                    edtPin.isFocused();
                    UI.hideKeyBoard(getActivity());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtPin.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                //edtPin.imageViewIsGone(true);
            } else {
                if (edtPin.getText().toString().isEmpty()) {

                } else {

                }
            }
        });
    }

    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;
        String errorMsg = null;

        if (password.isEmpty()) {
            errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.password_required) : errorMsg;
            //edtPin.setIsInvalid();
            isValid = false;
        }
        if (password.length() < 6) {
            errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.password_required_seis) : errorMsg;
            //edtPin.setIsInvalid();
            isValid = false;
        }
        if (isValid) {
            boolean isOnline = Utils.isDeviceOnline();
            if (isOnline) {
                onValidationSuccess();
            } else {
                UI.showErrorSnackBar(getActivity(), getResources().getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
            }
        } else {
            UI.showErrorSnackBar(getActivity(), errorMsg, Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        Preferencias preferencias = App.getInstance().getPrefs();
        accountPresenter.login(RequestHeaders.getUsername(), password);
        onEventListener.onEvent(EVENT_SHOW_LOADER, getContext().getString(R.string.update_data));
    }

    @Override
    public void getDataForm() {
        password = edtPin.getText().toString().trim();
    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, getContext().getString(R.string.update_data));
        if (cardStatusId.equals("1")) {
            // Operacion para Bloquear tarjeta
            mPreferPresenter.toPresenterBloquearCuenta(BLOQUEO);
            statusBloqueo = BLOQUEO;
        } else {
            // Operacion para Desbloquear tarjeta
            mPreferPresenter.toPresenterBloquearCuenta(DESBLOQUEO);
            statusBloqueo = DESBLOQUEO;
        }

    }

    @Override
    public void backScreen(String event, Object data) {

    }

    @Override
    public void showLoader(String message) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void sendSuccessBloquearCuentaToView(BloquearCuentaResponse response) {
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        String messageStatus = "";
        if (statusBloqueo == BLOQUEO) {
            messageStatus = getResources().getString(R.string.card_locked_success);
            App.getInstance().setStatusId(Recursos.ESTATUS_CUENTA_BLOQUEADA);
        } else if (statusBloqueo == DESBLOQUEO) {
            messageStatus = getResources().getString(R.string.card_unlocked_success);
            App.getInstance().setStatusId(Recursos.ESTATUS_CUENTA_DESBLOQUEADA);
        }

        // Armamos
        UI.showSuccessSnackBar(getActivity(), messageStatus + "\n" +
                getResources().getString(R.string.card_num_autorization) + " "
                + response.getData().getNumeroAutorizacion(), Snackbar.LENGTH_LONG);
        try {
            ApiAdtvo.cerrarSesion();
            // Toast.makeText(App.getContext(), "Close Session " + response.getData().getNumeroAutorizacion(), Toast.LENGTH_SHORT).show();
        } catch (OfflineException e) {
            e.printStackTrace();
        }

        // update de datos de Card instantaneos
        cardStatusId = App.getInstance().getStatusId();
        if (cardStatusId.equals("1")) {
            // La tarjeta esta DESBLOQUEADA, mostramos la cCard Azul
            rlyUnblockCard.setVisibility(View.GONE);
            imgBlockCard.setVisibility(View.VISIBLE);

            // Cambiamos las palabras a Bloquear
            txtTitle.setText(App.getContext().getResources().getString(R.string.block_your_card_title));
            txtMessageCard.setText(App.getContext().getResources().getString(R.string.block_your_card_subtitle));
            btnBlockCard.setText(App.getContext().getResources().getString(R.string.txt_bloquear));

        } else {
            // La tarjeta esta BLOQUEADA, mostramos la cCard Gray
            rlyUnblockCard.setVisibility(View.VISIBLE);
            imgBlockCard.setVisibility(View.GONE);

            // Cambiamos las palabras a Desboquear
            txtTitle.setText(App.getContext().getResources().getString(R.string.unblock_your_card_title));
            txtMessageCard.setText(App.getContext().getResources().getString(R.string.unblock_your_card_subtitle));
            btnBlockCard.setText(App.getContext().getResources().getString(R.string.txt_desbloquear));
        }
    }

    @Override
    public void sendErrorBloquearCuentaToView(String mensaje) {
        // Toast.makeText(App.getContext(), "Error Vloquear", Toast.LENGTH_SHORT).show();
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        UI.showErrorSnackBar(getActivity(), mensaje, Snackbar.LENGTH_SHORT);
        try {
            ApiAdtvo.cerrarSesion();
            //Toast.makeText(App.getContext(), "Close Session ", Toast.LENGTH_SHORT).show();
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError(Object error) {
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        UI.showErrorSnackBar(getActivity(), error.toString(), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void loginSucced() {

    }
}
