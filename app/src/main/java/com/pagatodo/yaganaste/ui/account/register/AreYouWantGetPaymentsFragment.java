package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class AreYouWantGetPaymentsFragment extends GenericFragment implements View.OnClickListener{

    private View rootview;

    @BindView(R.id.btnWantPaymentsCardYes)
    StyleButton btnWantPaymentsCardYes;
    @BindView(R.id.btnWantPaymentsCardNo)
    StyleButton btnWantPaymentsCardNo;

    public AreYouWantGetPaymentsFragment() {
    }

    public static AreYouWantGetPaymentsFragment newInstance() {
        AreYouWantGetPaymentsFragment fragmentRegister = new AreYouWantGetPaymentsFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;

        if (context instanceof Activity) {
            activity = (Activity) context;
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_are_you_want_get_payments_card, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnWantPaymentsCardNo.setOnClickListener(this);
        btnWantPaymentsCardYes.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnWantPaymentsCardNo:

                break;
            case R.id.btnWantPaymentsCardYes:

                break;
            default:
                break;
        }
    }

}

