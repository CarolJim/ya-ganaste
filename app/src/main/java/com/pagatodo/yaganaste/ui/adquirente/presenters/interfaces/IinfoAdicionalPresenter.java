package com.pagatodo.yaganaste.ui.adquirente.presenters.interfaces;

import com.pagatodo.yaganaste.interfaces.enums.SpinnerPLD;
import com.pagatodo.yaganaste.interfaces.enums.WebService;

/**
 * Created by Jordan on 04/08/2017.
 */

public interface IinfoAdicionalPresenter<T> {
    void getPaisesList();

    void onSuccessPaisesList(Object success);

    void createUsuarioAdquirente();

    void updateSession();

    void onSuccessCreateUsuarioAdquirente(Object success);

    void onWSError(WebService ws, T error);

    void setSpinner(SpinnerPLD sp);

    void onSuccessSpinnerList(Object success, SpinnerPLD sp);

}
