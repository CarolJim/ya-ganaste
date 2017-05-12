package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.exceptions.IllegalFactoryParameterException;
import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.controlles.MovementsView;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.MovementsPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.TabPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.TabPresenterImpl;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.GenericTabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Juan Guerra on 27/11/2016.
 */

public abstract class AbstractAdEmFragment<T extends IEnumTab, ItemRecycler> extends GenericFragment
        implements TabsView<T>, MovementsView<ItemRecycler>, SwipeRefreshLayout.OnRefreshListener,
        TabLayout.OnTabSelectedListener, OnRecyclerItemClickListener {

    private List<List<ItemRecycler>> movementsList;

    private View rootView;
    protected GenericTabLayout<T> tabMonths;
    private TabPresenter tabPresenter;

    protected MovementsPresenter<T> movementsPresenter;

    private RecyclerView recyclerMovements;
    private TextView txtInfoMovements;
    private SwipeRefreshLayout swipeContainer;
    public static final int MOVEMENTS = 1;
    public static final int PAYMENTS = 2;

    public static AbstractAdEmFragment newInstance(int type){
        AbstractAdEmFragment instance;
        switch (type){
            case MOVEMENTS:
                instance = PersonalAccountFragment.newInstance();
                break;
            case PAYMENTS:
                instance = PaymentsFragment.newInstance();
                break;

            default:
                throw new IllegalFactoryParameterException(String.valueOf(type));
        }
        Bundle args = new Bundle();
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        tabPresenter = new TabPresenterImpl(this);
        this.movementsList = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.adquitente_emisor_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @CallSuper
    @Override
    public void initViews() {

        recyclerMovements = (RecyclerView) rootView.findViewById(R.id.recycler_movements);
        txtInfoMovements = (TextView) rootView.findViewById(R.id.txt_info_movements);
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);

        swipeContainer.setOnRefreshListener(this);

        recyclerMovements.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerMovements.setHasFixedSize(true);

        this.tabMonths = (GenericTabLayout<T>)rootView.findViewById(R.id.tab_months);
        tabPresenter.getPagerData(getTab());
    }

    @Override
    public void loadViewPager(ViewPagerData<T> viewPagerData) {
        tabMonths.setUpWithoutViewPager(viewPagerData.getTabData());
        tabMonths.addOnTabSelectedListener(this);
        for (int n = 0 ; n < tabMonths.getTabCount() ; n++){
            movementsList.add(null);
        }
        onTabLoaded();
    }

    @Override
    public void onRefresh() {
        getDataForTab(tabMonths.getCurrentData(tabMonths.getSelectedTabPosition()));
    }

    protected abstract void onTabLoaded();

    protected abstract ViewPagerDataFactory.TABS getTab();

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.d("TABS ", "Se selecciono el tab: " + tab.getPosition());

        if (movementsList.get(tab.getPosition()) != null) {
            updateRecyclerData(createAdapter(movementsList.get(tab.getPosition())));
        } else {
            getDataForTab(tabMonths.getCurrentData(tab.getPosition()));
        }
    }

    protected void getDataForTab(T dataToRequest) {
        txtInfoMovements.setVisibility(View.VISIBLE);
        movementsPresenter.getRemoteMovementsData(dataToRequest);
    }

    private void updateRecyclerData(RecyclerView.Adapter adapter) {
        recyclerMovements.setAdapter(adapter);
    }

    protected void updateRecyclerData(RecyclerView.Adapter adapter, List<ItemRecycler> movements) {
        /*List<ItemMovements<MovimientosResponse>> movementsList = new ArrayList<>();
        ItemMovements items = new ItemMovements("a","b",125.36,"c","d",1);
        movementsList.add(items);
        movementsList.add(tabMonths.getSelectedTabPosition(), (ItemMovements<MovimientosResponse>) movementsList);*/
        txtInfoMovements.setVisibility(movementsList.isEmpty() ? View.VISIBLE : View.GONE);
        updateRecyclerData(adapter);
    }

    protected abstract RecyclerView.Adapter createAdapter(List<ItemRecycler> movementsList);

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    //No-op
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    //No-op
    }

    @Override
    public void showError(String error) {
        UI.showToastShort(error, getActivity());
    }

    @Override
    public void showLoader(String message) {
        swipeContainer.setRefreshing(true);
    }

    @Override
    public void hideLoader() {
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onRecyclerItemClick(View v, int position) {
        performClickOnRecycler(movementsList.get(tabMonths.getSelectedTabPosition()).get(position));
    }

    protected abstract void performClickOnRecycler(ItemRecycler itemClicked);
}
