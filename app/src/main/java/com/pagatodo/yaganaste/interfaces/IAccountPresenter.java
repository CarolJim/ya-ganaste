package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.exceptions.OfflineException;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountPresenter {

    public void initValidationLogin(String usuario) throws OfflineException;
}
