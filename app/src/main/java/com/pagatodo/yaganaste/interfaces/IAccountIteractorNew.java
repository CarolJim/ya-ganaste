package com.pagatodo.yaganaste.interfaces;

import android.app.Fragment;

import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioClienteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.RecuperarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.interfaces.enums.WebService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountIteractorNew<T> {
    void validateUserStatus(String user);

    void login(IniciarSesionRequest request);

    public void loginAdq();

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

    void assignmentNIP(AsignarNIPRequest request, WebService asignarNip);

    void recoveryPassword(RecuperarContraseniaRequest request);

    void getBalance();

    void getBalanceAdq();

    void getBalanceCupo();

    ArrayList<Countries> getPaisesList();

    void logoutSinRespuesta();

    void onStatusCuenta(EstatusCuentaRequest estatusCuentaRequest);

}

