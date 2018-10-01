package com.pagatodo.yaganaste.ui.account.login;

/**
 * Created by Armando Sandoval on 29/11/2017.
 */


import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.payments.fragments.PaymentAuthorizeFragment;
import com.pagatodo.yaganaste.ui_wallet.WalletMainActivity;
import com.pagatodo.yaganaste.ui_wallet.fragments.CancelAccountFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.DescargarEdoCuentaFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.DetalleOperadorFragment;

import java.security.KeyStore;

import javax.crypto.KeyGenerator;

import static com.pagatodo.yaganaste.utils.Recursos.CARD_STATUS;

/**
 * A dialog which uses fingerprint APIs to authenticate the user, and falls back to password
 * authentication if fingerprint is not available.
 */
public class FingerprintAuthenticationDialogFragment extends DialogFragment
        implements TextView.OnEditorActionListener, FingerprintUiHelper.Callback {

    private Button mCancelButton;
    private Button mSecondDialogButton;
    private View mFingerprintContent;
    private View mBackupContent;
    private EditText mPassword;
    private CheckBox mUseFingerprintFutureCheckBox;
    private TextView mPasswordDescriptionTextView;
    private TextView mNewFingerprintEnrolledTextView;
    private TextView fingerprint_titulo;


    private Stage mStage = Stage.FINGERPRINT;

    private FingerprintManager.CryptoObject mCryptoObject;
    private FingerprintUiHelper mFingerprintUiHelper;
    private LoaderActivity mActivity;
    //private PaymentsProcessingActivity paymentsProcessingActivity;
    private AccessCodeGenerateFragment accessCodeGenerateFragment;
    private generateCodehuella generateCode;
    private InputMethodManager mInputMethodManager;
    private SharedPreferences mSharedPreferences;
    private Fragment fragmentInstance;
    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Do not create a new Fragment when the Activity is re-created such as orientation changes.
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //getDialog().setTitle(getString(R.string.sign_in));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.fingerprint_dialog_container, container, false);
        mCancelButton = (Button) v.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        fingerprint_titulo = (TextView) v.findViewById(R.id.fingerprint_titulo);
        mSecondDialogButton = (Button) v.findViewById(R.id.second_dialog_button);
        mSecondDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStage == Stage.FINGERPRINT) {
                    goToBackup();
                } else {
                    verifyPassword();
                }
            }
        });


        mFingerprintContent = v.findViewById(R.id.fingerprint_container);
        mBackupContent = v.findViewById(R.id.backup_container);
        mPassword = (EditText) v.findViewById(R.id.password);
        mPassword.setOnEditorActionListener(this);
        mPasswordDescriptionTextView = (TextView) v.findViewById(R.id.password_description);
        mUseFingerprintFutureCheckBox = (CheckBox)
                v.findViewById(R.id.use_fingerprint_in_future_check);


        mNewFingerprintEnrolledTextView = (TextView)
                v.findViewById(R.id.new_fingerprint_enrolled_description);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mFingerprintUiHelper = new FingerprintUiHelper(
                    mActivity.getSystemService(FingerprintManager.class),
                    (ImageView) v.findViewById(R.id.fingerprint_icon),
                    (TextView) v.findViewById(R.id.fingerprint_status), this);
        }
        updateStage();

        // If fingerprint authentication is not available, switch immediately to the backup
        // (password) screen.
        if (!mFingerprintUiHelper.isFingerprintAuthAvailable()) {
            goToBackupintetos();
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mStage == Stage.FINGERPRINT) {
            mFingerprintUiHelper.startListening(mCryptoObject);
        }
    }

    public void setStage(Stage stage) {
        mStage = stage;
    }

    @Override
    public void onPause() {
        super.onPause();
        mFingerprintUiHelper.stopListening();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //mActivity = (AccountActivity) getActivity();
        if (context instanceof WalletMainActivity)
            mActivity = (WalletMainActivity) getActivity();
        if (context instanceof AccountActivity)
            mActivity = (AccountActivity) getActivity();
        if (context instanceof PaymentsProcessingActivity)
            mActivity = (PaymentsProcessingActivity) getActivity();
        if (context instanceof PreferUserActivity) {
            mActivity = (PreferUserActivity) getActivity();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mInputMethodManager = context.getSystemService(InputMethodManager.class);
        }
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setFragmentInstance(Fragment fragmentInstance) {
        this.fragmentInstance = fragmentInstance;
    }

    public void setGenerateCode(generateCodehuella generateCodec) {
        generateCode = generateCodec;
    }

    /**
     * Sets the crypto object to be passed in when authenticating with fingerprint.
     */
    public void setCryptoObject(FingerprintManager.CryptoObject cryptoObject) {
        mCryptoObject = cryptoObject;
    }

    /**
     * Switches to backup (password) screen. This either can happen when fingerprint is not
     * available or the user chooses to use the password authentication method by pressing the
     * button. This can also happen when the user had too many fingerprint attempts.
     */
    private void goToBackup() {
        mStage = Stage.INTENTS;
        updateStage();
        mPassword.requestFocus();

        // Show the keyboard.
        mPassword.postDelayed(mShowKeyboardRunnable, 500);

        // Fingerprint is not used anymore. Stop listening for it.
        mFingerprintUiHelper.stopListening();
    }

    private void goToBackupintetos() {
        mStage = Stage.PASSWORD;
        updateStage();
        // mPassword.requestFocus();

        // Show the keyboard.
        // mPassword.postDelayed(mShowKeyboardRunnable, 500);

        // Fingerprint is not used anymore. Stop listening for it.
        mFingerprintUiHelper.stopListening();
    }


    /**
     * Checks whether the current entered password is correct, and dismisses the the dialog and
     * let's the activity know about the result.
     */
    private void verifyPassword() {
        if (!checkPassword(mPassword.getText().toString())) {
            return;
        }
        if (mStage == Stage.NEW_FINGERPRINT_ENROLLED) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(getString(R.string.use_fingerprint_to_authenticate_key),
                    mUseFingerprintFutureCheckBox.isChecked());
            editor.apply();

            if (mUseFingerprintFutureCheckBox.isChecked()) {
                // Re-create the key so that fingerprints including new ones are validated.
                mActivity.createKey(AccessCodeGenerateFragment.DEFAULT_KEY_NAME, true);
                mStage = Stage.FINGERPRINT;
            }
        }
        mPassword.setText("");
        mActivity.onPurchased(false /* without Fingerprint */, null);
        dismiss();
    }

    /**
     * @return true if {@code password} is correct, false otherwise
     */
    private boolean checkPassword(String password) {
        // Assume the password is always correct.
        // In the real world situation, the password needs to be verified in the server side.
        return password.length() > 0;
    }

    private final Runnable mShowKeyboardRunnable = new Runnable() {
        @Override
        public void run() {
            mInputMethodManager.showSoftInput(mPassword, 0);
        }
    };

    @SuppressLint("ResourceAsColor")
    private void updateStage() {
        switch (mStage) {
            case FINGERPRINT:
                if (fragmentInstance instanceof DetalleOperadorFragment) {
                    fingerprint_titulo.setText("Autoriza esta operación ");
                    mSecondDialogButton.setOnClickListener(view -> dismiss());
                } else if (fragmentInstance instanceof BlockCardFragment) {
                    if (App.getInstance().getPrefs().loadData(CARD_STATUS).equals("1")) {
                        fingerprint_titulo.setText("Desbloquear tarjeta");
                    } else {
                        fingerprint_titulo.setText("Bloquear tarjeta");
                    }
                    mSecondDialogButton.setOnClickListener(view -> dismiss());
                } else if (fragmentInstance instanceof AccessCodeGenerateFragment) {
                    fingerprint_titulo.setText(R.string.generar_codigo_seguridad);
                    mSecondDialogButton.setOnClickListener(view -> dismiss());
                } else if (fragmentInstance instanceof PaymentAuthorizeFragment) {
                    fingerprint_titulo.setText(R.string.authorize_payment_title);
                    mSecondDialogButton.setOnClickListener(view -> dismiss());
                } else if (fragmentInstance instanceof CancelAccountFragment) {

                    fingerprint_titulo.setText("Autoriza esta operación");
                    mSecondDialogButton.setOnClickListener(view -> dismiss());
                }

                mCancelButton.setText(R.string.cancel);
                mSecondDialogButton.setText(R.string.use_password);

                mFingerprintContent.setVisibility(View.VISIBLE);
                mBackupContent.setVisibility(View.GONE);
                break;
            case NEW_FINGERPRINT_ENROLLED:
                // Intentional fall through

            case INTENTS:
                if (fragmentInstance instanceof DescargarEdoCuentaFragment) {
                    ((DescargarEdoCuentaFragment) fragmentInstance).showDialogPassword();
                    dismiss();
                } else {
                    mPasswordDescriptionTextView.setText("Demasiados intentos\nallidos, por favor\nusa tú contraseña");
                    mCancelButton.setText("Usar Contraseña");
                    mFingerprintContent.setVisibility(View.GONE);
                    mBackupContent.setVisibility(View.VISIBLE);
                    mSecondDialogButton.setVisibility(View.GONE);
                    mCancelButton.setOnClickListener(view -> {
                        dismiss();
                    });
                }
                break;
            case PASSWORD:
                mCancelButton.setText(R.string.cancel);
                mSecondDialogButton.setText("Configuración");
                mFingerprintContent.setVisibility(View.GONE);
                mBackupContent.setVisibility(View.VISIBLE);
                mPasswordDescriptionTextView.setText("No existe una huella digital registrada, por favor \ningrese a configuración");
                mSecondDialogButton.setOnClickListener(view -> {
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                });
                if (mStage == Stage.NEW_FINGERPRINT_ENROLLED) {
                    mPasswordDescriptionTextView.setVisibility(View.GONE);
                    mNewFingerprintEnrolledTextView.setVisibility(View.VISIBLE);
                    mUseFingerprintFutureCheckBox.setVisibility(View.VISIBLE);
                }
                if (fragmentInstance instanceof DescargarEdoCuentaFragment) {
                    ((DescargarEdoCuentaFragment) fragmentInstance).showDialogPassword();
                    dismiss();
                }
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            verifyPassword();
            return true;
        }
        return false;
    }

    @Override
    public void onAuthenticated() {
        // Callback from FingerprintUiHelper. Let the activity know that authentication was
        // successful.
        mActivity.generatecodehue(fragmentInstance);
        mActivity.onPurchased(true /* withFingerprint */, mCryptoObject);
        try {
            dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError() {
        goToBackup();
    }


    /**
     * Enumeration to indicate which authentication method the user is trying to authenticate with.
     */
    public enum Stage {
        FINGERPRINT,
        NEW_FINGERPRINT_ENROLLED,
        PASSWORD,
        INTENTS
    }

    public interface generateCodehuella {
        void generatecodehue(Fragment fm);
    }
}
