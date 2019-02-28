package com.pagatodo.yaganaste.modules.emisor.movements;

import com.pagatodo.view_manager.pages.Months;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MovementsAdapter extends FragmentPagerAdapter {

    private ArrayList<Months> monts;


    MovementsAdapter(FragmentManager fm, ArrayList<Months> monts) {
        super(fm);
        this.monts = monts;
    }

    @Override
    public MovementsFragment getItem(int position) {
        MovementsFragment fragment = MovementsFragment.newInstance();

        return fragment;
    }

    @Override
    public int getCount() {
        return monts.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return monts.get(position).getName();
    }


}
