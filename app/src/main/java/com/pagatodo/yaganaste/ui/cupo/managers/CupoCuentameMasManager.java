package com.pagatodo.yaganaste.ui.cupo.managers;

/**
 * Created by Jordan on 26/07/2017.
 */

public interface CupoCuentameMasManager<Error>  extends CupoBaseManager{

    void setValidationRules();

    void validateForm();

    void showValidationError(int id, Error error);

    void onValidationSuccess();

    void getDataForm();

}
