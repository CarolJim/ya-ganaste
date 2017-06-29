package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.HomeFragmentPresenter;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;
import com.pagatodo.yaganaste.utils.customviews.NoSwipeViewPager;
import com.pagatodo.yaganaste.utils.customviews.yaganasteviews.TabLayoutEmAd;

import java.util.List;

import static com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment.CODE_CANCEL;
import static com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment.RESULT_CANCEL_OK;


/**
 * @author Juan Guerra on 10/11/2016.
 */

public class HomeTabFragment extends SupportFragment implements TabsView, TabLayoutEmAd.InviteAdquirenteCallback,
        AbstractAdEmFragment.UpdateBalanceCallback{

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
        tabLayoutEmAd.setInviteAdquirenteCallback(this);
        tabLayoutEmAd.setUpWithViewPager(pagerAdquirente);
    }

    @Override
    public void loadViewPager(ViewPagerData viewPagerData) {
        pagerAdquirente.setAdapter(new GenericPagerAdapter<>(getActivity(), getChildFragmentManager(), viewPagerData.getFragmentList(), viewPagerData.getTabData()));
        pagerAdquirente.setIsSwipeable(SingletonUser.getInstance().getDataUser().isEsAgente() &&
                SingletonUser.getInstance().getDataUser().getEstatusDocumentacion() == Recursos.CRM_DOCTO_APROBADO);
    }

    @Override
    public void onInviteAdquirente() {
        if (onEventListener != null) {
            onEventListener.onEvent(TabActivity.EVENT_INVITE_ADQUIRENTE, null);
        }
    }

    @Override
    public void onUpdateBalance() {
        tabLayoutEmAd.updateData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<Fragment> all = getFragments();
        for (Fragment current : all) {
            if (current instanceof PaymentsFragment) {
                current.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}