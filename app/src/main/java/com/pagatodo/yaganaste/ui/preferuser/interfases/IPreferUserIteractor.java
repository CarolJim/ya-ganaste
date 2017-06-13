package com.pagatodo.yaganaste.ui.preferuser.interfases;

import android.graphics.Bitmap;

import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DesasociarDispositivoRequest;

/**
 * Created by Francisco Manzo on 08/06/2017.
 */

public interface IPreferUserIteractor {
    void desasociarToIteracto(Request request);

    void getImagenURLiteractor(String mUserImage);

    void sendToIteractorBitmap(Bitmap bitmap);
}
