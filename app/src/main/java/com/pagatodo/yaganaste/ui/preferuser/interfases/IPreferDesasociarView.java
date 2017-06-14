package com.pagatodo.yaganaste.ui.preferuser.interfases;

/**
 * Created by Francisco Manzo on 08/06/2017.
 */

public interface IPreferDesasociarView extends IPreferUserGeneric {

    void sendSuccessView(String mensaje);

    void sendErrorView(String mensaje);

    void sendErrorServerView(String error);

    void getImagenURLiteractor(String mUserImage);
}
