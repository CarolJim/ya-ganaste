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
 * Created by Jordan on 26/07/2017.
 */

public class CupoReferenciaFamiliarFragment extends GenericFragment implements View.OnClickListener{
    protected View rootview;

    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.btnNext)
    Button btnNext;
    private CupoActivityManager cupoActivityManager;

    public static CupoReferenciaFamiliarFragment newInstance() {
        CupoReferenciaFamiliarFragment fragment = new CupoReferenciaFamiliarFragment();
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
        rootview = inflater.inflate(R.layout.fragment_cupo_referencias, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                cupoActivityManager.onBtnBackPress();
                break;
            case R.id.btnNext:
                cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_REFERENCIA_PERSONAL, null);
                break;
        }
    }
}
