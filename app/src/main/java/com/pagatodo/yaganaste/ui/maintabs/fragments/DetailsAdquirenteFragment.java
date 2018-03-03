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

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.enums.EstatusMovimientoAdquirente;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CANCELADO;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_POR_REMBOLSAR;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_REMBOLSADO;

/**
 * @author Juan Guerra on 12/04/2017.
 */

public class DetailsAdquirenteFragment extends GenericFragment implements View.OnClickListener {

    public static final String TRANS_STATUS_RED = "1";

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

    @BindView(R.id.layoutComision)
    LinearLayout layoutComision;
    @BindView(R.id.txtComisionDescripcion)
    MontoTextView txtComisionDescripcion;

    @BindView(R.id.layoutIVA)
    LinearLayout layoutIVA;

    @BindView(R.id.txtIVADescripcion)
    MontoTextView txtIVADescripcion;

    @BindView(R.id.txt_monto)
    MontoTextView txtMonto;
    @BindView(R.id.imageDetail)
    ImageView imageDetail;
    /*@BindView(R.id.txtMontoDescripcion)
    MontoTextView txtMontoDescripcion;*/
    @BindView(R.id.txtRefernciaDescripcion)
    TextView txtRefernciaDescripcion;
    @BindView(R.id.txtConceptoDescripcion)
    TextView txtConceptoDescripcion;
    @BindView(R.id.txtFechaDescripcion)
    TextView txtFechaDescripcion;
    @BindView(R.id.txtHoraDescripcion)
    TextView txtHoraDescripcion;
    @BindView(R.id.txtAutorizacionDescripcion)
    TextView txtAutorizacionDescripcion;
    @BindView(R.id.txtEstatusDescripcion)
    TextView txtEstatusDescripcion;
    //@BindView(R.id.txtReciboDescripcion)
    //TextView txtReciboDescripcion;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_volver)
    Button btnVolver;
    private View rootView;
    private DataMovimientoAdq dataMovimientoAdq;
    ImageView imageView;
    ImageView imageViewshare;
    ImageView imageViewback;

    public static DetailsAdquirenteFragment newInstance(@NonNull DataMovimientoAdq dataMovimientoAdq) {
        DetailsAdquirenteFragment fragment = new DetailsAdquirenteFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailsActivity.DATA, dataMovimientoAdq);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        setHasOptionsMenu(true);
        imageViewshare = (ImageView) getActivity().findViewById(R.id.deposito_Share);
        imageViewback = (ImageView) getActivity().findViewById(R.id.btn_back);
        imageView = (ImageView) getActivity().findViewById(R.id.imgNotifications);
        if (args != null) {
            dataMovimientoAdq = (DataMovimientoAdq) args.getSerializable(DetailsActivity.DATA);
        } else {
            throw new IllegalCallException(DetailsAdquirenteFragment.class.getSimpleName() + "must be called by newInstance factory method");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_movements_adquirente, container, false);
    }

    public void setVisibilityPrefer(Boolean mBoolean) {
        /*if (mBoolean) {
            imageView.setVisibility(View.VISIBLE);
        } else {*/
        imageView.setVisibility(View.GONE);
        //}
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        setVisibilityPrefer(false);
        setVisibilityPrefershare(true);
        imageViewback.setVisibility(View.VISIBLE);
    }

    public void setVisibilityPrefershare(Boolean mBoolean) {
        if (mBoolean) {
            /**
             * Si la transaccion tiene un estatus de 1 es un movimiento cancelado y no se debe
             * compartir
             */
            if (dataMovimientoAdq.getEstatus().equals(TRANS_STATUS_RED)) {
                imageViewshare.setVisibility(View.GONE);
            } else {
                imageViewshare.setVisibility(View.VISIBLE);
            }
        } else {
            imageViewshare.setVisibility(View.GONE);
        }
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        int color = EstatusMovimientoAdquirente.getEstatusById(dataMovimientoAdq.getEstatus()).getColor();
        layoutMovementTypeColor.setBackgroundColor(ContextCompat.getColor(getContext(), color));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getAdquirenteMovementDate(dataMovimientoAdq.getFecha()));

        txtItemMovDate.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        txtItemMovMonth.setText(DateUtil.getMonthShortName(calendar));
        txtTituloDescripcion.setText(dataMovimientoAdq.getOperacion());
        txtSubTituloDetalle.setText(dataMovimientoAdq.getBancoEmisor());


        txtMonto.setText(StringUtils.getCurrencyValue(dataMovimientoAdq.getMonto()));

        txtMonto.setTextColor(ContextCompat.getColor(getContext(), color));
        txtRefernciaDescripcion.setText(dataMovimientoAdq.getReferencia());
        txtConceptoDescripcion.setText(dataMovimientoAdq.getConcepto());
        txtFechaDescripcion.setText(DateUtil.getBirthDateCustomString(calendar));
        //DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        String[] fecha = dataMovimientoAdq.getFecha().split(" ");
        txtHoraDescripcion.setText(fecha[1] + " hrs");

        txtAutorizacionDescripcion.setText(dataMovimientoAdq.getNoAutorizacion().trim());

        if (dataMovimientoAdq.getEstatus().equals(EstatusMovimientoAdquirente.POR_REEMBOLSAR.getId())) {
            btnCancel.setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.view).setVisibility(View.VISIBLE);
            btnCancel.setOnClickListener(this);
        }

        txtIVADescripcion.setText(StringUtils.getCurrencyValue(dataMovimientoAdq.getMontoAdqComisionIva()).replace("$", "$ "));
        txtComisionDescripcion.setText(StringUtils.getCurrencyValue(dataMovimientoAdq.getMontoAdqComision()).replace("$", "$ "));

        btnVolver.setOnClickListener(this);

        CatalogsDbApi catalogsDbApi = new CatalogsDbApi(getContext());
        String url = catalogsDbApi.getURLIconComercio(dataMovimientoAdq.getBancoEmisor());

        /**
         * Esta validacion es debido a que Piccaso marca un NullPoint si la URL esta vacia, pero
         * Glide permite falla y cargar un PlaceHolder
         */
        if (url != null && !url.isEmpty()) {
            Picasso.with(getContext())
                    .load(url)
                    .placeholder(R.mipmap.logo_ya_ganaste)
                    .into(imageDetail);
        }

        switch (dataMovimientoAdq.getEstatus()) {
            case ESTATUS_CANCELADO:
                txtEstatusDescripcion.setText(getString(R.string.status_cancelado));
                break;
            case ESTATUS_POR_REMBOLSAR:
                txtEstatusDescripcion.setText(getString(R.string.status_por_rembolsar));
                break;
            case ESTATUS_REMBOLSADO:
                txtEstatusDescripcion.setText(getString(R.string.status_rembolsado));
                break;
        }

    }


    @Override
    public void onClick(View v) {
        // TODO: 14/06/2017 Proceso para cancelar venta
        switch (v.getId()) {
            case R.id.btn_volver:
                getActivity().onBackPressed();
                break;
            case R.id.btn_cancel:

                UI.createCustomDialogCancelacionCobro(getString(R.string.cancelacion_dialog_title),
                        getString(R.string.cancelacion_dialog_message),
                        getFragmentManager(),
                        getFragmentTag(),
                        new DialogDoubleActions() {
                            @Override
                            public void actionConfirm(Object... params) {
                                ((DetailsActivity) getActivity()).loadInsertDongleFragment(dataMovimientoAdq);
                            }

                            @Override
                            public void actionCancel(Object... params) {

                            }
                        }, getString(R.string.cancelacion_dialog_aceptar), "");

                break;
        }
    }
}