package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.app.KeyguardManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
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
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dspread.xpos.QPOSService;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.interfaces.IBalanceView;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.ILoginContainerManager;
import com.pagatodo.yaganaste.ui.account.login.BlockCardFragment;
import com.pagatodo.yaganaste.ui.account.login.FingerprintAuthenticationDialogFragment;
import com.pagatodo.yaganaste.ui.account.login.LoginManagerContainerFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.ui_wallet.adapters.CardWalletAdpater;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsWalletAdapter;
import com.pagatodo.yaganaste.ui_wallet.dialog.DialogSetPassword;
import com.pagatodo.yaganaste.ui_wallet.dialog.DialogSetPasswordLogin;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ICardBalance;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IDialogSetPassword;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.Wallet;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.WalletBuilder;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.ui_wallet.views.BoardIndicationsView;
import com.pagatodo.yaganaste.ui_wallet.views.CustomDots;
import com.pagatodo.yaganaste.ui_wallet.views.ItemOffsetDecoration;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

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
import de.hdodenhof.circleimageview.CircleImageView;
import eu.davidea.flipview.FlipView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_ADMIN_ADQ;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_BLOCK_CARD;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_CHECK_MONEY_CARD;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_VENTAS;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_PAYMENT;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_PAYMENTQR;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_REWARDS;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_SECURE_CODE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_STORES;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ADMON_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_BALANCE_CLOSED_LOOP;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_BLOCK_CARD;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_GENERATE_TOKEN;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_PAGO_QR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_PAYMENT_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_RECOMPENSAS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_SUCURSALES;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_VENTAS_ADQAFUERA;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_EMISOR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_SETTINGS;
import static com.pagatodo.yaganaste.utils.Recursos.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_STATUS;
import static com.pagatodo.yaganaste.utils.Recursos.CONNECTION_TYPE;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_BLOQUEADA;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_BALANCE_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_SHORTCUT_CHARGE;
import static com.pagatodo.yaganaste.utils.Recursos.HUELLA_FAIL;
import static com.pagatodo.yaganaste.utils.Recursos.ID_COMERCIOADQ;
import static com.pagatodo.yaganaste.utils.Recursos.IS_OPERADOR;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.NAME_USER;
import static com.pagatodo.yaganaste.utils.Recursos.NOM_COM;
import static com.pagatodo.yaganaste.utils.Recursos.PSW_CPR;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;
import static com.pagatodo.yaganaste.utils.Recursos.STARBUCKS_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.URL_PHOTO_USER;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;

/**
 * Created by Omar on 13/02/2018.
 */

public class BalanceWalletFragment extends GenericFragment implements View.OnClickListener,
        ViewPager.OnPageChangeListener, IBalanceView, OnClickItemHolderListener,
        ICardBalance,IMyCardView ,IDialogSetPassword {
    public static final int BLOQUEO = 1;
    public static final int DESBLOQUEO = 2;
    private String cardStatusId;
    private int statusBloqueo;
    private PreferUserPresenter mPreferPresenter;
    private static final String DIALOG_FRAGMENT_TAG = "myFragment";
    private static final String SECRET_MESSAGE = "Very secret message";
    private static final String KEY_NAME_NOT_INVALIDATED = "key_not_invalidated";
    static final String DEFAULT_KEY_NAME = "default_key";
    private View rootView;
    private Preferencias prefs = App.getInstance().getPrefs();
    @BindView(R.id.img_profile_balance)
    CircleImageView crlProfileBalance;
    @BindView(R.id.txt_username_balance)
    StyleTextView txtUserNameBalance;
    @BindView(R.id.vp_balance)
    ViewPager vpBalace;
    @BindView(R.id.txt_balance_amount)
    MontoTextView txtAmountBalance;
    @BindView(R.id.txt_balance_type_payment)
    StyleTextView txtTypePaymentBalance;
    @BindView(R.id.txt_balance_card_desc)
    StyleTextView txtCardDescBalance;
    @BindView(R.id.txt_balance_card_desc2)
    StyleTextView txtCardDescBalance2;
    @BindView(R.id.img_refresh_balance)
    ImageView imgRefreshBalance;
    @BindView(R.id.rcv_balance_elements)
    RecyclerView rcvElementsBalance;
    @BindView(R.id.btn_balance_login)
    StyleButton btnLoginBalance;

    @BindView(R.id.board_indication)
    BoardIndicationsView board;
    @BindView(R.id.viewPagerCountDots)
    CustomDots pager_indicator;
    @BindView(R.id.chiandpin)
    ImageView chiandpin;
    DialogSetPasswordLogin dialogPassword;
    private Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private FingerprintManager.CryptoObject cryptoObject;
    static BalanceWalletFragment fragmentCode;
    KeyguardManager keyguardManager;
    FingerprintManager fingerprintManager;
    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;
    private SharedPreferences mSharedPreferences;
    private static Preferencias preferencias = App.getInstance().getPrefs();
    FingerprintAuthenticationDialogFragment fragment;
    private ILoginContainerManager loginContainerManager;
    private AccountPresenterNew accountPresenter;
    private CardWalletAdpater adapterBalanceCard;
    //private ElementsBalanceAdapter elementsBalanceAdpater;
    private ElementsWalletAdapter elementsWalletAdapter;
    private int pageCurrent;

    private String balanceEmisor, balanceAdq, balanceStarbucks, Status;

    public static BalanceWalletFragment newInstance() {
        fragmentCode = new BalanceWalletFragment();
        Bundle args = new Bundle();
        fragmentCode.setArguments(args);
        return fragmentCode;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginContainerManager = ((LoginManagerContainerFragment) getParentFragment()).getLoginContainerManager();
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();


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

        mPreferPresenter = new PreferUserPresenter(this);
        mPreferPresenter.setIView(this);

        this.pageCurrent = 0;
        prefs.saveDataBool(HUELLA_FAIL, false);
        Status = App.getInstance().getPrefs().loadData(CARD_STATUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
            keyguardManager = getActivity().getSystemService(KeyguardManager.class);
            fingerprintManager = getActivity().getSystemService(FingerprintManager.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_balance_wallet, container, false);
            initViews();
        }
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
            keyguardManager = getActivity().getSystemService(KeyguardManager.class);
            fingerprintManager = getActivity().getSystemService(FingerprintManager.class);
        }

        btnLoginBalance.setOnClickListener(this);
        imgRefreshBalance.setOnClickListener(this);
        txtUserNameBalance.setText("¡Hola " + prefs.loadData(NAME_USER) + "!");
        balanceEmisor = prefs.loadData(USER_BALANCE);
        balanceAdq = prefs.loadData(ADQUIRENTE_BALANCE);
        balanceStarbucks = prefs.loadData(STARBUCKS_BALANCE);
        elementsWalletAdapter = new ElementsWalletAdapter(getActivity(), this, true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(),
                R.dimen.item_offset);
        rcvElementsBalance.addItemDecoration(itemDecoration);
        rcvElementsBalance.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rcvElementsBalance.setHasFixedSize(true);
        if (prefs.loadData(CARD_NUMBER).isEmpty()) {
            Status = ESTATUS_CUENTA_BLOQUEADA;
        }
        setBalanceCards();
        board.setreloadOnclicklistener(view -> {
            if (adapterBalanceCard.getElemenWallet(this.pageCurrent) != null) {
                if (adapterBalanceCard.getElemenWallet(this.pageCurrent).getTypeWallet() == TYPE_EMISOR)
                    accountPresenter.updateBalance();
                else if (adapterBalanceCard.getElemenWallet(this.pageCurrent).getTypeWallet() != TYPE_SETTINGS) {
                    accountPresenter.updateBalanceAdq(adapterBalanceCard.getElemenWallet(this.pageCurrent));
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePhoto();
        //onRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_balance_login:
                loginContainerManager.loadLoginFragment();
                break;
        }
    }


    private void loadFinguerPrint(){
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


    private void upDateSaldo(String saldo) {
        adapterBalanceCard.updateSaldo(this.pageCurrent, saldo);
        board.setTextMonto(saldo);
    }


    @Override
    public void updateBalance() {
        balanceEmisor = prefs.loadData(USER_BALANCE);
        hideLoader();
        upDateSaldo(StringUtils.getCurrencyValue(balanceEmisor));

    }

    @Override
    public void updateBalanceAdq() {
        balanceAdq = prefs.loadData(ADQUIRENTE_BALANCE);
        upDateSaldo(StringUtils.getCurrencyValue(balanceAdq));
        hideLoader();
        //checkStatusCard();
    }

    @Override
    public void updateBalanceStarbucks() {
        balanceStarbucks = prefs.loadData(STARBUCKS_BALANCE);
        hideLoader();
    }

    @Override
    public void updateStatus() {
        hideLoader();
        Status = App.getInstance().getPrefs().loadData(CARD_STATUS);
        setBalanceCards();
    }

    @Override
    public void nextScreen(String event, Object data) {

        if (event =="EVENT_GO_MAINTAB" ){
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
        }else {

            onEventListener.onEvent(event, data);
        }
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
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
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        String messageStatus = "";
        if (statusBloqueo == BLOQUEO) {
            messageStatus = getResources().getString(R.string.card_locked_success);
            App.getInstance().getPrefs().saveData(CARD_STATUS, Recursos.ESTATUS_CUENTA_BLOQUEADA);
        } else if (statusBloqueo == DESBLOQUEO) {
            messageStatus = getResources().getString(R.string.card_unlocked_success);
            App.getInstance().getPrefs().saveData(CARD_STATUS, Recursos.ESTATUS_CUENTA_DESBLOQUEADA);
        }

        // Armamos
        UI.showSuccessSnackBar(getActivity(), messageStatus + "\n" +
                getResources().getString(R.string.card_num_autorization) + " "
                + response.getData().getNumeroAutorizacion(), Snackbar.LENGTH_LONG);
        try {
            ApiAdtvo.cerrarSesion();
            // Toast.makeText(App.getContext(), "Close Session " + response.getData().getNumeroAutorizacion(), Toast.LENGTH_SHORT).show();
        } catch (OfflineException e) {
            e.printStackTrace();
        }

        // update de datos de Card instantaneos
        cardStatusId = App.getInstance().getPrefs().loadData(CARD_STATUS);
        if (cardStatusId.equals("1")) {
            updateStatus();

        } else {
            // La tarjeta esta BLOQUEADA, mostramos la cCard Gray
            updateStatus();
        }

    }

    @Override
    public void sendErrorBloquearCuentaToView(String mensaje) {

    }

    @Override
    public void showError(Object error) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        pager_indicator.selectDots(pageCurrent % adapterBalanceCard.getSize(), position % adapterBalanceCard.getSize());
        this.pageCurrent = position;
        adapterBalanceCard.resetFlip();
        setVisibilityBackItems(GONE);
        setVisibilityFrontItems(VISIBLE);

        if (prefs.loadDataBoolean(IS_OPERADOR, false)) {
            rcvElementsBalance.setLayoutManager(new GridLayoutManager(getContext(), 2));
            accountPresenter.updateBalance();
        } else {
            if (adapterBalanceCard.getElemenWallet(position) != null) {
                if (adapterBalanceCard.getElemenWallet(position).getTypeWallet() != TYPE_SETTINGS) {
                    if (adapterBalanceCard.getElemenWallet(position).getTypeWallet() == TYPE_EMISOR) {
                        rcvElementsBalance.setLayoutManager(new GridLayoutManager(getContext(), 1));
                        //    accountPresenter.updateBalance();
                        upDateSaldo(StringUtils.getCurrencyValue(balanceEmisor));
                    } else {
                        int idcomercio = adapterBalanceCard.getElemenWallet(position).getAgentes().getIdComercio();
                        boolean esUyU = adapterBalanceCard.getElemenWallet(position).getAgentes().isEsComercioUYU();
                        App.getInstance().getPrefs().saveData(NOM_COM, adapterBalanceCard.getElemenWallet(position).getAgentes().getNombreNegocio());
                        App.getInstance().getPrefs().saveData(ID_COMERCIOADQ, idcomercio + "");
                        for (Operadores op : adapterBalanceCard.getElemenWallet(position).getAgentes().getOperadores()) {
                            if (op.getIsAdmin())
                                RequestHeaders.setIdCuentaAdq(op.getIdUsuarioAdquirente());
                        }
                        if (esUyU) {
                            rcvElementsBalance.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        } else {
                            rcvElementsBalance.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        }
                        //   accountPresenter.updateBalanceAdq(adapterBalanceCard.getElemenWallet(position));
                        upDateSaldo(StringUtils.getCurrencyValue(balanceAdq));
                    }
                }
            }
        }
        updateOperations(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
            UI.showErrorSnackBar(getActivity(), getResources().getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
        }

    }
    public void showDialogPassword() {
        dialogPassword = new DialogSetPasswordLogin();
        dialogPassword.setListener(this);
        dialogPassword.show(getActivity().getFragmentManager(), "Dialog Set Password");
    }
    @Override
    public void onItemClick(Object item) {
        ElementView elementView = (ElementView) item;
        adapterBalanceCard.resetFlip();
        setVisibilityBackItems(GONE);
        setVisibilityFrontItems(VISIBLE);
        switch (elementView.getIdOperacion()) {
            case OPTION_BLOCK_CARD:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !preferencias.loadData(PSW_CPR).equals("")) {
                    if (!fingerprintManager.isHardwareDetected()) {
                        showDialogPassword();
                    }else {
                        loadFinguerPrint();
                    }
                }else {
                    showDialogPassword();
                }

                //nextScreen(EVENT_BLOCK_CARD, null);
                break;
            case OPTION_PAGO_QR:
                nextScreen(EVENT_PAYMENTQR, null);
                break;
            case OPTION_GENERATE_TOKEN:
                nextScreen(EVENT_SECURE_CODE, null);
                break;
            case OPTION_PAYMENT_ADQ:
                Bundle bundle = new Bundle();
                bundle.putString(CONNECTION_TYPE, Utils.getTypeConnection());
                FirebaseAnalytics.getInstance(App.getContext()).logEvent(EVENT_SHORTCUT_CHARGE, bundle);
                JSONObject props = new JSONObject();
                if (!BuildConfig.DEBUG) {
                    try {
                        props.put(CONNECTION_TYPE, Utils.getTypeConnection());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    App.mixpanel.track(EVENT_SHORTCUT_CHARGE, props);
                }
                nextScreen(EVENT_PAYMENT, null);
                break;
            case OPTION_ADMON_ADQ:
                nextScreen(EVENT_ADMIN_ADQ, null);
                break;
            case OPTION_RECOMPENSAS:
                nextScreen(EVENT_REWARDS, null);
                break;
            case OPTION_SUCURSALES:
                nextScreen(EVENT_STORES, null);
                break;
            case OPTION_BALANCE_CLOSED_LOOP:
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if (!adapter.isEnabled() && App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                    Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enabler);
                } else {
                    nextScreen(EVENT_CHECK_MONEY_CARD, null);
                }
                break;
            case OPTION_VENTAS_ADQAFUERA:
                nextScreen(EVENT_GO_VENTAS, null);
                break;

            default:
                break;
        }
    }

    @Override
    public void onCardClick(View v, int position) {
        if (!((FlipView) v).isFlipped()) {
            ((FlipView) v).flip(true);
            if (prefs.loadDataBoolean(SHOW_BALANCE, true)) {
                setVisibilityBackItems(VISIBLE);
                setVisibilityFrontItems(GONE);
                if (adapterBalanceCard.getElemenWallet(this.pageCurrent).getTypeWallet() == TYPE_EMISOR)
                    accountPresenter.updateBalance();
                else if (adapterBalanceCard.getElemenWallet(this.pageCurrent).getTypeWallet() != TYPE_SETTINGS) {
                    accountPresenter.updateBalanceAdq(adapterBalanceCard.getElemenWallet(this.pageCurrent));
                }
            }
        } else {
            ((FlipView) v).flip(false);
            setVisibilityBackItems(GONE);
            setVisibilityFrontItems(VISIBLE);
        }
    }

    public void onRefresh(ElementWallet elementWallet) {
        if (!UtilsNet.isOnline(getActivity())) {
            UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_LONG);
        } else if (!prefs.loadData(CARD_NUMBER).isEmpty()) {
            //accountPresenter.updateBalance(elementWallet);
        }

    }

    /*private void checkStatusCard() {
        String f = SingletonUser.getInstance().getCardStatusId();
        if (f == null || f.isEmpty() || f.equals("0")) {
            String mTDC = prefs.loadData(CARD_NUMBER);
            accountPresenter.getEstatusCuenta(mTDC);
        } else {
            Status = f;
            App.getInstance().getPrefs().saveData(CARD_STATUS, f);
            setBalanceCards();
        }
    }*/

    private void setBalanceCards() {
        setVisibilityBackItems(GONE);
        setVisibilityFrontItems(VISIBLE);
        adapterBalanceCard = new CardWalletAdpater(false, this);
        if (prefs.containsData(IS_OPERADOR)) {
            rcvElementsBalance.setLayoutManager(new GridLayoutManager(getContext(), 2));
            chiandpin.setVisibility(VISIBLE);
            vpBalace.setVisibility(GONE);
            try {
                if (App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) != QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                    chiandpin.setImageResource(R.mipmap.lector_front);
                    chiandpin.getLayoutParams().width = 250;
                }
            } catch (Exception e) {
                Log.d("Lector", "Sin opc de lector seleccionada");
            }

        }
        Wallet walletList = WalletBuilder.createWalletsBalance();
        adapterBalanceCard.addAllList(walletList.getList());
        adapterBalanceCard.notifyDataSetChanged();
        //adapterBalanceCard.setListener(this);

        vpBalace.setAdapter(adapterBalanceCard);
        vpBalace.setCurrentItem(this.pageCurrent);
        vpBalace.setOffscreenPageLimit(2);
        vpBalace.addOnPageChangeListener(this);
        pager_indicator.removeAllViews();
        updateOperations(this.pageCurrent);
        if (accountPresenter != null) {
            accountPresenter.updateBalance();
        }
        if (!prefs.containsData(IS_OPERADOR)) {
            setUiPageViewController();
        }
    }

    private void setUiPageViewController() {
        pager_indicator.setView(this.pageCurrent % adapterBalanceCard.getSize(), adapterBalanceCard.getSize());
    }


    private void updateOperations(final int position) {
        if (adapterBalanceCard.getElemenWallet(position) != null) {
            elementsWalletAdapter.setListOptions(adapterBalanceCard.getElemenWallet(position).getElementViews());
            elementsWalletAdapter.notifyDataSetChanged();

            rcvElementsBalance.setAdapter(elementsWalletAdapter);
            rcvElementsBalance.scheduleLayoutAnimation();

            board.setTextSaldo(adapterBalanceCard.getElemenWallet(position).getTipoSaldo());
            if (adapterBalanceCard.getElemenWallet(position).isUpdate()) {
                board.setReloadVisibility(View.VISIBLE);
            } else {
                board.setReloadVisibility(View.INVISIBLE);
            }
            txtCardDescBalance.setText(adapterBalanceCard.getElemenWallet(position).getTitleDesRes());
            txtCardDescBalance2.setText(adapterBalanceCard.getElemenWallet(position).getCardNumberRes());
        }
    }

    private void updatePhoto() {
        String mUserImage = prefs.loadData(URL_PHOTO_USER);

        Picasso.with(getContext())
                .load(StringUtils.procesarURLString(mUserImage))
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .error(R.mipmap.icon_user)
                .into(crlProfileBalance);
    }

    private void setVisibilityFrontItems(int visibility) {
        txtCardDescBalance.setVisibility(visibility);
        txtCardDescBalance2.setVisibility(visibility);
    }

    private void setVisibilityBackItems(int visibility) {
        board.setVisibility(visibility);
    }

    @Override
    public void onPasswordSet(String xps) {
        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            Preferencias preferencias = App.getInstance().getPrefs();
            String[] pass = Utils.cipherAES(preferencias.loadData(PSW_CPR), false).split("-");
            accountPresenter.login(RequestHeaders.getUsername(),xps);
            onEventListener.onEvent(EVENT_SHOW_LOADER, getContext().getString(R.string.update_data));
        } else {
            UI.showErrorSnackBar(getActivity(), getResources().getString(R.string.no_internet_access), Snackbar.LENGTH_SHORT);
        }



    }

    @Override
    public void onOtpGenerated(String otp) {

    }

    @Override
    public void showError(Errors error) {

    }
}
