package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.app.KeyguardManager;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.login.FingerprintAuthenticationDialogFragment;
import com.pagatodo.yaganaste.ui.otp.presenters.OtpPresenterImp;
import com.pagatodo.yaganaste.ui_wallet.adapters.AdapterDownloadCuenta;
import com.pagatodo.yaganaste.ui_wallet.dialog.DialogSetPassword;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IDialogSetPassword;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;

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

import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_VISUALIZER_EDO_CUENTA;
import static com.pagatodo.yaganaste.utils.Recursos.PSW_CPR;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;

public class DescargarEdoCuentaFragment extends GenericFragment implements AdapterView.OnItemClickListener,
        IDialogSetPassword {

    private static final String KEY_NAME = "yourKey", KEY_NAME_NOT_INVALIDATED = "key_not_invalidated",
            DIALOG_FRAGMENT_TAG = "myFragment", DEFAULT_KEY_NAME = "default_key";

    View rootView;
    @BindView(R.id.lst_dates_download_edos)
    ListView lstDates;
    FingerprintAuthenticationDialogFragment authDialog;
    DialogSetPassword dialogPassword;

    private AdapterDownloadCuenta adapter;
    private KeyguardManager keyguardManager;
    private FingerprintManager fingerprintManager;
    private Cipher cipherNotInvalidated;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private SharedPreferences mSharedPreferences;
    private Preferencias preferencias = App.getInstance().getPrefs();
    private OtpPresenterImp presenter;
    private int year, month;

    public static DescargarEdoCuentaFragment newInstance() {
        DescargarEdoCuentaFragment fragment = new DescargarEdoCuentaFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyguardManager = getActivity().getSystemService(KeyguardManager.class);
            fingerprintManager = getActivity().getSystemService(FingerprintManager.class);
        }
        presenter = new OtpPresenterImp(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_edo_cuenta, container, false);
            initViews();
        }
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        adapter = new AdapterDownloadCuenta(getActivity());
        lstDates.setAdapter(adapter);
        lstDates.setOnItemClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !preferencias.loadData(PSW_CPR).equals("")) {
            if (fingerprintManager.isHardwareDetected()) {
                try {
                    keyStore = KeyStore.getInstance("AndroidKeyStore");
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                }
                try {
                    keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
                } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                    e.printStackTrace();
                }
                try {
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        year = adapter.getItem(i).getYear();
        month = adapter.getItem(i).getMonth();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (fingerprintManager.isHardwareDetected() && App.getInstance().getPrefs().loadDataBoolean(USE_FINGERPRINT, true)) {
                if (initCipher(cipherNotInvalidated, KEY_NAME_NOT_INVALIDATED)) {
                    // Show the fingerprint dialog. The user has the option to use the fingerprint with
                    // crypto, or you can fall back to using a server-side verified password.
                    authDialog = new FingerprintAuthenticationDialogFragment();
                    authDialog.setCryptoObject(new FingerprintManager.CryptoObject(cipherNotInvalidated));
                    boolean useFingerprintPreference = mSharedPreferences.getBoolean(getString(R.string.use_fingerprint_to_authenticate_key),
                            true);
                    if (useFingerprintPreference) {
                        authDialog.setStage(FingerprintAuthenticationDialogFragment.Stage.FINGERPRINT);
                    } else {
                        authDialog.setStage(FingerprintAuthenticationDialogFragment.Stage.PASSWORD);
                    }
                    authDialog.setFragmentInstance(this);
                    authDialog.show(getActivity().getFragmentManager(), DIALOG_FRAGMENT_TAG);
                } else {
                    FingerprintAuthenticationDialogFragment fragment
                            = new FingerprintAuthenticationDialogFragment();
                    fragment.setCryptoObject(new FingerprintManager.CryptoObject(cipherNotInvalidated));
                    fragment.setStage(
                            FingerprintAuthenticationDialogFragment.Stage.NEW_FINGERPRINT_ENROLLED);
                    fragment.setFragmentInstance(this);
                    fragment.show(getActivity().getFragmentManager(), DIALOG_FRAGMENT_TAG);
                }
            } else {
                dialogPassword = new DialogSetPassword();
                dialogPassword.setListener(this);
                dialogPassword.show(getActivity().getFragmentManager(), "Dialog Set Password");
            }
        } else {
            dialogPassword = new DialogSetPassword();
            dialogPassword.setListener(this);
            dialogPassword.show(getActivity().getFragmentManager(), "Dialog Set Password");
        }
    }

    private boolean initCipher(Cipher cipher, String keyName) {
        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(keyName, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            return false;
        }
    }

    private void createKey(String keyName, boolean invalidatedByBiometricEnrollment) {
        try {
            keyStore.load(null);
            KeyGenParameterSpec.Builder builder = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                builder = new KeyGenParameterSpec.Builder(keyName,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    builder.setInvalidatedByBiometricEnrollment(invalidatedByBiometricEnrollment);
                }
                keyGenerator.init(builder.build());
            }
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPasswordSet(@Nullable String xps) {
        loadOtpHuella(Utils.getSHA256(xps));
    }

    public void loadOtpHuella(String sha) {
        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            presenter.generateOTP(sha);
        } else {
            UI.showErrorSnackBar(getActivity(), getResources().getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
        }
    }

    public void showDialogPassword(){
        dialogPassword = new DialogSetPassword();
        dialogPassword.setListener(this);
        dialogPassword.show(getActivity().getFragmentManager(), "Dialog Set Password");
    }

    @Override
    public void onOtpGenerated(String otp) {
        Log.e("YG", "OTP: " + otp);
        UI.hideKeyBoard(getActivity());
        onEventListener.onEvent(EVENT_GO_VISUALIZER_EDO_CUENTA, year + "_" + (month + 1) + "_" + otp);
    }

    @Override
    public void showError(Errors error) {
        UI.showErrorSnackBar(getActivity(), error.getMessage(), Snackbar.LENGTH_SHORT);
    }
}
