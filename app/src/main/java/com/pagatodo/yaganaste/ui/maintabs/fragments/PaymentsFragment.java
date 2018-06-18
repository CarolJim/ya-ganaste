package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.AdquirentePaymentsTab;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.ui.maintabs.adapters.RecyclerMovementsAdapter;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.AdqPaymentesPresenter;
import com.pagatodo.yaganaste.ui_wallet.behavior.RecyclerItemTouchHelper;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ly.count.android.sdk.Countly;

import static com.pagatodo.yaganaste.interfaces.enums.MovementsColors.CANCELADO;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_DETAIL_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.behavior.RecyclerItemTouchHelper.LEFT_AD;
import static com.pagatodo.yaganaste.utils.Recursos.CONNECTION_TYPE;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_POR_REMBOLSAR;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_MOVS_ADQ;

public class PaymentsFragment extends AbstractAdEmFragment<AdquirentePaymentsTab, ItemMovements<DataMovimientoAdq>> {


    RecyclerView.Adapter currentAdapter;

    public static final int CODE_CANCEL = 821;
    public static final int RESULT_CANCEL_OK = 281;
    public static String CURRENT_TAB = "CURRENT_TAB";
    private ItemMovements<DataMovimientoAdq> itemClicked;
    private int currentTab;


    public static PaymentsFragment newInstance() {
        return new PaymentsFragment();
    }

    public static PaymentsFragment newInstance(int currentTab) {
        PaymentsFragment paymentsFragment = new PaymentsFragment();
        Bundle args = new Bundle();
        args.putInt(CURRENT_TAB, currentTab);
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
}
