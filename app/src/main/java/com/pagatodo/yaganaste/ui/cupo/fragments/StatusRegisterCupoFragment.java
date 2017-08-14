package com.pagatodo.yaganaste.ui.cupo.fragments;

import android.content.Context;
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
import android.widget.Button;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.cupo.interactors.StatusRegisterCupoInteractor;
import com.pagatodo.yaganaste.ui.cupo.presenters.StatusRegisterCupoPresenter;
import com.pagatodo.yaganaste.ui.cupo.view.IViewStatusRegisterCupo;
import com.pagatodo.yaganaste.utils.customviews.StatusViewCupo;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;


/**
 * Created by Dell on 25/07/2017.
 */

public class StatusRegisterCupoFragment extends GenericFragment  implements IViewStatusRegisterCupo{

    @BindView(R.id.status_view)StatusViewCupo statusViewCupo;
    @BindView(R.id.txt_status)StyleTextView statusText;
    @BindView(R.id.txt_status_info)StyleTextView statusTextInfo;
    @BindView(R.id.txt_contactanos)StyleTextView mTextContact;

    @BindView(R.id.btnNextScreen)Button mButtonContinue;

    private View rootview;
    private StatusRegisterCupoPresenter mPresenter;
    private LoaderActivity mLoader;

    public static StatusRegisterCupoFragment newInstance() {
        
        Bundle args = new Bundle();
        
        StatusRegisterCupoFragment fragment = new StatusRegisterCupoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new StatusRegisterCupoPresenter(new StatusRegisterCupoInteractor());
        mPresenter.setView(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null){
            mLoader = (LoaderActivity) context;
        }
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


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.doRequestStatusRegister();

    }


    public void requestLimitCreditSuccess(String mount){
        String txt1 =getString(R.string.txt_congratulations);
        SpannableString span1 = new SpannableString(getString(R.string.txt_congratulations));
        span1.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.text_size_normal)), 0, txt1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new RelativeSizeSpan(2f),txt1.length(), txt1.length() , 0);


        //String mount =getString(R.string.txt_mount_credit,"10,100,500.00");
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
        statusTextInfo.setText(getText(R.string.txt_validate_info));
    }

    @Override
    public void showLoader(boolean show) {
        if (show ) {
            mLoader.showLoader("Solicitando Estatus");
        }else{
            mLoader.hideLoader();
        }

    }

    @Override
    public void showError(String error) {
        // mLoader.showError(new ErrorObject());
    }

    @Override
    public void showStatusRegister() {
        statusText.setText("Validando\nReferencias\n1/3");
        statusViewCupo.updateStatus(33,0);
        statusViewCupo.updateError(66, 33);


    }
}
