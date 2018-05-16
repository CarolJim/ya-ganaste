package com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces;

import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui_wallet.patterns.interfaces.Presenter;

/**
 * @author Juan Guerra on 24/03/2017.
 */

public interface TabPresenter extends Presenter {

    void getPagerData(ViewPagerDataFactory.TABS tab);
}
