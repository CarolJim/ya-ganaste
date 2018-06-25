package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_ADDRESS;

public class PreDomicilioNegocioFragment extends GenericFragment implements View.OnClickListener {

    private View rootView;
    @BindView(R.id.btn_yes)
    StyleTextView btnYes;
    @BindView(R.id.btn_no)
    StyleTextView btnNo;

    public static PreDomicilioNegocioFragment newInstance() {
        PreDomicilioNegocioFragment fragment = new PreDomicilioNegocioFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_pre_domicilio_negocio, container, false);
            initViews();
        }
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_yes) {
            onEventListener.onEvent(EVENT_GO_BUSSINES_ADDRESS, null);
            RegisterAgent.getInstance().setUseSameAddress(true);
        } else if (view.getId() == R.id.btn_no) {
            onEventListener.onEvent(EVENT_GO_BUSSINES_ADDRESS, null);
            RegisterAgent.getInstance().setUseSameAddress(false);
        }
    }
}
