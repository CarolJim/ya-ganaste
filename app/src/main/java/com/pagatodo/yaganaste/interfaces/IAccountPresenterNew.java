package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.utils.camera.CameraManager;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountPresenterNew {

    void Photo(int i, CameraManager cameraManager);

    void picture(int i, CameraManager cameraManager);

    void validateEmail(String usuario);

    void updateUserInfo();

    void login(String usuario, String password);

    void logout();

    void checkCardAssigment(String numberCard);

    void validatePasswordFormat(String password);

    void createUser();

    void validatePersonData();

    void validateVersion();

    void getNeighborhoods(String zipCode);

    void assignAccount();

    void assignNIP(String nip);

    void gerNumberToSMS();

    void doPullActivationSMS(String message);

    void recoveryPassword(String email);

    void updateBalance();

    void getEstatusCuenta(String numberCard);

    void updateBalanceAdq();

    void updateBalanceCupo();

    void getPaisesList();

    boolean isBackShown();
}
