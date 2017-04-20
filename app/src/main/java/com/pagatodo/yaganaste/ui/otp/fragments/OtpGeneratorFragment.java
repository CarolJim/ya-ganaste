package com.pagatodo.yaganaste.ui.otp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.otp.presenter.OtpPResenter;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.otp.controllers.OtpView;
import com.pagatodo.yaganaste.ui.otp.presenters.OtpPresenterImp;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;


/**
 * @author Juan Guerra on 10/11/2016.
 */

public class OtpGeneratorFragment extends GenericFragment implements View.OnClickListener, OtpView {

    private View rootView;

    private CustomValidationEditText edtNip;
    private Button btnContinue;
    private OtpPResenter otpPResenter;


    public static OtpGeneratorFragment newInstance(){
        OtpGeneratorFragment homeTabFragment = new OtpGeneratorFragment();
        Bundle args = new Bundle();
        homeTabFragment.setArguments(args);
        return homeTabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.otpPResenter = new OtpPresenterImp(getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_password_otp, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        edtNip = (CustomValidationEditText) rootView.findViewById(R.id.edt_nip);
        btnContinue = (Button) rootView.findViewById(R.id.btn_continue);
        btnContinue.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        onContinueClicked();
    }

    private void onContinueClicked() {
        btnContinue.setEnabled(false);
        if (edtNip.getText().isEmpty()) {
            UI.showToast(R.string.nip_empty, getActivity());
        } else {
            otpPResenter.generateOTP(Utils.getSHA256(edtNip.getText()));
        }
    }

    @Override
    public void showError(Errors error) {
        UI.showToast(getString(R.string.no_offline_token), getActivity());
        btnContinue.setEnabled(false);
    }

    @Override
    public void onOtpGenerated(String otp) {

    }
}