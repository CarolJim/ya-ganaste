package com.pagatodo.yaganaste.ui.payments.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.EjecutarTransaccionResponse;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 27/04/2017.
 */

public class PaymentSuccessFragment extends GenericFragment {

    @BindView(R.id.txt_paymentTitle)
    TextView title;
    @BindView(R.id.txt_importe)
    MontoTextView importe;
    @BindView(R.id.layoutComision)
    LinearLayout layoutComision;
    @BindView(R.id.titleReferencia)
    TextView titleReferencia;
    @BindView(R.id.txtReferencia)
    TextView txtReferencia;
    @BindView(R.id.imgLogoPago)
    ImageView imgLogoPago;
    @BindView(R.id.txtAutorizacion)
    TextView autorizacion;
    @BindView(R.id.txtFecha)
    TextView fecha;
    @BindView(R.id.txtHora)
    TextView hora;
    @BindView(R.id.layoutMail)
    LinearLayout layoutMail;
    @BindView(R.id.titleMail)
    TextView titleMail;
    @BindView(R.id.layoutEditMail)
    RelativeLayout layoutEditMail;
    @BindView(R.id.editMail)
    EditText editMail;
    @BindView(R.id.btn_continueEnvio)
    StyleButton btnContinueEnvio;
    @BindView(R.id.layoutFavoritos)
    LinearLayout layoutFavoritos;

    private View rootview;
    Payments pago;
    EjecutarTransaccionResponse result;

    public static PaymentSuccessFragment newInstance(Payments pago, EjecutarTransaccionResponse result) {
        PaymentSuccessFragment fragment = new PaymentSuccessFragment();
        Bundle args = new Bundle();
        args.putSerializable("pago", pago);
        args.putSerializable("result", result);
        fragment.setArguments(args);
        return fragment;
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
        pago = (Payments) getArguments().getSerializable("pago");
        result = (EjecutarTransaccionResponse) getArguments().getSerializable("result");
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
        rootview = inflater.inflate(R.layout.fragment_payment_success, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        if (pago instanceof Recarga) {
            title.setText(R.string.title_recarga_success);
            layoutComision.setVisibility(View.GONE);
            titleReferencia.setText(R.string.txt_phone);
            layoutMail.setVisibility(View.GONE);
            layoutFavoritos.setVisibility(View.VISIBLE);
        } else if (pago instanceof Servicios) {

        } else if (pago instanceof Envios) {

        } else {

        }

        String text = String.format("%.2f", pago.getMonto());
        text = text.replace(",", ".");

        importe.setText(text);



        txtReferencia.setText(pago.getReferencia());
        Glide.with(getContext()).load(pago.getComercio().getLogoURL()).placeholder(R.mipmap.logo_ya_ganaste).error(R.mipmap.icon_tab_promos).dontAnimate().into(imgLogoPago);

        autorizacion.setText(result.getData().getNumeroAutorizacion());
        fecha.setText(result.getData().getFecha());
        hora.setText(result.getData().getHora());

        btnContinueEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                //getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}
