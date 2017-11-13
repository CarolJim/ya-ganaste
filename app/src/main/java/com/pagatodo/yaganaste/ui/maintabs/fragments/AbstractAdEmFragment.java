package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.exceptions.IllegalFactoryParameterException;
import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.controlles.MovementsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.MovementsPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.GenericTabLayout;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.R2.color.colorLoaderAlpha;


/**
 * @author Juan Guerra on 27/11/2016.
 * @author Jordan on 08/08/2017
 */

public abstract class AbstractAdEmFragment<T extends IEnumTab, ItemRecycler> extends GenericFragment
        implements MovementsView<ItemRecycler, T>, SwipyRefreshLayout.OnRefreshListener,
        TabLayout.OnTabSelectedListener, OnRecyclerItemClickListener {

    public SwipyRefreshLayoutDirection direction;
    public static final int MOVEMENTS = 1;
    public static final int PAYMENTS = 2;
    public static final String TYPE = "TYPE";
    @BindView(R.id.tab_months)
    GenericTabLayout<T> tabMonths;

    //public List<ItemRecycler> movements;
    protected MovementsPresenter<T> movementsPresenter;
    public List<List<ItemRecycler>> movementsList;
    private View rootView;
    private int type;
    public HashMap<Integer, Boolean> doubleSwipePosition;

    @BindView(R.id.recycler_movements)
    RecyclerView recyclerMovements;
    //@BindView(R.id.txt_info_movements)
    //TextView txtInfoMovements;
    @BindView(R.id.swipe_container)
    SwipyRefreshLayout swipeContainer;
    @BindView(R.id.progress_emisor)
    ProgressLayout progress_emisor;


    public static AbstractAdEmFragment newInstance(int type) {
        AbstractAdEmFragment instance;
        switch (type) {
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
        args.putInt(TYPE, type);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        this.movementsList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            type = args.getInt(TYPE);
        }
        doubleSwipePosition = new HashMap<>();
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
        ButterKnife.bind(this, rootView);
        //txtInfoMovements.setVisibility(View.GONE);
        //txtInfoMovements.setOnClickListener(this);
        progress_emisor.setVisibility(View.VISIBLE);
        //progress_emisor.setBackgroundColor(getResources().getColor(R.color.colorLoaderAlpha));
        swipeContainer.setDirection(type == MOVEMENTS ? SwipyRefreshLayoutDirection.BOTH : SwipyRefreshLayoutDirection.TOP);
        swipeContainer.setOnRefreshListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerMovements.setLayoutManager(layoutManager);
        recyclerMovements.setHasFixedSize(true);
        movementsPresenter.getPagerData(getTab());
    }

    @Override
    public void loadViewPager(ViewPagerData<T> viewPagerData) {
        tabMonths.setUpWithoutViewPager(viewPagerData.getTabData());
        tabMonths.addOnTabSelectedListener(this);
        for (int n = 0; n < tabMonths.getTabCount(); n++) {
            movementsList.add(null);
        }
        onTabLoaded();
    }

    @Override
    public void updateBalance() {
        ((UpdateBalanceCallback) getParentFragment()).onUpdateBalance();
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        /*if (movementsList.get(tabMonths.getSelectedTabPosition()) == null) {
            showLoader("");
        }*/
        //txtInfoMovements.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
        showLoader("");
        movementsPresenter.updateBalance();
        progress_emisor.setVisivilityImage(View.VISIBLE);
        progress_emisor.setVisibility(View.VISIBLE);
    }

    protected abstract void onTabLoaded();

    protected abstract ViewPagerDataFactory.TABS getTab();

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (!doubleSwipePosition.containsKey(tab.getPosition())) {
            swipeContainer.setDirection(SwipyRefreshLayoutDirection.BOTH);
        } else {
            swipeContainer.setDirection(SwipyRefreshLayoutDirection.TOP);
        }
        movementsPresenter.updateBalance();
        recyclerMovements.setVisibility(View.GONE);

        if (movementsList.get(tab.getPosition()) != null) {
            updateRecyclerData(createAdapter(movementsList.get(tab.getPosition())));
        } else {
            showLoader("");
            getDataForTab(tabMonths.getCurrentData(tab.getPosition()));
        }
    }

    protected void getDataForTab(T dataToRequest) {
        //txtInfoMovements.setVisibility(View.GONE);
        movementsPresenter.getRemoteMovementsData(dataToRequest);
    }

    public void updateRecyclerData(RecyclerView.Adapter adapter) {
        /*if (adapter.getItemCount() < 1) {
            //txtInfoMovements.setVisibility(View.VISIBLE);
        } else {
            //txtInfoMovements.setVisibility(View.GONE);
        }*/
        recyclerMovements.setAdapter(adapter);
        recyclerMovements.setVisibility(View.VISIBLE);

    }

    protected void updateRecyclerData(RecyclerView.Adapter adapter, List<ItemRecycler> movements) {
        movementsList.set(tabMonths.getSelectedTabPosition(), movements);
        //txtInfoMovements.setVisibility(movements.isEmpty() ? View.VISIBLE : View.GONE);
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
        hideLoader();
    }

    @Override
    public void showLoader(String message) {
        progress_emisor.setTextMessage(message);
        progress_emisor.setVisivilityImage(View.VISIBLE);
        progress_emisor.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        swipeContainer.setRefreshing(false);
        progress_emisor.setVisibility(View.GONE);
    }

    @Override
    public void onRecyclerItemClick(View v, int position) {
        performClickOnRecycler(movementsList.get(tabMonths.getSelectedTabPosition()).get(position));
    }

    protected abstract void performClickOnRecycler(ItemRecycler itemClicked);

    protected void notifyDataSetChanged() {
        recyclerMovements.getAdapter().notifyDataSetChanged();
    }

    public interface UpdateBalanceCallback {
        void onUpdateBalance();
    }

    /*@Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_info_movements) {
            onRefresh(SwipyRefreshLayoutDirection.TOP);
        }
    }*/
}