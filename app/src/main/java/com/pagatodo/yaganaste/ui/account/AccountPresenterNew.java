package com.pagatodo.yaganaste.ui.account;

import android.content.Context;
import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.MessageValidation;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActivacionAprovSofttokenRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioFWSRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.VerificarActivacionAprovSofttokenRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.provisioning.presenter.ProvisioningPresenterAbs;
import com.pagatodo.yaganaste.interfaces.IAccountAddressRegisterView;
import com.pagatodo.yaganaste.interfaces.IAccountCardNIPView;
import com.pagatodo.yaganaste.interfaces.IAccountCardView;
import com.pagatodo.yaganaste.interfaces.IAccountIteractorNew;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAccountPresenterNew;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.IAccountView2;
import com.pagatodo.yaganaste.interfaces.ILoginView;
import com.pagatodo.yaganaste.interfaces.IUserDataRegisterView;
import com.pagatodo.yaganaste.interfaces.IVerificationSMSView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTIVACION_APROV_SOFTTOKEN;
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
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION_APROV_SOFTTOKEN;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASOCIATE_PHONE;
import static com.pagatodo.yaganaste.utils.Recursos.DEVICE_ALREADY_ASSIGNED;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountPresenterNew extends ProvisioningPresenterAbs implements IAccountPresenterNew, IAccountManager {
    private static final String TAG = AccountPresenterNew.class.getName();
    private IAccountIteractorNew accountIteractor;
    private IAccountView2 accountView;
    private Preferencias prefs = App.getInstance().getPrefs();

    public AccountPresenterNew(Context context) {
        super(context);
        //this.accountView = accountView;
        accountIteractor = new AccountInteractorNew(this);
    }

    public void setIView( IAccountView2 accountView){
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
        accountView.nextStepRegister(event,data);
    }

    @Override
    public void createUser() {
        accountView.showLoader(App.getInstance().getString(R.string.msg_register));

        RegisterUser registerUser = RegisterUser.getInstance();
        CrearUsuarioFWSRequest request = new CrearUsuarioFWSRequest(
                registerUser.getEmail(),
                registerUser.getNombre(),
                registerUser.getApellidoPaterno(),
                registerUser.getApellidoMaterno(),
                registerUser.getEmail(),
                registerUser.getContrasenia());

        accountIteractor.createUser(request);
    }

    @Override
    public void login(String user, String password) {
        RequestHeaders.setTokendevice(Utils.getTokenDevice(App.getInstance().getApplicationContext()));
        accountView.showLoader("");
        IniciarSesionRequest requestLogin = new IniciarSesionRequest(user,password,"");//TODO Validar si se envia el telefono vacío-
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
            }else if(ws == VERIFICAR_ACTIVACION_APROV_SOFTTOKEN){
                ((IVerificationSMSView) accountView).verifyActivationProvisingFailed(error.toString());
            }else if(ws == ACTIVACION_APROV_SOFTTOKEN){
                ((IVerificationSMSView) accountView).activationProvisingFailed(error.toString());
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
            }else if(ws == VERIFICAR_ACTIVACION_APROV_SOFTTOKEN){ // Error en Verificacion de Aprovisionamiento

                getPinPolicy(); // Obtenemos las Reglas del Pin

            }else if(ws == ACTIVACION_APROV_SOFTTOKEN){// Error activación de Aprovisionamiento

                ((IVerificationSMSView) accountView).provisingCompleted(data.toString());

            }
        }else if(ws == CERRAR_SESION){
            Log.i(TAG,"La sesión se ha cerrado.");
        }
    }

    /***
     *Implementación de Aprovisionamiento*
     *
     * */
    @Override
    public void verifyActivationAprov(String codeActivation) {
        VerificarActivacionAprovSofttokenRequest request = new VerificarActivacionAprovSofttokenRequest(codeActivation);
        accountIteractor.verifyActivationAprov(request);
    }

    @Override
    public void activationAprov(String codeActivation) {
        ActivacionAprovSofttokenRequest request = new ActivacionAprovSofttokenRequest(codeActivation);
        accountIteractor.activationAprov(request);
    }

    /**Implementación de Freja**/

    @Override
    public void setActivationCode(String activationCode) {
        SingletonUser.getInstance().setActivacionCodeFreja(activationCode);// Almacenamos el activationCode de FREJA
        verifyActivationAprov(activationCode);
        //activationAprov(activationCode);
    }

    @Override
    public void setPinPolicy(int min, int max) { // OnSuccess de getPinPolicy()
        /*TODO se realiza validación del PIN*/
        /**Se envía la misma contraseña que ingreso el usuario en su cuenta de Ya Ganaste*/
        registerPin("1234");
    }

    @Override
    public void endProvisioning() { // Finaliza el proceso con FREJA
        String activationCode = SingletonUser.getInstance().getActivacionCodeFreja();
        activationAprov(activationCode);
    }

    @Override
    public void handleException(Exception e) {

        Log.i(TAG,e.toString());

    }

    @Override
    public void onError(Errors error) {

        switch (error){

            case NO_ACTIVATION_CODE:

                break;

            case NO_PIN_POLICY:

                break;

            case NO_NIP:

                break;

            case BAD_CHANGE_POLICY:

                break;
        }
    }
}
