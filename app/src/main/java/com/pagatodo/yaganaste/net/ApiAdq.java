package com.pagatodo.yaganaste.net;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.AutenticaNipRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ConsultaSaldoCupoRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ConsultaSesionAgenteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.EnviarTicketCompraRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.FirmaDeVoucherRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.LoginAdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.RegistraNipRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.RegistraNotificacionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.RegistroDeviceDataRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.RegistroDongleRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ResumenMovimientosMesRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.AutenticaNIPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ConsultaSaldoCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ConsultaSesionAgenteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.EnviarTicketCompraResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.FirmaDeVoucherResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.LoginAdqResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.RegistraNIPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.RegistraNotificacionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.RegistroDeviceDataResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.RegistroDongleResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ResumenMovimientosAdqResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.TransaccionEMVDepositResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;

import java.util.Map;

import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.METHOD_GET;
import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.METHOD_POST;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.AUTENTICA_NIP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTA_MOVIMIENTOS_MES_ADQ;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTA_SALDO_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTA_SESION_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ENVIAR_TICKET_COMPRA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.FIRMA_DE_VOUCHER;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.LOGIN_ADQ;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTRA_NIP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTRA_NOTIFICACION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTRO_DEVICE_DATA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTRO_DONGLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.TRANSACCIONES_EMV_DEPOSIT;
import static com.pagatodo.yaganaste.utils.Recursos.URL_SERVER_ADQ;

/**
 * Created by flima on 17/03/2017.
 *
 * Clase para gestionar el WS de Adquiriente.
 */

public class ApiAdq extends Api {

    /**
     * Verifica que el usuario\contraseña este dado de alta en la plataforma PagaTodo,
     * además de verificar que la versión del aplicativo coincida con la versión del web service.
     *
     * @param request {@link LoginAdqRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void loginAdq(LoginAdqRequest request, IRequestResult result)  throws OfflineException {
        NetFacade.consumeWS(LOGIN_ADQ,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqLogin),
                getHeadersAdq(),request, LoginAdqResponse.class,result);
    }

    /**
     * Correlaciona un dongle con el usuario.
     *
     * @param request {@link RegistroDongleRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void registroDongle(RegistroDongleRequest request, IRequestResult result)  throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuenta());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());

        NetFacade.consumeWS(REGISTRO_DONGLE,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqRegisterDongle),
                headers,request, RegistroDongleResponse.class,result);
    }

    /**
     * Registra el NIP del usuario tendrá que digitar antes de enviar una compra.
     *
     * @param request {@link RegistraNipRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void registraNIP(RegistraNipRequest request, IRequestResult result)  throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        NetFacade.consumeWS(REGISTRA_NIP,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqRegisterNIP),
                headers,request, RegistraNIPResponse.class,result);
    }

    /**
     * Consulta el estado de la sesión del agente.
     *
     * @param request {@link ConsultaSesionAgenteRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void consultaSesionAgente(ConsultaSesionAgenteRequest request, IRequestResult result)  throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        NetFacade.consumeWS(CONSULTA_SESION_AGENTE,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqGetSessionAgente),
                headers,request, ConsultaSesionAgenteResponse.class,result);
    }

    /**
     * Registra los datos relacionados con el dispositivo.
     *
     * @param request {@link RegistroDeviceDataRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void registroDeviceData(RegistroDeviceDataRequest request, IRequestResult result)  throws OfflineException {
        NetFacade.consumeWS(REGISTRO_DEVICE_DATA,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqRegisterDeviceData),
                getHeadersAdq(),request, RegistroDeviceDataResponse.class,result);
    }

    /**
     * Registra una notificación.
     *
     * @param request {@link RegistraNotificacionRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void registraNotificacion(RegistraNotificacionRequest request, IRequestResult result)  throws OfflineException {
        NetFacade.consumeWS(REGISTRA_NOTIFICACION,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqRegisterNotification),
                getHeadersAdq(),request, RegistraNotificacionResponse.class,result);
    }

    /**
     * Verifica que el Nip enviado sea valido.
     *
     * @param request {@link AutenticaNipRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void autenticaNIP(AutenticaNipRequest request, IRequestResult result)  throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());

        NetFacade.consumeWS(AUTENTICA_NIP,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqAuthNIP),
                headers,request, AutenticaNIPResponse.class,result);
    }

    /**
     * Recibe un pago con tarjeta.
     *
     * @param request {@link TransaccionEMVDepositRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void transaccionEMVDeposit(TransaccionEMVDepositRequest request, IRequestResult result)   throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuenta());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());

        NetFacade.consumeWS(TRANSACCIONES_EMV_DEPOSIT,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqTransactionEmv),
                headers,request, TransaccionEMVDepositResponse.class,result);
    }

    /**
     * Guarda la información referente a la firma del voucher.
     *
     * @param request {@link FirmaDeVoucherRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void firmaDeVoucher(FirmaDeVoucherRequest request, IRequestResult result)  throws OfflineException {

        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        NetFacade.consumeWS(FIRMA_DE_VOUCHER,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqSignatureVaucher),
                headers,request, FirmaDeVoucherResponse.class,result);
    }

    /**
     * Envía el ticket de compra.
     *
     * @param request {@link EnviarTicketCompraRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void enviarTicketCompra(EnviarTicketCompraRequest request, IRequestResult result)  throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        NetFacade.consumeWS(ENVIAR_TICKET_COMPRA,
                METHOD_POST, URL_SERVER_ADQ + App.getContext().getString(R.string.adqSendTicket),
                headers,request, EnviarTicketCompraResponse.class,result);
    }

    /**
     * Resumen de movimientos por día.
     *
     * @param request {@link ResumenMovimientosMesRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void resumenMovimientosMes(ResumenMovimientosMesRequest request, IRequestResult result)  throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());

        NetFacade.consumeWS(CONSULTA_MOVIMIENTOS_MES_ADQ,
                METHOD_GET, URL_SERVER_ADQ + App.getContext().getString(R.string.adqResumeMonth),
                headers,request, ResumenMovimientosAdqResponse.class,result);
    }

    /**
     * Resumen de movimientos por día.
     *
     * @param request {@link ConsultaSaldoCupoRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void consultaSaldoCupo(ConsultaSaldoCupoRequest request, IRequestResult result)  throws OfflineException {
        Map<String, String> headers = getHeadersAdq();
        headers.put(RequestHeaders.IdCuentaAdq, RequestHeaders.getIdCuentaAdq());
        headers.put(RequestHeaders.TokenAdq, RequestHeaders.getTokenAdq());
        NetFacade.consumeWS(CONSULTA_SALDO_CUPO,
                METHOD_GET, URL_SERVER_ADQ + App.getContext().getString(R.string.adqGetBalance),
                headers,request, ConsultaSaldoCupoResponse.class,result);
    }

}
