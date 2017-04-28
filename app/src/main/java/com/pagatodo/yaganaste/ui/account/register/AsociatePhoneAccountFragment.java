package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.MessageValidation;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.interfaces.IAprovView;
import com.pagatodo.yaganaste.interfaces.IVerificationSMSView;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.AprovPresenter;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentFormBaseFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_LOGIN;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_REGISTER_COMPLETE;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class AsociatePhoneAccountFragment extends PaymentFormBaseFragment implements IVerificationSMSView,IAprovView {

    private static final String TAG = AsociatePhoneAccountFragment.class.getSimpleName();
    private static final long CHECK_SMS_VALIDATE_DELAY = 10000;
    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;

    int counterRetry = 1;

    Timer timer = new Timer();
    BroadcastReceiver broadcastReceiver;

    private AccountPresenterNew accountPresenter;
    private AprovPresenter aprovPresenter;
    public AsociatePhoneAccountFragment() {
    }

    public static AsociatePhoneAccountFragment newInstance() {
        AsociatePhoneAccountFragment fragmentRegister = new AsociatePhoneAccountFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenter = ((AccountActivity)getActivity()).getPresenter();
        accountPresenter.setIView(this);
        aprovPresenter = new AprovPresenter(getActivity(),this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_associate_sms, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        hideLoader();
    }


    @Override
    protected void continuePayment() {
        accountPresenter.gerNumberToSMS();
    }



    @Override
    public void smsVerificationSuccess() {
        executeProvisioning();
    }

    @Override
    public void verifyActivationProvisingFailed(String message) {
        showError(message);
    }

    @Override
    public void activationProvisingFailed(String message) {
        showError(message);
    }

    private void executeProvisioning(){
        aprovPresenter.getActivationCode();
    }

    @Override
    public void showErrorAprov(Object error) {
        showError(error.toString());
        finishAssociation();
    }

    /*Una vez aprovisionado, se suscribe a las notificationes*/
    @Override
    public void provisingCompleted() {
        aprovPresenter.subscribePushNotification();
    }

    @Override
    public void subscribeNotificationSuccess() {
        /*TODO Almacenar preference indicando que ya se encuentra registrado a las notificaciones desde presenter*/
        finishAssociation();
    }

    public void finishAssociation(){
        nextScreen(EVENT_GO_REGISTER_COMPLETE,null);
    }

    @Override
    public void smsVerificationFailed(String message) {
        if (counterRetry < 4) {
            counterRetry++;
            showLoader(String.format("Verificando activación de dispositivo\nIntento %d", counterRetry));
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    accountPresenter.doPullActivationSMS(String.format("Verificando activación de dispositivo\nIntento %d", counterRetry));
                }
            }, CHECK_SMS_VALIDATE_DELAY);
        } else {
            showError(message);
            //executeProvisioning();
            goToLogin();
        }
    }

    @Override
    public void devicesAlreadyAssign(String message) {
        UI.showToast(message,getActivity());
        goToLogin();
    }

    @Override
    public void showLoader(String message) {
        progressLayout.setTextMessage(message);
        progressLayout.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(GONE);
    }

    @Override
    public void showError(Object error) {
        UI.showToastShort(error.toString(),getActivity());
    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void messageCreated(MessageValidation messageValidation) {
        sendSMS(messageValidation.getPhone(),messageValidation.getMessage());
    }

    /**BroadcastReceiver para realizar el envío del SMS**/
    BroadcastReceiver broadcastReceiverSend = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    UI.showToastShort("Mensaje Enviado",getActivity());

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            accountPresenter.doPullActivationSMS(getString(R.string.activacion_sms_verificando));
                        }
                    }, CHECK_SMS_VALIDATE_DELAY);

                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    UI.showToastShort("Falla al Enviar el Mensaje",getActivity());
                    goToLogin();
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    UI.showToastShort("Sin Servicio",getActivity());
                    goToLogin();
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    UI.showToastShort("Null PDU",getActivity());
                    goToLogin();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    UI.showToastShort("Sin Señal Telefónica",getActivity());
                    goToLogin();
                    break;
            }
        }
    };

    private void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(getActivity(), 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(getActivity(), 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        getActivity().registerReceiver(broadcastReceiverSend, new IntentFilter(SENT));
        //---when the SMS has been delivered---
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        UI.showToastShort("SMS Entregado",getActivity());
                        break;
                    case Activity.RESULT_CANCELED:
                        UI.showToastShort("SMS No Entregado",getActivity());
                        break;
                }
            }
        };

        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(DELIVERED));

        showLoader("Enviando Mensaje");
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            getActivity().unregisterReceiver(broadcastReceiverSend);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            getActivity().unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToLogin(){
        RegisterUser.resetRegisterUser();
        nextScreen(EVENT_GO_LOGIN,null);
    }
}

