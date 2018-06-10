package com.pagatodo.yaganaste.net;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.BloquearCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarCuentaDisponibleRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ConsultaAsignacionTarjetaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ConsultarTitularCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.EjecutarTransaccionRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarCuentaDisponibleResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarNIPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarAsignacionTarjetaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarSaldoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarTitularCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.EjecutarTransaccionResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui.preferuser.iteractors.PreferUserIteractor;
import com.pagatodo.yaganaste.ui.tarjeta.TarjetaUserIteractor;

import java.util.Map;

import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.METHOD_GET;
import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.METHOD_POST;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_CUENTA_DISPONIBLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.BLOQUEAR_CUENTA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_ASIGNACION_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_TITULAR_CUENTA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.EJECUTAR_TRANSACCION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ESTATUS_CUENTA;
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
     *
     * @param request    {@link AsignarNIPRequest} body de la petición.
     * @param result     {@link IRequestResult} listener del resultado de la petición.
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

        if (!RequestHeaders.getTokensesion().isEmpty()) {
            headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        }
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
     * Servicio que se encarga de enviar la peticion para Bloquear o Desbloquear la Card
     *
     * @param request
     * @param result
     * @throws OfflineException
     */
    public static void bloquearCuenta(BloquearCuentaRequest request,
                                      PreferUserIteractor result) throws OfflineException {

        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());

        int idCuenta = SingletonUser.getInstance().getDataUser().getEmisor()
                .getCuentas().get(0).getIdCuenta();
        headers.put("IdCuenta", "" + idCuenta);
        headers.put("Content-Type", "application/json");

        NetFacade.consumeWS(BLOQUEAR_CUENTA,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.bloquearDatosCuenta),
                headers, request, true, BloquearCuentaResponse.class, result);
    }

    public static void bloquearCuenta(BloquearCuentaRequest request,
                                      TarjetaUserIteractor result) throws OfflineException {

        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());

        int idCuenta = SingletonUser.getInstance().getDataUser().getEmisor()
                .getCuentas().get(0).getIdCuenta();
        headers.put("IdCuenta", "" + idCuenta);
        headers.put("Content-Type", "application/json");

        NetFacade.consumeWS(BLOQUEAR_CUENTA,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.bloquearDatosCuenta),
                headers, request, true, BloquearCuentaResponse.class, result);
    }

    /**
     * Servicio que se encarga de enviar la peticion para obtener el Estatus de la Card dentro de Preferencias
     *
     * @param request
     * @param result
     */
    public static void estatusCuenta(EstatusCuentaRequest request,
                                     PreferUserIteractor result) throws OfflineException {

        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());

        int idCuenta = SingletonUser.getInstance().getDataUser().getEmisor()
                .getCuentas().get(0).getIdCuenta();
        headers.put("IdCuenta", "" + idCuenta);
        headers.put("Content-Type", "application/json");

        NetFacade.consumeWS(ESTATUS_CUENTA,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.estatusDatosCuenta),
                headers, request, true, EstatusCuentaResponse.class, result);
    }

    /**
     * Obtener estatus de la tarjeta desde TarjetaActivity
     * {@link com.pagatodo.yaganaste.ui._controllers.TarjetaActivity}
     */
    public static void estatusCuenta(EstatusCuentaRequest request,
                                     TarjetaUserIteractor result) throws OfflineException {
        //TarjetaUserIteractor
        Map<String, String> headers = getHeadersYaGanaste();
        /* Si el usuario ya inicio sesión el tamaño del objeto Cuenta debe ser mayor a 0 porque
        se llena en el Singleton al iniciar sesión */
        if (SingletonUser.getInstance().getDataUser().getEmisor().getCuentas().size() > 0) {
            headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion()); // Si se inicia sesión mandar el Token Sesión
            int idCuenta = SingletonUser.getInstance().getDataUser().getEmisor()
                    .getCuentas().get(0).getIdCuenta();
            headers.put(RequestHeaders.IdCuenta, "" + idCuenta);
            /* En caso de que el Singleton arroje un objeto Cuenta vació significa que se encuentra
             * fuera de una Sesión */
        } else {

            //headers.put(RequestHeaders.TokenDispositivo, RequestHeaders.getTokendevice());  // Traido del SharedPreference
            headers.put(RequestHeaders.IdCuenta, RequestHeaders.getIdCuenta()); // Traido del SharedPreference
            headers.put(RequestHeaders.TokenAutenticacion, RequestHeaders.getTokenauth()); // Traido del SharedPreference
        }
        headers.put("Content-Type", "application/json");

        NetFacade.consumeWS(ESTATUS_CUENTA,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.estatusDatosCuenta),
                headers, request, true, EstatusCuentaResponse.class, result);
    }

    public static void estatusCuenta(EstatusCuentaRequest request,
                                     IRequestResult result) throws OfflineException {
        //TarjetaUserIteractor
        Map<String, String> headers = getHeadersYaGanaste();
        /* Si el usuario ya inicio sesión el tamaño del objeto Cuenta debe ser mayor a 0 porque
        se llena en el Singleton al iniciar sesión */
        if (SingletonUser.getInstance().getDataUser().getEmisor() != null) {
            //if (SingletonUser.getInstance().getDataUser().getEmisor().getCuentas().size() > 0) {
            headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion()); // Si se inicia sesión mandar el Token Sesión
            int idCuenta = SingletonUser.getInstance().getDataUser().getEmisor()
                    .getCuentas().get(0).getIdCuenta();
            headers.put(RequestHeaders.IdCuenta, "" + idCuenta);
            /* En caso de que el Singleton arroje un objeto Cuenta vació significa que se encuentra
             * fuera de una Sesión */
        } else {
            //headers.put(RequestHeaders.TokenDispositivo, RequestHeaders.getTokendevice());  // Traido del SharedPreference
            headers.put(RequestHeaders.IdCuenta, RequestHeaders.getIdCuenta()); // Traido del SharedPreference
            headers.put(RequestHeaders.TokenAutenticacion, RequestHeaders.getTokenauth()); // Traido del SharedPreference
        }
        NetFacade.consumeWS(ESTATUS_CUENTA,
                METHOD_POST, URL_SERVER_TRANS + App.getContext().getString(R.string.estatusDatosCuenta),
                headers, request, true, EstatusCuentaResponse.class, result);
    }
}
