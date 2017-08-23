package com.pagatodo.yaganaste.net;

import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActivacionAprovSofttokenRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActivacionServicioMovilRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarDatosCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AsignarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.BloquearCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarEmailRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CargaDocumentosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ConsultarMovimientosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearAgenteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioClienteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioFWSRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DesasociarDispositivoRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EnviarTicketTAEPDSRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.GetJsonWebTokenRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarTransaccionOnlineRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.LocalizarSucursalesRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerCatalogoRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerDocumentosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.RecuperarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarDatosPersonaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarEstatusUsuarioRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarFormatoContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.VerificarActivacionAprovSofttokenRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.cupo.CrearCupoSolicitudRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActivacionAprovSofttokenResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActivacionServicioMovilResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarAvatarResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarDatosCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarInformacionSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.AsignarContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CambiarContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CambiarEmailResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CargaDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CerrarSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarMovimientosMesResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CrearAgenteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CrearUsuarioClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CrearUsuarioFWSResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DesasociarDispositivoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EliminarAvatarResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EnviarTicketTAEPDSResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.GenerarCodigoRecuperacionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.GetJsonWebTokenResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.IniciarSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.IniciarTransaccionOnlineResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.LocalizarSucursalesResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerCatalogosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDomicilioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerNumeroSMSResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerSubgirosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.RecuperarContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarEstatusUsuarioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarFormatoContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.VerificarActivacionAprovSofttokenResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.VerificarActivacionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.cupo.CrearCupoSolicitudResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.cupo.EstadoDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.cupo.EstadoSolicitudResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.ui.preferuser.iteractors.PreferUserIteractor;

import java.util.Map;

import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.METHOD_GET;
import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.METHOD_POST;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTIVACION_APROV_SOFTTOKEN;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTIVACION_SERVICIO_MOVIL;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_AVATAR;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_INFO_SESION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZA_DOCUMENTOS_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.BLOQUEAR_CUENTA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CAMBIAR_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CARGA_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CARGA_DOCUMENTOS_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CERRAR_SESION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_MOVIMIENTOS_MES;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTA_STATUS_REGISTRO_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_CLIENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_FWS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_FWS_LOGIN;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREA_SOLICITUD_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.DESASOCIAR_DISPOSITIVO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ELIMINAR_AVATAR;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ENVIAR_TICKET_TAEPDS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.GENERAR_CODIGO_RECUPERACION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.GET_JSONWEBTOKEN;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.INICIAR_SESION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.INICIAR_SESION_SIMPLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.INICIAR_TRANSACCION_ONLINE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.LOCALIZAR_SUCURSALES;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_CATALOGOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO_PRINCIPAL;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_ESTADO_DOCUMENTOS_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_NUMERO_SMS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_SUBGIROS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.RECUPERAR_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.RECURSO_IMAGEN;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.UPDATE_DATOS_CUENTA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_DATOS_PERSONA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_ESTATUS_USUARIO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_FORMATO_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION_APROV_SOFTTOKEN;
import static com.pagatodo.yaganaste.utils.Recursos.URL_SERVER_ADTVO;
import static com.pagatodo.yaganaste.utils.Recursos.URL_SERVER_TRANS;

/**
 * Created by flima on 17/03/2017.
 * <p>
 * Clase para gestionar el WS de Ya Ganaste Administrativo.
 */

public class ApiAdtvo extends Api {

    /**
     * Método que se invoca para activar el Servicio de Banca Móvil, asociando una Cuenta con el telefóno
     * desde el cual se recibió el SMS.
     *
     * @param request {@link ActivacionServicioMovilRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void activacionServicioMovil(ActivacionServicioMovilRequest request, IRequestResult result) throws OfflineException {
        NetFacade.consumeWS(ACTIVACION_SERVICIO_MOVIL,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.activateMobileServiceUrl),
                getHeadersYaGanaste(), request, ActivacionServicioMovilResponse.class, result);
    }

    /**
     * Método que se invoca para subir la imagen del Avatar, o para asociar una URL publica que será usada como Avatar.
     *
     * @param request {@link ActualizarAvatarRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void actualizarAvatar(ActualizarAvatarRequest request, IRequestResult result) throws OfflineException {

        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(ACTUALIZAR_AVATAR,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.updateAvatarUrl),
                headers, request, ActualizarAvatarResponse.class, result);
    }

    /**
     * Método que se invoca cuando se desea actualizar la información obtenida del Usuario/Cliente.
     *
     * @param result {@link IRequestResult} listener del resultado de la petición.
     */
    public static void actualizarInformacionSesion(IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put(RequestHeaders.TokenAutenticacion, RequestHeaders.getTokenauth());

        NetFacade.consumeWS(ACTUALIZAR_INFO_SESION,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.updateSessionUrl),
                headers, null, ActualizarInformacionSesionResponse.class, result);
    }

    /**
     * Método que se invoca para asignar una nueva Contraseña a un Usuario cuando este solicita Recuperar su Contraseña.
     *
     * @param request {@link AsignarContraseniaRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void asigarContrasenia(AsignarContraseniaRequest request, IRequestResult result) throws OfflineException {
        NetFacade.consumeWS(ASIGNAR_CONTRASENIA,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.assignPaswordUrl),
                getHeadersYaGanaste(), request, AsignarContraseniaResponse.class, result);
    }

    /**
     * Método que se invoca cuando se desea Cambiar la Contraseña actual del Usuario.
     *
     * @param request {@link CambiarContraseniaRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void cambiarContrasenia(CambiarContraseniaRequest request, IRequestResult result) throws OfflineException {
//        Map<String, String> headers = getHeadersYaGanaste();
//        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
//        headers.put(RequestHeaders.TokenAutenticacion, RequestHeaders.getTokenauth());

        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.IdCuenta, RequestHeaders.getIdCuenta());
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put("Content-Type", "application/json");

        Log.d("PreferUserIteractor", "getTokensesion " + RequestHeaders.getTokensesion());
        Log.d("PreferUserIteractor", "getTokensesion " + RequestHeaders.getTokenauth());
        NetFacade.consumeWS(CAMBIAR_CONTRASENIA,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.changePasswordUrl),
                headers, request, CambiarContraseniaResponse.class, result);
    }

    /**
     * Método para realizar la Carga de los documentos para la afiliación de Comercios.
     *
     * @param request {@link CargaDocumentosRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void cargaDocumentos(CargaDocumentosRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(CARGA_DOCUMENTOS,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.uploadDocumentsUrl),
                headers, request, CargaDocumentosResponse.class, result);
    }

    public static void actualizarDocumentos(CargaDocumentosRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(ACTUALIZAR_DOCUMENTOS,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.updateDocumentosUrl),
                headers, request, CargaDocumentosResponse.class, result);
    }


    /**
     * Método que se invoca cuando se desea Cerrar una Sesión activa.
     *
     * @param result {@link IRequestResult} listener del resultado de la petición.
     */
    public static void cerrarSesion(IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(CERRAR_SESION,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.logoutUrl),
                headers, null, CerrarSesionResponse.class, result);
    }

    public static void cerrarSesion() throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(CERRAR_SESION,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.logoutUrl),
                headers, null, CerrarSesionResponse.class, null);
    }

    /**
     * Método que se invoca cuando se desean obtener más movimientos por mes.
     *
     * @param request {@link ConsultarMovimientosRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void consultarMovimientosMes(ConsultarMovimientosRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(CONSULTAR_MOVIMIENTOS_MES,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.getMovementsByMonthUrl),
                headers, request, ConsultarMovimientosMesResponse.class, result);
    }


    /**
     * Método que se invoca cuando se desean obtener más movimientos por mes.
     *
     * @param request {@link ValidarDatosPersonaRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void validarDatosPersona(ValidarDatosPersonaRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(VALIDAR_DATOS_PERSONA,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.validate_data_person),
                headers, request, GenericResponse.class, result);
    }


    /**
     * Método que se invoca para que un Cliente se vuelva Agente.
     *
     * @param request {@link CrearAgenteRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void crearAgente(CrearAgenteRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put(RequestHeaders.IdCuenta, RequestHeaders.getIdCuenta());
        NetFacade.consumeWS(CREAR_AGENTE,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.createAgentUrl),
                headers, request, CrearAgenteResponse.class, result);
    }

    /**
     * Método que se invoca cuando se desea generar un nuevo Usuario en el FWS Externo de BPT.
     *
     * @param request {@link CrearUsuarioFWSRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void crearUsuarioFWS(CrearUsuarioFWSRequest request, IRequestResult result) throws OfflineException {
        NetFacade.consumeWS(CREAR_USUARIO_FWS,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.createUserUrl),
                getHeadersYaGanaste(), request, CrearUsuarioFWSResponse.class, result);
    }

    /**
     * Método que se invoca cuando se desea generar un nuevo Usuario en el FWS Externo de BPT
     * y obtener un TokenSesion para continuar con el flujo de registro sin necesidad de lanzar
     * otra conexión.
     *
     * @param request {@link CrearUsuarioFWSRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void crearUsuarioFWSInicioSesion(CrearUsuarioFWSRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenDispositivo, RequestHeaders.getTokendevice());
        NetFacade.consumeWS(CREAR_USUARIO_FWS_LOGIN,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.createUserLoginUrl),
                headers, request, CrearUsuarioClienteResponse.class, result);
    }

    /**
     * Método que se invoca cuando se desea generar un nuevo Usuario en el FWS Externo de BPT
     * y obtener un TokenSesion para continuar con el flujo de registro sin necesidad de lanzar
     * otra conexión.
     *
     * @param request {@link CrearUsuarioFWSRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void crearUsuarioCliente(CrearUsuarioClienteRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenDispositivo, RequestHeaders.getTokendevice());
        NetFacade.consumeWS(CREAR_USUARIO_CLIENTE,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.createUserClientUrl),
                headers, request, CrearUsuarioClienteResponse.class, result);
    }

    /**
     * Método que se invoca para remover la imagen asignada para el Avatar.
     *
     * @param result {@link IRequestResult} listener del resultado de la petición.
     */
    public static void eliminarAvatar(IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(ELIMINAR_AVATAR,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.deleteAvatarUrl),
                headers, null, EliminarAvatarResponse.class, result);
    }

    /**
     * Método que se invoca para realizar el inicio de sesión de un Usuario o un Cliente BPT.
     *
     * @param request {@link IniciarSesionRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void iniciarSesion(IniciarSesionRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        //headers.put(RequestHeaders.TokenDispositivo, RequestHeaders.getTokendevice());
        headers.put(RequestHeaders.TokenDispositivo, "dasdasd");
        //if (!RequestHeaders.getTokenauth().equals(""))//Si ya se almaceno el tokenAuth, se envia en el login
        //    headers.put(RequestHeaders.TokenAutenticacion, RequestHeaders.getTokenauth());

        NetFacade.consumeWS(INICIAR_SESION,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.loginUrl),
                headers, request, IniciarSesionResponse.class, result);
    }

    /**
     * Método que se invoca para realizar el inicio de sesión de un Usuario o un Cliente BPT.
     *
     * @param request {@link IniciarSesionRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void iniciarSesionSimple(IniciarSesionRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenDispositivo, RequestHeaders.getTokendevice());
        if (!RequestHeaders.getTokenauth().isEmpty())//Si ya se almaceno el tokenAuth, se envia en el login
            headers.put(RequestHeaders.TokenAutenticacion, RequestHeaders.getTokenauth());

        NetFacade.consumeWS(INICIAR_SESION_SIMPLE,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.loginSimpleUrl),
                headers, request, IniciarSesionResponse.class, result);
    }


    /**
     * Método que se invoca para consultar los puntos que se quieren mostrar en el Mapa.
     *
     * @param request {@link LocalizarSucursalesRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void localizarSucursales(LocalizarSucursalesRequest request, IRequestResult result) throws OfflineException {
        NetFacade.consumeWS(LOCALIZAR_SUCURSALES,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.getSucursales),
                getHeadersYaGanaste(), request, LocalizarSucursalesResponse.class, result);
    }

    /**
     * Método que se invoca para obtener los Catálogos necesarios para la App Móvil.
     *
     * @param request {@link ObtenerCatalogoRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void obtenerCatalogos(ObtenerCatalogoRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.IdOperacion, "3");
        headers.put(RequestHeaders.NombreUsuario, "null");
        NetFacade.consumeWS(OBTENER_CATALOGOS,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.getCatalogsUrl),
                headers, request, ObtenerCatalogosResponse.class, result);
    }

    /**
     * Método que se invoca para obtener las Colonias a partir de un Código Postal.
     *
     * @param request {@link ObtenerColoniasPorCPRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void obtenerColoniasPorCP(ObtenerColoniasPorCPRequest request, IRequestResult result) throws OfflineException {
        NetFacade.consumeWS(OBTENER_COLONIAS_CP,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.getNeighborhoodByZipUrl),
                getHeadersYaGanaste(), request, ObtenerColoniasPorCPResponse.class, result);
    }

    /**
     * Método que se invoca para Consultar el estatus de los DocumentosFragment de los Comercios.
     *
     * @param result {@link IRequestResult} listener del resultado de la petición.
     */
    public static void obtenerDocumentos(IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();

        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(OBTENER_DOCUMENTOS,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.getDocuments),
                headers, null, ObtenerDocumentosRequest.class, result);
    }

    /**
     * Método que se invoca para obtener el Número de Teléfono al cual se debe enviar el SMS para la activación del Servicio Móvil.
     *
     * @param result {@link IRequestResult} listener del resultado de la petición.
     */
    public static void obtenerNumeroSMS(IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();

        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(OBTENER_NUMERO_SMS,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.getSMSNumberUrl),
                headers, null, ObtenerNumeroSMSResponse.class, result);
    }

    /**
     * Método que se invoca cuando se desea Recuperar una Contraseña de un Usurio.
     *
     * @param request {@link RecuperarContraseniaRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void recuperarContrasenia(RecuperarContraseniaRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        NetFacade.consumeWS(RECUPERAR_CONTRASENIA,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.recoverPasswordUrl),
                headers, request, RecuperarContraseniaResponse.class, result);
    }

    /**
     * Método que se invoca cuando se desea validar si existe un Usuario en el FWS Externo de BPT y en caso de existir,
     * nos indique si es Cliente de BPT y si es Agente.
     *
     * @param request {@link ValidarEstatusUsuarioRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void validarEstatusUsuario(ValidarEstatusUsuarioRequest request, IRequestResult result) throws OfflineException {
        NetFacade.consumeWS(VALIDAR_ESTATUS_USUARIO,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.validateStatusUserUrl),
                getHeadersYaGanaste(), request, ValidarEstatusUsuarioResponse.class, result);
    }

    /**
     * Método que se invoca cuando se desea validar composición de una Contraseña, apegado a las políticas internas de BPT.
     *
     * @param request {@link ValidarFormatoContraseniaRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void validarFormatoContrasenia(ValidarFormatoContraseniaRequest request, IRequestResult result) throws OfflineException {
        NetFacade.consumeWS(VALIDAR_FORMATO_CONTRASENIA,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.validateFormatPasswordUrl),
                getHeadersYaGanaste(), request, ValidarFormatoContraseniaResponse.class, result);
    }

    /**
     * Método que se invoca para verificar si ya se activó el Dispositivo y se asoció el Teléfono con la Cuenta especificada
     * en el Mensaje SMS. Ya que la recepción de los Mensajes SMS puede tomar tiempo, es posible que se tenga que invocar
     * varias veces este servicio para comprobar la activación del Servicio Móvil, para lo cual se recomienda hacer en
     * intervalos de tiempo de 30 segundos.
     *
     * @param result {@link IRequestResult} listener del resultado de la petición.
     */
    public static void verificarActivacion(IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenDispositivo, RequestHeaders.getTokendevice());
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(VERIFICAR_ACTIVACION,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.verifyActivateUrl),
                headers, null, VerificarActivacionResponse.class, result);
    }

    /**
     * No Description
     *
     * @param result {@link IRequestResult} listener del resultado de la petición.
     */
    /*TODO validar este servicio y sun funcionalidad*/
    public static void recursoImagen(IRequestResult result) throws OfflineException {
        NetFacade.consumeWS(RECURSO_IMAGEN,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.verifyActivateUrl),
                getHeadersYaGanaste(), null, null, result);
    }

    /**
     * No Description
     *
     * @param request {@link ActivacionAprovSofttokenRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void activacionAprovSofttoken(ActivacionAprovSofttokenRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        //headers.put(RequestHeaders.IdCuenta, RequestHeaders.IdCuenta);
        NetFacade.consumeWS(ACTIVACION_APROV_SOFTTOKEN,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.activateAprovSoftTokenUrl),
                headers, request, false, ActivacionAprovSofttokenResponse.class, result);
    }

    /**
     * No Description
     *
     * @param request {@link VerificarActivacionAprovSofttokenRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void verificarActivacionAprovSofttoken(VerificarActivacionAprovSofttokenRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        //headers.put(RequestHeaders.IdCuenta, RequestHeaders.IdCuenta);
        NetFacade.consumeWS(VERIFICAR_ACTIVACION_APROV_SOFTTOKEN,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.verifyActivateAprovSoftTokenUrl),
                headers, request, false, VerificarActivacionAprovSofttokenResponse.class, result);
    }

    /**
     * No Description
     *
     * @param request {@link IniciarTransaccionOnlineRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    /*TODO revisar request de este servicio*/
    public static void iniciarTransaccionOnline(IniciarTransaccionOnlineRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put(RequestHeaders.IdTransaccionFreja, RequestHeaders.getIdTransaccionFreja());
        headers.put(RequestHeaders.TokenFreja, RequestHeaders.getTokenFreja());

        NetFacade.consumeWS(INICIAR_TRANSACCION_ONLINE,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.initOnlineTransactionUrl),
                headers, request, IniciarTransaccionOnlineResponse.class, result);
    }

    /**
     * No Description
     *
     * @param request {@link GetJsonWebTokenRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    /*TODO revisar request de este servicio*/
    public static void getJsonWebTokenUrl(GetJsonWebTokenRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put(RequestHeaders.IdCuenta, RequestHeaders.getIdCuenta());
        NetFacade.consumeWS(GET_JSONWEBTOKEN,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.getJsonWebTokenUrlUrl),
                headers, request, GetJsonWebTokenResponse.class, result);
    }


    /**
     * Obtiene los subgiros par registro de Negocio.
     *
     * @param result {@link IRequestResult} listener del resultado de la petición.
     */
    /*TODO revisar request de este servicio*/
    public static void obtenerSubgiros(IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(OBTENER_SUBGIROS,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.obtenerSubgiros),
                headers, null, ObtenerSubgirosResponse.class, result);
    }

    /**
     * Obtiene los subgiros par registro de Negocio.
     *
     * @param result {@link IRequestResult} listener del resultado de la petición.
     */
    /*TODO revisar request de este servicio*/
    public static void obtenerDomicilioPrincipal(IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(OBTENER_DOMICILIO_PRINCIPAL,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.obtenerDomicilioPrincipal),
                headers, null, ObtenerDomicilioResponse.class, result);
    }


    /**
     * Metodo para obtener el domicilio del usuario
     */

    /**
     * Método que se invoca cuando se desea actualizar la información obtenida del Usuario/Cliente.
     *
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    /*public static void obtenerDomicilioPrincipal(IRequestResult result)  throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());

        NetFacade.consumeWS(OBTENER_DOMICILIO,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.obtenerDomicilio),
                headers,null, ObtenerDomicilioResponse.class,result);
    }*/


    /**
     * @param request
     * @param result
     * @throws OfflineException
     */
    public static void enviarTicketTAEPDS(EnviarTicketTAEPDSRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(ENVIAR_TICKET_TAEPDS,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.enviarTicketTAEPDS),
                headers, request, true, EnviarTicketTAEPDSResponse.class, result);
    }


    public static void generarRPC(IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put(RequestHeaders.IdCuenta, RequestHeaders.getIdCuenta());
        NetFacade.consumeWS(GENERAR_CODIGO_RECUPERACION,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.generate_rpc),
                headers, null, true, GenerarCodigoRecuperacionResponse.class, result);
    }

    /**
     * Envia al NetFacade lo necesario para consuir el servicio, arma los Headers restantes para consumir el servicio
     *
     * @param request
     * @param result
     * @throws OfflineException
     */
    public static void desasociarDispositivo(DesasociarDispositivoRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.IdCuenta, RequestHeaders.getIdCuenta());
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put("Content-Type", "application/json");

        NetFacade.consumeWS(DESASOCIAR_DISPOSITIVO,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.desasociarDispositivo),
                headers, request, true, DesasociarDispositivoResponse.class, result);
    }

    public static void cambiarEmail(CambiarEmailRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.IdCuenta, RequestHeaders.getIdCuenta());
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put("Content-Type", "application/json");

        NetFacade.consumeWS(DESASOCIAR_DISPOSITIVO,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.changePasswordUrl),
                headers, request, true, CambiarEmailResponse.class, result);
    }

    public static void updateDatosCuenta(ActualizarDatosCuentaRequest request,
                                         PreferUserIteractor result) throws OfflineException  {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put("IdOperacion", "0");
        headers.put("Content-Type", "application/json");

        NetFacade.consumeWS(UPDATE_DATOS_CUENTA,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.obtenerDatosCuenta),
                headers, request, true, ActualizarDatosCuentaResponse.class, result);
    }


    public static void CrearSolicitudCupo(CrearCupoSolicitudRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put("Content-Type", "application/json");



        NetFacade.consumeWS(CREA_SOLICITUD_CUPO,
                METHOD_POST,
                URL_SERVER_ADTVO /*"http://10.140.140.247:9000"*/  +  App.getContext().getString(R.string.cupoCrearSolicitudCupo),
                headers,
                request,
                CrearCupoSolicitudResponse.class,
                result
        );
    }


    /**
     * Método para realizar la Carga de los documentos para cupo.
     *
     * @param request {@link CargaDocumentosRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void cargaDocumentosCupo(CargaDocumentosRequest request, IRequestResult result) throws OfflineException {


        // TODO: Cambiar a url de desarollo
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(CARGA_DOCUMENTOS_CUPO,
                METHOD_POST,
                URL_SERVER_ADTVO /*"http://10.140.140.247:9000"*/  + App.getContext().getString(R.string.cupoCargaDocumentos),
                headers,
                request,
                CargaDocumentosResponse.class,
                result);
    }


    public static void actualizaDocumentosCupo(CargaDocumentosRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(ACTUALIZA_DOCUMENTOS_CUPO,
                METHOD_POST,
                /*URL_SERVER_ADTVO*/ "http://10.140.140.247:9000"  + App.getContext().getString(R.string.cupoActualizaDocumentosCupo),
                headers,
                request,
                CargaDocumentosResponse.class,
                result);
    }


    /**
     * Método que se invoca para Consultar el estatus de los DocumentosCupo dell Flujo de Cupo.
     *
     * @param result {@link IRequestResult} listener del resultado de la petición.
     */
    public static void obtenerEstadoDocumentosCupo(IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();

        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        NetFacade.consumeWS(OBTENER_ESTADO_DOCUMENTOS_CUPO,
                METHOD_GET,
                 /*URL_SERVER_ADTVO*/  "http://10.140.140.247:9000" + App.getContext().getString(R.string.cupoObtenerEstadoDeDocumentos),
                headers,
                null,
                EstadoDocumentosResponse.class,
                result);
    }



    public static void consultaStatusRegistroCupo(IRequestResult result) throws OfflineException{
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put(RequestHeaders.IdCuenta, RequestHeaders.getIdCuenta());
        NetFacade.consumeWS(
                CONSULTA_STATUS_REGISTRO_CUPO,
                METHOD_GET,
                /*URL_SERVER_ADTVO*/ "http://10.140.140.247:9000"  + App.getContext().getString(R.string.cupoObtenerSolicitudCupo),
                headers,
                null,
                EstadoSolicitudResponse.class ,
                result);
    }



}