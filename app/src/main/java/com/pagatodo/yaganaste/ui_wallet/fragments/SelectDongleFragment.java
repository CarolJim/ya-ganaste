package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;

public class SelectDongleFragment extends GenericFragment implements View.OnClickListener {

    private View rootView;
    @BindView(R.id.lyt_bt_dongle)
    LinearLayout lytBtDongle;
    @BindView(R.id.lyt_normal_dongle)
    LinearLayout lytNormalDongle;
    @BindView(R.id.img_type_bt)
    ImageView imgTypeBt;
    @BindView(R.id.img_type_normal)
    ImageView imgTypeNormal;

    public static SelectDongleFragment newInstance() {
        SelectDongleFragment selectDongleFragment = new SelectDongleFragment();
        Bundle args = new Bundle();
        selectDongleFragment.setArguments(args);
        return selectDongleFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_selection_dongle, container, false);
            initViews();
        }
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        /*if (App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
            imgTypeBt.setImageResource(R.drawable.rdb_pressed);
            imgTypeNormal.setImageResource(R.drawable.rdb_not_pressed);
        } else {
            imgTypeBt.setImageResource(R.drawable.rdb_not_pressed);
            imgTypeNormal.setImageResource(R.drawable.rdb_pressed);
        }*/
        lytBtDongle.setOnClickListener(this);
        lytNormalDongle.setOnClickListener(this);

        try {
            if (App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                imgTypeBt.setImageResource(R.drawable.rdb_pressed);
            } else {
                imgTypeNormal.setImageResource(R.drawable.rdb_pressed);
            }
        }catch (Exception e){
            Log.d("NO Data","Selec type connection");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lyt_bt_dongle:
                App.getInstance().getPrefs().saveDataInt(MODE_CONNECTION_DONGLE, QPOSService.CommunicationMode.BLUETOOTH.ordinal());
                imgTypeBt.setImageResource(R.drawable.rdb_pressed);
                imgTypeNormal.setImageResource(R.drawable.rdb_not_pressed);
                //onEventListener.onEvent(EVENT_GO_CONFIG_DONGLE, null);
                break;
            case R.id.lyt_normal_dongle:
                App.getInstance().getPrefs().saveDataInt(MODE_CONNECTION_DONGLE, QPOSService.CommunicationMode.AUDIO.ordinal());
                imgTypeBt.setImageResource(R.drawable.rdb_not_pressed);
                imgTypeNormal.setImageResource(R.drawable.rdb_pressed);
                break;
        }
    }
}
