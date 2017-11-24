package com.pagatodo.yaganaste.ui.account.login;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
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

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

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
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private String texto;
    private static Preferencias preferencias = App.getInstance().getPrefs();
    private CustomErrorDialog customErrorDialog;
    String titulo;
    ///////////////7

    @BindView(R.id.editPassword)
    CustomValidationEditText editPassword;
    @BindView(R.id.errorPasswordMessage)
    ErrorMessage errorPasswordMessage;
    @BindView(R.id.btnGenerateCode)
    StyleButton btnGenerateCode;

    FingerprintHandler helper;
    View rootView;



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
        if (errors>=3) {
            helper.stopListening();
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
        AccessCodeGenerateFragment fragment = new AccessCodeGenerateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        titulo = getString(R.string.titulo_dialogo_huella_generacion_codigo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
        }
        helper = new FingerprintHandler(this.getContext());

        rootView = inflater.inflate(R.layout.fragment_access_code_generate, container, false);
        texto = getString(R.string.authorize_payment_title);
        initViews();
        return rootView;
    }
    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnGenerateCode.setOnClickListener(this);
        editPassword.addCustomTextWatcher(new MTextWatcher());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            keyguardManager =
                    (KeyguardManager) getActivity().getSystemService(KEYGUARD_SERVICE);
            fingerprintManager =
                    (FingerprintManager) getActivity().getSystemService(FINGERPRINT_SERVICE);

            assert fingerprintManager != null;
            if (!fingerprintManager.isHardwareDetected()) {
                texto = getString(R.string.fingerprint_no_supported);
            }else if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                texto = getString(R.string.fingerprint_permission);
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {

                texto=getString(R.string.fingerprint_no_found);
                customErrorDialog= UI.createCustomDialogIraConfiguracion(titulo, texto, getFragmentManager(), getFragmentTag(), new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        opensettings();

                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                }, getString(R.string.fingerprint_go_to_settings), getString(R.string.fingerprint_use_password));

            }else if (!keyguardManager.isKeyguardSecure()) {
                texto = getString(R.string.fingerprint_eneable_security);
            } else {
                texto = getString(R.string.fingerprint_verification);
                String mensaje = "" + texto;

                try {

                    generateKey();
                    if (initCipher()) {
                        helper.setGenerateCode(this);
                        helper.startAuth(fingerprintManager, cryptoObject);
                    }
                    customErrorDialog= UI.createCustomDialogGeneraciondeCodigo(titulo, mensaje, getFragmentManager(), getFragmentTag(), new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {
                            stopautentication();

                        }

                        @Override
                        public void actionCancel(Object... params) {
                            stopautentication();
                        }
                    }, getString(R.string.fingerprint_use_password), "");
                } catch (FingerprintException e) {
                    e.printStackTrace();
                }

            }

        }


    }

    private void opensettings() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

    public void stopautentication(){
        helper.stopListening();
        customErrorDialog.setTitleMessageNotification(getString(R.string.fingerprint_verification));
    }
    @Override
    public void onPause() {
        super.onPause();
        helper.stopListeningcontraseña();
    }

    private void generateKey() throws FingerprintException {
        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");

            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyGenerator.init(new
                        KeyGenParameterSpec.Builder(KEY_NAME,
                        KeyProperties.PURPOSE_ENCRYPT |
                                KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(
                                KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build());
            }

            keyGenerator.generateKey();

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
            throw new FingerprintException(exc);
        }


    }

    public boolean initCipher() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (@SuppressLint("NewApi") KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    private class FingerprintException extends Exception {

        public FingerprintException(Exception e) {
            super(e);
        }
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
        customErrorDialog.dismiss();
            onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.generando_token));
            ((OtpInterface) getParentFragment()).loadCode(preferencias.loadData("SHA_256_FREJA"));

    }

    private void loadOtp() {
        if (editPassword.isValidText()) {
            onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.generando_token));
            ((OtpInterface) getParentFragment()).loadCode(Utils.getSHA256(editPassword.getText()));
        } else if (editPassword.getText().isEmpty()) {
            editPassword.setIsInvalid();
            errorPasswordMessage.setMessageText(getString(R.string.datos_usuario_pass));
        } else {
            editPassword.setIsInvalid();
            errorPasswordMessage.setMessageText(getString(R.string.datos_usuario_pass_formato));
        }
    }

    private class MTextWatcher extends AbstractTextWatcher {
        @Override
        public void afterTextChanged(String s) {
            editPassword.imageViewIsGone(true);
            errorPasswordMessage.setMessageText(null);
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