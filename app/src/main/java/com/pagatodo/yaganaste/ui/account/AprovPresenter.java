package com.pagatodo.yaganaste.ui.account;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActivacionAprovSofttokenRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.VerificarActivacionAprovSofttokenRequest;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.provisioning.presenter.ProvisioningPresenterAbs;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAprovIteractor;
import com.pagatodo.yaganaste.interfaces.IAprovPresenter;
import com.pagatodo.yaganaste.interfaces.IAprovView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTIVACION_APROV_SOFTTOKEN;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION_APROV_SOFTTOKEN;
import static com.pagatodo.yaganaste.utils.Recursos.CRC32_FREJA;

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
    public void hideLoader() {
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
