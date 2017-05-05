package com.pagatodo.yaganaste.ui.payments.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentsProcessingManager;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentsProcessingPresenter;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 02/05/2017.
 */

public class PaymentAuthorizeFragment extends GenericFragment implements View.OnClickListener {

    private View rootview;
    PaymentsProcessingManager manager;
    IPaymentsProcessingPresenter presenter;

    @BindView(R.id.btn_continueEnvio)
    Button btnContinue;
    @BindView(R.id.editPasswordEnvio)
    EditText pass;
    @BindView(R.id.editDestinatario)
    StyleTextView referencia;

    @BindView(R.id.txt_nombre)
    StyleTextView nombre;
    @BindView(R.id.txt_importe)
    StyleTextView importe;
    @BindView(R.id.imgLogoPago)
    ImageView imgLogoPago;

    String password;
    Envios envios;

    public static PaymentAuthorizeFragment newInstance(Envios envios) {
        PaymentAuthorizeFragment fragment = new PaymentAuthorizeFragment();
        Bundle args = new Bundle();
        args.putSerializable("envios", envios);
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
        envios = (Envios) getArguments().getSerializable("envios");
        PaymentsProcessingActivity parentActivity = (PaymentsProcessingActivity) getActivity();
        manager = parentActivity.getManager();
        presenter = parentActivity.getPresenter();
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
        rootview = inflater.inflate(R.layout.fragment_authorize_payment, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        nombre.setText(envios.getNombreDestinatario());

        String text = String.format("%.2f", envios.getMonto());
        text = text.replace(",", ".");
        int h = importe.getWidth();
        importe.setText(String.format("$%s", text));
        String ref = envios.getReferencia();
        switch (envios.getTipoEnvio()) {
            case CABLE:
                referencia.setText(ref);
                break;
            case NUMERO_TARJETA:
                referencia.setText("**** **** **** " + ref.substring(13, ref.length()));
                break;
            case NUMERO_TELEFONO:
                referencia.setText(ref);
                break;
            default:
                break;
        }

        Glide.with(getContext()).load(envios.getComercio().getLogoURL()).placeholder(R.mipmap.logo_ya_ganaste).error(R.mipmap.icon_tab_promos).dontAnimate().into(imgLogoPago);

        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_continueEnvio) {
            password = pass.getText().toString();
            if (password.isEmpty() || password.equals("")) {
                Toast.makeText(getContext(), "password vacío", Toast.LENGTH_LONG).show();
            } else {
                try {
                    manager.showLoader("Enviando Pago");
                    presenter.sendPayment(MovementsTab.TAB3, envios);
                } catch (OfflineException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
