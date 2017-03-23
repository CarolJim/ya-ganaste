package com.pagatodo.yaganaste.net;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.*;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.*;
import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.*;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.*;
import static com.pagatodo.yaganaste.utils.Recursos.URL_SERVER_TRANS;

/**
 * Created by flima on 17/03/2017.
 *
 * Clase para gestionar el WS de Ya Ganaste Transaccional.
 */

public class ApiTrans extends Api {

    /**
     * Método que se invoca para asignar una Cuenta a un Cliente.
     *
     * @param request {@link AsignarCuentaDisponibleRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void asignarCuentaDisponible(AsignarCuentaDisponibleRequest request, IRequestResult result) {
        NetFacade.consumeWS(ASIGNAR_CUENTA_DISPONIBLE,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.assignAccountAvailableUrl),
                headersYaGanaste,request, AsignarCuentaDisponibleResponse.class,result);
    }

    /**
     * Método para Asignar/Cambiar NIP de la tarjeta Activa.
     *
     * @param request {@link AsignarNIPRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void asignarNip(AsignarNIPRequest request, IRequestResult result) {
        NetFacade.consumeWS(ASIGNAR_NIP,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.assignNIPUrl),
                headersYaGanaste,request, AsignarNIPResponse.class,result);
    }

    /**
     * Método que se invoca para asociar una Tarjeta con una Cuenta.
     *
     * @param request {@link AsociarTarjetaCuentaRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void asociarTarjetaCuenta(AsociarTarjetaCuentaRequest request, IRequestResult result) {
        NetFacade.consumeWS(ASOCIAR_TARJETA_CUENTA,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.assignCard2AccountUrl),
                headersYaGanaste,request, AsociarTarjetaCuentaResponse.class,result);
    }

    /**
     * Método que se invoca para Bloquear o Desbloquear una Tarjeta de manera Temporal.
     *
     * @param request {@link BloquearTemporalmenteTarjetaRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void bloquearTemporalmenteTarjeta(BloquearTemporalmenteTarjetaRequest request, IRequestResult result) {
        NetFacade.consumeWS(BLOQUEAR_TEMPORALMENTE_TARJETA,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.lockTempCardUrl),
                headersYaGanaste,request, BloquearTemporalmenteTarjetaResponse.class,result);
    }

    /**
     * Método que se invoca para ver si una Tarjeta ya está asociada a un Cliente, y/o si esta Existe..
     *
     * @param request {@link ConsultaAsignacionTarjetaRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void consultaAsignacionTarjeta(ConsultaAsignacionTarjetaRequest request, IRequestResult result) {
        NetFacade.consumeWS(CONSULTAR_ASIGNACION_TARJETA,
                METHOD_GET, URL_SERVER_TRANS + App.getContext().getString(R.string.consultAssignCardUrl),
                headersYaGanaste,request, ConsultarAsignacionTarjetaResponse.class,result);
    }

    /**
     * Método que se invoca cuando se desea consultar el Saldo del Cliente.
     *
     * @param request {@link ConsultarSaldoRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void consultarSaldo(ConsultarSaldoRequest request, IRequestResult result) {
        NetFacade.consumeWS(CONSULTAR_SALDO,
                METHOD_GET, URL_SERVER_TRANS + App.getContext().getString(R.string.getBalanceUrl),
                headersYaGanaste,request, ConsultarSaldoResponse.class,result);
    }

    /**
     * Método que se invoca cuando se desea consultar el Nombre completo del Titular de una Cuenta.
     *
     * @param request {@link ConsultarTitularCuentaRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void consultarTitularCuenta(ConsultarTitularCuentaRequest request, IRequestResult result) {
        NetFacade.consumeWS(CONSULTAR_TITULAR_CUENTA,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.getAccountOwnerUrl),
                headersYaGanaste,request, ConsultarTitularCuentaResponse.class,result);
    }

    /**
     * Método que se invoca cuando se desea generar un nuevo Cliente de BPT.
     *
     * @param request {@link CrearClienteRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void crearCliente(CrearClienteRequest request, IRequestResult result) {
        NetFacade.consumeWS(CREAR_CLIENTE,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.createClientUrl),
                headersYaGanaste,request, CrearClienteResponse.class,result);
    }

    /**
     * Método que se invoca cuando se realiza una Recarga, Pago de Servicio o Envío de Dinero.
     *
     * @param request {@link EjecutarTransaccionRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void ejecutarTransaccion(EjecutarTransaccionRequest request, IRequestResult result) {
        NetFacade.consumeWS(EJECUTAR_TRANSACCION,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.executeTransactionUrl),
                headersYaGanaste,request, EjecutarTransaccionResponse.class,result);
    }

    /**
     * Método que se invoca cuando se desea validar el Estatus de una Transacción, permitiendo ejecutar
     * nuevamente la transacción en caso de requerirse (Modo Transaccional).
     *
     * @param request {@link ValidarTransaccionRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void validarEstatusTransaccion(ValidarTransaccionRequest request, IRequestResult result) {
        NetFacade.consumeWS(VALIDAR_ESTATUS_TRANSACCION,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.validateStatusTransactionUrl),
                headersYaGanaste,request, ValidarEstatusTransaccionResponse.class,result);
    }

    /**
     * Método que se invoca para traspasar dinero de una cuenta en banco PagaTodo hacia el saldo del comercio
     * en PTH (Cupo), esto para poder hacer transacciones PTH con un agente full.
     *
     * @param request {@link FondearCUPORequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void fondearCupo(FondearCUPORequest request, IRequestResult result) {
        NetFacade.consumeWS(FONDEAR_CUPO,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.fondearCupoUrl),
                headersYaGanaste,request, FondearCupoResponse.class,result);
    }

    /**
     * Este método obtiene el estatus en que se encuentra la tarjeta.
     *
     * @param request {@link ObtenerEstatusTarjetaRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void obtenerEstatusTarjeta(ObtenerEstatusTarjetaRequest request, IRequestResult result) {
        NetFacade.consumeWS(OBTENER_ESTATUS_TARJETA,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.getStatusCard),
                headersYaGanaste,request, ObtenerEstatusTarjetaResponse.class,result);
    }

    /**
     * Consulta el saldo de adquirente.
     *
     * @param request {@link ConsultarSaldoADQRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void consultarSaldoADQ(ConsultarSaldoADQRequest request, IRequestResult result) {
        NetFacade.consumeWS(CONSULTAR_SALDO_ADQ,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.getBalanceAdqUrl),
                headersYaGanaste,request, ConsultarSaldoADQResponse.class,result);
    }
}
