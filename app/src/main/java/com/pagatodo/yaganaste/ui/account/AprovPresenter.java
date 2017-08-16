package com.pagatodo.yaganaste.ui.account;

import android.content.Context;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActivacionAprovSofttokenRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.VerificarActivacionAprovSofttokenRequest;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.provisioning.presenter.ProvisioningPresenterAbs;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAprovIteractor;
import com.pagatodo.yaganaste.interfaces.IAprovPresenter;
import com.pagatodo.yaganaste.interfaces.IAprovView;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;

import java.lang.reflect.Method;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTIVACION_APROV_SOFTTOKEN;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION_APROV_SOFTTOKEN;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_LOGIN;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;

/**
 * Created by flima on 22/03/2017.
 */

public abstract class AprovPresenter extends ProvisioningPresenterAbs implements IAprovPresenter, IAccountManager {
    private IAprovIteractor aprovIteractor;
    private IAprovView aprovView;
    private Preferencias prefs = App.getInstance().getPrefs();
    private Method currentMethod;
    private Method initProvisioning;
    private Object[] currentMethodParams;

    private int generalReintent;
    private int individualReintent;
    private boolean requiresUserFeedback;

    private final int maxIntents = 3;



    AprovPresenter(Context context, boolean requiresUserFeedback) {
        super(context);
        this.requiresUserFeedback = requiresUserFeedback;
        aprovIteractor = new AprovInteractor(this);
    }

    void setAprovView(IAprovView aprovView) {
        this.aprovView = aprovView;
    }


    /***
     *Implementación de Aprovisionamiento*
     *
     * */
    public void reset() {
        this.generalReintent = 0;
        this.individualReintent = 0;
    }

    @Override
    public void getActivationCode() {
        individualReintent++;
        this.currentMethod = new Object(){}.getClass().getEnclosingMethod();
        initProvisioning = currentMethod;
        this.currentMethodParams = new Object[]{};
        super.getActivationCode();
    }


    @Override
    public void getPinPolicy() {
        individualReintent++;
        this.currentMethod = new Object(){}.getClass().getEnclosingMethod();
        this.currentMethodParams = new Object[]{};
        super.getPinPolicy();
    }


    @Override
    public void registerPin(String pin) {
        individualReintent++;
        this.currentMethod = new Object(){}.getClass().getEnclosingMethod();
        this.currentMethodParams = new Object[]{pin};
        super.registerPin(pin);
    }


    @Override
    public void setTokenNotificationId(String tokenNotificationId, String pin) {
        this.currentMethod = new Object(){}.getClass().getEnclosingMethod();
        this.currentMethodParams = new Object[]{tokenNotificationId, pin};
        super.setTokenNotificationId(tokenNotificationId, pin);
    }


    @Override
    public void verifyActivationAprov(String codeActivation) {
        individualReintent++;
        this.currentMethod = new Object(){}.getClass().getEnclosingMethod();
        this.currentMethodParams = new Object[]{codeActivation};
        VerificarActivacionAprovSofttokenRequest request = new VerificarActivacionAprovSofttokenRequest(codeActivation);
        aprovIteractor.verifyActivationAprov(request);
    }


    @Override
    public void activationAprov(String codeActivation) {
        individualReintent++;
        this.currentMethod = new Object(){}.getClass().getEnclosingMethod();
        this.currentMethodParams = new Object[]{codeActivation};
        ActivacionAprovSofttokenRequest request = new ActivacionAprovSofttokenRequest(codeActivation);
        aprovIteractor.activationAprov(request);
    }


    /**Implementación de Freja**/
    @Override
    public void setActivationCode(String activationCode) {
        SingletonUser.getInstance().setActivacionCodeFreja(activationCode);// Almacenamos el activationCode de FREJA
        individualReintent = 0;
        verifyActivationAprov(activationCode);
    }


    @Override
    public void setPinPolicy(int min, int max) {
        individualReintent = 0;
        String pin = prefs.loadData(SHA_256_FREJA);
        registerPin(pin);
    }


    @Override
    public void endProvisioning() { // Finaliza el proceso con FREJA
        individualReintent = 0;
        String activationCode = SingletonUser.getInstance().getActivacionCodeFreja();
        activationAprov(activationCode);
    }


    @Override
    public void subscribePushNotification() {
        String pin = prefs.loadData(SHA_256_FREJA);
        String tokenId = FirebaseInstanceId.getInstance().getToken();
        setTokenNotificationId(tokenId,pin);
    }

    @Override
    public void endTokenNotification() {
       aprovView.subscribeNotificationSuccess();
    }


    @Override
    public void onError(final Errors error) {
        final INavigationView navigationView =
                aprovView instanceof INavigationView ? (INavigationView) aprovView : null;

        if (individualReintent < 3) { //Si el reintento individual aun no excede el maximo
            if (requiresUserFeedback) {
                handleIndividualErrorWithUserFeedback(error, navigationView);
                return;
            } else {
                if (error.allowsReintent()) {
                    handleIndividualErrorNoFeedback(error);
                    return;
                }
            }
        }

        if (generalReintent < maxIntents-1) { //Si el numero de intentos general aun no excede
            generalReintent++;
            individualReintent = 0;
            if (requiresUserFeedback) {
                currentMethod = initProvisioning;
                currentMethodParams = new Object[]{};
                error.setAllowReintent(true);
                handleIndividualErrorWithUserFeedback(error, navigationView);
                return;
            } else {
                getActivationCode();
                return;
            }
        }
        if (navigationView != null) {
            navigationView.nextScreen(EVENT_APROV_FAILED, null);
        }

    }

    private void handleIndividualErrorWithUserFeedback(final Errors error, final INavigationView navigationView) {


        DialogDoubleActions actions = new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                if (error.allowsReintent()) {
                    currentMethod.setAccessible(true);
                    try {
                        currentMethod.invoke(AprovPresenter.this, currentMethodParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    navigationView.nextScreen(EVENT_APROV_FAILED, null);
                }
            }

            @Override
            public void actionCancel(Object... params) {
                navigationView.nextScreen(EVENT_APROV_FAILED, null);
            }
        };

        ErrorObject.Builder builder = new ErrorObject.Builder().setMessage(error.getMessage())
                .setHasCancel(error.allowsReintent()).setActions(actions);

        aprovView.showErrorAprov(builder.build());
    }

    private void handleIndividualErrorNoFeedback(Errors error) {
        if (error.allowsReintent()) {
            currentMethod.setAccessible(true);
            try {
                currentMethod.invoke(AprovPresenter.this, currentMethodParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSucces(WebService ws, Object msgSuccess) {
        if (ws == VERIFICAR_ACTIVACION_APROV_SOFTTOKEN) {
            individualReintent = 0;
            getPinPolicy();
        } else if (ws == ACTIVACION_APROV_SOFTTOKEN) {
            aprovView.provisingCompleted();
        }
    }

    @Override
    public void onError(WebService ws, Object error) {
        if (ws == VERIFICAR_ACTIVACION_APROV_SOFTTOKEN) {
            onError(Errors.VERIFICAR_ACTIVACION_APROV_SOFTTOKEN);
        } else if (ws == ACTIVACION_APROV_SOFTTOKEN) {
            onError(Errors.ACTIVACION_APROV_SOFTTOKEN);
        }
    }


}
