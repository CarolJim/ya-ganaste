package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosSbResponse;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui_wallet.interactors.MovementsInteractorImpl;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IMovementsSbInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IMovementsView;
import com.pagatodo.yaganaste.utils.UI;

import java.util.List;

import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovementsSbFragment extends SupportFragment implements IMovementsView {

    private IMovementsSbInteractor presenter;

    private View rootView;

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
        presenter.getMovements();
    }

    @Override
    public void loadMovementsResult(List<MovimientosSbResponse> movementsList) {
        Log.d("LOS MOVIMIENTOS", "" + movementsList.size());
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
        onEventListener.onEvent(EVENT_SHOW_LOADER,null);
    }

    @Override
    public void hideProgress() {
        onEventListener.onEvent(EVENT_HIDE_LOADER,null);
    }
}
