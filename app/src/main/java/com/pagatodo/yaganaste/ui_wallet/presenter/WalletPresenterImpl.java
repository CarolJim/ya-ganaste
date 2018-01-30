package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.ui_wallet.interactors.WalletInteractor;
import com.pagatodo.yaganaste.ui_wallet.interactors.WalletInteractorImpl;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WlletNotifaction;
import com.pagatodo.yaganaste.ui_wallet.views.WalletView;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.StringConstants;

import static com.pagatodo.yaganaste.utils.StringConstants.UPDATE_DATE_BALANCE_ADQ;

/**
 * Created by icruz on 12/12/2017.
 */

public class WalletPresenterImpl implements WalletPresenter, WlletNotifaction {

    private WalletView walletView;
    private WalletInteractor walletInteractor;

    public WalletPresenterImpl(WalletView walletView) {
        this.walletView = walletView;
        this.walletInteractor = new WalletInteractorImpl(this);
    }

    public WalletPresenterImpl(){

    }

    @Override
    public void getWalletsCards(boolean error){
        walletView.showProgress();
        walletInteractor.getWalletsCards(error, this);
    }

    @Override
    public void onDestroy() {
        walletView = null;
    }

    @Override
    public void getMovementsAdq() {
        walletView.showProgress();
    }

    @Override
    public void updateBalance() {
        walletView.showProgress();
        walletInteractor.getBalance();
    }

    @Override
    public void onSuccessADQ(String response) {
        walletView.hideProgress();
        SingletonUser.getInstance().getDatosSaldo().setSaldoAdq(response);
        App.getInstance().getPrefs().saveData(StringConstants.ADQUIRENTE_BALANCE, response);
        App.getInstance().getPrefs().saveData(UPDATE_DATE_BALANCE_ADQ, DateUtil.getTodayCompleteDateFormat());
        walletView.getSaldo();
    }

    @Override
    public void onSuccess(boolean error) {
        if (walletView != null){
            walletView.completed(error);
        }
    }

    @Override
    public void onFailed(int errorCode, int action, String error) {
        if (walletView != null){
            walletView.setError();
            walletView.hideProgress();
        }
    }
}
