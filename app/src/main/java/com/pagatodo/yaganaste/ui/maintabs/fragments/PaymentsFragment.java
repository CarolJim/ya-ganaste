package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.AdquirentePaymentsTab;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.AddToFavoritesActivity;
import com.pagatodo.yaganaste.ui.maintabs.adapters.RecyclerMovementsAdapter;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.AdqPaymentesPresenter;
import com.pagatodo.yaganaste.ui_wallet.Behavior.RecyclerItemTouchHelper;
import com.pagatodo.yaganaste.utils.IB;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.MovementsColors.CANCELADO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.DESTINATARIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_TIPO_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_TIPO_ENVIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.NOMBRE_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.REFERENCIA;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.REQUEST_CODE_FAVORITES;
import static com.pagatodo.yaganaste.ui_wallet.Behavior.RecyclerItemTouchHelper.LEFT;
import static com.pagatodo.yaganaste.ui_wallet.Behavior.RecyclerItemTouchHelper.LEFT_AD;
import static com.pagatodo.yaganaste.ui_wallet.Behavior.RecyclerItemTouchHelper.RIGHT;
import static com.pagatodo.yaganaste.ui_wallet.Behavior.RecyclerItemTouchHelper.RIGHT_AD;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_POR_REMBOLSAR;

/**
 * @author Juan Guerra on 27/11/2016.
 */

public class PaymentsFragment extends AbstractAdEmFragment<AdquirentePaymentsTab, ItemMovements<DataMovimientoAdq>> {


    RecyclerView.Adapter currentAdapter;

    public static final int CODE_CANCEL = 821;
    public static final int RESULT_CANCEL_OK = 281;
    private ItemMovements<DataMovimientoAdq> itemClicked;


    public static PaymentsFragment newInstance() {
        PaymentsFragment homeTabFragment = new PaymentsFragment();
        Bundle args = new Bundle();
        homeTabFragment.setArguments(args);
        return homeTabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movementsPresenter = new AdqPaymentesPresenter(this);
    }

    @Override
    protected void onTabLoaded() {
        //tabMonths.setVisibility(View.GONE);
        tabMonths.getTabAt(tabMonths.getTabCount() - 1).select();

    }

    @Override
    protected ViewPagerDataFactory.TABS getTab() {
        return ViewPagerDataFactory.TABS.PAYMENTS;
    }

    @Override
    public void loadMovementsResult(List<ItemMovements<DataMovimientoAdq>> movements) {
        this.currentAdapter = createAdapter(movements);
        updateRecyclerData(this.currentAdapter, movements);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallbackL = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, getListenerItemTouchLeft(),LEFT_AD);
        new ItemTouchHelper(itemTouchHelperCallbackL).attachToRecyclerView(recyclerMovements);


    }

    @Override
    protected RecyclerView.Adapter createAdapter(List<ItemMovements<DataMovimientoAdq>> movementsList) {
        return new RecyclerMovementsAdapter<>(movementsList, this);
    }

    @Override
    protected void performClickOnRecycler(ItemMovements<DataMovimientoAdq> itemClicked) {
        this.itemClicked = itemClicked;
        startActivity(DetailsActivity.createIntent(getActivity(), itemClicked.getMovement()));
        //getActivity().startActivityForResult(DetailsActivity.createIntent(getActivity(), itemClicked.getMovement()), CODE_CANCEL);
        //UI.showToastShort(itemClicked.getMovement().getTransactionIdentity(),getContext());

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
                    if (!movResponse.isClosedLoop() && (Integer.parseInt(movResponse.getIdTipoRembolso()) == 5 || Integer.parseInt(movResponse.getIdTipoRembolso()) == 4)
                            && movResponse.getEstatus().equalsIgnoreCase(ESTATUS_POR_REMBOLSAR)){
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
        UI.showSuccessSnackBar(getActivity(),getResources().getString(R.string.message_succes_reembolso), Snackbar.LENGTH_SHORT);
        getDataForTab(tabMonths.getCurrentData(tabMonths.getSelectedTabPosition()));
    }
}
