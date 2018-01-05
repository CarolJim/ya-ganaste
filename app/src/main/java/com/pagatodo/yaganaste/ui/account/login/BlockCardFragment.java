package com.pagatodo.yaganaste.ui.account.login;


import android.app.KeyguardManager;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.ILoginView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.AsignarContraseñaTextWatcher;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.BorderTitleLayout;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
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

import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_BLOCK_CARD_BACK;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.PASSWORD_CHANGE;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;
import static com.pagatodo.yaganaste.utils.StringConstants.PSW_CPR;

/**
 * Encargada de bloquear la Card por medio de nuestra contraseña.
 * 1 - Pedimos contraseña
 * 2 - Iniciamos session rapida
 * 3 - Actualizamos el estado de la session
 * 4 - Cerramos session
 */
public class BlockCardFragment extends GenericFragment implements ValidationForms,
        ILoginView, IMyCardView,View.OnClickListener{


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

    FingerprintAuthenticationDialogFragment fragment;


    ///////////////7


    public static final int BLOQUEO = 1;
    public static final int DESBLOQUEO = 2;

    @BindView(R.id.editUserPassword)
    CustomValidationEditText edtUserPass;
    @BindView(R.id.btnLoginExistUser)
    StyleButton btnLogin;
    @BindView(R.id.cardBlue)
    ImageView cardBlue;
    @BindView(R.id.cardGray)
    ImageView cardGray;
    @BindView(R.id.layoutContent)
    BorderTitleLayout tittleBorder;
    @BindView(R.id.txtMessageCard)
    StyleTextView txtMessageCard;
    private String password;
    private View rootview;
    private AccountPresenterNew accountPresenter;
    private PreferUserPresenter mPreferPresenter;
    boolean isChecked;
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




    @BindView(R.id.customkeyboard)
    LinearLayout customkeyboard;

    @BindView(R.id.keyboard_view)
    CustomKeyboardView keyboardView;

    @BindView(R.id.layoutScrollCard)
    LinearLayout linerar_principal;

    LinearLayout layout_control;
    TextView tv1Num;
    TextView tv2Num;
    TextView tv3Num;
    TextView tv4Num;
    TextView tv5Num;
    TextView tv6Num;
    private String nip = "";
    private Keyboard keyboard;
    ImageView asignar_iv1;
    private static int PIN_LENGHT = 6;
    @BindView(R.id.asignar_edittext)
    CustomValidationEditText edtPin;








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

        titulo = getString(R.string.titulo_dialogo_huella_generacion_codigo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
            keyguardManager = getActivity().getSystemService(KeyguardManager.class);
            fingerprintManager = getActivity().getSystemService(FingerprintManager.class);
        }
       // helper = new FingerprintHandler(this.getContext());
        texto = getString(R.string.authorize_payment_title);

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
        if (prefs.loadDataBoolean(PASSWORD_CHANGE,false) ){


            linerar_principal.setOnClickListener(this);
            edtUserPass.setVisibility(View.GONE);
            layout_control = (LinearLayout) rootview.findViewById(R.id.asignar_control_layout_login);
            customkeyboard.setVisibility(VISIBLE);
            customkeyboard.setOnClickListener(this);
            keyboardView.setKeyBoard(getActivity(), R.xml.keyboard_nip);
            keyboardView.setPreviewEnabled(false);
            keyboardView.showCustomKeyboard(rootview);
            tv1Num = (TextView) rootview.findViewById(R.id.asignar_tv1);
            tv2Num = (TextView) rootview.findViewById(R.id.asignar_tv2);
            tv3Num = (TextView) rootview.findViewById(R.id.asignar_tv3);
            tv4Num = (TextView) rootview.findViewById(R.id.asignar_tv4);
            tv5Num = (TextView) rootview.findViewById(R.id.asignar_tv5);
            tv6Num = (TextView) rootview.findViewById(R.id.asignar_tv6);
            // EditTExt oculto que procesa el PIN y sirve como ancla para validacion
            // Se le asigna un TextWatcher personalizado para realizar las oepraciones
            edtPin = (CustomValidationEditText) rootview.findViewById(R.id.asignar_edittext);
            edtPin.setMaxLength(6); // Se asigna un maximo de 4 caracteres para no tener problrmas
            edtPin.addCustomTextWatcher(new AsignarContraseñaTextWatcher(edtPin, tv1Num, tv2Num, tv3Num, tv4Num, tv5Num, tv6Num));
            edtPin.addCustomTextWatcher(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() == 6) {
                        keyboardView.hideCustomKeyboard();
                        //  Servicio para consumir usuario y contraseña
                        validateForm();
                        edtPin.setText("");
                        edtPin.isFocused();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });




            edtPin.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    EditText edittext = (EditText) v;
                    int inType = edittext.getInputType();       // Backup the input type
                    edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                    edittext.onTouchEvent(event);               // Call native handler
                    keyboardView.showCustomKeyboard(v);
                    edittext.setInputType(inType);              // Restore input type
                    return true; // Consume touch event
                }
            });
            //     btnNextAsignarPin.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {validateForm()}});
            setValidationRules();
            keyboardView.showCustomKeyboard(rootview);
            edtPin.requestEditFocus();

        }else {

            edtUserPass.setVisibility(View.VISIBLE);
        }








        if (cardStatusId.equals("1")) {
            // La tarjeta esta DESBLOQUEADA, mostramos la cCard Azul
            cardBlue.setVisibility(View.VISIBLE);
            cardGray.setVisibility(View.GONE);

            // Cambiamos las palabras a Bloquear
            tittleBorder.setTitle(App.getContext().getResources().getString(R.string.bloquear_tarjeta));
            txtMessageCard.setText(App.getContext().getResources().getString(R.string.ingresa_pass_block));

            // Cambiamos el texto del BTN
            btnLogin.setText(App.getContext().getResources().getString(R.string.txt_bloquear));
        } else {
            // La tarjeta esta BLOQUEADA, mostramos la cCard Gray
            cardBlue.setVisibility(View.GONE);
            cardGray.setVisibility(View.VISIBLE);

            // Cambiamos las palabras a Desboquear
            tittleBorder.setTitle(App.getContext().getResources().getString(R.string.desbloquear_tarjeta));
            txtMessageCard.setText(App.getContext().getResources().getString(R.string.ingresa_pass_desblock));

            // Cambiamos el texto del BTN
            btnLogin.setText(App.getContext().getResources().getString(R.string.txt_desbloquear));
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
            showDialogMesage(getResources().getString(R.string.no_internet_access), 0);
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
     //
        //   helper.stopListeningcontraseña();
    }


    @OnClick(R.id.btnLoginExistUser)
    public void validatePass() {
        //  Toast.makeText(App.getContext(), "Click", Toast.LENGTH_SHORT).show();
        validateForm();
    }


    @Override
    public void setValidationRules() {
        edtUserPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edtUserPass.imageViewIsGone(true);
                } else {
                    if (edtUserPass.getText().isEmpty() || !edtUserPass.isValidText()) {
                        edtUserPass.setIsInvalid();
                    } else if (edtUserPass.isValidText()) {
                        edtUserPass.setIsValid();
                    }



                }
            }
        });

        edtUserPass.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                edtUserPass.imageViewIsGone(true);
            }
        });

        // Asignamos el OnEditorActionListener a este CustomEditext para el efecto de consumir el servicio
        edtUserPass.addCustomEditorActionListener(new DoneOnEditorActionListener());

    }

    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;
        String errorMsg = null;

        if (password.isEmpty()) {
            errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.password_required) : errorMsg;
            edtUserPass.setIsInvalid();
            isValid = false;
        }
        if (prefs.loadDataBoolean(PASSWORD_CHANGE,false)){
            if (password.length()<6) {
                errorMsg = errorMsg == null || errorMsg.isEmpty() ? getString(R.string.password_required_seis) : errorMsg;
                edtUserPass.setIsInvalid();
                isValid = false;
            }
        }


        if (isValid) {
            boolean isOnline = Utils.isDeviceOnline();
            if (isOnline) {
                onValidationSuccess();
            } else {
                showDialogMesage(getResources().getString(R.string.no_internet_access), 0);
            }
        } else {
            showDialogMesage(errorMsg, 0);
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

        if (prefs.loadDataBoolean(PASSWORD_CHANGE,false)) {
            password = edtPin.getText().trim();
        }else {
            password = edtUserPass.getText().trim();
        }


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
        showDialogMesage(messageStatus +
                "\n" +
                getResources().getString(R.string.card_num_autorization) + " "
                + response.getData().getNumeroAutorizacion(), 1);
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
            cardBlue.setVisibility(View.VISIBLE);
            cardGray.setVisibility(View.GONE);

            // Cambiamos las palabras a Bloquear
            tittleBorder.setTitle(App.getContext().getResources().getString(R.string.bloquear_tarjeta));
        } else {
            // La tarjeta esta BLOQUEADA, mostramos la cCard Gray
            cardBlue.setVisibility(View.GONE);
            cardGray.setVisibility(View.VISIBLE);

            // Cambiamos las palabras a Desboquear
            tittleBorder.setTitle(App.getContext().getResources().getString(R.string.desbloquear_tarjeta));
        }
    }

    @Override
    public void sendErrorBloquearCuentaToView(String mensaje) {
        // Toast.makeText(App.getContext(), "Error Vloquear", Toast.LENGTH_SHORT).show();
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        showDialogMesage(mensaje, 0);
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
        showDialogMesage(error.toString(), 0);
    }

    @Override
    public void loginSucced() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.layoutScrollCard:
                keyboardView.hideCustomKeyboard();
                break;
            case R.id.customkeyboard:
                keyboardView.showCustomKeyboard(rootview);
                break;

        }


    }

    /**
     * Encargada de reaccionar al codigo de pusacion KEYCODE_ENDCALL=6 para consumir el servicio
     */
    private class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == KeyEvent.KEYCODE_ENDCALL) {
                // Toast.makeText(getContext(), "Click Enter", Toast.LENGTH_SHORT).show();
                // actionBtnLogin();
            }
            return false;
        }
    }

    private void showDialogMesage(final String mensaje, final int backAction) {
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        if (backAction == 1) {
                            // Accion de Out
                            onEventListener.onEvent(EVENT_BLOCK_CARD_BACK, "");
                        }
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }
}
