package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.RequestPaymentHorizontalAdapter;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoRequestPayment;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProcessRequestPaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProcessRequestPaymentFragment extends GenericFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String REQUEST_LIST = "REQUEST_LIST";
    private static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";

    View rootView;
    @BindView(R.id.txt_amount_request)
    MontoTextView txtTotalAmount;
    @BindView(R.id.rcv_request_payment)
    RecyclerView rcvRequest;
    @BindView(R.id.edt_message_payment)
    StyleEdittext edtMessagePayment;
    @BindView(R.id.btn_send_payment)
    StyleButton btnSendPayment;

    private ArrayList<DtoRequestPayment> lstRequest;
    private float monto;

    public ProcessRequestPaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProcessRequestPaymentFragment.
     */
    public static ProcessRequestPaymentFragment newInstance(ArrayList<DtoRequestPayment> lst, float monto) {
        ProcessRequestPaymentFragment fragment = new ProcessRequestPaymentFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(REQUEST_LIST, lst);
        args.putFloat(TOTAL_AMOUNT, monto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lstRequest = getArguments().getParcelableArrayList(REQUEST_LIST);
            monto = getArguments().getFloat(TOTAL_AMOUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_process_request_payment, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        txtTotalAmount.setText(Utils.getCurrencyValue(monto));
        rcvRequest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvRequest.setAdapter(new RequestPaymentHorizontalAdapter(lstRequest));
    }
}
