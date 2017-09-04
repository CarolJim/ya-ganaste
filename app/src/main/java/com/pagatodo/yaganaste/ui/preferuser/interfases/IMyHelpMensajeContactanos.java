package com.pagatodo.yaganaste.ui.preferuser.interfases;

/**
 * Created by Armando Sandoval on 23/08/2017.
 */

public interface IMyHelpMensajeContactanos  extends IPreferUserGeneric {
    void sendErrorEnvioCorreoContactanos(String mensaje);
    void sendSuccessMensaje(String mensaje);

    //Mostrar Loader personalizado
    void showLoader(String message);

    //Mostrar Loader personalizado]
    void hideLoader();

    void showExceptionToView(String mMesage);
}
