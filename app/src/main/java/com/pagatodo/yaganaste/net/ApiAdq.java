package com.pagatodo.yaganaste.net;

import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.AutenticaNipRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.CancelaTransaccionDepositoEmvRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ConsultaSesionAgenteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.DetalleMovimientoRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.EnviarTicketCompraRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.FirmaDeVoucherRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.LoginAdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ReembolsoDataRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.RegistraNipRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.RegistraNotificacionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.RegistroDeviceDataRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.RegistroDongleRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ResumenMovimientosMesRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.SaldoRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TypeRepaymentRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.AutenticaNIPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ConsultaSaldoCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ConsultaSesionAgenteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataResultAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.EnviarTicketCompraResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.FirmaDeVoucherResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.LoginAdqResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneDatosCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneTiposReembolsoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ReembolsoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.RegistraNIPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.RegistraNotificacionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.RegistroDeviceDataResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.RegistroDongleResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ResumenMovimientosAdqResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.TransaccionEMVDepositResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.GetResumenDiaResponse;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Agentes;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.METHOD_GET;
import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.METHOD_POST;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.AUTENTICA_NIP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CANCELA_TRANSACTION_EMV_DEPOSIT;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO_ADQ;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO_ADQ_ADM;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTA_MOVIMIENTOS_MES_ADQ;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTA_SESION_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULT_BALANCE_UYU;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.DETAIL_MOVEMENT;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ENVIAR_TICKET_COMPRA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ENVIAR_TICKET_COMPRA_AUTOM;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.FIRMA_DE_VOUCHER;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.GET_RESUMENDIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.GET_TYPE_REPAYMENT;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.LOGIN_ADQ;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTIENE_DATOS_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTRA_NIP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTRA_NOTIFICACION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTRO_DEVICE_DATA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTRO_DONGLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REVERSA_TRANSACTION_EMV_DEPOSIT;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.SEND_REEMBOLSO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.SHARED_TICKET_COMPRA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.TRANSACCIONES_EMV_DEPOSIT;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.UPDATE_TYPE_REPAYMENT;
import static com.pagatodo.yaganaste.utils.Recursos.ID_COMERCIOADQ;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ROL;
import static com.pagatodo.yaganaste.utils.Recursos.URL_SERVER_ADQ;

/**
 * Created by flima on 17/03/2017.
 * <p>
 * Clase para gestionar el WS de Adquiriente.
 */

public class ApiAdq extends Api {

    /**
     * Verifica que el usuario\contraseña este dado de alta en la plataforma PagaTodo,
     * además de verificar que la versión del aplicativo coincida con la versión del web service.
     *
     * @param request {@link LoginAdqRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void loginAdq(LoginAdqRequest request, IRequestResult result) throws OfflineException {
        NetFacade.consumeWS(LOGIN_ADQ,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqLogin),
                getHeadersAdq(), request, LoginAdqResponse.class, result);
    }

    /**
     * Correlaciona un dongle con el usuario.
     *
     * @param request {@link RegistroDongleRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void registroDongle(RegistroDongleRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        if (App.getInstance().getPrefs().loadDataInt(ID_ROL) == 129 || RequestHeaders.getIdCuentaAdq().equals("")) {
            String idUserAdq = "0";
            try {
                idUserAdq = new DatabaseManager().getIdUsuarioAdqRolOperador() + "";
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            RequestHeaders.setIdCuentaAdq(idUserAdq);
        }
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());

        NetFacade.consumeWS(REGISTRO_DONGLE,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqRegisterDongle),
                headers, request, RegistroDongleResponse.class, result);
    }

    /**
     * Registra el NIP del usuario tendrá que digitar antes de enviar una compra.
     *
     * @param request {@link RegistraNipRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void registraNIP(RegistraNipRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        NetFacade.consumeWS(REGISTRA_NIP,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqRegisterNIP),
                headers, request, RegistraNIPResponse.class, result);
    }

    /**
     * Consulta el estado de la sesión del agente.
     *
     * @param request {@link ConsultaSesionAgenteRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void consultaSesionAgente(ConsultaSesionAgenteRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        NetFacade.consumeWS(CONSULTA_SESION_AGENTE,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqGetSessionAgente),
                headers, request, ConsultaSesionAgenteResponse.class, result);
    }

    /**
     * Registra los datos relacionados con el dispositivo.
     *
     * @param request {@link RegistroDeviceDataRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void registroDeviceData(RegistroDeviceDataRequest request, IRequestResult result) throws OfflineException {
        NetFacade.consumeWS(REGISTRO_DEVICE_DATA,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqRegisterDeviceData),
                getHeadersAdq(), request, RegistroDeviceDataResponse.class, result);
    }

    /**
     * Registra una notificación.
     *
     * @param request {@link RegistraNotificacionRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void registraNotificacion(RegistraNotificacionRequest request, IRequestResult result) throws OfflineException {
        NetFacade.consumeWS(REGISTRA_NOTIFICACION,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqRegisterNotification),
                getHeadersAdq(), request, RegistraNotificacionResponse.class, result);
    }

    /**
     * Verifica que el Nip enviado sea valido.
     *
     * @param request {@link AutenticaNipRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void autenticaNIP(AutenticaNipRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());

        NetFacade.consumeWS(AUTENTICA_NIP,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqAuthNIP),
                headers, request, AutenticaNIPResponse.class, result);
    }

    /**
     * Recibe un pago con tarjeta.
     *
     * @param request {@link TransaccionEMVDepositRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void transaccionEMVDeposit(TransaccionEMVDepositRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());

        NetFacade.consumeWS(TRANSACCIONES_EMV_DEPOSIT,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqTransactionEmv),
                headers, request, TransaccionEMVDepositResponse.class, result);
    }

    /**
     * Consulta saldo tarjetas Closed Loop
     *
     * @param request {@link TransaccionEMVDepositRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void consultaSaldoUYU(TransaccionEMVDepositRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());

        NetFacade.consumeWS(CONSULT_BALANCE_UYU,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqConsultarSaldoCard),
                headers, request, TransaccionEMVDepositResponse.class, result);
    }

    /**
     * Guarda la información referente a la firma del voucher.
     *
     * @param request {@link FirmaDeVoucherRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void firmaDeVoucher(FirmaDeVoucherRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        NetFacade.consumeWS(FIRMA_DE_VOUCHER,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqSignatureVaucher),
                headers, request, FirmaDeVoucherResponse.class, result);
    }

    /**
     * Envía el ticket de compra.
     *
     * @param request    {@link EnviarTicketCompraRequest} body de la petición.
     * @param result     {@link IRequestResult} listener del resultado de la petición.
     * @param applyAgent {@true} Envío automático de ticket al agente
     */
    public static void enviarTicketCompra(EnviarTicketCompraRequest request, IRequestResult result, boolean applyAgent) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        request.setName("");
        NetFacade.consumeWS(applyAgent ? ENVIAR_TICKET_COMPRA_AUTOM : ENVIAR_TICKET_COMPRA,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqSendTicket),
                headers, request, EnviarTicketCompraResponse.class, result);
    }

    /**
     * Envía el ticket de compra.
     *
     * @param request {@link EnviarTicketCompraRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void enviarTicketCompraShare(EnviarTicketCompraRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        request.setName("");
        NetFacade.consumeWS(SHARED_TICKET_COMPRA,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqSendTicket),
                headers, request, EnviarTicketCompraResponse.class, result);
    }

    /**
     * Resumen de movimientos por día.
     *
     * @param request {@link ResumenMovimientosMesRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void resumenMovimientosMes(ResumenMovimientosMesRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        if (App.getInstance().getPrefs().loadDataInt(ID_ROL) == 129) {
            String idUserAdq = "0";
            try {
                idUserAdq = new DatabaseManager().getIdUsuarioAdqRolOperador() + "";
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            RequestHeaders.setIdCuentaAdq(idUserAdq);
        }
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, SingletonUser.getInstance().getDataUser().getUsuario().getTokenSesion());
        // TODO: Cambiar idUsuarioAdquiriente por el correspondiente
        NetFacade.consumeWS(CONSULTA_MOVIMIENTOS_MES_ADQ,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqMovimientos),
                headers, request, ResumenMovimientosAdqResponse.class, result);
    }

    /**
     * Resumen de movimientos por día.
     *
     * @param request {@link ReembolsoDataRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */

    public static void sendReembolso(ReembolsoDataRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());

        NetFacade.consumeWS(SEND_REEMBOLSO,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqReembolso),
                headers, request, ReembolsoResponse.class, result);
    }

    /**
     * Resumen de movimientos por día.
     *
     * @param result {@link IRequestResult} listener del resultado de la petición.
     */
    public static void consultaSaldoCupo(SaldoRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        //headers.put("Content-type", "application/json");
        /*if (agente != null) {
            String idUsAdq = "";
            for (Operadores operador : agente.getOperadores()) {
                if (operador.getIsAdmin()) {
                    idUsAdq = operador.getIdUsuarioAdquirente();
                }
            }
            RequestHeaders.setIdCuentaAdq(idUsAdq);
        }*/
        //headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        // headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        NetFacade.consumeWSnotag(CONSULTAR_SALDO_ADQ,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqGetSaldoAdm),
                headers, request, ConsultaSaldoCupoResponse.class, result);
    }

    /**
     * CONSULTA DE SALDO ADMINISTRADORES
     */
    public static void consultaSaldoAdmin(SaldoRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put("Content-type", "application/json");
        //if (agente != null) {
            /*String idUsAdq = "";
            for (Operadores operador : agente.getOperadores()) {
                if (operador.getIsAdmin()) {
                    idUsAdq = operador.getIdUsuarioAdquirente();
                }
            }*/
        //RequestHeaders.setIdCuentaAdq(idUsAdq);
        //}
        //headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        //headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        NetFacade.consumeWSnotag(CONSULTAR_SALDO_ADQ,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqGetSaldoAdm),
                headers, request, ConsultaSaldoCupoResponse.class, result);
    }

    /**
     * Método que se invoca cuando se desean obtener más movimientos por mes.
     *
     * @param result {@link IRequestResult} listener del resultado de la petición.
     */
    public static void getresumendia(String fecha, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put("Content-type", "application/json");
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        NetFacade.consumeWS(GET_RESUMENDIA,
                METHOD_GET, URL_SERVER_ADQ + App.getContext().getString(R.string.obtenerresumendia) + fecha,
                headers, null, GetResumenDiaResponse.class, result);
    }


    /**
     * @param request
     * @param result
     * @throws OfflineException
     */
    public static void cancelaTransaccionDepositoEmv(CancelaTransaccionDepositoEmvRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());

        NetFacade.consumeWS(CANCELA_TRANSACTION_EMV_DEPOSIT,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.cancelationTransactionEmv),
                headers, request, TransaccionEMVDepositResponse.class, result);
    }

    /**
     * @param request
     * @param result
     * @throws OfflineException
     */
    public static void reversaTransaccionDepositoEmv(CancelaTransaccionDepositoEmvRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());

        NetFacade.consumeWS(REVERSA_TRANSACTION_EMV_DEPOSIT,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.cancelationTransactionEmv),
                headers, request, TransaccionEMVDepositResponse.class, result);
    }

    /**
     * @param result
     * @throws OfflineException
     */
    public static void obtieneDatosCupo(IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        NetFacade.consumeWS(OBTIENE_DATOS_CUPO,
                METHOD_GET, URL_SERVER_ADQ + App.getContext().getString(R.string.adqGetDatosCupo),
                headers, null, ObtieneDatosCupoResponse.class, result);
    }

    /**
     * @param result
     * @throws OfflineException
     */
    public static void obtieneTiposReembolso(IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        String idUserAdq = "0";
        try {
            idUserAdq = new DatabaseManager().getIdUsuarioAdqRolOperador() + "";
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (RequestHeaders.getIdCuentaAdq().isEmpty()) {
            RequestHeaders.setIdCuentaAdq(idUserAdq);
        } else {
            headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        }


        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        NetFacade.consumeWS(GET_TYPE_REPAYMENT,
                METHOD_GET, URL_SERVER_ADQ + App.getContext().getString(R.string.adqGetTypeRepayment),
                headers, null, ObtieneTiposReembolsoResponse.class, result);
    }

    /**
     * @param result
     * @throws OfflineException
     */
    public static void actualizaTipoReembolso(TypeRepaymentRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        NetFacade.consumeWS(UPDATE_TYPE_REPAYMENT,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqUpdateTypeRepayment),
                headers, request, DataResultAdq.class, result);
    }

    /**
     * @param result
     * @throws OfflineException
     */
    public static void obtenerDetalleMovimiento(DetalleMovimientoRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        if (App.getInstance().getPrefs().loadDataInt(ID_ROL) == 129) {
            String idUserAdq = "0";
            try {
                idUserAdq = new DatabaseManager().getIdUsuarioAdqRolOperador() + "";
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            RequestHeaders.setIdCuentaAdq(idUserAdq);
        }
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        NetFacade.consumeWS(DETAIL_MOVEMENT,
                METHOD_GET, URL_SERVER_ADQ + App.getContext().getString(R.string.adqDetalleMovimiento) + "?"
                        + request.getNoSecUnicoPT() + "&" + request.getIdTransaction(),
                headers, null, ResumenMovimientosAdqResponse.class, result);
    }
}
