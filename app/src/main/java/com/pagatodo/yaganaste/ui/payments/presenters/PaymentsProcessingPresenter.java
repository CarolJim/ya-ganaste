package com.pagatodo.yaganaste.ui.payments.presenters;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.EjecutarTransaccionResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.ISessionExpired;
import com.pagatodo.yaganaste.ui._manager.GenericPresenterMain;
import com.pagatodo.yaganaste.ui.payments.interactors.PaymentsProcessingInteractor;
import com.pagatodo.yaganaste.ui.payments.interactors.interfaces.IPaymentsProcessingInteractor;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentsProcessingManager;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentsProcessingPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.utils.Constants;

import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_SESSION_EXPIRED;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

/**
 * Created by Jordan on 27/04/2017.
 */

public class PaymentsProcessingPresenter extends GenericPresenterMain implements IPaymentsProcessingPresenter, IPreferUserGeneric {
    IPaymentsProcessingInteractor interactor;
    PaymentsProcessingManager manager;

    public PaymentsProcessingPresenter(PaymentsProcessingManager manager) {
        this.manager = manager;
        interactor = new PaymentsProcessingInteractor(this);
        setIView(manager);
    }

    @Override
    public void setIView(ISessionExpired iPreferUserGeneric) {
        super.setIView(iPreferUserGeneric);
    }

    @Override
    public void sendPayment(int typeOperation, Object obj) throws OfflineException {
        if (typeOperation == Constants.PAYMENT_RECARGAS) {
            interactor.sendRecarga((Recarga) obj);
        } else if (typeOperation == Constants.PAYMENT_SERVICIOS) {
            interactor.sendPagoServicio((Servicios) obj);
        } else if (typeOperation == Constants.PAYMENT_ENVIOS) {
            interactor.sendEnvio((Envios) obj);
        }


       /* Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat dfH = new SimpleDateFormat("HH:mm:ss");
                String formattedDate = df.format(c.getTime());

                DataTransaccion dataTransaccion = new DataTransaccion();
                dataTransaccion.setFecha(formattedDate);
                dataTransaccion.setHora(dfH.format(c.getTime()));
                dataTransaccion.setNumeroAutorizacion("123456DK");
                EjecutarTransaccionResponse data = new EjecutarTransaccionResponse();
                data.setCodigoRespuesta(CODE_OK);
                data.setData(dataTransaccion);
                DataSourceResult sourceResult = new DataSourceResult(null, null, data);
                onSuccessPayment(sourceResult);
            }
        }, 2300);*/
    }

    @Override
    public void onSuccessPayment(DataSourceResult result) {
        EjecutarTransaccionResponse data = (EjecutarTransaccionResponse) result.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            //Actualizamos el Saldo del Emisor
            App.getInstance().getPrefs().saveData(USER_BALANCE, String.valueOf(data.getData().getSaldo()));
            manager.onSuccessPaymentRespone(result);

            /**
             * Manejamos el codigo CODE_SESSION_EXPIRED haciendo referencia al presenter actual, que a
             * su vez, por medio de herencia de GenericPResenterMain, contiene el metodo para
             * comunicarse con el GenericFRagment y realizar el proceso de cerrado de la app
             */
        } else if (((GenericResponse) result.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            manager.hideLoader();
            sessionExpiredToPresenter(result);
            // manager.onSuccessPaymentRespone(result);
        } else {
            manager.hideLoader();
            manager.onFailPaimentResponse(result);
        }

    }

    @Override
    public void onFailPayment(DataSourceResult error) {
        manager.hideLoader();
        manager.onFailPaimentResponse(error);
    }

    @Override
    public void errorSessionExpired(DataSourceResult response) {

    }
}
