package com.pagatodo.yaganaste.ui.preferuser.interfases;

/**
 * Created by Francisco Manzo on 08/06/2017.
 */

public interface IPreferDesasociarView extends IPreferUserGeneric {

    void sendSuccessDesasociarToView(String mensaje);

    void sendErrorServerView(String error);

    void sendErrorDesasociarToView(String mensaje);
}
