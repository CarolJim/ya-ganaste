package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.ui.maintabs.controlles.AdquirenteEmisorView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.AdquirenteEmisorPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.MovementsPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.TabPresenter;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public class AdquirenteEmisorPresenterImp<T extends IEnumTab> implements AdquirenteEmisorPresenter<T>{

    private AdquirenteEmisorView<T> adquirenteEmisorView;
    private TabPresenter tabPresenter;
    private MovementsPresenter movementsPresenter;

    public AdquirenteEmisorPresenterImp(AdquirenteEmisorView<T> adquirenteEmisorView) {
        this.adquirenteEmisorView = adquirenteEmisorView;
        this.tabPresenter = new TabPresenterImpl(adquirenteEmisorView);
        this.movementsPresenter = new AccountMovementsPresenter(adquirenteEmisorView);
    }

    @Override
    public void getRemoteMovementsData(T data) {
        movementsPresenter.getRemoteMovementsData(data);
    }

    @Override
    public void getPagerData(ViewPagerDataFactory.TABS tab) {
        tabPresenter.getPagerData(tab);
    }
}
