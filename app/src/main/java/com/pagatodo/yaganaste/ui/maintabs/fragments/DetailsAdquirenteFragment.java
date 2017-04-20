package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.Calendar;

/**
 * @author Juan Guerra on 12/04/2017.
 */

public class DetailsAdquirenteFragment extends GenericFragment {

    private View rootView;

    private TextView txtItemMovDate;
    private TextView txtItemMovMonth;
    LinearLayout layoutMovementDate;
    private TextView txtConceptShort;
    private TextView txtMarca;
    private TextView txtMonto;
    private TextView txtItemMovCents;
    LinearLayout layoutMovementAmount;
    private TextView txtSuccesFullConcept;
    private TextView txtSuccesFullAutorization;
    private TextView cardInfo;
    private TextView issuingBank;
    private TextView dateTime;
    private Button btGotToMenu;

    private DataMovimientoAdq dataMovimientoAdq;

    public static DetailsAdquirenteFragment newInstance(@NonNull DataMovimientoAdq dataMovimientoAdq){
        DetailsAdquirenteFragment detailsEmisorFragment = new DetailsAdquirenteFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailsActivity.DATA, dataMovimientoAdq);
        detailsEmisorFragment.setArguments(args);
        return detailsEmisorFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            dataMovimientoAdq = (DataMovimientoAdq)args.getSerializable(DetailsActivity.DATA);
        } else {
            throw new IllegalCallException(DetailsAdquirenteFragment.class.getSimpleName() + "must be called by newInstance factory method");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_adquirente_movement, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        loadViews();
        initViews();
    }

    private void loadViews() {
        txtItemMovDate = (TextView)rootView.findViewById(R.id.txtItemMovDate);
        txtItemMovMonth = (TextView)rootView.findViewById(R.id.txtItemMovMonth);
        layoutMovementDate = (LinearLayout)rootView.findViewById(R.id.layoutMovementDate);
        txtConceptShort = (TextView)rootView.findViewById(R.id.txt_concept_short);
        txtMarca = (TextView)rootView.findViewById(R.id.txt_marca);
        txtMonto = (TextView)rootView.findViewById(R.id.txt_monto);
        txtItemMovCents = (TextView)rootView.findViewById(R.id.txtItemMovCents);
        layoutMovementAmount = (LinearLayout)rootView.findViewById(R.id.layoutMovementAmount);
        txtSuccesFullConcept = (TextView)rootView.findViewById(R.id.txtSuccesFullConcept);
        txtSuccesFullAutorization = (TextView)rootView.findViewById(R.id.txtSuccesFullAutorization);
        cardInfo = (TextView)rootView.findViewById(R.id.cardInfo);
        issuingBank = (TextView)rootView.findViewById(R.id.issuingBank);
        dateTime = (TextView)rootView.findViewById(R.id.date_time);
        btGotToMenu = (Button) rootView.findViewById(R.id.bt_gotToMenu);
    }

    @Override
    public void initViews() {

        String[] monto = Utils.getCurrencyValue(dataMovimientoAdq.getMonto()).split("\\.");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getAdquirenteMovementDate(dataMovimientoAdq.getFecha()));

        txtItemMovDate.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        txtItemMovMonth.setText(DateUtil.getMonthShortName(calendar));
        txtConceptShort.setText(dataMovimientoAdq.getOperacion());
        txtMarca.setText(dataMovimientoAdq.getCompania());
        txtMonto.setText(monto[0]);
        txtItemMovCents.setText(monto[1]);
        txtSuccesFullConcept.setText(dataMovimientoAdq.getOperacion());
        txtSuccesFullAutorization.setText(dataMovimientoAdq.getNoAutorizacion());
        cardInfo.setText(dataMovimientoAdq.getTipoTarjetaBancaria() + "," + dataMovimientoAdq.getReferencia());
        issuingBank.setText(dataMovimientoAdq.getBancoEmisor() + "," + dataMovimientoAdq.getMarcaTarjetaBancaria());
        dateTime.setText(dataMovimientoAdq.getFecha());
    }


}