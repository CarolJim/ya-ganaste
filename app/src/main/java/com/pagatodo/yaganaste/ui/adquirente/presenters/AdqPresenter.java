package com.pagatodo.yaganaste.ui.adquirente.presenters;

import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.CancelaTransaccionDepositoEmvRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.CancellationData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.SignatureData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.TransaccionEMVDepositResponse;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAdqIteractor;
import com.pagatodo.yaganaste.interfaces.IAdqPresenter;
import com.pagatodo.yaganaste.interfaces.IAdqTransactionRegisterView;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui._manager.GenericPresenterMain;
import com.pagatodo.yaganaste.ui.adquirente.interactores.AdqInteractor;
import com.pagatodo.yaganaste.ui.adquirente.utils.UtilsAdquirente;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_DETAIL_TRANSACTION;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_TRANSACTION_RESULT;
import static com.pagatodo.yaganaste.ui.adquirente.utils.UtilsAdquirente.buildSignatureRequest;
import static com.pagatodo.yaganaste.utils.Recursos.CANCELATION_EMV;
import static com.pagatodo.yaganaste.utils.Recursos.KSN_LECTOR;
import static com.pagatodo.yaganaste.utils.StringUtils.createTicket;

/**
 * Created by flima on 17/04/2017.
 */

public class AdqPresenter extends GenericPresenterMain<IPreferUserGeneric> implements IAdqPresenter, IAccountManager {
    private String TAG = AdqPresenter.class.getName();
    private IAdqIteractor adqInteractor;
    private INavigationView iAdqView;
    private Preferencias prefs = App.getInstance().getPrefs();

    public AdqPresenter(INavigationView iAdqView) {
        this.iAdqView = iAdqView;
        this.adqInteractor = new AdqInteractor(this, iAdqView);
    }

    @Override
    public void setIView(IPreferUserGeneric iPreferUserGeneric) {
        super.setIView(iPreferUserGeneric);
    }

    @Override
    public void validateDongle(String serial) {
        iAdqView.showLoader(App.getContext().getString(R.string.validando_lector));
        prefs.saveData(KSN_LECTOR, serial);
        adqInteractor.registerDongle();

    }

    @Override
    public void initCancelation(TransaccionEMVDepositRequest request, DataMovimientoAdq dataMovimientoAdq) {
        String msg = App.getContext().getString(R.string.procesando_cancelacion);
        iAdqView.showLoader(msg);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getAdquirenteMovementDate(dataMovimientoAdq.getFecha()));
        java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        java.text.DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);

        CancellationData cancellationData = new CancellationData();
        cancellationData.setDatelOriginalTransaction(dateFormat.format(calendar.getTime()).replace("/", ""));//aaaammdd
        cancellationData.setIdOriginalTransaction(dataMovimientoAdq.getIdTransaction());
        cancellationData.setNoauthorizationOriginalTransaction(dataMovimientoAdq.getNoAutorizacion());
        cancellationData.setTicketOriginalTransaction(dataMovimientoAdq.getNoTicket());
        cancellationData.setFechaOriginalTransaction(dataMovimientoAdq.getFechaTransaccionOriginal());
        cancellationData.setTimeOriginalTransaction(hourFormat.format(calendar.getTime()).replace(":", ""));

        CancelaTransaccionDepositoEmvRequest cancelRequest = new CancelaTransaccionDepositoEmvRequest();
        cancelRequest.setNoSerie(request.getNoSerie());
        cancelRequest.setNoTicket(createTicket());
        cancelRequest.setAmount(dataMovimientoAdq.getMonto());
        cancelRequest.setSwipeData(request.getSwipeData());
        cancelRequest.setEMVTransaction(request.getIsEMVTransaction());
        cancelRequest.setCancellationData(cancellationData);
        cancelRequest.setTransactionDateTime(request.getTransactionDateTime());
        cancelRequest.setEmvData(request.getEmvData());
        cancelRequest.setTipoCliente(request.getTipoCliente());
        cancelRequest.setNoTransaction(Integer.toString(Utils.getTransactionSequence()));
        cancelRequest.setAccountDepositData(request.getAccountDepositData());
        cancelRequest.setImplicitData(request.getImplicitData());
        cancelRequest.setTipoCancelación(CANCELATION_EMV);

        adqInteractor.initCancelPayment(cancelRequest);
    }

    @Override
    public void initReverseTransaction(TransaccionEMVDepositRequest request, int typeReverse) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getAdquirenteMovementDate(request.getTransactionDateTime()));
        java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        java.text.DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        CancellationData cancellationData = new CancellationData();
        cancellationData.setDatelOriginalTransaction(dateFormat.format(calendar.getTime()).replace("/", ""));
        cancellationData.setTicketOriginalTransaction(request.getNoTicket());
        cancellationData.setTimeOriginalTransaction(hourFormat.format(calendar.getTime()).replace(":", ""));

        CancelaTransaccionDepositoEmvRequest cancelRequest = new CancelaTransaccionDepositoEmvRequest();
        cancelRequest.setNoSerie(request.getNoSerie());
        cancelRequest.setNoTicket(request.getNoTicket());
        cancelRequest.setAmount(request.getAmount());
        cancelRequest.setSwipeData(request.getSwipeData());
        cancelRequest.setEMVTransaction(request.getIsEMVTransaction());
        cancelRequest.setTransactionDateTime(request.getTransactionDateTime());
        cancelRequest.setEmvData(request.getEmvData());
        cancelRequest.setTipoCliente(request.getTipoCliente());
        cancelRequest.setNoTransaction(Integer.toString(Utils.getTransactionSequence()));
        cancelRequest.setAccountDepositData(request.getAccountDepositData());
        cancelRequest.setImplicitData(request.getImplicitData());
        cancelRequest.setTipoCancelación(typeReverse);
        cancelRequest.setCancellationData(cancellationData);
        adqInteractor.initReversePayment(cancelRequest);
    }

    @Override
    public void initTransaction(TransaccionEMVDepositRequest request, boolean signWithPin) {
        String msg = App.getContext().getString(R.string.procesando_cobro);
        iAdqView.showLoader(msg);
        adqInteractor.initPayment(request, signWithPin);
    }

    @Override
    public void initConsultBalance(TransaccionEMVDepositRequest request) {
        iAdqView.showLoader(App.getContext().getString(R.string.procesando_consulta));
        adqInteractor.initConsult(request);
    }

    @Override
    public void sendSignature(SignatureData signatureData) {
        iAdqView.showLoader(App.getContext().getString(R.string.enviando_firma));
        adqInteractor.sendSignalVoucher(buildSignatureRequest(TransactionAdqData.getCurrentTransaction().getTransaccionResponse().getId_transaction(), signatureData));
    }

    @Override
    public void sendTicket(String email, boolean applyAgent) {
        if (!applyAgent) iAdqView.showLoader(App.getContext().getString(R.string.enviando_ticket));
        adqInteractor.sendTicket(UtilsAdquirente.buildTicketRequest(
                TransactionAdqData.getCurrentTransaction().getTransaccionResponse().getId_transaction(),
                TransactionAdqData.getCurrentTransaction().getDescription(),
                email, applyAgent), applyAgent);
    }

    @Override
    public void sendTicket(String idTransicion, String name, String email, boolean applyAgent) {
        if (!applyAgent) iAdqView.showLoader(App.getContext().getString(R.string.enviando_ticket));
        adqInteractor.sendTicket(UtilsAdquirente.buildTicketRequest(idTransicion, name, email, applyAgent), applyAgent);
    }

    public void sendTicketShare(String emailToSend, String description, String idTransaction) {
        iAdqView.showLoader(App.getContext().getString(R.string.enviando_ticket));
        adqInteractor.enviarTicketCompraShare(UtilsAdquirente.buildTicketRequest(
                idTransaction,
                description,
                emailToSend, false));
    }

    @Override
    public void goToNextStepAccount(String event, Object data) {
        iAdqView.hideLoader();
        iAdqView.nextScreen(event, data);
    }

    @Override
    public void onError(WebService ws, Object error) {
        iAdqView.hideLoader();
        switch (ws) {
            case REGISTRO_DONGLE:
                iAdqView.showError(error);
                break;
            case TRANSACCIONES_EMV_DEPOSIT:
                iAdqView.showError(error.toString());
                if (!error.toString().equals(App.getContext().getString(R.string.no_internet_access))) {
                    ((IAdqTransactionRegisterView) iAdqView).onErrorTransaction();
                } else {
                    ((IAdqTransactionRegisterView) iAdqView).cancelTransactionChip();
                }
                break;
            case CONSULT_BALANCE_UYU:
                ((IAdqTransactionRegisterView) iAdqView).onErrorConsultSaldo(error.toString());
                break;
            case FIRMA_DE_VOUCHER:
                iAdqView.showError(error);
//                iAdqView.nextScreen(EVENT_GO_DETAIL_TRANSACTION, error);
                break;
            case ENVIAR_TICKET_COMPRA_AUTOM:
                if (BuildConfig.DEBUG)
                    Log.e(App.getContext().getString(R.string.app_name), "No se pudo mandar correo automático al agente");
                break;
            case ENVIAR_TICKET_COMPRA:
                iAdqView.showError(error);
//                iAdqView.nextScreen(EVENT_GO_TRANSACTION_RESULT,error);
                break;
            case SHARED_TICKET_COMPRA:
                iAdqView.showError(error);
//                iAdqView.nextScreen(EVENT_GO_TRANSACTION_RESULT,error);
                break;
            case CANCELA_TRANSACTION_EMV_DEPOSIT:
                ((IAdqTransactionRegisterView) iAdqView).transactionResult(error.toString(), "");
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
    public void onSuccesBalance() {

    }

    @Override
    public void onSuccesChangePass6(DataSourceResult dataSourceResult) {
    }

    @Override
    public void onSuccesBalanceAdq() {

    }

    @Override
    public void onSuccessBalanceStarbucks() {

    }

    @Override
    public void onSuccessDataPerson() {

    }

    @Override
    public void onSuccessDataPersonHomoError() {

    }

    @Override
    public void onHomonimiaDataPerson() {

    }

    @Override
    public void onSuccesStateCuenta() {

    }

    @Override
    public void onSucces(WebService ws, Object data) {
        switch (ws) {
            case REGISTRO_DONGLE:
                ((IAdqTransactionRegisterView) iAdqView).dongleValidated();
                break;
            case TRANSACCIONES_EMV_DEPOSIT:
                TransaccionEMVDepositResponse response = (TransaccionEMVDepositResponse) data;
                ((IAdqTransactionRegisterView) iAdqView).transactionResult(response.getError().toString(), response.getTlvResult());
                break;
            case CONSULT_BALANCE_UYU:
                TransaccionEMVDepositResponse responseBalance = (TransaccionEMVDepositResponse) data;
                ((IAdqTransactionRegisterView) iAdqView).transactionResult(responseBalance.getDescripcion() + "_" +
                        responseBalance.getSaldo() + "_" + responseBalance.getMaskedPan(), responseBalance.getTlvResult());
                break;
            case FIRMA_DE_VOUCHER:
                iAdqView.hideLoader();
                iAdqView.nextScreen(EVENT_GO_DETAIL_TRANSACTION, data);
                break;
            case ENVIAR_TICKET_COMPRA_AUTOM:
                if (BuildConfig.DEBUG)
                    Log.i(App.getContext().getString(R.string.app_name), "Notificación exitosa al Agente");
                break;
            case ENVIAR_TICKET_COMPRA:
                iAdqView.hideLoader();
                iAdqView.nextScreen(EVENT_GO_TRANSACTION_RESULT, data);
                break;
            case SHARED_TICKET_COMPRA:
                iAdqView.hideLoader();
                iAdqView.nextScreen(EVENT_GO_TRANSACTION_RESULT, data);
                break;
            case CANCELA_TRANSACTION_EMV_DEPOSIT:
                ((IAdqTransactionRegisterView) iAdqView).transactionResult(data.toString(), "");
                break;
        }
    }
}
