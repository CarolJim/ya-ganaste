package com.pagatodo.yaganaste.ui.account;

import android.content.Intent;
import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.DatosSaldo;
import com.pagatodo.yaganaste.data.model.MessageValidation;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.LoginAdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioClienteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.RecuperarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarDatosPersonaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarEstatusUsuarioRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarFormatoContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarCuentaDisponibleRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ConsultaAsignacionTarjetaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ConsultaSaldoCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.LoginAdqResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneDatosCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarInformacionSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CambiarContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CrearUsuarioClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataEstatusUsuario;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesion;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataUsuarioCliente;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.IniciarSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerNumeroSMSResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.RecuperarContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarEstatusUsuarioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarFormatoContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.VerificarActivacionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarCuentaDisponibleResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarNIPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarAsignacionTarjetaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarSaldoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataConsultarAsignacion;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataCuentaDisponible;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IAccountIteractorNew;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.enums.AccountOperation;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.net.ApiAdq;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.SplashActivity;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.AccountOperation.CREATE_USER;
import static com.pagatodo.yaganaste.interfaces.enums.AccountOperation.LOGIN;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_INFO_SESION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_CUENTA_DISPONIBLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_NIP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CHANGE_PASS_6;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_ASIGNACION_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO_ADQ;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTA_SALDO_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_CLIENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ESTATUS_CUENTA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.INICIAR_SESION_SIMPLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.LOGIN_ADQ;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_NUMERO_SMS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTIENE_DATOS_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.RECUPERAR_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_DATOS_PERSONA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_ESTATUS_USUARIO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_FORMATO_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASOCIATE_PHONE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASSIGN_NEW_CONTRASE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASSIGN_PIN;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_GET_CARD;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_SESSION_EXPIRED;
import static com.pagatodo.yaganaste.utils.Recursos.CONSULT_FAVORITE;
import static com.pagatodo.yaganaste.utils.Recursos.DEVICE_ALREADY_ASSIGNED;
import static com.pagatodo.yaganaste.utils.Recursos.HUELLA_FAIL;
import static com.pagatodo.yaganaste.utils.Recursos.PASSWORD_CHANGE;
import static com.pagatodo.yaganaste.utils.Recursos.PASSWORD_CHANGE_NOSERVISE;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_PROVISIONING;
import static com.pagatodo.yaganaste.utils.StringConstants.OLD_NIP;
import static com.pagatodo.yaganaste.utils.StringConstants.PSW_CPR;
import static com.pagatodo.yaganaste.utils.StringConstants.UPDATE_DATE;
import static com.pagatodo.yaganaste.utils.StringConstants.UPDATE_DATE_BALANCE_ADQ;
import static com.pagatodo.yaganaste.utils.StringConstants.UPDATE_DATE_BALANCE_CUPO;
import static com.pagatodo.yaganaste.utils.StringConstants.USER_PROVISIONED;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountInteractorNew implements IAccountIteractorNew, IRequestResult {

    private String TAG = AccountInteractorNew.class.getName();
    private IAccountManager accountManager;
    private AccountOperation operationAccount;
    private Request requestAccountOperation;
    private boolean logOutBefore;
    private Preferencias prefs = App.getInstance().getPrefs();
    private CatalogsDbApi api;
    private String pass;

    public AccountInteractorNew(IAccountManager accountManager) {
        this.accountManager = accountManager;
        prefs = App.getInstance().getPrefs();
        this.api = new CatalogsDbApi(App.getContext());
    }

    @Override
    public void validateUserStatus(String usuario) {
        ValidarEstatusUsuarioRequest request = new ValidarEstatusUsuarioRequest(usuario);
        try {
            ApiAdtvo.validarEstatusUsuario(request, this);
        } catch (OfflineException e) {
            //   e.printStackTrace();
            accountManager.onError(VALIDAR_ESTATUS_USUARIO, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void login(IniciarSesionRequest request) {
        try {
            api.deleteFavorites();
            ApiAdtvo.iniciarSesionSimple(request, this);
            SingletonUser.getInstance().setCardStatusId(null);
        } catch (OfflineException e) {
            accountManager.onError(INICIAR_SESION_SIMPLE, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void changePassToIteractor(CambiarContraseniaRequest request) {
        try {
            ApiAdtvo.cambiarContrasenia6digits(request, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            accountManager.onError(CHANGE_PASS_6,e.toString());
        }
    }


    @Override
    public void loginAdq() {
        SingletonUser singletonUser = SingletonUser.getInstance();
        LoginAdqRequest request = new LoginAdqRequest(
                singletonUser.getDataUser().getUsuario().getPetroNumero(),
                singletonUser.getDataUser().getUsuario().getClaveAgente());
        try {
            ApiAdq.loginAdq(request, this);
        } catch (OfflineException e) {
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
    public void logoutSinRespuesta() {
        try {
            ApiAdtvo.cerrarSesion();// Se envia null ya que el Body no aplica.
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
            accountManager.onError(ACTUALIZAR_INFO_SESION, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void checkSessionState(Request request, String password) {
        this.pass = password;
        this.requestAccountOperation = request;
        if (request instanceof IniciarSesionRequest) {
            this.operationAccount = LOGIN;
        } else if (request instanceof CrearUsuarioClienteRequest) {
            this.operationAccount = CREATE_USER;
        }

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
            accountManager.onError(ASIGNAR_CUENTA_DISPONIBLE, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void validatePassword(String password) {
        ValidarFormatoContraseniaRequest request = new ValidarFormatoContraseniaRequest(password);
        try {
            ApiAdtvo.validarFormatoContrasenia(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(VALIDAR_FORMATO_CONTRASENIA, App.getContext().getString(R.string.no_internet_access));
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
                registerUser.getPaisNacimiento().getIdPais(),/*Nacionalidad*/
                registerUser.getIdEstadoNacimineto(),
                registerUser.getEmail(),
                "",/*Telefono*/
                "",/*Telefono Celular*/
                registerUser.getIdColonia(),
                registerUser.getColonia(),
                registerUser.getCodigoPostal(),
                registerUser.getCalle(),
                registerUser.getNumExterior(),
                registerUser.getNumInterior(),
                registerUser.getPaisNacimiento().getId()
        );

        checkSessionState(request, registerUser.getContrasenia());
    }

    @Override
    public void validatePersonData() {

        RegisterUser registerUser = RegisterUser.getInstance();
        ValidarDatosPersonaRequest request = new ValidarDatosPersonaRequest();

        request.setNombre(registerUser.getNombre());
        request.setPrimerApellido(registerUser.getApellidoPaterno());
        request.setSegundoApellido(registerUser.getApellidoMaterno());
        request.setFechaNacimiento(registerUser.getFechaNacimiento());
        request.setGenero(registerUser.getGenero());
        request.setIdEstadoNacimiento(Integer.valueOf(registerUser.getIdEstadoNacimineto()));

        try {
            ApiAdtvo.validarDatosPersona(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(VALIDAR_DATOS_PERSONA, App.getContext().getString(R.string.no_internet_access));
        }

    }

    @Override
    public void createUserClient(CrearUsuarioClienteRequest request) {
         /*Establecemos las cabeceras de la peticion*/
        RequestHeaders.setTokendevice(Utils.getTokenDevice(App.getInstance().getApplicationContext()));
        try {
            ApiAdtvo.crearUsuarioCliente(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(CREAR_USUARIO_CLIENTE, "");
        }
    }

    @Override
    public void assignmentNIP(AsignarNIPRequest request, WebService webService) {
        try {
            ApiTrans.asignarNip(request, this, webService);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(ASIGNAR_NIP, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void getSMSNumber() {
        try {
            ApiAdtvo.obtenerNumeroSMS(this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(OBTENER_NUMERO_SMS, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void verifyActivationSMS() {
        try {
            ApiAdtvo.verificarActivacion(this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(VERIFICAR_ACTIVACION, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void getNeighborhoodByZipCode(String zipCode) {
        ObtenerColoniasPorCPRequest request = new ObtenerColoniasPorCPRequest(zipCode);
        try {
            ApiAdtvo.obtenerColoniasPorCP(request, this);

        } catch (OfflineException e) {
            // e.printStackTrace();
            accountManager.onError(OBTENER_COLONIAS_CP, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void recoveryPassword(RecuperarContraseniaRequest request) {

        try {
            ApiAdtvo.recuperarContrasenia(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(RECUPERAR_CONTRASENIA, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void getBalance() {
        try {
            ApiTrans.consultarSaldo(this);
        } catch (OfflineException e) {
            accountManager.onError(CONSULTAR_SALDO, null);
        }
    }

    @Override
    public void onStatusCuenta(EstatusCuentaRequest request) {
        try {
            ApiTrans.estatusCuenta(request, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            accountManager.onError(ESTATUS_CUENTA, null);
        }
    }

    @Override
    public void getBalanceAdq() {
        try {
            ApiAdq.consultaSaldoCupo(this);
        } catch (OfflineException e) {
            accountManager.onError(CONSULTAR_SALDO_ADQ, null);
        }
    }


    @Override
    public void getBalanceCupo() {
        try {
            ApiAdq.obtieneDatosCupo(this);
        } catch (OfflineException e) {
            accountManager.onError(OBTIENE_DATOS_CUPO, null);
        }
    }

    @Override
    public ArrayList<Countries> getPaisesList() {
        CatalogsDbApi api = new CatalogsDbApi(App.getContext());
        return api.getPaisesList();
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

        switch (dataSourceResult.getWebService()) {
            case CHANGE_PASS_6:
                processchangepass6(dataSourceResult);
                break;
            case VALIDAR_ESTATUS_USUARIO:
                processStatusEmail(dataSourceResult);
                break;

            case INICIAR_SESION_SIMPLE:
                processLogin(dataSourceResult);
                break;

            case CERRAR_SESION:
                RequestHeaders.setTokensesion("");//Reseteamos el token de sesión
                prefs.saveDataBool(CONSULT_FAVORITE, false); //Limpiamos bandera de descarga favoritos
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
                    Intent intent = new Intent(App.getContext(), SplashActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    App.getContext().startActivity(intent);
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

            case ASIGNAR_NEW_NIP:
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

            case CONSULTAR_SALDO:
                validateBalanceResponse((ConsultarSaldoResponse) dataSourceResult.getData());
                break;
            case ESTATUS_CUENTA:
                validateStatusRespond((EstatusCuentaResponse) dataSourceResult.getData());
                break;

            case VALIDAR_DATOS_PERSONA:
                validatePersonDataResponse((GenericResponse) dataSourceResult.getData());
                break;

            case CONSULTAR_SALDO_ADQ:
                validateBalanceAdqResponse((ConsultaSaldoCupoResponse) dataSourceResult.getData());
                break;

            case OBTIENE_DATOS_CUPO:
                validateDataCupo((ObtieneDatosCupoResponse) dataSourceResult.getData());
                break;

            case LOGIN_ADQ:
                processLoginAdq(dataSourceResult);
                break;
            default:
                break;
        }
    }

    private void processchangepass6(DataSourceResult dataSourceResult) {
        CambiarContraseniaResponse response = (CambiarContraseniaResponse) dataSourceResult.getData();
        if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
            //Log.d("PreferUserIteractor", "CambiarContrasenia Sucess " + response.getMensaje());
            accountManager.onSuccesChangePass6(dataSourceResult);
        } else {
            //Log.d("PreferUserIteractor", "CambiarContrasenia Sucess with Error " + response.getMensaje());
            accountManager.onError(CHANGE_PASS_6,response.getMensaje());
        }

    }

    private void validatePersonDataResponse(GenericResponse data) {
        if (data.getCodigoRespuesta() == 0) {
            accountManager.onSuccessDataPerson();
        } else {
            accountManager.onError(VALIDAR_DATOS_PERSONA, data.getMensaje());
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {

        if (error != null && error.getWebService() == ASIGNAR_NIP) {
            checkAfterLogin();
        }
        if (error != null && error.getWebService() == INICIAR_SESION_SIMPLE) {
            accountManager.onError(error.getWebService(), error.getData().toString());
        }
        if (error != null && error.getWebService() == LOGIN_ADQ) {
            checkAfterLogin();
        } else {
            if (error.getWebService() == INICIAR_SESION_SIMPLE && RequestHeaders.getTokenauth().isEmpty()) {
                RequestHeaders.setUsername("");
            }
            accountManager.onError(error.getWebService(), error.getData().toString());
        }
    }

    private void validateBalanceResponse(ConsultarSaldoResponse response) {
        if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
            prefs.saveData(StringConstants.USER_BALANCE, response.getData().getSaldo());
            prefs.saveData(UPDATE_DATE, DateUtil.getTodayCompleteDateFormat());
            accountManager.onSuccesBalance();
        } else {
            accountManager.onError(CONSULTAR_SALDO, null);
        }
    }

    private void validateStatusRespond(EstatusCuentaResponse dataSourceResult) {


        if (dataSourceResult.getCodigoRespuesta() == Recursos.CODE_OK) {
            //Log.d("PreferUserIteractor", "EstatusCuentaResponse Sucess " + response.getMensaje());
            //tarjetaUserPresenter.successGenericToPresenter(dataSourceResult);
            App.getInstance().setStatusId(dataSourceResult.getData().getStatusId());
            accountManager.onSuccesStateCuenta();

        } else {
            //Log.d("PreferUserIteractor", "EstatusCuentaResponse Sucess with Error " + response.getMensaje());
            //tarjetaUserPresenter.errorGenericToPresenter(dataSourceResult);
            accountManager.onError(ESTATUS_CUENTA, null);
        }

    }


    private void validateBalanceAdqResponse(ConsultaSaldoCupoResponse response) {
        if (response.getResult().getId().equals(Recursos.ADQ_CODE_OK)) {
            prefs.saveData(StringConstants.ADQUIRENTE_BALANCE, response.getSaldo());
            prefs.saveData(UPDATE_DATE_BALANCE_ADQ, DateUtil.getTodayCompleteDateFormat());
            accountManager.onSuccesBalanceAdq();
        } else {
            accountManager.onError(CONSULTAR_SALDO_ADQ, null);
        }
    }

    private void validateDataCupo(ObtieneDatosCupoResponse response) {
        if (response.getResult().getId().equals(Recursos.CODE_ADQ_OK)) {
            prefs.saveData(StringConstants.CUPO_BALANCE, response.getSaldoDisponible());
            prefs.saveData(UPDATE_DATE_BALANCE_CUPO, DateUtil.getTodayCompleteDateFormat());
            accountManager.onSuccesBalanceCupo();
        } else {
            accountManager.onError(CONSULTA_SALDO_CUPO, null);
        }
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
    private void processLoginAdq(DataSourceResult response) {
        LoginAdqResponse data = (LoginAdqResponse) response.getData();

        if (data.getToken() != null && !data.getToken().isEmpty()) { // Token devuelto
            RequestHeaders.setTokenAdq(data.getToken());
            RequestHeaders.setIdCuentaAdq(data.getId_user());

            UsuarioClienteResponse dataUser = SingletonUser.getInstance().getDataUser().getUsuario();
            dataUser.setTokenSesionAdquirente(data.getToken());
            dataUser.setIdUsuarioAdquirente(data.getId_user());
            dataUser.setNumeroAgente(data.getAgente());
            checkAfterLogin();
        } else {
            String error = App.getInstance().getString(R.string.error_respuesta);
            if (data.getError() != null) {
                error = data.getError().getMessage();
            }
            accountManager.onError(response.getWebService(), error);
        }
    }

    private void processLogin(DataSourceResult response) {
        IniciarSesionResponse data = (IniciarSesionResponse) response.getData();
        DataIniciarSesion dataUser = data.getData();
        String stepByUserStatus = "";
        if (data.getCodigoRespuesta() == CODE_OK) {
            //Seteamos los datos del usuario en el SingletonUser.
            SingletonUser user = SingletonUser.getInstance();
            if (dataUser.isEsUsuario()) {
                user.setDataUser(dataUser);// Si Usuario
                user.getDataUser().setEsAgente(dataUser.isEsAgente());
                String pswcph = pass + "-" + Utils.getSHA256(pass) + "-" + System.currentTimeMillis();
                App.getInstance().getPrefs().saveData(PSW_CPR, Utils.cipherAES(pswcph, true));
                RequestHeaders.setTokensesion(dataUser.getUsuario().getTokenSesion());//Guardamos Token de sesion
                RequestHeaders.setTokenAdq(dataUser.getUsuario().getTokenSesionAdquirente());
                RequestHeaders.setIdCuentaAdq(dataUser.getUsuario().getIdUsuarioAdquirente());
                if (dataUser.isConCuenta()) {// Si Cuenta
                    RequestHeaders.setIdCuenta(String.format("%s", data.getData().getUsuario().getCuentas().get(0).getIdCuenta()));
                        if (prefs.loadDataBoolean(PASSWORD_CHANGE, false)) {
                            if (dataUser.getUsuario().getCuentas().get(0).isAsignoNip()) { // NO necesita NIP
                                //if (!dataUser.getUsuario().getClaveAgente().isEmpty() && !dataUser.getUsuario().getPetroNumero().isEmpty()) {
                        /*if (!dataUser.getUsuario().getClaveAgente().isEmpty() && !dataUser.getUsuario().getPetroNumero().isEmpty()){
                            loginAdq();
                            return;
                        } else {*/
                                checkAfterLogin();
                                return;
                                //}
                            } else {//Requiere setear el NIP
                                stepByUserStatus = EVENT_GO_ASSIGN_PIN;
                            }
                        } else {

                            if (!dataUser.isRequiereActivacionSMS()) {

                                stepByUserStatus = EVENT_GO_ASSIGN_NEW_CONTRASE;
                            }else {

                                if (dataUser.getUsuario().getCuentas().get(0).isAsignoNip()) { // NO necesita NIP
                                    //if (!dataUser.getUsuario().getClaveAgente().isEmpty() && !dataUser.getUsuario().getPetroNumero().isEmpty()) {
                        /*if (!dataUser.getUsuario().getClaveAgente().isEmpty() && !dataUser.getUsuario().getPetroNumero().isEmpty()){
                            loginAdq();
                            return;
                        } else {*/
                                    checkAfterLogin();
                                    return;
                                    //}
                                } else {//Requiere setear el NIP
                                    stepByUserStatus = EVENT_GO_ASSIGN_PIN;
                                }

                            }
                        }

                } else { // No tiene cuenta asignada.

                        stepByUserStatus = EVENT_GO_GET_CARD; // Mostramos pantalla para asignar cuenta.

                }

                accountManager.goToNextStepAccount(stepByUserStatus, null); // Enviamos al usuario a la pantalla correspondiente.
            } else { // No es usuario
                if (RequestHeaders.getTokenauth().isEmpty()) {
                    RequestHeaders.setUsername("");
                }
                accountManager.onError(response.getWebService(), App.getContext().getString(R.string.usuario_no_existe));
            }
        } else {
            if (RequestHeaders.getTokenauth().isEmpty()) {
                RequestHeaders.setUsername("");
            }
            accountManager.onError(response.getWebService(), data.getMensaje());
        }
    }

    private void checkAfterLogin() {
        String stepByUserStatus;
        SingletonUser user = SingletonUser.getInstance();
        DataIniciarSesion dataUser = user.getDataUser();
        if (!dataUser.isRequiereActivacionSMS()) {// No Requiere Activacion de SMS
            //if(true){// No Requiere Activacion de SMS
                            /*TODO Aqui se debe de manejar el caso en el que el usuario no haya realizado el aprovisionamiento*/


            String old = prefs.loadData(OLD_NIP);
            SingletonUser.getInstance().setNeedsReset(!needsProvisioning() && (!old.equals(prefs.loadData(SHA_256_FREJA)) && !old.isEmpty()));
            if (!SingletonUser.getInstance().needsReset()) {
                prefs.saveData(OLD_NIP, prefs.loadData(SHA_256_FREJA));
            }

            user.setDatosSaldo(new DatosSaldo(String.format("%s", dataUser.getUsuario().getCuentas().get(0).getSaldo())));
            stepByUserStatus = EVENT_GO_MAINTAB; // Vamos al TabActiviy
        } else { // Requiere Activacion SMS, es obligatorio hacer aprovisionamiento
            stepByUserStatus = EVENT_GO_ASOCIATE_PHONE;
        }
        accountManager.goToNextStepAccount(stepByUserStatus, null); // Enviamos al usuario a la pantalla correspondiente.
    }

    public boolean needsProvisioning() {
        return (!prefs.containsData(HAS_PROVISIONING) || !prefs.loadData(USER_PROVISIONED).equals(RequestHeaders.getUsername()));
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
                accountManager.onSucces(response.getWebService(), App.getContext().getString(R.string.emisor_validate_card));
            } else {
                /*TODO enviar mensaje a vista*/
                accountManager.onError(response.getWebService(), App.getContext().getString(R.string.emisor_validate_card_fail));
            }
        } else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            accountManager.sessionExpiredToPresenter(response);
        } else {
            accountManager.onError(response.getWebService(), App.getContext().getString(R.string.emisor_validate_card_fail_2));
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
            accountManager.onSucces(response.getWebService(), data.getCodigoRespuesta());
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
            //RequestHeaders.setUsername(RegisterUser.getInstance().getEmail());
            RequestHeaders.setTokensesion(dataUser.getUsuario().getTokenSesion()); // Guardamos el Token de Sesión
            //Seteamos los datos del usuario en el SingletonUser.
            SingletonUser user = SingletonUser.getInstance();
            DataIniciarSesion dataIniciarSesion = user.getDataUser();
            dataIniciarSesion.setRequiereActivacionSMS(dataUser.isRequiereActivacionSMS());
            dataIniciarSesion.setSemilla(dataUser.getSemilla());
            dataIniciarSesion.getUsuario().setIdUsuario(dataUser.getUsuario().getIdUsuario());

            dataIniciarSesion.getUsuario().setNombreUsuario(dataUser.getUsuario().getNombreUsuario());
            dataIniciarSesion.getUsuario().setNombre(dataUser.getUsuario().getNombreUsuario());
            dataIniciarSesion.getUsuario().setPrimerApellido(dataUser.getUsuario().getPrimerApellido());
            dataIniciarSesion.getUsuario().setSegundoApellido(dataUser.getUsuario().getSegundoApellido());

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
                accountManager.onError(response.getWebService(), App.getContext().getString(R.string.emisor_validate_postalcode));//Retornamos mensaje de error.
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

        } else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            accountManager.sessionExpiredToPresenter(response);
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
        } else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            accountManager.sessionExpiredToPresenter(response);
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
            Log.d("WSC", "TokenValidation SHA: " + tokenValidationSHA);
            String message = String.format("%sT%sT%s",
                    user.getDataUser().getUsuario().getIdUsuario(),
                    user.getDataUser().getUsuario().getCuentas().get(0).getIdCuenta(), tokenValidationSHA);

            MessageValidation messageValidation = new MessageValidation(phone, message);
            accountManager.onSucces(response.getWebService(), messageValidation);
        } else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            accountManager.sessionExpiredToPresenter(response);
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
            newSessionData.getUsuario().setTokenSesionAdquirente(RequestHeaders.getTokenAdq());
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