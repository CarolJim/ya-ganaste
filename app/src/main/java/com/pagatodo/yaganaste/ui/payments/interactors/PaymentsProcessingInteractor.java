package com.pagatodo.yaganaste.ui.payments.interactors;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.EjecutarTransaccionRequest;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.ui.payments.interactors.interfaces.IPaymentsProcessingInteractor;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentsProcessingPresenter;

import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB1;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB2;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB3;

/**
 * Created by Jordan on 27/04/2017.
 */

public class PaymentsProcessingInteractor implements IPaymentsProcessingInteractor {

    IPaymentsProcessingPresenter presenter;

    public PaymentsProcessingInteractor(IPaymentsProcessingPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void sendRecarga(Recarga recarga) throws OfflineException {
        EjecutarTransaccionRequest request = new EjecutarTransaccionRequest(
                TAB1.getId(), recarga.getReferencia(), recarga.getMonto(),
                recarga.getComercio().getIdComercio());

        ApiTrans.ejecutarTransaccion(request, this);
    }

    @Override
    public void sendPagoServicio(Servicios servicio) throws OfflineException {
        EjecutarTransaccionRequest request = new EjecutarTransaccionRequest(
                TAB2.getId(), servicio.getReferencia(), servicio.getMonto(),
                servicio.getComercio().getIdComercio());

        ApiTrans.ejecutarTransaccion(request, this);
    }

    @Override
    public void sendEnvio(Envios envio) throws OfflineException {
        EjecutarTransaccionRequest request = new EjecutarTransaccionRequest(
                TAB3.getId(), envio.getReferencia(), envio.getMonto(),
                envio.getComercio().getIdComercio(), envio.getNombreDestinatario(),
                envio.getConcepto(), envio.getReferenciaNumerica());

        ApiTrans.ejecutarTransaccion(request, this);
    }

    @Override
    public void onSuccess(DataSourceResult result) {
        presenter.onSuccessPayment(result);
    }

    @Override
    public void onFailed(DataSourceResult error) {
        presenter.onFailPayment(error);
    }

}
