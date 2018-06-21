package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.AdquirentePaymentsTab;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui.maintabs.adapters.RecyclerMovementsAdapter;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.AdqPaymentesPresenter;
import com.pagatodo.yaganaste.ui_wallet.behavior.RecyclerItemTouchHelper;
import com.pagatodo.yaganaste.utils.UI;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.interfaces.enums.MovementsColors.CANCELADO;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_DETAIL_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.behavior.RecyclerItemTouchHelper.LEFT_AD;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_POR_REMBOLSAR;

public class PaymentsFragment extends AbstractAdEmFragment<AdquirentePaymentsTab, ItemMovements<DataMovimientoAdq>> {


    RecyclerView.Adapter currentAdapter;

    public static final int CODE_CANCEL = 821;
    public static final int RESULT_CANCEL_OK = 281;
    public static String CURRENT_TAB = "CURRENT_TAB";
    public static String IS_BUSSINES = "IS_BUSSINES";
    private ItemMovements<DataMovimientoAdq> itemClicked;
    private int currentTab;
    private boolean isBussines = false;


    public static PaymentsFragment newInstance() {
        return new PaymentsFragment();
    }

    public static PaymentsFragment newInstance(int currentTab, boolean isMyBussines) {
        PaymentsFragment paymentsFragment = new PaymentsFragment();
        Bundle args = new Bundle();
        args.putInt(CURRENT_TAB, currentTab);
        args.putBoolean(IS_BUSSINES, isMyBussines);
        paymentsFragment.setArguments(args);
        return paymentsFragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movementsPresenter = new AdqPaymentesPresenter(this);
        this.currentTab = -1;
        if (getArguments() != null) {
            this.currentTab = getArguments().getInt(CURRENT_TAB);
            this.isBussines = getArguments().getBoolean(IS_BUSSINES);
        }

    }

    @Override
    protected void onTabLoaded() {
        //tabMonths.setVisibility(View.GONE);
        if (this.currentTab != -1) {
            tabMonths.getTabAt(this.currentTab).select();
        } else {
            tabMonths.getTabAt(tabMonths.getTabCount() - 1).select();
        }

        if (this.isBussines){
            filterLinerLayout.setVisibility(View.VISIBLE);
        } else {
            filterLinerLayout.setVisibility(View.GONE);
        }

    }

    @Override
    protected ViewPagerDataFactory.TABS getTab() {
        return ViewPagerDataFactory.TABS.PAYMENTS;
    }

    @Override
    public void loadMovementsResult(List<ItemMovements<DataMovimientoAdq>> movements) {
        this.currentAdapter = createAdapter(movements);
        updateRecyclerData(this.currentAdapter, movements);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallbackL = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, getListenerItemTouchLeft(), LEFT_AD);
        new ItemTouchHelper(itemTouchHelperCallbackL).attachToRecyclerView(recyclerMovements);
    }

    @Override
    protected RecyclerView.Adapter createAdapter(List<ItemMovements<DataMovimientoAdq>> movementsList) {
        return new RecyclerMovementsAdapter<>(movementsList, this, true);
    }

    @Override
    protected void performClickOnRecycler(ItemMovements<DataMovimientoAdq> itemClicked, int pos) {
        this.itemClicked = itemClicked;
        //startActivity(DetailsActivity.createIntent(getActivity(), itemClicked.getMovement()));
        //getActivity().startActivityForResult(DetailsActivity.createIntent(getActivity(), itemClicked.getMovement()), CODE_CANCEL);
        //UI.showToastShort(itemClicked.getMovement().getTransactionIdentity(),getContext());
        DetailsAdquirenteFragment.MovTab movTab = new DetailsAdquirenteFragment.MovTab();
        movTab.setCurrentTab(tabMonths.getSelectedTabPosition());
        movTab.setItemMov(itemClicked.getMovement());
        this.currentTab = tabMonths.getSelectedTabPosition();
        onEventListener.onEvent(EVENT_GO_DETAIL_ADQ, movTab);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        showLoader("");
        itemClicked.setColor(CANCELADO.getColor());
        itemClicked.getMovement().setEsReversada(true);
        itemClicked.setSubtituloDetalle(getString(R.string.cancelada));
        notifyDataSetChanged();
        onRefresh(null);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        try {
            if ((direction.equals(SwipyRefreshLayoutDirection.BOTTOM))) {
                swipeContainer.setRefreshing(false);
            } else {
                try {
                    super.onRefresh(SwipyRefreshLayoutDirection.BOTTOM);
                    getDataForTab(tabMonths.getCurrentData(tabMonths.getSelectedTabPosition()));
                } catch (Exception e) {
                    hideLoader();
                }
            }
        } catch (Exception e) {
            hideLoader();

        }
    }

    private RecyclerItemTouchHelper.RecyclerItemTouchHelperListener getListenerItemTouchLeft() {
        return new RecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int index) {
                if (viewHolder instanceof RecyclerMovementsAdapter.RecyclerViewHolderMovements) {
                    int position = viewHolder.getAdapterPosition();
                    RecyclerMovementsAdapter adapter = (RecyclerMovementsAdapter) currentAdapter;
                    final DataMovimientoAdq movResponse = (DataMovimientoAdq) adapter.getItem(position);
                    adapter.updateChange();
                    if (SingletonUser.getInstance().getDataUser().getUsuario().getRoles().get(0).getIdRol() != 129 &&
                            movResponse.getEstatus().equalsIgnoreCase(ESTATUS_POR_REMBOLSAR)) {
                        if (direction == ItemTouchHelper.LEFT) {
                            UI.showAlertDialog(getContext(),
                                    getResources().getString(R.string.title_dailog_reem),
                                    getResources().getString(R.string.message_dialog_reem),
                                    R.string.positive_btn_reem,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            movementsPresenter.sendReembolso(movResponse);
                                        }
                                    });
                        }
                    } else {
                        UI.showAlertDialog(getContext(), getResources().getString(R.string.message_deseable_reembolso), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                    }


                }
            }
        };
    }

    @Override
    public void loadReembolso() {
        UI.showSuccessSnackBar(getActivity(), getResources().getString(R.string.message_succes_reembolso), Snackbar.LENGTH_SHORT);
        getDataForTab(tabMonths.getCurrentData(tabMonths.getSelectedTabPosition()));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("TABSELECT",i + "");
        int idADQ = 0;

        try {
            idADQ = new DatabaseManager().getIdUsuarioAdqByAgente(agentes.get(i).getNumeroAgente());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RequestHeaders.setIdCuentaAdq("" + idADQ);

        getDataForTab(tabMonths.getCurrentData(currentTab));

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
