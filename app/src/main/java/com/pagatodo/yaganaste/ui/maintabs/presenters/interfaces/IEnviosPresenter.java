package com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces;

import com.pagatodo.yaganaste.interfaces.enums.TransferType;

/**
 * Created by Jordan on 20/04/2017.
 */

public interface IEnviosPresenter {
    void validateForms(TransferType type, String number, String importe, String name, String concept, String reference);

    void getTitularName(String cuenta);
}
