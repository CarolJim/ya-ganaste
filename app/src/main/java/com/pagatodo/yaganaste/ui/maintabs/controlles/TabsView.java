package com.pagatodo.yaganaste.ui.maintabs.controlles;

import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.interfaces.IProgressView;

/**
 * @author Juan Guerra on 24/03/2017.
 */

public interface TabsView<T extends IEnumTab> {

    void loadViewPager(ViewPagerData<T> viewPagerData);
}
