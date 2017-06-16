package com.pagatodo.yaganaste.ui.preferuser.interfases;

import android.graphics.Bitmap;

import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarEmailRequest;

/**
 * Created by Francisco Manzo on 08/06/2017.
 */

public interface IPreferUserIteractor {
    void desasociarToIteracto(Request request);

    void getImagenURLToIteractor(String mUserImage);

    void sendToIteractorBitmap(Bitmap bitmap);

    void sendIteractorActualizarAvatar(ActualizarAvatarRequest avatarRequest);

    void showExceptionBitmapDownloadToIteractor(String s);

    void sendChangePassToIteractor(CambiarContraseniaRequest cambioPassRequest);

    void changeEmailToIteractor(CambiarEmailRequest cambiarEmailRequest);

    void changePassToIteractor(CambiarContraseniaRequest cambiarContraseniaRequest);
}
