package com.pagatodo.yaganaste.ui.preferuser.presenters;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IAdqTransactionRegisterView;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.adquirente.presenters.AdqPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.AUDIO_SERVICE;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_CONFIG_REPAYMENT;
import static com.pagatodo.yaganaste.utils.Recursos.ENCENDIDO;
import static com.pagatodo.yaganaste.utils.Recursos.ERROR;
import static com.pagatodo.yaganaste.utils.Recursos.ERROR_LECTOR;
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
import static com.pagatodo.yaganaste.utils.Recursos.SW_ERROR;
import static com.pagatodo.yaganaste.utils.Recursos.SW_TIMEOUT;

/**
 * Armando Sandoval 16/10/2017
 */
public class MyDongleFragment extends GenericFragment implements
        IAdqTransactionRegisterView, IPreferUserGeneric {
    protected boolean isReaderConected = false;
    @BindView(R.id.txtCompanyName)
    StyleTextView txtCompanyName;
    @BindView(R.id.txtNumberBattery)
    StyleTextView txtNumberBattery;
    @BindView(R.id.iconBattery)
    ImageView iconBattery;
    @BindView(R.id.lyt_config_repayment)
    LinearLayout lytConfigRepayment;
    private AudioManager audioManager;
    public Preferencias prefs;

    private IntentFilter broadcastEMVSwipe;
    private int currentVolumenDevice;
    private int maxVolumenDevice;
    private String amount = "";
    private String detailAmount = "";
    private AdqPresenter adqPresenter;
    private boolean isWaitingCard = false;
    private boolean isCancelation = false;

    private boolean mensajeuno = false;

    View rootview;

    public MyDongleFragment() {
        // Required empty public constructor
    }

    private BroadcastReceiver emvSwipeBroadcastReceiver = new BroadcastReceiver() {
        @SuppressLint("SimpleDateFormat")
        @Override
        public void onReceive(Context context, Intent intent) {
            int mensaje = intent.getIntExtra(MSJ, -1);
            String error = intent.getStringExtra(ERROR);
            switch (mensaje) {
                case LECTURA_OK:

                    break;
                case READ_KSN:
                    TransaccionEMVDepositRequest transactionKsn = (TransaccionEMVDepositRequest) intent.getSerializableExtra(Recursos.TRANSACTION);
                    verifyDongle(transactionKsn.getNoSerie());
                    break;
                case READ_BATTERY_LEVEL:
                    int batteryLevel = intent.getIntExtra(Recursos.BATTERY_LEVEL, 0);
                    Log.i("IposListener: ", "=====>>    batteryLevel " + batteryLevel);
                    String batteryPorcentage = intent.getStringExtra(Recursos.BATTERY_PORCENTAGE);
                    //Toast.makeText(context, "La bataca es: "+batteryLevel, Toast.LENGTH_LONG).show();
                    int n = Integer.parseInt(batteryPorcentage.toString().trim(), 16);
                    //Toast.makeText(context, "El porcentage de la bateria es: "+n, Toast.LENGTH_SHORT).show();
                    //App.getInstance().pos.getQposId();
                    setNumberBattery(n);
                    break;
                case ERROR_LECTOR:
                    Log.i("IposListener: ", "=====>>    ERROR_LECTOR");
                    hideLoader();
                    //closeProgress();
                    break;
                case LEYENDO:
                    Log.i("IposListener: ", "=====>>    LEYENDO");
                    App.getInstance().pos.doEmvApp(QPOSService.EmvOption.START);
                    showLoader(isCancelation ? getString(R.string.readcard_cancelation) : getResources().getString(R.string.readcard));
                    break;
                case REQUEST_AMOUNT:
                    Log.i("IposListener: ", "=====>>    REQUEST_AMOUNT");

                    String amountCard = TransactionAdqData.getCurrentTransaction().getAmount().replace(".", "");
                    if (isCancelation) {
                        amountCard = "150";
                    }
                    App.getInstance().pos.setAmount(amountCard, "", "484", QPOSService.TransactionType.PAYMENT);
                    break;
                case REQUEST_TIME:
                    String terminalTime = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                    App.getInstance().pos.sendTime(terminalTime);
                    Log.i("IposListener: ", "=====>>    REQUEST_TIME");
                    break;
                case REQUEST_IS_SERVER_CONNECTED:
                    App.getInstance().pos.isServerConnected(true);
                    Log.i("IposListener: ", "=====>>    REQUEST_IS_SERVER_CONNECTED");
                    break;
                case REQUEST_FINAL_CONFIRM:
                    App.getInstance().pos.finalConfirm(true);
                    Log.i("IposListener: ", "=====>>    REQUEST_FINAL_CONFIRM");
                    break;
                case REQUEST_PIN:
                    App.getInstance().pos.bypassPin();
                    Log.i("IposListener: ", "=====>>    REQUEST_PIN");
                    break;
                case SW_TIMEOUT:
                    //initListenerDongle();
                    break;
                case SW_ERROR:
                    if (mensajeuno == false) {
                        mensajeuno = true;
                        showSimpleDialogError(getString(R.string.vuelve_conectar_lector),
                                new DialogDoubleActions() {
                                    @Override
                                    public void actionConfirm(Object... params) {
                                        showInsertDongle();
                                    }

                                    @Override
                                    public void actionCancel(Object... params) {

                                    }
                                });

                    }
                    //Toast.makeText(getActivity(), getString(R.string.vuelve_conectar_lector), Toast.LENGTH_SHORT).show();
                    Log.i("IposListener: ", "=====>>    SW_Error");
                    break;
                case ENCENDIDO:
                    Log.i("IposListener: ", "=====>>    ENCENDIDO");
                    App.getInstance().pos.getQposInfo();
                    break;
                default:
                    Log.i("IposListener: ", "=====>>    DEFAULT:" + mensaje);
                    break;
            }
        }
    };

    private BroadcastReceiver headPhonesReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        isReaderConected = false;
                        txtNumberBattery.setText(" ");
                        //validatingDng = false; // Cancelar Validacion
                        mensajeuno = false;
                        try {
                            txtNumberBattery.setGravity(Gravity.START);
                            txtNumberBattery.setText(getString(R.string.please_connect_dongle_battery));
                            txtNumberBattery.setTextColor(getResources().getColor(R.color.redcolor23));
                            iconBattery.setVisibility(View.GONE);
                            txtNumberBattery.setSelected(true);
                            hideLoader();
                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                        } catch (Exception e) {

                        }
                        Log.i("IposListener: ", "isReaderConected  false");
                        break;
                    case 1:
                        try {
                            txtNumberBattery.setText("");
                            isReaderConected = true;
                            txtNumberBattery.setGravity(Gravity.START);
                            txtNumberBattery.setText(getString(R.string.receiving_dongle_battery));
                            txtNumberBattery.setTextColor(getResources().getColor(R.color.yellow));
                            iconBattery.setVisibility(View.GONE);
                            getActivity().registerReceiver(emvSwipeBroadcastReceiver, broadcastEMVSwipe);
                            maxVolumenDevice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolumenDevice, 0);
                            Log.i("IposListener: ", "=====>>   starReaderEmvSwipe ");
                            App.getInstance().pos.getQposInfo();
                        } catch (Exception e) {
                            getActivity().registerReceiver(emvSwipeBroadcastReceiver, broadcastEMVSwipe);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    };

    public static MyDongleFragment newInstance() {
        MyDongleFragment fragment = new MyDongleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        App.getInstance().pos.openAudio();
        maxVolumenDevice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolumenDevice, 0);
        // imageView.setVisibility(View.GONE);
        txtNumberBattery.setGravity(Gravity.START);
        txtNumberBattery.setText(getString(R.string.please_connect_dongle_battery));
        txtNumberBattery.setTextColor(getResources().getColor(R.color.redcolor23));
        iconBattery.setVisibility(View.GONE);
        txtNumberBattery.setSelected(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        prefs = App.getInstance().getPrefs();
        audioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
        currentVolumenDevice = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        broadcastEMVSwipe = new IntentFilter(Recursos.IPOS_READER_STATES);
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        getActivity().registerReceiver(headPhonesReceiver, filter);
        adqPresenter = new AdqPresenter(this);
        adqPresenter.setIView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_my_dongle, container, false);
        rootview = inflater.inflate(R.layout.fragment_my_dongle, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        txtCompanyName.setText( prefs.loadData(StringConstants.COMPANY_NAME));

        lytConfigRepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UtilsNet.isOnline(getActivity())) {
                    UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_LONG);
                } else {
                    onEventListener.onEvent(EVENT_GO_CONFIG_REPAYMENT, null);
                }
            }
        });
    }

    private void setNumberBattery(int mPorcentaje) {
        // Procesimiento para cambiar la imagen de manera dinamica, dependiendo del rango de carga
        txtNumberBattery.setText(" " + mPorcentaje + "%");
        mensajeuno = true;

        txtNumberBattery.setGravity(Gravity.END);
        txtNumberBattery.setTextColor(getResources().getColor(R.color.textColorAlternative));
        if (mPorcentaje > 0 && mPorcentaje < 25) {
            // Bateria Roja
            iconBattery.setVisibility(View.VISIBLE);
            iconBattery.setBackgroundResource(R.drawable.bateria25);
        } else if (mPorcentaje > 25 && mPorcentaje <= 50) {
            // Bateria Amarilla
            iconBattery.setVisibility(View.VISIBLE);
            iconBattery.setBackgroundResource(R.drawable.bateria50);
        } else if (mPorcentaje > 50 && mPorcentaje <= 85) {
            // Bateria Verde
            iconBattery.setVisibility(View.VISIBLE);
            iconBattery.setBackgroundResource(R.drawable.bateria75);
        } else if (mPorcentaje > 85 && mPorcentaje <= 100) {
            // Bateria Verde
            iconBattery.setVisibility(View.VISIBLE);
            iconBattery.setBackgroundResource(R.drawable.bateria100);
        } else if (mPorcentaje <= 0) {
            // Bateria 0 conectar
            iconBattery.setVisibility(View.VISIBLE);
            iconBattery.setBackgroundResource(R.drawable.bateria0);
        }
    }

    @Override
    public void showInsertDongle() {

    }

    @Override
    public void showInsertCard() {

    }

    @Override
    public void dongleValidated() {

    }

    @Override
    public void verifyDongle(String ksn) {

    }

    @Override
    public void transactionResult(String message) {

    }

    @Override
    public void showSimpleDialogError(String message, DialogDoubleActions actions) {
        /*UI.createSimpleCustomDialogNoCancel(getString(R.string.title_error), message,
                getFragmentManager(), actions);*/
        UI.showErrorSnackBar(getActivity(), message, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void nextScreen(String event, Object data) {

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
    public void showError(Object error) {

    }
}
