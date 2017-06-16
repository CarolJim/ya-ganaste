package com.pagatodo.yaganaste.ui.preferuser.interfases;

/**
 * Created by Francisco Manzo on 22/05/2017.
 */

public interface IMyPassView extends IPreferUserGeneric{
    void sendSuccessPassToView(String mensaje);

    void sendErrorPassToView(String mensaje);
}
