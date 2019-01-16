package com.pagatodo.yaganaste.ui.account.login;


import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

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

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.HUELLA_FAIL;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccessCodeGenerateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccessCodeGenerateFragment extends GenericFragment implements View.OnClickListener, FingerprintHandler.generateCode {

    ///////////////
    private static final String KEY_NAME = "yourKey";
    private Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private FingerprintManager.CryptoObject cryptoObject;

    private String texto;
    private static Preferencias preferencias = App.getInstance().getPrefs();
    private CustomErrorDialog customErrorDialog;
    String titulo;
    private Preferencias prefs = App.getInstance().getPrefs();
    ///////////////7
    AccessCodeGenerateFragment accessCodeGenerateFragment;
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.btnGenerateCode)
    StyleButton btnGenerateCode;

    @BindView(R.id.text_password)
    TextInputLayout text_password;




    FingerprintAuthenticationDialogFragment fragment;
    View rootView;
    static AccessCodeGenerateFragment fragmentCode;


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

    @Override
    public void generatecode() {
        loadOtpHuella();
    }

    @Override
    public void generatecode(String mensaje) {
        Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(100);
        customErrorDialog.setTitleNotification("Reintentar");
        customErrorDialog.setTitleMessageNotification(mensaje);

    }

    @Override
    public void generatecode(String mensaje, int errors) {
        if (errors >= 3) {
            Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(100);
            customErrorDialog.setTitleNotification(getString(R.string.fingerprint_failed_authentification));
            customErrorDialog.setTitleMessageNotification(getString(R.string.fingerprint_failed));

        }
    }

    public interface OtpInterface {
        void loadCode(String code);
    }

    public static AccessCodeGenerateFragment newInstance() {
        fragmentCode = new AccessCodeGenerateFragment();
        Bundle args = new Bundle();
        fragmentCode.setArguments(args);
        return fragmentCode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_access_code_generate, container, false);
        titulo = getString(R.string.titulo_dialogo_huella_generacion_codigo);
        texto = getString(R.string.authorize_payment_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
            keyguardManager = getActivity().getSystemService(KeyguardManager.class);
            fingerprintManager = getActivity().getSystemService(FingerprintManager.class);
        }
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnGenerateCode.setOnClickListener(this);

        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    text_password.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    text_password.setBackgroundResource(R.drawable.inputtext_normal);
                }
            }
        });




        // Se le asigna un TextWatcher personalizado para realizar las oepraciones
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 6) {
                    UI.hideKeyBoard(getActivity());
                    //  Servicio para consumir usuario y contraseña
                    loadOtp();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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

                } else {
                    // Hide the purchase button which uses a non-invalidated key
                    // if the app doesn't work on Android N preview
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
                    //Toast.makeText(getContext(), "Go to 'Settings -> Security -> Fingerprint' and register at least one fingerprint", Toast.LENGTH_LONG).show();
                    return;
                }
            }
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
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGenerateCode:
                loadOtp();
                break;
            default:
                //Nothing To Do
                break;
        }
    }

    public void loadOtpHuella() {
        onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.generando_token));
        ((OtpInterface) getParentFragment()).loadCode(preferencias.loadData("SHA_256_FREJA"));
        prefs.saveDataBool(HUELLA_FAIL, false);
    }

    private void loadOtp() {
        if(editPassword.getText().toString().isEmpty()) {
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_usuario_pass), Snackbar.LENGTH_SHORT);
        } else if(editPassword.getText().toString().length()<6){
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_usuario_pass_formato), Snackbar.LENGTH_SHORT);
        } else {
            onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.generando_token));
            ((OtpInterface) getParentFragment()).loadCode(Utils.getSHA256(editPassword.getText().toString()));
            prefs.saveDataBool(HUELLA_FAIL, false);
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (!menuVisible && editPassword != null && isAdded()) {
            editPassword.setText(null);
            UI.hideKeyBoard(getActivity());
        }
    }
}