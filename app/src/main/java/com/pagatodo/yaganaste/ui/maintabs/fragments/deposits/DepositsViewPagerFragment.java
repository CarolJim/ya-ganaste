package com.pagatodo.yaganaste.ui.maintabs.fragments.deposits;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;
import com.pagatodo.yaganaste.utils.customviews.NoSwipeViewPager;

import java.util.List;

/**
 * @author Juan Guerra on 26/07/2017.
 */

public class DepositsViewPagerFragment extends SupportFragment {

    private NoSwipeViewPager rootView;


    public static DepositsFragment newInstance() {
        DepositsFragment depositsFragment = new DepositsFragment();
        Bundle args = new Bundle();
        depositsFragment.setArguments(args);
        return depositsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new NoSwipeViewPager(getActivity());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = (NoSwipeViewPager) view;
        initViews();
    }

    @Override
    public void initViews() {
        //rootView.setAdapter(new GenericPagerAdapter<>());
    }




}
