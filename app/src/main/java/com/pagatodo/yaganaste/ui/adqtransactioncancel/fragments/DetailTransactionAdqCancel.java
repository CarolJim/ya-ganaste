package com.pagatodo.yaganaste.ui.adqtransactioncancel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DetailsAdquirenteFragment;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 19/06/2017.
 */

public class DetailTransactionAdqCancel extends GenericFragment implements View.OnClickListener {

    @BindView(R.id.txtMontoCobroCancel)
    MontoTextView txtMontoCobroCancel;
    @BindView(R.id.txtReferenciaCobroCancel)
    TextView txtReferenciaCobroCancel;
    @BindView(R.id.imgTypeCard)
    ImageView imgTypeCard;
    @BindView(R.id.txtConceptoCobroCancel)
    TextView txtConceptoCobroCancel;
    @BindView(R.id.txtFechaCobroCancel)
    TextView txtFechaCobroCancel;
    @BindView(R.id.txtHoraCobroCancel)
    TextView txtHoraCobroCancel;
    @BindView(R.id.btnVolver)
    Button btnVolver;
    @BindView(R.id.btnCancelCobro)
    Button btnCancel;
    private DataMovimientoAdq dataMovimientoAdq;
    private View rootView;

    public static DetailTransactionAdqCancel newInstance(DataMovimientoAdq dataMovimientoAdq) {
        DetailTransactionAdqCancel fragment = new DetailTransactionAdqCancel();
        Bundle args = new Bundle();
        args.putSerializable(DetailsActivity.DATA, dataMovimientoAdq);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            dataMovimientoAdq = (DataMovimientoAdq) args.getSerializable(DetailsActivity.DATA);
        } else {
            throw new IllegalCallException(DetailsAdquirenteFragment.class.getSimpleName() + "must be called by newInstance factory method");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detalle_adquirente_cancelar, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getAdquirenteMovementDate(dataMovimientoAdq.getFecha()));

        txtMontoCobroCancel.setText(dataMovimientoAdq.getMonto());
        txtReferenciaCobroCancel.setText(dataMovimientoAdq.getReferencia());
        if (dataMovimientoAdq.getMarcaTarjetaBancaria().equals("Visa")) {
            imgTypeCard.setImageResource(R.drawable.visa);
        } else if (dataMovimientoAdq.getMarcaTarjetaBancaria().equals("Master Card")) {
            imgTypeCard.setImageResource(R.drawable.mastercard_canvas);
        }

        txtConceptoCobroCancel.setText(dataMovimientoAdq.getOperacion());

        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", new Locale("es", "ES"));
        txtFechaCobroCancel.setText(dateFormat.format(calendar.getTime()));
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss", new Locale("es", "ES"));
        txtHoraCobroCancel.setText(hourFormat.format(calendar.getTime()) + " hrs");


        btnCancel.setOnClickListener(this);
        btnVolver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVolver:
                getActivity().onBackPressed();
                break;
            case R.id.btnCancelCobro:
                ((DetailsActivity) getActivity()).loadInsertDongleFragment(dataMovimientoAdq);
                break;
        }
    }
}
