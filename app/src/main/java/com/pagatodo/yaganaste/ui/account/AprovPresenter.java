package com.pagatodo.yaganaste.ui.account;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
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
import com.pagatodo.yaganaste.interfaces.IAprovIteractor;
import com.pagatodo.yaganaste.interfaces.IAprovPresenter;
import com.pagatodo.yaganaste.interfaces.IAprovView;
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
import static com.pagatodo.yaganaste.utils.Recursos.CRC32_FREJA;
import static com.pagatodo.yaganaste.utils.Recursos.DEVICE_ALREADY_ASSIGNED;

/**
 * Created by flima on 22/03/2017.
 */

public class AprovPresenter extends ProvisioningPresenterAbs implements IAprovPresenter, IAccountManager {
    private static final String TAG = AprovPresenter.class.getName();
    private IAprovIteractor aprovIteractor;
    private IAprovView aprovView;
    private Preferencias prefs = App.getInstance().getPrefs();

    public AprovPresenter(Context context,IAprovView aprovView) {
        super(context);
        this.aprovView = aprovView;
        aprovIteractor = new AprovInteractor(this);
    }


    @Override
    public void onError(WebService ws,Object error) {
        aprovView.showErrorAprov(error.toString());
    }

    @Override
    public void onSucces(WebService ws,Object data) {

            if(ws == VERIFICAR_ACTIVACION_APROV_SOFTTOKEN){ // Error en Verificacion de Aprovisionamiento
                getPinPolicy(); // Obtenemos las Reglas del Pin
            }else if(ws == ACTIVACION_APROV_SOFTTOKEN){// Error activación de Aprovisionamiento
                aprovView.provisingCompleted();
            }
    }

    @Override
    public void goToNextStepAccount(String event, Object data) {

    }

    /***
     *Implementación de Aprovisionamiento*
     *
     * */
    @Override
    public void verifyActivationAprov(String codeActivation) {
        VerificarActivacionAprovSofttokenRequest request = new VerificarActivacionAprovSofttokenRequest(codeActivation);
        aprovIteractor.verifyActivationAprov(request);
    }

    @Override
    public void activationAprov(String codeActivation) {
        ActivacionAprovSofttokenRequest request = new ActivacionAprovSofttokenRequest(codeActivation);
        aprovIteractor.activationAprov(request);
    }

    /**Implementación de Freja**/
    @Override
    public void setActivationCode(String activationCode) {
        SingletonUser.getInstance().setActivacionCodeFreja(activationCode);// Almacenamos el activationCode de FREJA
        Log.i("WSC FREJA","Pin recibido de Freja: "+activationCode );
        verifyActivationAprov(activationCode);
    }

    @Override
    public void setPinPolicy(int min, int max) { // OnSuccess de getPinPolicy()
        /**TODO Se envía la misma contraseña que ingreso el usuario en su cuenta de Ya Ganaste*/
        String pin = prefs.loadData(CRC32_FREJA);
        Log.i("WSC FREJA","Pin registrado Freja: "+pin );
        registerPin(pin);
    }

    @Override
    public void endProvisioning() { // Finaliza el proceso con FREJA
        String activationCode = SingletonUser.getInstance().getActivacionCodeFreja();
        activationAprov(activationCode);
    }

    @Override
    public void subscribePushNotification() {
        String pin = prefs.loadData(CRC32_FREJA);
        Log.i("WSC FREJA","Pin enviado a Freja: "+pin );
        String tokenId = FirebaseInstanceId.getInstance().getToken();
        setTokenNotificationId(tokenId,pin);
    }

    public void endTokenNotification() {
       aprovView.subscribeNotificationSuccess();
    }

    @Override
    public void handleException(Exception e) {
        Log.i(TAG,"ERROR FREJA: "+e.toString());
        aprovView.showErrorAprov(e.toString());
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

        aprovView.showErrorAprov("");
    }


}
