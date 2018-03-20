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
import com.pagatodo.yaganaste.ui._controllers.OnlineTxActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;

import java.io.Serializable;


/**
 * @author Juan Guerra on 10/11/2016.
 */

public class TxDetailsFragment extends GenericFragment implements View.OnClickListener {

    private View rootView;

    private TextView txtTypeTx;
    private TextView txtMount;
    private TextView txtReference;
    private CustomValidationEditText edtPassword;
    private Button btnContinue;

    private OnlineTxData data;



    public static TxDetailsFragment newInstance(Serializable data) {
        TxDetailsFragment txDetailsFragment = new TxDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(OnlineTxActivity.DATA, data);
        txDetailsFragment.setArguments(args);
        return txDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = (OnlineTxData) getArguments().getSerializable(OnlineTxActivity.DATA);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online_operation_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
        setData();
    }

    @Override
    public void initViews() {
        txtTypeTx = (TextView) rootView.findViewById(R.id.txt_type_tx);
        txtMount = (TextView) rootView.findViewById(R.id.txt_mount);
        txtReference = (TextView) rootView.findViewById(R.id.txt_reference);
        edtPassword = (CustomValidationEditText) rootView.findViewById(R.id.edt_password);
        btnContinue = (Button) rootView.findViewById(R.id.btn_continue);
        btnContinue.setOnClickListener(this);
    }

    private void setData() {
        txtTypeTx.setText(data.getIdTipoTransaccion().getName());
        txtMount.setText(StringUtils.getCurrencyValue(data.getDatosTransferencia().getMount()));
        txtReference.setText(data.getDatosTransferencia().getReference());
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
        if (edtPassword.getText().toString().isEmpty()) {
            UI.showToast(R.string.nip_empty, getActivity());
        } else {
            onEventListener.onEvent(OnlineTxActivity.EVENT_APROVE_TX, Utils.getSHA256(edtPassword.getText()));
            edtPassword.setText(null);
        }
    }

}