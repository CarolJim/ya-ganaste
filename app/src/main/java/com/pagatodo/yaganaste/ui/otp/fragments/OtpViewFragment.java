package com.pagatodo.yaganaste.ui.otp.fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.otp.presenter.OtpPResenter;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.otp.controllers.OtpView;
import com.pagatodo.yaganaste.ui.otp.presenters.OtpPresenterImp;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;
import com.pagatodo.yaganaste.utils.customviews.StatusViewCupo;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui.otp.activities.OtpCodeActivity.OTP_PASS;


/**
 * @author Juan Guerra on 10/11/2016.
 */

public class OtpViewFragment extends GenericFragment implements View.OnClickListener, OtpView {

    @BindView(R.id.txt_code)
    StyleTextView txtCode;
    @BindView(R.id.txt_time_remaining)
    StyleTextView txtTime;
    @BindView(R.id.btn_back)
    StyleButton btnBack;
    @BindView(R.id.progress_view)
    StatusViewCupo progressView;
    private View rootView;
    private String shaPass;
    private CountDownTimer timer;
    private OtpPResenter otpPResenter;


    public static OtpViewFragment newInstance(String shaPass) {
        OtpViewFragment otpViewFragment = new OtpViewFragment();
        Bundle args = new Bundle();
        args.putString(OTP_PASS, shaPass);
        otpViewFragment.setArguments(args);
        return otpViewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.shaPass = getArguments().getString(OTP_PASS);
        this.otpPResenter = new OtpPresenterImp(getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_success_otp, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnBack.setOnClickListener(this);
        otpPResenter.generateOTP(shaPass);
    }

    private void reload(String otpCode) {
        txtCode.setText(StringUtils.formatWithSpace(otpCode, 4,4));

        progressView.setTimeAnimation(90000);
        progressView.updateStatus(100,0);

        timer = new CountDownTimer(90 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (Build.VERSION.SDK_INT >= 24) {
                    txtTime.setText(Html.fromHtml(getString(R.string.caduca_en,
                            DateUtils.formatElapsedTime(millisUntilFinished / 1000)),
                            Html.FROM_HTML_MODE_LEGACY, null, null));
                } else {
                    txtTime.setText(Html.fromHtml(getString(R.string.caduca_en, DateUtils.formatElapsedTime(millisUntilFinished / 1000))));
                }
            }

            @Override
            public void onFinish() {
                onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.generando_token));
                otpPResenter.generateOTP(shaPass);
            }
        };
        timer.start();
    }

    @Override
    public void onDetach() {
        timer.cancel();
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
        super.onDetach();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                getActivity().onBackPressed();
                break;

            default:
                break;
        }
    }

    @Override
    public void showError(Errors error) {
        UI.createSimpleCustomDialog("", getString(R.string.no_offline_token),
                getActivity().getSupportFragmentManager(), CustomErrorDialog.TAG);
    }

    @Override
    public void onOtpGenerated(String otp) {
        reload(otp);
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);

    }
}