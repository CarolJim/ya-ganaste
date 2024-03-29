package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.LoginStarbucksRequest;
import com.pagatodo.yaganaste.data.room_db.entities.Agentes;
import com.pagatodo.yaganaste.data.room_db.entities.Paises;
import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioClienteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.RecuperarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountIteractorNew<T> {
    void validateUserStatus(String user);

    void sendIteractorActualizarAvatar(ActualizarAvatarRequest avatarRequest,WebService asignarNip);

    void login(IniciarSesionRequest request);

    void login(LoginStarbucksRequest request);

    void logout();

    void updateSessionData();

    void checkSessionState(Request request, String password);

    void checkCard(String numberCard);

    void validatePassword(String password);

    void createUser();

    void createUserClient(CrearUsuarioClienteRequest request);

    void validatePersonData();

    void validatePersonDatanew();

    void validatePersonDataHomonimia();

    void getNeighborhoodByZipCode(String zipCode);

    void assigmentAccountAvaliable(int idAccount);

    void getSMSNumber();

    void verifyActivationSMS();

    void assignmentNIP(AsignarNIPRequest request, WebService asignarNip);

    void recoveryPassword(RecuperarContraseniaRequest request);

    void getBalance();

    void getBalanceAdq(Agentes agentes);

    void getBalanceStarbucks();

    List<Paises> getPaisesList();

    void logoutSinRespuesta();

    void onStatusCuenta(EstatusCuentaRequest estatusCuentaRequest);

    void changePassToIteractor(CambiarContraseniaRequest cambiarContraseniaRequest);
}

