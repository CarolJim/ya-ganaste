package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.pagatodo.yaganaste.data.dto.AdquirentePaymentsTab;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui.maintabs.adapters.RecyclerMovementsAdapter;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.AdqPaymentesPresenter;
import java.util.List;

/**
 * @author Juan Guerra on 27/11/2016.
 */

public class PaymentsFragment extends AbstractAdEmFragment<AdquirentePaymentsTab,ItemMovements<DataMovimientoAdq>> {


    public static PaymentsFragment newInstance(){
        PaymentsFragment homeTabFragment = new PaymentsFragment();
        Bundle args = new Bundle();
        homeTabFragment.setArguments(args);
        return homeTabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movementsPresenter = new AdqPaymentesPresenter(this);
    }

    @Override
    protected void onTabLoaded() {
        //tabMonths.setVisibility(View.GONE);
        onTabSelected(tabMonths.getTabAt(0));

    }

    @Override
    protected ViewPagerDataFactory.TABS getTab() {
        return ViewPagerDataFactory.TABS.PAYMENTS;
    }

    @Override
    public void loadMovementsResult(List<ItemMovements<DataMovimientoAdq>> movements) {
        updateRecyclerData(createAdapter(movements), movements);
    }

    @Override
    protected RecyclerView.Adapter createAdapter(List<ItemMovements<DataMovimientoAdq>> movementsList) {

        return new RecyclerMovementsAdapter<DataMovimientoAdq>(getContext(), movementsList, this);
    }

    @Override
    protected void performClickOnRecycler(ItemMovements<DataMovimientoAdq> itemClicked) {
        startActivity(DetailsActivity.createIntent(getActivity(), itemClicked.getMovement()));
    }

}
