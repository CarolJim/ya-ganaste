package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.pagatodo.yaganaste.interfaces.IEnumTab;

import java.util.List;

/**
 * @author Juan Guerra on 21/03/2017.
 */

public class GenericPagerAdapter<T extends IEnumTab> extends FragmentPagerAdapter {

    private T[] values;
    private Context context;
    private List<Fragment> fragments;

    public GenericPagerAdapter(@NonNull Context context, @NonNull FragmentManager fm,
                               @NonNull List<Fragment> fragments, @NonNull T[] values) {
        super(fm);
        this.values = values;
        this.context = context;
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        return values[position].getName(context);
    }

    public int getPageIcon(int position){
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
}
