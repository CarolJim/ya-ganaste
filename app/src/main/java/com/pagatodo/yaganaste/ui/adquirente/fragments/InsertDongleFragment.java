package com.pagatodo.yaganaste.ui.adquirente.fragments;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.dspread.xpos.EmvAppTag;
import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.AccountDepositData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IAdqTransactionRegisterView;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.adquirente.presenters.AdqPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.USBClass;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ly.count.android.sdk.Countly;

import static android.content.Context.AUDIO_SERVICE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_GET_BALANCE_RESULT;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_TRANSACTION_RESULT;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui.adquirente.utils.UtilsAdquirente.getImplicitData;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;
import static com.pagatodo.yaganaste.utils.Constants.TIPO_TRANSACCION_CHIP;
import static com.pagatodo.yaganaste.utils.Recursos.BT_PAIR_DEVICE;
import static com.pagatodo.yaganaste.utils.Recursos.CONFIG_READER_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CONFIG_READER_OK_ERROR;
import static com.pagatodo.yaganaste.utils.Recursos.CONNECTION_TYPE;
import static com.pagatodo.yaganaste.utils.Recursos.EMV_DETECTED;
import static com.pagatodo.yaganaste.utils.Recursos.ENCENDIDO;
import static com.pagatodo.yaganaste.utils.Recursos.ERROR;
import static com.pagatodo.yaganaste.utils.Recursos.ERROR_LECTOR;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_BALANCE_UYU;
import static com.pagatodo.yaganaste.utils.Recursos.ID_CUENTA;
import static com.pagatodo.yaganaste.utils.Recursos.KSN_LECTOR;
import static com.pagatodo.yaganaste.utils.Recursos.LECTURA_OK;
import static com.pagatodo.yaganaste.utils.Recursos.LEYENDO;
import static com.pagatodo.yaganaste.utils.Recursos.MSJ;
import static com.pagatodo.yaganaste.utils.Recursos.READ_BATTERY_LEVEL;
import static com.pagatodo.yaganaste.utils.Recursos.READ_KSN;
import static com.pagatodo.yaganaste.utils.Recursos.REQUEST_AMOUNT;
import static com.pagatodo.yaganaste.utils.Recursos.REQUEST_FINAL_CONFIRM;
import static com.pagatodo.yaganaste.utils.Recursos.REQUEST_IS_SERVER_CONNECTED;
import static com.pagatodo.yaganaste.utils.Recursos.REQUEST_PIN;
import static com.pagatodo.yaganaste.utils.Recursos.REQUEST_TIME;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;
import static com.pagatodo.yaganaste.utils.Recursos.SW_ERROR;
import static com.pagatodo.yaganaste.utils.Recursos.SW_TIMEOUT;
import static com.pagatodo.yaganaste.utils.Recursos.TIPO_AGENTE;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class InsertDongleFragment extends GenericFragment implements View.OnClickListener,
        IAdqTransactionRegisterView, IPreferUserGeneric {

    private static final String TAG = InsertDongleFragment.class.getSimpleName();
    private static String DATA_KEY = "is_cancelation_data", DATA_MOVEMENTS = "data_movimiento_adq",
            MODE_COMMUNICACTION = "mode_communication", TYPE_TRANSACTION = "type_transaction";

    @BindView(R.id.imgInsertDongle)
    LottieAnimationView imgInsertDongle;
    @BindView(R.id.imgInsertCard)
    LottieAnimationView imgInsertCard;
    @BindView(R.id.searchBluetoothDevice)
    LottieAnimationView imgSearchBt;
    @BindView(R.id.imgInsertCardBt)
    LottieAnimationView imgInsertCardBt;
    @BindView(R.id.imgInsertNip)
    LottieAnimationView imgInsertNip;
    @BindView(R.id.tv_txt_lector)
    StyleTextView tv_lector;

    DataMovimientoAdq dataMovimientoAdq;
    private View rootview;
    private AudioManager audioManager;
    private Handler handlerSwipe;
    private IntentFilter broadcastEMVSwipe;
    private int currentVolumenDevice, maxVolumenDevice, communicationMode;
    private QPOSService.TransactionType transactionType;
    private AdqPresenter adqPresenter;
    private boolean isWaitingCard = false, isCancelation = false, isReaderConected = false, signWithPin = false;
    private static boolean banderaCacelachevron = false;
    private Preferencias prefs;
    private UsbDevice usbDevice;

    public InsertDongleFragment() {
    }

    public static InsertDongleFragment newInstance(boolean isCancelation, DataMovimientoAdq dataMovimientoAdq,
                                                   int communicationMode, int idTypeTransaction) {
        InsertDongleFragment fragment = new InsertDongleFragment();
        Bundle args = new Bundle();
        args.putBoolean(DATA_KEY, isCancelation);
        args.putSerializable(DATA_MOVEMENTS, dataMovimientoAdq);
        args.putInt(MODE_COMMUNICACTION, communicationMode);
        args.putInt(TYPE_TRANSACTION, idTypeTransaction);
        fragment.setArguments(args);
        banderaCacelachevron = true;
        return fragment;
    }

    public static InsertDongleFragment newInstance(int communicationMode, int idTypeTransaction) {
        InsertDongleFragment fragment = new InsertDongleFragment();
        Bundle args = new Bundle();
        args.putBoolean(DATA_KEY, false);
        args.putSerializable(DATA_MOVEMENTS, null);
        args.putInt(MODE_COMMUNICACTION, communicationMode);
        args.putInt(TYPE_TRANSACTION, idTypeTransaction);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCancelation = getArguments().getBoolean(DATA_KEY);
        dataMovimientoAdq = getArguments().getSerializable(DATA_MOVEMENTS) != null ? (DataMovimientoAdq) getArguments().getSerializable(DATA_MOVEMENTS) : null;
        communicationMode = getArguments().getInt(MODE_COMMUNICACTION);
        audioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
        currentVolumenDevice = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        broadcastEMVSwipe = new IntentFilter(Recursos.IPOS_READER_STATES);
        handlerSwipe = new Handler();
        if (communicationMode == QPOSService.CommunicationMode.AUDIO.ordinal()) {
            IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
            getActivity().registerReceiver(headPhonesReceiver, filter);
            App.getInstance().initEMVListener(QPOSService.CommunicationMode.AUDIO);
        } else if (communicationMode == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
            App.getInstance().initEMVListener(QPOSService.CommunicationMode.BLUETOOTH);
            App.getInstance().pos.clearBluetoothBuffer();
            App.getInstance().pos.scanQPos2Mode(App.getContext(), 30);
            getActivity().registerReceiver(emvSwipeBroadcastReceiver, broadcastEMVSwipe);
            //pos.selectEmvApp(position);
        } else if (communicationMode == QPOSService.CommunicationMode.USB_OTG_CDC_ACM.ordinal()) {
            USBClass usb = new USBClass();
            ArrayList<String> deviceList = usb.GetUSBDevices(App.getContext());
            /*final CharSequence[] items = deviceList.toArray(new CharSequence[deviceList.size()]);
            String selectedDevice = (String) items[0];
            usbDevice = USBClass.getMdevices().get(selectedDevice);
            App.getInstance().initEMVListener(QPOSService.CommunicationMode.USB_OTG_CDC_ACM);
            App.getInstance().pos.openUsb(usbDevice);*/
        }
        /* Obtener tipo de transaccion por realizar */
        int idTypeTransaction = getArguments().getInt(TYPE_TRANSACTION);
        if (idTypeTransaction == QPOSService.TransactionType.PAYMENT.ordinal()) {
            transactionType = QPOSService.TransactionType.PAYMENT;
        } else if (idTypeTransaction == QPOSService.TransactionType.INQUIRY.ordinal()) {
            transactionType = QPOSService.TransactionType.INQUIRY;
        }
        prefs = App.getInstance().getPrefs();
        adqPresenter = new AdqPresenter(this);
        adqPresenter.setIView(this);
    }

    @Override
    public void verifyDongle(String ksn) {
        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
            Log.i("IposListener: ", "=====>>  READ_KSN  " + ksn);
        }
        if (ksn.length() > 10) {
            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                Log.i("IposListener: ", "=====>>  saveData");
            }
            checkDongleStatus(ksn);
        } else {
            hideLoader();
            showSimpleDialogError(getString(R.string.inserta_lector_valido),
                    new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {
                            unregisterReceiverDongle();
                            showInsertDongle();
                        }

                        @Override
                        public void actionCancel(Object... params) {

                        }
                    });
            //UI.showToastShort(getString(R.string.inserta_lector_valido), getActivity());

        }
    }

    private void checkDongleStatus(String serial) {
        adqPresenter.validateDongle(serial);
    }

    private TransaccionEMVDepositRequest buildEMVRequest(TransaccionEMVDepositRequest request) {
        TransactionAdqData currentTransaction = TransactionAdqData.getCurrentTransaction();
        SingletonUser currentUser = SingletonUser.getInstance();
//        request.setAccountDepositData(getCurrentDatesAccountDepositData(currentTransaction.getDescription()));
        request.setAccountDepositData(new AccountDepositData(App.getInstance().getPrefs().loadData(ID_CUENTA),
                currentTransaction.getDescription()));
        request.setAmount(currentTransaction.getAmount());
        request.setImplicitData(getImplicitData());
        request.setNoSerie(prefs.loadData(KSN_LECTOR));
        request.setNoTicket(String.valueOf(System.currentTimeMillis() / 1000L));
        request.setTipoCliente(String.valueOf(App.getInstance().getPrefs().loadDataInt(TIPO_AGENTE)));
        request.setTransactionDateTime(Utils.getTimeStamp());
        request.setDigitPIN(signWithPin);
        return request;
    }

    private void getKSN() {
        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
            Log.i("IposListener: ", "=====>>    getKsn");
        }
        App.getInstance().pos.getQposId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_insert_dongle, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        showInsertDongle();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (banderaCacelachevron = true) {
            setHasOptionsMenu(false);
        }
        if (communicationMode == QPOSService.CommunicationMode.AUDIO.ordinal()) {
            App.getInstance().pos.openAudio();
        }
        maxVolumenDevice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolumenDevice, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (App.getInstance().pos != null)
            App.getInstance().pos.stopScanQPos2Mode();
    }

    @Override
    public void onDestroy() {
        unregisterReceiverDongle();
        unregisterReceiverHeadPhone();
        if (App.getInstance().pos != null) {
            App.getInstance().pos.closeAudio();
            if (communicationMode == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                App.getInstance().pos.disconnectBT();
            } else if (communicationMode == QPOSService.CommunicationMode.USB_OTG_CDC_ACM.ordinal()) {
                App.getInstance().pos.closeUsb();
            }
        }
        super.onDestroy();
    }

    public void unregisterReceiverDongle() {
        try {
            getActivity().unregisterReceiver(emvSwipeBroadcastReceiver); // Desregistramos receiver
        } catch (IllegalArgumentException ex) {
            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                Log.e(TAG, "emvSwipeBroadcastReceiver no registrado. Ex- " + ex.toString());
            }
        }
    }

    private void unregisterReceiverHeadPhone() {
        try {
            getActivity().unregisterReceiver(headPhonesReceiver);
        } catch (IllegalArgumentException ex) {
            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                Log.e(TAG, "emvSwipeBroadcastReceiver no registrado. Ex- " + ex.toString());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            default:
                break;
        }
    }

    @Override
    public void showInsertDongle() {
        try {
            if (communicationMode == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                imgSearchBt.setVisibility(VISIBLE);
                imgSearchBt.playAnimation();
                imgInsertCardBt.setVisibility(View.INVISIBLE);
                imgInsertCardBt.pauseAnimation();
                imgInsertNip.setVisibility(View.INVISIBLE);
                imgInsertNip.pauseAnimation();
                tv_lector.setText(getString(R.string.enciende_el_lector_bt_para_continuar));
            } else {
                imgInsertDongle.setVisibility(VISIBLE);
                imgInsertDongle.playAnimation();
                imgInsertCard.setVisibility(View.INVISIBLE);
                imgInsertCard.pauseAnimation();
                tv_lector.setText(getString(R.string.inserta_el_lector_para_ncontinuar));
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void showInsertCard() {
        if (communicationMode == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
            imgSearchBt.setVisibility(View.INVISIBLE);
            imgSearchBt.pauseAnimation();
            imgInsertCardBt.setVisibility(VISIBLE);
            imgInsertCardBt.playAnimation();
            imgInsertNip.setVisibility(View.INVISIBLE);
            imgInsertNip.pauseAnimation();
        } else {
            imgInsertDongle.setVisibility(View.INVISIBLE);
            imgInsertDongle.pauseAnimation();
            imgInsertCard.setVisibility(VISIBLE);
            imgInsertCard.playAnimation();
        }
        String message;

        if (isCancelation) {
            if (dataMovimientoAdq.getTipoTransaccion().equals(TIPO_TRANSACCION_CHIP)) {
                message = getString(R.string.text_insert_cancelation);
                //imgInsertCard.setImageResource(R.mipmap.dongle_insert_card_cancelation);
            } else {
                // showError("Mensaje de prueba dongle_swipe_card");
                message = getString(R.string.text_slide_cancelation);
                //imgInsertCard.setImageResource(R.mipmap.dongle_swipe_card);

            }
        } else {
            // message = getString(R.string.text_slide_or_insert);
            message = getString(R.string.text_insert_or_slide);

        }

        tv_lector.setText(message);
    }

    @Override
    public void showInsertPin() {
        if (communicationMode == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
            imgInsertNip.setVisibility(View.VISIBLE);
            imgInsertNip.playAnimation();
            imgSearchBt.setVisibility(View.INVISIBLE);
            imgSearchBt.pauseAnimation();
            imgInsertCardBt.setVisibility(View.INVISIBLE);
            imgInsertCardBt.pauseAnimation();
        } else {
            imgInsertDongle.setVisibility(View.INVISIBLE);
            imgInsertDongle.pauseAnimation();
            imgInsertCard.setVisibility(VISIBLE);
            imgInsertCard.playAnimation();
        }
        tv_lector.setText(getString(R.string.please_insert_pin));
    }

    @Override
    public void dongleValidated() {
        hideLoader();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (communicationMode == QPOSService.CommunicationMode.AUDIO.ordinal()) {
                    showInsertCard();
                    initListenerDongle();//Lectura de Tarjeta
                    isWaitingCard = true;
                } else {
                    showLoader(getString(R.string.configurate_device));
                    configurePos();
                }
            }
        }, DELAY_MESSAGE_PROGRESS);
    }

    @Override
    public void transactionResult(final String message, String tlv) {
        /* Proceso para validar si se necesita realizar Reverso o no */
        if (communicationMode == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
            App.getInstance().pos.sendOnlineProcessResult("8A023030" + (tlv != null ? Utils.translateTlv(tlv) : ""));
        }
        new Handler().postDelayed(() -> {
            hideLoader();
            if (transactionType == QPOSService.TransactionType.PAYMENT) {
                nextScreen(EVENT_GO_TRANSACTION_RESULT, message);
            } else if (transactionType == QPOSService.TransactionType.INQUIRY) {

                if (!BuildConfig.DEBUG) {
                    Map<String, String> segmentation = new HashMap<>();
                    segmentation.put(CONNECTION_TYPE, Utils.getTypeConnection());
                    Countly.sharedInstance().endEvent(EVENT_BALANCE_UYU, segmentation, 1, 0);
                }
                nextScreen(EVENT_GO_GET_BALANCE_RESULT, message);
            }
        }, DELAY_MESSAGE_PROGRESS);
    }

    @Override
    public void showSimpleDialogError(String message, DialogDoubleActions actions) {
        UI.createSimpleCustomDialogNoCancel(getString(R.string.title_error), message,
                getFragmentManager(), actions);
    }

    @Override
    public void onErrorConsultSaldo(String message) {
        if (communicationMode == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
            App.getInstance().pos.sendOnlineProcessResult("8A023030");
        }
        DialogDoubleActions doubleActions = new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                getActivity().finish();
            }

            @Override
            public void actionCancel(Object... params) {

            }
        };
        UI.createSimpleCustomDialog(getString(R.string.title_error), message, getFragmentManager(), doubleActions, true, false);
    }


    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void showLoader(String message) {
        //progressLayout.setTextMessage(message);
        //progressLayout.setVisibility(VISIBLE);
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        //progressLayout.setVisibility(GONE);
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void showError(Object error) {
        //UI.showToast(error.toString(), getActivity());
        DialogDoubleActions doubleActions = new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                if (error.toString().equals(getString(R.string.no_internet_access))) {
                    getActivity().finish();
                }
            }

            @Override
            public void actionCancel(Object... params) {

            }
        };
        UI.createSimpleCustomDialog(getString(R.string.title_error), error.toString(), getFragmentManager(), doubleActions, true, true);
    }

    private void initListenerDongle() {
        if (communicationMode == QPOSService.CommunicationMode.AUDIO.ordinal()) {
            App.getInstance().pos.doTradeNoPinpad(30);
        } else {
            App.getInstance().pos.setFormatId("0000");
            App.getInstance().pos.doCheckCard(30);
        }
    }

    private void configurePos() {
        ArrayList<String> configuration = new ArrayList<>();
        configuration.add(EmvAppTag.ICS + "F4F0F0FAAFFE8000");
        configuration.add(EmvAppTag.Terminal_type + "22");
        configuration.add(EmvAppTag.Terminal_Capabilities + "60B8C8");
        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
            Log.i("IposListener:", "Lista de configuracion: " + configuration.toString());
        }
        App.getInstance().pos.updateEmvAPP(QPOSService.EMVDataOperation.update, configuration);
    }

    /* Broadcast para esperar entrada de audio */
    private BroadcastReceiver headPhonesReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        isReaderConected = false;
                        //validatingDng = false; // Cancelar Validacion
                        unregisterReceiverDongle();
                        hideLoader();
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                        showInsertDongle();
                        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                            Log.i("IposListener: ", "isReaderConected  false");
                        }
                        break;
                    case 1:
                        isReaderConected = true;
                        //checkDongleStatus("1234");
                        getActivity().registerReceiver(emvSwipeBroadcastReceiver, broadcastEMVSwipe);
                        maxVolumenDevice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolumenDevice, 0);
                        //validatingDng = false; // Cancelar Validacion
                        //insertCard();
                        handlerSwipe.postDelayed(starReaderEmvSwipe, 500);
                        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                            Log.i("IposListener: ", "isReaderConected  true");
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    };

    private Runnable starReaderEmvSwipe = new Runnable() {
        @Override
        public void run() {
            if (isReaderConected) {
                if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                    Log.i("IposListener: ", "=====>>   starReaderEmvSwipe ");
                }
                App.getInstance().pos.openAudio();
                getKSN();
                showLoader(getResources().getString(R.string.validatelector));
            }
        }
    };
    private BroadcastReceiver emvSwipeBroadcastReceiver = new BroadcastReceiver() {
        @SuppressLint("SimpleDateFormat")
        @Override
        public void onReceive(Context context, Intent intent) {
            int mensaje = intent.getIntExtra(MSJ, -1);
            String error = intent.getStringExtra(ERROR);

            switch (mensaje) {
                case LECTURA_OK:
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>  LECTURA OK");
                    }
                    if (isWaitingCard) {
                        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                            Log.i("IposListener: ", "=====>>   LECTURA_OK ");
                        }
                        TransaccionEMVDepositRequest requestTransaction = (TransaccionEMVDepositRequest) intent.getSerializableExtra(Recursos.TRANSACTION);
                        if (transactionType == QPOSService.TransactionType.PAYMENT) {
                            if (isCancelation) {
                                adqPresenter.initCancelation(buildEMVRequest(requestTransaction), dataMovimientoAdq);
                            } else {
                                adqPresenter.initTransaction(buildEMVRequest(requestTransaction), signWithPin);
                            }
                        } else if (transactionType == QPOSService.TransactionType.INQUIRY) {
                            TransactionAdqData.getCurrentTransaction().setAmount("0");
                            if (!BuildConfig.DEBUG) {
                                Countly.sharedInstance().startEvent(EVENT_BALANCE_UYU);
                            }
                            adqPresenter.initConsultBalance(buildEMVRequest(requestTransaction));
                        }
                    }
                    break;
                case READ_KSN:
                    TransaccionEMVDepositRequest transactionKsn = (TransaccionEMVDepositRequest) intent.getSerializableExtra(Recursos.TRANSACTION);
                    verifyDongle(transactionKsn.getNoSerie());
                    break;
                case READ_BATTERY_LEVEL:
                    int batteryLevel = intent.getIntExtra(Recursos.BATTERY_LEVEL, 0);
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>    batteryLevel " + batteryLevel);
                    }
                    App.getInstance().pos.getQposId();
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>    Obteniendo ksn ");
                    }
                    break;
                case ERROR_LECTOR:
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>    ERROR_LECTOR");
                    }
                    hideLoader();
                    if (communicationMode == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                        App.getInstance().pos.disconnectBT();
                    }
                    //closeProgress();
                    break;
                case LEYENDO:
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>    LEYENDO");
                    }
                    App.getInstance().pos.doEmvApp(QPOSService.EmvOption.START);
                    break;
                case REQUEST_AMOUNT:
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>    REQUEST_AMOUNT");
                    }
                    String amountCard = TransactionAdqData.getCurrentTransaction().getAmount().replace(".", "");
                    if (isCancelation) {
                        amountCard = dataMovimientoAdq.getMonto().replace(".", "");
                    }
                    App.getInstance().pos.setPosDisplayAmountFlag(true);
                    App.getInstance().pos.setAmount(amountCard, "", "484", transactionType);
                    break;
                case REQUEST_TIME:
                    String terminalTime = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                    App.getInstance().pos.sendTime(terminalTime);
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>    REQUEST_TIME");
                    }
                    break;
                case REQUEST_IS_SERVER_CONNECTED:
                    App.getInstance().pos.isServerConnected(true);
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>    REQUEST_IS_SERVER_CONNECTED");
                    }
                    break;
                case REQUEST_FINAL_CONFIRM:
                    App.getInstance().pos.finalConfirm(true);
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>    REQUEST_FINAL_CONFIRM");
                    }
                    break;
                case REQUEST_PIN:
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>    REQUEST_PIN");
                    }
                    if (communicationMode == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                        signWithPin = true;
                        showInsertPin();
                    }
                    break;
                case SW_TIMEOUT:
                    //initListenerDongle();
                    hideLoader();
                    showSimpleDialogError(getString(R.string.vuelve_conectar_lector),
                            new DialogDoubleActions() {
                                @Override
                                public void actionConfirm(Object... params) {
                                    showInsertDongle();
                                    if (communicationMode == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                                        App.getInstance().pos.disconnectBT();
                                        App.getInstance().pos.clearBluetoothBuffer();
                                        App.getInstance().pos.scanQPos2Mode(App.getContext(), 30);
                                    }
                                }

                                @Override
                                public void actionCancel(Object... params) {

                                }
                            });
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>    SW_TIMEOUT");
                    }
                    break;
                case SW_ERROR:
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>    SW_ERROR");
                    }
                    hideLoader();
                    if (!isWaitingCard)
                        unregisterReceiverDongle();
                    else {
                        showSimpleDialogError(error, new DialogDoubleActions() {
                            @Override
                            public void actionConfirm(Object... params) {
                                showInsertDongle();
                                if (communicationMode == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                                    App.getInstance().pos.disconnectBT();
                                    App.getInstance().pos.clearBluetoothBuffer();
                                    App.getInstance().pos.scanQPos2Mode(App.getContext(), 30);
                                }
                            }

                            @Override
                            public void actionCancel(Object... params) {

                            }
                        });
                        //Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case ENCENDIDO:
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>    ENCENDIDO");
                    }
                    if (communicationMode == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                        getKSN();
                    }
                    break;
                case EMV_DETECTED:
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "======>> Bluetooth Device");
                    }
                    List<BluetoothDevice> devicesBT = App.getInstance().pos.getDeviceList();
                    for (BluetoothDevice device : devicesBT) {
                        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                            Log.i("IposListener: ", "======>> Bluetooth Address: " + device.getName() + " " + device.getAddress());
                        }
                        if (device.getAddress().equals(prefs.loadData(BT_PAIR_DEVICE))) {
                            App.getInstance().pos.stopScanQPos2Mode();
                            App.getInstance().pos.connectBluetoothDevice(true, 15, device.getAddress());
                            break;
                        }
                    }
                    break;
                case CONFIG_READER_OK:
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>    Configuration Success");
                    }
                    hideLoader();
                    showInsertCard();
                    initListenerDongle();//Lectura de Tarjeta
                    isWaitingCard = true;
                    break;
                case CONFIG_READER_OK_ERROR:
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>    Configuration Error");
                    }
                    hideLoader();
                    showSimpleDialogError(getString(R.string.vuelve_configurar_lector),
                            new DialogDoubleActions() {
                                @Override
                                public void actionConfirm(Object... params) {
                                    configurePos();
                                }

                                @Override
                                public void actionCancel(Object... params) {
                                    getActivity().finish();
                                }
                            });
                    break;
                default:
                    if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.i("IposListener: ", "=====>>    default");
                    }
                    hideLoader();
                    break;
            }
        }
    };
}

