package com.pagatodo.yaganaste.ui._manager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariofernandezbermudez on 02/02/17.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    public static int LOOPS_COUNT = 1000;
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

        if (fragments != null && fragments.size() > 0) {
            position = position % fragments.size(); // use modulo for infinite cycling
            return fragments.get(position);
        }

        return null;

    }

    @Override
    public int getCount() {
        if (fragments != null && fragments.size() > 0) {
            return fragments.size() * LOOPS_COUNT; // simulate infinite by big number of products
        } else {
            return 1;
        }
    }

}
