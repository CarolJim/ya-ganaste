package com.pagatodo.yaganaste.ui.maintabs.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.DepositsDataFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.DepositsMapFragment;

/**
 * Created by Jordan on 19/05/2017.
 */

public class FragmentDepositPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    final int PAGE_COUNT = 2;

    public FragmentDepositPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DepositsDataFragment.newInstance();
            case 1:
                return DepositsMapFragment.newInstance();
            default:
                return DepositsMapFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
