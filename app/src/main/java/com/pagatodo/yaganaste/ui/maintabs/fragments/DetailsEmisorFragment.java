package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.interfaces.enums.MovementsColors;
import com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE.RECARGA;

/**
 * @author Juan Guerra on 12/04/2017.
 */

public class DetailsEmisorFragment extends GenericFragment implements View.OnClickListener {


    @BindView(R.id.layoutMontoTotal)
    LinearLayout layoutMontoTotal;
    @BindView(R.id.txtMontoTotal)
    MontoTextView txtMontoTotal;
    @BindView(R.id.titleMontoTotal)
    StyleTextView titleMontoTotal;

    @BindView(R.id.layoutMontoCompra)
    LinearLayout layoutMontoCompra;
    @BindView(R.id.txtMontoCompra)
    MontoTextView txtMontoCompra;

    @BindView(R.id.layoutComision)
    LinearLayout layoutComision;
    @BindView(R.id.txtComisionDescripcion)
    MontoTextView txtComision;
    @BindView(R.id.titleComisionDescripcion)
    StyleTextView titleComisionDescripcion;

    @BindView(R.id.layoutIVA)
    LinearLayout layoutIVA;
    @BindView(R.id.txtIVA)
    MontoTextView txtIVA;

    @BindView(R.id.layoutReferencia)
    LinearLayout layoutReferencia;
    @BindView(R.id.txtRefernciaDescripcion)
    StyleTextView txtRefernciaDescripcion;
    @BindView(R.id.txtReferenciaTitle)
    TextView txtReferenciaTitle;

    @BindView(R.id.layoutConcepto)
    LinearLayout layoutConcepto;
    @BindView(R.id.txtConceptoDescripcion)
    TextView txtConceptoDescripcion;

    @BindView(R.id.layoutClaveRastreo)
    LinearLayout layoutClaveRastreo;
    @BindView(R.id.txtClaveRastreo)
    StyleTextView txtClaveRastreo;

    @BindView(R.id.layoutNumeroReferencia)
    LinearLayout layoutNumeroReferencia;
    @BindView(R.id.txtNumeroReferencia)
    StyleTextView txtNumeroReferencia;

    @BindView(R.id.layoutFechaDescripcion)
    LinearLayout layoutFechaDescripcion;
    @BindView(R.id.txtFechaDescripcion)
    TextView txtFechaDescripcion;

    @BindView(R.id.layoutHora)
    LinearLayout layoutHora;
    @BindView(R.id.txtHoraDescripcion)
    TextView txtHoraDescripcion;

    @BindView(R.id.layoutAutorizacon)
    LinearLayout layoutAutorizacon;
    @BindView(R.id.txtAutorizacionDescripcion)
    TextView txtAutorizacionDescripcion;

    @BindView(R.id.layout_movement_type_color)
    View layoutMovementTypeColor;

    @BindView(R.id.txt_item_mov_date)
    TextView txtItemMovDate;
    @BindView(R.id.txt_item_mov_month)
    TextView txtItemMovMonth;
    @BindView(R.id.txtTituloDescripcion)
    TextView txtTituloDescripcion;
    @BindView(R.id.txtSubTituloDetalle)
    TextView txtSubTituloDetalle;
    @BindView(R.id.txt_monto)
    MontoTextView txtMonto;

    @BindView(R.id.imageDetail)
    ImageView imageDetail;

    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_volver)
    Button btnVolver;
    private View rootView;
    private MovimientosResponse movimientosResponse;


    public static DetailsEmisorFragment newInstance(@NonNull MovimientosResponse movimientosResponse) {
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
            movimientosResponse = (MovimientosResponse) args.getSerializable(DetailsActivity.DATA);
        } else {
            throw new IllegalCallException(DetailsEmisorFragment.class.getSimpleName() + "must be called by newInstance factory method");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_movements_emisor, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        //loadViews();
        initViews();
    }


    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        initFieldsViews();
        //layoutRecibo.setVisibility(View.GONE);

        String[] date = movimientosResponse.getFechaMovimiento().split(" ");
        ItemMovements item = new ItemMovements<>(movimientosResponse.getDescripcion(), movimientosResponse.getDetalle(),
                movimientosResponse.getTotal(), date[0], date[1],
                MovementsColors.getMovementColorByType(movimientosResponse.getTipoMovimiento()).getColor(),
                movimientosResponse);

        txtMonto.setTextColor(ContextCompat.getColor(getContext(), item.getColor()));
        txtMonto.setText(StringUtils.getCurrencyValue(item.getMonto()));
        if (item.getColor() == android.R.color.transparent) {
            txtMonto.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        }




        //layoutMovementTypeColor.setBackgroundColor(item.getColor());
        layoutMovementTypeColor.setBackgroundResource(item.getColor());
        txtItemMovDate.setText(item.getDate());
        txtItemMovMonth.setText(item.getMonth());
        txtTituloDescripcion.setText(item.getTituloDescripcion());
        txtSubTituloDetalle.setText(item.getSubtituloDetalle());

        //MovementsTab movementsType = MovementsTab.getMovementById(movimientosResponse.getIdTipoTransaccion());
        TipoTransaccionPCODE tipoTransaccion = TipoTransaccionPCODE.getTipoTransaccionById(movimientosResponse.getIdTipoTransaccion());

        Glide.with(this)
                .load(movimientosResponse.getURLImagen())
                .placeholder(R.mipmap.logo_ya_ganaste)
                .into(imageDetail);

        if (tipoTransaccion == RECARGA) {
            txtReferenciaTitle.setText(movimientosResponse.getIdComercio() == 7 ?
                    getString(R.string.iave_pase) : getString(R.string.txt_phone_b));

            txtRefernciaDescripcion.setText(movimientosResponse.getReferencia());
            layoutConcepto.setVisibility(GONE);
        }
        btnVolver.setOnClickListener(this);
    }


    private void initFieldsViews() {
        layoutFechaDescripcion.setVisibility(VISIBLE);
        txtFechaDescripcion.setText(movimientosResponse.getFechaMovimiento());
        layoutHora.setVisibility(VISIBLE);
        txtHoraDescripcion.setText(movimientosResponse.getHoraMovimiento().concat(" hrs"));
        layoutAutorizacon.setVisibility(VISIBLE);
        txtAutorizacionDescripcion.setText(movimientosResponse.getNumAutorizacion().trim());

        switch (TipoTransaccionPCODE.getTipoTransaccionById(movimientosResponse.getIdTipoTransaccion())) {
            case RECARGA://1
                layoutReferencia.setVisibility(VISIBLE);
                txtRefernciaDescripcion.setText(movimientosResponse.getReferencia());

                if (movimientosResponse.getIdComercio() == 7) {
                    txtReferenciaTitle.setText(getString(R.string.txt_tag));

                    layoutComision.setVisibility(VISIBLE);
                    txtComision.setText(StringUtils.getCurrencyValue(movimientosResponse.getComision()));
                    layoutIVA.setVisibility(VISIBLE);
                    txtIVA.setText(StringUtils.getCurrencyValue(movimientosResponse.getIVA()));
                } else {
                    txtReferenciaTitle.setText(getString(R.string.txt_phone));
                }
                break;
            case PAGO_DE_SERVICIO://2
                layoutComision.setVisibility(VISIBLE);
                titleComisionDescripcion.setText(getString(R.string.txt_cargo_servicio));
                txtComision.setText(StringUtils.getCurrencyValue(movimientosResponse.getComision()));
                layoutIVA.setVisibility(VISIBLE);
                txtIVA.setText(StringUtils.getCurrencyValue(movimientosResponse.getIVA()));
                layoutReferencia.setVisibility(VISIBLE);
                txtRefernciaDescripcion.setText(movimientosResponse.getReferencia());
                break;
            case TRASPASO_MISMO_BANCO_CARGO://3
                layoutReferencia.setVisibility(VISIBLE);
                txtReferenciaTitle.setText(getReferencuaTitleType(movimientosResponse.getReferencia()));
                txtRefernciaDescripcion.setText(movimientosResponse.getReferencia());
                layoutConcepto.setVisibility(VISIBLE);
                txtConceptoDescripcion.setText(movimientosResponse.getConcepto());
                break;
            case SPEI_CARGO://4
                layoutComision.setVisibility(VISIBLE);
                txtComision.setText(StringUtils.getCurrencyValue(movimientosResponse.getComision()));
                layoutIVA.setVisibility(VISIBLE);
                txtIVA.setText(StringUtils.getCurrencyValue(movimientosResponse.getIVA()));
                layoutReferencia.setVisibility(VISIBLE);
                txtReferenciaTitle.setText(getReferencuaTitleType(movimientosResponse.getReferencia()));
                txtRefernciaDescripcion.setText(movimientosResponse.getReferencia());
                layoutConcepto.setVisibility(VISIBLE);
                txtConceptoDescripcion.setText(movimientosResponse.getConcepto());
                layoutClaveRastreo.setVisibility(VISIBLE);
                txtClaveRastreo.setText(movimientosResponse.getClaveRastreo());
                layoutNumeroReferencia.setVisibility(VISIBLE);
                txtNumeroReferencia.setText(movimientosResponse.getReferenciaNum());
                break;
            case TRASPASO_MISMO_BANCO_ABONO://5
                layoutConcepto.setVisibility(VISIBLE);
                txtConceptoDescripcion.setText(movimientosResponse.getConcepto());
                break;
            case SPEI_ABONO://6
                layoutConcepto.setVisibility(VISIBLE);
                txtConceptoDescripcion.setText(movimientosResponse.getConcepto());
                layoutClaveRastreo.setVisibility(VISIBLE);
                txtClaveRastreo.setText(movimientosResponse.getClaveRastreo());
                layoutNumeroReferencia.setVisibility(VISIBLE);
                txtNumeroReferencia.setText(movimientosResponse.getReferenciaNum());
                break;

            case SIETE://7

                break;
            case COMPRA:

                break;
            case RETIRO_DE_DINERO_ATM://9
                layoutMontoTotal.setVisibility(VISIBLE);
                titleMontoTotal.setText(getString(R.string.txt_monto_retiro));
                txtMontoTotal.setText(StringUtils.getCurrencyValue(movimientosResponse.getImporte()));
                layoutComision.setVisibility(VISIBLE);
                txtComision.setText(StringUtils.getCurrencyValue(movimientosResponse.getComision()));
                layoutIVA.setVisibility(VISIBLE);
                txtIVA.setText(StringUtils.getCurrencyValue(movimientosResponse.getIVA()));
                break;
            case CONSULTA_ATM://10

                break;
            case CASH_BACK://11
                layoutMontoCompra.setVisibility(VISIBLE);
                txtMontoCompra.setText(StringUtils.getCurrencyValue(movimientosResponse.getCompra()));
                layoutMontoTotal.setVisibility(VISIBLE);
                titleMontoTotal.setText(getString(R.string.txt_monto_retiro));
                txtMontoTotal.setText(StringUtils.getCurrencyValue(movimientosResponse.getImporte()));
                break;
            case COMISION://12
                layoutComision.setVisibility(VISIBLE);
                txtComision.setText(StringUtils.getCurrencyValue(movimientosResponse.getComision()));
                layoutIVA.setVisibility(VISIBLE);
                txtIVA.setText(StringUtils.getCurrencyValue(movimientosResponse.getIVA()));
                break;
            case COBRO_CON_TARJETA_DISPERSION_ADQ://13

                break;
            default:
                break;
        }
    }

    private String getReferencuaTitleType(String ref) {
        String referencia = ref.replaceAll(" ", "").trim();
        int longitud = referencia.length();

        if (longitud == 10) {
            return getString(R.string.txt_phone);
        } else if (longitud == 16) {
            return getString(R.string.tarjeta);
        } else if (longitud == 11) {
            return getString(R.string.txt_cuenta);
        } else if (longitud == 18) {
            return getString(R.string.txt_cable);
        } else {
            return getString(R.string.ferencia_txt);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_volver:
                getActivity().onBackPressed();
                break;
        }
    }
}