package com.pagatodo.yaganaste.ui.adquirente;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;
import com.pagatodo.yaganaste.interfaces.IAdqTransactionRegisterView;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.customviews.CustomKeyboardView;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.AUDIO_SERVICE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.utils.Recursos.ENCENDIDO;
import static com.pagatodo.yaganaste.utils.Recursos.ERROR;
import static com.pagatodo.yaganaste.utils.Recursos.ERROR_LECTOR;
import static com.pagatodo.yaganaste.utils.Recursos.LECTURA_OK;
import static com.pagatodo.yaganaste.utils.Recursos.LEYENDO;
import static com.pagatodo.yaganaste.utils.Recursos.MSJ;
import static com.pagatodo.yaganaste.utils.Recursos.READ_BATTERY_LEVEL;
import static com.pagatodo.yaganaste.utils.Recursos.READ_KSN;
import static com.pagatodo.yaganaste.utils.Recursos.REQUEST_AMOUNT;
import static com.pagatodo.yaganaste.utils.Recursos.REQUEST_FINAL_CONFIRM;
import static com.pagatodo.yaganaste.utils.Recursos.REQUEST_IS_SERVER_CONNECTED;
import static com.pagatodo.yaganaste.utils.Recursos.REQUEST_PIN;
import static com.pagatodo.yaganaste.utils.Recursos.REQUEST_TIME;
import static com.pagatodo.yaganaste.utils.Recursos.SW_ERROR;
import static com.pagatodo.yaganaste.utils.Recursos.SW_TIMEOUT;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class InsertDongleFragment extends GenericFragment implements View.OnClickListener,IAdqTransactionRegisterView {

    private static final String TAG = InsertDongleFragment.class.getSimpleName();
    private View rootview;
    @BindView(R.id.imgInsertDongle)
    ImageView imgInsertDongle;
    @BindView(R.id.imgInsertCard)
    ImageView imgInsertCard;
    @BindView(R.id.tv_txt_lector)
    StyleTextView tv_lector;
    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;

    private AudioManager audioManager;
    private Handler handlerSwipe;
    public Preferencias prefs;
    private int currentVolumenDevice;
    private int maxVolumenDevice;

    protected boolean isReaderConected = false;
    private IntentFilter broadcastEMVSwipe;

    private String amount = "";
    private String detailAmount = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = App.getInstance().getPrefs();
        audioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
        currentVolumenDevice = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        broadcastEMVSwipe = new IntentFilter(Recursos.IPOS_READER_STATES);
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        getActivity().registerReceiver(headPhonesReceiver, filter);
        handlerSwipe = new Handler();

    }

    private BroadcastReceiver headPhonesReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        isReaderConected = false;
                        //validatingDng = false; // Cancelar Validacion
                        unregisterReceiverDongle();
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
                        showInsertDongle();
                        Log.i("IposListener: ","isReaderConected  false");
                        break;
                    case 1:

                        maxVolumenDevice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolumenDevice, 0);
                        isReaderConected = true;
                        //validatingDng = false; // Cancelar Validacion
                        //insertCard();
                        handlerSwipe.postDelayed(starReaderEmvSwipe, 500);
                        Log.i("IposListener: ","isReaderConected  true");
                        break;
                    default:
                        break;
                }
            }
        }
    };

    private Runnable starReaderEmvSwipe = new Runnable() {
        @Override
        public void run() {
            if (isReaderConected) {
                Log.i("IposListener: ","=====>>   starReaderEmvSwipe ");
                getActivity().registerReceiver(emvSwipeBroadcastReceiver, broadcastEMVSwipe);
                App.getInstance().pos.openAudio();
                App.getInstance().pos.getQposId();
                //initListenerDongle();
                //initListenerDongle();
                /*if(!prefs.contains(Recursos.KSN_LECTOR)) {
                    App.getInstance().pos.getQposId();
                    App.getInstance().pos.getQposInfo();
                    showProgress(getResources().getString(R.string.initlector));
                }else{
                    App.getInstance().pos.doTradeNoPinpad(30);
                    showProgress(getResources().getString(R.string.readcard));
                }*/

                //App.getInstance().pos.getQposId();
                //App.getInstance().pos.getQposInfo();
                showLoader(getResources().getString(R.string.validatelector));
            }
        }
    };

    private BroadcastReceiver emvSwipeBroadcastReceiver = new BroadcastReceiver() {
        @SuppressLint("SimpleDateFormat")
        @Override
        public void onReceive(Context context, Intent intent) {

            int mensaje = intent.getIntExtra(MSJ, -1);
            String error = intent.getStringExtra(ERROR);
            //Procesar lectura mientras no se este validando Dongle
            //if(!validatingDng){

                switch (mensaje) {
                    case LECTURA_OK:
                        Log.i("IposListener: ","=====>>  LECTURA OK");
                        /*if(isWaitingCard){
                            Log.i("IposListener: ","=====>>   LECTURA_OK ");
                            requestTransaction = new TransaccionEMVDepositRequest();
                            requestTransaction = (RequestTransactionEMVSwipe) intent.getSerializableExtra(Recursos.TRANSACTION);
                            //doTransactions();
                            initTransaction();
                            //closeProgress();
                        }*/
                        break;
                    case READ_KSN:
                        TransaccionEMVDepositRequest transactionKsn = (TransaccionEMVDepositRequest) intent.getSerializableExtra(Recursos.TRANSACTION);
                        //ksn = transactionKsn.noSerie;
                        Log.i("IposListener: ","=====>>  READ_KSN  "+transactionKsn.getNoSerie());
                        if(transactionKsn.getNoSerie().length() > 10) {
                            prefs.saveData(Recursos.KSN_LECTOR, transactionKsn.getNoSerie());
                            Log.i("IposListener: ","=====>>  saveData");
                        }

                        //validatingDng = true;
                        /*if(!isAgentValidated)
                            validateAdq();// Validamos Adq para poder validar el dongle.
                        else
                            validateDongle(ksn);//Adq validado, validamos el dongle.*/
                        //App.getInstance().pos.doTradeNoPinpad(30);
                        break;
                    case READ_BATTERY_LEVEL:
                        int batteryLevel = intent.getIntExtra(Recursos.BATTERY_LEVEL, 0);
                        Log.i("IposListener: ","=====>>    batteryLevel "+batteryLevel);
                        App.getInstance().pos.getQposId();
                        Log.i("IposListener: ","=====>>    Obteniendo ksn ");
                        break;
                    case ERROR_LECTOR:
                        Log.i("IposListener: ","=====>>    ERROR_LECTOR");
                        hideLoader();
                        //closeProgress();
                        break;
                    case LEYENDO:
                        Log.i("IposListener: ","=====>>    LEYENDO");
                        App.getInstance().pos.doEmvApp(QPOSService.EmvOption.START);
                        showLoader(getResources().getString(R.string.readcard));
                        break;
                    case REQUEST_AMOUNT:
                        Log.i("IposListener: ","=====>>    REQUEST_AMOUNT");
                        App.getInstance().pos.setAmount(amount.replace(".",""), "", "484", QPOSService.TransactionType.PAYMENT);
                        break;
                    case REQUEST_TIME:
                        String terminalTime = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                        App.getInstance().pos.sendTime(terminalTime);
                        Log.i("IposListener: ","=====>>    REQUEST_TIME");
                        break;
                    case REQUEST_IS_SERVER_CONNECTED:
                        App.getInstance().pos.isServerConnected(true);
                        Log.i("IposListener: ","=====>>    REQUEST_IS_SERVER_CONNECTED");
                        break;
                    case REQUEST_FINAL_CONFIRM:
                        App.getInstance().pos.finalConfirm(true);
                        Log.i("IposListener: ","=====>>    REQUEST_FINAL_CONFIRM");
                        break;
                    case REQUEST_PIN:
                        App.getInstance().pos.bypassPin();
                        Log.i("IposListener: ","=====>>    REQUEST_PIN");
                        break;
                    case SW_TIMEOUT:
                        initListenerDongle();
                        hideLoader();
                        Toast.makeText(getActivity(), "Vuelve a conectar el Lector.", Toast.LENGTH_SHORT).show();
                        showInsertDongle();
                        Log.i("IposListener: ","=====>>    SW_TIMEOUT");
                        break;
                    case SW_ERROR:
                        Log.i("IposListener: ","=====>>    SW_ERROR");
                        //closeProgress();
                        hideLoader();
                       // if(!isWaitingCard)
                          //  resetReceiverDongle();
                        //else {
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getActivity(), "Vuelve a conectar el Lector.", Toast.LENGTH_SHORT).show();
                           showInsertDongle();
                        //}
                        break;
                    case ENCENDIDO:
                        Log.i("IposListener: ","=====>>    ENCENDIDO");
                        //closeProgress();
                        hideLoader();
                        //readDongle();
                        break;
                    default:
                        Log.i("IposListener: ","=====>>    default");
                        //App.getInstance().pos.doTradeNoPinpad(30);
                        //closeProgress();
                        hideLoader();
                        break;
                }

           // }
        }
    };


    /****/

    private void readDongle(){

        getKSN();
    }

    public void getKSN() {
        App.getInstance().pos.getQposId();
    }

    public InsertDongleFragment() {
    }

    public static InsertDongleFragment newInstance() {
        InsertDongleFragment fragmentRegister = new InsertDongleFragment();
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

        rootview = inflater.inflate(R.layout.fragment_insert_dongle, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
    }

    @Override
    public void onResume() {
        super.onResume();
        App.getInstance().pos.openAudio();
        maxVolumenDevice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolumenDevice, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        App.getInstance().pos.closeAudio();
    }

    @Override
    public void onDestroy() {
        unregisterReceiverDongle();
        unregisterReceiverHeadPhone();
        super.onDestroy();
    }


    public void unregisterReceiverDongle(){
        try{
            getActivity().unregisterReceiver(emvSwipeBroadcastReceiver); // Desregistramos receiver
        }catch (IllegalArgumentException ex){
            Log.e(TAG,"emvSwipeBroadcastReceiver no registrado. Ex- " + ex.toString());
        }
    }

    private void unregisterReceiverHeadPhone(){
        try{
            getActivity().unregisterReceiver(headPhonesReceiver);
        }catch (IllegalArgumentException ex){
            Log.e(TAG,"emvSwipeBroadcastReceiver no registrado. Ex- " + ex.toString());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            default:
                break;
        }
    }

    @Override
    public void showInsertDongle() {
        imgInsertDongle.setVisibility(VISIBLE);
        imgInsertCard.setVisibility(View.INVISIBLE);
        tv_lector.setVisibility(VISIBLE);
    }

    @Override
    public void showInsertCard() {
        imgInsertDongle.setVisibility(View.INVISIBLE);
        imgInsertCard.setVisibility(VISIBLE);
        tv_lector.setVisibility(View.INVISIBLE);
    }

    @Override
    public void transactionSuccess() {

    }

    @Override
    public void transactionFailed() {

    }

    @Override
    public void nextStepRegister(String event, Object data) {

    }

    @Override
    public void backStepRegister(String event, Object data) {

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

    }

    public void initListenerDongle(){
        App.getInstance().pos.doTradeNoPinpad(30);
    }
}
