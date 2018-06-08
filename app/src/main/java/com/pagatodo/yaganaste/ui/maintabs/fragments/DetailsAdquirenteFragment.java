package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.enums.EstatusMovimientoAdquirente;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IDetailAdqView;
import com.pagatodo.yaganaste.ui_wallet.patterns.facade.FacadeMovements;
import com.pagatodo.yaganaste.ui_wallet.presenter.MovementDetailAdqPresenter;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_INSERT_DONGLE_CANCELATION;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_TO_MOV_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_TO_SEND_TICKET;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CANCELADO;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_POR_REMBOLSAR;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_REMBOLSADO;

/**
 * @author Juan Guerra on 12/04/2017.
 */

public class DetailsAdquirenteFragment extends GenericFragment implements View.OnClickListener,
        FacadeMovements.listenerMovements, IDetailAdqView {

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
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_volver)
    Button btnVolver;
    @BindView(R.id.up_down)
    ImageView upDown;
    @BindView(R.id.progress_details)
    ProgressLayout progressLayout;

    private View rootView;
    private MovTab movTab;
    private DataMovimientoAdq dataMovimientoAdq;
    private MovementDetailAdqPresenter presenter;
    FacadeMovements facadeMovements;

    public static DetailsAdquirenteFragment newInstance(@NonNull MovTab movTab) {
        DetailsAdquirenteFragment fragment = new DetailsAdquirenteFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailsActivity.DATA, movTab);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        setHasOptionsMenu(true);
        //imageViewshare = (ImageView) getActivity().findViewById(R.id.deposito_Share);
        //imageViewback = (ImageView) getActivity().findViewById(R.id.btn_back);
        //imageView = (ImageView) getActivity().findViewById(R.id.imgNotifications);
        if (args != null) {
            movTab = (MovTab) args.getSerializable(DetailsActivity.DATA);
            dataMovimientoAdq = movTab.getItemMov();
            presenter = new MovementDetailAdqPresenter(this, this, dataMovimientoAdq);
        } else {
            throw new IllegalCallException(DetailsAdquirenteFragment.class.getSimpleName() + "must be called by newInstance factory method");
        }
        facadeMovements = new FacadeMovements(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_movements_adquirente, container, false);
    }

    public void setVisibilityPrefer(Boolean mBoolean) {
        /*if (mBoolean) {
            imageView.setVisibility(View.VISIBLE);
        } else {*/
        //imageView.setVisibility(View.GONE);
        //}
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        setVisibilityPrefer(false);
        setVisibilityPrefershare(true);
        //imageViewback.setVisibility(View.VISIBLE);
    }

    public void setVisibilityPrefershare(Boolean mBoolean) {
        if (mBoolean) {
            /**
             * Si la transaccion tiene un estatus de 1 es un movimiento cancelado y no se debe
             * compartir
             */
            if (dataMovimientoAdq.getEstatus().equals(TRANS_STATUS_RED)) {
                //imageViewshare.setVisibility(View.GONE);
            } else {
                //imageViewshare.setVisibility(View.VISIBLE);
            }
        } else {
            //imageViewshare.setVisibility(View.GONE);
        }
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        presenter.getDetailMovement();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case android.R.id.home:
                this.onEventListener.onEvent(EVENT_GO_TO_MOV_ADQ,this.movTab);
                return true;*/
            case R.id.action_rembolsar:
                if (!dataMovimientoAdq.isEsClosedLoop() && (Integer.parseInt(dataMovimientoAdq.getTipoReembolso()) == 5 || Integer.parseInt(dataMovimientoAdq.getTipoReembolso()) == 4)
                        && dataMovimientoAdq.getEstatus().equalsIgnoreCase(ESTATUS_POR_REMBOLSAR)) {

                    UI.showAlertDialog(getContext(),
                            getResources().getString(R.string.title_dailog_reem),
                            getResources().getString(R.string.message_dialog_reem),
                            R.string.positive_btn_reem,
                            (dialogInterface, i) -> facadeMovements.launchRefund(dataMovimientoAdq));
                } else {
                    UI.showAlertDialog(getContext(),
                            getResources().getString(R.string.message_deseable_reembolso),
                            (dialogInterface, i) -> dialogInterface.dismiss());
                }
                return true;
            case R.id.action_reenviar_ticket:
                this.onEventListener.onEvent(EVENT_GO_TO_SEND_TICKET, this.movTab);
                return true;
            case R.id.action_cancelar_cobro:
                UI.showAlertDialog(getContext(), getString(R.string.cancelacion_dialog_message), (dialogInterface, i) -> onEventListener.onEvent(EVENT_GO_INSERT_DONGLE_CANCELATION, dataMovimientoAdq));
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

    @Override
    public void loadReembolso() {
        UI.showSuccessSnackBar(getActivity(), getResources().getString(R.string.message_succes_reembolso), Snackbar.LENGTH_SHORT);
        this.onEventListener.onEvent(EVENT_GO_TO_MOV_ADQ, this.movTab);
    }

    @Override
    public void showLoader(String message) {
        progressLayout.setTextMessage(message);
        progressLayout.setVisivilityImage(View.VISIBLE);
        progressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(Object error) {
        UI.showErrorSnackBar(getActivity(), "Hubo un error al reembolsar intentalo de nuevo", Snackbar.LENGTH_SHORT);
    }

    @Override
    public void printTicket(DataMovimientoAdq ticket) {
        int color = EstatusMovimientoAdquirente.getEstatusById(ticket.getEstatus()).getColor();
        layoutMovementTypeColor.setBackgroundColor(ContextCompat.getColor(getContext(), color));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getAdquirenteMovementDate(ticket.getFecha()));

        txtItemMovDate.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        txtItemMovMonth.setText(DateUtil.getMonthShortName(calendar));
        txtTituloDescripcion.setText(ticket.getOperacion());
        txtSubTituloDetalle.setText(ticket.getBancoEmisor());


        txtMonto.setText(StringUtils.getCurrencyValue(ticket.getMonto()));

        txtMonto.setTextColor(ContextCompat.getColor(getContext(), R.color.adadad));
        txtRefernciaDescripcion.setText(ticket.getReferencia());
        txtConceptoDescripcion.setText(ticket.getConcepto());
        txtFechaDescripcion.setText(DateUtil.getBirthDateCustomString(calendar));
        //DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        String[] fecha = ticket.getFecha().split(" ");
        txtHoraDescripcion.setText(fecha[1] + " hrs");

        txtAutorizacionDescripcion.setText(ticket.getNoAutorizacion().trim());

        if (ticket.getEstatus().equals(EstatusMovimientoAdquirente.POR_REEMBOLSAR.getId())) {


            btnCancel.setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.view).setVisibility(View.VISIBLE);
            btnCancel.setOnClickListener(this);
        }

        txtIVADescripcion.setText(StringUtils.getCurrencyValue(ticket.getComisionIva()).replace("$", "$ "));
        txtComisionDescripcion.setText(StringUtils.getCurrencyValue(ticket.getComision()).replace("$", "$ "));

        btnVolver.setOnClickListener(this);

        String url = null;
        try {
            url = new DatabaseManager().getUrlLogoComercio(ticket.getBancoEmisor());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

        switch (ticket.getEstatus()) {
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

        if (color == R.color.redColorNegativeMovements) {
            upDown.setBackgroundResource(R.drawable.down_red);
        }

        if (color == R.color.greenColorPositiveMovements) {
            upDown.setBackgroundResource(R.drawable.upadq);
        }

        if (color == R.color.colorAccent) {
            upDown.setBackgroundResource(R.drawable.ico_idle);
        }

        if (color == R.color.redColorNegativeMovements) {
            upDown.setBackgroundResource(R.drawable.down);
        }
    }

    @Override
    public void onError(String message) {
        UI.showAlertDialog2("Error", message, getString(R.string.title_aceptar), getActivity(), new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                DetailsAdquirenteFragment.this.onEventListener.onEvent(EVENT_GO_TO_MOV_ADQ, DetailsAdquirenteFragment.this.movTab);
            }

            @Override
            public void actionCancel(Object... params) {
                DetailsAdquirenteFragment.this.onEventListener.onEvent(EVENT_GO_TO_MOV_ADQ, DetailsAdquirenteFragment.this.movTab);
            }
        });
    }

    public static class MovTab implements Serializable {
        private int currentTab;
        private DataMovimientoAdq itemMov;

        public int getCurrentTab() {
            return currentTab;
        }

        public void setCurrentTab(int currentTab) {
            this.currentTab = currentTab;
        }

        public DataMovimientoAdq getItemMov() {
            return itemMov;
        }

        public void setItemMov(DataMovimientoAdq itemMov) {
            this.itemMov = itemMov;
        }
    }
}