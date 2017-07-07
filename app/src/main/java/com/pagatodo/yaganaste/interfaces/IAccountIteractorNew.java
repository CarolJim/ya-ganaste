package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioClienteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.RecuperarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountIteractorNew<T> {
    void validateUserStatus(String user);

    void login(IniciarSesionRequest request);

    void logout();

    void updateSessionData();

    void checkSessionState(Request request);

    void checkCard(String numberCard);

    void validatePassword(String password);

    void createUser();

    void createUserClient(CrearUsuarioClienteRequest request);

    void validatePersonData();

    void getNeighborhoodByZipCode(String zipCode);

    void assigmentAccountAvaliable(int idAccount);

    void getSMSNumber();

    void verifyActivationSMS();

    void assignmentNIP(AsignarNIPRequest request);

    void recoveryPassword(RecuperarContraseniaRequest request);

    void getBalance();

    void getBalanceAdq();

}

