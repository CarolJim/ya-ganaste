package com.pagatodo.yaganaste.ui.account;

import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.DatosSaldo;
import com.pagatodo.yaganaste.data.model.MessageValidation;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioClienteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.RecuperarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarEstatusUsuarioRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarFormatoContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarCuentaDisponibleRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ConsultaAsignacionTarjetaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarInformacionSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CrearUsuarioClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataEstatusUsuario;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesion;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataUsuarioCliente;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.IniciarSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerNumeroSMSResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.RecuperarContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarEstatusUsuarioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarFormatoContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.VerificarActivacionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarCuentaDisponibleResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarNIPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarAsignacionTarjetaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataConsultarAsignacion;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataCuentaDisponible;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IAccountIteractorNew;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.enums.AccountOperation;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.AccountOperation.CREATE_USER;
import static com.pagatodo.yaganaste.interfaces.enums.AccountOperation.LOGIN;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_CUENTA_DISPONIBLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_NIP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_ASIGNACION_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_CLIENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.INICIAR_SESION_SIMPLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_NUMERO_SMS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.RECUPERAR_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_ESTATUS_USUARIO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_FORMATO_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASOCIATE_PHONE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASSIGN_PIN;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_GET_CARD;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_PENDIENTE;
import static com.pagatodo.yaganaste.utils.Recursos.DEVICE_ALREADY_ASSIGNED;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountInteractorNew implements IAccountIteractorNew, IRequestResult {

    private String TAG = AccountInteractorNew.class.getName();
    private IAccountManager accountManager;
    private AccountOperation operationAccount;
    private Request requestAccountOperation;
    private boolean logOutBefore;

    public AccountInteractorNew(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public void validateUserStatus(String usuario) {
        RequestHeaders.setUsername(usuario); // Seteamos el usuario en el Header
        ValidarEstatusUsuarioRequest request = new ValidarEstatusUsuarioRequest(usuario);
        try {
            ApiAdtvo.validarEstatusUsuario(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(VALIDAR_ESTATUS_USUARIO, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void login(IniciarSesionRequest request) {
        try {
            ApiAdtvo.iniciarSesionSimple(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(INICIAR_SESION_SIMPLE, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void logout() {
        try {
            ApiAdtvo.cerrarSesion(this);// Se envia null ya que el Body no aplica.
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSessionData() {
        try {
            ApiAdtvo.actualizarInformacionSesion(this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkSessionState(Request request) {
        this.requestAccountOperation = request;
        if (request instanceof IniciarSesionRequest)
            this.operationAccount = LOGIN;
        else if (request instanceof CrearUsuarioClienteRequest)
            this.operationAccount = CREATE_USER;

        if (!RequestHeaders.getTokensesion().isEmpty()) {
            logOutBefore = true;
            logout();
        } else {
            logOutBefore = false;
            switch (this.operationAccount) {
                case CREATE_USER:
                    createUserClient((CrearUsuarioClienteRequest) this.requestAccountOperation); // Creamos usuario.
                    break;

                case LOGIN:
                    login((IniciarSesionRequest) this.requestAccountOperation);
                    break;
            }

        }
    }

    @Override
    public void checkCard(String numberCard) {
        ConsultaAsignacionTarjetaRequest request = new ConsultaAsignacionTarjetaRequest(numberCard);
        try {
            ApiTrans.consultaAsignacionTarjeta(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(CONSULTAR_ASIGNACION_TARJETA, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void assigmentAccountAvaliable(int idAccount) {
        AsignarCuentaDisponibleRequest request = new AsignarCuentaDisponibleRequest(idAccount);
        try {
            ApiTrans.asignarCuentaDisponible(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(ASIGNAR_CUENTA_DISPONIBLE, "");
        }
    }

    @Override
    public void validatePassword(String password) {
        ValidarFormatoContraseniaRequest request = new ValidarFormatoContraseniaRequest(password);
        try {
            ApiAdtvo.validarFormatoContrasenia(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(VALIDAR_FORMATO_CONTRASENIA, "");
        }
    }

    @Override
    public void createUser() {
        RegisterUser registerUser = RegisterUser.getInstance();
        /*Creamos Request para realizar registro*/
        CrearUsuarioClienteRequest request = new CrearUsuarioClienteRequest(
                registerUser.getEmail(),
                Utils.cipherRSA(registerUser.getContrasenia()),
                registerUser.getNombre(),
                registerUser.getApellidoPaterno(),
                registerUser.getApellidoMaterno(),
                registerUser.getGenero(),
                registerUser.getFechaNacimiento(),
                "",/*RFC*/
                "",/*CURP*/
                registerUser.getNacionalidad(),/*Nacionalidad*/
                registerUser.getIdEstadoNacimineto(),
                registerUser.getEmail(),
                "",/*Telefono*/
                "",/*Telefono Celular*/
                registerUser.getIdColonia(),
                registerUser.getColonia(),
                registerUser.getCodigoPostal(),
                registerUser.getCalle(),
                registerUser.getNumExterior(),
                registerUser.getNumInterior()
        );

        checkSessionState(request);
    }


    @Override
    public void createUserClient(CrearUsuarioClienteRequest request) {
         /*Establecemos las cabeceras de la peticion*/
        RequestHeaders.setTokendevice(Utils.getTokenDevice(App.getInstance().getApplicationContext()));
        RequestHeaders.setUsername(request.getCorreo()); // Seteamos el usuario en el Header
        try {
            ApiAdtvo.crearUsuarioCliente(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(CREAR_USUARIO_CLIENTE, "");
        }
    }

    @Override
    public void assignmentNIP(AsignarNIPRequest request) {
        try {
            ApiTrans.asignarNip(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(ASIGNAR_NIP, "");
        }
    }

    @Override
    public void getSMSNumber() {
        try {
            ApiAdtvo.obtenerNumeroSMS(this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(OBTENER_NUMERO_SMS, "");
        }
    }

    @Override
    public void verifyActivationSMS() {
        try {
            ApiAdtvo.verificarActivacion(this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(VERIFICAR_ACTIVACION, "");
        }
    }

    @Override
    public void getNeighborhoodByZipCode(String zipCode) {
        ObtenerColoniasPorCPRequest request = new ObtenerColoniasPorCPRequest(zipCode);
        try {
            ApiAdtvo.obtenerColoniasPorCP(request, this);

        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(OBTENER_COLONIAS_CP, "");

        }
    }

    @Override
    public void recoveryPassword(RecuperarContraseniaRequest request) {

        try {
            ApiAdtvo.recuperarContrasenia(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(RECUPERAR_CONTRASENIA, "");
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

        switch (dataSourceResult.getWebService()) {

            case VALIDAR_ESTATUS_USUARIO:
                processStatusEmail(dataSourceResult);
                break;

            case INICIAR_SESION_SIMPLE:
                processLogin(dataSourceResult);
                break;

            case CERRAR_SESION:
                RequestHeaders.setTokensesion("");//Reseteamos el token de sesión
                if (logOutBefore) {
                    logOutBefore = false;
                    switch (this.operationAccount) {
                        case CREATE_USER:
                            createUserClient((CrearUsuarioClienteRequest) this.requestAccountOperation); // Creamos usuario.
                            break;
                        case LOGIN:
                            login((IniciarSesionRequest) this.requestAccountOperation);
                            break;
                    }

                } else {
                    //TODO Evento para llevar al usuario al splash
                }

                break;

            case CONSULTAR_ASIGNACION_TARJETA:
                selectValidationCardStep(dataSourceResult);
                break;

            case VALIDAR_FORMATO_CONTRASENIA:
                processValidationPassword(dataSourceResult);
                break;

            case CREAR_USUARIO_CLIENTE:
                processClientCreated(dataSourceResult);
                break;

            case OBTENER_COLONIAS_CP:

                processNeighborhoods(dataSourceResult);

                break;

            case ASIGNAR_CUENTA_DISPONIBLE:
                processAssigmentAccount(dataSourceResult);
                break;

            case ASIGNAR_NIP:
                processNIPAssigned(dataSourceResult);
                break;

            case OBTENER_NUMERO_SMS:
                processNumberSMS(dataSourceResult);
                break;

            case VERIFICAR_ACTIVACION:
                processVerifyActivation(dataSourceResult);
                break;

            case ACTUALIZAR_INFO_SESION:
                proccesDataSession(dataSourceResult);
                break;

            case RECUPERAR_CONTRASENIA:
                proccesDataRecuperarContrasenia(dataSourceResult);
                break;

            default:
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        accountManager.onError(error.getWebService(), error.getData().toString());
    }

    /**
     * Método para decidir a donde dirigir al usuario dependiendo su estatus de usuario
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processStatusEmail(DataSourceResult response) {
        ValidarEstatusUsuarioResponse data = (ValidarEstatusUsuarioResponse) response.getData();
        DataEstatusUsuario userStatus = data.getData();
        String eventTypeUser = "";
        if (data.getCodigoRespuesta() == CODE_OK) {
            // RequestHeaders.setOperation(String.valueOf(data.getIdOperacion()));//TODO validar razon de esta asignación
            //Seteamos los datos del usuario en el SingletonUser.
            if (userStatus.isEsUsuario()) {
                /*SingletonUser user = SingletonUser.getInstance();
                user.getDataUser().setEsUsuario(userStatus.isEsUsuario());
                user.getDataUser().setEsCliente(userStatus.isEsCliente());
                user.getDataUser().setEsAgente(userStatus.isEsAgente());
                user.getDataUser().setConCuenta(userStatus.isConCuenta());
                user.getDataUser().getUsuario().setIdUsuario(userStatus.getIdUsuario() != null ? userStatus.getIdUsuario() : 0);*/
                accountManager.onSucces(response.getWebService(), true);
            } else {
                accountManager.onSucces(response.getWebService(), false);
            }

        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(), data.getMensaje());
        }

    }

    /**
     * Método para procesar respuesta del Login.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processLogin(DataSourceResult response) {
        IniciarSesionResponse data = (IniciarSesionResponse) response.getData();
        DataIniciarSesion dataUser = data.getData();
        String stepByUserStatus = "";
        if (data.getCodigoRespuesta() == CODE_OK) {
            //Seteamos los datos del usuario en el SingletonUser.
            SingletonUser user = SingletonUser.getInstance();
            if (!dataUser.isEsAgente()) {
                data.getData().getUsuario().setTipoAgente(17);
            }

            user.setDataUser(dataUser);
            user.getDataUser().setEsAgente(false);/*TODO Testin de flujo Adq*/
            if (dataUser.isEsUsuario()) { // Si Usuario
                RequestHeaders.setTokensesion(dataUser.getUsuario().getTokenSesion());//Guardamos Token de sesion
                if (dataUser.isConCuenta()) {// Si Cuenta
                    if (dataUser.getUsuario().getCuentas().get(0).isAsignoNip()) { // NO necesita NIP
                        if (!dataUser.isRequiereActivacionSMS()) {// No Requiere Activacion de SMS
                            //if(true){// No Requiere Activacion de SMS
                            /*TODO Aqui se debe de manejar el caso en el que el usuario no haya realizado el aprovisionamiento*/
                            user.setDatosSaldo(new DatosSaldo(String.format("%s", dataUser.getUsuario().getCuentas().get(0).getSaldo())));
                            stepByUserStatus = EVENT_GO_MAINTAB; // Vamos al TabActiviy
                        } else { // Requiere Activacion SMS
                            stepByUserStatus = EVENT_GO_ASOCIATE_PHONE;
                        }
                    } else {//Requiere setear el NIP
                        stepByUserStatus = EVENT_GO_ASSIGN_PIN;
                    }
                    RequestHeaders.setIdCuenta(String.format("%s", data.getData().getUsuario().getCuentas().get(0).getIdCuenta()));
                } else { // No tiene cuenta asignada.
                    stepByUserStatus = EVENT_GO_GET_CARD; // Mostramos pantalla para asignar cuenta.
                }

                accountManager.goToNextStepAccount(stepByUserStatus, null); // Enviamos al usuario a la pantalla correspondiente.
            } else { // No es usuario
                accountManager.onError(response.getWebService(), App.getContext().getString(R.string.usuario_no_existe));
            }
        } else {
            accountManager.onError(response.getWebService(), data.getMensaje());
        }
    }


    /**
     * Método para seleccionar la pantalla que se debe mostrar dependiendo de la validación de la tarjeta.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void selectValidationCardStep(DataSourceResult response) {
        ConsultarAsignacionTarjetaResponse data = (ConsultarAsignacionTarjetaResponse) response.getData();
        DataConsultarAsignacion dataCard = data.getData();
        RequestHeaders.setOperation(String.valueOf(data.getIdOperacion()));//Seteamos el IdOperacion
        if (data.getCodigoRespuesta() == CODE_OK) {
            Card card = Card.getInstance();
            card.setHasClient(dataCard.isConCliente());
            if (!card.isHasClient()) {
                card.setAlias(dataCard.getAlias());
                card.setUserName(dataCard.getNombreUsuario());
                card.setIdAccount(dataCard.getIdCuenta());
                Log.w(TAG, "parseJson Card Account: " + dataCard.getIdCuenta());
                SingletonUser user = SingletonUser.getInstance();
                user.getDataExtraUser().setNeedSetPin(true);//TODO Validar esta bandera
                accountManager.onSucces(response.getWebService(), "Tarjeta Válida");
            } else {
                /*TODO enviar mensaje a vista*/
                accountManager.onError(response.getWebService(), "Esta Tarjeta Ya Ha Sido Asignada, Ingresa Otra Tarjeta o Selecciona la Opción NO TENGO TARJETA");
            }
        } else {
            accountManager.onError(response.getWebService(), data.getMensaje());
        }

    }

    /**
     * Método para procesar la respuesta del servicio respecto al formato de la contraseña.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processValidationPassword(DataSourceResult response) {
        ValidarFormatoContraseniaResponse data = (ValidarFormatoContraseniaResponse) response.getData();
        if (data.getAccion() == CODE_OK) {
            accountManager.onSucces(response.getWebService(), data.getMensaje());
        } else {
            accountManager.onError(response.getWebService(), data.getMensaje());//Retornamos mensaje de error.
        }
    }

    /**
     * Método para procesar la respuesta sobre la creación de un nuevo cliente.
     *
     * @param response {@link DataSourceResult} respuesta del servicio.
     */
    private void processClientCreated(DataSourceResult response) {
        CrearUsuarioClienteResponse data = (CrearUsuarioClienteResponse) response.getData();
        DataUsuarioCliente dataUser = data.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            RequestHeaders.setUsername(RegisterUser.getInstance().getEmail());
            RequestHeaders.setTokensesion(dataUser.getUsuario().getTokenSesion()); // Guardamos el Token de Sesión
            //Seteamos los datos del usuario en el SingletonUser.
            SingletonUser user = SingletonUser.getInstance();
            user.getDataUser().setRequiereActivacionSMS(dataUser.isRequiereActivacionSMS());
            user.getDataUser().setSemilla(dataUser.getSemilla());
            user.getDataUser().getUsuario().setIdUsuario(dataUser.getUsuario().getIdUsuario());
            accountManager.onSucces(response.getWebService(), data.getMensaje());
        } else {
            accountManager.onError(response.getWebService(), data.getMensaje());//Retornamos mensaje de error.
        }
    }


    /**
     * Método para procesar la respuesta con la lista de colonias en el código postal.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processNeighborhoods(DataSourceResult response) {
        ObtenerColoniasPorCPResponse data = (ObtenerColoniasPorCPResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            List<ColoniasResponse> listaColonias = data.getData();
            if (listaColonias != null && listaColonias.size() > 0) {
                accountManager.onSucces(response.getWebService(), listaColonias);

            } else {
                accountManager.onError(response.getWebService(), "Verifica tu Código Postal");//Retornamos mensaje de error.
            }
        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(), data.getMensaje());//Retornamos mensaje de error.
        }
    }

    /**
     * Método para procesar la respuesta uan vez que se realizo la asignación de una cuenta disponible.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processAssigmentAccount(DataSourceResult response) {
        AsignarCuentaDisponibleResponse data = (AsignarCuentaDisponibleResponse) response.getData();
        DataCuentaDisponible cuentaAsiganadaData = data.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            //Obtenemos cuenta asiganada.
            CuentaResponse cuenta = cuentaAsiganadaData.getCuenta();
            SingletonUser user = SingletonUser.getInstance();
            if (user.getDataUser().getUsuario().getCuentas() == null) {
                user.getDataUser().getUsuario().setCuentas(new ArrayList<CuentaResponse>());
            }
            if (user.getDataUser().getUsuario().getCuentas().isEmpty()) {
                user.getDataUser().getUsuario().getCuentas().add(new CuentaResponse());
            }
            // Asignamos la cuenta al usuario TODO el servicio maneja lista en login
            user.getDataUser().getUsuario().getCuentas().get(0).setIdCuenta(cuenta.getIdCuenta());
            RequestHeaders.setIdCuenta(String.format("%s", cuenta.getIdCuenta()));
            Card.getInstance().setIdAccount(cuenta.getIdCuenta());
            accountManager.onSucces(response.getWebService(), data.getMensaje());

        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(), data.getMensaje());//Retornamos mensaje de error.
        }
    }

    /**
     * Método para procesar la respuesta una vez que se asigno el NIP a la tarjeta.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processNIPAssigned(DataSourceResult response) {
        AsignarNIPResponse data = (AsignarNIPResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            accountManager.onSucces(response.getWebService(), data.getMensaje());
        } else {
            accountManager.onError(response.getWebService(), data.getMensaje());
        }
    }

    /**
     * Método para procesar la respuesta al recuperar el número para envío de SMS
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processNumberSMS(DataSourceResult response) {
        ObtenerNumeroSMSResponse data = (ObtenerNumeroSMSResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            SingletonUser user = SingletonUser.getInstance();
            String phone = data.getData().getNumeroTelefono();
            String tokenValidation = user.getDataUser().getSemilla() + RequestHeaders.getUsername() + RequestHeaders.getTokendevice();
            Log.d("WSC", "TokenValidation: " + tokenValidation);
            String tokenValidationSHA = Utils.bin2hex(Utils.getHash(tokenValidation));
            String message = String.format("%sT%sT%s",
                    user.getDataUser().getUsuario().getIdUsuario(),
                    user.getDataUser().getUsuario().getCuentas().get(0).getIdCuenta(), tokenValidationSHA);

            MessageValidation messageValidation = new MessageValidation(phone, message);
            accountManager.onSucces(response.getWebService(), messageValidation);
        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(), data.getMensaje());//Retornamos mensaje de error.
        }
    }

    /**
     * Método para procesar la respuesta sobre la verificación de la asociación del teléfono mediante el SMS
     * enviado previamente.
     *
     * @param response {@link DataSourceResult} respuesta del servicio.
     */
    private void processVerifyActivation(DataSourceResult response) {
        VerificarActivacionResponse data = (VerificarActivacionResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            SingletonUser user = SingletonUser.getInstance();
            user.getDataExtraUser().setPhone(data.getData().getNumeroTelefono());
            //Almacenamos el Token de Autenticacion
            RequestHeaders.setTokenauth(data.getData().getTokenAutenticacion());
            accountManager.onSucces(response.getWebService(), data.getMensaje());
        } else if (data.getCodigoRespuesta() == DEVICE_ALREADY_ASSIGNED) {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(), new Object[]{DEVICE_ALREADY_ASSIGNED, data.getMensaje()});//Retornamos mensaje de error.
        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(), data.getMensaje());//Retornamos mensaje de error.
        }
    }


    private void proccesDataSession(DataSourceResult response) {
        ActualizarInformacionSesionResponse data = (ActualizarInformacionSesionResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            DataIniciarSesion newSessionData = data.getData();
            SingletonUser userInfo = SingletonUser.getInstance();
            userInfo.setDataUser(newSessionData);
            /*TODO 10/05/17 obtener saldo por medio de ws de saldos.*/
            userInfo.setDatosSaldo(new DatosSaldo(String.format("%s", userInfo.getDataUser().getUsuario().getCuentas().get(0).getSaldo())));
            accountManager.onSucces(response.getWebService(), "Información Actualizada.");
        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(), data.getMensaje());//Retornamos mensaje de error.
        }
    }

    private void proccesDataRecuperarContrasenia(DataSourceResult response) {
        RecuperarContraseniaResponse data = (RecuperarContraseniaResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            accountManager.onSucces(response.getWebService(), data.getMensaje());
        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(), data.getMensaje());//Retornamos mensaje de error.
        }
    }
}