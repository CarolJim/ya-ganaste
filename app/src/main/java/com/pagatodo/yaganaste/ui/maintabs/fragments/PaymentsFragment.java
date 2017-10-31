package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.AdquirentePaymentsTab;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui.maintabs.adapters.RecyclerMovementsAdapter;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.AdqPaymentesPresenter;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.MovementsColors.CANCELADO;

/**
 * @author Juan Guerra on 27/11/2016.
 */

public class PaymentsFragment extends AbstractAdEmFragment<AdquirentePaymentsTab, ItemMovements<DataMovimientoAdq>> {


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
    }

    @Override
    protected RecyclerView.Adapter createAdapter(List<ItemMovements<DataMovimientoAdq>> movementsList) {
        return new RecyclerMovementsAdapter<>(movementsList, this);
    }

    @Override
    protected void performClickOnRecycler(ItemMovements<DataMovimientoAdq> itemClicked) {
        this.itemClicked = itemClicked;
        getActivity().startActivityForResult(DetailsActivity.createIntent(getActivity(), itemClicked.getMovement()), CODE_CANCEL);
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
}
