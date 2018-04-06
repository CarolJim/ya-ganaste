package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.interfaces.IAprovView;
import com.pagatodo.yaganaste.ui.account.AprovPresenter;

/**
 * @author Juan Guerra on 24/03/2017.
 */

public class MainMenuPresenterImp extends AprovPresenter {

    public MainMenuPresenterImp(IAprovView mainTabsView) {
        super(App.getInstance(), false);
        super.setAprovView(mainTabsView);

    }

    @Override
    public void goToNextStepAccount(String event, Object data) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void onSuccesBalance() {

    }

    @Override
    public void onSuccesChangePass6(DataSourceResult dataSourceResult) {

    }


    @Override
    public void onSuccesBalanceAdq() {

    }

    @Override
    public void onSuccesBalanceCupo() {

    }

    @Override
    public void onSuccessDataPerson() {

    }

    @Override
    public void onSuccesStateCuenta() {

    }
}
