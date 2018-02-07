package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.RequestPaymentActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.RequestPaymentHorizontalAdapter;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoRequestPayment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.RequestPaymentActivity.EVENT_SOLICITAR_PAGO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProcessRequestPaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProcessRequestPaymentFragment extends GenericFragment implements View.OnClickListener {

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
    private int count;

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
        count = 0;
        // enviosPresenter = new EnviosPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        btnSendPayment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (lstRequest.size() > 0) {
            ((RequestPaymentActivity) getActivity()).showLoader("Enviando Mensajes");
            //Validacion de telefonos de yaganaste
            for (count = 0; count < lstRequest.size(); count++) {
                sendSMS(lstRequest.get(count).getReference(),
                        lstRequest.get(count).getHeadMessage() +
                                ". " + edtMessagePayment.getText().toString() +
                                " " + lstRequest.get(count).getFootMessage());
            }
        }
    }

    private void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(getActivity(), 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(getActivity(), 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        getActivity().registerReceiver(broadcastReceiverSend, new IntentFilter(SENT));
        //---when the SMS has been delivered---

        //showLoader(getContext().getString(R.string.verificando_sms_esperanuevo));
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    BroadcastReceiver broadcastReceiverSend = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    if (count == lstRequest.size()) {
                        ((RequestPaymentActivity) getActivity()).hideLoader();
                        showDialogMesage(getContext().getResources().getString(R.string.solicitud_enviada));
                    }
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    ((RequestPaymentActivity) getActivity()).hideLoader();
                    UI.showToastShort(getString(R.string.fallo_envio), getActivity());
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    ((RequestPaymentActivity) getActivity()).hideLoader();
                    UI.showToastShort(getString(R.string.sin_servicio), getActivity());
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    ((RequestPaymentActivity) getActivity()).hideLoader();
                    UI.showToastShort(getString(R.string.null_pdu), getActivity());
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    ((RequestPaymentActivity) getActivity()).hideLoader();
                    UI.showToastShort(getString(R.string.sin_senial), getActivity());
                    break;
            }
            getActivity().unregisterReceiver(this);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventListener) {
            this.onEventListener = (OnEventListener) context;
        }
    }

    private void showDialogMesage(final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        //Utils.sessionExpired();
                        onEventListener.onEvent(EVENT_SOLICITAR_PAGO, null);
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }

}
