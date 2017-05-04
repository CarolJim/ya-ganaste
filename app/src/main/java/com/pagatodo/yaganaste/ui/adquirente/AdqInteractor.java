package com.pagatodo.yaganaste.ui.adquirente;

import android.content.Context;
import android.os.Handler;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.PageResult;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
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
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.IAdqIteractor;
import com.pagatodo.yaganaste.interfaces.enums.AccountOperation;
import com.pagatodo.yaganaste.net.ApiAdq;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.net.RequestHeaders;

import java.io.Serializable;

import static com.pagatodo.yaganaste.interfaces.enums.AccountOperation.LOGIN_ADQ_PAYMENT;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ENVIAR_TICKET_COMPRA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.FIRMA_DE_VOUCHER;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTRO_DONGLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.TRANSACCIONES_EMV_DEPOSIT;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_REMOVE_CARD;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_ACCES_DENIED;
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_TRANSACTION_APROVE;
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_TRANSACTION_ERROR;
import static com.pagatodo.yaganaste.utils.Recursos.KSN_LECTOR;

/**
 * Created by flima on 17/04/2017.
 */

public class AdqInteractor implements Serializable, IAdqIteractor, IRequestResult {

    private final int MAX_REINTENTOS = 3;
    private String TAG = AdqInteractor.class.getName();
    private IAccountManager accountManager;
    private AccountOperation accountOperation;
    private Preferencias prefs = App.getInstance().getPrefs();
    private int retryLogin = 0;

    public AdqInteractor(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public void loginAdq() {

        SingletonUser singletonUser = SingletonUser.getInstance();
        LoginAdqRequest request = new LoginAdqRequest(
                singletonUser.getDataUser().getUsuario().getPetroNumero(),
                singletonUser.getDataUser().getUsuario().getClaveAgente());
        try {
            ApiAdq.loginAdq(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void registerDongle() {

        RequestHeaders.setTokenAdq("383dedf37ef830ae351849c681dc9db70ce5745a714ac6d43983dcd0b2a90887");
        RequestHeaders.setIdCuenta("11040");
//        if(!RequestHeaders.getTokenAdq().isEmpty()){
            String serial = prefs.loadData(KSN_LECTOR);
            RegistroDongleRequest request = new RegistroDongleRequest(serial);
            try {
                ApiAdq.registroDongle(request,this);
            } catch (OfflineException e) {
                e.printStackTrace();
            }
//        }else{
//            accountOperation = LOGIN_ADQ_PAYMENT;
//            loginAdq();
//        }

// FLUJO DUMMY
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                accountManager.onSucces(REGISTRO_DONGLE, "Ejecución Exitosa");
//            }
//        }, DELAY_MESSAGE_PROGRESS * 2);
    }

    @Override
    public void initPayment(final TransaccionEMVDepositRequest request) {
        try {
            ApiAdq.transaccionEMVDeposit(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }

//FLUJO DUMMY
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//
//                TransactionAdqData result = TransactionAdqData.getCurrentTransaction();
//                result.setStatusTransaction(ADQ_TRANSACTION_APROVE);
//                result.setResponseCode(0);
//                PageResult pageResult = new PageResult(R.mipmap.icon_validate_green,"Aprobada","El Pago Fue Completado\nCorrectamente.",false);
//                /*PageResult pageResult = new PageResult(R.drawable.error_icon,
//                        "Ocurrió un error",
//                        "Tuvimos un Problema\nProcesando la Transacción.",
//                        true);*/
//                pageResult.setNamerBtnPrimary("Continuar");
//                //pageResult.setNamerBtnSecondary("Llamar");
//                pageResult.setActionBtnPrimary(new Command() {
//                    @Override
//                    public void action(Context context, Object... params) {
//                        INavigationView viewInterface = (INavigationView) params[0];
//                        viewInterface.nextScreen(EVENT_GO_REMOVE_CARD, "Ejecución Éxitosa");
//                    }
//                });
//
//                /*
//                pageResult.setActionBtnSecondary(new Command() {
//                    @Override
//                    public void action(final Context context, Object... params) {
//                        //INavigationView viewInterface = (INavigationView) params[0];
//                        //viewInterface.nextScreen(EVENT_GO_MAINTAB,"Ejecución Éxitosa");
//                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "5555555555"));
//                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                            ValidatePermissions.checkSinglePermissionWithExplanation((Activity) context,
//                                    Manifest.permission.CALL_PHONE,"Solicitud de Permisos","Debes permitir realizar llamadas",new DialogDoubleActions(){
//                                        @Override
//                                        public void actionConfirm(Object... params) {
//                                            ValidatePermissions.openDetailsApp((Activity) context, Constants.PERMISSION_GENERAL);
//                                        }
//
//                                        @Override
//                                        public void actionCancel(Object... params) {
//                                        }
//                                    },123);
//                            return;
//                        }
//                        ((Activity) context).startActivity(intent);
//                    }
//                });*/
//
//                /*pageResult.setActionBtnSecondary(new Command() {
//                    @Override
//                    public void action(Context context,Object[] data) {
//                        ((Activity)context).finish(); // Finalizamos la AdqActivity//TODO regresar al fragment GetAmount precargado.
//                        //Borramos los datos de la transacción
//                        TransactionAdqData.getCurrentTransaction().resetCurrentTransaction();
//                    }
//                });*/
//
//                result.setPageResult(pageResult);
//                result.setTransaccionResponse(new TransaccionEMVDepositResponse());
//
//                accountManager.onSucces(TRANSACCIONES_EMV_DEPOSIT,"Ejecución Exitosa");
//
//            }
//        }, DELAY_MESSAGE_PROGRESS*3);
    }

    @Override
    public void sendSignalVoucher(FirmaDeVoucherRequest request) {
        /*try {
            ApiAdq.firmaDeVoucher(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }*/

        new Handler().postDelayed(new Runnable() {
            public void run() {
                accountManager.onSucces(FIRMA_DE_VOUCHER,"Enviado");
            }
        }, DELAY_MESSAGE_PROGRESS);
    }

    @Override
    public void sendTicket(EnviarTicketCompraRequest request) {
        /*try {
            ApiAdq.enviarTicketCompra(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }*/

        new Handler().postDelayed(new Runnable() {
            public void run() {

                TransactionAdqData result = TransactionAdqData.getCurrentTransaction();
                PageResult pageResult = new PageResult(R.mipmap.ic_validate_blue, "¡Listo!", "El Recibo Fue\nEnviado con Éxito.", false);
                pageResult.setActionBtnPrimary(new Command() {
                    @Override
                    public void action(Context context, Object... params) {
                        INavigationView viewInterface = (INavigationView) params[0];
                        viewInterface.nextScreen(EVENT_GO_MAINTAB, "Ejecución Éxitosa");
                        //Borramos los datos de la transacción
                        TransactionAdqData.getCurrentTransaction().resetCurrentTransaction();

                    }
                });

                pageResult.setNamerBtnPrimary("Continuar");
                result.setPageResult(pageResult);
                result.setTransaccionResponse(new TransaccionEMVDepositResponse());

                accountManager.onSucces(ENVIAR_TICKET_COMPRA,"Ejecución Exitosa");
            }
        }, DELAY_MESSAGE_PROGRESS*3);
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

        switch(dataSourceResult.getWebService()) {

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

            case ENVIAR_TICKET_COMPRA:
                processSendTicket(dataSourceResult);
                break;

            default:
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        accountManager.onError(error.getWebService(),error.getData().toString());
    }

    /**
     * Método para procesar la respuesta del servicio respecto al formato de la contraseña.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processLoginAdq(DataSourceResult response) {
        LoginAdqResponse data = (LoginAdqResponse) response.getData();
        if(data.getToken() != null && !data.getToken().isEmpty()){ // Token devuelto
            RequestHeaders.setTokenAdq(data.getToken());
            RequestHeaders.setIdCuentaAdq(data.getId_user());
            switch (accountOperation){
                    case LOGIN_ADQ_PAYMENT: // Login anterior a validar Dongle.
                        registerDongle();
                        break;
            }

        }else{

        }
    }


    /**
     * Método para procesar la respuesta del servicio respecto al formato de la contraseña.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processValidationDongle(DataSourceResult response) {
        RegistroDongleResponse data = (RegistroDongleResponse) response.getData();
        if(data.getId().equals(ADQ_CODE_OK)){
            accountManager.onSucces(response.getWebService(),data.getMessage());
        }
//        else if(data.getId().equals(ADQ_ACCES_DENIED)) { // Acceso no autorizado
//            if(retryLogin < MAX_REINTENTOS){
//                retryLogin++;
//                accountOperation = LOGIN_ADQ_PAYMENT;
//                loginAdq();// Realizamos login nuevamente.
//            }else {
//                retryLogin = 0;
//                accountManager.onError(response.getWebService(),data.getMessage());//Retornamos mensaje de error.
//            }
//        }
        else{
            accountManager.onError(response.getWebService(),data.getMessage());
            //accountManager.onError(response.getWebService(),data.getMessage());//Retornamos mensaje de error.
        }
    }

    /**
     * Método para procesar la respuesta del servicio respecto al formato de la contraseña.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processTransactionResult(DataSourceResult response) {
        TransaccionEMVDepositResponse data = (TransaccionEMVDepositResponse) response.getData();
        TransactionAdqData result = TransactionAdqData.getCurrentTransaction();
        switch (data.getError().getId()){
            case ADQ_CODE_OK:
                result.setStatusTransaction(ADQ_TRANSACTION_ERROR);
                PageResult pageResult = new PageResult(R.drawable.error_icon,
                        App.getInstance().getString(R.string.title_cancelar),
                        data.getError().getMessage(),
                        true);

                pageResult.setNamerBtnPrimary(App.getInstance().getString(R.string.title_cancelar));
                pageResult.setNamerBtnSecondary(App.getInstance().getString(R.string.title_reintentar));
                pageResult.setActionBtnPrimary(new Command() {
                    @Override
                    public void action(Context context, Object... params) {

                        INavigationView viewInterface = (INavigationView) params[0];
                        viewInterface.nextScreen(EVENT_GO_MAINTAB,"");
                        TransactionAdqData.getCurrentTransaction().resetCurrentTransaction();
                    }
                });

                result.setPageResult(pageResult);
                accountManager.onSucces(response.getWebService(),data.getError().getMessage());
                break;
            default:
                result.setStatusTransaction(ADQ_TRANSACTION_ERROR);
                PageResult pageResultError = new PageResult(R.drawable.error_icon,
                        "Ocurrió un error",
                        data.getError().getMessage(),
                        true);

                pageResultError.setNamerBtnPrimary(App.getInstance().getString(R.string.title_cancelar));
                pageResultError.setNamerBtnSecondary(App.getInstance().getString(R.string.title_reintentar));
                pageResultError.setActionBtnPrimary(new Command() {
                    @Override
                    public void action(Context context, Object... params) {

                        INavigationView viewInterface = (INavigationView) params[0];
                        viewInterface.nextScreen(EVENT_GO_MAINTAB,"");
                        TransactionAdqData.getCurrentTransaction().resetCurrentTransaction();
                    }
                });

                pageResultError.setActionBtnSecondary(new Command() {
                    @Override
                    public void action(Context context, Object... params) {
                        INavigationView viewInterface = (INavigationView) params[0];
                        viewInterface.nextScreen(EVENT_GO_MAINTAB,"");
                        TransactionAdqData.getCurrentTransaction().resetDataToRetry(); // Reintentamos
                    }
                });

                result.setPageResult(pageResultError);

                accountManager.onError(response.getWebService(),data.getError().getMessage());//Retornamos mensaje de error.
                break;
        }
    }

    /**
     * Método para procesar la respuesta del servicio respecto al formato de la contraseña.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processSendSignal(DataSourceResult response) {
        FirmaDeVoucherResponse data = (FirmaDeVoucherResponse) response.getData();
        if(data.getId().equals(ADQ_CODE_OK)){
            accountManager.onSucces(response.getWebService(),data.getMessage());
        }else{
            accountManager.onError(response.getWebService(),data.getMessage());//Retornamos mensaje de error.
        }
    }

    /**
     * Método para procesar la respuesta del servicio respecto al formato de la contraseña.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processSendTicket(DataSourceResult response) {
        EnviarTicketCompraResponse data = (EnviarTicketCompraResponse) response.getData();
        if(data.getId().equals(ADQ_CODE_OK)){
            accountManager.onSucces(response.getWebService(),data.getMessage());
        }else{
            accountManager.onError(response.getWebService(),data.getMessage());//Retornamos mensaje de error.
        }
    }

}