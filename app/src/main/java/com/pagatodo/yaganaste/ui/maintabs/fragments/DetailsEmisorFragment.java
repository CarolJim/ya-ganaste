package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.MovementColorsFactory;

/**
 * @author Juan Guerra on 12/04/2017.
 */

public class DetailsEmisorFragment extends GenericFragment {

    private View rootView;

    private View layoutMovementTypeColor;
    private TextView txtItemMovDate;
    private TextView txtItemMovMonth;
    private LinearLayout layoutMovementDate;
    private TextView txtPremios;
    private TextView txtMarca;
    private TextView txtMonto;
    private TextView txtItemMovCents;
    private ImageView imgMovementDetail;
    private TextView txtMovementDetailCardNumber;
    private TextView txtMovementDetailClaveRastreo;
    private TextView txtMovementDetailConcept;
    private TextView txtMovementDetailDate;
    private TextView txtMovementDetailHour;
    private TextView txtMovementDetailAuthNumber;

    private MovimientosResponse movimientosResponse;

    public static DetailsEmisorFragment newInstance(@NonNull MovimientosResponse movimientosResponse){
        DetailsEmisorFragment detailsEmisorFragment = new DetailsEmisorFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailsActivity.DATA, movimientosResponse);
        detailsEmisorFragment.setArguments(args);
        return detailsEmisorFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            movimientosResponse = (MovimientosResponse)args.getSerializable(DetailsActivity.DATA);
        } else {
            throw new IllegalCallException(DetailsEmisorFragment.class.getSimpleName() + "must be called by newInstance factory method");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movement_emisor, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        loadViews();
        initViews();
    }

    private void loadViews() {
        layoutMovementTypeColor = rootView.findViewById(R.id.layout_movement_type_color);
        txtItemMovDate = (TextView)rootView.findViewById(R.id.txt_item_mov_date);
        txtItemMovMonth = (TextView)rootView.findViewById(R.id.txt_item_mov_month);
        layoutMovementDate = (LinearLayout) rootView.findViewById(R.id.layout_movement_date);
        txtPremios = (TextView)rootView.findViewById(R.id.txt_premios);
        txtMarca = (TextView)rootView.findViewById(R.id.txt_marca);
        txtMonto = (TextView)rootView.findViewById(R.id.txt_monto);
        txtItemMovCents = (TextView)rootView.findViewById(R.id.txt_item_mov_cents);
        imgMovementDetail = (ImageView) rootView.findViewById(R.id.imgMovementDetail);
        txtMovementDetailCardNumber = (TextView)rootView.findViewById(R.id.txtMovementDetailCardNumber);
        txtMovementDetailClaveRastreo = (TextView)rootView.findViewById(R.id.txtMovementDetailClaveRastreo);
        txtMovementDetailConcept = (TextView)rootView.findViewById(R.id.txtMovementDetailConcept);
        txtMovementDetailDate = (TextView)rootView.findViewById(R.id.txtMovementDetailDate);
        txtMovementDetailHour = (TextView)rootView.findViewById(R.id.txtMovementDetailHour);
        txtMovementDetailAuthNumber = (TextView)rootView.findViewById(R.id.txtMovementDetailAuthNumber);
    }

    @Override
    public void initViews() {

        Glide.with(this).load(movimientosResponse.getURLImagen()).placeholder(R.mipmap.ic_background_pago).error(R.mipmap.ic_background_pago).into(imgMovementDetail);

        String[] monto = String.valueOf(movimientosResponse.getImporte()).split("\\.");
        String[] date = movimientosResponse.getFechaMovimiento().split(" ");
        @ColorInt int movementColor = ContextCompat.getColor(getActivity(),
                MovementColorsFactory.getColorMovement(movimientosResponse.getTipoMovimiento()));

        layoutMovementTypeColor.setBackgroundColor(movementColor);
        txtPremios.setText(movimientosResponse.getDetalle());
        txtMarca.setText(movimientosResponse.getDescripcion());
        txtItemMovDate.setText(date[0]);
        txtItemMovMonth.setText(date[1]);
        if (movimientosResponse.getTipoMovimiento() != 1) {
            txtMonto.setText(String.format("$%s", monto[0]));
        } else {
            txtMonto.setText(String.format("-$%s", monto[0]));
        }
        if (monto.length > 1) {
            txtItemMovCents.setText(monto[1]);
        } else {
            txtItemMovCents.setText("00");
        }

        txtMonto.setTextColor(movementColor);
        txtItemMovCents.setTextColor(movementColor);

        txtMovementDetailDate.setText(movimientosResponse.getFechaMovimiento());
        txtMovementDetailHour.setText(movimientosResponse.getHoraMovimiento());
        txtMovementDetailAuthNumber.setText(movimientosResponse.getNumAutorizacion());
        txtMovementDetailClaveRastreo.setText(movimientosResponse.getIdMovimiento());
    }


}