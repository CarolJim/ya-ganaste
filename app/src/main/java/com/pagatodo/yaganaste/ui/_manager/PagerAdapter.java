package com.pagatodo.yaganaste.ui._manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariofernandezbermudez on 02/02/17.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<Fragment>();
    }

    public void addFragment(Fragment fragment) {
        this.fragments.add(fragment);
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
