package com.pagatodo.yaganaste.modules.register.PhysicalCode;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.vision.barcode.Barcode;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;


import butterknife.BindView;
import butterknife.ButterKnife;


public class PhysicalCodeFragment extends GenericFragment implements View.OnClickListener {

    private View rootView;
    private RegActivity activity;

    @BindView(R.id.button_yes)
    public StyleButton buttonYes;
    @BindView(R.id.button_no)
    public StyleButton buttonNo;

    public static PhysicalCodeFragment newInstance() {
        return new PhysicalCodeFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (RegActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.physical_code_fragment, container, false);
        initViews();
        return rootView;
    }


    public void initViews() {
        ButterKnife.bind(this, rootView);
        activity.nextStep();
        buttonYes.setOnClickListener(this);
        buttonNo.setOnClickListener(this);
        activity.backvisivility(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_yes:
                activity.getRouter().showScanQR();
                break;
            case R.id.button_no:
                activity.getRouter().showDigitalCode();
                break;
        }
    }
}
