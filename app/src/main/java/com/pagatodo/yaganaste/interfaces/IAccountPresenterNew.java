package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.interfaces.enums.AccountOperation;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountPresenterNew {

    public void validateEmail(String usuario);
    public void updateUserInfo();
    public void login(String usuario, String password);
    public void logout();
    public void checkCardAssigment(String numberCard);
    public void validatePasswordFormat(String password);
    public void createUser();
    public void getNeighborhoods(String zipCode);
    public void assignAccount();
    public void assignNIP(String nip);
    public void gerNumberToSMS();
    public void doPullActivationSMS(String message);
    public void recoveryPassword(String email);
    void checkUpdateDocs();

}
