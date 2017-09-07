package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.dto.MonthsMovementsTab;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui.maintabs.adapters.RecyclerMovementsAdapter;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.AccountMovementsPresenter;

import java.util.List;

/**
 * @author Juan Guerra on 27/11/2016.
 */

public class PersonalAccountFragment extends AbstractAdEmFragment<MonthsMovementsTab, ItemMovements<MovimientosResponse>> {

    RecyclerView.Adapter currentAdapter;

    public static PersonalAccountFragment newInstance() {
        PersonalAccountFragment homeTabFragment = new PersonalAccountFragment();
        Bundle args = new Bundle();
        homeTabFragment.setArguments(args);
        return homeTabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movementsPresenter = new AccountMovementsPresenter(this);
    }

    @Override
    public void initViews() {
        super.initViews();
        tabMonths.setEnabled(false);
    }

    @Override
    protected void onTabLoaded() {
        tabMonths.getTabAt(tabMonths.getTabCount() - 1).select();
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
            //showLoader("");
            getDataForTab(tabMonths.getCurrentData(tabMonths.getSelectedTabPosition()));
        }

    }

    @Override
    public void loadMovementsResult(List<ItemMovements<MovimientosResponse>> movementsList) {

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
        }
    }

    @Override
    protected void updateRecyclerData(RecyclerView.Adapter adapter, List<ItemMovements<MovimientosResponse>> movements) {
        //txtInfoMovements.setVisibility(movements.isEmpty() ? View.VISIBLE : View.GONE);
        updateRecyclerData(adapter);
    }

    @Override
    protected RecyclerView.Adapter createAdapter(final List<ItemMovements<MovimientosResponse>> movementsList) {
        return new RecyclerMovementsAdapter<>(movementsList, this);
    }

    @Override
    protected void performClickOnRecycler(ItemMovements<MovimientosResponse> itemClicked) {
        startActivity(DetailsActivity.createIntent(getActivity(), itemClicked.getMovement()));
    }
}
