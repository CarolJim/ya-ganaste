package com.pagatodo.yaganaste.ui;

/**
 * Created by FranciscoManzo on 28/12/2017.
 */

public class NewPaymentInteractor implements INewPaymentInteractor{
    INewPaymentPresenter mPresenter;

    public NewPaymentInteractor(INewPaymentPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void testToInteractor() {
        mPresenter.resToPresenter();
    }
}
