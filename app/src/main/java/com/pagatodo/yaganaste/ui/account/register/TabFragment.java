package com.pagatodo.yaganaste.ui.account.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.HomeFragmentPresenter;
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;


/**
 * @author Juan Guerra on 10/11/2016.
 */

public class TabFragment extends GenericFragment implements TabsView {

    private TabLayout tabAdquirente;
    private ViewPager pagerAdquirente;
    private View rootView;
    private HomeFragmentPresenter homeFragmentPresenter;

    public static TabFragment newInstance(){
        TabFragment homeTabFragment = new TabFragment();
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
        pagerAdquirente = (ViewPager) rootView.findViewById(R.id.pager_adquirente);
        homeFragmentPresenter.getPagerData(ViewPagerDataFactory.TABS.HOME_FRAGMENT);
    }

    @Override
    public void loadViewPager(ViewPagerData viewPagerData) {
        pagerAdquirente.setAdapter(new GenericPagerAdapter<>(getActivity(), getChildFragmentManager(), viewPagerData.getFragmentList(), viewPagerData.getTabData()));
        tabAdquirente.setupWithViewPager(pagerAdquirente);
    }
}
