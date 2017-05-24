package com.pagatodo.yaganaste.ui.maintabs.fragments.deposits;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataLocalizaSucursal;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.maintabs.managers.DepositsManager;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Jordan on 19/05/2017.
 */

public class DepositsFragment extends SupportFragment implements DepositsManager {

    private View rootView;

    public static DepositsFragment newInstance() {
        DepositsFragment depositsFragment = new DepositsFragment();
        Bundle args = new Bundle();
        depositsFragment.setArguments(args);
        return depositsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deposito, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, this.rootView);
        loadFragment(DepositsDataFragment.newInstance(), Direction.NONE, false);
    }

    public DepositsManager getDepositManager() {
        return this;
    }

    @Override
    public void onTapButton() {
        removeLastFragment();
        ((TabActivity) getActivity()).showProgressLayout("Cargando");
        loadFragment(DepositsMapFragment.newInstance(), Direction.FORDWARD, false);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void onBtnBackPress() {
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        onEventListener.onEvent(TabActivity.EVENT_SHOW_MAIN_TAB, null);
        if (fragments != null && fragments.get(0) instanceof DepositsMapFragment) {
            removeLastFragment();
            loadFragment(DepositsDataFragment.newInstance(), Direction.BACK, false);
        } else if (fragments != null && fragments.get(0) instanceof DepositsDataFragment) {
            ((TabActivity) getActivity()).goHome();
        } else if (fragments != null && fragments.get(0) instanceof DepositSucursalDetail) {
            removeLastFragment();
            loadFragment(DepositsMapFragment.newInstance(), Direction.BACK, false);
        }
    }

    public void loadDescriptionFragment(DataLocalizaSucursal sucursal) {
        removeLastFragment();
        loadFragment(DepositSucursalDetail.newInstance(sucursal), Direction.FORDWARD, false);
    }
}
