package com.pagatodo.yaganaste.modules.emisor;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ShareActionProvider;

import com.dspread.xpos.QPOSService;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Giros;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.TiposReembolsoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerBancoBinResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarTitularCuentaResponse;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.modules.emisor.BlockCard.BlockCardFragment;
import com.pagatodo.yaganaste.modules.emisor.VirtualCardAccount.MyVirtualCardAccountFragment;
import com.pagatodo.yaganaste.modules.emisor.movements.MovmentsContentFragment;
import com.pagatodo.yaganaste.modules.management.response.QrValidateResponse;
import com.pagatodo.yaganaste.modules.management.singletons.NotificationSingleton;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.AdqActivity;
import com.pagatodo.yaganaste.ui._controllers.BussinesActivity;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.account.login.FingerprintAuthenticationDialogFragment;
import com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DatosNegocioFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DetailTransactionFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DocumentosFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DomicilioNegocioFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.GetMountFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.GetSignatureFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.InsertDongleFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.RemoveCardFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.TransactionResultFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DetailsAdquirenteFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DetailsAdquirenteFragment.MovTab;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DetailsEmisorFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PersonalAccountFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyCardReportaTarjetaFragment;
import com.pagatodo.yaganaste.ui.preferuser.presenters.MyDongleFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.AdminCardsFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.AdminStarbucksFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.AdministracionFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.ChatFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.DescargarEdoCuentaFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.DetalleOperadorFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.EdoCuentaFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.FavoritesFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.FrogetPasswordStarbucks;
import com.pagatodo.yaganaste.ui_wallet.fragments.LoginStarbucksFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.MapStarbucksFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.MovementsSbFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.OperadorSuccesFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.OperadoresUYUFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.PairBluetoothFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.RegisterCompleteStarbucksFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.RegisterStarbucksFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.RewardsStarbucksFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.SelectDongleFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.SendTicketFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.TimeRepaymentFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.TutorialsFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.VentasDiariasFragment;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;
import com.pagatodo.yaganaste.utils.keyboard.UiKeyBoard;
import com.pagatodo.yaganaste.utils.qrcode.Auxl;
import com.pagatodo.yaganaste.utils.qrcode.Qrlectura;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.crypto.KeyGenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_DETALLE_PROMO;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_OPERADOR_DETALLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_PAYMENT;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_RETRY_PAYMENT;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.SUCCES_CHANGE_STATUS_OPERADOR;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_DETAIL_TRANSACTION;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_GET_SIGNATURE;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_INSERT_DONGLE;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_INSERT_DONGLE_CANCELATION;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_LOGIN_FRAGMENT;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_TRANSACTION_RESULT;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_DOC_CHECK;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_COMPLETE;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_DOCUMENTS;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_SET_BUSINESS_LIST;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.REQUEST_CODE_FAVORITES;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.PICK_WALLET_TAB_REQUEST;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.RESULT_CODE_SELECT_DONGLE;
import static com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment.COMPLETE_MESSAGES.ADD_FAV;
import static com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment.COMPLETE_MESSAGES.ADQ_REVISION;
import static com.pagatodo.yaganaste.ui.adquirente.fragments.GetMountFragment.REQUEST_ID_MULTIPLE_PERMISSIONS;
import static com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment.RESULT_CANCEL_OK;
import static com.pagatodo.yaganaste.ui_wallet.fragments.WalletTabFragment.ITEM_OPERATION;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ADDFAVORITE_PAYMENT;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ADD_NEW_FAV_SUCCES;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ADMON_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ADMON_EMISOR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ADMON_STARBUCK;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_CHARGE_WITH_CARD;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_CONFIG_DONGLE;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_CONTINUE_DOCS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_DEPOSITO;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ERROR_ADDRESS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ERROR_ADDRESS_DOCS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ESTADOS_CUENTA;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_FIRST_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_MVIMIENTOS_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_MVIMIENTOS_BUSSINES;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_MVIMIENTOS_EMISOR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_MVIMIENTOS_STARBUCKS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_MY_CARD_SALES;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_OPERADORES_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_PAGO_QR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_PAYMENT_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_RECOMPENSAS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_REMBOLSO_FIRST;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_SETTINGSCARD;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_SUCURSALES;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_TUTORIALS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_VENTAS_ADQ;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE_COMERCE;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENTS_ADQUIRENTE;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_PENDIENTE;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_DOCUMENTACION;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.FIST_ADQ_LOGIN;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_CONFIG_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_PENDIENTE;
import static com.pagatodo.yaganaste.modules.register.RegActivity.RESULT_CODE_KEYBOARD;

public class WalletMainActivity extends LoaderActivity implements View.OnClickListener,
        WalletEmisorContracts.Listener, FingerprintAuthenticationDialogFragment.generateCodehuella{

    public final static String EVENT_GO_NIP_CHANGE = "EVENT_GO_NIP_CHANGE";
    public final static String EVENT_GO_CONFIG_REPAYMENT = "EVENT_GO_CONFIG_REPAYMENT";
    private final static String EVENT_GO_CONFIG_REPAYMENT_BACK = "EVENT_GO_CONFIG_REPAYMENT_BACK";
    public final static String EVENT_GO_SELECT_DONGLE = "EVENT_GO_SELECT_DONGLE";
    //public final static String EFVENT_GO_SELECT_DONGLE_BACK = "EVENT_GO_SELECT_DONGLE_BACK";
    public final static String EVENT_GO_CONFIG_DONGLE = "EVENT_GO_CONFIG_DONGLE";
    //public final static String EVENT_GO_CONFIG_DONGLE_BACK = "EVENT_GO_CONFIG_DONGLE_BACK";
    public final static String EVENT_GO_CARD_REPORT = "EVENT_GO_CARD_REPORD";
    public final static String EVENT_GO_DETAIL_EMISOR = "EVENT_GO_DETAIL_EMISOR";
    public final static String EVENT_GO_DETAIL_ADQ = "EVENT_GO_DETAIL_ADQ";
    private final static String EVENT_GO_TO_FINALIZE_SUCCESS = "FINALIZAR_CANCELACION_SUCCESS";
    private final static String EVENT_GO_TO_FINALIZE_ERROR = "FINALIZAR_CANCELACION_ERROR";
    public final static String EVENT_GO_TO_LOGIN_STARBUCKS = "EVENT_GO_TO_LOGIN_STARBUCKS";
    public final static String EVENT_GO_TO_REGISTER_COMPLETE_STARBUCKS = "EVENT_GO_TO_REGISTER_COMPLETE_STARBUCKS";
    public final static String EVENT_GO_TO_REGISTER_STARBUCKS = "EVENT_GO_TO_REGISTER_STARBUCKS";
    public final static String EVENT_GO_TO_FORGET_PASSWORD_STARBUCKS = "EVENT_GO_TO_FORGET_PASSWORD_STARBUCKS";
    public final static String EVENT_GO_TO_ADMIN_STARBUCKS = "EVENT_GO_TO_ADMIN_STARBUCKS";
    public final static String EVENT_GO_TO_MOV_ADQ = "EVENT_GO_TO_MOV_ADQ";
    public final static String EVENT_GO_TO_SEND_TICKET = "EVENT_GO_TO_SEND_TICKET";
    private final static String EVENT_GO_CHAT = "EVENT_GO_CHAT";
    public final static String EVENT_GO_VISUALIZER_EDO_CUENTA = "EVENT_GO_VISUALIZER_EDO_CUENTA";
    public final static String EVENT_GO_VISUALIZER_EDO_CUENTA_ERROR = "EVENT_GO_VISUALIZER_EDO_CUENTA_ERROR";

    //public final static String EVENT_GO_TO_FAVORITES = "EVENT_GO_TO_FAVORITES";

    private static final int ACTION_SHARE = 0, ACTION_CANCEL_CHARGE = 1;
    public static final int REQUEST_CHECK_SETTINGS = 91, MY_PERMISSIONS_REQUEST_PHONE = 100;
    private MovTab movTab;
    private boolean onDongleChanged = false;

    @BindView(R.id.toolbar_wallet)
    Toolbar toolbar;

    private Menu menu;
    private ElementView itemOperation;
    private ShareActionProvider mShareActionProvider;
    private List<TiposReembolsoResponse> tiposReembolso;
    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;
    private List<Giros> girosComercio = new ArrayList<>();
    private boolean isMyBussines = false;
    public int tabMonthMov = 0;
    private WalletEmisorRouter router;
    private WalletEmisorInteractor interactor;
    private Envios envio;
    private String acountClabe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_main);
        this.init();
        router = new WalletEmisorRouter(this);
        interactor = new WalletEmisorInteractor(this,this);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            if (getIntent().getExtras() != null) {
                itemOperation = (ElementView) getIntent().getSerializableExtra(ITEM_OPERATION);
                getLoadFragment(itemOperation.getIdOperacion());

            }
            //loadFragment(MovementsGenericFragment.newInstance(), R.id.fragment_container);
        }

    }

    public WalletEmisorRouter getRouter() {
        return router;
    }

    public WalletEmisorInteractor getInteractor(){
        return interactor;
    }

    public void showToolbarShadow() {
        ///android:background="@color/colorAccent"
        toolbar.setBackgroundResource(R.drawable.background_toolbar);
    }

    public void showError(String msj) {
        UI.showErrorSnackBar(this, msj, Snackbar.LENGTH_SHORT);
    }

    private void init() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        //getMenuInflater().inflate(R.menu.menu_wallet, menu);
        if (itemOperation.getIdOperacion() == OPTION_DEPOSITO) {
            getMenuInflater().inflate(R.menu.menu_wallet, menu);
            menu.getItem(ACTION_SHARE).setVisible(true);
        } else if (itemOperation.getIdOperacion() == OPTION_MVIMIENTOS_EMISOR && getCurrentFragment() instanceof DetailsEmisorFragment) {
            getMenuInflater().inflate(R.menu.menu_wallet, menu);
            menu.getItem(ACTION_SHARE).setVisible(true);
        } else if (itemOperation.getIdOperacion() == OPTION_MVIMIENTOS_ADQ && getCurrentFragment() instanceof DetailsAdquirenteFragment) {
            getMenuInflater().inflate(R.menu.menu_mov_det_adq, menu);
            boolean isComerioUyU = false;
            try {
                isComerioUyU = new DatabaseManager().isComercioUyU(RequestHeaders.getIdCuentaAdq());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if (isComerioUyU) {
                menu.findItem(R.id.action_rembolsar).setVisible(false);
            }
            if (!movTab.getItemMov().getEstatus().equals("2")) {
                menu.findItem(R.id.action_rembolsar).setVisible(false);
                menu.findItem(R.id.action_cancelar_cobro).setVisible(false);
            }
            //menu.findItem(R.id.action_reenviar_ticket).setVisible(true);
            //menu.findItem(R.id.action_cancelar_cobro).setVisible(true);
        } else if (getCurrentFragment() instanceof BlockCardFragment) {
            getMenuInflater().inflate(R.menu.menu_wallet, menu);
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getCurrentFragment() instanceof TimeRepaymentFragment) {
                    onEvent(EVENT_GO_CONFIG_REPAYMENT_BACK, null);
                } else {
                    onBackPressed();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getLoadFragment(int idoperation) {
        switch (idoperation) {
            case OPTION_MVIMIENTOS_EMISOR:
                loadFragment(PersonalAccountFragment.newInstance(tabMonthMov), R.id.fragment_container);
                //loadFragment(MovmentsContentFragment.newInstance(), R.id.fragment_container);
                break;

            case OPTION_MVIMIENTOS_ADQ:
            case OPTION_MY_CARD_SALES:
                //loadFragment(OperadoresUYUFragment.newInstance(itemOperation), R.id.fragment_container);
                isMyBussines = false;
                loadFragment(PaymentsFragment.newInstance(tabMonthMov, isMyBussines), R.id.fragment_container);
                break;
            case OPTION_OPERADORES_ADQ:
                loadFragment(OperadoresUYUFragment.newInstance(itemOperation), R.id.fragment_container);
                break;
            case OPTION_VENTAS_ADQ:
                loadFragment(VentasDiariasFragment.newInstance(itemOperation), R.id.fragment_container);
                break;
            case OPTION_MVIMIENTOS_STARBUCKS:
                loadFragment(MovementsSbFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_DEPOSITO:
                //loadFragment(MyVirtualCardAccountFragment.newInstance(), R.id.fragment_container);
                router.onShowMyVirtualCardAccount();
                break;
            case OPTION_ADMON_EMISOR:
                loadFragment(AdministracionFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_PAGO_QR:
                //Intent intent = new Intent(this, ScannVisionActivity.class);
                Intent intent = ScannVisionActivity.createIntent(this,true,
                        getResources().getString(R.string.title_scan));
                intent.putExtra(ScannVisionActivity.QRObject, true);
                this.startActivityForResult(intent, BARCODE_READER_REQUEST_CODE_COMERCE);

                break;
            case OPTION_ADMON_ADQ:
                int permissionCall = ContextCompat.checkSelfPermission(App.getContext(),
                        Manifest.permission.RECORD_AUDIO);
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if (permissionCall == -1) {
                    ValidatePermissions.checkPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO},
                            REQUEST_ID_MULTIPLE_PERMISSIONS);
                    finish();
                } else if (!adapter.isEnabled() && App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                    Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enabler);
                    finish();
                } else {
                    loadFragment(MyDongleFragment.newInstance(App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE)),
                            R.id.fragment_container);
                }
                break;
            case OPTION_ADMON_STARBUCK:
                loadFragment(AdminStarbucksFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_PAYMENT_ADQ:
            case OPTION_CHARGE_WITH_CARD:
                /*if (App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal()
                        && App.getInstance().getPrefs().loadData(BT_PAIR_DEVICE).equals("")) {
                    loadFragment(PairBluetoothFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD);
                    UI.showErrorSnackBar(this, getString(R.string.please_config_dongle), Snackbar.LENGTH_SHORT);
                } else {*/
                loadFragment(GetMountFragment.newInstance(itemOperation.getNombreNegocio()), R.id.fragment_container);
                //}
                break;
            case 7:
                //loadFragment(BusinessDataFragment.newInstance(this), R.id.fragment_container);
                startActivity(BussinesActivity.createIntent(this, itemOperation.getNumeroAgente()));
                setResult(PICK_WALLET_TAB_REQUEST);
                setResult(TabActivity.RESULT_ADQUIRENTE_SUCCESS);
                finish();
                break;
            case OPTION_CONTINUE_DOCS:
                loadFragment(DocumentosFragment.newInstance(), R.id.fragment_container);
                break;
            case 12:
                App.getInstance().getPrefs().saveDataInt(ESTATUS_AGENTE, CRM_PENDIENTE);
                App.getInstance().getPrefs().saveDataInt(ESTATUS_DOCUMENTACION, STATUS_DOCTO_PENDIENTE);
                loadFragment(DocumentosFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_CONFIG_DONGLE:
               /* if (App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                    App.getInstance().getPrefs().saveDataBool(HAS_CONFIG_DONGLE,true);
                    loadFragment(PairBluetoothFragment.newInstance(), R.id.fragment_container);
                } else {*/
                setResult(PICK_WALLET_TAB_REQUEST);
                setResult(TabActivity.RESULT_ADQUIRENTE_SUCCESS);
                finish();
                App.getInstance().getPrefs().saveDataBool(HAS_CONFIG_DONGLE, true);
                //}
                break;
            case OPTION_FIRST_ADQ:
                App.getInstance().getPrefs().saveDataBool(FIST_ADQ_LOGIN, true);
                setResult(PICK_WALLET_TAB_REQUEST);
                setResult(TabActivity.RESULT_ADQUIRENTE_SUCCESS);
                finish();
                break;
            case OPTION_REMBOLSO_FIRST:
                setResult(PICK_WALLET_TAB_REQUEST);
                setResult(TabActivity.RESULT_ADQUIRENTE_SUCCESS);
                finish();
                break;
            case OPTION_RECOMPENSAS:
                loadFragment(RewardsStarbucksFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_SUCURSALES:
                loadFragment(MapStarbucksFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_SETTINGSCARD:
                loadFragment(AdminCardsFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_ADDFAVORITE_PAYMENT:
                loadFragment(FavoritesFragment.newInstance(OPTION_ADDFAVORITE_PAYMENT), R.id.fragment_container);
                break;
            case OPTION_MVIMIENTOS_BUSSINES:
                isMyBussines = true;
                loadFragment(PaymentsFragment.newInstance(tabMonthMov, isMyBussines), R.id.fragment_container);
                break;
            case OPTION_ERROR_ADDRESS_DOCS:
            case OPTION_ERROR_ADDRESS:
                loadFragment(DatosNegocioFragment.newInstance(null), R.id.fragment_container);
                break;
            case OPTION_TUTORIALS:
                loadFragment(TutorialsFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_ESTADOS_CUENTA:
                loadFragment(DescargarEdoCuentaFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_ADD_NEW_FAV_SUCCES:
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                loadFragment(RegisterCompleteFragment.newInstance(ADD_FAV), R.id.fragment_container);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FAVORITES) {
            switch (itemOperation.getIdOperacion()) {
                case OPTION_MVIMIENTOS_EMISOR:
                    loadFragment(PersonalAccountFragment.newInstance(tabMonthMov), R.id.fragment_container);
                    break;
                case OPTION_MVIMIENTOS_ADQ:
                    loadFragment(PaymentsFragment.newInstance(tabMonthMov, isMyBussines), R.id.fragment_container);
                    break;
            }
        } else if (requestCode == BARCODE_READER_REQUEST_CODE_COMERCE) {
            if (resultCode == RESULT_CODE_KEYBOARD) {
                router.onShowWritePlateQR();
            } else if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    try {
                        Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                        JsonElement jelement = new JsonParser().parse(barcode.displayValue);
                        JsonObject jobject = jelement.getAsJsonObject();
                        jobject = jobject.getAsJsonObject("Aux");
                        String plate = jobject.get("Pl").getAsString();

                        interactor.valideteQR(plate);
                    }catch (JsonParseException e){
                        e.printStackTrace();
                        onErrorValidatePlate("QR Invalido");
                    } catch (NullPointerException e){
                        onErrorValidatePlate("QR Invalido");
                    }
                    //interactor.onValidateQr(plate);
                    /*if (barcode.displayValue.contains("reference") &&
                            barcode.displayValue.contains("commerce") && barcode.displayValue.contains("codevisivility")) {
                        MyQrCommerce myQr = new Gson().fromJson(barcode.displayValue, MyQrCommerce.class);
                        Log.d("Ya codigo qr", myQr.getCommerce());
                        Log.d("Ya codigo qr", myQr.getReference());

                        loadFragment(PayQRFragment.newInstance(myQr.getCommerce(), myQr.getReference(), Boolean.parseBoolean(myQr.getCodevisivility())), R.id.fragment_container);
                    } else {
                        UI.showErrorSnackBar(this, getString(R.string.transfer_qr_invalid), Snackbar.LENGTH_SHORT);
                    }*/
                } else {
                    finish();
                }
            }
        } else if (requestCode == DocumentosFragment.REQUEST_TAKE_PHOTO || requestCode == DocumentosFragment.SELECT_FILE_PHOTO
                || requestCode == PAYMENTS_ADQUIRENTE) {
            getCurrentFragment().onActivityResult(requestCode, resultCode, data);
        } else if (data != null && requestCode != BARCODE_READER_REQUEST_CODE_COMERCE) {
            getCurrentFragment().onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == Constants.BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                    if (barcode.displayValue.contains("Pl")) {
                        Qrlectura myQr = new Gson().fromJson(barcode.displayValue, Qrlectura.class);
                        Auxl auxl = myQr.getAux();
                        String plate  = auxl.getPl();

                    /*    cardNumber.setText(myQr.getClabe());
                        receiverName.setText(myQr.getUserName());*/
                    }
                }else{
                    finish();
                }
            }else if (resultCode ==153){

            }

        } else {
            finish();
        }

    }

    public void onErrorValidatePlate(String error) {
        UI.showErrorSnackBar(this,error,Snackbar.LENGTH_SHORT);
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case EVENT_PAYMENT:
                loadFragment(GetMountFragment.newInstance(itemOperation.getNombreNegocio()), R.id.fragment_container, Direction.BACK);
                break;
            case EVENT_GO_INSERT_DONGLE:
                loadFragment(InsertDongleFragment.newInstance(App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE),
                        QPOSService.TransactionType.PAYMENT.ordinal()), R.id.fragment_container, Direction.FORDWARD, false);
                break;
            case EVENT_GO_INSERT_DONGLE_CANCELATION:
                loadFragment(InsertDongleFragment.newInstance(true, (DataMovimientoAdq) data,
                        App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE), QPOSService.TransactionType.PAYMENT.ordinal()),
                        R.id.fragment_container, Direction.FORDWARD, true);
                //menu.getItem(ACTION_CANCEL_CHARGE).setVisible(false);
                break;
            case EVENT_GO_TRANSACTION_RESULT:
                loadFragment(TransactionResultFragment.newInstance(TransactionAdqData.getCurrentTransaction().getPageResult()),
                        R.id.fragment_container, Direction.FORDWARD, false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
            case AdqActivity.EVENT_GO_REMOVE_CARD:
                loadFragment(RemoveCardFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD, false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
            case EVENT_GO_GET_SIGNATURE:
                loadFragment(GetSignatureFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD, false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
            case EVENT_GO_DETAIL_TRANSACTION:
                loadFragment(DetailTransactionFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD, false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
            case EVENT_GO_MAINTAB:
                finish();
                break;
            case EVENT_GO_LOGIN_FRAGMENT:
                setResult(Activity.RESULT_OK);
                finish();
                break;
            case EVENT_RETRY_PAYMENT:
                loadFragment(InsertDongleFragment.newInstance(App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE),
                        QPOSService.TransactionType.PAYMENT.ordinal()), R.id.fragment_container, Direction.FORDWARD, false);
                break;
            case EVENT_GO_NIP_CHANGE:
                //loadFragment(MyChangeNip.newInstance(), R.id.fragment_container, Direction.FORDWARD, true);
                router.onShowMyChangeNip();
                break;
            case EVENT_GO_CARD_REPORT:
                loadFragment(MyCardReportaTarjetaFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD, true);
                break;
            case EVENT_GO_CONFIG_REPAYMENT:
                loadFragment(TimeRepaymentFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD, false);
                break;
            case EVENT_GO_CONFIG_REPAYMENT_BACK:
                loadFragment(MyDongleFragment.newInstance(App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE)),
                        R.id.fragment_container, Direction.BACK, false);
                break;
            case EVENT_GO_DETAIL_EMISOR:
                loadFragment(DetailsEmisorFragment.newInstance((MovimientosResponse) data), R.id.fragment_container, Direction.FORDWARD, true);
                //menu.getItem(ACTION_SHARE).setVisible(true);
                break;
            case EVENT_GO_DETAIL_ADQ:
                this.movTab = (MovTab) data;
                loadFragment(DetailsAdquirenteFragment.newInstance(this.movTab), R.id.fragment_container, Direction.FORDWARD);
                //if (movTab.getItemMov().getEstatus().equals(EstatusMovimientoAdquirente.POR_REEMBOLSAR.getId()))
                //  menu.getItem(ACTION_CANCEL_CHARGE).setVisible(true);
                break;
            case EVENT_GO_TO_FINALIZE_SUCCESS:
                setResult(RESULT_CANCEL_OK);
                this.finish();
                break;
            case EVENT_GO_TO_FINALIZE_ERROR:
                setResult(-1);
                this.finish();
                break;
            case EVENT_GO_TO_LOGIN_STARBUCKS:
                loadFragment(LoginStarbucksFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD);
                break;
            case EVENT_GO_TO_REGISTER_COMPLETE_STARBUCKS:
                loadFragment(RegisterCompleteStarbucksFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD);
                break;
            case EVENT_GO_TO_REGISTER_STARBUCKS:
                loadFragment(RegisterStarbucksFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD);
                break;
            case EVENT_GO_TO_FORGET_PASSWORD_STARBUCKS:
                loadFragment(FrogetPasswordStarbucks.newInstance(), R.id.fragment_container, Direction.FORDWARD);
                break;
            case EVENT_GO_TO_ADMIN_STARBUCKS:
                loadFragment(AdminStarbucksFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
            case EVENT_GO_SELECT_DONGLE:
                loadFragment(SelectDongleFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD, false);
                break;
            case EVENT_GO_CONFIG_DONGLE:
                if (App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                    loadFragment(PairBluetoothFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD);
                } else {
                    finish();
                }
                break;
            case EVENT_GO_TO_MOV_ADQ:
                this.movTab = (MovTab) data;
                loadFragment(PaymentsFragment.newInstance(movTab.getCurrentTab(), isMyBussines), R.id.fragment_container);
                break;
            case EVENT_GO_TO_SEND_TICKET:
                this.movTab = (MovTab) data;
                loadFragment(SendTicketFragment.newInstance(movTab), R.id.fragment_container);
                break;
            case EVENT_OPERADOR_DETALLE:
                loadFragment(DetalleOperadorFragment.newInstance((Operadores) data), R.id.fragment_container);
                break;
            case EVENT_DETALLE_PROMO:
                Intent intent = new Intent(this, ScannVisionActivity.class);
                intent.putExtra(ScannVisionActivity.QRObject, true);
                this.startActivity(intent);
                break;
            case SUCCES_CHANGE_STATUS_OPERADOR:
                loadFragment(OperadorSuccesFragment.newInstance((int) data), R.id.fragment_container);
                break;
            case EVENT_GO_BUSSINES_COMPLETE:
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                loadFragment(RegisterCompleteFragment.newInstance(ADQ_REVISION), R.id.fragment_container);
                break;
            case EVENT_GO_BUSSINES_DOCUMENTS:
                if (itemOperation.getIdOperacion() == OPTION_ERROR_ADDRESS_DOCS) {
                    App.getInstance().getPrefs().saveDataInt(ESTATUS_AGENTE, CRM_PENDIENTE);
                    App.getInstance().getPrefs().saveDataInt(ESTATUS_DOCUMENTACION, STATUS_DOCTO_PENDIENTE);
                    loadFragment(DocumentosFragment.newInstance(), R.id.fragment_container);
                } else {

                    RegisterAgent.getInstance().setUseSameAddress(true);
                    String folio = "";
                    try {
                        folio = new DatabaseManager().getFolioAgente(itemOperation.getIdComercio());
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    new DatabaseManager().setIdStatusAgente(folio);
                    setResult(PICK_WALLET_TAB_REQUEST);
                    setResult(TabActivity.RESULT_ADQUIRENTE_SUCCESS);
                    finish();
                }
                break;
            case EVENT_SET_BUSINESS_LIST:
                RegisterAgent.getInstance().setOnErrorAddress(true);
                String folio = "";
                try {
                    folio = new DatabaseManager().getFolioAgente(itemOperation.getIdComercio());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                //loadFragment(DatosNegocioFragment.newInstance(null), R.id.fragment_container);
                loadFragment(DomicilioNegocioFragment.newInstance(null, null, folio), R.id.fragment_container);
                break;
            case EVENT_GO_CHAT:
                loadFragment(ChatFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD);
                break;
            case EVENT_DOC_CHECK:
                App.getInstance().getPrefs().saveDataInt(ESTATUS_DOCUMENTACION, STATUS_DOCTO_PENDIENTE);
                App.getInstance().getPrefs().saveDataBool(ES_AGENTE, true);
                setResult(TabActivity.RESULT_ADQUIRENTE_SUCCESS);
                finish();
                break;
            case EVENT_GO_VISUALIZER_EDO_CUENTA:
                String info[] = data.toString().split("_");
                loadFragment(EdoCuentaFragment.newInstance(Integer.valueOf(info[0]), Integer.valueOf(info[1]),
                        info[2]), R.id.fragment_container, Direction.FORDWARD);
                break;
            case EVENT_GO_VISUALIZER_EDO_CUENTA_ERROR:
                loadFragment(DescargarEdoCuentaFragment.newInstance(), R.id.fragment_container, Direction.BACK);
                UI.showErrorSnackBar(this, "No se encontró el estado de cuenta", Snackbar.LENGTH_SHORT);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            getCurrentFragment().onRequestPermissionsResult(requestCode, permissions, grantResults);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getCurrentFragment();
        if (fragment instanceof LoginStarbucksFragment) {
            loadFragment(AdminCardsFragment.newInstance(), R.id.fragment_container, Direction.BACK);
        } else if (fragment instanceof SendTicketFragment) {
            loadFragment(DetailsAdquirenteFragment.newInstance(movTab), R.id.fragment_container, Direction.BACK);
            UI.hideKeyBoard(this);
        } else if (fragment instanceof DetailsAdquirenteFragment) {
            loadFragment(PaymentsFragment.newInstance(tabMonthMov, isMyBussines), R.id.fragment_container);
        } else if (fragment instanceof DetalleOperadorFragment) {
            loadFragment(OperadoresUYUFragment.newInstance(itemOperation), R.id.fragment_container, Direction.BACK);
        } else if (fragment instanceof SelectDongleFragment || onDongleChanged) {
            setResult(RESULT_CODE_SELECT_DONGLE);
            super.onBackPressed();
        } else if (fragment instanceof PairBluetoothFragment) {
            onDongleChanged = true;
            loadFragment(MyDongleFragment.newInstance(App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE)),
                    R.id.fragment_container, Direction.BACK);
        } else if (fragment instanceof DocumentosFragment) {
            setResult(TabActivity.RESULT_ADQUIRENTE_SUCCESS);
            super.onBackPressed();
        } else if (fragment instanceof TimeRepaymentFragment) {
            setResult(PICK_WALLET_TAB_REQUEST);
            setResult(TabActivity.RESULT_ADQUIRENTE_SUCCESS);
            finish();
            super.onBackPressed();
        } else if (fragment instanceof MyDongleFragment) {
            setResult(PICK_WALLET_TAB_REQUEST);
            setResult(TabActivity.RESULT_ADQUIRENTE_SUCCESS);
            finish();
            super.onBackPressed();
        } else if (fragment instanceof EdoCuentaFragment) {
            loadFragment(DescargarEdoCuentaFragment.newInstance(), R.id.fragment_container, Direction.BACK);
        } else {
            setResult(PICK_WALLET_TAB_REQUEST);
            super.onBackPressed();
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

    @Override
    public void showLoad() {
        this.showLoader("");
    }

    @Override
    public void hideLoad() {
        this.hideLoader();
    }

    @Override
    public void onSouccesValidateCard() {
        this.router.onShowGeneratePIN();
    }

    @Override
    public void onErrorRequest(String msj) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(findViewById(R.id.fragment_container).getWindowToken(), 0);
        UI.showErrorSnackBar(this,msj,Snackbar.LENGTH_SHORT);
        //this.router.onShowGeneratePIN();
    }

    @Override
    public void onSouccesDataQR(QrValidateResponse qRresponse) {
        envio = new Envios();
        envio.setTipoEnvio(TransferType.CLABE);
        acountClabe = qRresponse.getData().getAccount();
        envio.setReferencia(acountClabe);
        envio.setMonto(0D);
        envio.setConcepto(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
        NotificationSingleton.getInstance().getRequest().setConcept(App.getContext().getResources().getString(R.string.trans_yg_envio_txt));
        envio.setReferenciaNumerica("123456");
        interactor.getTitular(acountClabe);

    }

    @Override
    public void onSouccesGetTitular(ConsultarTitularCuentaResponse dataTitular) {
        envio.setNombreDestinatario(dataTitular.getData().getNombre().concat(" ")
                .concat(dataTitular.getData().getPrimerApellido()).concat(" ")
                .concat(dataTitular.getData().getSegundoApellido()));
        interactor.getDataBank(acountClabe, "clave");
    }

    @Override
    public void onSouccessgetgetDataBank(ObtenerBancoBinResponse data) {
        Comercio comercio = new Comercio();
        comercio.setColorMarca("#00b6ff");
        comercio.setIdComercio(Integer.parseInt(data.getData().getIdComercioAfectado()));
        envio.setComercio(comercio);
        router.onShowEnvioFormulario(envio);
    }

    @Override
    protected void onStop() {
        super.onStop();
        UiKeyBoard.hideKeyboard(findViewById(R.id.fragment_container));
    }

    @Override
    public void generatecodehue(Fragment fm) {
        if (fm instanceof MyVirtualCardAccountFragment)
            ((MyVirtualCardAccountFragment) fm).loadOtpHuella();
    }
    
}
