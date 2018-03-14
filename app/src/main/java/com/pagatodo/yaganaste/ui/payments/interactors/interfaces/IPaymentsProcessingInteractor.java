package com.pagatodo.yaganaste.ui.payments.interactors.interfaces;

import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;

/**
 * Created by Jordan on 27/04/2017.
 */

public interface IPaymentsProcessingInteractor extends IRequestResult {
    void sendRecarga(Recarga recarga) throws OfflineException;

    void sendEnvio(Envios envio) throws OfflineException;

    void sendPagoServicio(Servicios servicio) throws OfflineException;
}
