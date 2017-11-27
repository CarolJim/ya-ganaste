package com.pagatodo.yaganaste.ui.payments.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.login.AccessCodeGenerateFragment;
import com.pagatodo.yaganaste.ui.account.login.FingerprintHandler;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentAuthorizeManager;
import com.pagatodo.yaganaste.ui.payments.presenters.PaymentAuthorizePresenter;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentAuthorizePresenter;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
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

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.EVENT_SEND_PAYMENT;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;
import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;

/**
 * Created by Jordan on 02/05/2017.
 */

public class PaymentAuthorizeFragment extends GenericFragment implements View.OnClickListener,
        PaymentAuthorizeManager, FingerprintHandler.generateCode {

    private static final String KEY_NAME = "yourKey";

    IPaymentAuthorizePresenter paymentAuthorizePresenter;

    @BindView(R.id.txt_importe)
    MontoTextView importe;
    @BindView(R.id.btn_continueEnvio)
    StyleButton btnContinueEnvio;
    @BindView(R.id.nombreEnvio)
    StyleTextView nombreEnvio;
    @BindView(R.id.titleReferencia)
    StyleTextView titleReferencia;
    @BindView(R.id.txtBanco)
    StyleTextView txtBanco;

    @BindView(R.id.txtReferencia)
    StyleTextView txtReferencia;


    @BindView(R.id.editPassword)
    CustomValidationEditText editPassword;
    @BindView(R.id.errorPasswordMessage)
    ErrorMessage errorPasswordMessage;


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
    private FingerprintHandler helper;
    private static Preferencias preferencias = App.getInstance().getPrefs();

    public static PaymentAuthorizeFragment newInstance(Payments envio) {
        PaymentAuthorizeFragment fragment = new PaymentAuthorizeFragment();
        Bundle args = new Bundle();
        args.putSerializable("envios", envio);
        fragment.setArguments(args);
        return fragment;
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
        rootview = inflater.inflate(R.layout.fragment_authorize_payment, container, false);
        titulo = getString(R.string.titulo_dialogo_huella_generacion_codigo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
        }
        helper = new FingerprintHandler(this.getContext());
        texto = getString(R.string.authorize_payment_title);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        nombreEnvio.setVisibility(VISIBLE);
        nombreEnvio.setText(envio.getNombreDestinatario());

        importe.setText(StringUtils.getCurrencyValue(envio.getMonto()));
        titleReferencia.setText(envio.getTipoEnvio().getShortName());
        txtBanco.setText(envio.getComercio().getNombreComercio());
        String ref = envio.getReferencia();

        switch (envio.getTipoEnvio()) {
            case CLABE:
                txtReferencia.setText(StringUtils.format(ref, SPACE, 3, 3, 4, 4, 4));
                break;
            case NUMERO_TARJETA:
                txtReferencia.setText(StringUtils.maskReference(StringUtils.format(ref, SPACE, 4, 4, 4, 4), '*', ref.length() - 12));
                break;
            case NUMERO_TELEFONO:
                txtReferencia.setText(StringUtils.format(ref, SPACE, 2, 4, 4));
                break;
            default:
                break;
        }
        setValidationRules();
        btnContinueEnvio.setOnClickListener(this);

        //Huella
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && App.getInstance().getPrefs().loadDataBoolean(USE_FINGERPRINT, true)) {
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

                texto = getString(R.string.fingerprint_no_found);
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

    public void stopautentication(){
        helper.stopListening();
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
                    editPassword.imageViewIsGone(true);
                }
            }
        });

        editPassword.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editPassword.getId());
                editPassword.imageViewIsGone(true);
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
        errorPasswordMessage.setVisibilityImageError(false);
    }

    @Override
    public void onValidationSuccess() {
        onEventListener.onEvent(EVENT_SEND_PAYMENT, envio);
    }

    @Override
    public void getDataForm() {
        password = editPassword.getText().toString();
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
        if (!TextUtils.isEmpty(error.toString())) {
            UI.createSimpleCustomDialog(errorTittle, errorBody, getActivity().getSupportFragmentManager(), getFragmentTag());
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
            helper.stopListening();
            Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(100);
            customErrorDialog.setTitleMessageNotification(getString(R.string.fingerprint_failed));
        }
    }

    public void loadOtpHuella() {
       /*
        RequestHeaders.setTokenFreja(preferencias.loadData("SHA_256_FREJA"));
        onEventListener.onEvent(EVENT_SEND_PAYMENT, envio);*/
        customErrorDialog.dismiss();
       paymentAuthorizePresenter.generateOTP(preferencias.loadData("SHA_256_FREJA"));
        //onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.generando_token));
        //((AccessCodeGenerateFragment.OtpInterface) getParentFragment()).loadCode(preferencias.loadData("SHA_256_FREJA"));

    }

    private class FingerprintException extends Exception {

        public FingerprintException(Exception e) {
            super(e);
        }
    }
}
