package com.pagatodo.yaganaste.ui.account;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.MessageValidation;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.RecuperarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
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
import com.pagatodo.yaganaste.ui.account.login.QuickBalanceFragment;
import com.pagatodo.yaganaste.ui.adquirente.interfases.IDocumentApproved;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.preferuser.MyChangeNip;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IChangeNIPView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyPassValidation;
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.R.id.view;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTIVACION_APROV_SOFTTOKEN;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_INFO_SESION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_CUENTA_DISPONIBLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_NEW_NIP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_NIP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CERRAR_SESION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_ASIGNACION_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO_ADQ;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTA_SALDO_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_CLIENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ESTATUS_CUENTA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.INICIAR_SESION_SIMPLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_ESTATUS_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_NUMERO_SMS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.RECUPERAR_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_DATOS_PERSONA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_ESTATUS_USUARIO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_FORMATO_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION_APROV_SOFTTOKEN;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASOCIATE_PHONE;
import static com.pagatodo.yaganaste.ui.preferuser.MyChangeNip.EVENT_GO_CHANGE_NIP_SUCCESS;
import static com.pagatodo.yaganaste.utils.Recursos.DEVICE_ALREADY_ASSIGNED;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_PROVISIONING;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_PUSH;
import static com.pagatodo.yaganaste.utils.StringConstants.OLD_NIP;
import static com.pagatodo.yaganaste.utils.StringConstants.USER_PROVISIONED;

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
    private ResetPinPresenter resetPinPresenter;
    private IPurseView view;

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
    public void validateEmail(String usuario) {
        accountView.showLoader(context.getString(R.string.verificando_email));
        accountIteractor.validateUserStatus(usuario);
    }

    @Override
    public void goToNextStepAccount(String event, Object data) {
        accountView.hideLoader();
        accountView.nextScreen(event, data);
    }

    @Override
    public void validatePersonData() {
        accountView.showLoader(context.getString(R.string.msg_renapo));
        accountIteractor.validatePersonData();
    }

    @Override
    public void createUser() {
        accountView.showLoader(context.getString(R.string.msg_register));
        RegisterUser registerUser = RegisterUser.getInstance();
        prefs.saveData(SHA_256_FREJA, Utils.getSHA256(registerUser.getContrasenia()));//Freja
        accountIteractor.createUser();
    }

    @Override
    public void login(String user, String password) {
        RequestHeaders.setUsername(user);
        RequestHeaders.setTokendevice(Utils.getTokenDevice(App.getInstance().getApplicationContext()));
        accountView.showLoader("");
        prefs.saveData(SHA_256_FREJA, Utils.getSHA256(password));//Freja
        IniciarSesionRequest requestLogin = new IniciarSesionRequest(user, Utils.cipherRSA(password), "");//TODO Validar si se envia el telefono vacío-
        // Validamos estatus de la sesion, si se encuentra abierta, la cerramos.
        accountIteractor.checkSessionState(requestLogin);
        ///accountIteractor.login(requestLogin);
    }

    @Override
    public void logout() {
        accountIteractor.logout();
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
        accountIteractor.validatePassword(Utils.cipherRSA(password));
    }

    @Override
    public void assignAccount() {
        accountView.showLoader(context.getString(R.string.tienes_tarjeta_asignando));
        accountIteractor.assigmentAccountAvaliable(Card.getInstance().getIdAccount());
    }

    @Override
    public void assignNIP(String nip) {
        accountView.showLoader(context.getString(R.string.tienes_tarjeta_asignando_nipnuevo));
        AsignarNIPRequest request = new AsignarNIPRequest(Utils.cipherRSA(nip));
        accountIteractor.assignmentNIP(request, ASIGNAR_NIP);
    }

    public void assignNIP(String nip, String nipNewConfirm) {
        accountView.showLoader(context.getString(R.string.tienes_tarjeta_asignando_nipnuevo));
        AsignarNIPRequest request = new AsignarNIPRequest(
                Utils.cipherRSA(nip),
                Utils.cipherRSA(nipNewConfirm)
        );
        accountIteractor.assignmentNIP(request, ASIGNAR_NEW_NIP);
    }

    @Override
    public void gerNumberToSMS() {
        accountView.showLoader(context.getString(R.string.verificando_sms_esperanuevo));
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
        RecuperarContraseniaRequest request = new RecuperarContraseniaRequest(email);
        accountIteractor.recoveryPassword(request);
    }

    @Override
    public void onSuccessDataPerson() {
        ((IRenapoView) accountView).onValidateUserDataSuccess();
    }

    @Override
    public void onError(WebService ws, Object error) {
        accountView.hideLoader();
        if (accountView instanceof  IRenapoView){
            if (ws == VALIDAR_DATOS_PERSONA){
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
            } else if (ws == CONSULTA_SALDO_CUPO) {
                onSuccesBalanceCupo();
            } else if (ws == ESTATUS_CUENTA){
                onSuccesStateCuenta();
            } else {
                accountView.showError(error);
            }


        } else if (accountView instanceof IDocumentApproved) {
            accountView.showError(error);
        } else if (accountView instanceof ILoginView) {
            if (ws == INICIAR_SESION_SIMPLE) {
                //RequestHeaders.setUsername("");
            }
            accountView.showError(error);
        } else if (accountView instanceof IMyPassValidation) {
            if (ws == VALIDAR_FORMATO_CONTRASENIA) {
                ((IMyPassValidation) accountView).validationPasswordFailed(error.toString());
            }
        } else if (accountView instanceof IMyCardView) {
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
    public void geEstatusCuenta(String numberCard) {
        this.accountView.showLoader("");
        EstatusCuentaRequest estatusCuentaRequest = new EstatusCuentaRequest(numberCard);
        accountIteractor.onStatusCuenta(estatusCuentaRequest);
    }

    @Override
    public void updateBalanceAdq() {
        this.accountView.showLoader(context.getString(R.string.actualizando_saldo));
        accountIteractor.getBalanceAdq();
    }

    @Override
    public void updateBalanceCupo() {
        this.accountView.showLoader(context.getString(R.string.actualizando_saldo));
        accountIteractor.getBalanceCupo();
    }

    @Override
    public void getPaisesList() {
        ArrayList<Countries> arrayList = accountIteractor.getPaisesList();
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
        } else if (accountView instanceof IChangeNIPView) {
            if (ws == ASIGNAR_NEW_NIP) {
                accountView.nextScreen(EVENT_GO_CHANGE_NIP_SUCCESS, data);
                //accountView.showError(data.toString());
               // mensajesucces.showError(data.toString());

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
            Log.i(TAG, context.getString(R.string.sesion_close));
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
        ((IBalanceView) this.accountView).updateBalance();
    }

    @Override
    public void onSuccesStateCuenta() {
        try {
            ((IBalanceView) this.accountView).updateStatus();
        } catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccesBalanceAdq() {
        ((IBalanceView) this.accountView).updateBalanceAdq();
    }

    @Override
    public void onSuccesBalanceCupo() {
        ((IBalanceView) this.accountView).updateBalanceCupo();
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
        }catch (Exception e){


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
        }catch (Exception e){
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadCardCover(int container, Fragment fragment) {
        this.isBackShown = false;
        view.loadCardCover(container,fragment);
    }

    @Override
    public void loadCardCoverdongle(int container, Fragment fragment) {
        this.isBackShowndongle = false;
        view.loadCardCover(container,fragment);

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
