package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.app.KeyguardManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.interfaces.IChangeOperador;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.login.FingerprintAuthenticationDialogFragment;
import com.pagatodo.yaganaste.ui.payments.fragments.PaymentAuthorizeFragment;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.presenter.ChangeStatusOperadorPresenter;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;
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

import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleOperadorFragment extends GenericFragment implements View.OnClickListener, ValidationForms, IChangeOperador{

    private View rootView;
    Operadores operadoresResponse;
    @BindView(R.id.correo_operador)
    StyleTextView correo_operador;
    @BindView(R.id.contrasena_operador)
    StyleTextView contrasena_operador;
    @BindView(R.id.status_operador)
    StyleTextView status_operador;
    @BindView(R.id.titulo_negocio)
    StyleTextView titulo_negocio;



    private static final String KEY_NAME = "yourKey";
    private static final String KEY_NAME_NOT_INVALIDATED = "key_not_invalidated";
    private static final String DIALOG_FRAGMENT_TAG = "myFragment";
    static final String DEFAULT_KEY_NAME = "default_key";
    private SharedPreferences mSharedPreferences;
    static PaymentAuthorizeFragment fragmentCode;
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


    ChangeStatusOperadorPresenter changeStatusOperadorPresenter;

    public static DetalleOperadorFragment newInstance(Operadores operadoresResponse) {
        DetalleOperadorFragment fragment = new DetalleOperadorFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailsActivity.DATA, operadoresResponse);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        operadoresResponse = (Operadores) args.getSerializable(DetailsActivity.DATA);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_detalle_operador, container, false);
        initViews();
        changeStatusOperadorPresenter = new ChangeStatusOperadorPresenter(getContext());
        changeStatusOperadorPresenter.setIView(this);

        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);

        //titulo_negocio.setText(operadoresResponse.getNombreNegocio());
        titulo_negocio.setText("");
        correo_operador.setText(operadoresResponse.getNombreUsuario());
        contrasena_operador.setText(operadoresResponse.getPetroNumero());
        if (operadoresResponse.getIdOperador() ==1){
            status_operador.setText("Operador activo");
        }else {
            status_operador.setText("Operador bloqueado");
            status_operador.setTextColor(Color.parseColor("#D0021B"));
        }

        status_operador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
                        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
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



              //  changeStatusOperadorPresenter.change(operadoresResponse.getNombreUsuario(),operadoresResponse.getIdEstatusUsuario()==1?8:1);
            }
        });

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


    @Override
    public void loginstarsucced() {

    }

    @Override
    public void loginfail() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {

    }

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {

    }

    @Override
    public void getDataForm() {

    }

    @Override
    public void nextScreen(String event, Object data) {

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
    public void showError(Object error) {

    }
}
