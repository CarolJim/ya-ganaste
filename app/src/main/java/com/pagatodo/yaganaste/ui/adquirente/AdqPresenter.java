package com.pagatodo.yaganaste.ui.adquirente;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.SignatureData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarSaldoResponse;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.IAdqIteractor;
import com.pagatodo.yaganaste.interfaces.IAdqPresenter;
import com.pagatodo.yaganaste.interfaces.IAdqTransactionRegisterView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui.adquirente.utils.UtilsAdquirente;
import com.pagatodo.yaganaste.utils.Utils;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ENVIAR_TICKET_COMPRA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.FIRMA_DE_VOUCHER;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTRO_DONGLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.TRANSACCIONES_EMV_DEPOSIT;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_DETAIL_TRANSACTION;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_TRANSACTION_RESULT;
import static com.pagatodo.yaganaste.ui.adquirente.utils.UtilsAdquirente.buildSignatureRequest;
import static com.pagatodo.yaganaste.utils.Recursos.KSN_LECTOR;

/**
 * Created by flima on 17/04/2017.
 */

public  class AdqPresenter implements IAdqPresenter, IAccountManager {
    private String TAG = AdqPresenter.class.getName();
    private IAdqIteractor adqInteractor;
    private INavigationView iAdqView;
    private Preferencias prefs = App.getInstance().getPrefs();

    public AdqPresenter(INavigationView iAdqView) {
        this.iAdqView = iAdqView;
        adqInteractor = new AdqInteractor(this);
    }

    @Override
    public void validateDongle(String serial) {
        iAdqView.showLoader("Validando Lector...");
        prefs.saveData(KSN_LECTOR, serial);
        adqInteractor.registerDongle();

    }

    @Override
    public void initTransaction(TransaccionEMVDepositRequest request) {
        iAdqView.showLoader("Estamos en Proceso de Cobro");
        adqInteractor.initPayment(request);
    }

    @Override
    public void sendSignature(SignatureData signatureData) {
        iAdqView.showLoader("Enviando Firma");
        adqInteractor.sendSignalVoucher(buildSignatureRequest(TransactionAdqData.getCurrentTransaction().getTransaccionResponse().getId_transaction(), signatureData));
    }

    @Override
    public void sendTicket(String email) {
            iAdqView.showLoader("Enviando Ticket");
            adqInteractor.sendTicket(UtilsAdquirente.buildTicketRequest(
                    TransactionAdqData.getCurrentTransaction().getTransaccionResponse().getId_transaction(),
                    TransactionAdqData.getCurrentTransaction().getDescription(),
                    email));
    }

    @Override
    public void goToNextStepAccount(String event,Object data) {
        iAdqView.hideLoader();
        iAdqView.nextScreen(event,data);
    }

    @Override
    public void onError(WebService ws,Object error) {
        iAdqView.hideLoader();
        switch (ws){
            case REGISTRO_DONGLE:
                iAdqView.showError(error);
                break;
            case TRANSACCIONES_EMV_DEPOSIT:
                ((IAdqTransactionRegisterView) iAdqView).transactionResult(error.toString());
                break;
            case FIRMA_DE_VOUCHER:
                iAdqView.showError(error);
//                iAdqView.nextScreen(EVENT_GO_DETAIL_TRANSACTION, error);
                break;
            case ENVIAR_TICKET_COMPRA:
                iAdqView.showError(error);
//                iAdqView.nextScreen(EVENT_GO_TRANSACTION_RESULT,error);
                break;
            default:
                iAdqView.showError(error);
                break;
        }
    }

    @Override
    public void hideLoader() {
        iAdqView.hideLoader();
    }

    @Override
    public void onSuccesBalance(ConsultarSaldoResponse response) {

    }

    @Override
    public void onSucces(WebService ws,Object data) {
        switch (ws){
            case REGISTRO_DONGLE:
                ((IAdqTransactionRegisterView) iAdqView).dongleValidated();
                break;
            case TRANSACCIONES_EMV_DEPOSIT:
                ((IAdqTransactionRegisterView) iAdqView).transactionResult(data.toString());
                break;
            case FIRMA_DE_VOUCHER:
                iAdqView.hideLoader();
                iAdqView.nextScreen(EVENT_GO_DETAIL_TRANSACTION,data);
                break;
            case ENVIAR_TICKET_COMPRA:
                iAdqView.hideLoader();
                iAdqView.nextScreen(EVENT_GO_TRANSACTION_RESULT,data);
                break;
        }
    }
}
