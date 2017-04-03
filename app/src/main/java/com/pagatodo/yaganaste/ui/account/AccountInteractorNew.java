package com.pagatodo.yaganaste.ui.account;

import android.util.Log;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.MessageValidation;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioFWSRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarEstatusUsuarioRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarFormatoContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarCuentaDisponibleRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ConsultaAsignacionTarjetaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.CrearClienteRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CrearUsuarioFWSInicioSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataEstatusUsuario;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesion;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataUsuarioFWSInicioSesion;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.IniciarSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerNumeroSMSResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarEstatusUsuarioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarFormatoContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.VerificarActivacionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarCuentaDisponibleResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarNIPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarAsignacionTarjetaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.CrearClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataConsultarAsignacion;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataCuentaDisponible;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IAccountIteractorNew;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.enums.TypeLogin;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.TypeLogin.LOGIN_AFTER_REGISTER;
import static com.pagatodo.yaganaste.interfaces.enums.TypeLogin.LOGIN_NORMAL;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_COMPLETO;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.DEVICE_ALREADY_ASSIGNED;
import static com.pagatodo.yaganaste.utils.Recursos.ERROR_LOGIN;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountInteractorNew implements IAccountIteractorNew,IRequestResult {

    private String TAG = AccountInteractorNew.class.getName();
    private IAccountManager accountManager;
    private TypeLogin typeLogin;
    private CrearUsuarioFWSRequest crearUsuarioFWSRequest;
    private boolean logOutBeforeLogin;

    public AccountInteractorNew(IAccountManager accountManager){
        this.accountManager = accountManager;
    }

    @Override
    public void validateUserStatus(String usuario) {
        RequestHeaders.setUsername(usuario); // Seteamos el usuario en el Header
        ValidarEstatusUsuarioRequest request = new ValidarEstatusUsuarioRequest(usuario);
        try {
            ApiAdtvo.validarEstatusUsuario(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void login(IniciarSesionRequest request) {
        try{
            ApiAdtvo.iniciarSesion(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logout() {
        try{
        ApiAdtvo.cerrarSesion(this);// Se envia null ya que el Body no aplica.
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkSessionState(CrearUsuarioFWSRequest request) {
        if(!RequestHeaders.getTokensesion().isEmpty()) {
            logOutBeforeLogin = true;
            logout();
        }else{
            logOutBeforeLogin = false;
            createUserFWSWithLogin(request); // Creamos usuario.
        }
    }

    @Override
    public void checkCard(String numberCard) {
        ConsultaAsignacionTarjetaRequest request = new ConsultaAsignacionTarjetaRequest(numberCard);
        try{
            ApiTrans.consultaAsignacionTarjeta(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void assigmentAccountAvaliable(int idAccount) {
        AsignarCuentaDisponibleRequest request = new AsignarCuentaDisponibleRequest(idAccount);
        try {
            ApiTrans.asignarCuentaDisponible(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void validatePassword(String password) {
        ValidarFormatoContraseniaRequest request = new ValidarFormatoContraseniaRequest(password);
        try{
            ApiAdtvo.validarFormatoContrasenia(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUser() {
        RegisterUser registerUser = RegisterUser.getInstance();
        CrearUsuarioFWSRequest request = new CrearUsuarioFWSRequest(
                registerUser.getEmail(),
                registerUser.getNombre(),
                registerUser.getApellidoPaterno(),
                registerUser.getApellidoMaterno(),
                registerUser.getEmail(),
                registerUser.getContrasenia());

        this.crearUsuarioFWSRequest = request;

        checkSessionState(request);
        //createUserFWS(request);// Creamos Usuario FWS.
    }

    @Override
    public void createUserFWS(CrearUsuarioFWSRequest request) {
        try {
            ApiAdtvo.crearUsuarioFWS(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUserFWSWithLogin(CrearUsuarioFWSRequest request) {
        try {
            ApiAdtvo.crearUsuarioFWSInicioSesion(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activationLogin() {
        IniciarSesionRequest request = new IniciarSesionRequest(
                RequestHeaders.getUsername(),
                RegisterUser.getInstance().getContrasenia(),
                RegisterUser.getInstance().getTelefono());
        /**Se verifica primero el estatus del usuario, si esta logueado en la db primero se cierra sesión y
         * posteriormente se consume el método de login()*/
        //checkSessionState(LOGIN_AFTER_REGISTER,request);

        /*this.typeLogin = LOGIN_AFTER_REGISTER;
        this.loginRequest = request;
        login();*/
    }

    @Override
    public void createClient() {
        RegisterUser register = RegisterUser.getInstance();
        //Creamos el request
        CrearClienteRequest request = new CrearClienteRequest();
        request.setNombre(register.getNombre());
        request.setPrimerApellido(register.getApellidoPaterno());
        request.setSegundoApellido(register.getApellidoMaterno());
        request.setFechaNacimiento(register.getFechaNacimiento());
        request.setGenero(register.getGenero());
        request.setRFC("");
        request.setCURP("");
        request.setIdPaisNacimiento(127);// TODO Validar este campo - 127 para Mexico
        request.setNacionalidad("MX");
        request.setIdEstadoNacimiento(register.getIdEstadoNacimineto());
        request.setCorreo(register.getEmail());
        request.setTelefono("");
        request.setTelefonoCelular("");
        request.setIdColonia(register.getIdColonia());
        request.setColonia(register.getColonia());
        request.setCP(register.getCodigoPostal());
        request.setCalle(register.getCalle());
        request.setNumeroExterior(register.getNumExterior());
        request.setNumeroInterior(register.getNumInterior());

        try {
            ApiTrans.crearCliente(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void assignmentNIP(AsignarNIPRequest request) {
        try {
            ApiTrans.asignarNip(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getSMSNumber() {
        try {
            ApiAdtvo.obtenerNumeroSMS(this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void verifyActivationSMS() {
        try {
            ApiAdtvo.verificarActivacion(this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getNeighborhoodByZipCode(String zipCode) {
        ObtenerColoniasPorCPRequest request = new ObtenerColoniasPorCPRequest(zipCode);
        try{
            ApiAdtvo.obtenerColoniasPorCP(request,this );
        } catch (OfflineException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

        switch (dataSourceResult.getWebService()) {

            case VALIDAR_ESTATUS_USUARIO:
                processStatusEmail(dataSourceResult);
                break;

            case INICIAR_SESION:
                if(this.typeLogin == LOGIN_NORMAL)
                    processLogin(dataSourceResult);
                else if(this.typeLogin == LOGIN_AFTER_REGISTER)
                    processLoginAfterRegister(dataSourceResult);
                break;

            case CERRAR_SESION:
                RequestHeaders.setTokensesion("");//Reseteamos el token de sesión
                if(logOutBeforeLogin){
                    logOutBeforeLogin = false;
                    createUserFWSWithLogin(this.crearUsuarioFWSRequest);
                }else {
                    //TODO Evento para llevar al usuario al splash
                }

                break;

            case CONSULTAR_ASIGNACION_TARJETA:
                selectValidationCardStep(dataSourceResult);
                break;

            case VALIDAR_FORMATO_CONTRASENIA:
                processValidationPassword(dataSourceResult);
                break;

            case CREAR_USUARIO_FWS:
                processUserCreated(dataSourceResult);
                break;

            case CREAR_USUARIO_FWS_LOGIN:
                processUserCreatedLoged(dataSourceResult);
                break;

            case CREAR_CLIENTE:
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

            default:
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        /**TODO Casos de Servicio fallido*/
        accountManager.onError(error.getWebService(),error.getData().toString());
    }

    /**
     * Método para decidir si es dirigido al {@link com.pagatodo.yaganaste.ui.account.login.LoginFragment} o al
     * {@link com.pagatodo.yaganaste.ui.account.register.GetCardFragment} dependiendo su estatus de usuario
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processStatusEmail(DataSourceResult response){
        ValidarEstatusUsuarioResponse  data = (ValidarEstatusUsuarioResponse) response.getData();
        DataEstatusUsuario userStatus = data.getData();
        String eventTypeUser = "";
        if(data.getCodigoRespuesta() == CODE_OK){
           // RequestHeaders.setOperation(String.valueOf(data.getIdOperacion()));//TODO validar razon de esta asignación
            //Seteamos los datos del usuario en el SingletonUser.
            if(userStatus.isEsUsuario()){
                SingletonUser user = SingletonUser.getInstance();
                user.getDataUser().setEsUsuario(userStatus.isEsUsuario());
                user.getDataUser().setEsCliente(userStatus.isEsCliente());
                user.getDataUser().setEsAgente(userStatus.isEsAgente());
                user.getDataUser().setConCuenta(userStatus.isConCuenta());
                user.getDataUser().getUsuario().setIdUsuario(userStatus.getIdUsuario());
                accountManager.onSucces(response.getWebService(),true);
            }else{
                accountManager.onSucces(response.getWebService(),false);
            }

        }else{
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(),data.getMensaje());
        }

    }


    /**
     * Método para procesar respuesta del Login.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processLogin(DataSourceResult response) {

    }

    /**
     * Método para seleccionar la pantalla que se debe mostrar dependiendo de la validación de la tarjeta.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void selectValidationCardStep(DataSourceResult response) {
        ConsultarAsignacionTarjetaResponse  data = (ConsultarAsignacionTarjetaResponse) response.getData();
        DataConsultarAsignacion dataCard = data.getData();
        RequestHeaders.setOperation(String.valueOf(data.getIdOperacion()));//Seteamos el IdOperacion
        if(data.getCodigoRespuesta() == CODE_OK) {
            Card card = Card.getInstance();
            card.setHasClient(dataCard.isConCliente());
            if (!card.isHasClient()) {
                card.setAlias(dataCard.getAlias());
                card.setUserName(dataCard.getNombreUsuario());
                card.setIdAccount(dataCard.getIdCuenta());
                Log.w(TAG, "parseJson Card Account: " + dataCard.getIdCuenta());
                SingletonUser user = SingletonUser.getInstance();
                user.getDataExtraUser().setNeedSetPin(true);//TODO Validar esta bandera
                accountManager.onSucces(response.getWebService(),"Tarjeta Válida");
            } else {
                /*TODO enviar mensaje a vista*/
                accountManager.onError(response.getWebService(),"Esta Tarjeta Ya Ha Sido Asignada, Ingresa Otra Tarjeta o Selecciona la Opción NO TENGO TARJETA");
            }
        }else{
            accountManager.onError(response.getWebService(),data.getMensaje());
        }

    }

    /**
     * Método para procesar la respuesta del servicio respecto al formato de la contraseña.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processValidationPassword(DataSourceResult response) {
        ValidarFormatoContraseniaResponse  data = (ValidarFormatoContraseniaResponse) response.getData();
        if(data.getAccion() == CODE_OK){
            accountManager.onSucces(response.getWebService(),data.getMensaje());
        }else{
            accountManager.onError(response.getWebService(),data.getMensaje());//Retornamos mensaje de error.
        }
    }

    /**
     * Método para procesar la respuesta del servicio en la creación de un nuevo usuario FWS.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processUserCreated(DataSourceResult response) {
        GenericResponse data = (GenericResponse) response.getData();
        if(data.getCodigoRespuesta() == CODE_OK){
            RequestHeaders.setUsername(RegisterUser.getInstance().getEmail());
            activationLogin();
        }else{
            accountManager.onError(CREAR_USUARIO_COMPLETO,data.getMensaje());
        }
    }

    /**
     * Método para procesar la respuesta del servicio en la creación de un nuevo usuario FWS obteniendo un TokenSession.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processUserCreatedLoged(DataSourceResult response) {
        CrearUsuarioFWSInicioSesionResponse data = (CrearUsuarioFWSInicioSesionResponse) response.getData();
        DataUsuarioFWSInicioSesion dataUser = data.getData();
        if(data.getCodigoRespuesta() == CODE_OK){
            RequestHeaders.setUsername(RegisterUser.getInstance().getEmail());
            RequestHeaders.setTokensesion(dataUser.getUsuario().getTokenSesion()); // Guardamos el Token de Sesión
            //Seteamos los datos del usuario en el SingletonUser.
            SingletonUser user = SingletonUser.getInstance();
            user.getDataUser().setRequiereActivacionSMS(dataUser.isRequiereActivacionSMS());
            user.getDataUser().setSemilla(dataUser.getSemilla());
            user.getDataUser().getUsuario().setIdUsuario(dataUser.getUsuario().getIdUsuario());
            createClient();// Creamos Cliente
        }else if(data.getCodigoRespuesta() == ERROR_LOGIN) {


        }else {
            accountManager.onError(CREAR_USUARIO_COMPLETO,data.getMensaje());
        }
    }

    /**
     * Método para procesar el Login realizado después de que se realizo un registro como usuario FWS.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processLoginAfterRegister(DataSourceResult response) {
        IniciarSesionResponse  data = (IniciarSesionResponse) response.getData();
        DataIniciarSesion dataUser = data.getData();
        if(data.getCodigoRespuesta() == CODE_OK){
            RequestHeaders.setTokensesion(dataUser.getUsuario().getTokenSesion()); // Guardamos el Token de Sesión
            //Seteamos los datos del usuario en el SingletonUser.
            SingletonUser user = SingletonUser.getInstance();
            user.setDataUser(dataUser);
            createClient();// Creamos Cliente
        }else{
         accountManager.onError(CREAR_USUARIO_COMPLETO,data.getMensaje());
        }
    }

    /**
     * Método para procesar la respuesta sobre la creación de un nuevo cliente.
     * @param  response {@link DataSourceResult} respuesta del servicio.
     * */
    private void processClientCreated(DataSourceResult response) {
        CrearClienteResponse data = (CrearClienteResponse) response.getData();
        if(data.getCodigoRespuesta() == CODE_OK){
            //accountManager.onSucces(response.getWebService(),"");
            registerComplete(data.getMensaje());
        }else{
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(CREAR_USUARIO_COMPLETO,data.getMensaje());//Retornamos mensaje de error.
        }
    }

    private void registerComplete(String message) {
        accountManager.onSucces(CREAR_USUARIO_COMPLETO,message);
    }

    /**
     * Método para procesar la respuesta con la lista de colonias en el código postal.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processNeighborhoods(DataSourceResult response) {
        ObtenerColoniasPorCPResponse data = (ObtenerColoniasPorCPResponse) response.getData();
        if(data.getCodigoRespuesta() == CODE_OK){
            List<ColoniasResponse> listaColonias = data.getData();
            if(listaColonias != null && listaColonias.size() > 0){
                accountManager.onSucces(response.getWebService(),listaColonias);
            }else{
                accountManager.onError(response.getWebService(),"Verifica tu Código Postal");//Retornamos mensaje de error.
            }
        }else{
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(),data.getMensaje());//Retornamos mensaje de error.
        }
    }

    /**
     * Método para procesar la respuesta uan vez que se realizo la asignación de una cuenta disponible.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processAssigmentAccount(DataSourceResult response) {
        AsignarCuentaDisponibleResponse data = (AsignarCuentaDisponibleResponse) response.getData();
        DataCuentaDisponible cuentaAsiganadaData = data.getData();
        if(data.getCodigoRespuesta() == CODE_OK){
            //Obtenemos cuenta asiganada.
            CuentaResponse cuenta = cuentaAsiganadaData.getCuenta();
            SingletonUser user = SingletonUser.getInstance();
            if(user.getDataUser().getUsuario().getCuentas().isEmpty()){
                user.getDataUser().getUsuario().getCuentas().add(new CuentaResponse());
            }

            RequestHeaders.setIdCuenta(String.format("%s",cuenta.getIdCuenta()));
            // Asignamos la cuenta al usuario TODO el servicio maneja lista en login
            user.getDataUser().getUsuario().getCuentas().get(0).setIdCuenta(cuenta.getIdCuenta());
            Card.getInstance().setIdAccount(cuenta.getIdCuenta());
            accountManager.onSucces(response.getWebService(),data.getMensaje());

        }else{
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(),data.getMensaje());//Retornamos mensaje de error.
        }
    }

    /**
     * Método para procesar la respuesta una vez que se asigno el NIP a la tarjeta.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processNIPAssigned(DataSourceResult response) {
        AsignarNIPResponse data = (AsignarNIPResponse) response.getData();
        if(data.getCodigoRespuesta() == CODE_OK) {
            accountManager.onSucces(response.getWebService(),data.getMensaje());
        }else{
            accountManager.onError(response.getWebService(),data.getMensaje());
        }
    }

    /**
     * Método para procesar la respuesta al recuperar el número para envío de SMS
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processNumberSMS(DataSourceResult response) {
        ObtenerNumeroSMSResponse data = (ObtenerNumeroSMSResponse) response.getData();
        if(data.getCodigoRespuesta() == CODE_OK){
            SingletonUser user = SingletonUser.getInstance();

            String phone = data.getData().getNumeroTelefono();
            String tokenValidation = user.getDataUser().getSemilla() + RequestHeaders.getUsername() + RequestHeaders.getTokendevice();
            String tokenValidationSHA = Utils.bin2hex(Utils.getHash(tokenValidation));
            String message = String.format("%sT%sT%s",
                    user.getDataUser().getUsuario().getIdUsuario(),
                    user.getDataUser().getUsuario().getCuentas().get(0).getIdCuenta(), tokenValidationSHA);

            MessageValidation messageValidation = new MessageValidation(phone,message);
            accountManager.onSucces(response.getWebService(),messageValidation);
        }else{
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(),data.getMensaje());//Retornamos mensaje de error.
        }
    }

    /**
     * Método para procesar la respuesta sobre la verificación de la asociación del teléfono mediante el SMS
     * enviado previamente.
     * @param  response {@link DataSourceResult} respuesta del servicio.
     * */
    private void processVerifyActivation(DataSourceResult response) {
        VerificarActivacionResponse data = (VerificarActivacionResponse) response.getData();
        if(data.getCodigoRespuesta() == CODE_OK) {
            SingletonUser user = SingletonUser.getInstance();
            user.getDataExtraUser().setPhone(data.getData().getNumeroTelefono());
            RequestHeaders.setTokenauth(data.getData().getTokenAutenticacion());
            accountManager.onSucces(response.getWebService(), "");
        }else if(data.getCodigoRespuesta() == DEVICE_ALREADY_ASSIGNED){
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(),new Object[]{DEVICE_ALREADY_ASSIGNED,data.getMensaje()});//Retornamos mensaje de error.
        }else{
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(),data.getMensaje());//Retornamos mensaje de error.
        }
    }
}