package com.pagatodo.yaganaste.ui.account;

import android.os.Handler;
import android.util.Log;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.MessageValidation;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarEstatusUsuarioRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarFormatoContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarCuentaDisponibleRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ConsultaAsignacionTarjetaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataEstatusUsuario;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesion;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.IniciarSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerNumeroSMSResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarEstatusUsuarioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarFormatoContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.VerificarActivacionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarCuentaDisponibleResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarAsignacionTarjetaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataConsultarAsignacion;
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

import static com.pagatodo.yaganaste.interfaces.enums.DataSource.WS;
import static com.pagatodo.yaganaste.interfaces.enums.TypeLogin.LOGIN_AFTER_REGISTER;
import static com.pagatodo.yaganaste.interfaces.enums.TypeLogin.LOGIN_NORMAL;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_COMPLETO;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_GET_CARD;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_GET_PAYMENTS;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_LOGIN;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAIN_TAB_ACTIVITY;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_PIN_CONFIRMATION;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountInteractorNew implements IAccountIteractorNew,IRequestResult {

    private String TAG = AccountInteractorNew.class.getName();
    private IAccountManager accountManager;
    private TypeLogin typeLogin;
    private IniciarSesionRequest loginRequest;
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
    public void login() {

        try{
            ApiAdtvo.iniciarSesion(this.loginRequest,this);
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
    public void checkSessionState(TypeLogin type,IniciarSesionRequest request) {
        this.typeLogin = type;
        loginRequest = request;
        if(!RequestHeaders.getTokensesion().isEmpty()) {
            logOutBeforeLogin = true;
            logout();
        }else{
            logOutBeforeLogin = false;
            login();
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

        new Handler().postDelayed(new Runnable() {
            public void run() {

                DataSourceResult dataSourceResult = new DataSourceResult(CREAR_USUARIO_COMPLETO, WS,
                        new GenericResponse(CODE_OK,"Ejecución exitosa"));
                processUserCreated(dataSourceResult);

            }
        }, 3000);
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
                setUserStatus(dataSourceResult);
                break;

            case INICIAR_SESION:
                if(this.typeLogin == LOGIN_NORMAL)
                    selectAccountStep(dataSourceResult);
                else if(this.typeLogin == LOGIN_AFTER_REGISTER)
                    processLoginAfterRegister(dataSourceResult);
                break;

            case CERRAR_SESION:
                RequestHeaders.setTokensesion("");//Reseteamos el token de sesión
                if(logOutBeforeLogin){
                    logOutBeforeLogin = false;
                    login();
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

            case OBTENER_COLONIAS_CP:
                processNeighborhoods(dataSourceResult);
                break;

            case ASIGNAR_CUENTA_DISPONIBLE:
                processAssigmentAccount(dataSourceResult);
                break;

            case OBTENER_NUMERO_SMS:
                processNumberSMS(dataSourceResult);
                break;

            case VERIFICAR_ACTIVACION:
                processNumberSMS(dataSourceResult);
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
    private void setUserStatus(DataSourceResult response){
        ValidarEstatusUsuarioResponse  data = (ValidarEstatusUsuarioResponse) response.getData();
        DataEstatusUsuario userStatus = data.getData();
        String eventTypeUser = "";
        if(data.getCodigoRespuesta() == CODE_OK){
            RequestHeaders.setOperation(String.valueOf(data.getIdOperacion()));//TODO validar razon de esta asignación
            //Seteamos los datos del usuario en el SingletonUser.
            SingletonUser user = SingletonUser.getInstance();
            user.setDataUser(new DataIniciarSesion(userStatus.isEsUsuario(),userStatus.isEsCliente(),
                    userStatus.isEsAgente(),userStatus.isConCuenta(),new UsuarioClienteResponse(userStatus.getIdUsuario())));

            if(!userStatus.isEsUsuario() && !userStatus.isEsCliente()){
                eventTypeUser = EVENT_GO_GET_CARD;
            }else if(userStatus.isEsUsuario()){
                eventTypeUser = EVENT_GO_LOGIN;
            }

            accountManager.goToNextStepAccount(eventTypeUser);
        }else{
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
        }

    }

    /**
     * Método para seleccionar la pantalla que se debe mostrar dependiendo los estatus del usuario.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void selectAccountStep(DataSourceResult response) {
        IniciarSesionResponse  data = (IniciarSesionResponse) response.getData();
        DataIniciarSesion dataUser = data.getData();
        String stepByUserStatus = "";
        if(data.getCodigoRespuesta() == CODE_OK){
            //Seteamos los datos del usuario en el SingletonUser.
            SingletonUser user = SingletonUser.getInstance();
            user.setDataUser(dataUser);
            if(dataUser.isEsUsuario()){ // Si Usuario
                RequestHeaders.setTokensesion(dataUser.getUsuario().getTokenSesion());//Guardamos token de sesion
                if(dataUser.isEsCliente()){// Si Cliente
                    if(dataUser.isConCuenta()){//Si Cuenta
                        if(!dataUser.isRequiereActivacionSMS()){// No Requiere Activacion de SMS
                            Log.e(TAG, "OnSucces: tiene cuenta y no requiere sms");
                            /*FlowUser.setGoLogin(true); // TODO Verificar manejo de Session activa
                            SingletonSesion.setInSession(true);
                            SingletonSesion.setIsFlowUser(true);*/
                            if (!dataUser.isAsignoNip()) { // Si necesita NIP
                                stepByUserStatus = EVENT_GO_PIN_CONFIRMATION;
                            } else {// No necesita NIP
                                stepByUserStatus = EVENT_GO_MAIN_TAB_ACTIVITY;
                            }
                        }else{// Si Requiere Activacion de SMS
                            Log.e(TAG, "OnSucces: tiene cuenta y requiere sms");
                            //startActivity(new Intent(LoginActivity.this, AreYouWantGetPaymentsCardActivity.class).putExtra("fromlogin", true));
                            stepByUserStatus = EVENT_GO_GET_PAYMENTS;
                        }
                    }else{// No Cuenta
                        Log.e(TAG, "OnSucces: no tiene cuenta");
                        stepByUserStatus = EVENT_GO_GET_CARD;
                    }
                }else{// No Cliente
                    Log.e(TAG, "OnSucces: es usuario pero no cliente");
                    stepByUserStatus = EVENT_GO_GET_CARD;
                }
            }

            accountManager.goToNextStepAccount(stepByUserStatus);

        }else{
             /*TODO enviar mensaje a vista*/
            accountManager.onError(response.getWebService(),data.getMensaje());
        }
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
                accountManager.onSucces(response.getWebService(),"");
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
            accountManager.onSucces(response.getWebService(),"");
        }else{
            accountManager.onError(response.getWebService(),"Eror al crear usuario");
        }
    }

    /**
     * Método para procesar el Login realizado después de que se realizo un registro como usuario FWS.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processLoginAfterRegister(DataSourceResult response) {
        IniciarSesionResponse  data = (IniciarSesionResponse) response.getData();
        DataIniciarSesion dataUser = data.getData();

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
        if(data.getCodigoRespuesta() == CODE_OK){

        }else{
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(),data.getMensaje());//Retornamos mensaje de error.
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
                    user.getDataExtraUser().getIdCuentaUser(), tokenValidationSHA);

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
        if(data.getCodigoRespuesta() == CODE_OK){
            SingletonUser user = SingletonUser.getInstance();
            user.getDataExtraUser().setPhone(data.getData().getNumeroTelefono());
            RequestHeaders.setTokenauth(data.getData().getTokenAutenticacion());
            accountManager.onSucces(response.getWebService(),"");
        }else{
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(),data.getMensaje());//Retornamos mensaje de error.
        }
    }
}
