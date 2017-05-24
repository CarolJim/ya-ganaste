package com.pagatodo.yaganaste.ui.payments.interactors;

import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EnviarTicketTAEPDSRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EnviarTicketTAEPDSResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.EjecutarTransaccionResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity;
import com.pagatodo.yaganaste.ui.payments.interactors.interfaces.IPaymentsSuccessInteractor;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentsProcessingManager;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentsSuccessManaget;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ENVIAR_TICKET_TAEPDS;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by jmhernandez on 22/05/2017.
 */

public class PaymentsSuccessInteractor  implements IPaymentsSuccessInteractor , IRequestResult{
    Context ctx;
    EjecutarTransaccionResponse result;
    PaymentsSuccessManaget accountManager;
    // TODO pendiente pasar el PaymentSuccess a al MVP
    public PaymentsSuccessInteractor(PaymentsSuccessManaget accountManager, Context context , EjecutarTransaccionResponse res) {
        this.accountManager = accountManager;
        ctx = context;
        result = res;
    }

    @Override
    public void validateMail(String mail) {
        if (mail != null && !mail.equals("")) {
            if (ValidateForm.isValidEmailAddress(mail)) {
                sendTicket(mail);
            } else {
                accountManager.onError(ENVIAR_TICKET_TAEPDS,App.getInstance().getString(R.string.datos_usuario_correo_formato));
              //  showSimpleDialog( getString(R.string.datos_usuario_correo_formato));
            }
        }else{
            onFinalize();
        }
    }


    private void sendTicket(String mail) {
        EnviarTicketTAEPDSRequest request = new EnviarTicketTAEPDSRequest();
        request.setEmail(mail);
        request.setIdTransaction(result.getData().getIdTransaccion());
        try {
            ApiAdtvo.enviarTicketTAEPDS(request, this);
            accountManager.hideLoader();
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(ENVIAR_TICKET_TAEPDS, App.getInstance().getString(R.string.no_internet_access));

        }
    }

    private void onFinalize() {

    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

        switch (dataSourceResult.getWebService()){

            case ENVIAR_TICKET_TAEPDS:
                processSendEmailTAEPDS(dataSourceResult);
                break;
            default:
                break;

        }
    }

    private void processSendEmailTAEPDS(DataSourceResult response) {
        EnviarTicketTAEPDSResponse data = (EnviarTicketTAEPDSResponse) response.getData();
        if(data.getCodigoRespuesta() == CODE_OK){
            accountManager.onSucces(ENVIAR_TICKET_TAEPDS,data.getMensaje());
        }else{
            accountManager.onError(ENVIAR_TICKET_TAEPDS,"Error" + data.getMensaje());
        }

    }

    @Override
    public void onFailed(DataSourceResult error) {
        accountManager.onError(error.getWebService(),error.getData().toString());
    }
}
