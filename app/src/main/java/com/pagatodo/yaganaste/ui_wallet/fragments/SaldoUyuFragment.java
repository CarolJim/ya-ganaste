package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_PAYMENT;

public class SaldoUyuFragment extends GenericFragment {

    private final static String MESSAGE_SALDO = "MESSAGE_SALDO";
    private View rootView;

    @BindView(R.id.txt_subtitle)
    StyleTextView txtSubtitle;
    @BindView(R.id.et_amount)
    public EditText et_amount;
    @BindView(R.id.btn_continue)
    StyleButton btnContinue;

    private String cardType, balance, cardNumber="1111";

    public static SaldoUyuFragment newInstance(String message) {
        SaldoUyuFragment saldoUyuFragment = new SaldoUyuFragment();
        Bundle args = new Bundle();
        args.putString(MESSAGE_SALDO, message);
        saldoUyuFragment.setArguments(args);
        return saldoUyuFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            String message = getArguments().getString(MESSAGE_SALDO);
            String[] desc = message.split("_");
            cardType = desc[0];
            balance = desc[1];
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_saldo_uyu, container, false);
            initViews();
        }
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        txtSubtitle.setText(getString(R.string.consultar_saldo_subtitle).format("%s", cardType, cardNumber));
        et_amount.setText(balance);
        btnContinue.setOnClickListener(view -> onEventListener.onEvent(EVENT_PAYMENT, null));
    }
}
