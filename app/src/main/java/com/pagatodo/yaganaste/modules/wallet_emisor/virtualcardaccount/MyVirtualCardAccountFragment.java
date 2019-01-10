package com.pagatodo.yaganaste.modules.wallet_emisor.virtualcardaccount;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.fingerprint.FingerprintManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.pagatodo.view_manager.components.IconButton;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaUyUResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EmisorResponse;
import com.pagatodo.yaganaste.modules.wallet_emisor.WalletMainActivity;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.login.AccessCodeGenerateFragment;
import com.pagatodo.yaganaste.ui.account.login.BlockCardFragment;
import com.pagatodo.yaganaste.ui.account.login.FingerprintAuthenticationDialogFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.ui_wallet.fragments.BalanceWalletFragment;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.Container;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.pojos.TextData;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.UtilsIntents;
import com.pagatodo.yaganaste.utils.qrcode.InterbankQr;
import com.pagatodo.yaganaste.utils.qrcode.QrcodeGenerator;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringUtils;

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

import static android.content.Context.WINDOW_SERVICE;
import static android.view.View.GONE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui.preferuser.MyCardFragment.BLOQUEO;
import static com.pagatodo.yaganaste.ui.preferuser.MyCardFragment.DESBLOQUEO;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_STATUS;
import static com.pagatodo.yaganaste.utils.Recursos.HUELLA_FAIL;
import static com.pagatodo.yaganaste.utils.Recursos.PSW_CPR;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;
import static com.pagatodo.yaganaste.utils.StringUtils.getCreditCardFormat;

public class MyVirtualCardAccountFragment extends SupportFragment implements View.OnClickListener,IMyCardView {


    @BindView(R.id.text_data_list)
    ListView listView;
    @BindView(R.id.active_card)
    IconButton activeCardBtn;
    @BindView(R.id.change_nip)
    IconButton changeNip;
    @BindView(R.id.block_card)
    IconButton blockCard;
    private View rootView;
    String mensaje, cardNumber;
    boolean onlineNetWork, onlineGPS;
    private WalletMainActivity activity;


    /**
     * FingerPrint Autentication
     * @return
     */
    private FingerprintManager.CryptoObject cryptoObject;
    KeyguardManager keyguardManager;
    FingerprintManager fingerprintManager;
    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;
    private SharedPreferences mSharedPreferences;
    private static Preferencias preferencias = App.getInstance().getPrefs();
    FingerprintAuthenticationDialogFragment fragment;
    static MyVirtualCardAccountFragment fragmentCode;
    private static final String DIALOG_FRAGMENT_TAG = "myFragment";
    private static final String SECRET_MESSAGE = "Very secret message";
    private static final String KEY_NAME_NOT_INVALIDATED = "key_not_invalidated";
    static final String DEFAULT_KEY_NAME = "default_key";
    private Cipher cipher;
    public static final int BLOQUEO = 1;
    public static final int DESBLOQUEO = 2;
    private String cardStatusId;
    private int statusBloqueo;
    private AccountPresenterNew accountPresenter;
    private PreferUserPresenter mPreferPresenter;

    public static MyVirtualCardAccountFragment newInstance() {
        fragmentCode = new MyVirtualCardAccountFragment();
        Bundle args = new Bundle();
        fragmentCode.setArguments(args);
        return fragmentCode;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (WalletMainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
            keyguardManager = getActivity().getSystemService(KeyguardManager.class);
            fingerprintManager = getActivity().getSystemService(FingerprintManager.class);
        }

        accountPresenter = new AccountPresenterNew(getActivity());
        mPreferPresenter = new PreferUserPresenter(this);
        mPreferPresenter.setIView(this);


        if (accountPresenter != null) {
            accountPresenter.setIView(this);
            // Consultamos el estado del Singleton, que tiene el estado de nuestra tarjeta
            cardStatusId = App.getInstance().getPrefs().loadData(CARD_STATUS);
            if (cardStatusId == null || cardStatusId.equals("-1")) {
                cardStatusId = "1";
            }

            if (cardStatusId.equals("2")) {
                // Significa que la card esta bloqueada, despues de la operacion pasa a desbloqueada
                statusBloqueo = BLOQUEO;
            } else {
                // Significa que la card esta desbloqueada, despues de la operacion pasa a bloqueada
                statusBloqueo = DESBLOQUEO;
            }
            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                Log.e("YG", "cardStatusId: " + cardStatusId + " statusBloqueo: " + statusBloqueo);
            }

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case android.R.id.home:
                onBackPressed();
                return true;*/
            case R.id.action_share:
                UtilsIntents.IntentShare(getContext(), mensaje);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onEventListener.onEvent(ToolBarActivity.EVENT_CHANGE_TOOLBAR_VISIBILITY, true);
        return inflater.inflate(R.layout.fragment_deposito_datos, container, false);
    }


    private void getStatusGPS() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        onlineNetWork = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        onlineGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
        ClienteResponse userData = SingletonUser.getInstance().getDataUser().getCliente();



        cardStatusId = App.getInstance().getPrefs().loadData(CARD_STATUS);
        if (cardStatusId == null || cardStatusId.equals("-1")) {
            cardStatusId = "1";
        }

        if (cardStatusId.equals("2")) {
            // Significa que la card esta bloqueada, despues de la operacion pasa a desbloqueada
            statusBloqueo = BLOQUEO;
        } else {
            // Significa que la card esta desbloqueada, despues de la operacion pasa a bloqueada
            statusBloqueo = DESBLOQUEO;
        }
        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
            Log.e("YG", "cardStatusId: " + cardStatusId + " statusBloqueo: " + statusBloqueo);
        }

        String nombreprimerUser;
        String apellidoMostrarUser;
        if (userData.getPrimerApellido().isEmpty()) {
            apellidoMostrarUser = userData.getSegundoApellido();
        } else {
            apellidoMostrarUser = userData.getPrimerApellido();
        }
        nombreprimerUser = StringUtils.getFirstName(userData.getNombre());
        if (nombreprimerUser.isEmpty()) {
            nombreprimerUser = userData.getNombre();
        }

        EmisorResponse usuario = SingletonUser.getInstance().getDataUser().getEmisor();
        //String name = usuario.getNombre().concat(SPACE).concat(usuario.getPrimerApellido()).concat(SPACE).concat(usuario.getSegundoApellido());

        //txtNameTitular.setText(name);
        String name = nombreprimerUser + " " + apellidoMostrarUser;
        //txtNameTitular.setText(name);

        String celPhone = "";
        String clabe = "";
        cardNumber = "";
        if (usuario.getCuentas() != null && usuario.getCuentas().size() >= 1) {
            CuentaUyUResponse cuenta = usuario.getCuentas().get(0);
            celPhone = usuario.getCuentas().get(0).getTelefono();
            cardNumber = getCreditCardFormat(cuenta.getTarjetas().get(0).getNumero());
            clabe = cuenta.getCLABE();
        }
        showQRCode(name, celPhone, usuario.getCuentas().get(0));

        mensaje = getString(R.string.string_share_deposits, name, celPhone, clabe, cardNumber);

        Container list = new Container();
        list.addTextDataA(new TextData(R.string.datos_deposito_titular, name));
        list.addTextDataA(new TextData(R.string.datos_depsito_numero_celular, celPhone));
        list.addTextDataA(new TextData(R.string.datos_deposito_clabe, clabe));
        list.addTextDataA(new TextData(R.string.datos_deposito_num_card, cardNumber));
        listView.setAdapter(ContainerBuilder.DEPOSITO(getContext(), list));
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        //btnDepositar.setOnClickListener(this);
        //imageshae.setOnClickListener(this);
        WalletMainActivity.showToolBarMenu();
        activeCardBtn.setOnClickListener(this);
        changeNip.setOnClickListener(this);
        blockCard.setOnClickListener(this);
    }

    private void checkState(String state) {
        switch (state) {
            case Recursos.ESTATUS_CUENTA_DESBLOQUEADA:
                //mycard_switch.setChecked(false);
                blockCard.setIconButton(getResources().getDrawable(R.drawable.ic_bloquear_wallet),
                        getResources().getString(R.string.name_block_card));
                break;
            case Recursos.ESTATUS_CUENTA_BLOQUEADA:
                //mycard_switch.setChecked(true); ic_desbloquear_wallet
                blockCard.setIconButton(getResources().getDrawable(R.drawable.ic_desbloquear_wallet),
                        getResources().getString(R.string.desbloquear_tarjeta));
                break;
            default:
                if (BuildConfig.DEBUG)
                    Log.d("ESTAUS", state);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        getStatusGPS();
        switch (v.getId()){
            case R.id.deposito_Share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Compartir con.."));
                break;
            case R.id.active_card:
                this.activity.getRouter().onShowActivatePhysicalCard();
                break;
            case R.id.block_card:
                loadFinguerPrint();
                break;
            case R.id.change_nip:
                this.activity.getRouter().onShowMyChangeNip();
                break;
        }
    }
    private void loadFinguerPrint(){

        cardStatusId = App.getInstance().getPrefs().loadData(CARD_STATUS);
        if (cardStatusId == null || cardStatusId.equals("-1")) {
            cardStatusId = "1";
        }

        if (cardStatusId.equals("2")) {
            // Significa que la card esta bloqueada, despues de la operacion pasa a desbloqueada
            statusBloqueo = BLOQUEO;
        } else {
            // Significa que la card esta desbloqueada, despues de la operacion pasa a bloqueada
            statusBloqueo = DESBLOQUEO;
        }
        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
            Log.e("YG", "cardStatusId: " + cardStatusId + " statusBloqueo: " + statusBloqueo);
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
                        fragment = new FingerprintAuthenticationDialogFragment();
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
            e.printStackTrace();
        }
    }

    public void loadOtpHuella() {
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
        if (getParentFragment() != null && getParentFragment().isMenuVisible()) {
            //((ToolBarActivity) getActivity()).setVisibilityPrefer(false);
        }

        String statusId = SingletonUser.getInstance().getCardStatusId();
        if (SingletonUser.getInstance().getDataUser().getEmisor().getCuentas().get(0).getTarjetas().get(0).getNumero().equals("")) {
            checkState("0");
        } else if (statusId != null && !statusId.isEmpty()) {
            // && statusId.equals(Recursos.ESTATUS_DE_NO_BLOQUEADA)
            checkState(statusId);
        } else {
            checkState(App.getInstance().getPrefs().loadData(CARD_NUMBER));
        }
    }

    private void showQRCode(String name, String cellPhone, CuentaUyUResponse usuario) {
        //Find screen size
        WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;
        //MyQr myQr = new MyQr(name, cellPhone, usuario.getTarjetas().get(0).getNumero(), usuario.getCLABE());
        InterbankQr qr = new InterbankQr();
        qr.setVersion("0.1");
        qr.setType("100");
        qr.getOptionalData().countryCode = "MX";
        qr.getOptionalData().currencyCode = "MXN";
        qr.getOptionalData().bankId = "148";
        qr.getOptionalData().beneficiaryAccount = usuario.getCLABE().replace(" ", "");
        qr.getOptionalData().beneficiaryName = (name.length() > 19 ? name.substring(0, 19) : name);
        qr.getOptionalData().referenceNumber = DateUtil.getDayMonthYear();
        String gson = new Gson().toJson(qr);
        //String gsonCipher = Utils.cipherAES(gson, true);
        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
            Log.e("Ya Ganaste", "QR JSON: " + /*myQr.toString()*/gson /*+ "\nQR Ciphered: " + gsonCipher*/);
        }
        QrcodeGenerator qrCodeEncoder = new QrcodeGenerator(gson, null,
                BarcodeFormat.QR_CODE.toString(), smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            //imgYaGanasteQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void showLoader(String message) {
        if (onEventListener != null) {
            onEventListener.onEvent(EVENT_SHOW_LOADER, message);
        }
    }

    @Override
    public void hideLoader() {
        if (onEventListener != null) {
            onEventListener.onEvent(EVENT_HIDE_LOADER, null);
        }
    }


    @Override
    public void sendSuccessBloquearCuentaToView(BloquearCuentaResponse response) {
        String messageStatus = "";
        if (statusBloqueo == BLOQUEO) {
            App.getInstance().getPrefs().saveData(CARD_STATUS, Recursos.ESTATUS_CUENTA_BLOQUEADA);
            messageStatus = getResources().getString(R.string.card_locked_success);
            SingletonUser.getInstance().setCardStatusId(Recursos.ESTATUS_CUENTA_BLOQUEADA);
            checkState(Recursos.ESTATUS_CUENTA_BLOQUEADA);



        } else if (statusBloqueo == DESBLOQUEO) {
            App.getInstance().getPrefs().saveData(CARD_STATUS, Recursos.ESTATUS_CUENTA_DESBLOQUEADA);
            messageStatus = getResources().getString(R.string.card_unlocked_success);
            SingletonUser.getInstance().setCardStatusId(Recursos.ESTATUS_CUENTA_DESBLOQUEADA);
            checkState(Recursos.ESTATUS_CUENTA_DESBLOQUEADA);
        }

        UI.showSuccessSnackBar(getActivity(), messageStatus + "\n" +
                getResources().getString(R.string.card_num_autorization) + " "
                + response.getData().getNumeroAutorizacion(), Snackbar.LENGTH_LONG);

    }

    @Override
    public void sendErrorBloquearCuentaToView(String mensaje) {
        hideLoader();
        UI.showErrorSnackBar(getActivity(), getResources().getString(R.string.no_internet_access), Snackbar.LENGTH_LONG);
    }

}
