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
import com.pagatodo.yaganaste.utils.Utils;

/**
 * @author Juan Guerra on 12/04/2017.
 */

public class DetailsEmisorFragment extends GenericFragment {

    private View rootView;

    private View layoutMovementTypeColor;
    private TextView txtItemMovDate;
    private TextView txtItemMovMonth;
    private TextView txtPremios;
    private TextView txtMarca;
    private TextView txtMonto;
    private TextView txtItemMovCents;
    private ImageView imgMovementDetail;
    private TextView txtMovementDetailIdTransaccion;
    private TextView txtMovementDetailConcept;
    private TextView txtMovementDetailDate;
    private TextView txtMovementDetailHour;
    private TextView txtMovementDetailAuthNumber;
    private TextView txtMovementDetailReference;
    private TextView txtMovementDetailImport;
    private TextView txtMovementDetailComision;
    private TextView txtMovementDetailIva;


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
        //layoutMovementDate = (LinearLayout) rootView.findViewById(R.id.layout_movement_date);
        txtPremios = (TextView)rootView.findViewById(R.id.txt_premios);
        txtMarca = (TextView)rootView.findViewById(R.id.txt_marca);
        txtMonto = (TextView)rootView.findViewById(R.id.txt_monto);
        txtItemMovCents = (TextView)rootView.findViewById(R.id.txt_item_mov_cents);
        imgMovementDetail = (ImageView) rootView.findViewById(R.id.imgMovementDetail);


        txtMovementDetailIdTransaccion = (TextView)rootView.findViewById(R.id.txtMovementDetailIdTransaccion);
        txtMovementDetailConcept = (TextView)rootView.findViewById(R.id.txtMovementDetailConcept);
        txtMovementDetailDate = (TextView)rootView.findViewById(R.id.txtMovementDetailDate);
        txtMovementDetailHour = (TextView)rootView.findViewById(R.id.txtMovementDetailHour);
        txtMovementDetailAuthNumber = (TextView)rootView.findViewById(R.id.txtMovementDetailAuthNumber);
        txtMovementDetailReference = (TextView) rootView.findViewById(R.id.txtMovementDetailReference);
        txtMovementDetailImport = (TextView) rootView.findViewById(R.id.txtMovementDetailImport);
        txtMovementDetailComision = (TextView) rootView.findViewById(R.id.txtMovementDetailComision);
        txtMovementDetailIva = (TextView) rootView.findViewById(R.id.txtMovementDetailIva);
    }

    @Override
    public void initViews() {

        Glide.with(this).load(movimientosResponse.getURLImagen()).placeholder(R.mipmap.ic_background_pago).error(R.mipmap.ic_background_pago).into(imgMovementDetail);

        String[] monto = Utils.getCurrencyValue(movimientosResponse.getTotal()).split("\\.");

        String[] date = movimientosResponse.getFechaMovimiento().split(" ");
        @ColorInt int movementColor = ContextCompat.getColor(getActivity(),
                MovementColorsFactory.getColorMovement(movimientosResponse.getTipoMovimiento()));

        layoutMovementTypeColor.setBackgroundColor(movementColor);
        txtPremios.setText(movimientosResponse.getDetalle());
        txtMarca.setText(movimientosResponse.getDescripcion());
        txtItemMovDate.setText(date[0]);
        txtItemMovMonth.setText(date[1]);
        txtMonto.setText(monto[0].concat("."));

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

        txtMovementDetailIdTransaccion.setText(movimientosResponse.getIdMovimiento());
        txtMovementDetailConcept.setText(movimientosResponse.getConcepto());

        txtMovementDetailReference.setText(movimientosResponse.getReferencia());
        txtMovementDetailImport.setText(Utils.getCurrencyValue(movimientosResponse.getImporte()));

        if (movimientosResponse.getComision() > 0.0) {
            ((View)txtMovementDetailComision.getParent()).setVisibility(View.GONE);
        } else {
            txtMovementDetailComision.setText(Utils.getCurrencyValue(movimientosResponse.getComision()));
        }

        if (movimientosResponse.getIVA() > 0.0) {
            ((View)txtMovementDetailIva.getParent()).setVisibility(View.GONE);
        } else {
            txtMovementDetailIva.setText(Utils.getCurrencyValue(movimientosResponse.getIVA()));
        }
    }


}