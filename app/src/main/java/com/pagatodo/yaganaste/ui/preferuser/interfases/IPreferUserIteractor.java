package com.pagatodo.yaganaste.ui.preferuser.interfases;

import android.graphics.Bitmap;

import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarDatosCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.BloquearCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarEmailRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EnviarCorreoContactanosRequest;

/**
 * Created by Francisco Manzo on 08/06/2017.
 */

public interface IPreferUserIteractor {
    void desasociarToIteracto(Request request);

    void showExceptionBitmapDownloadToIteractor(String s);

    void sendChangePassToIteractor(CambiarContraseniaRequest cambioPassRequest);

    void changeEmailToIteractor(CambiarEmailRequest cambiarEmailRequest);

    void changePassToIteractor(CambiarContraseniaRequest cambiarContraseniaRequest);

    void sendIteractorDatosCuenta(ActualizarDatosCuentaRequest datosCuentaRequest);

    void toIteractorBloquearCuenta(BloquearCuentaRequest bloquearCuentaRequest);

    void toIteractorEstatusCuenta(EstatusCuentaRequest estatusCuentaRequest);

    void enviarCorreoContactanos(EnviarCorreoContactanosRequest enviarCorreoContactanosRequest);


}
