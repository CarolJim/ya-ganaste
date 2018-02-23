package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneTiposReembolsoResponse;
import com.pagatodo.yaganaste.ui_wallet.interactors.TimeRepaymentIteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ITimeRepaymentIteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ITimeRepaymentPresenter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ITimeRepaymentView;

/**
 * Created by Omar on 22/02/2018.
 */

public class TimeRepaymentPresenter implements ITimeRepaymentPresenter {

    private ITimeRepaymentView view;
    private ITimeRepaymentIteractor timeRepaymentIteractor;

    public TimeRepaymentPresenter(ITimeRepaymentView view) {
        this.view = view;
        timeRepaymentIteractor = new TimeRepaymentIteractor(this);
    }

    @Override
    public void getTypePayments() {
        view.showLoader("");
        timeRepaymentIteractor.getTypesRepayments();
    }

    @Override
    public void updateTypeRepayment(int idTypeRepayment) {
        view.showLoader("");
        timeRepaymentIteractor.updateTypeRepayment(idTypeRepayment);
    }

    @Override
    public void onSuccessGetTypesRepayment(ObtieneTiposReembolsoResponse response) {
        view.hideLoader();
        view.onSuccessGetTypes(response);
    }

    @Override
    public void onSuccessUpdateTypeRepayment() {
        view.hideLoader();
        view.onSuccessUpdateType();
    }

    @Override
    public void onFailedGetTypesRepayment(String error) {
        view.hideLoader();
        view.onError(error);
    }

    @Override
    public void onFailedUpdateTypeRepayment(String error) {
        view.hideLoader();
        view.onError(error);
    }

    @Override
    public void toPresenterErrorServer(String message) {
        view.hideLoader();
        view.onError(message);
    }
}
