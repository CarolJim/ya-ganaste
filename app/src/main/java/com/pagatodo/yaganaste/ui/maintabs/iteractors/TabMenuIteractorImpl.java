package com.pagatodo.yaganaste.ui.maintabs.iteractors;

import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.TabMenuIteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.TabMenuManager;

public class TabMenuIteractorImpl implements TabMenuIteractor {

    private TabMenuManager tabMenuManager;

    public TabMenuIteractorImpl(TabMenuManager tabMenuManager) {
        this.tabMenuManager = tabMenuManager;
    }

    @Override
    public final void getpagerData(ViewPagerDataFactory.TABS tab) {
        ViewPagerData viewPagerData = ViewPagerDataFactory.createList(tab);
        tabMenuManager.onViewPagerDataLoaded(viewPagerData);
    }
}
