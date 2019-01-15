package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.enums.EstatusMovimientoAdquirente;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IDetailAdqView;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.CreateDatailBuilder;
import com.pagatodo.yaganaste.ui_wallet.patterns.facade.FacadeMovements;
import com.pagatodo.yaganaste.ui_wallet.presenter.MovementDetailAdqPresenter;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import java.io.Serializable;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_INSERT_DONGLE_CANCELATION;
import static com.pagatodo.yaganaste.modules.emisor.WalletMainActivity.EVENT_GO_TO_MOV_ADQ;
import static com.pagatodo.yaganaste.modules.emisor.WalletMainActivity.EVENT_GO_TO_SEND_TICKET;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_POR_REMBOLSAR;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;

/**
 * @author Juan Guerra on 12/04/2017.
 */

public class DetailsAdquirenteFragment extends GenericFragment implements
        FacadeMovements.listenerMovements, IDetailAdqView {

    public static final String TRANS_STATUS_RED = "1";

    @BindView(R.id.header_mov)
    LinearLayout header;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.progress_details)
    ProgressLayout progressLayout;

    private View rootView;
    private MovTab movTab;
    private DataMovimientoAdq dataMovimientoAdq;
    private MovementDetailAdqPresenter presenter;
    private FacadeMovements facadeMovements;

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
        return inflater.inflate(R.layout.fragment_detail_movements_emisor, container, false);
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
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if (!adapter.isEnabled() && App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                    Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enabler);
                } else {
                    UI.showAlertDialogCancelar(getContext(), getString(R.string.cancelacion_dialog_message), (dialogInterface, i) -> onEventListener.onEvent(EVENT_GO_INSERT_DONGLE_CANCELATION, dataMovimientoAdq));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getAdquirenteMovementDate(ticket.getFecha()));
        int color = EstatusMovimientoAdquirente.getEstatusById(ticket.getEstatus()).getColor();

        ItemMovements<DataMovimientoAdq> movement = new ItemMovements<>(ticket.getOperacion(),
                ticket.getBancoEmisor(), Double.parseDouble(ticket.getMonto()),
                String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),
                DateUtil.getMonthShortName(calendar),
                color, ticket);

        try {
            CreateDatailBuilder.creatHeaderMovDetail(getContext(), header, movement, true);
            CreateDatailBuilder.createByTypeAdq(getContext(), container, ticket);
        } catch (Exception e) {
            e.printStackTrace();
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