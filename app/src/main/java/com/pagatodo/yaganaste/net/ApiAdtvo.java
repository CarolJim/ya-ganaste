package com.pagatodo.yaganaste.net;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.*;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.*;
import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.*;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.*;
import static com.pagatodo.yaganaste.utils.Recursos.URL_SERVER_ADTVO;

/**
 * Created by flima on 17/03/2017.
 *
 * Clase para gestionar el WS de Ya Ganaste Administrativo.
 */

public class ApiAdtvo extends Api {

    /**
     * Método que se invoca para activar el Servicio de Banca Móvil, asociando una Cuenta con el telefóno
     * desde el cual se recibió el SMS.
     *
     * @param request {@link ActivacionServicioMovilRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void activacionServicioMovil(ActivacionServicioMovilRequest request, IRequestResult result) {
        NetFacade.consumeWS(ACTIVACION_SERVICIO_MOVIL,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.activateMobileServiceUrl),
                headersYaGanaste,request, ActivacionServicioMovilResponse.class,result);
    }

    /**
     * Método que se invoca para subir la imagen del Avatar, o para asociar una URL publica que será usada como Avatar.
     *
     * @param request {@link ActualizarAvatarRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void actualizarAvatar(ActualizarAvatarRequest request, IRequestResult result) {
        NetFacade.consumeWS(ACTUALIZAR_AVATAR,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.updateAvatarUrl),
                headersYaGanaste,request, ActualizarAvatarResponse.class,result);
    }

    /**
     * Método que se invoca cuando se desea actualizar la información obtenida del Usuario/Cliente.
     *
     * @param request {@link ActualizarInformacionSesionRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void actualizarInformacionSesion(ActualizarInformacionSesionRequest request, IRequestResult result) {
        NetFacade.consumeWS(ACTUALIZAR_INFO_SESION,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.updateSessionUrl),
                headersYaGanaste,request, ActualizarInformacionSesionResponse.class,result);
    }

    /**
     * Método que se invoca para asignar una nueva Contraseña a un Usuario cuando este solicita Recuperar su Contraseña.
     *
     * @param request {@link AsignarContraseniaRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void asigarContrasenia(AsignarContraseniaRequest request, IRequestResult result) {
        NetFacade.consumeWS(ASIGNAR_CONTRASENIA,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.assignPaswordUrl),
                headersYaGanaste,request, AsignarContraseniaResponse.class,result);
    }

    /**
     * Método que se invoca cuando se desea Cambiar la Contraseña actual del Usuario.
     *
     * @param request {@link CambiarContraseniaRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void cambiarContrasenia(CambiarContraseniaRequest request, IRequestResult result) {
        NetFacade.consumeWS(CAMBIAR_CONTRASENIA,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.changePasswordUrl),
                headersYaGanaste,request, CambiarContraseniaResponse.class,result);
    }

    /**
     * Método para realizar la Carga de los documentos para la afiliación de Comercios.
     *
     * @param request {@link CargaDocumentosRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void cargaDocumentos(CargaDocumentosRequest request, IRequestResult result) {
        NetFacade.consumeWS(CARGA_DOCUMENTOS,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.uploadDocumentsUrl),
                headersYaGanaste,request, CargaDocumentosResponse.class,result);
    }

    /**
     * Método que se invoca cuando se desea Cerrar una Sesión activa.
     *
     * @param request {@link CerrarSesionRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void cerrarSesion(CerrarSesionRequest request, IRequestResult result) {
        NetFacade.consumeWS(CERRAR_SESION,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.logoutUrl),
                headersYaGanaste,request, CerrarSesionResponse.class,result);
    }

    /**
     * Método que se invoca cuando se desean obtener más movimientos por mes.
     *
     * @param request {@link ConsultarMovimientosRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void consultarMovimientosMes(ConsultarMovimientosRequest request, IRequestResult result) {
        NetFacade.consumeWS(CONSULTAR_MOVIMIENTOS_MES,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.getMovementsByMonthUrl),
                headersYaGanaste,request, ConsultarMovimientosMesResponse.class,result);
    }

    /**
     * Método que se invoca para que un Cliente se vuelva Agente.
     *
     * @param request {@link CrearAgenteRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void crearAgente(CrearAgenteRequest request, IRequestResult result) {
        NetFacade.consumeWS(CREAR_AGENTE,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.createAgentUrl),
                headersYaGanaste,request, CrearAgenteResponse.class,result);
    }

    /**
     * Método que se invoca cuando se desea generar un nuevo Usuario en el FWS Externo de BPT.
     *
     * @param request {@link CrearUsuarioFWSRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void crearUsuarioFWS(CrearUsuarioFWSRequest request, IRequestResult result) {
        NetFacade.consumeWS(CREAR_USUARIO_FWS,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.createUserUrl),
                headersYaGanaste,request, CrearUsuarioFWSResponse.class,result);
    }

    /**
     * Método que se invoca para remover la imagen asignada para el Avatar.
     *
     * @param request {@link EliminarAvatarRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void eliminarAvatar(EliminarAvatarRequest request, IRequestResult result) {
        NetFacade.consumeWS(ELIMINAR_AVATAR,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.deleteAvatarUrl),
                headersYaGanaste,request, EliminarAvatarResponse.class,result);
    }

    /**
     * Método que se invoca para realizar el inicio de sesión de un Usuario o un Cliente BPT.
     *
     * @param request {@link IniciarSesionRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void iniciarSesion(IniciarSesionRequest request, IRequestResult result) {
        NetFacade.consumeWS(INICIAR_SESION,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.loginUrl),
                headersYaGanaste,request, IniciarSesionResponse.class,result);
    }

    /**
     * Método que se invoca para consultar los puntos que se quieren mostrar en el Mapa.
     *
     * @param request {@link LocalizarSucursalesRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void localizarSucursales(LocalizarSucursalesRequest request, IRequestResult result) {
        NetFacade.consumeWS(LOCALIZAR_SUCURSALES,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.getSucursales),
                headersYaGanaste,request, LocalizarSucursalesResponse.class,result);
    }

    /**
     * Método que se invoca para obtener los Catálogos necesarios para la App Móvil.
     *
     * @param request {@link ObtenerCatalogoRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void obtenerCatalogos(ObtenerCatalogoRequest request, IRequestResult result) {
        NetFacade.consumeWS(OBTENER_CATALOGOS,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.getCatalogsUrl),
                headersYaGanaste,request, ObtenerCatalogosResponse.class,result);
    }

    /**
     * Método que se invoca para obtener las Colonias a partir de un Código Postal.
     *
     * @param request {@link ObtenerColoniasPorCPRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void obtenerColoniasPorCP(ObtenerColoniasPorCPRequest request, IRequestResult result) {
        NetFacade.consumeWS(OBTENER_COLONIAS_CP,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.getNeighborhoodByZipUrl),
                headersYaGanaste,request, ObtenerColoniasPorCPResponse.class,result);
    }

    /**
     * Método que se invoca para Consultar el estatus de los Documentos de los Comercios.
     *
     * @param request {@link ObtenerDocumentosRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void obtenerDocumentos(ObtenerDocumentosRequest request, IRequestResult result) {
        NetFacade.consumeWS(OBTENER_DOCUMENTOS,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.getDocuments),
                headersYaGanaste,request, ObtenerDocumentosResponse.class,result);
    }

    /**
     * Método que se invoca para obtener el Número de Teléfono al cual se debe enviar el SMS para la activación del Servicio Móvil.
     *
     * @param request {@link ObtenerNumeroSMSRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void obtenerNumeroSMS(ObtenerNumeroSMSRequest request, IRequestResult result) {
        NetFacade.consumeWS(OBTENER_NUMERO_SMS,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.getSMSNumberUrl),
                headersYaGanaste,request, ObtenerNumeroSMSResponse.class,result);
    }

    /**
     * Método que se invoca cuando se desea Recuperar una Contraseña de un Usurio.
     *
     * @param request {@link RecuperarContraseniaRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void recuperarContrasenia(RecuperarContraseniaRequest request, IRequestResult result) {
        NetFacade.consumeWS(RECUPERAR_CONTRASENIA,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.recoverPasswordUrl),
                headersYaGanaste,request, RecuperarContraseniaResponse.class,result);
    }

    /**
     * Método que se invoca cuando se desea validar si existe un Usuario en el FWS Externo de BPT y en caso de existir,
     * nos indique si es Cliente de BPT y si es Agente.
     *
     * @param request {@link ValidarEstatusUsuarioRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void validarEstatusUsuario(ValidarEstatusUsuarioRequest request, IRequestResult result) {
        NetFacade.consumeWS(VALIDAR_ESTATUS_USUARIO,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.validateStatusUserUrl),
                headersYaGanaste,request, ValidarEstatusUsuarioResponse.class,result);
    }

    /**
     * Método que se invoca cuando se desea validar composición de una Contraseña, apegado a las políticas internas de BPT.
     *
     * @param request {@link ValidarFormatoContraseniaRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void validarFormatoContrasenia(ValidarFormatoContraseniaRequest request, IRequestResult result) {
        NetFacade.consumeWS(VALIDAR_FORMATO_CONTRASENIA,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.validateFormatPasswordUrl),
                headersYaGanaste,request, ValidarFormatoContraseniaResponse.class,result);
    }

    /**
     * Método que se invoca para verificar si ya se activó el Dispositivo y se asoció el Teléfono con la Cuenta especificada
     * en el Mensaje SMS. Ya que la recepción de los Mensajes SMS puede tomar tiempo, es posible que se tenga que invocar
     * varias veces este servicio para comprobar la activación del Servicio Móvil, para lo cual se recomienda hacer en
     * intervalos de tiempo de 30 segundos.
     *
     * @param request {@link VerificarActivacionRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void verificarActivacion(VerificarActivacionRequest request, IRequestResult result) {
        NetFacade.consumeWS(VERIFICAR_ACTIVACION,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.verifyActivateUrl),
                headersYaGanaste,request, VerificarActivacionResponse.class,result);
    }

    /**
     * No Description
     *
     * @param request {@link RecursoImagenRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    /*TODO validar este servicio y sun funcionalidad*/
    public static void recursoImagen(RecursoImagenRequest request, IRequestResult result) {
        NetFacade.consumeWS(RECURSO_IMAGEN,
                METHOD_GET, URL_SERVER_ADTVO + App.getContext().getString(R.string.verifyActivateUrl),
                headersYaGanaste,request, null,result);
    }

    /**
     * No Description
     *
     * @param request {@link ActivacionAprovSofttokenRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void activacionAprovSofttoken(ActivacionAprovSofttokenRequest request, IRequestResult result) {
        NetFacade.consumeWS(ACTIVACION_APROV_SOFTTOKEN,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.activateAprovSoftTokenUrl),
                headersYaGanaste,request, ActivacionAprovSofttokenResponse.class,result);
    }

    /**
     * No Description
     *
     * @param request {@link VerificarActivacionAprovSofttokenRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void verificarActivacionAprovSofttoken(VerificarActivacionAprovSofttokenRequest request, IRequestResult result) {
        NetFacade.consumeWS(VERIFICAR_ACTIVACION_APROV_SOFTTOKEN,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.verifyActivateAprovSoftTokenUrl),
                headersYaGanaste,request, VerificarActivacionAprovSofttokenResponse.class,result);
    }

    /**
     * No Description
     *
     * @param request {@link IniciarTransaccionOnlineRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    /*TODO revisar request de este servicio*/
    public static void iniciarTransaccionOnline(IniciarTransaccionOnlineRequest request, IRequestResult result) {
        NetFacade.consumeWS(INICIAR_TRANSACCION_ONLINE,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.initOnlineTransactionUrl),
                headersYaGanaste,request, IniciarTransaccionOnlineResponse.class,result);
    }

    /**
     * No Description
     *
     * @param request {@link GetJsonWebTokenRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    /*TODO revisar request de este servicio*/
    public static void getJsonWebTokenUrl(GetJsonWebTokenRequest request, IRequestResult result) {
        NetFacade.consumeWS(GET_JSONWEBTOKEN,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.getJsonWebTokenUrlUrl),
                headersYaGanaste,request, GetJsonWebTokenResponse.class,result);
    }

}