package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.dto.MonthsMovementsTab;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.AccountMovementsPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.MovementsPresenter;

import java.util.List;


/**
 * @author Juan Guerra on 27/11/2016.
 */

public class PersonalAccountFragment extends AbstractAdEmFragment<MonthsMovementsTab, ItemMovements<MovimientosResponse>> {

    private MovementsPresenter<MonthsMovementsTab> movementsPresenter;

    public static PersonalAccountFragment newInstance(){
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
    protected void onTabLoaded() {
        tabMonths.getTabAt(tabMonths.getTabCount()-1).select();
    }

    @Override
    protected ViewPagerDataFactory.TABS getTab() {
        return ViewPagerDataFactory.TABS.PERSONAL_ACCOUNT;
    }

    @Override
    protected void getDataForTab(MonthsMovementsTab dataToRequest) {
        movementsPresenter.getRemoteMovementsData(dataToRequest);
    }


    @Override
    public void loadMovementsResult(List<ItemMovements<MovimientosResponse>> movementsList) {

        /*rvAdapter = new RecyclerViewAdapterMovements(getContext(), movements, new RecyclerViewClickListener() {
            @Override
            public void onRecyclerItemClick(View v, int position) {
                SingletonSesion.setIsFlowUser(true);
                startActivity(new Intent(getActivity(), MovementDetailActivity.class).putExtra("movement", movementList.get(position).toString()));
            }
        });
        recyclerView.setAdapter(rvAdapter);*/
    }

}
