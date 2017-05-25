package com.pagatodo.yaganaste.ui.maintabs.fragments.deposits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataLocalizaSucursal;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.maintabs.managers.DepositsManager;
import com.pagatodo.yaganaste.utils.UI;

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
        if (UtilsNet.isOnline(getContext())) {
            removeLastFragment();
            ((TabActivity) getActivity()).showProgressLayout("Cargando");
            loadFragment(DepositsMapFragment.newInstance(), Direction.FORDWARD, false);
        } else {
            showErrorMessage(getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void showErrorMessage(String message) {
        UI.createSimpleCustomDialog("", message, fragmentManager, getFragmentTag());
    }

    @Override
    public void onBtnBackPress() {
        List<Fragment> fragments = getChildFragmentManager().getFragments();

        if (fragments != null && fragments.get(0) instanceof DepositsMapFragment) {
            removeLastFragment();
            loadFragment(DepositsDataFragment.newInstance(), Direction.BACK, false);
            onEventListener.onEvent(TabActivity.EVENT_SHOW_MAIN_TAB, null);
        } else if (fragments != null && fragments.get(0) instanceof DepositsDataFragment) {
            ((TabActivity) getActivity()).goHome();
            onEventListener.onEvent(TabActivity.EVENT_SHOW_MAIN_TAB, null);
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