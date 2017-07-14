package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.interfaces.IEnumTab;

import java.util.List;

/**
 * @author Juan Guerra on 21/03/2017.
 */

public class GenericPagerAdapter<T extends IEnumTab> extends FragmentPagerAdapter {

    private T[] values;
    private Context context;
    private List<Fragment> fragments;
    private FragmentManager fragmentManager;

    public GenericPagerAdapter(@NonNull Context context, @NonNull FragmentManager fm,
                               @NonNull List<Fragment> fragments, @NonNull T[] values) {
        super(fm);
        this.fragmentManager = fm;
        this.values = values;
        this.context = context;
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        return values[position].getName(context);
    }

    public int getPageIcon(int position) {
        return values[position].getIconRes();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public T getCurrentData(int position) {
        return values[position];
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove((Fragment) object).commitNowAllowingStateLoss();
    }

}
