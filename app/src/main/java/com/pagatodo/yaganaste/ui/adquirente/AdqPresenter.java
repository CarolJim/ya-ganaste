package com.pagatodo.yaganaste.ui.adquirente;

import android.os.Handler;
import android.util.Log;

import com.pagatodo.yaganaste.data.model.TransactionAdqResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.LoginAdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.IAccountView2;
import com.pagatodo.yaganaste.interfaces.IAdqAccountIteractor;
import com.pagatodo.yaganaste.interfaces.IAdqAccountPresenter;
import com.pagatodo.yaganaste.interfaces.IAdqIteractor;
import com.pagatodo.yaganaste.interfaces.IAdqPresenter;
import com.pagatodo.yaganaste.interfaces.IAdqRegisterView;
import com.pagatodo.yaganaste.interfaces.IAdqTransactionRegisterView;
import com.pagatodo.yaganaste.interfaces.IUploadDocumentsView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui.account.AccountAdqInteractor;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ENVIAR_TICKET_COMPRA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.FIRMA_DE_VOUCHER;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTRO_DONGLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.TRANSACCIONES_EMV_DEPOSIT;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_DETAIL_TRANSACTION;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_TRANSACTION_RESULT;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;

/**
 * Created by flima on 17/04/2017.
 */

public  class AdqPresenter implements IAdqPresenter, IAccountManager {
    private String TAG = AdqPresenter.class.getName();
    private IAdqIteractor adqInteractor;
    private IAccountView2 iAdqView;

    public AdqPresenter(IAccountView2 iAdqView) {
        this.iAdqView = iAdqView;
        adqInteractor = new AdqInteractor(this);
    }

    @Override
    public void validateDongle(String serial) {
        iAdqView.showLoader("Validando Lector...");
        adqInteractor.registerDongle(serial);
    }

    @Override
    public void initTransaction() {
        iAdqView.showLoader("Estamos en Proceso de Cobro");
        adqInteractor.initPayment(null);

    }

    @Override
    public void sendSignature() {
        iAdqView.showLoader("Enviando Firma");
        adqInteractor.sendSignalVoucher(null);
    }

    @Override
    public void sendTicket() {
        iAdqView.showLoader("Enviando Ticket");
        adqInteractor.sendTicket(null);
    }

    @Override
    public void goToNextStepAccount(String event,Object data) {
        iAdqView.hideLoader();
        iAdqView.nextStepRegister(event,data);
    }

    @Override
    public void onError(WebService ws,Object error) {
        iAdqView.hideLoader();
        if (ws == TRANSACCIONES_EMV_DEPOSIT) {
            ((IAdqTransactionRegisterView) iAdqView).transactionResult(error.toString());
        }else  if (ws == FIRMA_DE_VOUCHER) {
            iAdqView.showError(error);
            iAdqView.nextStepRegister(EVENT_GO_DETAIL_TRANSACTION, error);
        }else if(ws == ENVIAR_TICKET_COMPRA){
                iAdqView.nextStepRegister(EVENT_GO_TRANSACTION_RESULT,error);
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
            iAdqView.nextStepRegister(EVENT_GO_DETAIL_TRANSACTION,data);
        }else if(ws == ENVIAR_TICKET_COMPRA){
            iAdqView.hideLoader();
            iAdqView.nextStepRegister(EVENT_GO_TRANSACTION_RESULT,data);
        }
    }
}
