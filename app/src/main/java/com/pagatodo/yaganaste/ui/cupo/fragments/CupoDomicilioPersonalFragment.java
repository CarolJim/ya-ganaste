package com.pagatodo.yaganaste.ui.cupo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.cupo.managers.CupoActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 25/07/2017.
 */

public class CupoDomicilioPersonalFragment extends GenericFragment implements View.OnClickListener {

    protected View rootview;
    private CupoActivityManager cupoActivityManager;

    @BindView(R.id.btnNextBussinesAddress)
    Button btnNextBussinesAddress;
    @BindView(R.id.btnBackBussinesAddress)
    Button btnBackBussinesAddress;

    public static CupoDomicilioPersonalFragment newInstance() {
        CupoDomicilioPersonalFragment fragment = new CupoDomicilioPersonalFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cupoActivityManager = ((RegistryCupoActivity) getActivity()).getCupoActivityManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_domicilio_negocio_cupo, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnNextBussinesAddress.setOnClickListener(this);
        btnBackBussinesAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextBussinesAddress:
                cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_COMPROBANTES, null);
                break;
            case R.id.btnBackBussinesAddress:
                cupoActivityManager.onBtnBackPress();
                break;
        }
    }
}
