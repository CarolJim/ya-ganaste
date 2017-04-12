package com.pagatodo.yaganaste.ui.maintabs.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.pagatodo.yaganaste.ui.maintabs.fragments.EnviosCarouselFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.RecargasCarouselFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.ServiciosCarouselFragment;

/**
 * Created by Jordan on 10/04/2017.
 */

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    final int PAGE_COUNT = 3;

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return RecargasCarouselFragment.newInstance();
            case 1:
                return ServiciosCarouselFragment.newInstance();
            case 2:
                return EnviosCarouselFragment.newInstance();
            default:
                return RecargasCarouselFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
