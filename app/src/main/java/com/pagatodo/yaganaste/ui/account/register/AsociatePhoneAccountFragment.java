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
import com.pagatodo.yaganaste.interfaces.IVerificationSMSView;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

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
public class AsociatePhoneAccountFragment extends GenericFragment implements View.OnClickListener,IVerificationSMSView {

    private static final String TAG = AsociatePhoneAccountFragment.class.getSimpleName();
    private static final long CHECK_SMS_VALIDATE_DELAY = 10000;
    private View rootview;
    @BindView(R.id.btnAssociateNext)
    StyleButton btnAssociateNext;
    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;

    int counterRetry = 1;

    Timer timer = new Timer();
    BroadcastReceiver broadcastReceiver;

    private AccountPresenterNew accountPresenter;
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
        //accountPresenter = new AccountPresenterNew(getActivity(),this);
        //RequestHeaders.setUsername("mailprueba200@mail.com");
        //RequestHeaders.setTokensesion("72186BF634808214F348AC75ED3CF8D66C7BF42A0403F1CE8E483F1307FA1148670195A73B082434C0537317459DBD8F");
        //RequestHeaders.setUsername("userd@mail.com");
        //RequestHeaders.setTokensesion("6137E51838FDBEA96161DD34B6426CAA99D5D958E8F213307B34F832E993E4A389A221A7D04A89FFE1E36509A3451CAC");
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
        btnAssociateNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAssociateNext:
                accountPresenter.gerNumberToSMS();
                //executeProvisioning();
                break;
            default:
                break;
        }
    }

    @Override
    public void smsVerificationSuccess() {
        //nextStepRegister(EVENT_GO_REGISTER_COMPLETE,null);
        executeProvisioning();
    }

    @Override
    public void provisingCompleted(String message) {

        UI.showToastShort(message,getActivity());
        nextStepRegister(EVENT_GO_REGISTER_COMPLETE,null);
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
        accountPresenter.getActivationCode();
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
        goToLogin(); // TODO Se sigue al siguiente paso solo para realizar pruebas.
        //executeProvisioning();
        //nextStepRegister(EVENT_GO_REGISTER_COMPLETE,null);
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
    public void nextStepRegister(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void backStepRegister(String event, Object data) {
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
            getActivity().unregisterReceiver( broadcastReceiverSend);
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
        nextStepRegister(EVENT_GO_LOGIN,null);
    }
}

