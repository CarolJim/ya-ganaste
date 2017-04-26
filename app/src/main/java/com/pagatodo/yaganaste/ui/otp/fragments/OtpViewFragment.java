package com.pagatodo.yaganaste.ui.otp.fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static com.pagatodo.yaganaste.ui.otp.activities.OtpCodeActivity.OTP_PARAM;


/**
 * @author Juan Guerra on 10/11/2016.
 */

public class OtpViewFragment extends GenericFragment implements View.OnClickListener {

    private View rootView;

    @BindView(R.id.txt_code)
    StyleTextView txtCode;
    @BindView(R.id.txt_time_remaining)
    StyleTextView txtTime;
    @BindView(R.id.btn_back)
    StyleButton btn_back;
    @BindView(R.id.btn_continue)
    StyleButton btn_continue;

    private String otpCode;
    private CountDownTimer timer;


    public static OtpViewFragment newInstance(String otpCode){
        OtpViewFragment otpViewFragment = new OtpViewFragment();
        Bundle args = new Bundle();
        args.putString(OTP_PARAM, otpCode);
        otpViewFragment.setArguments(args);
        return otpViewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.otpCode = getArguments().getString(OTP_PARAM);
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
        btn_back.setOnClickListener(this);
        btn_continue.setOnClickListener(this);
        txtCode.setText(otpCode);

        timer = new CountDownTimer(90 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("Time", String.valueOf(millisUntilFinished));

                if (Build.VERSION.SDK_INT >= 24) {
                    txtTime.setText(Html.fromHtml(getString(R.string.caduca_en,
                            DateUtils.formatElapsedTime(millisUntilFinished/1000)),
                            Html.FROM_HTML_MODE_LEGACY, null, null));
                } else {
                    txtTime.setText(Html.fromHtml(getString(R.string.caduca_en, DateUtils.formatElapsedTime(millisUntilFinished/1000))));

                }
            }

            @Override
            public void onFinish() {
                txtTime.setText(R.string.codigo_expirado);
                //NO-OP
            }
        };
        timer.start();
    }

    @Override
    public void onDetach() {
        timer.cancel();
        super.onDetach();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_back:
                getActivity().finish();
                break;

            case R.id.btn_continue:
                getActivity().setResult(RESULT_OK);
                getActivity().finish();
                break;
        }

    }


}