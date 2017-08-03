package com.pagatodo.yaganaste.net;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarCuentaDisponibleRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsociarTarjetaCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.BloquearTemporalmenteTarjetaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ConsultaAsignacionTarjetaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ConsultarTitularCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.CrearClienteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.EjecutarTransaccionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.FondearCUPORequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ObtenerEstatusTarjetaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ValidarTransaccionRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarCuentaDisponibleResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarNIPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsociarTarjetaCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.BloquearTemporalmenteTarjetaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarAsignacionTarjetaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarSaldoADQResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarSaldoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarTitularCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.CrearClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.EjecutarTransaccionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.FondearCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ObtenerEstatusTarjetaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ValidarEstatusTransaccionResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui.account.AccountInteractorNew;

import java.util.Map;
import java.util.Objects;

import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.METHOD_GET;
import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.METHOD_POST;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_CUENTA_DISPONIBLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_NIP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASOCIAR_TARJETA_CUENTA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.BLOQUEAR_TEMPORALMENTE_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_ASIGNACION_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO_ADQ;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_TITULAR_CUENTA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTA_STATUS_REGISTRO_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_CLIENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.EJECUTAR_TRANSACCION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.FONDEAR_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_ESTATUS_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_ESTATUS_TRANSACCION;
import static com.pagatodo.yaganaste.utils.Recursos.URL_SERVER_TRANS;

/**
 * Created by flima on 17/03/2017.
 * <p>
 * Clase para gestionar el WS de Ya Ganaste Transaccional.
 */

public class ApiTrans extends Api {

    /**
     * Método que se invoca para asignar una Cuenta a un Cliente.
     *
     * @param request {@link AsignarCuentaDisponibleRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void asignarCuentaDisponible(AsignarCuentaDisponibleRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());

        NetFacade.consumeWS(ASIGNAR_CUENTA_DISPONIBLE,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.assignAccountAvailableUrl),
                headers, request, AsignarCuentaDisponibleResponse.class, result);
    }

    /**
     * Método para Asignar/Cambiar NIP de la tarjeta Activa.
     *  @param request {@link AsignarNIPRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     * @param webService
     */
    public static void asignarNip(AsignarNIPRequest request, IRequestResult result, WebService webService) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put(RequestHeaders.IdCuenta, RequestHeaders.getIdCuenta());
        NetFacade.consumeWS(webService,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.assignNIPUrl),
                headers, request, AsignarNIPResponse.class, result);
    }

    /**
     * Método que se invoca para asociar una Tarjeta con una Cuenta.
     *
     * @param request {@link AsociarTarjetaCuentaRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void asociarTarjetaCuenta(AsociarTarjetaCuentaRequest request, IRequestResult result) throws OfflineException {
        NetFacade.consumeWS(ASOCIAR_TARJETA_CUENTA,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.assignCard2AccountUrl),
                getHeadersYaGanaste(), request, AsociarTarjetaCuentaResponse.class, result);
    }

    /**
     * Método que se invoca para Bloquear o Desbloquear una Tarjeta de manera Temporal.
     *
     * @param request {@link BloquearTemporalmenteTarjetaRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void bloquearTemporalmenteTarjeta(BloquearTemporalmenteTarjetaRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put(RequestHeaders.IdCuenta, RequestHeaders.getIdCuenta());
        NetFacade.consumeWS(BLOQUEAR_TEMPORALMENTE_TARJETA,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.lockTempCardUrl),
                headers, request, BloquearTemporalmenteTarjetaResponse.class, result);
    }

    /**
     * Método que se invoca para ver si una Tarjeta ya está asociada a un Cliente, y/o si esta Existe..
     *
     * @param request {@link ConsultaAsignacionTarjetaRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void consultaAsignacionTarjeta(ConsultaAsignacionTarjetaRequest request, IRequestResult result) throws OfflineException {
        NetFacade.consumeWS(CONSULTAR_ASIGNACION_TARJETA,
                METHOD_GET, URL_SERVER_TRANS + App.getContext().getString(R.string.consultAssignCardUrl),
                getHeadersYaGanaste(), request, ConsultarAsignacionTarjetaResponse.class, result);
    }

    /**
     * Método que se invoca cuando se desea consultar el Saldo del Cliente.
     *
     * @param result {@link IRequestResult} listener del resultado de la petición.
     */
    public static void consultarSaldo(IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put(RequestHeaders.TokenAutenticacion, RequestHeaders.getTokenauth());
        NetFacade.consumeWS(CONSULTAR_SALDO,
                METHOD_GET, URL_SERVER_TRANS + App.getContext().getString(R.string.getBalanceUrl),
                headers, null, ConsultarSaldoResponse.class, result);
    }

    /**
     * Método que se invoca cuando se desea consultar el Nombre completo del Titular de una Cuenta.
     *
     * @param request {@link ConsultarTitularCuentaRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void consultarTitularCuenta(ConsultarTitularCuentaRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(CONSULTAR_TITULAR_CUENTA,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.getAccountOwnerUrl),
                headers, request, ConsultarTitularCuentaResponse.class, result);
    }

    /**
     * Método que se invoca cuando se desea generar un nuevo Cliente de BPT.
     *
     * @param request {@link CrearClienteRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void crearCliente(CrearClienteRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(CREAR_CLIENTE,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.createClientUrl),
                headers, request, CrearClienteResponse.class, result);
    }

    /**
     * Método que se invoca cuando se realiza una Recarga, Pago de Servicio o Envío de Dinero.
     *
     * @param request {@link EjecutarTransaccionRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void ejecutarTransaccion(EjecutarTransaccionRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put(RequestHeaders.IdTransaccionFreja, RequestHeaders.getIdTransaccionFreja());
        headers.put(RequestHeaders.TokenFreja, RequestHeaders.getTokenFreja());
        if (request.getIdTipoTransaccion() == MovementsTab.TAB3.getId()) {
            headers.put(RequestHeaders.IdOperacion, "1");
        }

        NetFacade.consumeWS(EJECUTAR_TRANSACCION,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.executeTransactionUrl),
                headers, request, EjecutarTransaccionResponse.class, result);
    }

    /**
     * Método que se invoca cuando se desea validar el Estatus de una Transacción, permitiendo ejecutar
     * nuevamente la transacción en caso de requerirse (Modo Transaccional).
     *
     * @param request {@link ValidarTransaccionRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void validarEstatusTransaccion(ValidarTransaccionRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put(RequestHeaders.IdTransaccionFreja, RequestHeaders.getIdTransaccionFreja());
        headers.put(RequestHeaders.TokenFreja, RequestHeaders.getTokenFreja());
        headers.put(RequestHeaders.TokenCuenta, RequestHeaders.getTokenCuenta());

        NetFacade.consumeWS(VALIDAR_ESTATUS_TRANSACCION,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.validateStatusTransactionUrl),
                headers, request, ValidarEstatusTransaccionResponse.class, result);
    }

    /**
     * Método que se invoca para traspasar dinero de una cuenta en banco PagaTodo hacia el saldo del comercio
     * en PTH (Cupo), esto para poder hacer transacciones PTH con un agente full.
     *
     * @param request {@link FondearCUPORequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void fondearCupo(FondearCUPORequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(FONDEAR_CUPO,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.fondearCupoUrl),
                headers, request, FondearCupoResponse.class, result);
    }

    /**
     * Este método obtiene el estatus en que se encuentra la tarjeta.
     *
     * @param request {@link ObtenerEstatusTarjetaRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void obtenerEstatusTarjeta(ObtenerEstatusTarjetaRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put(RequestHeaders.IdCuenta, RequestHeaders.getIdCuenta());
        NetFacade.consumeWS(OBTENER_ESTATUS_TARJETA,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.getStatusCard),
                getHeadersYaGanaste(), request, ObtenerEstatusTarjetaResponse.class, result);
    }

    /**
     * Consulta el saldo de adquirente.
     *
     * @param result {@link IRequestResult} listener del resultado de la petición.
     */
    public static void consultarSaldoADQ(IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put(RequestHeaders.IdCuenta, RequestHeaders.getIdCuenta());
        NetFacade.consumeWS(CONSULTAR_SALDO_ADQ,
                METHOD_GET, URL_SERVER_TRANS + App.getContext().getString(R.string.getBalanceAdqUrl),
                headers, null, ConsultarSaldoADQResponse.class, result);
    }




    public static void consultaStatusRegistroCupo(IRequestResult result) throws OfflineException{
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put(RequestHeaders.IdCuenta, RequestHeaders.getIdCuenta());

        //todo CUPO  poner URL correcta  y Class Response adecuada
        String URL = "";
        Class classResponse = Object.class;

        NetFacade.consumeWS(CONSULTA_STATUS_REGISTRO_CUPO,METHOD_GET,URL,headers,null, classResponse,result);
    }

}
