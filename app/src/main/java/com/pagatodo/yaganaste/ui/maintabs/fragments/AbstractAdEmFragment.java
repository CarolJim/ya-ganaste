package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.pagatodo.view_manager.buttons.ButtonContinue;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;

import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Agentes;
import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.modules.emisor.WalletMainActivity;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesPresenter;
import com.pagatodo.yaganaste.ui.maintabs.controlles.MovementsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.MovementsPresenter;
import com.pagatodo.yaganaste.ui_wallet.adapters.SimpleArrayAdapater;
import com.pagatodo.yaganaste.ui_wallet.presenter.PresenterPaymentFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.GenericTabLayout;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Constants.MOVEMENTS_EMISOR;

/**
 * Android TEAM 28/02/2018
 */

public abstract class AbstractAdEmFragment<T extends IEnumTab, ItemRecycler> extends GenericFragment
        implements MovementsView<ItemRecycler, T>, SwipyRefreshLayout.OnRefreshListener,
        TabLayout.OnTabSelectedListener, OnRecyclerItemClickListener, AdapterView.OnItemSelectedListener, View.OnClickListener{

    public SwipyRefreshLayoutDirection direction;
    public static final String TYPE = "TYPE";
    @BindView(R.id.tab_months)
    GenericTabLayout<T> tabMonths;

    //public List<ItemRecycler> Movements;
    protected MovementsPresenter<T> movementsPresenter;
    public List<List<ItemRecycler>> movementsList;
    private View rootView;
    private int type;
    public HashMap<Integer, Boolean> doubleSwipePosition;

    @BindView(R.id.recycler_movements)
    RecyclerView recyclerMovements;
    @BindView(R.id.swipe_container)
    SwipyRefreshLayout swipeContainer;
    @BindView(R.id.progress_emisor)
    ProgressLayout progress_emisor;
    @BindView(R.id.title)
    StyleTextView title;
    @BindView(R.id.filter)
    LinearLayout filterLinerLayout;
    @BindView(R.id.spnTypeSend)
    Spinner spinner;
    @BindView(R.id.imageViewCustomSpinner)
    AppCompatImageView row;
    @BindView(R.id.buttonContinue)
    ButtonContinue btnContinue;

    protected IFavoritesPresenter favoritesPresenter;
    protected PresenterPaymentFragment paymentPresenter;
    protected List<String> listComercios;
    protected ArrayAdapter<String> spinnerArrayAdapter;
    protected List<Agentes> agentes;
    protected boolean isBussines = false;
    protected int currentTab;
    protected WalletMainActivity activity;

    public AbstractAdEmFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (WalletMainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        this.listComercios = new ArrayList<>();
        agentes = new ArrayList<>();
        try {
            agentes = new DatabaseManager().getAgentes();
            for (Agentes itemAgente : agentes) {
                this.listComercios.add(itemAgente.getNombreNegocio());
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.movementsList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        if (args != null) {
            type = args.getInt(TYPE);
        }
        doubleSwipePosition = new HashMap<>();
        /*spinnerArrayAdapter = new ArrayAdapter<>
                (getContext(), android.R.layout.simple_spinner_item,
                        listComercios);*/
        spinnerArrayAdapter = new SimpleArrayAdapater(getContext(), -1, this.listComercios);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);


    }

    public void activeButton(){
        btnContinue.active();
    }

    public void inactiveButton(){
        btnContinue.inactive();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.adquitente_emisor_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @CallSuper
    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);


        //filterLinerLayout
        swipeContainer.setDirection(type == MOVEMENTS_EMISOR ? SwipyRefreshLayoutDirection.BOTH : SwipyRefreshLayoutDirection.TOP);
        swipeContainer.setOnRefreshListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerMovements.setLayoutManager(layoutManager);
        recyclerMovements.setHasFixedSize(true);
        //recyclerMovements.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.item_offset_mov));
        recyclerMovements.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        movementsPresenter.getPagerData(getTab());
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);
        row.setOnClickListener(view -> {
            spinner.performClick();
        });
        btnContinue.setOnClickListener(this);

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
        //((UpdateBalanceCallback) getParentFragment()).onUpdateBalance();
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        /*if (movementsList.get(tabMonths.getSelectedTabPosition()) == null) {
            showLoader("");
        }*/
        //txtInfoMovements.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
        //showLoader("");
        //movementsPresenter.updateBalance();
        //progress_emisor.setVisivilityImage(View.VISIBLE);
        //progress_emisor.setVisibility(View.VISIBLE);
    }

    protected abstract void onTabLoaded();

    protected abstract ViewPagerDataFactory.TABS getTab();

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        this.currentTab = tab.getPosition();
        if (!doubleSwipePosition.containsKey(tab.getPosition())) {
            swipeContainer.setDirection(SwipyRefreshLayoutDirection.BOTH);
        } else {
            swipeContainer.setDirection(SwipyRefreshLayoutDirection.TOP);
        }
        //movementsPresenter.updateBalance();
        recyclerMovements.setVisibility(View.GONE);

        if (movementsList.get(tab.getPosition()) != null) {
            updateRecyclerData(createAdapter(movementsList.get(tab.getPosition())));
        } else {
            if (isBussines) {
                int idADQ = 0;
                try {
                    if (!agentes.isEmpty()) {
                        idADQ = new DatabaseManager().getIdUsuarioAdqByAgente(agentes.get(0).getNumeroAgente());
                        RequestHeaders.setIdCuentaAdq("" + idADQ);
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            getDataForTab(tabMonths.getCurrentData(tab.getPosition()));
        }
    }

    protected void getDataForTab(T dataToRequest) {
        //Obtener el ID
        movementsPresenter.getRemoteMovementsData(dataToRequest);
    }

    public void updateRecyclerData(final RecyclerView.Adapter adapter) {
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
        //txtInfoMovements.setVisibility(Movements.isEmpty() ? View.VISIBLE : View.GONE);
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
        // UI.showToastShort(error, getActivity());
        //showDialogMesage(error.toString());
        hideLoader();
        UI.showErrorSnackBar(getActivity(), error, Snackbar.LENGTH_SHORT);
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
        performClickOnRecycler(movementsList.get(tabMonths.getSelectedTabPosition()).get(position), position);
    }

    protected abstract void performClickOnRecycler(ItemRecycler itemClicked, int position);

    protected void notifyDataSetChanged() {
        recyclerMovements.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        activity.getRouter().onshowAccountStatus();
    }
}