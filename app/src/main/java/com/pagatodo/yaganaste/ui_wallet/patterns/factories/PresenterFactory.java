package com.pagatodo.yaganaste.ui_wallet.patterns.factories;

import com.pagatodo.yaganaste.ui.maintabs.controlles.MovementsView;
import com.pagatodo.yaganaste.ui.maintabs.presenters.AccountMovementsPresenter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IWalletView;
import com.pagatodo.yaganaste.ui_wallet.patterns.interfaces.Presenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.WalletPresenterImpl;

public class PresenterFactory {

    private Object obj;

    public static PresenterFactory newInstace(Object obj){
        return new PresenterFactory(obj);
    }


    private PresenterFactory(Object obj){
        this.obj = obj;
    }


    public Presenter getPresenter(TypePresenter type){
        if (type == TypePresenter.WALLETPRESENTER){
            return  new WalletPresenterImpl((IWalletView)this.obj);
        }
        if (type == TypePresenter.MOVEMENTSPRESENTER){
            return new AccountMovementsPresenter((MovementsView) this.obj);
        }

        return  null;
    }

    public enum TypePresenter{
        WALLETPRESENTER,
        MOVEMENTSPRESENTER
    }
}
