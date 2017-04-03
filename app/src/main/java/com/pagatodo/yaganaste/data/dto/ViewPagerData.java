package com.pagatodo.yaganaste.data.dto;

import android.support.v4.app.Fragment;

import com.pagatodo.yaganaste.interfaces.IEnumTab;

import java.util.List;

/**
 * @author Juan Guerra on 24/03/2017.
 */

public class ViewPagerData<T extends IEnumTab> {

    private List<Fragment> fragmentList;
    private T[] tabData;

    public ViewPagerData(List<Fragment> fragmentList, T... tabData) {
        this.fragmentList = fragmentList;
        this.tabData = tabData;
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public T[] getTabData() {
        return tabData;
    }
}
