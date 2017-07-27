package com.pagatodo.yaganaste.ui.cupo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.cupo.managers.CupoActivityManager;
import com.pagatodo.yaganaste.utils.customviews.BorderTitleLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 25/07/2017.
 */

public class CupoComprobantesFragment extends GenericFragment implements View.OnClickListener{

    protected View rootview;
    @BindView(R.id.first_cube)
    BorderTitleLayout layoutIdentificacion;
    @BindView(R.id.lnr_help)
    LinearLayout layoutHelp;
    private CupoActivityManager cupoActivityManager;

    @BindView(R.id.btnWeNeedSmFilesNext)
    Button btnNext;
    @BindView(R.id.btnRegresar)
    Button btnBack;

    public static CupoComprobantesFragment newInstance() {
        CupoComprobantesFragment fragment = new CupoComprobantesFragment();
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
        rootview = inflater.inflate(R.layout.fragments_documents, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        layoutIdentificacion.setVisibility(View.GONE);
        layoutHelp.setVisibility(View.GONE);

        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegresar:
                cupoActivityManager.onBtnBackPress();
                break;
            case R.id.btnWeNeedSmFilesNext:
                //cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_DOMICILIO_PERSONAL, null);
                getActivity().finish();
                break;
        }
    }
}
