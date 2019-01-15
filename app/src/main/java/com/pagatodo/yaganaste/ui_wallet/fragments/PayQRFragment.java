package com.pagatodo.yaganaste.ui_wallet.fragments;



import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dspread.xpos.QPOSService;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DeleteFavoriteRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.EditTextImeBackListener;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.AdqActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.login.FingerprintAuthenticationDialogFragment;
import com.pagatodo.yaganaste.ui.account.login.FingerprintHandler;
import com.pagatodo.yaganaste.ui.account.register.DatosPersonalesFragment;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.ui.adquirente.fragments.InsertDongleFragment;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentAuthorizeManager;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoStates;
import com.pagatodo.yaganaste.ui_wallet.pojos.TransactionQR;
import com.pagatodo.yaganaste.utils.NumberCalcTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_PAYMENT_SUCCES_QR;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;


public class PayQRFragment extends GenericFragment implements EditTextImeBackListener,
        OnCompleteListener<LocationSettingsResponse>, View.OnClickListener, NumberCalcTextWatcher.TextChange, FingerprintHandler.generateCode ,PaymentAuthorizeManager {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 900;
    private static final String NAME_COMERCE = "NAME_COMERCE";

    @BindView(R.id.txtNameComerce)
    StyleTextView txtNameComerce;
    @BindView(R.id.et_amount)
    public EditText et_amount;
    @BindView(R.id.edtConcept)
    EditText edtConcept;
    @BindView(R.id.keyboard_view)
    CustomKeyboardView keyboardView;
    @BindView(R.id.img_arrow_previous)
    ImageView imgArrowPrev;
    @BindView(R.id.btncobrar)
    LinearLayout btncobrar;
    @BindView(R.id.cobrar)
    StyleTextView cobrar;

    String montoFirebase;
    @BindView(R.id.text_input_concepto)
    TextInputLayout inputConcept;
    @BindView(R.id.img_tienda)
    ImageView img_tienda;

    LinearLayout layout_amount;
    private String nameComerce = "";
    private float MIN_AMOUNT = 1.0f;
    boolean isValid;
    private int[] perrmisionArray = {1, 1};
    private StyleTextView tvMontoEntero, tvMontoDecimal;
    private View rootview;
    String datetime;
    float current_mount;
    String monto_apagar;
    double monto_final;

    private static String DATA_KEY_REF = "TITULO",DATA_KEY_COM= "SUBTITULO",DATA_KEY_CODE= "CODE";

    String titulo,subtitulo;
    boolean codevisivility;




    private static final String KEY_NAME_NOT_INVALIDATED = "key_not_invalidated";
    private static final String DIALOG_FRAGMENT_TAG = "myFragment";
    static final String DEFAULT_KEY_NAME = "default_key";
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private FingerprintManager fingerprintManager;
    private FingerprintManager.CryptoObject cryptoObject;
    private KeyguardManager keyguardManager;
    private SharedPreferences mSharedPreferences;
    static PayQRFragment fragmentCode;
    private Cipher cipher;
    private List<TransactionQR> states = new ArrayList<>();
    public PayQRFragment() {
    }

    public static  PayQRFragment newInstance (String referencia,String comercio, boolean codevisivility ){
        fragmentCode = new PayQRFragment();
        Bundle args = new Bundle();
        args.putString(DATA_KEY_REF,referencia);
        args.putString(DATA_KEY_COM,comercio);
        args.putBoolean(DATA_KEY_CODE,codevisivility);
        fragmentCode.setArguments(args);
        return fragmentCode;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventListener) {
            this.onEventListener = (OnEventListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titulo = getArguments().getString(DATA_KEY_REF);
        subtitulo = getArguments().getString(DATA_KEY_COM);
        codevisivility = getArguments().getBoolean(DATA_KEY_CODE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
            keyguardManager = getActivity().getSystemService(KeyguardManager.class);
            fingerprintManager = getActivity().getSystemService(FingerprintManager.class);

        }




/*

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("QR").child("Transactions");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    states = new ArrayList<>();
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        TransactionQR item = singleSnapshot.getValue(TransactionQR.class);
                        states.add(item);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.i(" QR", " Error"+databaseError.toString());

            }
        });
*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_qrpayment, container, false);
        initViews();
        return rootview;
    }


    public void montoacobrar(String montoFirebase) {
        String montoacobrar = montoFirebase+"";
        String[] split = montoacobrar.split("\\.");
        String enteroP = split[0];
        String decimalP = split[1];
        tvMontoEntero.setText(enteroP);
        tvMontoDecimal.setText(decimalP);
        monto_apagar = montoacobrar;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        txtNameComerce = (StyleTextView) rootview.findViewById(R.id.txtNameComerce);
        img_tienda = (ImageView) rootview.findViewById(R.id.img_tienda);
        layout_amount = (LinearLayout) rootview.findViewById(R.id.layout_amount_control);
        tvMontoEntero = (StyleTextView) rootview.findViewById(R.id.tv_monto_entero);
        tvMontoDecimal = (StyleTextView) rootview.findViewById(R.id.tv_monto_decimal);
        imgArrowPrev = (ImageView) rootview.findViewById(R.id.img_arrow_previous);
        cobrar = (StyleTextView) rootview.findViewById(R.id.cobrar);
        edtConcept = (EditText) rootview.findViewById(R.id.edtConcept);
        inputConcept = rootview.findViewById(R.id.text_input_concepto);
        et_amount.addTextChangedListener(new NumberCalcTextWatcher(et_amount, tvMontoEntero, tvMontoDecimal, edtConcept, this));
        keyboardView.setKeyBoard(getActivity(), R.xml.keyboard_nip);
        keyboardView.setPreviewEnabled(false);
        btncobrar.setOnClickListener(this);
        txtNameComerce.setText(titulo);
        et_amount.requestFocus();

        if(!codevisivility){
            img_tienda.setImageResource(R.drawable.ic_aba);


        }


/*      Se comenta este codigo para quitar el Shebron y la funcionalidad, se deja por si se retoma el disenio
        if (getActivity() instanceof AccountActivity) {
            imgArrowPrev.setVisibility(View.VISIBLE);
            imgArrowPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((QuickBalanceContainerFragment) getParentFragment()).getQuickBalanceManager().onBackPress();
                }
            });
        }*/


        // Make the custom keyboard appear
        et_amount.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                keyboardView.showCustomKeyboard(v);
            } else {
                //keyboardView.hideCustomKeyboard();
            }
        });

        /**
         * Agregamos el Listener al layout que contiene todos los elementos, esto es para que se
         * abra el teclado custom
         */
        layout_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_amount.requestFocus();
                keyboardView.showCustomKeyboard(v);
            }
        });

        et_amount.setOnTouchListener(new View.OnTouchListener() {
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

        //edtConcept.setOnEditTextImeBackListener(this);
        edtConcept.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtConcept.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    et_amount.requestFocus();
                    return true;
                }
                return false;
            }
        });
        edtConcept.setOnFocusChangeListener((view, b) -> {
            if (b) {
                inputConcept.setBackgroundResource(R.drawable.inputtext_active);
            } else {
                inputConcept.setBackgroundResource(R.drawable.inputtext_normal);
                et_amount.requestFocus();
            }
        });
        et_amount.setText(montoFirebase);
        edtConcept.setText(null);
        //mySeekBar.setProgress(0);
        NumberCalcTextWatcher.cleanData();
        et_amount.requestFocus();

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.btncobrar){
            Autentifica();
        }

    }

    private void Autentifica() {
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
                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
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
    public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

    }

    @Override
    public void onImeBack() {

    }

    @Override
    public void onChangeTextListener(String text) {

    }

    @Override
    public void generatecode() {
        loadOtpHuella();
    }
    private void actionChargecobro() {
        //String valueAmount = et_amount.getText().toString().trim();
        App.getInstance().setCurrentMount(et_amount.getText().toString().trim());
        String valueAmount = App.getInstance().getCurrentMount();
        if (valueAmount.length() > 0 && !valueAmount.equals(getString(R.string.mount_cero))) {
            try {

                StringBuilder cashAmountBuilder = new StringBuilder(valueAmount);

                // Limpiamos del caracter $ en caso de tenerlo
                int positionMoney = valueAmount.indexOf("$");
                if (positionMoney == 0) {
                    valueAmount = cashAmountBuilder.deleteCharAt(0).toString();
                }

                // Limpiamos del "," que tenemos del EditText auxiliar
                int positionQuote = valueAmount.indexOf(",");
                if (positionQuote > 0) {
                    String[] valueAmountArray = valueAmount.split(",");
                    valueAmount = valueAmountArray[0] + valueAmountArray[1];
                }


                current_mount = Float.parseFloat(valueAmount);
                monto_apagar = valueAmount;
                String current_concept = edtConcept.getText().toString().trim();//Se agrega Concepto opcional
                if (current_mount >= MIN_AMOUNT) {



                }

            } catch (NumberFormatException e) {
                //     showValidationError(getString(R.string.mount_valid));
            }
        }
    }


    private void loadOtpHuellados() {
        if (montoFirebase.equals( monto_apagar)) {
            final String[] LASTMESSAGE = {NULL};
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
            datetime = dateformat.format(c.getTime());

            int b = Math.round(current_mount);

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            Query lastQuery = databaseReference.child("QR").child("Transactions").orderByKey().limitToLast(1);
            lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    // Log.d("",ds.child("status").getValue().toString()+"");
                    for (DataSnapshot snapshot: ds.getChildren()) {
                        snapshot.getRef().child("status").setValue("okay");
                        snapshot.getRef().child("timeStamp").setValue(datetime);

                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    LASTMESSAGE[0] = "No-Last-MESSAGE-EXIT";
                } });







            // DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            // TransactionQR transactionQR = new TransactionQR(45,"aprobada","Hora de hoy");
            // database.child("QR").child("Transactions").child("-LJ0Q3GT5N33wImTM4sb").child("status").setValue("okay");
            // database.child("QR").child("Transactions").child(database.child("QR").child("Transactions").orderByKey().limitToLast(1)+"").setValue("okay");
/*
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("QR").child("Transactions");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    states = new ArrayList<>();
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        TransactionQR item = singleSnapshot.getValue(TransactionQR.class);
                        states.add(item);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.i(" QR", " Error"+databaseError.toString());

            }
        });


*/



/*
        FirebaseDatabase  database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = database.getReference();

        mDatabaseRef.child("QR").child("Transactions").child("0001").child("Status").setValue("aprobada");
*/
            App.getInstance().getPrefs().saveData(USER_BALANCE, monto_final+"");
            onEventListener.onEvent(EVENT_PAYMENT_SUCCES_QR, null);
        }else {
            UI.showErrorSnackBar(getActivity(),"El monto no coincide", Snackbar.LENGTH_SHORT);
            noIgualMonto();
            getActivity().onBackPressed();
        }

    }

    @Override
    public void generatecode(String mensaje) {

    }

    @Override
    public void generatecode(String mensaje, int errors) {

    }

    @Override
    public void showError(Object error) {

    }

    @Override
    public void validationPasswordSucces() {

    }

    @Override
    public void validationPasswordFailed(String error) {

    }

    @Override
    public void showLoader(String title) {

    }

    @Override
    public void hideLoader() {

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
    public void onOtpGenerated(String otp) {

    }

    @Override
    public void showError(Errors error) {

    }

    public void canceltransaction() {

        final String[] LASTMESSAGE = {NULL};
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        datetime = dateformat.format(c.getTime());

        int b = Math.round(current_mount);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query lastQuery = databaseReference.child("QR").child("Transactions").orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                // Log.d("",ds.child("status").getValue().toString()+"");
                for (DataSnapshot snapshot: ds.getChildren()) {
                    snapshot.getRef().child("status").setValue("denied");
                    snapshot.getRef().child("timeStamp").setValue(datetime);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                LASTMESSAGE[0] = "No-Last-MESSAGE-EXIT";
            } });
        getActivity().onBackPressed();
    }

    public void sinfondos() {

        final String[] LASTMESSAGE = {NULL};
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        datetime = dateformat.format(c.getTime());

        int b = Math.round(current_mount);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query lastQuery = databaseReference.child("QR").child("Transactions").orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                // Log.d("",ds.child("status").getValue().toString()+"");
                for (DataSnapshot snapshot: ds.getChildren()) {
                    snapshot.getRef().child("status").setValue("withoutcash");
                    snapshot.getRef().child("timeStamp").setValue(datetime);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                LASTMESSAGE[0] = "No-Last-MESSAGE-EXIT";
            } });
        getActivity().onBackPressed();
        UI.showErrorSnackBar(getActivity(),"No hay dinero suficiente ", Snackbar.LENGTH_SHORT);
    }

    public void noIgualMonto() {

        final String[] LASTMESSAGE = {NULL};
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        datetime = dateformat.format(c.getTime());

        int b = Math.round(current_mount);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query lastQuery = databaseReference.child("QR").child("Transactions").orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                // Log.d("",ds.child("status").getValue().toString()+"");
                for (DataSnapshot snapshot: ds.getChildren()) {
                    snapshot.getRef().child("status").setValue("cash");
                    snapshot.getRef().child("timeStamp").setValue(datetime);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                LASTMESSAGE[0] = "No-Last-MESSAGE-EXIT";
            } });
        getActivity().onBackPressed();
    }


    public void calldialogpasss() {

        UI.createSimpleCustomDialogQRPASS(getString(R.string.txt_enter_passwordpayqr), "",
                getFragmentManager(), new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        actionChargecobro();
                        loadOtpHuellados();
                    }

                    @Override
                    public void actionCancel(Object... params) {
                    }
                }, true, false);
    }


    public void loadOtpHuella() {

        actionChargecobro();
        if (Double.parseDouble(App.getInstance().getPrefs().loadData(USER_BALANCE))<Double.parseDouble(monto_apagar)){
            sinfondos();

        }else {

            monto_final=Double.parseDouble(App.getInstance().getPrefs().loadData(USER_BALANCE))- current_mount;


            if (!codevisivility) {

                UI.createSimpleCustomDialogQRpago("Este es tu código", "Comparte este código con tu vendedor",
                        getFragmentManager(), new DialogDoubleActions() {
                            @Override
                            public void actionConfirm(Object... params) {
                                getActivity().onBackPressed();
                                App.getInstance().getPrefs().saveData(USER_BALANCE, monto_final+"");
                            }

                            @Override
                            public void actionCancel(Object... params) {
                            }
                        }, true, false);
            } else {
//37257

                actionChargecobro();
                loadOtpHuellados();

            }
        }
    }
}
