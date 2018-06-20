package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.app.KeyguardManager;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CancelRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesionUYU;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
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

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_CANCEL_RESULT;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ESTATUS_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.ID_USUARIO_ADQUIRIENTE;

public class CancelAccountFragment extends SupportFragment implements View.OnClickListener, IRequestResult, TextWatcher, INavigationView {

    private View rootView;
    public static final String TITLE = "TITLE";
    public static final String DESC = "DESC";
    private static final String KEY_NAME_NOT_INVALIDATED = "key_not_invalidated";
    static final String DEFAULT_KEY_NAME = "default_key";
    private static final String DIALOG_FRAGMENT_TAG = "myFragment";

    private String title;
    private String desc;

    @BindView(R.id.cancelTitle)
    StyleTextView titleTextView;
    @BindView(R.id.description)
    StyleTextView descTextView;
    @BindView(R.id.edt_block_password)
    EditText editTextPass;
    @BindView(R.id.btnEntendido)
    StyleButton btn;

    private FingerprintManager fingerprintManager;
    private FingerprintManager.CryptoObject cryptoObject;
    private Cipher cipher;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private SharedPreferences mSharedPreferences;
    private Cipher defaultCipher;
    private Cipher cipherNotInvalidated;
    private AccountPresenterNew presenter;

    public static CancelAccountFragment newInstance(String title, String desc) {
        CancelAccountFragment fragment = new CancelAccountFragment();
        Bundle args = new Bundle();
        args.putString(TITLE,title);
        args.putString(DESC,desc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            title = getArguments().getString(TITLE);
            desc = getArguments().getString(DESC);
        }
        presenter = new AccountPresenterNew(getContext());
        presenter.setIView(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cancel_account, container, false);
        ButterKnife.bind(this, rootView);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        titleTextView.setText(title);
        descTextView.setText(desc);
        btn.setOnClickListener(this);
        editTextPass.addTextChangedListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
            keyguardManager = getActivity().getSystemService(KeyguardManager.class);
            fingerprintManager = getActivity().getSystemService(FingerprintManager.class);
        }
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
    public void onClick(View view) {
        boolean isValid = true;
        String errorMsg = null;
        if (editTextPass.getText().toString().isEmpty()) {
            errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.password_required) : errorMsg;
            //edtPin.setIsInvalid();
            isValid = false;
        }
        if (editTextPass.getText().toString().length() < 6) {
            errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.password_required_seis) : errorMsg;
            //edtPin.setIsInvalid();
            isValid = false;
        }
        if (isValid){
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

    private void onValidationSuccess() {
        presenter.login(RequestHeaders.getUsername(), editTextPass.getText().toString());

    }

    public void loadOtpHuella() {
        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            SingletonUser singletonUser = SingletonUser.getInstance();
            DataIniciarSesionUYU data = singletonUser.getDataUser();
            int Idestatus = App.getInstance().getPrefs().loadDataInt(ID_ESTATUS_EMISOR);
            int idafiliacion = 2;

            if (Idestatus == IdEstatus.I5.getId()){
                idafiliacion = 1;
            }

            if (Idestatus == IdEstatus.ADQUIRENTE.getId()){
                idafiliacion = 3;
            }
            CancelRequest request = new CancelRequest(idafiliacion,App.getInstance().getPrefs().loadData(ID_USUARIO_ADQUIRIENTE));
            cancelAccount(request);
        } else {
            UI.showErrorSnackBar(getActivity(), getResources().getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
        }
    }



    /**
     * Service Cancel
     */
    public void cancelAccount(CancelRequest request) {
        //onEventListener.onEvent(EVENT_SHOW_LOADER,getString(R.string.load_cancel));
        try {
            ApiAdtvo.cancelAccount(request, this);
        } catch (OfflineException e) {
            UI.showErrorSnackBar(getActivity(),App.getContext().getString(R.string.no_internet_access),Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        onEventListener.onEvent(EVENT_HIDE_LOADER,null);
        if (((GenericResponse)dataSourceResult.getData()).getCodigoRespuesta() == CODE_OK) {
            App.getInstance().getPrefs().clearPreferences();
            App.getInstance().clearCache();
            RequestHeaders.clearPreferences();
            onEventListener.onEvent(PREFER_CANCEL_RESULT, null);
        } else {
            UI.showErrorSnackBar(getActivity(),((GenericResponse)dataSourceResult.getData()).getMensaje(),Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        onEventListener.onEvent(EVENT_HIDE_LOADER,null);
        UI.showErrorSnackBar(getActivity(),((GenericResponse)error.getData()).getMensaje(),Snackbar.LENGTH_SHORT);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        if (s.toString().length() == 6) {
            UI.hideKeyBoard(getActivity());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void nextScreen(String event, Object data) {
        //onEventListener.onEvent(EVENT_HIDE_LOADER,null);
        //SingletonUser singletonUser = SingletonUser.getInstance();
        //DataIniciarSesion data = singletonUser.getDataUser();
        int Idestatus = App.getInstance().getPrefs().loadDataInt(ID_ESTATUS_EMISOR);
        int idafiliacion = 2;

        if (Idestatus == IdEstatus.I5.getId()){
            idafiliacion = 1;
        }

        if (Idestatus == IdEstatus.ADQUIRENTE.getId()){
            idafiliacion = 3;
        }
        String IdUsuarioAdquirente = App.getInstance().getPrefs().loadData(ID_USUARIO_ADQUIRIENTE);
        if (IdUsuarioAdquirente.isEmpty()){
            IdUsuarioAdquirente = "0";
        }
        CancelRequest request = new CancelRequest(idafiliacion,IdUsuarioAdquirente);
        cancelAccount(request);
    }

    @Override
    public void backScreen(String event, Object data) {

    }

    @Override
    public void showLoader(String message) {
        onEventListener.onEvent(EVENT_SHOW_LOADER,"Cargando..");

    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER,null);
    }

    @Override
    public void showError(Object error) {
        UI.showErrorSnackBar(getActivity(),error.toString(),Snackbar.LENGTH_SHORT);
    }
}
