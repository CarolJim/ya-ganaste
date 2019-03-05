package com.pagatodo.yaganaste.ui.preferuser.presenters;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
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
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.AUDIO_SERVICE;
import static android.view.View.GONE;
import static com.pagatodo.yaganaste.modules.emisor.WalletMainActivity.EVENT_GO_CONFIG_REPAYMENT;
import static com.pagatodo.yaganaste.modules.emisor.WalletMainActivity.EVENT_GO_SELECT_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.BT_PAIR_DEVICE;
import static com.pagatodo.yaganaste.utils.Recursos.COMPANY_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.EMV_DETECTED;
import static com.pagatodo.yaganaste.utils.Recursos.ENCENDIDO;
import static com.pagatodo.yaganaste.utils.Recursos.ERROR;
import static com.pagatodo.yaganaste.utils.Recursos.ERROR_LECTOR;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ROL;
import static com.pagatodo.yaganaste.utils.Recursos.IS_AGREGADOR;
import static com.pagatodo.yaganaste.utils.Recursos.IS_UYU;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.MSJ;
import static com.pagatodo.yaganaste.utils.Recursos.READ_BATTERY_LEVEL;
import static com.pagatodo.yaganaste.utils.Recursos.REQUEST_FINAL_CONFIRM;
import static com.pagatodo.yaganaste.utils.Recursos.REQUEST_IS_SERVER_CONNECTED;
import static com.pagatodo.yaganaste.utils.Recursos.REQUEST_PIN;
import static com.pagatodo.yaganaste.utils.Recursos.REQUEST_TIME;
import static com.pagatodo.yaganaste.utils.Recursos.SW_TIMEOUT;

/**
 * Armando Sandoval 16/10/2017
 */
public class MyDongleFragment extends GenericFragment implements IPreferUserGeneric {

    private static final String TAG = MyDongleFragment.class.getSimpleName();
    private static String MODE_COMMUNICACTION = "mode_communication";
    protected boolean isReaderConected = false;
    @BindView(R.id.txtCompanyName)
    StyleTextView txtCompanyName;
    @BindView(R.id.txtNumberBattery)
    StyleTextView txtNumberBattery;
    @BindView(R.id.iconBattery)
    ImageView iconBattery;
    @BindView(R.id.lyt_config_repayment)
    LinearLayout lytConfigRepayment;
    @BindView(R.id.lyt_config_dongle)
    LinearLayout lytConfigDongle;
    @BindView(R.id.imgYaGanasteCard)
    ImageView imgYaGanasteCard;
    @BindView(R.id.txtSerialNumber)
    StyleTextView txtSerialNumber;
    private AudioManager audioManager;
    public Preferencias prefs;

    private IntentFilter broadcastEMVSwipe;
    private int currentVolumenDevice, maxVolumenDevice, communicationMode;
    private boolean isCancelation = false, mensajeuno = false;

    View rootview;

    public static MyDongleFragment newInstance(int communicationMode) {
        MyDongleFragment fragment = new MyDongleFragment();
        Bundle args = new Bundle();
        args.putInt(MODE_COMMUNICACTION, communicationMode);
        fragment.setArguments(args);
        return fragment;
    }
    public static MyDongleFragment newInstance() {
        MyDongleFragment fragment = new MyDongleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //communicationMode = getArguments().getInt(MODE_COMMUNICACTION);
        prefs = App.getInstance().getPrefs();
        audioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
        currentVolumenDevice = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        broadcastEMVSwipe = new IntentFilter(Recursos.IPOS_READER_STATES);
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        if (communicationMode == QPOSService.CommunicationMode.AUDIO.ordinal()) {
            App.getInstance().initEMVListener(QPOSService.CommunicationMode.AUDIO);
            getActivity().registerReceiver(headPhonesReceiver, filter);
        } else {
            App.getInstance().initEMVListener(QPOSService.CommunicationMode.BLUETOOTH);
            App.getInstance().pos.clearBluetoothBuffer();
            if (App.getInstance().pos != null) {
                App.getInstance().pos.scanQPos2Mode(App.getContext(), 30);
            } else {
                UI.createSimpleCustomDialogNoCancel(getString(R.string.title_error), getString(R.string.error_pos_is_null),
                        getFragmentManager(), new DialogDoubleActions() {
                            @Override
                            public void actionConfirm(Object... params) {
                                getActivity().finish();
                            }

                            @Override
                            public void actionCancel(Object... params) {
                            }
                        });
            }
            getActivity().registerReceiver(emvSwipeBroadcastReceiver, broadcastEMVSwipe);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_my_dongle, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        if (communicationMode == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
            imgYaGanasteCard.setImageResource(R.drawable.lector_bt);
            String[] btDevice = new String[2];
            if (prefs.loadData(BT_PAIR_DEVICE).contains("_")) {
                btDevice = prefs.loadData(BT_PAIR_DEVICE).split("_");
                txtSerialNumber.setText("Dispositivo: " + btDevice[0]);
            } else {
                btDevice[1] = prefs.loadData(BT_PAIR_DEVICE);
                txtSerialNumber.setText("S/N: " + btDevice[1]);
            }
        } else {
            imgYaGanasteCard.setImageResource(R.mipmap.lector_front);
            txtSerialNumber.setVisibility(GONE);
        }
        txtCompanyName.setText(prefs.loadData(COMPANY_NAME));
        boolean isComerioUyU = App.getInstance().getPrefs().loadDataBoolean(IS_UYU,true);
        boolean isAgregador = App.getInstance().getPrefs().loadDataBoolean(IS_AGREGADOR,true);

        try {
            if (!RequestHeaders.getIdCuentaAdq().equals(""))
                isComerioUyU = new DatabaseManager().isComercioUyU(RequestHeaders.getIdCuentaAdq());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (App.getInstance().getPrefs().loadDataInt(ID_ROL) == 129 || isComerioUyU||isAgregador) {
            lytConfigRepayment.setVisibility(View.GONE);
        }
        lytConfigRepayment.setOnClickListener(v -> {
            if (!UtilsNet.isOnline(getActivity())) {
                UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_LONG);
            } else {
                onEventListener.onEvent(EVENT_GO_CONFIG_REPAYMENT, null);
            }
        });
        lytConfigDongle.setOnClickListener(v -> {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            if (!adapter.isEnabled()) {
                Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enabler);
            } else {
                onEventListener.onEvent(EVENT_GO_SELECT_DONGLE, null);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (communicationMode == QPOSService.CommunicationMode.AUDIO.ordinal()) {
            App.getInstance().pos.openAudio();
        }
        App.getInstance().pos.getSdkVersion();
        maxVolumenDevice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolumenDevice, 0);
        // imageView.setVisibility(View.GONE);
        txtNumberBattery.setGravity(Gravity.START);
        if (communicationMode == QPOSService.CommunicationMode.AUDIO.ordinal()) {
            txtNumberBattery.setText(getString(R.string.please_connect_dongle_battery));
        } else {
            txtNumberBattery.setText(getString(R.string.please_turn_on_dongle_battery));
        }
        txtNumberBattery.setTextColor(getResources().getColor(R.color.redcolor23));
        iconBattery.setVisibility(GONE);
        txtNumberBattery.setSelected(true);
    }

    @Override
    public void onDestroy() {
        App.getInstance().pos.closeAudio();
        App.getInstance().pos.stopScanQPos2Mode();
        App.getInstance().pos.disconnectBT();
        unregisterReceiverDongle();
        super.onDestroy();
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

    public void unregisterReceiverDongle() {
        try {
            getActivity().unregisterReceiver(emvSwipeBroadcastReceiver); // Desregistramos receiver
        } catch (IllegalArgumentException ex) {
            Log.e(TAG, "emvSwipeBroadcastReceiver no registrado. Ex- " + ex.toString());
        }
    }

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
                            iconBattery.setVisibility(GONE);
                            txtNumberBattery.setSelected(true);
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
                            iconBattery.setVisibility(GONE);
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

    private BroadcastReceiver emvSwipeBroadcastReceiver = new BroadcastReceiver() {
        @SuppressLint("SimpleDateFormat")
        @Override
        public void onReceive(Context context, Intent intent) {
            int mensaje = intent.getIntExtra(MSJ, -1);
            String error = intent.getStringExtra(ERROR);
            switch (mensaje) {
                case READ_BATTERY_LEVEL:
                    int batteryLevel = intent.getIntExtra(Recursos.BATTERY_LEVEL, 0);
                    String batteryPorcentage = intent.getStringExtra(Recursos.BATTERY_PORCENTAGE);
                   /* if (communicationMode == QPOSService.CommunicationMode.AUDIO.ordinal()) {
                        Log.i("IposListener: ", "=====>>    batteryLevel " + batteryLevel);
                        //Toast.makeText(context, "La bataca es: "+batteryLevel, Toast.LENGTH_LONG).show();
                        int n = Integer.parseInt(batteryPorcentage.toString().trim(), 16);
                        //Toast.makeText(context, "El porcentage de la bateria es: "+n, Toast.LENGTH_SHORT).show();
                        //App.getInstance().pos.getQposId();
                        setNumberBattery(n);
                    } else {*/
                    setNumberBattery(Integer.parseInt(batteryPorcentage));
                    //}
                    break;
                case ERROR_LECTOR:
                    Log.i("IposListener: ", "=====>>    ERROR_LECTOR");
                    //closeProgress();
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
                    try {
                        if (!mensajeuno) {
                            App.getInstance().pos.stopScanQPos2Mode();
                            App.getInstance().pos.clearBluetoothBuffer();
                            App.getInstance().pos.scanQPos2Mode(App.getContext(), 30);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case ENCENDIDO:
                    Log.i("IposListener: ", "=====>>    ENCENDIDO");
                    App.getInstance().pos.getQposInfo();
                    break;
                case EMV_DETECTED:
                    Log.i("IposListener: ", "======>> Bluetooth Device ");
                    List<BluetoothDevice> devicesBT = App.getInstance().pos.getDeviceList();
                    String[] btDevice = new String[2];
                    if (prefs.loadData(BT_PAIR_DEVICE).contains("_")) {
                        btDevice = prefs.loadData(BT_PAIR_DEVICE).split("_");
                    } else {
                        btDevice[1] = prefs.loadData(BT_PAIR_DEVICE);
                    }
                    for (BluetoothDevice device : devicesBT) {
                        if (device.getAddress().equals(btDevice[1])) {
                            App.getInstance().pos.connectBluetoothDevice(true, 15, device.getAddress());
                            break;
                        } /*else if (device.getName().contains("MPOS")) {
                            App.getInstance().pos.connectBluetoothDevice(true, 15, device.getAddress());
                            break;
                        }*/
                    }
                    break;
                default:
                    Log.i("IposListener: ", "=====>>    DEFAULT:" + mensaje);
                    break;
            }
        }
    };
}
