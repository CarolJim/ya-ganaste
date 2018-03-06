package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import static com.pagatodo.yaganaste.ui_wallet.Behavior.RecyclerItemTouchHelper.RIGHT;

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
        updateRecyclerData(createAdapter(movements), movements);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallbackL = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, getListenerItemTouchLeft(),LEFT);
        new ItemTouchHelper(itemTouchHelperCallbackL).attachToRecyclerView(recyclerMovements);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallbackR = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, getListenerItemTouchLeft(),RIGHT);
        new ItemTouchHelper(itemTouchHelperCallbackR).attachToRecyclerView(recyclerMovements);
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
                    MovimientosResponse movResponse = (MovimientosResponse) adapter.getItem(position);
                    if (direction == ItemTouchHelper.LEFT) {

                        String referService = StringUtils.formatCardToService(movResponse.getReferencia());
                        int idComercio = movResponse.getIdComercio();

                        adapter.updateChange();

                        int tipoEnvio = 0;
                        int tab = 0;
                        boolean isValidMov = true;
                        switch (movResponse.getIdTipoTransaccion()) {
                            case 1:
                                tab = 1;
                                break;
                            case 2:
                                tab = 2;
                                break;
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                                tab = 3;
                                switch (referService.length()) {
                                    case 10:
                                        tipoEnvio = 1;
                                        break;
                                    case 16:
                                        tipoEnvio = 2;
                                        break;
                                    case 18:
                                        tipoEnvio = 3;
                                        break;
                                    default:
                                        tipoEnvio = 0;
                                        break;
                                }

                                break;

                            default:
                                isValidMov = false;
                                break;

                        }
                        if (isValidMov && idComercio != 0) {
                            ComercioResponse comercioResponse = paymentPresenter.getComercioById(idComercio);
                            if (!favoritesPresenter.alreadyExistFavorite(referService, idComercio)) {

                                Intent intent = new Intent(getContext(), AddToFavoritesActivity.class);
                                intent.putExtra(AddToFavoritesActivity.FAV_PROCESS, 1);
                                intent.putExtra(NOMBRE_COMERCIO, comercioResponse.getNombreComercio());
                                intent.putExtra(ID_COMERCIO, idComercio);
                                intent.putExtra(ID_TIPO_COMERCIO, comercioResponse.getIdTipoComercio());
                                intent.putExtra(ID_TIPO_ENVIO, tipoEnvio);
                                intent.putExtra(REFERENCIA, referService);
                                intent.putExtra(CURRENT_TAB_ID, tab);
                                intent.putExtra(DESTINATARIO, adapter.getMovItem(position).getSubtituloDetalle());
                                getActivity().startActivityForResult(intent, REQUEST_CODE_FAVORITES);
                            } else {
                                UI.showAlertDialog(getContext(), "Este movimiento ya es favorito", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                            }
                        } else {
                            UI.showAlertDialog(getContext(), "Este movimiento no puede ser agregado a favoritos", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                        }
                    }
                    if (direction == ItemTouchHelper.RIGHT) {
                        //share
                        adapter.updateChange();
                        ItemMovements item = adapter.getMovItem(position);
                        String message = StringUtils.getCurrencyValue(item.getMonto()) + "\n"
                                + item.getDate() + " " + item.getMonth() + "\n"
                                + item.getTituloDescripcion() + "\n"
                                + item.getSubtituloDetalle() + "\n";
                        IB.IntentShare(getContext(), message);
                    }
                }
            }
        };
    }

}
