package com.pagatodo.yaganaste.ui.account;

import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioFWSRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.interfaces.IAccountAddressRegisterView;
import com.pagatodo.yaganaste.interfaces.IAccountBasicRegisterView;
import com.pagatodo.yaganaste.interfaces.IAccountIteractorOld;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAccountPresenterOld;
import com.pagatodo.yaganaste.interfaces.IAccountView;
import com.pagatodo.yaganaste.interfaces.IUserDataRegisterView;
import com.pagatodo.yaganaste.interfaces.enums.TypeLogin;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CERRAR_SESION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_FWS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_FORMATO_CONTRASENIA;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASOCIATE_PHONE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_BASIC_INFO;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_GET_PAYMENTS;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_REGISTER_ADDRESS;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountPresenterOld implements IAccountPresenterOld, IAccountManager {
    private String TAG = AccountPresenterOld.class.getName();
    private IAccountIteractorOld accountIteractor;
    private IAccountView accountView;

    public AccountPresenterOld(IAccountView accountView) {
        this.accountView = accountView;
        accountIteractor = new AccountInteractorOld(this);
    }

    @Override
    public void initValidationLogin(String usuario) {
        accountView.showLoader("");
        accountIteractor.validateUserStatus(usuario);
    }

    @Override
    public void goToNextStepAccount(String event) {
        accountView.hideLoader();
        accountView.nextStepAccountFlow(event);
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
        accountView.showLoader("");
        accountIteractor.checkCard(numberCard);
    }

    @Override
    public void createUser(CrearUsuarioFWSRequest requestCreateUser) {
        /*Guardamos información de usuario en SingletonUser*/
        SingletonUser user = SingletonUser.getInstance();
        user.getDataUser().getUsuario().setNombre(requestCreateUser.getNombre());
        user.getDataUser().getUsuario().setPrimerApellido(requestCreateUser.getPrimerApellido());
        user.getDataUser().getUsuario().setSegundoApellido(requestCreateUser.getSegundoApellido());
        user.getDataExtraUser().setMail(requestCreateUser.getCorreo());
        user.getDataExtraUser().setPass(requestCreateUser.getContrasena());
        RequestHeaders.setTokendevice(Utils.getTokenDevice(App.getInstance().getApplicationContext()));

        accountView.showLoader("");
        accountIteractor.createUser(requestCreateUser);
    }

    @Override
    public void getNeighborhoods(String zipCode) {
        accountIteractor.getNeighborhoodByZipCode(zipCode);
    }

    @Override
    public void selectStepNoCard() {
        SingletonUser user = SingletonUser.getInstance();
        Log.e(TAG, "noCardAction: Usuario: " + user.getDataUser().isEsCliente() +
                " Cuenta: " +user.getDataUser().isConCuenta() +
                " Agente: " + user.getDataUser().isEsAgente());

        if (!user.getDataUser().isEsUsuario()) { // No es Usuario
            accountView.nextStepAccountFlow(EVENT_GO_BASIC_INFO);
        } else { // Si es Usuario
            if(user.getDataUser().isEsCliente()){ // Si Cliente
                if(!user.getDataUser().isConCuenta()){//No Cuenta
                    accountView.nextStepAccountFlow(EVENT_GO_GET_PAYMENTS);
                }else{ // Si Cuenta
                    accountView.nextStepAccountFlow(EVENT_GO_ASOCIATE_PHONE);
                }
            }else if(!user.getDataUser().isEsCliente() && !user.getDataUser().isConCuenta()){// No Cliente, No Cuenta
                if(!user.getDataUser().isConCuenta()){
                    accountView.nextStepAccountFlow(EVENT_GO_REGISTER_ADDRESS);
                }
            }
        }
    }

    @Override
    public void validatePasswordFormat(String password) {
        accountIteractor.validatePassword(password);
    }

    @Override
    public void onError(WebService ws,Object error) {
        accountView.hideLoader();
        if(accountView instanceof IUserDataRegisterView){
            if(ws == VALIDAR_FORMATO_CONTRASENIA) {
                ((IUserDataRegisterView) accountView).validationPasswordFailed(error.toString());
            }
        } else if(accountView instanceof IAccountBasicRegisterView){
            if(ws == VALIDAR_FORMATO_CONTRASENIA){
                ((IAccountBasicRegisterView) accountView).validationPasswordFailed(error.toString());
            }else if(ws == CREAR_USUARIO_FWS){
                accountView.showError(error.toString());
            }

        }else {
            accountView.showError(error);
        }
    }

    @Override
    public void onSucces(WebService ws,Object data) {
        if(accountView instanceof IUserDataRegisterView){
            if(ws == VALIDAR_FORMATO_CONTRASENIA) {
                ((IUserDataRegisterView) accountView).validationPasswordSucces();
            }
        }else if(accountView instanceof IAccountBasicRegisterView){
            if(ws == VALIDAR_FORMATO_CONTRASENIA) {
                ((IAccountBasicRegisterView) accountView).validationPasswordSucces();
            }else if(ws == CREAR_USUARIO_FWS){
                ((IAccountBasicRegisterView) accountView).userCreatedSuccess(data.toString());
            }
        }else if(accountView instanceof IAccountAddressRegisterView){

            ((IAccountAddressRegisterView) accountView).setNeighborhoodsAvaliables((List<ColoniasResponse>)data);

        }else if(ws == CERRAR_SESION){
            Log.i(TAG,"La sesión se ha cerrado.");
        }


    }
}
