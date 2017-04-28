package com.pagatodo.yaganaste.ui.adquirente;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.IAdqIteractor;
import com.pagatodo.yaganaste.interfaces.IAdqPresenter;
import com.pagatodo.yaganaste.interfaces.IAdqTransactionRegisterView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ENVIAR_TICKET_COMPRA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.FIRMA_DE_VOUCHER;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTRO_DONGLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.TRANSACCIONES_EMV_DEPOSIT;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_DETAIL_TRANSACTION;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_TRANSACTION_RESULT;
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
    public void sendSignature() {
        iAdqView.showLoader("Enviando Firma");
        adqInteractor.sendSignalVoucher(null);
    }

    @Override
    public void sendTicket(String email) {
            iAdqView.showLoader("Enviando Ticket");
            adqInteractor.sendTicket(null);
    }

    @Override
    public void goToNextStepAccount(String event,Object data) {
        iAdqView.hideLoader();
        iAdqView.nextScreen(event,data);
    }

    @Override
    public void onError(WebService ws,Object error) {
        iAdqView.hideLoader();
        if (ws == TRANSACCIONES_EMV_DEPOSIT) {
            ((IAdqTransactionRegisterView) iAdqView).transactionResult(error.toString());
        }else  if (ws == FIRMA_DE_VOUCHER) {
            iAdqView.showError(error);
            iAdqView.nextScreen(EVENT_GO_DETAIL_TRANSACTION, error);
        }else if(ws == ENVIAR_TICKET_COMPRA){
                iAdqView.nextScreen(EVENT_GO_TRANSACTION_RESULT,error);
        }else{
            iAdqView.showError(error);
        }
    }

    @Override
    public void onSucces(WebService ws,Object data) {
        if (ws == REGISTRO_DONGLE) {
            ((IAdqTransactionRegisterView) iAdqView).dongleValidated();
        }else if(ws == TRANSACCIONES_EMV_DEPOSIT){
            ((IAdqTransactionRegisterView) iAdqView).transactionResult(data.toString());
        }else if(ws == FIRMA_DE_VOUCHER){
            iAdqView.hideLoader();
            iAdqView.nextScreen(EVENT_GO_DETAIL_TRANSACTION,data);
        }else if(ws == ENVIAR_TICKET_COMPRA){
            iAdqView.hideLoader();
            iAdqView.nextScreen(EVENT_GO_TRANSACTION_RESULT,data);
        }
    }
}
