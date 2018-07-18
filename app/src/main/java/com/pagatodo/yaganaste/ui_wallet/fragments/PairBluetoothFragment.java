package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dspread.xpos.EmvAppTag;
import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IAdqTransactionRegisterView;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.adquirente.presenters.AdqPresenter;
import com.pagatodo.yaganaste.ui_wallet.WalletMainActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.BluetoothDeviceAdapter;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui.adquirente.fragments.GetMountFragment.REQUEST_ID_MULTIPLE_PERMISSIONS;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;
import static com.pagatodo.yaganaste.utils.Recursos.BT_PAIR_DEVICE;
import static com.pagatodo.yaganaste.utils.Recursos.CONFIG_READER_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CONFIG_READER_OK_ERROR;
import static com.pagatodo.yaganaste.utils.Recursos.EMV_DETECTED;
import static com.pagatodo.yaganaste.utils.Recursos.ENCENDIDO;
import static com.pagatodo.yaganaste.utils.Recursos.ERROR;
import static com.pagatodo.yaganaste.utils.Recursos.ERROR_LECTOR;
import static com.pagatodo.yaganaste.utils.Recursos.MSJ;
import static com.pagatodo.yaganaste.utils.Recursos.READ_KSN;
import static com.pagatodo.yaganaste.utils.Recursos.SW_TIMEOUT;

public class PairBluetoothFragment extends SupportFragment implements AdapterView.OnItemClickListener,
        IAdqTransactionRegisterView {

    private static final String TAG = PairBluetoothFragment.class.getSimpleName();
    private View rootView;
    @BindView(R.id.rcv_founded_devices)
    ListView lstDevices;
    @BindView(R.id.founded_devices)
    StyleTextView txtDeviceFounded;
    @BindView(R.id.btnSearchDevices)
    StyleButton btnSearch;

    private List<String> devicesFounded;
    private List<BluetoothDevice> devicesBT;
    private BluetoothDeviceAdapter adapter;
    private IntentFilter broadcastEMVSwipe;
    private AdqPresenter adqPresenter;
    private int position;

    public static PairBluetoothFragment newInstance() {
        PairBluetoothFragment fragment = new PairBluetoothFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcastEMVSwipe = new IntentFilter(Recursos.IPOS_READER_STATES);
        devicesFounded = new ArrayList<>();
        adqPresenter = new AdqPresenter(this);
        adqPresenter.setIView(this);
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (!adapter.isEnabled()) {
            Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enabler);
        }
        getActivity().registerReceiver(emvPairBluetoothReceiver, broadcastEMVSwipe);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_pair_bluetooth, container, false);
            initViews();
        }
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        lstDevices.setOnItemClickListener(this);
        btnSearch.setOnClickListener(v -> {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            if (!adapter.isEnabled()) {
                Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enabler);
            } else {
                btnSearch.setVisibility(View.GONE);
                txtDeviceFounded.setVisibility(View.VISIBLE);
                lstDevices.setVisibility(View.VISIBLE);
                App.getInstance().initEMVListener(QPOSService.CommunicationMode.BLUETOOTH);
                App.getInstance().pos.clearBluetoothBuffer();
                //App.getInstance().pos.disconnectBT();
                App.getInstance().pos.scanQPos2Mode(getActivity(), 20);
                showLoader(getString(R.string.searching_devices));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        /*int permissionCall = ContextCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.RECORD_AUDIO);*/
        int permissionLocationFine = ContextCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionLocation = ContextCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permissionLocation == -1 /*|| permissionCall == -1*/ || permissionLocationFine == -1) {
            ValidatePermissions.checkPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION/*, Manifest.permission.RECORD_AUDIO*/},
                    REQUEST_ID_MULTIPLE_PERMISSIONS);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (App.getInstance().pos != null) {
            App.getInstance().pos.stopScanQPos2Mode();
        }
    }

    @Override
    public void onDestroy() {
        unregisterReceiverDongle();
        if (App.getInstance().pos != null)
            App.getInstance().pos.disconnectBT();
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (devicesBT.get(position).getBondState() != BluetoothDevice.BOND_BONDED) {
            onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.pairing_device));
            App.getInstance().pos.connectBluetoothDevice(true, 25,
                    devicesBT.get(position).getAddress());
        } else {
            this.position = position;
            App.getInstance().getPrefs().saveData(BT_PAIR_DEVICE, devicesBT.get(position).getName() + "_" + devicesBT.get(position).getAddress());
            UI.showSuccessSnackBar(getActivity(), getString(R.string.pairing_device_success),
                    Snackbar.LENGTH_SHORT);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getActivity().onBackPressed();
                }
            }, 500);
        }
    }

    @Override
    public void showInsertDongle() {

    }

    @Override
    public void showInsertCard() {

    }

    @Override
    public void showInsertPin() {

    }

    @Override
    public void dongleValidated() {
        hideLoader();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                configurePos();
            }
        }, DELAY_MESSAGE_PROGRESS);
    }

    @Override
    public void verifyDongle(String ksn) {
        Log.i("IposListener: ", "=====>>  READ_KSN  " + ksn);
        if (ksn.length() > 10) {
            Log.i("IposListener: ", "=====>>  saveData");
            adqPresenter.validateDongle(ksn);
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
        }
    }

    @Override
    public void transactionResult(String message, String tlv) {
    }

    @Override
    public void showSimpleDialogError(String message, DialogDoubleActions actions) {
        UI.createSimpleCustomDialogNoCancel(getString(R.string.title_error), message,
                getFragmentManager(), actions);
    }

    @Override
    public void onErrorConsultSaldo(String message) {

    }

    @Override
    public void onErrorTransaction() {

    }

    @Override
    public void cancelTransactionChip() {

    }

    @Override
    public void nextScreen(String event, Object data) {
    }

    @Override
    public void backScreen(String event, Object data) {
    }

    @Override
    public void showLoader(String message) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void showError(Object error) {
        DialogDoubleActions doubleActions = new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {

            }

            @Override
            public void actionCancel(Object... params) {

            }
        };
        UI.showAlertDialog(error.toString(), "Aceptar", getActivity(), doubleActions);
    }

    private void refreshAdapter() {
        if (adapter != null) {
            adapter.clearData();
            adapter = null;
        }
        devicesBT = App.getInstance().pos.getDeviceList();
        for (BluetoothDevice device : devicesBT) {
            devicesFounded.add(device.getName());
        }
        adapter = new BluetoothDeviceAdapter(App.getContext(), devicesFounded);
        lstDevices.setAdapter(adapter);
    }

    private void configurePos() {
        ArrayList<String> configuration = new ArrayList<>();
        configuration.add(EmvAppTag.ICS + "F4F0F0FAAFFE8000");
        configuration.add(EmvAppTag.Terminal_type + "22");
        configuration.add(EmvAppTag.Terminal_Capabilities + "60B8C8");
        App.getInstance().pos.updateEmvAPP(QPOSService.EMVDataOperation.update, configuration);
    }

    private void unregisterReceiverDongle() {
        try {
            getActivity().unregisterReceiver(emvPairBluetoothReceiver); // Desregistramos receiver
        } catch (IllegalArgumentException ex) {
            Log.e(TAG, "emvPairBluetoothReceiver no registrado. Ex- " + ex.toString());
        }
    }

    private BroadcastReceiver emvPairBluetoothReceiver = new BroadcastReceiver() {
        @SuppressLint("SimpleDateFormat")
        @Override
        public void onReceive(Context context, Intent intent) {

            int mensaje = intent.getIntExtra(MSJ, -1);
            String error = intent.getStringExtra(ERROR);

            switch (mensaje) {
                case READ_KSN:
                    TransaccionEMVDepositRequest transactionKsn = (TransaccionEMVDepositRequest) intent.getSerializableExtra(Recursos.TRANSACTION);
                    verifyDongle(transactionKsn.getNoSerie());
                    break;
                case ERROR_LECTOR:
                    Log.i("IposListener: ", "=====>>    ERROR_LECTOR");
                    //hideLoader();
                    //closeProgress();
                    break;
                case SW_TIMEOUT:
                    Log.i("IposListener: ", "=====>>    Timeout");
                    hideLoader();
                    if (lstDevices.getCount() == 0) {
                        lstDevices.setVisibility(View.GONE);
                        txtDeviceFounded.setVisibility(View.GONE);
                        btnSearch.setVisibility(View.VISIBLE);
                        UI.showErrorSnackBar(getActivity(), getString(R.string.searching_devices_error), Snackbar.LENGTH_SHORT);
                    }
                    break;
                case ENCENDIDO:
                    Log.i("IposListener: ", "=====>>    ENCENDIDO");
                    App.getInstance().pos.getQposId();
                    break;
                case EMV_DETECTED:
                    Log.i("IposListener: ", "======>> Bluetooth Device");
                    //App.getInstance().pos.stopScanQPos2Mode();
                    hideLoader();
                    refreshAdapter();
                    break;
                case CONFIG_READER_OK:
                    Log.i("IposListener: ", "=====>>    Configuration Success");
                    App.getInstance().getPrefs().saveData(BT_PAIR_DEVICE, devicesBT.get(position).getName() + "_" + devicesBT.get(position).getAddress());
                    UI.showSuccessSnackBar(getActivity(), getString(R.string.pairing_device_success),
                            Snackbar.LENGTH_SHORT);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getActivity().onBackPressed();
                        }
                    }, 2000);
                    break;
                case CONFIG_READER_OK_ERROR:
                    Log.i("IposListener: ", "=====>>    Configuration Error");
                    UI.showErrorSnackBar(getActivity(), getString(R.string.pairing_device_error), Snackbar.LENGTH_LONG);
                    break;
                default:
                    Log.i("IposListener: ", "=====>>    default");
                    //hideLoader();
                    break;
            }
        }
    };
}
