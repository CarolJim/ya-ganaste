package com.pagatodo.yaganaste.ui.adquirente;

import android.content.Context;
import android.os.Handler;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.PageResult;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.EnviarTicketCompraRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.FirmaDeVoucherRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.RegistroDongleRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.EnviarTicketCompraResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.FirmaDeVoucherResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.RegistroDongleResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.TransaccionEMVDepositResponse;
import com.pagatodo.yaganaste.interfaces.Command;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAccountView2;
import com.pagatodo.yaganaste.interfaces.IAdqIteractor;
import com.pagatodo.yaganaste.net.IRequestResult;

import java.io.Serializable;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ENVIAR_TICKET_COMPRA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.FIRMA_DE_VOUCHER;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTRO_DONGLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.TRANSACCIONES_EMV_DEPOSIT;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_REMOVE_CARD;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_TRANSACTION_APROVE;

/**
 * Created by flima on 17/04/2017.
 */

public class AdqInteractor implements Serializable, IAdqIteractor, IRequestResult {

    private String TAG = AdqInteractor.class.getName();
    private IAccountManager accountManager;

    public AdqInteractor(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }


    @Override
    public void registerDongle(String serial) {
        RegistroDongleRequest request = new RegistroDongleRequest(serial);
        /*try {
            ApiAdq.registroDongle(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }*/

        new Handler().postDelayed(new Runnable() {
            public void run() {
                accountManager.onSucces(REGISTRO_DONGLE, "Ejecución Exitosa");
            }
        }, DELAY_MESSAGE_PROGRESS * 2);
    }

    @Override
    public void initPayment(final TransaccionEMVDepositRequest request) {
        /*try {
            ApiAdq.transaccionEMVDeposit(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }*/

        new Handler().postDelayed(new Runnable() {
            public void run() {

                TransactionAdqData result = TransactionAdqData.getCurrentTransaction();
                result.setStatusTransaction(ADQ_TRANSACTION_APROVE);
                result.setResponseCode(0);
                PageResult pageResult = new PageResult(R.mipmap.icon_validate_green,"Aprobada","El Pago Fue Completado\nCorrectamente.",false);
                /*PageResult pageResult = new PageResult(R.drawable.error_icon,
                        "Ocurrió un error",
                        "Tuvimos un Problema\nProcesando la Transacción.",
                        true);*/
                pageResult.setNamerBtnPrimary("Continuar");
                //pageResult.setNamerBtnSecondary("Llamar");
                pageResult.setActionBtnPrimary(new Command() {
                    @Override
                    public void action(Context context, Object... params) {
                        IAccountView2 viewInterface = (IAccountView2) params[0];
                        viewInterface.nextStepRegister(EVENT_GO_REMOVE_CARD, "Ejecución Éxitosa");
                    }
                });

                /*
                pageResult.setActionBtnSecondary(new Command() {
                    @Override
                    public void action(final Context context, Object... params) {
                        //IAccountView2 viewInterface = (IAccountView2) params[0];
                        //viewInterface.nextStepRegister(EVENT_GO_MAINTAB,"Ejecución Éxitosa");
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "5555555555"));
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ValidatePermissions.checkSinglePermissionWithExplanation((Activity) context,
                                    Manifest.permission.CALL_PHONE,"Solicitud de Permisos","Debes permitir realizar llamadas",new DialogDoubleActions(){
                                        @Override
                                        public void actionConfirm(Object... params) {
                                            ValidatePermissions.openDetailsApp((Activity) context, Constants.PERMISSION_GENERAL);
                                        }

                                        @Override
                                        public void actionCancel(Object... params) {
                                        }
                                    },123);
                            return;
                        }
                        ((Activity) context).startActivity(intent);
                    }
                });*/

                /*pageResult.setActionBtnSecondary(new Command() {
                    @Override
                    public void action(Context context,Object[] data) {
                        ((Activity)context).finish(); // Finalizamos la AdqActivity//TODO regresar al fragment GetAmount precargado.
                        //Borramos los datos de la transacción
                        TransactionAdqData.getCurrentTransaction().resetCurrentTransaction();
                    }
                });*/

                result.setPageResult(pageResult);
                result.setTransaccionResponse(new TransaccionEMVDepositResponse());

                accountManager.onSucces(TRANSACCIONES_EMV_DEPOSIT,"Ejecución Exitosa");

            }
        }, DELAY_MESSAGE_PROGRESS*3);
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
                        IAccountView2 viewInterface = (IAccountView2) params[0];
                        viewInterface.nextStepRegister(EVENT_GO_MAINTAB, "Ejecución Éxitosa");
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
    private void processValidationDongle(DataSourceResult response) {
        RegistroDongleResponse data = (RegistroDongleResponse) response.getData();
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
    private void processTransactionResult(DataSourceResult response) {
        TransaccionEMVDepositResponse data = (TransaccionEMVDepositResponse) response.getData();
        if(data.getError().getId().equals(ADQ_CODE_OK)){
            accountManager.onSucces(response.getWebService(),data.getError().getMessage());
        }else{
            accountManager.onError(response.getWebService(),data.getError().getMessage());//Retornamos mensaje de error.
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