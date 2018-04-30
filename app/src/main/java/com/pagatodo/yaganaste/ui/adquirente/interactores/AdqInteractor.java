package com.pagatodo.yaganaste.ui.adquirente.interactores;

import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.PageResult;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.CancelaTransaccionDepositoEmvRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.EnviarTicketCompraRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.FirmaDeVoucherRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.LoginAdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.RegistroDongleRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.EnviarTicketCompraResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.FirmaDeVoucherResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.LoginAdqResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.RegistroDongleResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.TransaccionEMVDepositResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.Command;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAdqIteractor;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.enums.AccountOperation;
import com.pagatodo.yaganaste.net.ApiAdq;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.utils.NumberCalcTextWatcher;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CANCELA_TRANSACTION_EMV_DEPOSIT;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ENVIAR_TICKET_COMPRA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ENVIAR_TICKET_COMPRA_AUTOM;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.FIRMA_DE_VOUCHER;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTRO_DONGLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.SHARED_TICKET_COMPRA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.TRANSACCIONES_EMV_DEPOSIT;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_RETRY_PAYMENT;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_GET_SIGNATURE;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_LOGIN_FRAGMENT;
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_TRANSACTION_APROVE;
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_TRANSACTION_ERROR;
import static com.pagatodo.yaganaste.utils.Recursos.DEBUG;
import static com.pagatodo.yaganaste.utils.Recursos.KSN_LECTOR;

/**
 * Created by flima on 17/04/2017.
 */

public class AdqInteractor implements Serializable, IAdqIteractor, IRequestResult {
    private final int MAX_REINTENTOS = 3;
    INavigationView iSessionExpired;
    private String TAg = getClass().getSimpleName();
    private String TAG = getClass().getSimpleName();
    private IAccountManager accountManager;
    private AccountOperation accountOperation;
    private Preferencias prefs = App.getInstance().getPrefs();
    private int retryLogin = 0;
    private Context context = App.getInstance().getApplicationContext();
    private CancelaTransaccionDepositoEmvRequest cancelaTransaccionDepositoEmvRequest;

    public AdqInteractor(IAccountManager accountManager, INavigationView iSessionExpired) {
        this.accountManager = accountManager;
        this.iSessionExpired = iSessionExpired;

    }

    @Override
    public void registerDongle() {
        String serial = prefs.loadData(KSN_LECTOR);
        RegistroDongleRequest request = new RegistroDongleRequest(serial);
        try {
            ApiAdq.registroDongle(request, this);
        } catch (OfflineException e) {
            accountManager.hideLoader();
            accountManager.onError(REGISTRO_DONGLE, context.getString(R.string.no_internet_access));
        }
    }

    @Override
    public void initCancelPayment(CancelaTransaccionDepositoEmvRequest request) {
        cancelaTransaccionDepositoEmvRequest = request;
        try {
            ApiAdq.cancelaTransaccionDepositoEmv(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.hideLoader();
            accountManager.onError(CANCELA_TRANSACTION_EMV_DEPOSIT, context.getString(R.string.no_internet_access));
        }
    }

    @Override
    public void initPayment(final TransaccionEMVDepositRequest request) {
        try {
            ApiAdq.transaccionEMVDeposit(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.hideLoader();
            accountManager.onError(TRANSACCIONES_EMV_DEPOSIT, context.getString(R.string.no_internet_access));
        }

    }

    @Override
    public void sendSignalVoucher(FirmaDeVoucherRequest request) {
        try {
            ApiAdq.firmaDeVoucher(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(FIRMA_DE_VOUCHER, context.getString(R.string.no_internet_access));
        }

        //TODO FLUJO DUMMY
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                accountManager.onSucces(FIRMA_DE_VOUCHER,"Enviado");
//            }
//        }, DELAY_MESSAGE_PROGRESS);
    }

    @Override
    public void sendTicket(EnviarTicketCompraRequest request, boolean applyAgent) {
        try {
            ApiAdq.enviarTicketCompra(request, this, applyAgent);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.hideLoader();
            accountManager.onError(applyAgent ? ENVIAR_TICKET_COMPRA_AUTOM : ENVIAR_TICKET_COMPRA, context.getString(R.string.no_internet_access));
        }
    }

    @Override
    public void enviarTicketCompraShare(EnviarTicketCompraRequest request) {
        try {
            ApiAdq.enviarTicketCompraShare(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.hideLoader();
            accountManager.onError(SHARED_TICKET_COMPRA, context.getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        switch (dataSourceResult.getWebService()) {

            case LOGIN_ADQ:
                processLoginAdq(dataSourceResult);
                break;

            case REGISTRO_DONGLE:
                processValidationDongle(dataSourceResult);
                break;
            case TRANSACCIONES_EMV_DEPOSIT:
                processTransactionResult(dataSourceResult);
                break;

            case FIRMA_DE_VOUCHER:
                processSendSignal(dataSourceResult);
                break;
            case ENVIAR_TICKET_COMPRA_AUTOM:
                processSendTicketAutom(dataSourceResult);
                break;
            case ENVIAR_TICKET_COMPRA:
                processSendTicket(dataSourceResult);
                break;
            case SHARED_TICKET_COMPRA:
                processSendTicketShared(dataSourceResult);
                break;

            case CANCELA_TRANSACTION_EMV_DEPOSIT:
                //processCancelAdq(dataSourceResult);
                processCancelAdq(dataSourceResult);
                break;

            default:
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        switch (error.getWebService()) {
            case REGISTRO_DONGLE:
                prefs.clearPreference(KSN_LECTOR);
                break;
            case SHARED_TICKET_COMPRA:
                // prefs.clearPreference(KSN_LECTOR);
                break;
        }
        accountManager.onError(error.getWebService(), error.getData().toString());
    }

    /**
     * @param response
     */
    private void processCancelAdq(DataSourceResult response) {
        TransaccionEMVDepositResponse data = (TransaccionEMVDepositResponse) response.getData();
        TransactionAdqData result = TransactionAdqData.getCurrentTransaction();
        String marcaBancaria = result.getTransaccionResponse().getMarcaTarjetaBancaria();
        switch (data.getError().getId()) {
            case ADQ_CODE_OK:
                result.setStatusTransaction(ADQ_TRANSACTION_APROVE);
                result.setResponseCode(0);
                result.setTransaccionResponse(data);
                PageResult pageResult = new PageResult(R.drawable.ic_done, context.getString(R.string.cancelation_success),
                        context.getString(R.string.cancelation_success_message), false);
                pageResult.setNamerBtnPrimary(context.getString(R.string.terminar));
                //pageResult.setNamerBtnSecondary("Llamar");
                pageResult.setActionBtnPrimary(new Command() {
                    @Override
                    public void action(Context context, Object... params) {
                        INavigationView viewInterface = (INavigationView) params[0];
                        viewInterface.nextScreen(DetailsActivity.EVENT_GO_TO_FINALIZE_SUCCESS, context.getString(R.string.cancelation_success));
                    }
                });
                pageResult.setBtnPrimaryType(PageResult.BTN_DIRECTION_NEXT);
                result.setPageResult(pageResult);
                if (!DEBUG) {
                    /*Answers.getInstance().logPurchase(new PurchaseEvent()
                            .putItemPrice(BigDecimal.valueOf(Double.parseDouble(cancelaTransaccionDepositoEmvRequest.getAmount()) * -1))
                            .putCurrency(Currency.getInstance("MXN"))
                            .putItemName(App.getContext().getString(R.string.ce_cobro_cancelado))
                            .putItemType(marcaBancaria == null ? "PagaTodo" : marcaBancaria));*/
                }
                cancelaTransaccionDepositoEmvRequest = null;
                accountManager.onSucces(response.getWebService(), data.getError().getMessage());
                break;
            default:
                result.setStatusTransaction(ADQ_TRANSACTION_ERROR);
                PageResult pageResultError = new PageResult(R.drawable.ic_cancel,
                        context.getString(R.string.title_error),
                        data.getError().getMessage(),
                        false);

                pageResultError.setNamerBtnPrimary(context.getString(R.string.title_aceptar));
                //pageResultError.setNamerBtnSecondary(App.getInstance().getString(R.string.title_reintentar));
                pageResultError.setActionBtnPrimary(new Command() {
                    @Override
                    public void action(Context context, Object... params) {

                        INavigationView viewInterface = (INavigationView) params[0];
                        viewInterface.nextScreen(DetailsActivity.EVENT_GO_TO_FINALIZE_ERROR, "");
                        TransactionAdqData.getCurrentTransaction().resetCurrentTransaction();
                    }
                });

                /*pageResultError.setActionBtnSecondary(new Command() {
                    @Override
                    public void action(Context context, Object... params) {
                        INavigationView viewInterface = (INavigationView) params[0];
                        viewInterface.nextScreen(DetailsActivity.EVENT_GO_TO_FINALIZE, "");
                        TransactionAdqData.getCurrentTransaction().resetDataToRetry(); // Reintentamos
                    }
                });*/
                pageResultError.setBtnPrimaryType(PageResult.BTN_ACTION_OK);
                //pageResultError.setBtnSecundaryType(PageResult.BTN_ACTION_OK);
                result.setPageResult(pageResultError);

                accountManager.onError(response.getWebService(), data.getError().getMessage());//Retornamos mensaje de error.
                break;
        }
    }

    /**
     * Método para procesar la respuesta del servicio respecto al formato de la contraseña.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processLoginAdq(DataSourceResult response) {
        LoginAdqResponse data = (LoginAdqResponse) response.getData();
        if (data.getToken() != null && !data.getToken().isEmpty()) { // Token devuelto
            RequestHeaders.setTokenAdq(data.getToken());
            RequestHeaders.setIdCuentaAdq(data.getId_user());
            switch (accountOperation) {
                case LOGIN_ADQ_PAYMENT: // Login anterior a validar Dongle.
                    registerDongle();
                    break;
            }

        } else {

        }
    }


    /**
     * Método para procesar la respuesta del servicio respecto al formato de la contraseña.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processValidationDongle(DataSourceResult response) {
        RegistroDongleResponse data = (RegistroDongleResponse) response.getData();
        if (data.getId().equals(ADQ_CODE_OK)) {
            accountManager.onSucces(response.getWebService(), data.getMessage());
//        else if(data.getId().equals(ADQ_ACCES_DENIED)) { // Acceso no autorizado
//            if(retryLogin < MAX_REINTENTOS){
//                retryLogin++;
//                accountOperation = LOGIN_ADQ_PAYMENT;
//                loginAdq();// Realizamos login nuevamente.
//            }else {
//                retryLogin = 0;
//                accountManager.onErrorValidateService(response.getWebService(),data.getMessage());//Retornamos mensaje de error.
//            }
//        }

        } /*else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            iSessionExpired.errorSessionExpired(response);
        } */ else {
            prefs.clearPreference(KSN_LECTOR);
            accountManager.onError(response.getWebService(), data.getMessage());
            //accountManager.onErrorValidateService(response.getWebService(),data.getMessage());//Retornamos mensaje de error.
        }
    }

    /**
     * Método para procesar la respuesta del servicio respecto al formato de la contraseña.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */

    private void processTransactionResult(DataSourceResult response) {
        TransaccionEMVDepositResponse data = (TransaccionEMVDepositResponse) response.getData();
        TransactionAdqData result = TransactionAdqData.getCurrentTransaction();
        String marcaBancaria = result.getTransaccionResponse().getMarcaTarjetaBancaria();
        switch (data.getError().getId()) {
            case ADQ_CODE_OK:
                result.setStatusTransaction(ADQ_TRANSACTION_APROVE);
                result.setResponseCode(0);
                result.setTransaccionResponse(data);
                PageResult pageResult = new PageResult(R.drawable.ic_check_success, context.getString(R.string.aprobada_cobro),
                        context.getString(R.string.aprobada_cobro), false);

                pageResult.setNamerBtnPrimary(context.getString(R.string.continuar));
                //pageResult.setNamerBtnSecondary("Llamar");
                pageResult.setActionBtnPrimary(new Command() {
                    @Override
                    public void action(Context context, Object... params) {
                        INavigationView viewInterface = (INavigationView) params[0];
                        viewInterface.nextScreen(EVENT_GO_GET_SIGNATURE, null);
                    }
                });
                pageResult.setBtnPrimaryType(PageResult.BTN_DIRECTION_NEXT);
                pageResult.setDescription("");
                result.setPageResult(pageResult);
                if (!DEBUG) {
                    /*Answers.getInstance().logPurchase(new PurchaseEvent()
                            .putItemPrice(BigDecimal.valueOf(Double.parseDouble(TransactionAdqData.getCurrentTransaction().getAmount())))
                            .putCurrency(Currency.getInstance("MXN"))
                            .putItemName(App.getContext().getString(R.string.ce_cobro_aceptado))
                            .putItemType(marcaBancaria == null ? "PagaTodo" : marcaBancaria)
                            .putSuccess(true));*/
                }
                accountManager.onSucces(response.getWebService(), data.getError().getMessage());
                break;
            /* Se realizo un cambio de EVENT_GO_REMOVE_CARD a EVENT_GO_GET_SIGNATURE para evitar
             animacion de quitar al tarjeta e ir directo a la firma
             Igualmente aqui podemos poner la informacion de Description en PageResult para que se muestre de acuero
             a la proramacion de Transaction Result Fragment
             */
            default:
                result.setStatusTransaction(ADQ_TRANSACTION_ERROR);
                PageResult pageResultError = new PageResult(R.drawable.ic_cancel,
                        context.getString(R.string.title_error),
                        data.getError().getMessage(),
                        true);

                pageResultError.setNamerBtnPrimary(App.getInstance().getString(R.string.title_cancelar));
                //pageResultError.setNamerBtnSecondary(App.getInstance().getString(R.string.title_reintentar));
                pageResultError.setActionBtnPrimary(new Command() {
                    @Override
                    public void action(Context context, Object... params) {

                        INavigationView viewInterface = (INavigationView) params[0];
                        viewInterface.nextScreen(EVENT_GO_MAINTAB, "");
                        TransactionAdqData.getCurrentTransaction().resetCurrentTransaction();
                    }
                });
                pageResultError.setNamerBtnSecondary(App.getInstance().getString(R.string.title_reintentar));
                pageResultError.setActionBtnSecondary(new Command() {
                    @Override
                    public void action(Context context, Object... params) {
                        INavigationView viewInterface = (INavigationView) params[0];
                        viewInterface.nextScreen(EVENT_RETRY_PAYMENT, "");
                        TransactionAdqData.getCurrentTransaction().resetDataToRetry(); // Reintentamos
                    }
                });
                pageResultError.setBtnPrimaryType(PageResult.BTN_ACTION_ERROR);
                pageResultError.setBtnSecundaryType(PageResult.BTN_ACTION_OK);
                result.setPageResult(pageResultError);
                if (!DEBUG) {
                    /*Answers.getInstance().logPurchase(new PurchaseEvent()
                            .putItemPrice(BigDecimal.valueOf(Double.parseDouble(TransactionAdqData.getCurrentTransaction().getAmount())))
                            .putCurrency(Currency.getInstance("MXN"))
                            .putItemName(App.getContext().getString(R.string.ce_cobro_rechazado))
                            .putItemType(marcaBancaria == null ? "PagaTodo" : marcaBancaria)
                            .putSuccess(false));*/
                }
                accountManager.onError(response.getWebService(), data.getError().getMessage());//Retornamos mensaje de error.
                break;
        }
    }

    /**
     * Método para procesar la respuesta del servicio respecto al formato de la contraseña.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processSendSignal(DataSourceResult response) {
        FirmaDeVoucherResponse data = (FirmaDeVoucherResponse) response.getData();
        if (data.getId().equals(ADQ_CODE_OK)) {
            accountManager.onSucces(response.getWebService(), data.getMessage());
        } /*else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            iSessionExpired.errorSessionExpired(response);
        } */ else {
            accountManager.onError(response.getWebService(), data.getMessage());//Retornamos mensaje de error.
        }
    }

    private void processSendTicketAutom(DataSourceResult response) {
        EnviarTicketCompraResponse data = (EnviarTicketCompraResponse) response.getData();
        if (data.getId().equals(ADQ_CODE_OK)) {
            accountManager.onSucces(response.getWebService(), data.getMessage());
        } else {
            accountManager.onError(response.getWebService(), data.getMessage());//Retornamos mensaje de error.
        }
    }

    /**
     * Método para procesar la respuesta del servicio respecto al formato de la contraseña.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processSendTicket(DataSourceResult response) {
        EnviarTicketCompraResponse data = (EnviarTicketCompraResponse) response.getData();
        if (data.getId().equals(ADQ_CODE_OK)) {
            TransactionAdqData result = TransactionAdqData.getCurrentTransaction();
            PageResult pageResult = new PageResult(R.drawable.ic_done, context.getString(R.string.listo),
                    context.getString(R.string.recibo_enviado_line), false);
            pageResult.setActionBtnPrimary(new Command() {
                @Override
                public void action(Context context, Object... params) {
                    INavigationView viewInterface = (INavigationView) params[0];

                    //Borramos los datos de la transacción
                    TransactionAdqData.getCurrentTransaction().resetCurrentTransaction();

                    /**
                     * Metodo para elimnar datos temporales de la calculadora, cuando tenemos ya una
                     * operacion completa
                     */
                    NumberCalcTextWatcher.cleanData();

                    viewInterface.nextScreen(EVENT_GO_LOGIN_FRAGMENT, null);
                }
            });

            pageResult.setNamerBtnPrimary(context.getString(R.string.continuar));
            pageResult.setBtnPrimaryType(PageResult.BTN_DIRECTION_NEXT);
            result.setPageResult(pageResult);
            result.setTransaccionResponse(new TransaccionEMVDepositResponse());
            accountManager.onSucces(response.getWebService(), data.getMessage());
        } /*else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            iSessionExpired.errorSessionExpired(response);
        } */ else {
            accountManager.onError(response.getWebService(), data.getMessage());//Retornamos mensaje de error.
        }
    }

    private void processSendTicketShared(DataSourceResult response) {
        EnviarTicketCompraResponse data = (EnviarTicketCompraResponse) response.getData();
        if (data.getId().equals(ADQ_CODE_OK)) {
            accountManager.onSucces(response.getWebService(), data.getMessage());
        } /*else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            iSessionExpired.errorSessionExpired(response);
        } */ else {
            accountManager.onError(response.getWebService(), data.getMessage());//Retornamos mensaje de error.
        }
    }
}