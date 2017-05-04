package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActivacionAprovSofttokenRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioClienteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioFWSRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.VerificarActivacionAprovSofttokenRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.interfaces.enums.AccountOperation;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountIteractorNew <T>{
    public void validateUserStatus(String user);
    public void login(IniciarSesionRequest request);
    public void logout();
    public void updateSessionData();
    public void checkSessionState(Request request);
    public void checkCard(String numberCard);
    public void validatePassword(String password);
    public void createUser();
    public void createUserClient(CrearUsuarioClienteRequest request );
    public void getNeighborhoodByZipCode(String zipCode);
    public void assigmentAccountAvaliable(int idAccount);
    public void getSMSNumber();
    public void verifyActivationSMS();
    public void assignmentNIP(AsignarNIPRequest request);
    void checkDocs();

}

