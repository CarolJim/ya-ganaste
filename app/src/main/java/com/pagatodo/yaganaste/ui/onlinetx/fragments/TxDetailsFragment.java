package com.pagatodo.yaganaste.ui.onlinetx.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.OnlineTxData;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.ui._controllers.OnlineTxActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.onlinetx.controllers.OnlineTxView;
import com.pagatodo.yaganaste.ui.onlinetx.presenters.OnlineTxPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import java.io.Serializable;


/**
 * @author Juan Guerra on 10/11/2016.
 */

public class TxDetailsFragment extends GenericFragment implements View.OnClickListener, OnlineTxView {

    private View rootView;

    private TextView txtTypeTx;
    private TextView txtMount;
    private TextView txtDetails;
    private TextView txtReference;
    private EditText edtNip;
    private Button btnContinue;
    private ProgressLayout progressLayout;

    private OnlineTxData data;

    private OnlineTxPresenter onlineTxPresenter;


    public static TxDetailsFragment newInstance(Serializable data) {
        TxDetailsFragment homeTabFragment = new TxDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(OnlineTxActivity.DATA, data);
        homeTabFragment.setArguments(args);
        return homeTabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = (OnlineTxData) getArguments().getSerializable(OnlineTxActivity.DATA);
        onlineTxPresenter = new OnlineTxPresenter(getActivity(), this, data.getIdFreja());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online_operation_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
        onlineTxPresenter.getTransactions();
    }

    @Override
    public void initViews() {
        txtTypeTx = (TextView) rootView.findViewById(R.id.txt_type_tx);
        txtMount = (TextView) rootView.findViewById(R.id.txt_mount);
        txtDetails = (TextView) rootView.findViewById(R.id.txt_details);
        txtReference = (TextView) rootView.findViewById(R.id.txt_reference);
        edtNip = (EditText) rootView.findViewById(R.id.edt_nip);
        btnContinue = (Button) rootView.findViewById(R.id.btn_continue);
        progressLayout = (ProgressLayout) rootView.findViewById(R.id.progress_indicator);
        btnContinue.setOnClickListener(this);
    }

    private void setData() {
        txtTypeTx.setText(data.getTypeTx());
        txtMount.setText(data.getMount());
        txtDetails.setText(data.getDetails());
        txtReference.setText(data.getReference());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_continue:
                onContinueClicked();
                break;

            default:
                break;
        }
    }

    private void onContinueClicked() {
        btnContinue.setEnabled(false);
        if (edtNip.getText().toString().isEmpty()) {
            UI.showToast(R.string.nip_empty, getActivity());
        } else {
            onlineTxPresenter.aproveTransaction(data.getIdFreja(), Utils.getSHA256(edtNip.getText().toString()));
        }
    }

    @Override
    public void loadTransactionData() {
        setData();
    }

    @Override
    public void showLoader(String message) {
        progressLayout.setVisibility(View.VISIBLE);
        progressLayout.setTextMessage(message);
    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(Errors error) {
        UI.showToast(getString(R.string.tx_expired), getActivity());
        btnContinue.setEnabled(false);
    }

    @Override
    public void onTxAproved() {
        onEventListener.onEvent(OnlineTxActivity.EVENT_TX_APROVED, null);
    }

}