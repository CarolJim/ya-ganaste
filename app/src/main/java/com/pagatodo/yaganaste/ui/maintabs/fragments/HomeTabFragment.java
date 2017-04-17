package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.HomeFragmentPresenter;
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;
import com.pagatodo.yaganaste.utils.customviews.NoSwipeViewPager;
import com.pagatodo.yaganaste.utils.customviews.yaganasteviews.TabLayoutEmAd;


/**
 * @author Juan Guerra on 10/11/2016.
 */

public class HomeTabFragment extends GenericFragment implements TabsView, TabLayoutEmAd.AdquirenteCallback {

    private NoSwipeViewPager pagerAdquirente;
    private View rootView;
    private HomeFragmentPresenter homeFragmentPresenter;
    private TabLayoutEmAd tabLayoutEmAd;

    public static HomeTabFragment newInstance(){
        HomeTabFragment homeTabFragment = new HomeTabFragment();
        Bundle args = new Bundle();
        homeTabFragment.setArguments(args);
        return homeTabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.homeFragmentPresenter = new HomeFragmentPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_main_tab_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        tabLayoutEmAd = (TabLayoutEmAd) rootView.findViewById(R.id.tab_em_adq);
        pagerAdquirente = (NoSwipeViewPager) rootView.findViewById(R.id.pager_adquirente);
        homeFragmentPresenter.getPagerData(ViewPagerDataFactory.TABS.HOME_FRAGMENT);
        tabLayoutEmAd.setAdquirenteCallback(this);
    }

    @Override
    public void loadViewPager(ViewPagerData viewPagerData) {
        pagerAdquirente.setAdapter(new GenericPagerAdapter<>(getActivity(), getChildFragmentManager(), viewPagerData.getFragmentList(), viewPagerData.getTabData()));
        tabLayoutEmAd.setUpWithViewPager(pagerAdquirente);
        // TODO: 10/04/2017 Esto se debe hacer desde la clase Tab, al momento de setear todos los datos para las pestañas
        pagerAdquirente.setIsSwipeable(true);
    }

    @Override
    public void onClickAdquirente(boolean isAdquirente) {
        if (onEventListener != null) {
            onEventListener.onEvent(TabActivity.EVENT_ADQUIRENTE_SELECTED, isAdquirente);
        }
    }
}