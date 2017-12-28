package com.pagatodo.yaganaste.ui;

/**
 * Created by FranciscoManzo on 28/12/2017.
 */

public class NewPaymentPresenter implements INewPaymentPresenter {
    NewPaymentFragment mView;
    INewPaymentInteractor mInteractor;

    public NewPaymentPresenter(NewPaymentFragment mView) {
        this.mView = mView;
        mInteractor = new NewPaymentInteractor(this);
    }

    @Override
    public void testToPresenter() {
        mInteractor.testToInteractor();
    }

    @Override
    public void resToPresenter() {
        mView.resToView();
    }
}
