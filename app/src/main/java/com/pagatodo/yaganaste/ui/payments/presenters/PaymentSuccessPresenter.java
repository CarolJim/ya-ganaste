package com.pagatodo.yaganaste.ui.payments.presenters;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EnviarTicketTAEPDSResponse;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui.payments.interactors.PaymentsSuccessInteractor;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentSuccessManager;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentsSuccessPresenter;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ENVIAR_TICKET_TAEPDS;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by jmhernandez on 22/05/2017.
 */

public class PaymentSuccessPresenter implements IPaymentsSuccessPresenter {

    private PaymentsSuccessInteractor interactor;
    private PaymentSuccessManager successManager;

    public PaymentSuccessPresenter(PaymentSuccessManager manager) {
        successManager = manager;
        interactor = new PaymentsSuccessInteractor(this);
    }

    @Override
    public void sendTicket(String mail, String idTransaction) {
        interactor.sendTicket(mail, idTransaction);
    }

    @Override
    public void onSuccess(WebService ws, Object success) {
        successManager.hideLoader();
        if (ws == ENVIAR_TICKET_TAEPDS) {
            processSendEmailTAEPDS((DataSourceResult) success);
        }
    }

    @Override
    public void onError(WebService ws, Object error) {
        successManager.hideLoader();
        if (ws == ENVIAR_TICKET_TAEPDS) {
            successManager.onErrorSendMail(error.toString());
        }
    }

    private void processSendEmailTAEPDS(DataSourceResult response) {
        EnviarTicketTAEPDSResponse data = (EnviarTicketTAEPDSResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            successManager.onSuccessSendMail(data.getMensaje());
        } else {
            successManager.onErrorSendMail(data.getMensaje());
        }
    }
}
