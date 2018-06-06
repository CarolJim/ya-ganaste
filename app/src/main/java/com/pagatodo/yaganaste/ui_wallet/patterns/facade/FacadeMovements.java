package com.pagatodo.yaganaste.ui_wallet.patterns.facade;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.dto.AdquirentePaymentsTab;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.ui.maintabs.controlles.MovementsView;
import com.pagatodo.yaganaste.ui.maintabs.presenters.AdqPaymentesPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.MovementsPresenter;

import java.util.List;

public class FacadeMovements implements MovementsView {

    private MovementsPresenter<AdquirentePaymentsTab> movementsPresenter;
    private listenerMovements listener;

    public FacadeMovements(listenerMovements listener){
        this.movementsPresenter = new AdqPaymentesPresenter(this);
        this.listener = listener;
    }

        public void launchRefund(DataMovimientoAdq item){
        this.movementsPresenter.sendReembolso(item);
    }

    @Override
    public void loadMovementsResult(List movements) {

    }

    @Override
    public void updateBalance() {

    }

    @Override
    public void loadReembolso() {
        this.listener.loadReembolso();
    }

    @Override
    public void showLoader(String message) {
        this.listener.showLoader(message);
    }

    @Override
    public void hideLoader() {
        this.listener.hideLoader();
    }

    @Override
    public void showError(Object error) {
        this.listener.showError(error);
    }


    @Override
    public void errorSessionExpired(DataSourceResult response) {

    }

    @Override
    public void loadViewPager(ViewPagerData viewPagerData) {

    }

    public interface listenerMovements{
        void loadReembolso();
        void showLoader(String message);
        void hideLoader();
        void showError(Object error);
    }
}
