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
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 25/07/2017.
 */

public class CupoInicioFragment extends GenericFragment implements View.OnClickListener {
    protected View rootview;
    @BindView(R.id.btnMeInteresa)
    Button btnMeInteresa;
    @BindView(R.id.txtOtroMomento)
    StyleTextView txtOtroMomento;
    CupoActivityManager cupoActivityManager;

    public static CupoInicioFragment newInstance() {
        CupoInicioFragment fragment = new CupoInicioFragment();
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
        rootview = inflater.inflate(R.layout.fragment_cupo_inicio, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnMeInteresa.setOnClickListener(this);
        txtOtroMomento.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMeInteresa:
                cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_CUENTAME_MAS, null);
                break;
            case R.id.txtOtroMomento:
                cupoActivityManager.onBtnBackPress();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        cupoActivityManager.hideToolBar();
    }
}
