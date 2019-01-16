package com.pagatodo.yaganaste.ui.adqtransactioncancel.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
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
import de.hdodenhof.circleimageview.CircleImageView;

import static com.pagatodo.yaganaste.R.id.imageView;
import static com.pagatodo.yaganaste.interfaces.enums.CreditCardsTypes.MASTER_CARD;
import static com.pagatodo.yaganaste.interfaces.enums.CreditCardsTypes.VISA;

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
    ImageView imageView;
    public static DetailTransactionAdqCancel newInstance(DataMovimientoAdq dataMovimientoAdq) {
        DetailTransactionAdqCancel fragment = new DetailTransactionAdqCancel();
        Bundle args = new Bundle();
        args.putSerializable(DetailsActivity.DATA, dataMovimientoAdq);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        setVisibilityPrefer(false);
    }
    public void setVisibilityPrefer(Boolean mBoolean){
        if(mBoolean){
            imageView.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.GONE);
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        imageView= (ImageView) getActivity().findViewById(R.id.deposito_Share);
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
        if (dataMovimientoAdq.getMarcaTarjetaBancaria().equals(VISA.getDescription())) {
            imgTypeCard.setImageResource(VISA.getImage());
        } else if (dataMovimientoAdq.getMarcaTarjetaBancaria().equals(MASTER_CARD.getDescription())) {
            imgTypeCard.setImageResource(MASTER_CARD.getImage());
        }

        txtConceptoCobroCancel.setText(dataMovimientoAdq.getOperacion());


        txtFechaCobroCancel.setText(DateUtil.getBirthDateCustomString(calendar));
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
