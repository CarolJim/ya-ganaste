package com.pagatodo.yaganaste.data.dto;

import androidx.fragment.app.Fragment;

import com.pagatodo.yaganaste.interfaces.IEnumTab;

import java.util.List;

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
