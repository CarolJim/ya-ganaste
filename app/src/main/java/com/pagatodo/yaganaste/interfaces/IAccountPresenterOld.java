package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioFWSRequest;
import com.pagatodo.yaganaste.interfaces.enums.AccountOperation;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountPresenterOld {

    public void initValidationLogin(String usuario);
    public void login(AccountOperation type, String usuario, String password);
    public void logout();
    public void checkCardAssigment(String numberCard);
    public void selectStepNoCard();
    public void validatePasswordFormat(String password);
    public void createUser(CrearUsuarioFWSRequest requestCreateUser);
    public void getNeighborhoods(String zipCode);
}
