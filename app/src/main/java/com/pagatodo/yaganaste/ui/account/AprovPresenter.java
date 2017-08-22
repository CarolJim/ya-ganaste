package com.pagatodo.yaganaste.ui.account;

import android.content.Context;
import android.util.Log;

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
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.utils.StringConstants;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTIVACION_APROV_SOFTTOKEN;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION_APROV_SOFTTOKEN;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_LOGIN;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_PROVISIONING;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_PUSH;
import static com.pagatodo.yaganaste.utils.StringConstants.USER_PROVISIONED;

/**
 * Created by flima on 22/03/2017.
 */

public abstract class AprovPresenter extends ProvisioningPresenterAbs implements IAprovPresenter, IAccountManager {

    private static final String TAG = AprovPresenter.class.getSimpleName();
    private IAprovIteractor aprovIteractor;
    private IAprovView aprovView;
    private Preferencias prefs = App.getInstance().getPrefs();
    private Method currentMethod;
    private Method initProvisioning;
    private Object[] currentMethodParams;
    private Object[] initMethodParams;

    private int generalReintent;
    private int individualReintent;
    private boolean requiresUserFeedback;

    private final int maxIntents = 3;
    private Preferencias preferencias;
    private static AtomicBoolean isProvisioning = new AtomicBoolean(false);



    public AprovPresenter(Context context, boolean requiresUserFeedback) {
        super(context);
        this.requiresUserFeedback = requiresUserFeedback;
        aprovIteractor = new AprovInteractor(this);
        this.preferencias = App.getInstance().getPrefs();
    }

    public void setAprovView(IAprovView aprovView) {
        if (aprovView instanceof TabsView){
            super.setTabsView((TabsView) aprovView);
        }
        this.aprovView = aprovView;
    }

    public void doProvisioning(){
        if (!isProvisioning.get()) {
            isProvisioning.set(true);
            reset();
            if (!preferencias.containsData(HAS_PROVISIONING) || !preferencias.loadData(USER_PROVISIONED).equals(RequestHeaders.getUsername())) {
                aprovView.showLoader("");
                getActivationCode();
            } else if (!preferencias.containsData(HAS_PUSH)){
                subscribePushNotification();
            } else {
                isProvisioning.set(false);
            }
        }
    }

    /***
     *Implementación de Aprovisionamiento*
     *
     * */
    private void reset() {
        this.generalReintent = 0;
        this.individualReintent = 0;
    }

    @Override
    public void getActivationCode() {
        aprovView.showLoader("");
        individualReintent++;
        this.currentMethod = new Object(){}.getClass().getEnclosingMethod();
        Log.e(TAG, currentMethod.getName());
        initProvisioning = currentMethod;
        this.currentMethodParams = new Object[]{};
        this.initMethodParams = new Object[]{};
        super.getActivationCode();
    }

    /**Implementación de Freja**/
    @Override
    public void setActivationCode(String activationCode) {
        SingletonUser.getInstance().setActivacionCodeFreja(activationCode);// Almacenamos el activationCode de FREJA
        individualReintent = 0;
        verifyActivationAprov(activationCode);
    }


    @Override
    public void verifyActivationAprov(String codeActivation) {
        aprovView.showLoader("");
        individualReintent++;
        this.currentMethod = new Object(){}.getClass().getEnclosingMethod();
        Log.e(TAG, currentMethod.getName());
        this.currentMethodParams = new Object[]{codeActivation};
        VerificarActivacionAprovSofttokenRequest request = new VerificarActivacionAprovSofttokenRequest(codeActivation);
        aprovIteractor.verifyActivationAprov(request);
    }

    @Override
    public void getPinPolicy() {
        aprovView.showLoader("");
        individualReintent++;
        this.currentMethod = new Object(){}.getClass().getEnclosingMethod();
        Log.e(TAG, currentMethod.getName());
        this.currentMethodParams = new Object[]{};
        super.getPinPolicy();
    }

    @Override
    public void setPinPolicy(int min, int max) {
        individualReintent = 0;
        String pin = prefs.loadData(SHA_256_FREJA);
        registerPin(pin);
    }

    @Override
    public void registerPin(String pin) {
        aprovView.showLoader("");
        individualReintent++;
        this.currentMethod = new Object(){}.getClass().getEnclosingMethod();
        Log.e(TAG, currentMethod.getName());
        this.currentMethodParams = new Object[]{pin};
        super.registerPin(pin);
    }

    @Override
    public void endProvisioning() { // Finaliza el proceso con FREJA
        individualReintent = 0;
        String activationCode = SingletonUser.getInstance().getActivacionCodeFreja();
        activationAprov(activationCode);
    }

    @Override
    public void activationAprov(String codeActivation) {
        aprovView.showLoader("");
        individualReintent++;
        this.currentMethod = new Object(){}.getClass().getEnclosingMethod();
        Log.e(TAG, currentMethod.getName());
        this.currentMethodParams = new Object[]{codeActivation};
        ActivacionAprovSofttokenRequest request = new ActivacionAprovSofttokenRequest(codeActivation);
        aprovIteractor.activationAprov(request);
    }

    @Override
    public void onSucces(WebService ws, Object msgSuccess) {
        Log.e(TAG, "onSucces: " + ws.toString());
        aprovView.showLoader("");
        if (ws == VERIFICAR_ACTIVACION_APROV_SOFTTOKEN) {
            individualReintent = 0;
            getPinPolicy();
        } else if (ws == ACTIVACION_APROV_SOFTTOKEN) {
            preferencias.saveDataBool(HAS_PROVISIONING, true);
            preferencias.saveData(USER_PROVISIONED, RequestHeaders.getUsername());
            subscribePushNotification();
        }
    }

    @Override
    public void onError(WebService ws, Object error) {
        Log.e(TAG, "onError: " + ws.toString());
        if (ws == VERIFICAR_ACTIVACION_APROV_SOFTTOKEN) {
            onError(Errors.VERIFICAR_ACTIVACION_APROV_SOFTTOKEN);
        } else if (ws == ACTIVACION_APROV_SOFTTOKEN) {
            onError(Errors.ACTIVACION_APROV_SOFTTOKEN);
        }
    }

    @Override
    public void subscribePushNotification() {
        String pin = prefs.loadData(SHA_256_FREJA);
        String tokenId = FirebaseInstanceId.getInstance().getToken();
        individualReintent = 0;
        setTokenNotificationId(tokenId,pin);
    }

    @Override
    public void setTokenNotificationId(String tokenNotificationId, String pin) {
        aprovView.showLoader("");
        this.currentMethod = new Object(){}.getClass().getEnclosingMethod();
        Log.e(TAG, currentMethod.getName());
        initProvisioning = currentMethod;
        this.currentMethodParams = new Object[]{tokenNotificationId, pin};
        initMethodParams = new Object[]{tokenNotificationId, pin};
        super.setTokenNotificationId(tokenNotificationId, pin);
    }

    @Override
    public void endTokenNotification() {
        hideLoader();
        preferencias.saveDataBool(HAS_PUSH, true);
        isProvisioning.set(false);
        aprovView.finishAssociation();
    }



    @Override
    public void onError(final Errors error) {
        Log.e(TAG, "onError: " + error.getMessage() + "\n Code: " + String.valueOf(error.getErrorCode()));
        final INavigationView navigationView =
                aprovView instanceof INavigationView ? (INavigationView) aprovView : null;

        if (individualReintent < 3) { //Si el reintento individual aun no excede el maximo
            if (requiresUserFeedback) {
                handleIndividualErrorWithUserFeedback(error, navigationView);
                return;
            } else {
                if (error.allowsReintent()) {
                    handleIndividualErrorNoFeedback();
                    return;
                }
            }
        }

        if (generalReintent < maxIntents-1) { //Si el numero de intentos general aun no excede
            generalReintent++;
            individualReintent = 0;
            if (requiresUserFeedback) {
                currentMethod = initProvisioning;
                currentMethodParams = initMethodParams;
                error.setAllowReintent(true);
                handleIndividualErrorWithUserFeedback(error, navigationView);
                return;
            } else {
                getActivationCode();
                return;
            }
        }
        aprovView.hideLoader();
        isProvisioning.set(false);
        if (navigationView != null) {
            navigationView.nextScreen(EVENT_APROV_FAILED, null);
        }

    }

    private void handleIndividualErrorWithUserFeedback(final Errors error, final INavigationView navigationView) {
        aprovView.hideLoader();
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
                    isProvisioning.set(false);
                    navigationView.nextScreen(EVENT_APROV_FAILED, null);

                }
            }

            @Override
            public void actionCancel(Object... params) {
                isProvisioning.set(false);
                navigationView.nextScreen(EVENT_APROV_FAILED, null);
            }
        };

        ErrorObject.Builder builder = new ErrorObject.Builder().setMessage(error.getMessage())
                .setHasCancel(error.allowsReintent()).setActions(actions);

        aprovView.showErrorAprov(builder.build());
    }

    private void handleIndividualErrorNoFeedback() {
        currentMethod.setAccessible(true);
        try {
            currentMethod.invoke(AprovPresenter.this, currentMethodParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
