package com.pagatodo.yaganaste.ui.account.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.account.ILoginContainerManager;
import com.pagatodo.yaganaste.ui.account.IQuickBalanceManager;
import com.pagatodo.yaganaste.ui.account.QuickBalanceAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 14/07/2017.
 */

public class QuickBalanceContainerFragment extends SupportFragment implements IQuickBalanceManager {

    private QuickBalanceAdapter quickBalanceAdapter;
    private View rootView;

    @BindView(R.id.viewPagerQuickBalance)
    ViewPager viewPagerQuickBalance;
    ILoginContainerManager loginContainerManager;

    public static QuickBalanceContainerFragment newInstance() {
        QuickBalanceContainerFragment fragment = new QuickBalanceContainerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quickBalanceAdapter = new QuickBalanceAdapter(getChildFragmentManager(), !RequestHeaders.getTokenAdq().isEmpty());
        loginContainerManager = ((LoginManagerContainerFragment) getParentFragment()).getLoginContainerManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quickbalance_container, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        viewPagerQuickBalance.setAdapter(quickBalanceAdapter);
    }

    public ILoginContainerManager getLoginContainerManager() {
        return loginContainerManager;
    }

    public IQuickBalanceManager getQuickBalanceManager() {
        return this;
    }

    @Override
    public void onBackPress() {
        if (viewPagerQuickBalance.getCurrentItem() > 0) {
            viewPagerQuickBalance.setCurrentItem(0);
        } else {
            getActivity().finish();
        }
    }

    @Override
    public void nextPage() {
        viewPagerQuickBalance.setCurrentItem(1);
    }
}