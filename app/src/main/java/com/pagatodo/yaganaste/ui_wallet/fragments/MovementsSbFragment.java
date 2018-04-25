package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosSbResponse;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.MovementsHeadAdpater;
import com.pagatodo.yaganaste.ui_wallet.interactors.MovementsInteractorImpl;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IMovementsSbInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IMovementsView;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovementsSbFragment extends SupportFragment implements IMovementsView {

    @BindView(R.id.title_fragment)
    StyleTextView title;
    @BindView(R.id.rcv_superItem)
    RecyclerView recyclerView;

    private IMovementsSbInteractor presenter;
    private View rootView;

    private MovementsHeadAdpater adpater;
    private LinearLayoutManager mLayoutManager;

    public static MovementsSbFragment newInstance() {
        return new MovementsSbFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MovementsInteractorImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_movements_sb, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        this.adpater = new MovementsHeadAdpater(getContext());
        this.mLayoutManager = new LinearLayoutManager(getContext());
        this.recyclerView.setLayoutManager(this.mLayoutManager);
        this.recyclerView.setAdapter(adpater);
        presenter.getMovements();
    }

    @Override
    public void loadMovementsResult(List<MovimientosSbResponse> movementsList) {
        if (movementsList.isEmpty()){
            title.setText(getResources().getString(R.string.no_movimientos));
        } else {
            this.adpater.setList(movementsList);
            this.adpater.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailed(String error) {
        UI.showErrorSnackBar(getActivity(),error, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void setError(String message) {
        UI.showErrorSnackBar(getActivity(),message, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showProgress() {
        String load = "Cargando...";
        onEventListener.onEvent(EVENT_SHOW_LOADER,load);
    }

    @Override
    public void hideProgress() {
        onEventListener.onEvent(EVENT_HIDE_LOADER,null);
    }
}
