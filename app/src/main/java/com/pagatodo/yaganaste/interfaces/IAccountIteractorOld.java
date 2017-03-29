package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioFWSRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.interfaces.enums.TypeLogin;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountIteractorOld {
    public void validateUserStatus(String user);
    public void login();
    public void logout();
    public void checkSessionState(TypeLogin type,IniciarSesionRequest request);
    public void checkCard(String numberCard);
    public void validatePassword(String password);
    public void createUser(CrearUsuarioFWSRequest request);
    public void getNeighborhoodByZipCode(String zipCode);
}
