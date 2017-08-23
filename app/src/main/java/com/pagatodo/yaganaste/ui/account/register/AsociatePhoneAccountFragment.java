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

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.MessageValidation;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.freja.reset.managers.IResetNIPView;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IAprovView;
import com.pagatodo.yaganaste.interfaces.IVerificationSMSView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.SeekBarBaseFragment;

import java.util.Timer;

import butterknife.BindView;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_LOGIN;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_REGISTER_COMPLETE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_ERROR;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity.EVENT_SESSION_EXPIRED;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class AsociatePhoneAccountFragment extends SeekBarBaseFragment implements IVerificationSMSView,
        IAprovView, IResetNIPView {

    private static final String TAG = AsociatePhoneAccountFragment.class.getSimpleName();
    private static final long CHECK_SMS_VALIDATE_DELAY = 10000;
    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;
    int counterRetry = 1;
    Timer timer = new Timer();
    BroadcastReceiver broadcastReceiver;
    private WebService failed;
    private AccountPresenterNew accountPresenter;
    private Preferencias preferencias;
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
                            accountPresenter.doPullActivationSMS(getString(R.string.verificando_sms_espera));
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
            getActivity().unregisterReceiver(this);
        }
    };

    public static AsociatePhoneAccountFragment newInstance() {
        AsociatePhoneAccountFragment fragmentRegister = new AsociatePhoneAccountFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.preferencias = App.getInstance().getPrefs();
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);
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
        mySeekBar.setEnabled(false);
        accountPresenter.gerNumberToSMS();
    }

    @Override
    public void dataUpdated(String message) {
        executeProvisioning();
    }

    @Override
    public void smsVerificationSuccess() {
        accountPresenter.updateUserInfo();
    }

    private void executeProvisioning() {
        accountPresenter.doProvisioning();
    }

    @Override
    public void showErrorAprov(ErrorObject error) {
        hideLoader();
        onEventListener.onEvent(EVENT_SHOW_ERROR, error);
    }


    public void finishAssociation() {
        if (SingletonUser.getInstance().needsReset()) {
            accountPresenter.doReseting(preferencias.loadData(SHA_256_FREJA));
        } else {
            nextScreen(EVENT_GO_REGISTER_COMPLETE, null);
        }
    }

    @Override
    public void showErrorReset(ErrorObject error) {
        showErrorAprov(error);
    }

    @Override
    public void finishReseting() {
        nextScreen(EVENT_GO_REGISTER_COMPLETE, null);
    }

    @Override
    public void onResetingFailed() {
        nextScreen(EVENT_GO_REGISTER_COMPLETE, null);
    }


    @Override
    public void smsVerificationFailed(String message) {
        if (counterRetry < 4) {
            counterRetry++;
            showLoader(getString(R.string.verificando_sms_espera));
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    accountPresenter.doPullActivationSMS(getString(R.string.verificando_sms_espera));
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
    public void showError(final Object error) {

        if (error != null && !error.toString().isEmpty()) {
            //  UI.showToastShort(error.toString(), getActivity());
            UI.createSimpleCustomDialog("", error.toString(), getFragmentManager(),
                    new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {
                            if (error.toString().equals(Recursos.MESSAGE_OPEN_SESSION)) {
                                onEventListener.onEvent(EVENT_SESSION_EXPIRED, 1);
                            }
                            mySeekBar.setEnabled(true);
                            mySeekBar.setProgress(0);
                        }

                        @Override
                        public void actionCancel(Object... params) {

                        }
                    },
                    true, false);
        }
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
                getActivity().unregisterReceiver(this);
            }
        };

        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(DELIVERED));

        showLoader(getContext().getString(R.string.verificando_sms_espera));
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
        hideLoader();
        UI.createCustomDialog("", message, getFragmentManager(), getFragmentTag(), new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                counterRetry = 1;
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

    @Override
    public void onResume() {
        super.onResume();
        mySeekBar.setEnabled(true);
        mySeekBar.setProgress(0);
    }


}

