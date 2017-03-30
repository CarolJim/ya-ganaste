package com.pagatodo.yaganaste.ui.account;

import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.MessageValidation;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.interfaces.IAccountAddressRegisterView;
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

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CERRAR_SESION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_ASIGNACION_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_COMPLETO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_NUMERO_SMS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_FORMATO_CONTRASENIA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountPresenterNew implements IAccountPresenterNew, IAccountManager {
    private String TAG = AccountPresenterNew.class.getName();
    private IAccountIteractorNew accountIteractor;
    private IAccountView2 accountView;

    public AccountPresenterNew(IAccountView2 accountView) {
        this.accountView = accountView;
        accountIteractor = new AccountInteractorNew(this);
    }

    @Override
    public void initValidationLogin(String usuario) {
        accountView.showLoader("");
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
        accountIteractor.checkSessionState(type,requestLogin);
    }

    @Override
    public void logout() {
        accountIteractor.logout();
    }

    @Override
    public void checkCardAssigment(String numberCard) {
        accountView.showLoader(App.getContext().getString(R.string.tienes_tarjeta_validando_cuenta));
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
    public void assignCard() {
        accountView.showLoader(App.getContext().getString(R.string.tienes_tarjeta_asignando));
        accountIteractor.assigmentAccountAvaliable(Card.getCardData().getIdAccount());
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
            if(ws == VALIDAR_FORMATO_CONTRASENIA) {
                ((IUserDataRegisterView) accountView).validationPasswordFailed(error.toString());
            }
        } else if(accountView instanceof IVerificationSMSView) {
            if(ws == VERIFICAR_ACTIVACION){
                ((IVerificationSMSView) accountView).smsVerificationFailed(error.toString());
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
            if(ws == VALIDAR_FORMATO_CONTRASENIA) {
                ((IUserDataRegisterView) accountView).validationPasswordSucces();
            }
        }else if(accountView instanceof IAccountAddressRegisterView) {
            if (ws == OBTENER_COLONIAS_CP) {
                ((IAccountAddressRegisterView) accountView).setNeighborhoodsAvaliables((List<ColoniasResponse>) data);
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
