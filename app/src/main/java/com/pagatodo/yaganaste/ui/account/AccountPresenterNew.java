package com.pagatodo.yaganaste.ui.account;

import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.MessageValidation;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.interfaces.IAccountAddressRegisterView;
import com.pagatodo.yaganaste.interfaces.IAccountCardNIPView;
import com.pagatodo.yaganaste.interfaces.IAccountCardView;
import com.pagatodo.yaganaste.interfaces.IAccountIteractorNew;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAccountPresenterNew;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.IAccountView2;
import com.pagatodo.yaganaste.interfaces.IUserDataRegisterView;
import com.pagatodo.yaganaste.interfaces.IVerificationSMSView;
import com.pagatodo.yaganaste.interfaces.enums.TypeLogin;
import com.pagatodo.yaganaste.interfaces.enums.WebService;

import java.util.List;
import java.util.function.ObjLongConsumer;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_CUENTA_DISPONIBLE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_NIP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CERRAR_SESION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_ASIGNACION_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_COMPLETO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_NUMERO_SMS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_ESTATUS_USUARIO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_FORMATO_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASOCIATE_PHONE;
import static com.pagatodo.yaganaste.utils.Recursos.DEVICE_ALREADY_ASSIGNED;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountPresenterNew implements IAccountPresenterNew, IAccountManager {
    private static final String TAG = AccountPresenterNew.class.getName();
    private IAccountIteractorNew accountIteractor;
    private IAccountView2 accountView;

    public AccountPresenterNew(IAccountView2 accountView) {
        this.accountView = accountView;
        accountIteractor = new AccountInteractorNew(this);
    }

    @Override
    public void validateEmail(String usuario) {
        accountView.showLoader("Verificando Correo Electrónico.");
        accountIteractor.validateUserStatus(usuario);
    }

    @Override
    public void goToNextStepAccount(String event) {
        accountView.hideLoader();
    }


    @Override
    public void createUser() {
        accountView.showLoader(App.getInstance().getString(R.string.msg_register));
        accountIteractor.createUser();
    }

    @Override
    public void login(TypeLogin type,String user, String password) {
        accountView.showLoader("");
        IniciarSesionRequest requestLogin = new IniciarSesionRequest(user,password,"");//TODO Validar si se envia el telefono vacío-
        accountIteractor.login(requestLogin);
    }

    @Override
    public void logout() {
        accountIteractor.logout();
    }

    @Override
    public void checkCardAssigment(String numberCard) {
        accountView.showLoader(App.getContext().getString(R.string.tienes_tarjeta_validando_tarjeta));
        accountIteractor.checkCard(numberCard);
    }

    @Override
    public void getNeighborhoods(String zipCode) {
        accountIteractor.getNeighborhoodByZipCode(zipCode);
    }

    @Override
    public void validatePasswordFormat(String password) {
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
    public void onError(WebService ws,Object error) {
        accountView.hideLoader();
        if(accountView instanceof IAccountRegisterView){
            if (ws == CREAR_USUARIO_COMPLETO) {
                ((IAccountRegisterView) accountView).clientCreateFailed(error.toString());
            }else if(ws == OBTENER_COLONIAS_CP){
                ((IAccountRegisterView) accountView).showError(error.toString());
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
            }else{
                accountView.showError(error);
            }
        }else{
            accountView.showError(error);
        }
    }

    @Override
    public void onSucces(WebService ws,Object data) {
        if(accountView instanceof IAccountRegisterView){
            if (ws == CREAR_USUARIO_COMPLETO) {
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
                ((IUserDataRegisterView) accountView).validationPasswordSucces();
            }
        }else if(accountView instanceof IAccountAddressRegisterView) {
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
                accountView.nextStepRegister(EVENT_GO_ASOCIATE_PHONE,data);
            }
        }else if(accountView instanceof IVerificationSMSView) {
            if(ws == OBTENER_NUMERO_SMS) {
                ((IVerificationSMSView) accountView).messageCreated((MessageValidation) data);
            }else if(ws == VERIFICAR_ACTIVACION){ // Activacion con SMS ha sido verificada.
                ((IVerificationSMSView) accountView).smsVerificationSuccess();
            }
        }else if(ws == CERRAR_SESION){
            Log.i(TAG,"La sesión se ha cerrado.");
        }
    }
}
