package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._controllers.OnlineTxActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OperadorSuccesFragment extends GenericFragment {
    View rootView;


    @BindView(R.id.imgResult)
    ImageView imgResult;
    @BindView(R.id.txtTitleResult)
    StyleTextView txtTitleResult;
    @BindView(R.id.txtSubtitleResult)
    StyleTextView txtMessageResult;
    //@BindView(R.id.btnNextResult)
    //StyleButton btnPrimaryResult;
    //@BindView(R.id.btnErrorResult)
    //StyleButton btnSecondaryResult;
    @BindView(R.id.layoutButtonsResult)
    LinearLayout llContentBtns;
    @BindView(R.id.txtDescriptionResult)
    StyleTextView txtDescriptionResult;
    @BindView(R.id.check)
    LottieAnimationView check;

    @BindView(R.id.btn_continueEnvio)
    StyleButton btn_continueEnvio;




    static int idusuario;

    public static OperadorSuccesFragment newInstance(int data) {
        OperadorSuccesFragment fragment = new OperadorSuccesFragment();
        idusuario=data;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView =inflater.inflate(R.layout.fragment_operador_succes, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {

        ButterKnife.bind(this, rootView);
        check.setAnimation(R.raw.check);
        check.playAnimation();
        if (idusuario==1){
            txtMessageResult.setText(getResources().getString(R.string.sub_change_status_success));
        }else {
            txtMessageResult.setText(getResources().getString(R.string.sub_change_status_blok));
        }


        btn_continueEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });







    }
}
