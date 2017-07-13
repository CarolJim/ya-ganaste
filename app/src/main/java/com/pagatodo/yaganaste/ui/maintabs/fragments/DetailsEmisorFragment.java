package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.ColorInt;
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
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.interfaces.enums.MovementsColors;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Juan Guerra on 12/04/2017.
 */

public class DetailsEmisorFragment extends GenericFragment implements View.OnClickListener {

    private View rootView;

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


    @BindView(R.id.layoutComision)
    LinearLayout layoutComision;
    @BindView(R.id.txtComisionDescripcion)
    MontoTextView txtComision;
    @BindView(R.id.layoutSendTo)
    LinearLayout layoutSendTo;
    @BindView(R.id.txtSendToDescripcion)
    TextView txtSendTo;
    @BindView(R.id.layoutConcepto)
    LinearLayout layoutConcepto;
    @BindView(R.id.layoutRecibo)
    LinearLayout layoutRecibo;

    @BindView(R.id.txtReferenciaTitle)
    TextView txtReferenciaTitle;

    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_volver)
    Button btnVolver;


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
        return inflater.inflate(R.layout.fragment_detail_movements, container, false);
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
        layoutRecibo.setVisibility(View.GONE);
        @ColorInt int movementColor = ContextCompat.getColor(getActivity(),
                MovementsColors.getMovementColorByType(movimientosResponse.getTipoMovimiento()).getColor());
        layoutMovementTypeColor.setBackgroundColor(movementColor);
        txtMonto.setTextColor(movementColor);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getEmisorMovementDate(movimientosResponse.getFechaMovimiento()));
        txtItemMovDate.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        txtItemMovMonth.setText(DateUtil.getMonthShortName(calendar));
        txtConceptShort.setText(movimientosResponse.getDetalle());

        MovementsTab movementsType = MovementsTab.getMovementById(movimientosResponse.getIdTipoTransaccion());

        txtConceptShort.setText(movementsType.getDescription());
        txtMarca.setText(movementsType == MovementsTab.TAB1 || movementsType == MovementsTab.TAB2 ?
                movimientosResponse.getComercio() :
                movimientosResponse.getDetalle() + ", " + movimientosResponse.getComercio());


        txtMonto.setText(String.format("%.2f", movimientosResponse.getImporte()).trim().replace(",", "."));

        Glide.with(this).load(movimientosResponse.getURLImagen())
                .placeholder(R.mipmap.logo_ya_ganaste).into(imageDetail);

        txtMontoDescripcion.setText(String.format("%.2f", movimientosResponse.getImporte()).trim().replace(",", "."));

        if (movementsType == MovementsTab.TAB1) {
            txtReferenciaTitle.setText(movimientosResponse.getIdComercio() == 7 ?
                    getString(R.string.iave_pase) : getString(R.string.txt_phone_b));

            txtRefernciaDescripcion.setText(movimientosResponse.getReferencia());
            layoutConcepto.setVisibility(View.GONE);
        }

        if (movementsType == MovementsTab.TAB2) {
            layoutComision.setVisibility(View.VISIBLE);
            txtComision.setText(String.format("%.2f", movimientosResponse.getComision()).trim().replace(",", "."));
            txtRefernciaDescripcion.setText(movimientosResponse.getReferencia());
            layoutConcepto.setVisibility(View.GONE);
        }


        if (movementsType == MovementsTab.TAB3) {
            layoutSendTo.setVisibility(View.VISIBLE);
            txtSendTo.setText(movimientosResponse.getBeneficiario());
            txtReferenciaTitle.setText(getString(R.string.cuenta));
            String ref = movimientosResponse.getReferencia().replaceAll(" ", "");
            ref = ref.substring(ref.length() - 4, ref.length());
            txtRefernciaDescripcion.setText(getString(R.string.mask_card) + " " + ref);
            txtConceptoDescripcion.setText(movimientosResponse.getConcepto());
        }


        txtFechaDescripcion.setText(movimientosResponse.getFechaMovimiento());
        txtHoraDescripcion.setText(movimientosResponse.getHoraMovimiento() + " hrs");
        txtAutorizacionDescripcion.setText(movimientosResponse.getNumAutorizacion().trim().toString());
        btnVolver.setOnClickListener(this);
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