package com.pagatodo.yaganaste.ui.maintabs.fragments.deposits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.maintabs.adapters.FragmentDepositPagerAdapter;
import com.pagatodo.yaganaste.ui.maintabs.managers.DepositsManager;
import com.pagatodo.yaganaste.utils.customviews.NoSwipeViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 19/05/2017.
 */

public class DepositsFragment extends SupportFragment implements DepositsManager {

    private View rootView;
    private FragmentDepositPagerAdapter fragmentPagerAdapter;

    @BindView(R.id.deposito_view_pager)
    NoSwipeViewPager depositsViewPager;

    public static DepositsFragment newInstance() {
        DepositsFragment depositsFragment = new DepositsFragment();
        Bundle args = new Bundle();
        depositsFragment.setArguments(args);
        return depositsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentPagerAdapter = new FragmentDepositPagerAdapter(getChildFragmentManager());
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
        depositsViewPager.setAdapter(fragmentPagerAdapter);
        depositsViewPager.setCurrentItem(0);
    }

    public DepositsManager getDepositManager() {
        return this;
    }

    @Override
    public void onTapButton() {
        depositsViewPager.setCurrentItem(1);
        removeLastFragment();
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
        int currentItem = depositsViewPager.getCurrentItem();
        if (currentItem > 0) {
            depositsViewPager.setCurrentItem(currentItem - 1);
            removeLastFragment();
        }else{
            ((TabActivity)getActivity()).goHome();
        }
    }
}
