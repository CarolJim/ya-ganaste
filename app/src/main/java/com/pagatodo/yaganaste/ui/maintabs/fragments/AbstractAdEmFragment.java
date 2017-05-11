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

import com.google.android.gms.vision.text.Text;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.data.model.DemoMov;
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
    private RecyclerView.Adapter mAdapter;

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
        ArrayList<DemoMov> values = new ArrayList<>();
        values.add(new DemoMov(R.color.colorAccent,"20","Mar","Venta Con Tarjeta","Bancomer","$2,000",".00"));
        values.add(new DemoMov(R.color.greencolor,"19","Mar","Venta Con Tarjeta 5736","Bancomer","$,1500",".00"));
        values.add(new DemoMov(R.color.greencolor,"19","Mar","Venta Con Tarjeta 6374","Santander","$650",".00"));
        values.add(new DemoMov(R.color.redcolor,"19","Mar","Venta Con Tarjeta 0593 (Cancelada)","Bancomer","$300",".00"));
        values.add(new DemoMov(R.color.redcolor,"18","Mar","Venta Con Tarjeta 1358 (Cancelada)","Santander","$1,000",".00"));
        values.add(new DemoMov(R.color.greencolor,"18","Mar","Venta Con Tarjeta 2579","Banamex","$1,650",".00"));
        mAdapter = new MyAdapter(values);
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
        txtInfoMovements.setVisibility(View.GONE);
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);

//        swipeContainer.setOnRefreshListener(this);

        recyclerMovements.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerMovements.setHasFixedSize(true);
        recyclerMovements.setAdapter(mAdapter);
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
        Log.e("TABS ", "Se selecciono el tab: " + tab.getPosition());

        if (movementsList.get(tab.getPosition()) != null) {
            updateRecyclerData(createAdapter(movementsList.get(tab.getPosition())));
        } else {
            getDataForTab(tabMonths.getCurrentData(tab.getPosition()));
        }
    }

    protected void getDataForTab(T dataToRequest) {
        txtInfoMovements.setVisibility(View.GONE);
        movementsPresenter.getRemoteMovementsData(dataToRequest);
    }

    private void updateRecyclerData(RecyclerView.Adapter adapter) {
        recyclerMovements.setAdapter(adapter);
    }

    protected void updateRecyclerData(RecyclerView.Adapter adapter, List<ItemRecycler> movements) {
        txtInfoMovements.setVisibility(movements.isEmpty() ? View.VISIBLE : View.GONE);
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

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private ArrayList<DemoMov> mdataset;

        public MyAdapter(ArrayList mdataset) {
            this.mdataset = mdataset;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(getContext()).inflate(R.layout.item_movement,parent,false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            mdataset.get(position);

            holder.colorMark.setBackgroundColor(getResources().getColor(mdataset.get(position).getColor()));
            holder.txtDay.setText(mdataset.get(position).getDia());
            holder.txtMes.setText(mdataset.get(position).getMes());
            holder.txtPremio.setText(mdataset.get(position).getConcepto());
            holder.txtDescripcion.setText(mdataset.get(position).getDescripcion());
            holder.txtMonto.setText(mdataset.get(position).getMonto());
            holder.txtMonto.setTextColor(getResources().getColor(mdataset.get(position).getColor()));
            holder.txtCentavos.setText(mdataset.get(position).getCentavos());
            holder.txtCentavos.setTextColor(getResources().getColor(mdataset.get(position).getColor()));
        }

        @Override
        public int getItemCount() {
            return mdataset.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView txtDay, txtMes,txtPremio,txtDescripcion,txtMonto,txtCentavos;
            public View colorMark;
            public ViewHolder(View i) {
                super(i);
                colorMark = (View) i.findViewById(R.id.layout_movement_type_color);
                txtDay = (TextView) i.findViewById(R.id.txt_item_mov_date);
                txtMes = (TextView) i.findViewById(R.id.txt_item_mov_month);
                txtPremio = (TextView) i.findViewById(R.id.txt_premios);
                txtDescripcion = (TextView) i.findViewById(R.id.txt_marca);
                txtMonto = (TextView) i.findViewById(R.id.txt_monto);
                txtCentavos = (TextView) i.findViewById(R.id.txt_item_mov_cents);

                 //mTExtView = (TextView) itemView.findViewById();
            }
        }
    }
}
