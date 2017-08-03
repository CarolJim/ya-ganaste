package com.pagatodo.yaganaste.ui.maintabs.fragments;

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
import com.pagatodo.yaganaste.data.model.SingletonUser;
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

    @BindView(R.id.layoutMonto)
    LinearLayout layoutMonto;
    @BindView(R.id.txtMontoDescripcion)
    MontoTextView txtMontoDescripcion;

    @BindView(R.id.layoutMontoCompra)
    LinearLayout layoutMontoCompra;
    @BindView(R.id.txtMontoCompra)
    MontoTextView txtMontoCompra;

    @BindView(R.id.layoutMontoRetiro)
    LinearLayout layoutMontoRetiro;
    @BindView(R.id.txtMontoRetiro)
    MontoTextView txtMontoRetiro;

    @BindView(R.id.layoutComision)
    LinearLayout layoutComision;
    @BindView(R.id.txtComisionDescripcion)
    MontoTextView txtComision;

    @BindView(R.id.layoutIVA)
    LinearLayout layoutIVA;
    @BindView(R.id.txtIVA)
    MontoTextView txtIVA;

    @BindView(R.id.layoutCargoServicio)
    LinearLayout layoutCargoServicio;
    @BindView(R.id.txtCargoServicio)
    MontoTextView txtCargoServicio;

    @BindView(R.id.layoutTelefono)
    LinearLayout layoutTelefono;
    @BindView(R.id.txtTelefono)
    StyleTextView txtTelefono;

    @BindView(R.id.layoutReferencia)
    LinearLayout layoutReferencia;
    @BindView(R.id.txtRefernciaDescripcion)
    StyleTextView txtRefernciaDescripcion;
    @BindView(R.id.txtReferenciaTitle)
    TextView txtReferenciaTitle;

    @BindView(R.id.layoutFrom)
    LinearLayout layoutFrom;
    @BindView(R.id.txtFrom)
    StyleTextView txtFrom;

    @BindView(R.id.layoutCuentaOdenante)
    LinearLayout layoutCuentaOdenante;
    @BindView(R.id.txtCuentaOdenante)
    StyleTextView txtCuentaOdenante;

    @BindView(R.id.layoutSendTo)
    LinearLayout layoutSendTo;
    @BindView(R.id.txtSendToDescripcion)
    TextView txtSendTo;

    @BindView(R.id.layoutCuentaBeneficiario)
    LinearLayout layoutCuentaBeneficiario;
    @BindView(R.id.txtCuentaBeneficiario)
    StyleTextView txtCuentaBeneficiario;

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


    //@BindView(R.id.layoutRecibo)
    //LinearLayout layoutRecibo;
    //@BindView(R.id.txtReciboDescripcion)
    //TextView txtReciboDescripcion;

    @BindView(R.id.layout_movement_type_color)
    View layoutMovementTypeColor;

    @BindView(R.id.txt_item_mov_date)
    TextView txtItemMovDate;
    @BindView(R.id.txt_item_mov_month)
    TextView txtItemMovMonth;
    @BindView(R.id.txt_premios)
    TextView txtConceptShort;
    @BindView(R.id.txt_marca)
    TextView txtMarca;
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
        ItemMovements item = new ItemMovements<>(movimientosResponse.getDetalle(), movimientosResponse.getDescripcion(),
                movimientosResponse.getTotal(), date[0], date[1],
                MovementsColors.getMovementColorByType(movimientosResponse.getTipoMovimiento()).getColor(),
                movimientosResponse);

        txtMonto.setTextColor(ContextCompat.getColor(getContext(), item.getColor()));
        txtMonto.setText(StringUtils.getCurrencyValue(item.getMonto()));
        if (item.getColor() == android.R.color.transparent) {
            txtMonto.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        }

        layoutMovementTypeColor.setBackgroundColor(item.getColor());
        txtItemMovDate.setText(item.getDate());
        txtItemMovMonth.setText(item.getMonth());
        txtConceptShort.setText(item.getPremio());
        txtMarca.setText(item.getMarca());

        //MovementsTab movementsType = MovementsTab.getMovementById(movimientosResponse.getIdTipoTransaccion());
        TipoTransaccionPCODE tipoTransaccion = TipoTransaccionPCODE.getTipoTransaccionById(movimientosResponse.getIdTipoTransaccion());

        Glide.with(this).load(movimientosResponse.getURLImagen())
                .placeholder(R.mipmap.logo_ya_ganaste).into(imageDetail);

        if (tipoTransaccion == RECARGA) {
            txtReferenciaTitle.setText(movimientosResponse.getIdComercio() == 7 ?
                    getString(R.string.iave_pase) : getString(R.string.txt_phone_b));

            txtRefernciaDescripcion.setText(movimientosResponse.getReferencia());
            layoutConcepto.setVisibility(GONE);
        }

        /*if (tipoTransaccion == TipoTransaccionPCODE.PAGO_DE_SERVICIO) {
            layoutComision.setVisibility(VISIBLE);
            txtComision.setText(String.format("%.2f", movimientosResponse.getComision()).trim().replace(",", "."));
            txtRefernciaDescripcion.setText(movimientosResponse.getReferencia());
            layoutConcepto.setVisibility(GONE);
        }*/


        /*if (tipoTransaccion == TipoTransaccionPCODE.ENVIO_DE_DINERO_MISMO_BANCO || tipoTransaccion == TipoTransaccionPCODE.ENVIO_DE_DINERO_OTRO_BANCO
                || tipoTransaccion == TipoTransaccionPCODE.RECEPCION_DE_DINERO_MISMO_BANCO || tipoTransaccion == TipoTransaccionPCODE.RECEPCION_DE_DINERO_OTRO_BANCO) {

            if (!movimientosResponse.getBeneficiario().isEmpty()) {
                layoutSendTo.setVisibility(VISIBLE);
                txtSendTo.setText(movimientosResponse.getBeneficiario());
            }

            txtReferenciaTitle.setText(getString(R.string.cuenta));
            String ref = movimientosResponse.getReferencia().replaceAll(" ", "");
            if (ref.isEmpty()) {
                ref = SingletonUser.getInstance().getDataUser().getUsuario().getCuentas().get(0).getTarjeta();
            }
            ref = ref.substring(ref.length() - 4, ref.length());
            txtRefernciaDescripcion.setText(getString(R.string.mask_card) + " " + ref);
            txtConceptoDescripcion.setText(movimientosResponse.getConcepto());
        }*/

        btnVolver.setOnClickListener(this);
    }


    private void initFieldsViews() {
        layoutMonto.setVisibility(VISIBLE);
        txtMontoDescripcion.setText(StringUtils.getCurrencyValue(movimientosResponse.getTotal()));

        layoutFechaDescripcion.setVisibility(VISIBLE);
        txtFechaDescripcion.setText(movimientosResponse.getFechaMovimiento());
        layoutHora.setVisibility(VISIBLE);
        txtHoraDescripcion.setText(movimientosResponse.getHoraMovimiento() + " hrs");
        layoutAutorizacon.setVisibility(VISIBLE);
        txtAutorizacionDescripcion.setText(movimientosResponse.getNumAutorizacion().trim().toString());

        switch (TipoTransaccionPCODE.getTipoTransaccionById(movimientosResponse.getIdTipoTransaccion())) {
            case RECARGA:
                if (movimientosResponse.getIdComercio() == 7) {
                    layoutIVA.setVisibility(VISIBLE);
                    txtIVA.setText(StringUtils.getCurrencyValue(movimientosResponse.getIVA()));
                    layoutCargoServicio.setVisibility(VISIBLE);
                    txtCargoServicio.setText(StringUtils.getCurrencyValue(movimientosResponse.getComision()));
                    layoutReferencia.setVisibility(VISIBLE);
                    txtRefernciaDescripcion.setText(movimientosResponse.getReferencia());
                } else {
                    layoutTelefono.setVisibility(VISIBLE);
                    txtTelefono.setText(movimientosResponse.getReferencia());
                }
                break;
            case PAGO_DE_SERVICIO:
                layoutIVA.setVisibility(VISIBLE);
                txtIVA.setText(StringUtils.getCurrencyValue(movimientosResponse.getIVA()));
                layoutCargoServicio.setVisibility(VISIBLE);
                txtCargoServicio.setText(StringUtils.getCurrencyValue(movimientosResponse.getComision()));
                layoutReferencia.setVisibility(VISIBLE);
                txtRefernciaDescripcion.setText(movimientosResponse.getReferencia());
                break;
            case ENVIO_DE_DINERO_MISMO_BANCO:
                layoutReferencia.setVisibility(VISIBLE);
                txtRefernciaDescripcion.setText(movimientosResponse.getReferencia());
                layoutSendTo.setVisibility(VISIBLE);
                txtSendTo.setText(movimientosResponse.getBeneficiario());
                layoutConcepto.setVisibility(VISIBLE);
                txtConceptoDescripcion.setText(movimientosResponse.getConcepto());
                break;
            case ENVIO_DE_DINERO_OTRO_BANCO:
                layoutComision.setVisibility(VISIBLE);
                txtComision.setText(StringUtils.getCurrencyValue(movimientosResponse.getComision()));
                layoutIVA.setVisibility(VISIBLE);
                txtIVA.setText(StringUtils.getCurrencyValue(movimientosResponse.getIVA()));
                layoutSendTo.setVisibility(VISIBLE);
                txtSendTo.setText(movimientosResponse.getBeneficiario());
                layoutConcepto.setVisibility(VISIBLE);
                txtConceptoDescripcion.setText(movimientosResponse.getConcepto());
                layoutClaveRastreo.setVisibility(VISIBLE);
                txtClaveRastreo.setText(movimientosResponse.getClaveRastreo());
                layoutNumeroReferencia.setVisibility(VISIBLE);
                txtNumeroReferencia.setText(movimientosResponse.getReferenciaNum());
                break;
            case RECEPCION_DE_DINERO_MISMO_BANCO:
                layoutReferencia.setVisibility(VISIBLE);
                txtRefernciaDescripcion.setText(movimientosResponse.getReferencia());
                layoutFrom.setVisibility(VISIBLE);
                txtFrom.setText(movimientosResponse.getBeneficiario());
                layoutConcepto.setVisibility(VISIBLE);
                txtConceptoDescripcion.setText(movimientosResponse.getConcepto());
                break;
            case RECEPCION_DE_DINERO_OTRO_BANCO:
                layoutFrom.setVisibility(VISIBLE);
                txtFrom.setText(movimientosResponse.getBeneficiario());
                layoutConcepto.setVisibility(VISIBLE);
                txtConceptoDescripcion.setText(movimientosResponse.getConcepto());
                layoutClaveRastreo.setVisibility(VISIBLE);
                txtClaveRastreo.setText(movimientosResponse.getClaveRastreo());
                layoutNumeroReferencia.setVisibility(VISIBLE);
                txtNumeroReferencia.setText(movimientosResponse.getReferenciaNum());
                break;
            case YA_GANASTE_PROMO_CODES:
                layoutMontoCompra.setVisibility(VISIBLE);
                layoutMontoRetiro.setVisibility(VISIBLE);
                layoutComision.setVisibility(VISIBLE);
                layoutIVA.setVisibility(VISIBLE);
                layoutCargoServicio.setVisibility(VISIBLE);
                layoutTelefono.setVisibility(VISIBLE);
                layoutReferencia.setVisibility(VISIBLE);
                layoutFrom.setVisibility(VISIBLE);
                layoutCuentaOdenante.setVisibility(VISIBLE);
                layoutSendTo.setVisibility(VISIBLE);
                layoutCuentaBeneficiario.setVisibility(VISIBLE);
                layoutConcepto.setVisibility(VISIBLE);
                layoutClaveRastreo.setVisibility(VISIBLE);
                layoutNumeroReferencia.setVisibility(VISIBLE);
                break;
            case COMPRA:
                layoutConcepto.setVisibility(VISIBLE);
                txtConceptoDescripcion.setText(movimientosResponse.getDetalle());
                break;
            case RETIRO_DE_DINERO_ATM:
                layoutMontoRetiro.setVisibility(VISIBLE);
                txtMontoRetiro.setText(StringUtils.getCurrencyValue(movimientosResponse.getImporte()));
                layoutComision.setVisibility(VISIBLE);
                txtComision.setText(StringUtils.getCurrencyValue(movimientosResponse.getComision()));
                layoutIVA.setVisibility(VISIBLE);
                txtIVA.setText(StringUtils.getCurrencyValue(movimientosResponse.getIVA()));
                layoutConcepto.setVisibility(VISIBLE);
                txtConceptoDescripcion.setText(movimientosResponse.getDetalle());
                break;
            case CONSULTA_ATM:
                layoutIVA.setVisibility(VISIBLE);
                txtIVA.setText(StringUtils.getCurrencyValue(movimientosResponse.getIVA()));
                break;
            case CASH_BACK:
                layoutMontoCompra.setVisibility(VISIBLE);
                txtMontoCompra.setTag(StringUtils.getCurrencyValue(movimientosResponse.getCompra()));
                layoutMontoRetiro.setVisibility(VISIBLE);
                txtMontoRetiro.setText(StringUtils.getCurrencyValue(movimientosResponse.getImporte()));
                layoutConcepto.setVisibility(VISIBLE);
                txtConceptoDescripcion.setText(movimientosResponse.getDetalle());
                break;
            case COMISION:
                layoutIVA.setVisibility(VISIBLE);
                txtIVA.setText(StringUtils.getCurrencyValue(movimientosResponse.getIVA()));
                break;
            case COBRO_CON_TARJETA_DISPERSION_ADQ:
                layoutMontoCompra.setVisibility(VISIBLE);
                layoutMontoRetiro.setVisibility(VISIBLE);
                layoutComision.setVisibility(VISIBLE);
                layoutIVA.setVisibility(VISIBLE);
                layoutCargoServicio.setVisibility(VISIBLE);
                layoutTelefono.setVisibility(VISIBLE);
                layoutReferencia.setVisibility(VISIBLE);
                layoutFrom.setVisibility(VISIBLE);
                layoutCuentaOdenante.setVisibility(VISIBLE);
                layoutSendTo.setVisibility(VISIBLE);
                layoutCuentaBeneficiario.setVisibility(VISIBLE);
                layoutConcepto.setVisibility(VISIBLE);
                layoutClaveRastreo.setVisibility(VISIBLE);
                layoutNumeroReferencia.setVisibility(VISIBLE);
                break;
            default:
                break;
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