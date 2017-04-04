package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.interfaces.enums.TypeLogin;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqAccountPresenter {

    public void createAdq();
    public void login();
    public void getNeighborhoods(String zipCode);
}
