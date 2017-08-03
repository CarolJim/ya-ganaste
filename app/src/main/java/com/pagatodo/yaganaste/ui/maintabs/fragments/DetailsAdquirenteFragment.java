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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.interfaces.enums.MovementsColors.APROBADO;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsColors.CANCELADO;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsColors.CARGO;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsColors.PENDIENTE;
import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;

/**
 * @author Juan Guerra on 12/04/2017.
 */

public class DetailsAdquirenteFragment extends GenericFragment implements View.OnClickListener {

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
    @BindView(R.id.txtMontoDescripcion)
    MontoTextView txtMontoDescripcion;
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
    @BindView(R.id.txtReciboDescripcion)
    TextView txtReciboDescripcion;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_volver)
    Button btnVolver;
    private View rootView;
    private DataMovimientoAdq dataMovimientoAdq;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }


    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        //String[] monto = Utils.getCurrencyValue(dataMovimientoAdq.getMonto()).split("\\.");
        int color;
        if (dataMovimientoAdq.isEsCargo()) {
            color = CARGO.getColor();
        } else if (dataMovimientoAdq.isEsReversada()) {
            color = CANCELADO.getColor();
        } else if (dataMovimientoAdq.isEsPendiente()) {
            color = PENDIENTE.getColor();
        } else {
            color = APROBADO.getColor();
        }
        layoutMovementTypeColor.setBackgroundColor(ContextCompat.getColor(getContext(), color));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getAdquirenteMovementDate(dataMovimientoAdq.getFecha()));

        txtItemMovDate.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        txtItemMovMonth.setText(DateUtil.getMonthShortName(calendar));
        txtConceptShort.setText(dataMovimientoAdq.getOperacion());

        txtMarca.setText(dataMovimientoAdq.getBancoEmisor().concat(SPACE).concat(
                dataMovimientoAdq.isEsReversada() ? "- " + App.getInstance().getString(R.string.cancelada) :
                        dataMovimientoAdq.isEsPendiente() ? "- " + App.getInstance().getString(R.string.pendiente) : SPACE));


        txtMonto.setText(dataMovimientoAdq.getMonto());
        txtMonto.setTextColor(ContextCompat.getColor(getContext(), color));
        txtMontoDescripcion.setText(dataMovimientoAdq.getMonto());
        txtRefernciaDescripcion.setText(dataMovimientoAdq.getReferencia());
        txtConceptoDescripcion.setText(dataMovimientoAdq.getOperacion());

        txtFechaDescripcion.setText(DateUtil.getBirthDateCustomString(calendar));
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss", new Locale("es", "ES"));
        txtHoraDescripcion.setText(hourFormat.format(calendar.getTime()) + " hrs");

        txtAutorizacionDescripcion.setText(dataMovimientoAdq.getNoAutorizacion().trim().toString());
        txtReciboDescripcion.setText(dataMovimientoAdq.getNoTicket());

        if (dataMovimientoAdq.isEsAprobada() && !dataMovimientoAdq.isEsCargo() && !dataMovimientoAdq.isEsReversada()) {
            btnCancel.setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.view).setVisibility(View.VISIBLE);
            btnCancel.setOnClickListener(this);
        }

        btnVolver.setOnClickListener(this);

        CatalogsDbApi catalogsDbApi = new CatalogsDbApi(getContext());
        String url = catalogsDbApi.getURLIconComercio(dataMovimientoAdq.getBancoEmisor());

        Glide.with(getContext()).load(url)
                .placeholder(R.mipmap.logo_ya_ganaste)
                .error(R.mipmap.logo_ya_ganaste)
                .dontAnimate().into(imageDetail);
    }


    @Override
    public void onClick(View v) {
        // TODO: 14/06/2017 Proceso para cancelar venta
        switch (v.getId()) {
            case R.id.btn_volver:
                getActivity().onBackPressed();
                break;
            case R.id.btn_cancel:
                ((DetailsActivity) getActivity()).loadCancelFragment(dataMovimientoAdq);
                break;
        }
    }
}