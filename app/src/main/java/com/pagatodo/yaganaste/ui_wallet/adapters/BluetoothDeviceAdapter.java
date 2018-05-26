package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;

import java.util.List;

public class BluetoothDeviceAdapter extends BaseAdapter {

    private List<String> devices;
    private LayoutInflater m_Inflater;

    public void clearData() {
        devices.clear();
        devices = null;
    }

    public BluetoothDeviceAdapter(Context context, List<String> map) {
        this.devices = map;
        this.m_Inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public Object getItem(int position) {
        return devices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        if (convertView == null) {
            convertView = m_Inflater.inflate(R.layout.row_device_bluetooth, null);
        }
        TextView m_TitleName = (TextView) convertView
                .findViewById(R.id.txt_device_bt_founded);
        m_TitleName.setText(devices.get(position));
        return convertView;
    }

}
