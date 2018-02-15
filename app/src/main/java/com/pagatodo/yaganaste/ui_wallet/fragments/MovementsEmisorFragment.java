package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarMovimientosMesResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.maintabs.adapters.RecyclerMovementsAdapter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.AccountMovementsPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.WalletPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.WalletPresenterImpl;
import com.pagatodo.yaganaste.ui_wallet.views.MovementsEmisorView;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovementsEmisorFragment extends SupportFragment implements MovementsEmisorView, OnRecyclerItemClickListener {

    @BindView(R.id.progressGIF)
    ProgressLayout progressLayout;
    @BindView(R.id.recycler_movements)
    RecyclerView recyclerMovements;

    WalletPresenter walletPresenter;
    public static MovementsEmisorFragment newInstance() {
        return new MovementsEmisorFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.walletPresenter = new WalletPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movements_generic, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void initViews() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerMovements.setLayoutManager(layoutManager);
        recyclerMovements.setHasFixedSize(true);
        walletPresenter.getRemoteMovementsData();
    }

    @Override
    public void showProgress() {
        progressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void setError() {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMovementsResult(List<ItemMovements<MovimientosResponse>> movementsList) {
        List<ItemMovements<MovimientosResponse>> actualList = null;
        /*int tabPosition = tabMonths.getSelectedTabPosition();


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
        }*/

        RecyclerMovementsAdapter movementsAdapter = new RecyclerMovementsAdapter<>(movementsList, this);
        recyclerMovements.setAdapter(movementsAdapter);
    }

    @Override
    public void onRecyclerItemClick(View v, int position) {

    }
}
