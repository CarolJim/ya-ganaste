package com.pagatodo.yaganaste.ui.maintabs.presenters;

import android.support.annotation.Nullable;

import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.ui._manager.GenericPresenterMain;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.TabMenuIteractorImpl;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.TabMenuIteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.TabMenuManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.TabPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;

/**
 * @author Juan Guerra on 27/03/2017.
 */

public class TabPresenterImpl extends GenericPresenterMain<IPreferUserGeneric> implements TabPresenter, TabMenuManager {
    private TabMenuIteractor tabMenuIteractor;
    private TabsView tabsView;

    public TabPresenterImpl(TabsView mainTabsView) {
        this.tabsView = mainTabsView;
        this.tabMenuIteractor = new TabMenuIteractorImpl(this);
    }


    public TabPresenterImpl() {
        this.tabMenuIteractor = new TabMenuIteractorImpl(this);
    }

    public void setTabsView(TabsView tabsView) {
        this.tabsView = tabsView;
    }

    @Override
    public final void getPagerData(@Nullable ViewPagerDataFactory.TABS tab) {
        if (tab != null) {
            tabMenuIteractor.getpagerData(tab);
        }
    }

    @Override
    public final void onViewPagerDataLoaded(ViewPagerData viewPagerData) {
        tabsView.loadViewPager(viewPagerData);
    }
}