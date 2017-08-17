package com.pagatodo.yaganaste.ui.payments.interactors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EnviarTicketTAEPDSRequest;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.ui.payments.interactors.interfaces.IPaymentsSuccessInteractor;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentsSuccessPresenter;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ENVIAR_TICKET_TAEPDS;

/**
 * Created by jmhernandez on 22/05/2017.
 */

public class PaymentsSuccessInteractor implements IPaymentsSuccessInteractor {

    IPaymentsSuccessPresenter successPresenter;

    // TODO pendiente pasar el PaymentSuccess a al MVP
    public PaymentsSuccessInteractor(IPaymentsSuccessPresenter presenter) {
        successPresenter = presenter;
    }


    @Override
    public void sendTicket(String mail, String idTransaction) {
        EnviarTicketTAEPDSRequest request = new EnviarTicketTAEPDSRequest();
        request.setEmail(mail);
        request.setIdTransaction(idTransaction);
        try {
            ApiAdtvo.enviarTicketTAEPDS(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            successPresenter.onError(ENVIAR_TICKET_TAEPDS, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

        switch (dataSourceResult.getWebService()) {
            case ENVIAR_TICKET_TAEPDS:
                successPresenter.onSuccess(dataSourceResult.getWebService(), dataSourceResult);
                break;
            default:
                break;

        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        successPresenter.onError(error.getWebService(), error.getData().toString());
    }


}
