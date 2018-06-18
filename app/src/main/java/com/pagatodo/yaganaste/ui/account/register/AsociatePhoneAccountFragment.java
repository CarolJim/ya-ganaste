package com.pagatodo.yaganaste.ui.account.register;


import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.MessageValidation;
import com.pagatodo.yaganaste.data.model.RegisterUser;
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
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ly.count.android.sdk.Countly;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_ASSIGN_NEW_CONTRASE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_LOGIN;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_REGISTER_COMPLETE;
import static com.pagatodo.yaganaste.ui._controllers.DetailsActivity.MY_PERMISSIONS_REQUEST_SEND_SMS;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_ERROR;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity.EVENT_SESSION_EXPIRED;
import static com.pagatodo.yaganaste.utils.Recursos.CONNECTION_TYPE;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_APROV;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_LOG_IN;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_SPLASH;
import static com.pagatodo.yaganaste.utils.Recursos.PASSWORD_CHANGE;
import static com.pagatodo.yaganaste.utils.Recursos.USER_PROVISIONED;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class AsociatePhoneAccountFragment extends GenericFragment implements IVerificationSMSView,
        IAprovView, IResetNIPView {

    private static final String TAG = AsociatePhoneAccountFragment.class.getSimpleName();
    private static final long CHECK_SMS_VALIDATE_DELAY = 10000;
    private View view;
    @BindView(R.id.txt_send_sms)
    StyleTextView txtSendSms;
    @BindView(R.id.anim_send_sms)
    LottieAnimationView animSendSms;
    int counterRetry = 1;
    BroadcastReceiver broadcastReceiver;
    private WebService failed;
    private AccountPresenterNew accountPresenter;
    private Preferencias preferencias;
    private boolean showAnimation = false;
    App aplicacion;

    /**
     * BroadcastReceiver para realizar el envío del SMS
     **/
    BroadcastReceiver broadcastReceiverSend = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    UI.showSuccessSnackBar(getActivity(), "Mensaje Enviado", Snackbar.LENGTH_SHORT);
                    //animSendSms.pauseAnimation();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            accountPresenter.doPullActivationSMS(getString(R.string.verificando_sms_esperanuevo));
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
        aplicacion = new App();
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_associate_sms, container, false);
            initViews();
        }
        return view;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, view);
        animSendSms.setProgress(0.5f);
        txtSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isDeviceOnline()) {
                    continuePayment();
                } else {
                    UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_LONG);
                }
            }
        });
    }

    private void continuePayment() {
        int permissionSms = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.SEND_SMS);
        int permissionCall = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_PHONE_STATE);
        // Si no tenemos el permiso lo solicitamos, en cawso contrario entramos al proceso de envio del MSN
        if (permissionSms == -1 || permissionCall == -1) {
            ValidatePermissions.checkPermissions(getActivity(),
                    new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            onRequestPermissionsResult();
        }
    }

    public void onRequestPermissionsResult() {
        if (!BuildConfig.DEBUG) {
            Countly.sharedInstance().startEvent(EVENT_APROV);
        }
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
        //hideLoader();
        animSendSms.pauseAnimation();
        animSendSms.setProgress(0.5f);
        onEventListener.onEvent(EVENT_SHOW_ERROR, error);
    }


    public void finishAssociation() {
        /*if (SingletonUser.getInstance().needsReset()) {
            accountPresenter.doReseting(preferencias.loadData(SHA_256_FREJA));
        } else {*/
        if (!BuildConfig.DEBUG) {
            Map<String, String> segmentation = new HashMap<>();
            segmentation.put(CONNECTION_TYPE, Utils.getTypeConnection());
            Countly.sharedInstance().endEvent(EVENT_APROV, segmentation, 1, 0);
        }
        if (preferencias.loadDataBoolean(PASSWORD_CHANGE, false)) {
            nextScreen(EVENT_GO_REGISTER_COMPLETE, null);
        } else {
            nextScreen(EVENT_GO_ASSIGN_NEW_CONTRASE, null);
        }
        //}
    }

    @Override
    public void showErrorReset(ErrorObject error) {
        showErrorAprov(error);
    }

    @Override
    public void finishReseting() {
        if (!BuildConfig.DEBUG) {
            Map<String, String> segmentation = new HashMap<>();
            segmentation.put(CONNECTION_TYPE, Utils.getTypeConnection());
            Countly.sharedInstance().endEvent(EVENT_APROV, segmentation, 1, 0);
        }
        nextScreen(EVENT_GO_REGISTER_COMPLETE, null);
    }

    @Override
    public void onResetingFailed() {
        if (!BuildConfig.DEBUG) {
            Map<String, String> segmentation = new HashMap<>();
            segmentation.put(CONNECTION_TYPE, Utils.getTypeConnection());
            Countly.sharedInstance().endEvent(EVENT_APROV, segmentation, 1, 0);
        }
        nextScreen(EVENT_GO_REGISTER_COMPLETE, null);
    }


    @Override
    public void smsVerificationFailed(String message) {
        if (counterRetry < 4) {
            counterRetry++;
            showLoader(getString(R.string.verificando_sms_esperanuevo));
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    accountPresenter.doPullActivationSMS(getString(R.string.verificando_sms_esperanuevo));
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
        if (!showAnimation) {
            onEventListener.onEvent(EVENT_SHOW_LOADER, message);
        } else {
            showAnimation = false;
        }
    }

    @Override
    public void hideLoader() {
        txtSendSms.setVisibility(View.VISIBLE);
        animSendSms.pauseAnimation();
        animSendSms.setProgress(0.5f);
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void showError(final Object error) {
        if (error != null && !error.toString().isEmpty()) {


            UI.showAlertDialog(getActivity(), getResources().getString(R.string.app_name), error.toString(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (error.toString().equals(Recursos.MESSAGE_OPEN_SESSION)) {
                        onEventListener.onEvent(EVENT_SESSION_EXPIRED, 1);
                    }
                }
            });

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
                        //UI.showToastShort("SMS Entregado", getActivity());
                        UI.showSuccessSnackBar(getActivity(), "SMS entregado", Snackbar.LENGTH_SHORT);
                        break;
                    case Activity.RESULT_CANCELED:
                        //UI.showToastShort("SMS No Entregado", getActivity());
                        UI.showErrorSnackBar(getActivity(), "SMS no entregado", Snackbar.LENGTH_SHORT);
                        txtSendSms.setVisibility(View.VISIBLE);
                        break;
                }
                getActivity().unregisterReceiver(this);
            }
        };

        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(DELIVERED));

        //showLoader(getContext().getString(R.string.verificando_sms_esperanuevo));
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
        txtSendSms.setVisibility(View.INVISIBLE);
        animSendSms.playAnimation();
        showAnimation = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(broadcastReceiverSend);
            getActivity().unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goLoginAlert(String message) {
        hideLoader();
        if (!message.toString().isEmpty())
            UI.createCustomDialogSMS("", message, getFragmentManager(), getFragmentTag(), new DialogDoubleActions() {
                @Override
                public void actionConfirm(Object... params) {
                    counterRetry = 1;
                    continuePayment();
                }

                @Override
                public void actionCancel(Object... params) {
                    //No-Op
                    aplicacion.cerrarAppsms();
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
    }
}

