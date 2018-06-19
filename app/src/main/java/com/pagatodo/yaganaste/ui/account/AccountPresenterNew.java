package com.pagatodo.yaganaste.ui.account;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.MessageValidation;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.RecuperarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.DispositivoStartBucks;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.LoginStarbucksRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CambiarContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.room_db.entities.Paises;
import com.pagatodo.yaganaste.freja.change.presenters.ChangeNipPresenterImp;
import com.pagatodo.yaganaste.freja.reset.managers.IResetNIPView;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenter;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenterImp;
import com.pagatodo.yaganaste.interfaces.IAccountAddressRegisterView;
import com.pagatodo.yaganaste.interfaces.IAccountCardNIPView;
import com.pagatodo.yaganaste.interfaces.IAccountCardView;
import com.pagatodo.yaganaste.interfaces.IAccountIteractorNew;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAccountPresenterNew;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.IAprovView;
import com.pagatodo.yaganaste.interfaces.IBalanceView;
import com.pagatodo.yaganaste.interfaces.IChangePass6;
import com.pagatodo.yaganaste.interfaces.ILoginView;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.IPursePresenter;
import com.pagatodo.yaganaste.interfaces.IPurseView;
import com.pagatodo.yaganaste.interfaces.IRenapoView;
import com.pagatodo.yaganaste.interfaces.IUserDataRegisterView;
import com.pagatodo.yaganaste.interfaces.IVerificationSMSView;
import com.pagatodo.yaganaste.interfaces.RecoveryPasswordView;
import com.pagatodo.yaganaste.interfaces.View;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui.adquirente.interfases.IDocumentApproved;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.preferuser.MyChangeNip;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IChangeNIPView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyPassValidation;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTIVACION_APROV_SOFTTOKEN;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_AVATAR;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_INFO_SESION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_CUENTA_DISPONIBLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_NEW_NIP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_NIP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CERRAR_SESION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_ASIGNACION_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO_ADQ;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO_SB;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTA_SALDO_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_CLIENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ESTATUS_CUENTA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_NUMERO_SMS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.RECUPERAR_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_DATOS_PERSONA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_ESTATUS_USUARIO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_FORMATO_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION_APROV_SOFTTOKEN;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASOCIATE_PHONE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.utils.Recursos.DEVICE_ALREADY_ASSIGNED;
import static com.pagatodo.yaganaste.utils.Recursos.EMAIL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.GENERO;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_PROVISIONING;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_PUSH;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.PUBLIC_KEY_RSA;
import static com.pagatodo.yaganaste.utils.Recursos.PUBLIC_STARBUCKS_KEY_RSA;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;
import static com.pagatodo.yaganaste.utils.Recursos.USER_PROVISIONED;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountPresenterNew extends AprovPresenter implements IAccountPresenterNew, IAccountManager, IPursePresenter {
    private static final String TAG = AccountPresenterNew.class.getName();
    private IAccountIteractorNew accountIteractor;
    private INavigationView accountView;
    private Preferencias prefs = App.getInstance().getPrefs();
    private Context context;
    private MyChangeNip mensajesucces;
    public static boolean isBackShown;
    public static boolean isBackShowndongle;
    private IPurseView view;
    private ChangeNipPresenterImp changeNipPresenterImp;
    private ResetPinPresenter resetPinPresenter;


    public void setIView(View accountView) {
        super.setIView(accountView);

        this.accountView = (INavigationView) accountView;

        if (accountView instanceof TabsView) {
            setTabsView((TabsView) accountView);
        } else if (accountView instanceof IAprovView) {
            super.setAprovView((IAprovView) accountView);
        }
        if (accountView instanceof IResetNIPView) {
            resetPinPresenter.setResetNIPView((IResetNIPView) accountView);
        }
        if (accountView instanceof IMyCardView) {

        }
    }

    public AccountPresenterNew(Context context) {
        super(context, false);
        this.context = context;
        accountIteractor = new AccountInteractorNew(this);
        this.resetPinPresenter = new ResetPinPresenterImp(false);
    }


    @Override
    public void Photo(int i, CameraManager cameraManager) {

        try {
            cameraManager.Photo(i);
        } catch (Exception e) {
            //Toast.makeText(App.getContext(), "Exception " + e, Toast.LENGTH_SHORT).show();
            // iListaOpcionesView.showExceptionToView(e.toString());
        }

    }

    @Override
    public void picture(int i, CameraManager cameraManager) {
        try {
            cameraManager.Picture(i);
        } catch (Exception e) {
            //Toast.makeText(App.getContext(), "Exception " + e, Toast.LENGTH_SHORT).show();
            // iListaOpcionesView.showExceptionToView(e.toString());
        }


    }

    @Override
    public void validateEmail(String usuario) {
        accountView.showLoader(context.getString(R.string.verificando_email));
        RequestHeaders.setUsername(usuario);
        accountIteractor.validateUserStatus(usuario);
    }

    @Override
    public void goToNextStepAccount(String event, Object data) {
        if (!event.equals(EVENT_GO_MAINTAB)) {
            if (prefs.loadDataBoolean(HAS_STARBUCKS, false)) {
                loginStarbucks();
            }
            accountView.hideLoader();
        }
        accountView.nextScreen(event, data);
    }

    @Override
    public void validatePersonData() {
        accountView.showLoader(context.getString(R.string.msg_renapo));
        accountIteractor.validatePersonData();
    }

    @Override
    public void validatePersonDataHomonimia() {
        accountView.showLoader(context.getString(R.string.msg_renapo));
        accountIteractor.validatePersonDataHomonimia();
    }

    @Override
    public void createUser() {
        accountView.showLoader(context.getString(R.string.msg_register));
        RegisterUser registerUser = RegisterUser.getInstance();
        prefs.saveData(SHA_256_FREJA, Utils.getSHA256(registerUser.getContrasenia()));//Freja
        prefs.saveData(GENERO, registerUser.getGenero());//genero
        accountIteractor.createUser();
    }

    @Override
    public void login(String user, String password) {
        RequestHeaders.setUsername(user);
        RequestHeaders.setTokendevice(Utils.getTokenDevice(App.getInstance().getApplicationContext()));
        accountView.showLoader("Iniciando sesión");
        prefs.saveData(SHA_256_FREJA, Utils.getSHA256(password));//Freja
        IniciarSesionRequest requestLogin = new IniciarSesionRequest(user, Utils.cipherRSA(password, PUBLIC_KEY_RSA));
        // Validamos estatus de la sesion, si se encuentra abierta, la cerramos.
        accountIteractor.checkSessionState(requestLogin, password);
        ///accountIteractor.login(requestLogin);
    }

    @Override
    public void loginStarbucks() {
        LoginStarbucksRequest request = new LoginStarbucksRequest();
        request.setEmail(prefs.loadData(EMAIL_STARBUCKS));
        request.setContrasenia(prefs.loadData(SHA_256_STARBUCKS));
        DispositivoStartBucks datadispositivo = new DispositivoStartBucks();
        datadispositivo.setUdid(Utils.getUdid(App.getContext()));
        datadispositivo.setIdTokenNotificacion("");
        datadispositivo.setLatitud("0.0");
        datadispositivo.setLongitud("0.0");
        request.setDispositivoStartBucks(datadispositivo);
        request.setFuente("Movil");
        accountIteractor.login(request);
    }

    @Override
    public void logout() {
        accountIteractor.logout();
    }


    public void changepasssixdigits(String mPassActual, String mPassNueva) {
        accountView.showLoader(App.getContext().getResources().getString(R.string.user_change_password));
        CambiarContraseniaRequest cambiarContraseniaRequest = new CambiarContraseniaRequest();
        cambiarContraseniaRequest.setContrasenaActual(mPassActual);
        cambiarContraseniaRequest.setContrasenaNueva(mPassNueva);
        accountIteractor.changePassToIteractor(cambiarContraseniaRequest);
    }

    public void logoutSinRespuesta() {
        accountIteractor.logoutSinRespuesta();
    }

    @Override
    public void updateUserInfo() {
        //accountView.showLoader(context.getString(R.string.msg_register));
        if (accountView instanceof IDocumentApproved) {
            accountView.showLoader("Verificando Estado");
        } else {
            accountView.showLoader(context.getString(R.string.verificando_sms_esperanuevo));
        }

        accountIteractor.updateSessionData();
    }

    @Override
    public void checkCardAssigment(String numberCard) {
        accountView.showLoader(context.getString(R.string.tienes_tarjeta_validando_tarjeta_2));
        accountIteractor.checkCard(numberCard);
    }

    @Override
    public void getNeighborhoods(String zipCode) {
        accountView.showLoader(App.getInstance().getString(R.string.obteniendo_colonias));
        accountIteractor.getNeighborhoodByZipCode(zipCode);

    }

    @Override
    public void validatePasswordFormat(String password) {
        accountView.showLoader(App.getInstance().getString(R.string.validando_password));
        accountIteractor.validatePassword(Utils.cipherRSA(password, PUBLIC_KEY_RSA));
    }

    @Override
    public void assignAccount() {
        accountView.showLoader(context.getString(R.string.tienes_tarjeta_asignando));
        accountIteractor.assigmentAccountAvaliable(Card.getInstance().getIdAccount());
    }

    @Override
    public void assignNIP(String nip) {
        accountView.showLoader(context.getString(R.string.tienes_tarjeta_asignando_nipnuevo));
        AsignarNIPRequest request = new AsignarNIPRequest(Utils.cipherRSA(nip, PUBLIC_KEY_RSA));
        accountIteractor.assignmentNIP(request, ASIGNAR_NIP);
    }

    public void changeNIP(String nip, String nipNewConfirm) {
        accountView.showLoader(context.getString(R.string.tienes_tarjeta_asignando_nipnuevo));
        AsignarNIPRequest request = new AsignarNIPRequest(
                Utils.cipherRSA(nip, PUBLIC_KEY_RSA),
                Utils.cipherRSA(nipNewConfirm, PUBLIC_KEY_RSA)
        );
        accountIteractor.assignmentNIP(request, ASIGNAR_NEW_NIP);
    }

    @Override
    public void sendPresenterActualizarAvatar(ActualizarAvatarRequest avatarRequest) {
        accountIteractor.sendIteractorActualizarAvatar(avatarRequest, ACTUALIZAR_AVATAR);
    }

    @Override
    public void gerNumberToSMS() {
        accountView.showLoader(context.getString(R.string.verificando_sms_espera));
        accountIteractor.getSMSNumber();
    }

    @Override
    public void doPullActivationSMS(String message) {
        accountView.showLoader(message);
        prefs.clearPreference(HAS_PROVISIONING);
        prefs.clearPreference(HAS_PUSH);
        prefs.clearPreference(USER_PROVISIONED);
        SingletonUser.getInstance().setNeedsReset(false);
        accountIteractor.verifyActivationSMS();
    }

    @Override
    public void recoveryPassword(String email) {
        accountView.showLoader("");
        RequestHeaders.setUsername(email);
        RecuperarContraseniaRequest request = new RecuperarContraseniaRequest(email);
        accountIteractor.recoveryPassword(request);
    }

    @Override
    public void onSuccessDataPerson() {
        accountView.hideLoader();
        ((IRenapoView) accountView).onValidateUserDataSuccess();
    }

    @Override
    public void onHomonimiaDataPerson() {
        accountView.hideLoader();
        ((IRenapoView) accountView).onHomonimiaError();
    }

    @Override
    public void onError(WebService ws, Object error) {
        accountView.hideLoader();
        if (accountView instanceof IRenapoView) {
            if (ws == VALIDAR_DATOS_PERSONA) {
                accountView.showError(error.toString());
            }
        } else if (accountView instanceof IAccountRegisterView) {
            if (ws == CREAR_USUARIO_CLIENTE) {
                ((IAccountRegisterView) accountView).clientCreateFailed(error.toString());
            } else if (ws == OBTENER_COLONIAS_CP) {
                ((IAccountRegisterView) accountView).zipCodeInvalid(error.toString());
                accountView.showError(error.toString());
            }
        } else if (accountView instanceof IUserDataRegisterView) {
            if (ws == VALIDAR_ESTATUS_USUARIO) {
                accountView.showError(error.toString());
            } else if (ws == VALIDAR_FORMATO_CONTRASENIA) {
                ((IUserDataRegisterView) accountView).validationPasswordFailed(error.toString());
            }
        } else if (accountView instanceof IAccountCardView) {
            accountView.showError(error);
        } else if (accountView instanceof IAccountCardNIPView) {
            if (ws == ASIGNAR_NIP) {
                accountView.showError(error.toString());
            }
        } else if (accountView instanceof IListaOpcionesView) {
            if (ws == ACTUALIZAR_AVATAR) {
                ((IListaOpcionesView) accountView).sendErrorAvatarToView("imagen no cargada");
            }
        } else if (accountView instanceof IChangeNIPView) {
            if (ws == ASIGNAR_NEW_NIP) {
                accountView.showError(error.toString());
            }
        } else if (accountView instanceof IVerificationSMSView) {
            if (ws == VERIFICAR_ACTIVACION) {
                if (error instanceof Object[]) {
                    /*Aqui recibimos una respuesta que contiene el código de respuesta del WS y el mensaje del mismo.*/
                    Object[] responseCode = (Object[]) error;
                    if ((int) responseCode[0] == DEVICE_ALREADY_ASSIGNED) {
                        ((IVerificationSMSView) accountView).devicesAlreadyAssign(responseCode[1].toString());
                    }
                } else {
                    ((IVerificationSMSView) accountView).smsVerificationFailed(error.toString());
                }
            } else if (ws == VERIFICAR_ACTIVACION_APROV_SOFTTOKEN || ws == ACTIVACION_APROV_SOFTTOKEN) {
                super.onError(ws, error);
            } else if (ws == ACTUALIZAR_INFO_SESION) { // Activacion con SMS ha sido verificada.
                ((IVerificationSMSView) accountView).dataUpdated(error.toString());
            } else {
                accountView.showError(error);
            }
        } else if (accountView instanceof RecoveryPasswordView) {
            if (ws == RECUPERAR_CONTRASENIA) {
                ((RecoveryPasswordView) accountView).recoveryPasswordFailed(error.toString());
            }

        } else if ((accountView instanceof IBalanceView)) {
            if (ws == CONSULTAR_SALDO) {
                onSuccesBalance();
            } else if (ws == CONSULTAR_SALDO_ADQ) {
                onSuccesBalanceAdq();
            } else if (ws == CONSULTAR_SALDO_SB) {
                onSuccessBalanceStarbucks();
            } else if (ws == ESTATUS_CUENTA) {
                onSuccesStateCuenta();
            } else {
                accountView.showError(error);
            }

        } else if (accountView instanceof IDocumentApproved) {
            accountView.showError(error);
        } else if (accountView instanceof ILoginView) {
            accountView.showError(error);
        } else if (accountView instanceof IMyPassValidation) {
            if (ws == VALIDAR_FORMATO_CONTRASENIA) {
                ((IMyPassValidation) accountView).validationPasswordFailed(error.toString());
            }
        } else if (accountView instanceof IMyCardView) {
            accountView.showError(error);
        } else if (accountView instanceof IChangePass6) {
            accountView.showError(error);
        } else {
            accountView.showError(error);
        }
    }

    @Override
    public void updateBalance() {
        this.accountView.showLoader(context.getString(R.string.actualizando_saldo));
        accountIteractor.getBalance();
    }

    @Override
    public void getEstatusCuenta(String numberCard) {
        this.accountView.showLoader("");
        EstatusCuentaRequest estatusCuentaRequest = new EstatusCuentaRequest(numberCard);
        accountIteractor.onStatusCuenta(estatusCuentaRequest);
    }

    @Override
    public void updateBalanceAdq(ElementWallet elementWallet) {
        this.accountView.showLoader(context.getString(R.string.actualizando_saldo));
        accountIteractor.getBalanceAdq(elementWallet);
    }

    @Override
    public void updateBalanceStarbucks() {
        this.accountView.showLoader(context.getString(R.string.actualizando_saldo));
        accountIteractor.getBalanceStarbucks();
    }

    @Override
    public void getPaisesList() {
        List<Paises> arrayList = accountIteractor.getPaisesList();
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        ((IRenapoView) accountView).showDialogList(arrayList);
    }

    @Override
    public void hideLoader() {
        accountView.hideLoader();
    }

    @Override
    public void onSucces(WebService ws, Object data) {
        accountView.hideLoader();
        if (accountView instanceof IAccountRegisterView) {
            if (ws == CREAR_USUARIO_CLIENTE) {
                ((IAccountRegisterView) accountView).clientCreatedSuccess(data.toString());
            } else if (ws == OBTENER_COLONIAS_CP) {
                ((IAccountRegisterView) accountView).setNeighborhoodsAvaliables((List<ColoniasResponse>) data);
            }
        } else if (accountView instanceof IUserDataRegisterView) {
            if (ws == VALIDAR_ESTATUS_USUARIO) {
                boolean isUser = (boolean) data;
                if (!isUser) {
                    ((IUserDataRegisterView) accountView).isEmailAvaliable();
                } else {
                    ((IUserDataRegisterView) accountView).isEmailRegistered();
                }
            } else if (ws == VALIDAR_FORMATO_CONTRASENIA) {
                boolean validatePass = ((int) data == 0);
                if (validatePass) {
                    ((IUserDataRegisterView) accountView).validationPasswordSucces();
                } else {
                    ((IUserDataRegisterView) accountView).validationPasswordFailed(context.getString(R.string.password_incorrect));
                }
            }
        } else if (accountView instanceof IAccountAddressRegisterView) { // obtiene el listado de colonias
            if (ws == OBTENER_COLONIAS_CP) {
                ((IAccountAddressRegisterView) accountView).setNeighborhoodsAvaliables((List<ColoniasResponse>) data);
            }
        } else if (accountView instanceof IAccountCardView) {
            if (ws == CONSULTAR_ASIGNACION_TARJETA) {
                ((IAccountCardView) accountView).cardIsValidate(data.toString());
            } else if (ws == ASIGNAR_CUENTA_DISPONIBLE) {
                ((IAccountCardView) accountView).accountAssigned(data.toString());
            }
        } else if (accountView instanceof IAccountCardNIPView) {
            if (ws == ASIGNAR_NIP) {
                accountView.nextScreen(EVENT_GO_ASOCIATE_PHONE, data);
            }
            if (ws == ACTUALIZAR_AVATAR) {
                ((IListaOpcionesView) accountView).sendSuccessAvatarToView("Imagen cargada Correctamente");
                //accountView.showErrorTitular(data.toString());
                // mensajesucces.showErrorTitular(data.toString());

            }
        } else if (accountView instanceof IChangeNIPView) {
            if (ws == ASIGNAR_NEW_NIP) {
                ((IChangeNIPView) accountView).setSuccessChangeNip(data);
                //accountView.showErrorTitular(data.toString());
                // mensajesucces.showErrorTitular(data.toString());

            }
            if (ws == ACTUALIZAR_AVATAR) {
                ((IListaOpcionesView) accountView).sendSuccessAvatarToView("Imagen cargada Correctamente");
                //accountView.showErrorTitular(data.toString());
                // mensajesucces.showErrorTitular(data.toString());

            }
        } else if (accountView instanceof IListaOpcionesView) {
            if (ws == ACTUALIZAR_AVATAR) {
                ((IListaOpcionesView) accountView).sendSuccessAvatarToView("Imagen cargada Correctamente");
                //accountView.showErrorTitular(data.toString());
                // mensajesucces.showErrorTitular(data.toString());

            }
        } else if (accountView instanceof IVerificationSMSView) {
            if (ws == OBTENER_NUMERO_SMS) {
                ((IVerificationSMSView) accountView).messageCreated((MessageValidation) data);
            } else if (ws == VERIFICAR_ACTIVACION) { // Activacion con SMS ha sido verificada.
                ((IVerificationSMSView) accountView).smsVerificationSuccess();
            } else if (ws == ACTUALIZAR_INFO_SESION) { // Activacion con SMS ha sido verificada.
                ((IVerificationSMSView) accountView).dataUpdated(data.toString());
            } else if (ws == VERIFICAR_ACTIVACION_APROV_SOFTTOKEN || ws == ACTIVACION_APROV_SOFTTOKEN) {
                super.onSucces(ws, data);
            }
        } else if (accountView instanceof RecoveryPasswordView) {
            if (ws == RECUPERAR_CONTRASENIA) {
                ((RecoveryPasswordView) accountView).recoveryPasswordSuccess(data.toString());
            }
        } else if (ws == CERRAR_SESION) {
            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                Log.i(TAG, context.getString(R.string.sesion_close));
            }
        } else if (accountView instanceof IDocumentApproved) {
            ((IDocumentApproved) accountView).dataUpdated(data.toString());
        }

        if (accountView instanceof IMyPassValidation) {
            if (ws == VALIDAR_FORMATO_CONTRASENIA) {
                ((IMyPassValidation) accountView).validationPasswordSucces();
            }
        }
    }

    @Override
    public void onSuccesBalance() {
        try {
            ((IBalanceView) this.accountView).updateBalance();
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onSuccesChangePass6(DataSourceResult dataSourceResult) {
        CambiarContraseniaResponse response = (CambiarContraseniaResponse) dataSourceResult.getData();
        ((IChangePass6) this.accountView).sendSuccessPassToView(response.getMensaje());
    }

    @Override
    public void onSuccesStateCuenta() {
        try {
            ((IBalanceView) this.accountView).updateStatus();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccesBalanceAdq() {
        ((IBalanceView) this.accountView).updateBalanceAdq();
    }

    @Override
    public void onSuccessBalanceStarbucks() {
        ((IBalanceView) this.accountView).updateBalanceStarbucks();
    }

    @Override
    public void doProvisioning() {
        super.doProvisioning();
    }

    @Override
    public void flipCardemisor(int container, Fragment fragment) {
        try {
            if (view.isAnimationAble()) {
                view.flipCard(container, fragment, isBackShown);
                view.changeBGVisibility(isBackShown);
                isBackShown = !isBackShown;
            }
        } catch (Exception e) {


        }
    }

    @Override
    public void flipCard(int container, Fragment fragment) {

        try {
            if (view.isAnimationAble()) {
                view.flipCard(container, fragment, isBackShown);
                view.changeBGVisibility(isBackShown);
                // isBackShown = !isBackShown;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void flipCarddongle(int container, Fragment fragment) {
        try {
            if (view.isAnimationAble()) {
                view.flipCarddongle(container, fragment, isBackShowndongle);
                view.changeBGVisibility(isBackShowndongle);
                //    isBackShowndongle = !isBackShowndongle;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadCardCover(int container, Fragment fragment) {
        this.isBackShown = false;
        view.loadCardCover(container, fragment);
    }

    @Override
    public void loadCardCoverdongle(int container, Fragment fragment) {
        this.isBackShowndongle = false;
        view.loadCardCover(container, fragment);

    }

    public boolean isBackShown() {
        return this.isBackShown;
    }

    @Override
    public void setPurseReference(IPurseView view) {
        this.view = view;
    }

    /**
     * Methodo delegado de {@link ResetPinPresenterImp}
     * @param newNip
     */
    /*public void doReseting(String newNip) {
        resetPinPresenter.doReseting(newNip);
    }*/

}
