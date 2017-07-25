package com.pagatodo.yaganaste.ui.cupo;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StatusViewCupo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;


/**
 * Created by Dell on 25/07/2017.
 */

public class CupoStatusFragment extends GenericFragment {

    @BindView(R.id.status_view)StatusViewCupo statusViewCupo;
    @BindView(R.id.txt_status)TextView statusText;
    @BindView(R.id.txt_status_info)TextView statusTextInfo;
    @BindView(R.id.txt_contactanos)TextView mTextContact;

    @BindView(R.id.btnNextScreen)Button mButtonContinue;

    private View rootview;
    public static CupoStatusFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CupoStatusFragment fragment = new CupoStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         if (rootview == null) {
            rootview = inflater.inflate(R.layout.activity_cupo_status, container, false);
            initViews();
        }
        return rootview;


    }



    @OnClick(R.id.status_view)
    public void onClick(StatusViewCupo v){
        ObjectAnimator anim = ObjectAnimator.ofFloat(v, "firstPercent",
                0, v.getFirstPercent());
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(1000);
        anim.start();
    }

    @OnClick(R.id.btn_error_phase2)
    public void error(){
        statusViewCupo.updateError(60,30);
        statusText.setText("Error\nValidando Referencias\n2/3");

        statusTextInfo.setText(getText(R.string.txt_validate_fail_info));
    }


    @OnClick(R.id.btn_phase_0)
    public void resetPhase(){
        statusViewCupo.updateStatus(0,0);
        statusText.setText("Por confirmar");
        statusTextInfo.setText(getText(R.string.txt_validate_info));


        mButtonContinue.setVisibility(View.GONE);
        mTextContact.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_phase_1)
    public void updatePhase1(){
        statusViewCupo.updateStatus(33,0);
        statusText.setText("Validando\nDocumentos\n1/3");
        statusTextInfo.setText(getText(R.string.txt_validate_info));
    }
    @OnClick(R.id.btn_phase_2)
    public void updatePhase2(){
        statusViewCupo.updateStatus(66,33);
        statusText.setText("Validando\nReferencias\n2/3");
        statusTextInfo.setText(getText(R.string.txt_validate_info));
    }

    @OnClick(R.id.btn_phase_3)
    public void updatePhase3(){
        statusViewCupo.updateStatus(100,66);
        statusText.setText("Asignando\nLinea de Crédito\n3/3");

        statusTextInfo.setText(getText(R.string.txt_validate_info));
    }

    @OnClick(R.id.btn_phase_4)
    public void updatePhase4(){
        statusViewCupo.updateStatus(0,100);
        statusTextInfo.setText(getText(R.string.txt_validate_success_info));

        String txt1 =getString(R.string.txt_congratulations);
        SpannableString span1 = new SpannableString(getString(R.string.txt_congratulations));
        span1.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.text_size_normal)), 0, txt1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new RelativeSizeSpan(2f),txt1.length(), txt1.length() , 0);


        String mount =getString(R.string.txt_mount_credit,"10,100,500.00");
        SpannableString span2 = new SpannableString(mount);
        span2.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.text_size_title)), 0, mount.length()-2, SPAN_INCLUSIVE_INCLUSIVE);
        span2.setSpan(new SuperscriptSpan(),mount.length()-2,mount.length() ,SPAN_INCLUSIVE_INCLUSIVE);



        SpannableString span3 = new SpannableString(getString(R.string.txt_limit_credit));
        span3.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.text_size_normal)), 0, txt1.length(), SPAN_INCLUSIVE_INCLUSIVE);

        CharSequence finalText = TextUtils.concat(span1, " ", span2," ",span3);



        statusText.setText(finalText);

        mButtonContinue.setVisibility(View.VISIBLE);
        mTextContact.setVisibility(View.GONE);

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
    }
}
