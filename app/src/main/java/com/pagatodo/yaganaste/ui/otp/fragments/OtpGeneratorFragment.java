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
import com.pagatodo.yaganaste.ui.otp.activities.OtpCodeActivity;
import com.pagatodo.yaganaste.ui.otp.controllers.OtpView;
import com.pagatodo.yaganaste.ui.otp.presenters.OtpPresenterImp;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;

import static com.pagatodo.yaganaste.ui._controllers.SessionActivity.REQUESTCODE_OTP;


/**
 * @author Juan Guerra on 10/11/2016.
 */

public class OtpGeneratorFragment extends GenericFragment implements View.OnClickListener, OtpView {

    private View rootView;


    private CustomValidationEditText edtNipOtp;
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
        edtNipOtp = (CustomValidationEditText) rootView.findViewById(R.id.edt_nip);
        btnContinue = (Button) rootView.findViewById(R.id.btn_continue);
        btnContinue.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_continue:
                onContinueClicked();
                break;
        }
    }

    private void onContinueClicked() {
        btnContinue.setEnabled(false);
        if (edtNipOtp.getText().isEmpty()) {
            UI.showToast(R.string.nip_empty, getActivity());
            btnContinue.setEnabled(true);
        } else {
            otpPResenter.generateOTP(Utils.getSHA256(edtNipOtp.getText()));
        }
    }

    @Override
    public void showError(Errors error) {
        UI.showToastShort(getString(R.string.no_offline_token), getActivity());
        btnContinue.setEnabled(false);
    }

    @Override
    public void onOtpGenerated(String otp) {
        btnContinue.setEnabled(true);
        edtNipOtp.setText("");
        getActivity().startActivityForResult(OtpCodeActivity.createIntent(getActivity(),otp),REQUESTCODE_OTP);

    }
}