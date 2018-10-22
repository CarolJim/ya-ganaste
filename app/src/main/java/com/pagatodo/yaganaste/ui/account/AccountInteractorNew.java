package com.pagatodo.yaganaste.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.dspread.xpos.QPOSService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.MessageValidation;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.SaldoRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioClienteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.RecuperarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarDatosPersonaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarEstatusUsuarioRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarFormatoContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.CardRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.LoginStarbucksRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarCuentaDisponibleRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ConsultaAsignacionTarjetaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ConsultaSaldoCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.LoginAdqResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneDatosCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarAvatarResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarInformacionSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.AdquirienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CambiarContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CardStarbucks;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CrearUsuarioClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaUyUResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataEstatusUsuario;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesionUYU;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataUsuarioCliente;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.IniciarSesionUYUResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerNumeroSMSResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.RecuperarContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarEstatusUsuarioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarFormatoContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.VerificarActivacionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.LoginStarbucksResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.SaldoSBRespons;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarCuentaDisponibleResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarNIPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarAsignacionTarjetaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarSaldoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataConsultarAsignacion;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataCuentaDisponible;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Agentes;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.data.room_db.entities.Paises;
import com.pagatodo.yaganaste.data.room_db.entities.Rewards;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IAccountIteractorNew;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.interfaces.enums.AccountOperation;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.net.ApiAdq;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.ApiStarbucks;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.SplashActivity;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.QrcodeGenerator;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.interfaces.enums.AccountOperation.CREATE_USER;
import static com.pagatodo.yaganaste.interfaces.enums.AccountOperation.LOGIN;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_AVATAR;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_INFO_SESION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_CUENTA_DISPONIBLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_NIP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CHANGE_PASS_6;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_ASIGNACION_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO_ADQ;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO_SB;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTA_SALDO_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_CLIENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ESTATUS_CUENTA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.INICIAR_SESION_SIMPLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.LOGINSTARBUCKS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.LOGIN_ADQ;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_NUMERO_SMS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.RECUPERAR_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_DATOS_PERSONA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_DATOS_PERSONAHOMO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_ESTATUS_USUARIO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_FORMATO_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASOCIATE_PHONE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASSIGN_NEW_CONTRASE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASSIGN_PIN;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_GET_CARD;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.utils.Recursos.ACTUAL_LEVEL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_STATUS;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_SESSION_EXPIRED;
import static com.pagatodo.yaganaste.utils.Recursos.CONNECTION_TYPE;
import static com.pagatodo.yaganaste.utils.Recursos.CUPO_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.DEVICE_ALREADY_ASSIGNED;
import static com.pagatodo.yaganaste.utils.Recursos.EMAIL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_APROV;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_BALANCE_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_BALANCE_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_LOG_IN;
import static com.pagatodo.yaganaste.utils.Recursos.FAVORITE_DRINK;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_CONFIG_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_FIREBASE_ACCOUNT;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_PROVISIONING;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ESTATUS_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.ID_MIEMBRO_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.IS_UYU;
import static com.pagatodo.yaganaste.utils.Recursos.MEMBER_NUMBER_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.MEMBER_SINCE;
import static com.pagatodo.yaganaste.utils.Recursos.MISSING_STARS_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.NEXT_LEVEL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.NUMBER_CARD_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.OLD_NIP;
import static com.pagatodo.yaganaste.utils.Recursos.PASSWORD_CHANGE;
import static com.pagatodo.yaganaste.utils.Recursos.PSW_CPR;
import static com.pagatodo.yaganaste.utils.Recursos.PUBLIC_KEY_RSA;
import static com.pagatodo.yaganaste.utils.Recursos.REWARDS;
import static com.pagatodo.yaganaste.utils.Recursos.SECURITY_TOKEN_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;
import static com.pagatodo.yaganaste.utils.Recursos.STARBUCKS_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.STARBUCKS_CARDS;
import static com.pagatodo.yaganaste.utils.Recursos.STARS_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_GOLD;
import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_FIREBASE;
import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_FIREBASE_AUTH;
import static com.pagatodo.yaganaste.utils.Recursos.UPDATE_DATE;
import static com.pagatodo.yaganaste.utils.Recursos.UPDATE_DATE_BALANCE_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.UPDATE_DATE_BALANCE_CUPO;
import static com.pagatodo.yaganaste.utils.Recursos.URL_BD_ODIN;
import static com.pagatodo.yaganaste.utils.Recursos.URL_BD_ODIN_USERS;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.USER_PROVISIONED;

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
    private String pass;

    public AccountInteractorNew(IAccountManager accountManager) {
        this.accountManager = accountManager;
        prefs = App.getInstance().getPrefs();
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
    public void sendIteractorActualizarAvatar(ActualizarAvatarRequest avatarRequest, WebService webService) {
        try {
            ApiAdtvo.actualizarAvatar(avatarRequest, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            accountManager.onError(ACTUALIZAR_AVATAR, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void login(IniciarSesionRequest request) {
        try {
            new DatabaseManager().deleteFavorites();
            ApiAdtvo.iniciarSesionSimple(request, this);
            SingletonUser.getInstance().setCardStatusId(null);
        } catch (OfflineException e) {
            accountManager.onError(INICIAR_SESION_SIMPLE, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void login(LoginStarbucksRequest request) {
        try {
            ApiStarbucks.loginStarbucks(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(LOGINSTARBUCKS, e.toString());
        }
    }

    @Override
    public void changePassToIteractor(CambiarContraseniaRequest request) {
        try {
            ApiAdtvo.cambiarContrasenia6digits(request, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            accountManager.onError(CHANGE_PASS_6, e.toString());
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
                Utils.cipherRSA(registerUser.getContrasenia(), PUBLIC_KEY_RSA),
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
    public void validatePersonDataHomonimia() {

        RegisterUser registerUser = RegisterUser.getInstance();
        ValidarDatosPersonaRequest request = new ValidarDatosPersonaRequest();

        request.setNombre(registerUser.getNombre());
        request.setPrimerApellido(registerUser.getApellidoPaterno());
        request.setSegundoApellido(registerUser.getApellidoMaterno());
        request.setFechaNacimiento(registerUser.getFechaNacimiento());
        request.setGenero(registerUser.getGenero());
        request.setIdEstadoNacimiento(Integer.valueOf(registerUser.getIdEstadoNacimineto()));
        request.setCURP(registerUser.getCURP());

        try {
            ApiAdtvo.validarDatosPersonaHomonimia(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(VALIDAR_DATOS_PERSONAHOMO, App.getContext().getString(R.string.no_internet_access));
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
    public void getBalanceAdq(Agentes agente) {
        try {
            //ApiAdq.consultaSaldoCupo(this, elementWallet.getAgentes());
            try {
                SaldoRequest saldoRequest = new SaldoRequest();
                DatabaseManager db = new DatabaseManager();
                Operadores operador = db.getOperadoresAdmin(agente);
                saldoRequest.addPetroNum(new SaldoRequest.PetroNum(operador.getPetroNumero()));
                RequestHeaders.setIdCuentaAdq(operador.getIdUsuarioAdquirente());
                App.getInstance().getPrefs().saveDataBool(IS_UYU, agente.isEsComercioUYU());
                ApiAdq.consultaSaldoCupo(saldoRequest, this);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (OfflineException e) {
            accountManager.onError(CONSULTAR_SALDO_ADQ, null);
        }
    }


    @Override
    public void getBalanceStarbucks() {
        try {
            String numCard = App.getInstance().getPrefs().loadData(NUMBER_CARD_STARBUCKS);
            CardRequest cardRequest = new CardRequest(numCard);
            ApiStarbucks.saldoSb(cardRequest, this);
        } catch (OfflineException e) {
            accountManager.onError(CONSULTAR_SALDO_SB, null);
        }
    }

    @Override
    public List<Paises> getPaisesList() {
        try {
            return new DatabaseManager().getPaisesList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

        switch (dataSourceResult.getWebService()) {
            case ACTUALIZAR_AVATAR:
                cargarfotousuario(dataSourceResult);
                break;
            case CHANGE_PASS_6:
                processchangepass6(dataSourceResult);
                break;
            case VALIDAR_ESTATUS_USUARIO:
                processStatusEmail(dataSourceResult);
                break;

            case INICIAR_SESION_SIMPLE:
                processLogin(dataSourceResult);
                break;
            case LOGINSTARBUCKS:
                saveDataUsuStarBucks(dataSourceResult);
                break;
            case CERRAR_SESION:
                RequestHeaders.setTokensesion("");//Reseteamos el token de sesión
                FirebaseAuth.getInstance().signOut();
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
            case CONSULTAR_SALDO_SB:
                validateBalanceStarbucks((SaldoSBRespons) dataSourceResult.getData());
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

    private void cargarfotousuario(DataSourceResult dataSourceResult) {
        ActualizarAvatarResponse response = (ActualizarAvatarResponse) dataSourceResult.getData();
        if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
            accountManager.onSucces(ACTUALIZAR_AVATAR, dataSourceResult);
        } else {
            accountManager.onError(ACTUALIZAR_AVATAR, response.getMensaje());
        }
    }

    private void processchangepass6(DataSourceResult dataSourceResult) {
        CambiarContraseniaResponse response = (CambiarContraseniaResponse) dataSourceResult.getData();
        if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
            accountManager.onSuccesChangePass6(dataSourceResult);
        } else {
            accountManager.onError(CHANGE_PASS_6, response.getMensaje());
        }

    }

    private void validatePersonDataResponse(GenericResponse data) {
        if (data.getCodigoRespuesta() == 0) {
            accountManager.onSuccessDataPerson();
        } else if (data.getCodigoRespuesta() == 352) {
            accountManager.onHomonimiaDataPerson();
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
        if (error != null && error.getWebService() == LOGINSTARBUCKS) {
            accountManager.onError(error.getWebService(), error.getData().toString());
        }
    }

    private void validateBalanceResponse(ConsultarSaldoResponse response) {
        Bundle bundle = new Bundle();
        bundle.putString(CONNECTION_TYPE, Utils.getTypeConnection());
        FirebaseAnalytics.getInstance(App.getContext()).logEvent(EVENT_BALANCE_EMISOR, bundle);
        JSONObject props = null;
        if(!BuildConfig.DEBUG) {
            try {
                props = new JSONObject().put(CONNECTION_TYPE, Utils.getTypeConnection());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            App.mixpanel.track(EVENT_BALANCE_EMISOR, props);
        }
        if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
            prefs.saveData(USER_BALANCE, response.getData().getSaldo());
            prefs.saveData(UPDATE_DATE, DateUtil.getTodayCompleteDateFormat());
            accountManager.onSuccesBalance();
        } else {
            accountManager.onError(CONSULTAR_SALDO, null);
        }
    }

    private void validateStatusRespond(EstatusCuentaResponse dataSourceResult) {
        if (dataSourceResult.getCodigoRespuesta() == Recursos.CODE_OK) {
            App.getInstance().getPrefs().saveData(CARD_STATUS, dataSourceResult.getData().getStatusId());
            accountManager.onSuccesStateCuenta();
        } else {
            accountManager.onError(ESTATUS_CUENTA, null);
        }
    }


    private void validateBalanceAdqResponse(ConsultaSaldoCupoResponse response) {
        Bundle bundle = new Bundle();
        bundle.putString(CONNECTION_TYPE, Utils.getTypeConnection());
        FirebaseAnalytics.getInstance(App.getContext()).logEvent(EVENT_BALANCE_ADQ, bundle);
        JSONObject props = null;
        if(!BuildConfig.DEBUG) {
            try {
                props = new JSONObject().put(CONNECTION_TYPE, Utils.getTypeConnection());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            App.mixpanel.track(EVENT_BALANCE_ADQ, props);
        }
        if (response.getResult().getId().equals(Recursos.ADQ_CODE_OK)) {
            prefs.saveData(ADQUIRENTE_BALANCE, response.getSaldo());
            prefs.saveData(UPDATE_DATE_BALANCE_ADQ, DateUtil.getTodayCompleteDateFormat());
            accountManager.onSuccesBalanceAdq();
        } else {
            accountManager.onError(CONSULTAR_SALDO_ADQ, null);
        }
    }

    private void validateBalanceStarbucks(SaldoSBRespons response) {
        if (response.getRespuesta().getCodigoRespuesta() == Recursos.CODE_OK) {
            prefs.saveData(STARBUCKS_BALANCE, response.getSaldo());
            accountManager.onSuccessBalanceStarbucks();
        } else {
            accountManager.onError(CONSULTAR_SALDO_SB, null);
        }
    }

    private void validateDataCupo(ObtieneDatosCupoResponse response) {
        if (response.getResult().getId().equals(Recursos.CODE_ADQ_OK)) {
            prefs.saveData(CUPO_BALANCE, response.getSaldoDisponible());
            prefs.saveData(UPDATE_DATE_BALANCE_CUPO, DateUtil.getTodayCompleteDateFormat());
            //accountManager.onSuccesBalanceCupo();
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

            UsuarioResponse dataUser = SingletonUser.getInstance().getDataUser().getUsuario();
            dataUser.setTokenSesionAdquirente(data.getToken());
            dataUser.setIdUsuarioAdquirente(data.getId_user());
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
        IniciarSesionUYUResponse data = (IniciarSesionUYUResponse) response.getData();
        /*new Gson().fromJson("{\"Accion\":0,\"CodigoRespuesta\":0,\"IdOperacion\":0,\"Mensaje\":\"\",\"Data\":{\"Adquirente\":{\"Agentes\":[{\"EsComercioUYU\":false,\"Folio\":\"YG-5977\",\"IdComercio\":4565,\"IdEstatus\":12,\"NombreNegocio\":\"Mi Tienda\",\"NumeroAgente\":\"54669\",\"Operadores\":[{\"EstatusUsuario\":\"Activo\",\"IdEstatusUsuario\":1,\"IdOperador\":12445,\"IdUsuario\":52577,\"IdUsuarioAdquirente\":\"28018\",\"IsAdmin\":true,\"NombreUsuario\":\"irmaza@live.com.mx\",\"PetroNumero\":\"46695001\"}]}]},\"Cliente\":{\"ConCuenta\":true,\"Nombre\":\"Armando Kilian\",\"PrimerApellido\":\"Sandoval\",\"SegundoApellido\":\"Silva\"},\"Control\":{\"EsCliente\":true,\"EsUsuario\":true,\"RequiereActivacionSMS\":false},\"Emisor\":{\"Cuentas\":[{\"CLABE\":\"148 180 1000 0044 8111\",\"Cuenta\":\"100 0004 4811\",\"IdCuenta\":112571,\"IdUsuario\":0,\"NumeroCliente\":null,\"Tarjetas\":[{\"AsignoNip\":true,\"Numero\":\"5389 8403 0011 7941\"}],\"Telefono\":\"55 7908 1837\"}]},\"Usuario\":{\"EsExtranjero\":false,\"IdEstatus\":12,\"IdUsuario\":942,\"IdUsuarioAdquirente\":\"\",\"ImagenAvatarURL\":\"https:\\/\\/www.yaganaste.com\\/Avatar\\/e7c2941bf7f751f5f77efefc3d074b1bd647a1225baf748ff4806e85a2d4be26_{0}.jpg\",\"NombreUsuario\":\"ipodssak@live.com.mx\",\"PasswordAsignado\":true,\"Roles\":[{\"IdRol\":128,\"NombreRol\":\"Operador P. Física\"}],\"Semilla\":\"\",\"TokenSesion\":\"14C00D72096885C791DC5E36FCE488EB0D787AD9FCC20FC2556A626425CA44EE807D6F48E96536AFC302784731375CDF\",\"TokenSesionAdquirente\":\"\"}}}", IniciarSesionUYUResponse.class);*/
        DataIniciarSesionUYU dataUser = data.getData();
        String stepByUserStatus = "";
        if (data.getCodigoRespuesta() == CODE_OK) {
            RequestHeaders.setUsername(dataUser.getUsuario().getNombreUsuario());
            if (dataUser.getAdquirente().getAgentes() != null && dataUser.getAdquirente().getAgentes().size() > 0) {
                new DatabaseManager().insertAgentes(dataUser.getAdquirente().getAgentes());
            }
            //Seteamos los datos del usuario en el SingletonUser.
            SingletonUser user = SingletonUser.getInstance();
            if (dataUser.getControl().getEsUsuario()) {
                user.setDataUser(dataUser);// Si Usuario
                App.getInstance().getPrefs().saveDataInt(ID_ESTATUS_EMISOR, dataUser.getUsuario().getIdEstatusEmisor());
                String pswcph = pass + "-" + Utils.getSHA256(pass) + "-" + System.currentTimeMillis();
                App.getInstance().getPrefs().saveData(PSW_CPR, Utils.cipherAES(pswcph, true));
                RequestHeaders.setTokensesion(dataUser.getUsuario().getTokenSesion());//Guardamos Token de sesion
                RequestHeaders.setTokenAdq(dataUser.getUsuario().getTokenSesion());
                RequestHeaders.setIdCuentaAdq(dataUser.getUsuario().getIdUsuarioAdquirente());
                AdquirienteResponse adquiriente = user.getDataUser().getAdquirente();
                if (adquiriente.getAgentes() != null && adquiriente.getAgentes().size() > 0 &&
                        !App.getInstance().getPrefs().loadDataBoolean(HAS_CONFIG_DONGLE, false)) {
                    App.getInstance().getPrefs().saveDataBool(HAS_CONFIG_DONGLE, true);
                    App.getInstance().getPrefs().saveDataInt(MODE_CONNECTION_DONGLE, QPOSService.CommunicationMode.BLUETOOTH.ordinal());
                }
                if (dataUser.getCliente().getConCuenta()) {// Si Cuenta
                    RequestHeaders.setIdCuenta(String.format("%s", data.getData().getEmisor().getCuentas().get(0).getIdCuenta()));
                    if (prefs.loadDataBoolean(PASSWORD_CHANGE, false)) {
                        if (dataUser.getEmisor().getCuentas().get(0).getTarjetas().get(0).getAsignoNip()) { // NO necesita NIP
                            checkAfterLogin();
                            return;
                        } else {//Requiere setear el NIP
                            stepByUserStatus = EVENT_GO_ASSIGN_PIN;
                        }
                    } else {
                        if (!dataUser.getControl().getRequiereActivacionSMS()) {
                            stepByUserStatus = EVENT_GO_ASSIGN_NEW_CONTRASE;
                        } else {
                            if (dataUser.getEmisor().getCuentas().get(0).getTarjetas().get(0).getAsignoNip()) { // NO necesita NIP
                                checkAfterLogin();
                                return;
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

    /*private void processLogin(DataSourceResult response) {
        IniciarSesionUYUResponse data = (IniciarSesionUYUResponse) response.getData();
        DataIniciarSesionUYU dataUserUyu = data.getData();
        SingletonUser.getInstance().setDataUserUyu(dataUserUyu);

        DataIniciarSesion dataUser = new DataIniciarSesion();
        dataUser.setEsUsuario(dataUserUyu.getControl().getEsUsuario());
        dataUser.setConCuenta(dataUserUyu.getCliente().getConCuenta());
        dataUser.setIdEstatusEmisor(dataUserUyu.getUsuario().getIdEstatusEmisor());
        dataUser.setRequiereActivacionSMS(dataUserUyu.getControl().getRequiereActivacionSMS());
        dataUser.setSemilla(dataUserUyu.getUsuario().getSemilla());

        UsuarioClienteResponse usuario = new UsuarioClienteResponse();
        usuario.setIdUsuario(dataUserUyu.getUsuario().getIdUsuario());
        usuario.setIdUsuarioAdquirente(dataUserUyu.getUsuario().getIdUsuarioAdquirente());
        usuario.setNombreUsuario("");
        //usuario.setNombreUsuario("qa.pt.apple4@gmail.com");
        usuario.setNombre(dataUserUyu.getCliente().getNombre());
        usuario.setPrimerApellido(dataUserUyu.getCliente().getPrimerApellido());
        usuario.setSegundoApellido(dataUserUyu.getCliente().getSegundoApellido());
        usuario.setImagenAvatarURL(dataUserUyu.getUsuario().getImagenAvatarURL());
        usuario.setTokenSesion(dataUserUyu.getUsuario().getTokenSesion());
        usuario.setTokenSesionAdquirente(dataUserUyu.getUsuario().getTokenSesionAdquirente());
        usuario.setPasswordAsignado(dataUserUyu.getUsuario().getPasswordAsignado());
        usuario.setExtranjero(dataUserUyu.getUsuario().isEsExtranjero());
        List<CuentaResponse> cuentas = new ArrayList<>();
        CuentaResponse cuentaResponse = new CuentaResponse();
        cuentaResponse.setIdCuenta(dataUserUyu.getEmisor().getCuentas().get(0).getIdCuenta());
        cuentaResponse.setAsignoNip(dataUserUyu.getEmisor().getCuentas().get(0).getTarjetas().get(0).getAsignoNip());
        cuentaResponse.setCuenta(dataUserUyu.getEmisor().getCuentas().get(0).getCuenta());
        cuentaResponse.setCLABE(dataUserUyu.getEmisor().getCuentas().get(0).getCLABE());
        cuentaResponse.setTarjeta(dataUserUyu.getEmisor().getCuentas().get(0).getTarjetas().get(0).getNumero());
        cuentaResponse.setTelefono(dataUserUyu.getEmisor().getCuentas().get(0).getTelefono());
        cuentas.add(cuentaResponse);
        usuario.setCuentas(cuentas);
        dataUser.setUsuario(usuario);
        String stepByUserStatus = "";
        if (data.getCodigoRespuesta() == CODE_OK) {
            //Seteamos los datos del usuario en el SingletonUser.
            SingletonUser user = SingletonUser.getInstance();
            if (dataUser.isEsUsuario()) {
                user.setDataUser(dataUser);// Si Usuario
                App.getInstance().getPrefs().saveDataInt(ID_ESTATUS_EMISOR, dataUser.getIdEstatusEmisor());
                String pswcph = pass + "-" + Utils.getSHA256(pass) + "-" + System.currentTimeMillis();
                App.getInstance().getPrefs().saveData(PSW_CPR, Utils.cipherAES(pswcph, true));
                RequestHeaders.setTokensesion(dataUser.getUsuario().getTokenSesion());//Guardamos Token de sesion
                RequestHeaders.setTokenAdq(dataUser.getUsuario().getTokenSesionAdquirente());
                RequestHeaders.setIdCuentaAdq(dataUser.getUsuario().getIdUsuarioAdquirente());

                if (dataUser.isConCuenta()) {// Si Cuenta
                    RequestHeaders.setIdCuenta(String.format("%s", dataUser.getUsuario().getCuentas().get(0).getIdCuenta()));
                    if (prefs.loadDataBoolean(PASSWORD_CHANGE, false)) {
                        if (dataUser.getUsuario().getCuentas().get(0).isAsignoNip()) { // NO necesita NIP
                            checkAfterLogin();
                            return;
                        } else {//Requiere setear el NIP
                            stepByUserStatus = EVENT_GO_ASSIGN_PIN;
                        }
                    } else {

                        if (!dataUser.isRequiereActivacionSMS()) {

                            stepByUserStatus = EVENT_GO_ASSIGN_NEW_CONTRASE;
                        } else {

                            if (dataUser.getUsuario().getCuentas().get(0).isAsignoNip()) { // NO necesita NIP
                                checkAfterLogin();
                                return;
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
*/
    private void checkAfterLogin() {
        String stepByUserStatus;
        SingletonUser user = SingletonUser.getInstance();
        DataIniciarSesionUYU dataUser = user.getDataUser();
        FirebaseAnalytics.getInstance(App.getContext()).setUserId(dataUser.getUsuario().getNombreUsuario());
        Crashlytics.setUserIdentifier(dataUser.getUsuario().getNombreUsuario());
        Bundle bundle = new Bundle();
        bundle.putString(CONNECTION_TYPE, Utils.getTypeConnection());
        FirebaseAnalytics.getInstance(App.getContext()).logEvent(EVENT_LOG_IN, bundle);
        JSONObject props = null;
        if(!BuildConfig.DEBUG) {
            try {
                props = new JSONObject().put(CONNECTION_TYPE, Utils.getTypeConnection());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            App.mixpanel.track(EVENT_LOG_IN, props);
        }
        if (!dataUser.getControl().getRequiereActivacionSMS()) {// No Requiere Activacion de SMS
            //if(true){// No Requiere Activacion de SMS
            //TODO Aqui se debe de manejar el caso en el que el usuario no haya realizado el aprovisionamiento
            String old = prefs.loadData(OLD_NIP);
            SingletonUser.getInstance().setNeedsReset(!needsProvisioning() && (!old.equals(prefs.loadData(SHA_256_FREJA)) && !old.isEmpty()));
            if (!SingletonUser.getInstance().needsReset()) {
                prefs.saveData(OLD_NIP, prefs.loadData(SHA_256_FREJA));
            }
            stepByUserStatus = EVENT_GO_MAINTAB; // Vamos al TabActiviy
            if (!prefs.loadDataBoolean(HAS_FIREBASE_ACCOUNT, false)) {
                registerUserInFirebase(dataUser, stepByUserStatus);
            } else {
                logInFirebase(dataUser, stepByUserStatus);
            }
        } else { // Requiere Activacion SMS, es obligatorio hacer aprovisionamiento
            stepByUserStatus = EVENT_GO_ASOCIATE_PHONE;
            accountManager.goToNextStepAccount(stepByUserStatus, null); // Enviamos al usuario a la pantalla correspondiente.
        }
    }

    private void registerUserInFirebase(DataIniciarSesionUYU data, String stepUser) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(data.getUsuario().getNombreUsuario(), pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                prefs.saveDataBool(HAS_FIREBASE_ACCOUNT, true);
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = auth.getCurrentUser();
                    prefs.saveData(TOKEN_FIREBASE_AUTH, user.getUid());
                    Map<String, String> users = new HashMap<>();
                    users.put("Mbl", data.getEmisor().getCuentas().get(0).getTelefono().replace(" ", ""));
                    users.put("DvcId", FirebaseInstanceId.getInstance().getToken());
                    FirebaseDatabase.getInstance(URL_BD_ODIN_USERS).getReference().child(user.getUid()).setValue(users);
                    accountManager.goToNextStepAccount(stepUser, null);
                } else {
                    logInFirebase(data, stepUser);
                }
            }
        });
    }

    private void logInFirebase(DataIniciarSesionUYU data, String stepUser) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(data.getUsuario().getNombreUsuario(), pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = auth.getCurrentUser();
                prefs.saveData(TOKEN_FIREBASE_AUTH, user.getUid());
                Map<String, String> users = new HashMap<>();
                users.put("Mbl", data.getEmisor().getCuentas().get(0).getTelefono().replace(" ", ""));
                users.put("DvcId", FirebaseInstanceId.getInstance().getToken());
                FirebaseDatabase.getInstance(URL_BD_ODIN_USERS).getReference().child(user.getUid()).setValue(users);
            }
            accountManager.goToNextStepAccount(stepUser, null);
        });
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
                if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                    Log.w(TAG, "parseJson Card Account: " + dataCard.getIdCuenta());
                }
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
            DataIniciarSesionUYU dataIniciarSesion = user.getDataUser();
            dataIniciarSesion.setAdquirente(dataUser.getAdquirente());
            dataIniciarSesion.setCliente(dataUser.getCliente());
            dataIniciarSesion.setControl(dataUser.getControl());
            dataIniciarSesion.setEmisor(dataUser.getEmisor());
            dataIniciarSesion.setUsuario(dataUser.getUsuario());
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
            if (user.getDataUser().getEmisor().getCuentas() == null) {
                user.getDataUser().getEmisor().setCuentas(new ArrayList<CuentaUyUResponse>());
            }
            if (user.getDataUser().getEmisor().getCuentas().isEmpty()) {
                user.getDataUser().getEmisor().getCuentas().add(new CuentaUyUResponse());
            }
            // Asignamos la cuenta al usuario TODO el servicio maneja lista en login
            user.getDataUser().getEmisor().getCuentas().get(0).setIdCuenta(cuenta.getIdCuenta());
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
    private void fotoperfil(DataSourceResult response) {
        AsignarNIPResponse data = (AsignarNIPResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            accountManager.onSucces(response.getWebService(), data.getMensaje());
        } else {
            accountManager.onError(response.getWebService(), data.getMensaje());
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
            String tokenValidation = user.getDataUser().getUsuario().getSemilla() + RequestHeaders.getUsername() + RequestHeaders.getTokendevice();
            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                Log.d("WSC", "TokenValidation: " + tokenValidation);
            }
            String tokenValidationSHA = Utils.bin2hex(Utils.getHash(tokenValidation));
            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                Log.d("WSC", "TokenValidation SHA: " + tokenValidationSHA);
            }
            String message = String.format("%sT%sT%s",
                    user.getDataUser().getUsuario().getIdUsuario(),
                    user.getDataUser().getEmisor().getCuentas().get(0).getIdCuenta(), tokenValidationSHA);
            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                Log.d("WSC", "Token Firebase ID: " + FirebaseInstanceId.getInstance().getToken());
            }
            if (FirebaseInstanceId.getInstance().getToken() != null) {
                prefs.saveData(TOKEN_FIREBASE, FirebaseInstanceId.getInstance().getToken());
                // prefs.saveData(TOKEN_FIREBASE_EXIST, TOKEN_FIREBASE_EXIST);
            }
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
            DataIniciarSesionUYU newSessionData = data.getData();
            if (newSessionData.getAdquirente().getAgentes() != null && newSessionData.getAdquirente().getAgentes().size() > 0) {
                new DatabaseManager().insertAgentes(newSessionData.getAdquirente().getAgentes());
            }
            SingletonUser userInfo = SingletonUser.getInstance();
            newSessionData.getUsuario().setTokenSesionAdquirente(RequestHeaders.getTokenAdq());
            userInfo.setDataUser(newSessionData);
            App.getInstance().getPrefs().saveDataInt(ID_ESTATUS_EMISOR, newSessionData.getUsuario().getIdEstatusEmisor());
            /*TODO 10/05/17 obtener saldo por medio de ws de saldos.*/
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

    private void saveDataUsuStarBucks(DataSourceResult dataSourceResult) {
        LoginStarbucksResponse data = (LoginStarbucksResponse) dataSourceResult.getData();

        if (data.getData().getCodigoRespuesta() != 0) {
            //accountManager.onError(dataSourceResult.getWebService(), data.getData().getMensaje());
        } else if (data.getData().getCodigoRespuesta() == 0) {
            if (!data.getBeneficiosRewards().isEmpty()) {
                List<Rewards> lista = data.getBeneficiosRewards();
                Gson gson = new Gson();
                String json = gson.toJson(lista);
                App.getInstance().getPrefs().saveData(REWARDS, json);
            }
            if (data.getDatosMiembro().getBebidaFavorita() == null) {
                App.getInstance().getPrefs().saveData(FAVORITE_DRINK, "Sin Bebida Favorita");
            } else {
                App.getInstance().getPrefs().saveData(FAVORITE_DRINK, data.getDatosMiembro().getBebidaFavorita());
            }
            App.getInstance().getPrefs().saveData(EMAIL_STARBUCKS, data.getDatosMiembro().getEmail());
            App.getInstance().getPrefs().saveData(MEMBER_SINCE, data.getDatosMiembro().getMiembroDesde());
            App.getInstance().getPrefs().saveDataInt(STATUS_GOLD, data.getDatosMiembro().getStatusGold());
            App.getInstance().getPrefs().saveDataInt(ID_MIEMBRO_STARBUCKS, data.getId_Miembro());
            App.getInstance().getPrefs().saveData(ACTUAL_LEVEL_STARBUCKS, data.getInfoRewardsStarbucks().getNivelActual());
            App.getInstance().getPrefs().saveDataInt(STARS_NUMBER, data.getInfoRewardsStarbucks().getNumEstrellas());
            App.getInstance().getPrefs().saveDataInt(MISSING_STARS_NUMBER, data.getInfoRewardsStarbucks().getNumEstrellasFaltantes());
            App.getInstance().getPrefs().saveData(NEXT_LEVEL_STARBUCKS, data.getInfoRewardsStarbucks().getSiguienteNivel());
            App.getInstance().getPrefs().saveData(MEMBER_NUMBER_STARBUCKS, data.getNumeroMiembro());
            App.getInstance().getPrefs().saveData(SECURITY_TOKEN_STARBUCKS, data.getTokenSeguridad());
            App.getInstance().getPrefs().saveDataBool(HAS_STARBUCKS, true);
            if (!data.getTarjetas().isEmpty()) {
                List<CardStarbucks> listadetarjetas = data.getTarjetas();
                Gson gson = new Gson();
                String json = gson.toJson(listadetarjetas);
                App.getInstance().getPrefs().saveData(STARBUCKS_CARDS, json);
                /**
                 * para obtener el valor
                 * SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                 Gson gson = new Gson(); //Instancia Gson.
                 String json = prefs.getString("myObjeto", "");
                 myObject myobjeto = gson.fromJson(json, myObject.class);
                 *
                 */
            }
            //accountManager.onSucces(LOGINSTARBUCKS, data.getData());
        }
    }
}