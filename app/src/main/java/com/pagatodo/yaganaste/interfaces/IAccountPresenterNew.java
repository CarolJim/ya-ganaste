package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.interfaces.enums.TypeLogin;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountPresenterNew {

    public void initValidationLogin(String usuario);
    public void login(TypeLogin type, String usuario, String password);
    public void logout();
    public void checkCardAssigment(String numberCard);
    public void validatePasswordFormat(String password);
    public void createUser();
    public void getNeighborhoods(String zipCode);
    public void assignCard();
    public void gerNumberToSMS();
    public void doPullActivationSMS(String message);
}
