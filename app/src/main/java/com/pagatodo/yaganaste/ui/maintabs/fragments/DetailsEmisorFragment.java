package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.interfaces.enums.MovementsColors;
import com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.patterns.Builder;
import com.pagatodo.yaganaste.ui_wallet.patterns.Container;
import com.pagatodo.yaganaste.ui_wallet.patterns.CreateDatailBuilder;
import com.pagatodo.yaganaste.ui_wallet.patterns.DetailBulder;
import com.pagatodo.yaganaste.ui_wallet.pojos.TextData;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE.RECARGA;
import static com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE.REEMBOLSO_ADQUIRIENTE;


public class DetailsEmisorFragment extends GenericFragment {

    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.header_mov)
    LinearLayout header;

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
        setHasOptionsMenu(true);
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
        initViews();
    }


    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);

        String[] date = movimientosResponse.getFechaMovimiento().split(" ");
        TipoTransaccionPCODE tipoTransaccion = TipoTransaccionPCODE.getTipoTransaccionById(movimientosResponse.getIdTipoTransaccion());
        ItemMovements item;
        if (tipoTransaccion != REEMBOLSO_ADQUIRIENTE) {
            item = new ItemMovements<>(movimientosResponse.getDescripcion(), movimientosResponse.getDetalle(),
                    movimientosResponse.getTotal(), date[0], date[1],
                    MovementsColors.getMovementColorByType(movimientosResponse.getTipoMovimiento()).getColor(),
                    movimientosResponse);
        } else {
            item = new ItemMovements<>(movimientosResponse.getDetalle(), movimientosResponse.getConcepto(),
                    movimientosResponse.getTotal(), date[0], date[1],
                    MovementsColors.getMovementColorByType(movimientosResponse.getTipoMovimiento()).getColor(),
                    movimientosResponse);
        }

        CreateDatailBuilder.creatHeaderMovDetail(getContext(),header,item);
        CreateDatailBuilder.createByType(getContext(),container,movimientosResponse);

    }

}