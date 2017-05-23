package com.pagatodo.yaganaste.ui.account;

import android.content.Context;
import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.MessageValidation;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerDocumentosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.RecuperarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDocumentosResponse;
import com.pagatodo.yaganaste.interfaces.IAccountAddressRegisterView;
import com.pagatodo.yaganaste.interfaces.IAccountCardNIPView;
import com.pagatodo.yaganaste.interfaces.IAccountCardView;
import com.pagatodo.yaganaste.interfaces.IAccountIteractorNew;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAccountPresenterNew;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.IDocumentsPresenter;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.IUploadDocumentsView;
import com.pagatodo.yaganaste.interfaces.IUserDataRegisterView;
import com.pagatodo.yaganaste.interfaces.IVerificationSMSView;
import com.pagatodo.yaganaste.interfaces.RecoveryPasswordView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.utils.Codec;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_INFO_SESION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_CUENTA_DISPONIBLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_NIP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CERRAR_SESION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_ASIGNACION_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_CLIENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_COMPLETO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_NUMERO_SMS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.RECUPERAR_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_ESTATUS_USUARIO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_FORMATO_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASOCIATE_PHONE;
import static com.pagatodo.yaganaste.utils.Recursos.CRC32_FREJA;
import static com.pagatodo.yaganaste.utils.Recursos.DEVICE_ALREADY_ASSIGNED;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountPresenterNew implements IAccountPresenterNew, IAccountManager {
    private static final String TAG = AccountPresenterNew.class.getName();
    private IAccountIteractorNew accountIteractor;
    private INavigationView accountView;
    private Preferencias prefs = App.getInstance().getPrefs();

    public AccountPresenterNew(Context context) {
        accountIteractor = new AccountInteractorNew(this);
    }


    public void setIView( INavigationView accountView){
        this.accountView = accountView;
    }

    @Override
    public void validateEmail(String usuario) {
        accountView.showLoader("Verificando Correo Electrónico.");
        accountIteractor.validateUserStatus(usuario);
    }

    @Override
    public void goToNextStepAccount(String event, Object data) {
        accountView.hideLoader();
        accountView.nextScreen(event,data);
    }


    @Override
    public void createUser() {
        accountView.showLoader(App.getInstance().getString(R.string.msg_register));
        RegisterUser registerUser = RegisterUser.getInstance();
        prefs.saveData(CRC32_FREJA, Codec.applyCRC32(registerUser.getContrasenia()));//Freja

        accountIteractor.createUser();
    }

    @Override
    public void login(String user, String password) {
        RequestHeaders.setUsername(user);
        RequestHeaders.setTokendevice(Utils.getTokenDevice(App.getInstance().getApplicationContext()));
        accountView.showLoader("");
        IniciarSesionRequest requestLogin = new IniciarSesionRequest(user,Utils.cipherRSA(password),"");//TODO Validar si se envia el telefono vacío-
        // Validamos estatus de la sesion, si se encuentra abierta, la cerramos.
        accountIteractor.checkSessionState(requestLogin);
        ///accountIteractor.login(requestLogin);
    }

    @Override
    public void logout() {
        accountIteractor.logout();
    }

    @Override
    public void updateUserInfo() {
        accountIteractor.updateSessionData();
    }

    @Override
    public void checkCardAssigment(String numberCard) {
        accountView.showLoader(App.getContext().getString(R.string.tienes_tarjeta_validando_tarjeta));
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
        accountIteractor.validatePassword(password);
    }

    @Override
    public void assignAccount() {
        accountView.showLoader(App.getContext().getString(R.string.tienes_tarjeta_asignando));
        accountIteractor.assigmentAccountAvaliable(Card.getInstance().getIdAccount());
    }

    @Override
    public void assignNIP(String nip) {
        accountView.showLoader(App.getContext().getString(R.string.tienes_tarjeta_asignando_nip));
        AsignarNIPRequest request = new AsignarNIPRequest(nip);
        accountIteractor.assignmentNIP(request);
    }

    @Override
    public void gerNumberToSMS() {
        accountView.showLoader(App.getContext().getString(R.string.activacion_sms_loader));
        accountIteractor.getSMSNumber();
    }

    @Override
    public void doPullActivationSMS(String message) {
        accountView.showLoader(message);
        accountIteractor.verifyActivationSMS();
    }


    @Override
    public void recoveryPassword(String email) {
        accountView.showLoader("");
        RecuperarContraseniaRequest request = new RecuperarContraseniaRequest(email);
        accountIteractor.recoveryPassword(request);
    }


    @Override
    public void onError(WebService ws,Object error) {
        accountView.hideLoader();
        if(accountView instanceof IAccountRegisterView){
            if (ws == CREAR_USUARIO_CLIENTE) {
                ((IAccountRegisterView) accountView).clientCreateFailed(error.toString());
            }else if(ws == OBTENER_COLONIAS_CP){
                ((IAccountRegisterView) accountView).zipCodeInvalid(error.toString());
            }
        } else if(accountView instanceof IUserDataRegisterView){
            if(ws == VALIDAR_ESTATUS_USUARIO){
                accountView.showError(error.toString());
            }else if(ws == VALIDAR_FORMATO_CONTRASENIA) {
                ((IUserDataRegisterView) accountView).validationPasswordFailed(error.toString());
            }
        } else if(accountView instanceof IAccountCardView) {
            accountView.showError(error);
        }else if(accountView instanceof IAccountCardNIPView) {
            if(ws == ASIGNAR_NIP){
                accountView.showError(error.toString());
            }
        }else if(accountView instanceof IVerificationSMSView) {
            if(ws == VERIFICAR_ACTIVACION){
                if(error instanceof Object[]) {
                    /*Aqui recibimos una respuesta que contiene el código de respuesta del WS y el mensaje del mismo.*/
                    Object[] responseCode = (Object[]) error;
                    if ((int)responseCode[0] == DEVICE_ALREADY_ASSIGNED)
                        ((IVerificationSMSView) accountView).devicesAlreadyAssign(responseCode[1].toString());
                } else{
                    ((IVerificationSMSView) accountView).smsVerificationFailed(error.toString());
                }
            } else if(ws == ACTUALIZAR_INFO_SESION){ // Activacion con SMS ha sido verificada.
                ((IVerificationSMSView) accountView).dataUpdated(error.toString());
            } else{
                accountView.showError(error);
            }
        }else if(accountView instanceof RecoveryPasswordView) {
            if(ws == RECUPERAR_CONTRASENIA){
                ((RecoveryPasswordView) accountView).recoveryPasswordFailed(error.toString());
            }

        }else{
            accountView.showError(error);
        }
    }

    @Override
    public void hideLoader() {
        accountView.hideLoader();
    }

    @Override
    public void onSucces(WebService ws,Object data) {
        accountView.hideLoader();
        if(accountView instanceof IAccountRegisterView){
            if (ws == CREAR_USUARIO_CLIENTE) {
                ((IAccountRegisterView) accountView).clientCreatedSuccess(data.toString());
            }else if(ws == OBTENER_COLONIAS_CP){
                ((IAccountRegisterView) accountView).setNeighborhoodsAvaliables((List<ColoniasResponse>) data);
            }
        }else if(accountView instanceof IUserDataRegisterView){
            if(ws == VALIDAR_ESTATUS_USUARIO) {
                boolean isUser = (boolean)data;
                if(!isUser){
                    ((IUserDataRegisterView) accountView).isEmailAvaliable();
                }else {
                    ((IUserDataRegisterView) accountView).isEmailRegistered();
                }
            }else if(ws == VALIDAR_FORMATO_CONTRASENIA) {
                boolean validatePass = ((int)data == 0);
                if(validatePass){
                    ((IUserDataRegisterView) accountView).validationPasswordSucces();
                }else{
                    ((IUserDataRegisterView) accountView).validationPasswordFailed("Su contraseña es incorrecta");
                }
            }
        }else if(accountView instanceof IAccountAddressRegisterView) { // obtiene el listado de colonias
            if (ws == OBTENER_COLONIAS_CP) {
                ((IAccountAddressRegisterView) accountView).setNeighborhoodsAvaliables((List<ColoniasResponse>) data);
            }
        }else if(accountView instanceof IAccountCardView) {
                if(ws == CONSULTAR_ASIGNACION_TARJETA){
                    ((IAccountCardView) accountView).cardIsValidate(data.toString());
                }else if(ws == ASIGNAR_CUENTA_DISPONIBLE){
                    ((IAccountCardView) accountView).accountAssigned(data.toString());
                }
        }else if(accountView instanceof IAccountCardNIPView) {
            if(ws == ASIGNAR_NIP){
                accountView.nextScreen(EVENT_GO_ASOCIATE_PHONE,data);
            }
        }else if(accountView instanceof IVerificationSMSView) {
            if(ws == OBTENER_NUMERO_SMS) {
                ((IVerificationSMSView) accountView).messageCreated((MessageValidation) data);
            }else if(ws == VERIFICAR_ACTIVACION){ // Activacion con SMS ha sido verificada.
                ((IVerificationSMSView) accountView).smsVerificationSuccess();
            } else if(ws == ACTUALIZAR_INFO_SESION){ // Activacion con SMS ha sido verificada.
            ((IVerificationSMSView) accountView).dataUpdated(data.toString());
            }
        }else if(accountView instanceof RecoveryPasswordView) {
            if (ws == RECUPERAR_CONTRASENIA) {
                ((RecoveryPasswordView) accountView).recoveryPasswordSuccess(data.toString());
            }
        }else if(ws == CERRAR_SESION){
            Log.i(TAG,"La sesión se ha cerrado.");
        }
    }

}
