package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.dto.MonthsMovementsTab;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE;
import com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity;
import com.pagatodo.yaganaste.ui.addfavorites.presenters.FavoritesPresenter;
import com.pagatodo.yaganaste.ui.maintabs.adapters.RecyclerMovementsAdapter;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.AccountMovementsPresenter;
import com.pagatodo.yaganaste.modules.emisor.WalletMainActivity;
import com.pagatodo.yaganaste.ui_wallet.behavior.RecyclerItemTouchHelper;
import com.pagatodo.yaganaste.ui_wallet.presenter.PresenterPaymentFragment;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.UtilsIntents;

import java.util.List;
import java.util.Objects;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.DESTINATARIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_TIPO_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.ID_TIPO_ENVIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.NOMBRE_COMERCIO;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.REFERENCIA;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.REQUEST_CODE_FAVORITES;
import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.FAVORITE_PROCESS;
import static com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment.CURRENT_TAB;
import static com.pagatodo.yaganaste.modules.emisor.WalletMainActivity.EVENT_GO_DETAIL_EMISOR;
import static com.pagatodo.yaganaste.ui_wallet.behavior.RecyclerItemTouchHelper.LEFT;
import static com.pagatodo.yaganaste.ui_wallet.behavior.RecyclerItemTouchHelper.RIGHT;
import static com.pagatodo.yaganaste.utils.Constants.NEW_FAVORITE_FROM_OPERATION;


public class PersonalAccountFragment extends AbstractAdEmFragment<MonthsMovementsTab, ItemMovements<MovimientosResponse>>{

    public static final String TAG = PersonalAccountFragment.class.getSimpleName();
    private int currentTab;
    RecyclerView.Adapter currentAdapter;

    public static PersonalAccountFragment newInstance(int currentTab) {
        PersonalAccountFragment personalAccountFragment = new PersonalAccountFragment();
        Bundle args = new Bundle();
        args.putInt(CURRENT_TAB, currentTab);
        personalAccountFragment.setArguments(args);
        return personalAccountFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.currentTab = -1;
        this.movementsPresenter = new AccountMovementsPresenter(this);
        favoritesPresenter = new FavoritesPresenter();
        paymentPresenter = new PresenterPaymentFragment();
        if (getArguments() != null) {
            this.currentTab = getArguments().getInt(CURRENT_TAB);
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        //tabMonths.setEnabled(false);
    }

    @Override
    protected void onTabLoaded() {
        filterLinerLayout.setVisibility(View.GONE);
        if(!movementsList.isEmpty()){
            btnContinue.active();
            ItemTouchHelper.SimpleCallback itemTouchHelperCallbackL =
                    new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, getListenerItemTouchLeft(), LEFT);
            new ItemTouchHelper(itemTouchHelperCallbackL).attachToRecyclerView(recyclerMovements);

            ItemTouchHelper.SimpleCallback itemTouchHelperCallbackR =
                    new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, getListenerItemTouchLeft(), RIGHT);
            new ItemTouchHelper(itemTouchHelperCallbackR).attachToRecyclerView(recyclerMovements);
        } else {
            btnContinue.inactive();
        }

        if (this.currentTab != 0) {
            tabMonths.getTabAt(this.currentTab).select();
        } else {
            tabMonths.getTabAt(tabMonths.getTabCount() - 1).select();
        }
    }

    @Override
    protected ViewPagerDataFactory.TABS getTab() {
        return ViewPagerDataFactory.TABS.PERSONAL_ACCOUNT;
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        super.onRefresh(direction);
        this.direction = direction;
        List<ItemMovements<MovimientosResponse>> actualList = this.movementsList.get(tabMonths.getSelectedTabPosition());
        if (actualList != null) {
            String itemId = "";
            int listSize = actualList.size();
            if (direction == SwipyRefreshLayoutDirection.TOP) {
                if (listSize > 0) {
                    itemId = actualList.get(0).getMovement().getIdMovimiento();
                }
            } else {
                if (listSize > 0) {
                    itemId = actualList.get(listSize - 1).getMovement().getIdMovimiento();
                }
            }
            movementsPresenter.getRemoteMovementsData(tabMonths.getCurrentData(tabMonths.getSelectedTabPosition()), direction, itemId);
        } else {
            getDataForTab(tabMonths.getCurrentData(tabMonths.getSelectedTabPosition()));
        }
    }

    @Override
    public void loadMovementsResult(List<ItemMovements<MovimientosResponse>> movementsList) {
        if (!movementsList.isEmpty()){
            this.activeButton();
        } else this.inactiveButton();
        List<ItemMovements<MovimientosResponse>> actualList = null;
        int tabPosition = tabMonths.getSelectedTabPosition();
        try {
            actualList = this.movementsList.get(tabPosition);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (actualList != null && actualList.size() > 0) {

            if (movementsList.size() > 0) {


                if (direction.equals(SwipyRefreshLayoutDirection.TOP)) {
                    this.movementsList.get(tabPosition).addAll(0, movementsList);
                } else {
                    this.movementsList.get(tabPosition).addAll(actualList.size(), movementsList);
                }
                currentAdapter.notifyDataSetChanged();
            } else {

                if (direction.equals(SwipyRefreshLayoutDirection.BOTTOM)) {
                    swipeContainer.setDirection(SwipyRefreshLayoutDirection.TOP);
                    doubleSwipePosition.put(tabPosition, false);
                }
            }
        } else {

            this.movementsList.set(tabPosition, movementsList);
            currentAdapter = createAdapter(this.movementsList.get(tabPosition));
            updateRecyclerData(currentAdapter, movementsList);
            ItemTouchHelper.SimpleCallback itemTouchHelperCallbackL = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, getListenerItemTouchLeft(), LEFT);
            new ItemTouchHelper(itemTouchHelperCallbackL).attachToRecyclerView(recyclerMovements);

            ItemTouchHelper.SimpleCallback itemTouchHelperCallbackR = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, getListenerItemTouchLeft(), RIGHT);
            new ItemTouchHelper(itemTouchHelperCallbackR).attachToRecyclerView(recyclerMovements);
        }
    }

    @Override
    protected void updateRecyclerData(RecyclerView.Adapter adapter, List<ItemMovements<MovimientosResponse>> movements) {
        updateRecyclerData(adapter);
    }

    @Override
    protected RecyclerView.Adapter createAdapter(final List<ItemMovements<MovimientosResponse>> movementsList) {
        return new RecyclerMovementsAdapter<>(movementsList, this);
    }

    @Override
    protected void performClickOnRecycler(ItemMovements<MovimientosResponse> itemClicked, int pos) {
        this.currentTab = tabMonths.getSelectedTabPosition();
        ((WalletMainActivity)getActivity()).tabMonthMov = currentTab;
        onEventListener.onEvent(EVENT_GO_DETAIL_EMISOR, itemClicked.getMovement());
    }

    private RecyclerItemTouchHelper.RecyclerItemTouchHelperListener getListenerItemTouchLeft() {
        return (viewHolder, direction, index) -> {
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
                        Comercio comercioResponse = paymentPresenter.getComercioById(idComercio);
                        if (!favoritesPresenter.alreadyExistFavorite(referService, idComercio)) {
                            Intent intent = new Intent(getContext(), FavoritesActivity.class);
                            intent.putExtra(FAVORITE_PROCESS, NEW_FAVORITE_FROM_OPERATION);
                            intent.putExtra(NOMBRE_COMERCIO, comercioResponse.getNombreComercio());
                            intent.putExtra(ID_COMERCIO, idComercio);
                            intent.putExtra(ID_TIPO_COMERCIO, comercioResponse.getIdTipoComercio());
                            intent.putExtra(ID_TIPO_ENVIO, tipoEnvio);
                            intent.putExtra(REFERENCIA, referService);
                            intent.putExtra(CURRENT_TAB_ID, tab);
                            intent.putExtra(DESTINATARIO, adapter.getMovItem(position).getSubtituloDetalle());
                            //Objects.requireNonNull(getActivity()).startActivityForResult(intent, REQUEST_CODE_FAVORITES);
                            startActivity(intent);
                        } else {
                            UI.showAlertDialog(getContext(), "Este movimiento ya es favorito",
                                    (dialogInterface, i) -> dialogInterface.dismiss());
                        }
                    } else {
                        UI.showAlertDialog(getContext(), "Este movimiento no puede ser agregado a favoritos",
                                (dialogInterface, i) -> dialogInterface.dismiss());
                    }
                }
                if (direction == ItemTouchHelper.RIGHT) {
                    //share
                    adapter.updateChange();
                    //ItemMovements item = adapter.getMovItem(position);
                    MovimientosResponse movlResponse = (MovimientosResponse) adapter.getItem(position);
                    TipoTransaccionPCODE tipoTransaccion = TipoTransaccionPCODE.getTipoTransaccionById(movlResponse.getIdTipoTransaccion());
                    String message = "¡Hola!\n" + "Estos son los datos del movimiento de tu cuenta ya ganaste\n\n" +
                            "Concepto: " + tipoTransaccion.getName() + "\n" +
                            "Fecha: " + movlResponse.getFechaMovimiento() + "\n" +
                            "Hora:" + movlResponse.getHoraMovimiento() + "\n" +
                            "Autorización: " + movlResponse.getNumAutorizacion();
                    UtilsIntents.IntentShare(getContext(), message);
                }
            }
        };
    }

    @Override
    public void loadReembolso() {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
