package com.pagatodo.yaganaste.ui_wallet.dialog;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IAdqTransactionRegisterView;
import com.pagatodo.yaganaste.ui_wallet.adapters.BluetoothDeviceAdapter;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogSelectBluetooth extends DialogFragment implements AdapterView.OnItemClickListener {

    View rootView;
    @BindView(R.id.rcv_founded_devices)
    ListView lstDevices;
    @BindView(R.id.bnt_cancel)
    StyleButton btnCancel;

    private IAdqTransactionRegisterView iAdqTransactionRegisterView;
    private List<BluetoothDevice> devices;
    private BluetoothDeviceAdapter adapter;
    private List<String> devicesFounded = new ArrayList<>();

    public static DialogSelectBluetooth newInstance() {
        DialogSelectBluetooth dialogFragment = new DialogSelectBluetooth();
        return dialogFragment;
    }

    public void setiAdqTransactionRegisterView(IAdqTransactionRegisterView iAdqTransactionRegisterView) {
        this.iAdqTransactionRegisterView = iAdqTransactionRegisterView;
    }

    public void setDevices(List<BluetoothDevice> devices) {
        this.devices = devices;
    }

    public void refreshData(List<BluetoothDevice> devices) {
        this.devices = devices;
        if (adapter != null) {
            adapter.clearData();
            adapter = null;
        }
        for (BluetoothDevice device : this.devices) {
            devicesFounded.add(device.getName());
        }
        adapter = new BluetoothDeviceAdapter(App.getContext(), devicesFounded);
        if (lstDevices != null)
            lstDevices.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_select_bluetooth, null);
        initViews();
        return rootView;
    }

    private void initViews() {
        ButterKnife.bind(this, rootView);
        lstDevices.setOnItemClickListener(this);
        btnCancel.setOnClickListener(view -> {
            iAdqTransactionRegisterView.onSearchCancel();
            dismiss();
        });
        if (adapter != null) {
            adapter.clearData();
            adapter = null;
        }
        for (BluetoothDevice device : this.devices) {
            devicesFounded.add(device.getName());
        }
        adapter = new BluetoothDeviceAdapter(App.getContext(), devicesFounded);
        lstDevices.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        iAdqTransactionRegisterView.onDongleSelected(i);
        dismiss();
    }
}
