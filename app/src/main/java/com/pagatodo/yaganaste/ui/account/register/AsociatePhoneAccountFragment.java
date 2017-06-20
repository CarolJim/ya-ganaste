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
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IAprovView;
import com.pagatodo.yaganaste.interfaces.IVerificationSMSView;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.SeekBarBaseFragment;

import java.util.Timer;

import butterknife.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_LOGIN;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_REGISTER_COMPLETE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class AsociatePhoneAccountFragment extends SeekBarBaseFragment implements IVerificationSMSView, IAprovView {

    private static final String TAG = AsociatePhoneAccountFragment.class.getSimpleName();
    private static final long CHECK_SMS_VALIDATE_DELAY = 10000;
    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;

    int counterRetry = 1;

    Timer timer = new Timer();
    BroadcastReceiver broadcastReceiver;

    private AccountPresenterNew accountPresenter;

    //private AprovPresenter aprovPresenter;
    public AsociatePhoneAccountFragment() {
    }

    public static AsociatePhoneAccountFragment newInstance() {
        AsociatePhoneAccountFragment fragmentRegister = new AsociatePhoneAccountFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);
        //aprovPresenter = new AprovPresenter(getActivity(),this);
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
        super.initViews();
        hideLoader();
    }


    @Override
    protected void continuePayment() {
        accountPresenter.gerNumberToSMS();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                mySeekBar.setProgress(0);
            }
        }, 300);

    }

    @Override
    public void dataUpdated(String message) {
        finishAssociation();
    }

    @Override
    public void smsVerificationSuccess() {
        // executeProvisioning();//TODO Descomentar para realizar aprovisionamiento
        accountPresenter.updateUserInfo();
    }

    @Override
    public void verifyActivationProvisingFailed(String message) {
        showError(message);
    }

    @Override
    public void activationProvisingFailed(String message) {
        showError(message);
    }

    private void executeProvisioning() {
        //    aprovPresenter.getActivationCode();
    }

    @Override
    public void showErrorAprov(Object error) {
        showError(error.toString());
        finishAssociation();
    }

    /*Una vez aprovisionado, se suscribe a las notificationes*/
    @Override
    public void provisingCompleted() {

        //aprovPresenter.subscribePushNotification();
    }

    @Override
    public void subscribeNotificationSuccess() {
        /*TODO Almacenar preference indicando que ya se encuentra registrado a las notificaciones desde presenter*/
        finishAssociation();
    }

    public void finishAssociation() {
        nextScreen(EVENT_GO_REGISTER_COMPLETE, null);
    }

    @Override
    public void smsVerificationFailed(String message) {
        if (counterRetry < 4) {
            counterRetry++;
            showLoader(getString(R.string.activacion_sms_verificando));
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    accountPresenter.doPullActivationSMS(String.format("Verificando activación de dispositivo\nIntento %d", counterRetry));
                }
            }, CHECK_SMS_VALIDATE_DELAY);
        } else {
            goLoginAlert(message);
        }
    }

    @Override
    public void devicesAlreadyAssign(String message) {
        goLoginAlert(message);
    }

    @Override
    public void showLoader(String message) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void showError(Object error) {
        if (!error.toString().isEmpty()) UI.showToastShort(error.toString(), getActivity());
    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void messageCreated(MessageValidation messageValidation) {
        sendSMS(messageValidation.getPhone(), messageValidation.getMessage());
    }

    /**
     * BroadcastReceiver para realizar el envío del SMS
     **/
    BroadcastReceiver broadcastReceiverSend = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    UI.showToastShort("Mensaje Enviado", getActivity());

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            accountPresenter.doPullActivationSMS(getString(R.string.activacion_sms_verificando));
                        }
                    }, CHECK_SMS_VALIDATE_DELAY);

                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    goLoginAlert(getString(R.string.fallo_envio));
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    goLoginAlert(getString(R.string.sin_servicio));
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    goLoginAlert(getString(R.string.null_pdu));
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    goLoginAlert(getString(R.string.sin_senial));
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
                        UI.showToastShort("SMS Entregado", getActivity());
                        break;
                    case Activity.RESULT_CANCELED:
                        UI.showToastShort("SMS No Entregado", getActivity());
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

    private void goLoginAlert(String message) {

        UI.createCustomDialog("", message, getChildFragmentManager(), getFragmentTag(), new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                continuePayment();
            }

            @Override
            public void actionCancel(Object... params) {
                //No-Op
                goToLogin();
            }
        }, "Reintentar", "Cancelar");

        //UI.createSimpleCustomDialogNoCancel("", message, getChildFragmentManager(),);
    }

    private void goToLogin() {
        RegisterUser.resetRegisterUser();
        nextScreen(EVENT_GO_LOGIN, null);
    }
}

