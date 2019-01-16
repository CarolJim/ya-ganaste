package com.pagatodo.yaganaste.ui._controllers.manager;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.interfaces.IProgressView;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui.account.login.AccessCodeGenerateFragment;
import com.pagatodo.yaganaste.ui.account.login.BlockCardFragment;
import com.pagatodo.yaganaste.ui.account.login.FingerprintAuthenticationDialogFragment;
import com.pagatodo.yaganaste.ui.payments.fragments.PaymentAuthorizeFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.CancelAccountFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.DescargarEdoCuentaFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.PayQRFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;

/**
 * @author Juan Guerra on 09/05/2017.
 */

public abstract class LoaderActivity extends ToolBarActivity implements OnEventListener, IProgressView<ErrorObject>,
        FingerprintAuthenticationDialogFragment.generateCodehuella,FingerprintAuthenticationDialogFragment.calldialogpass,FingerprintAuthenticationDialogFragment.canceltransaction {

    public static final String EVENT_SHOW_LOADER = "EVENT_SHOW_LOADER";
    public static final String EVENT_HIDE_LOADER = "EVENT_HIDE_LOADER";
    public static final String EVENT_SHOW_ERROR = "EVENT_SHOW_ERROR";
    private static final String SECRET_MESSAGE = "Very secret message";
    public boolean isLoaderShow = false;
    private ProgressLayout progressLayout;
    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_loader);
        if (layoutResID != R.layout.activity_loader) {
            LinearLayout content = findViewById(R.id.ll_content);
            View view = getLayoutInflater().inflate(layoutResID, null);
            content.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            setUpActionBar();
        }

        progressLayout = findViewById(R.id.progress_view);
    }

    @Override
    public void showLoader(String message) {
        if (progressLayout != null) {
            isLoaderShow = true;
            progressLayout.setTextMessage(message != null ? message : "");
            progressLayout.setVisibility(View.VISIBLE);
            progressLayout.bringToFront();
        }
    }

    @Override
    public void hideLoader() {
        isLoaderShow = false;
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(ErrorObject error) {
        UI.createSimpleCustomDialog("", error.getErrorMessage(), getSupportFragmentManager(), error.getErrorActions(), true, false);
    }

    @Override
    @CallSuper
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case EVENT_SHOW_LOADER:
                isLoaderShow = true;
                showLoader(data.toString());
                break;

            case EVENT_HIDE_LOADER:
                isLoaderShow = false;
                hideLoader();
                break;

            case EVENT_SHOW_ERROR:
                showError((ErrorObject) data);
                break;
          /*  case EVENT_SESSION_EXPIRED:
                Intent intent = new Intent(App.getContext(), MainActivity.class);
                intent.putExtra(SELECTION, MAIN_SCREEN);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                App.getContext().startActivity(intent);
                break;*/
            default:

                break;
        }
    }

    @Override
    public void errorSessionExpired(DataSourceResult response) {
//        String mensaje = response.getData().toString();
//        super.onEvent(EVENT_SESSION_EXPIRED, mensaje);
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
                mKeyGenerator.init(builder.build());
            }
            mKeyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showConfirmation(byte[] encrypted) {
        //findViewById(R.id.confirmation_message).setVisibility(View.VISIBLE);
        //if (encrypted != null) {
        //TextView v = (TextView) findViewById(R.id.encrypted_message);
        //  v.setVisibility(View.VISIBLE);
        //v.setText(Base64.encodeToString(encrypted, 0 /* flags */));
        //}
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
            //Log.e(TAG, "Failed to encrypt the data with the generated key." + e.getMessage());
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tryEncrypt(cryptoObject.getCipher());
            }
        } else {
            // Authentication happened with backup password. Just show the confirmation message.
            showConfirmation(null);
        }
    }

    @Override
    public void generatecodehue(Fragment fm) {
        if (fm instanceof AccessCodeGenerateFragment)
            ((AccessCodeGenerateFragment) fm).loadOtpHuella();
        if (fm instanceof PaymentAuthorizeFragment)
            ((PaymentAuthorizeFragment) fm).loadOtpHuella();
        if (fm instanceof BlockCardFragment)
            ((BlockCardFragment) fm).loadOtpHuella();
        if (fm instanceof CancelAccountFragment)
            ((CancelAccountFragment) fm).loadOtpHuella();
        if (fm instanceof DescargarEdoCuentaFragment)
            ((DescargarEdoCuentaFragment) fm).loadOtpHuella(App.getInstance().getPrefs().loadData("SHA_256_FREJA"));
    }

    @Override
    public void onBackPressed() {
        if (!isLoaderShow) {
            super.onBackPressed();
        }
    }

    @Override
    public void calldialogpass(Fragment fm) {
        if (fm instanceof PayQRFragment)
            ((PayQRFragment) fm).calldialogpasss();
    }

    @Override
    public void canceltransaction(Fragment fm) {
        if (fm instanceof PayQRFragment)
            ((PayQRFragment) fm).canceltransaction();
    }
}
