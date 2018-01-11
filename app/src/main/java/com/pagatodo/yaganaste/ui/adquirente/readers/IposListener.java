package com.pagatodo.yaganaste.ui.adquirente.readers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dspread.xpos.QPOSService.Display;
import com.dspread.xpos.QPOSService.DoTradeResult;
import com.dspread.xpos.QPOSService.Error;
import com.dspread.xpos.QPOSService.QPOSServiceListener;
import com.dspread.xpos.QPOSService.TransactionResult;
import com.dspread.xpos.QPOSService.UpdateInformationResult;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.EmvData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ImplicitData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.SwipeData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;

public class IposListener implements QPOSServiceListener {
    private Context context;

    public IposListener(Context context) {
        this.context = context;
    }

    @Override
    public void onRequestWaitingUser() {
        enviaMensaje(Recursos.ENCENDIDO);
        Log.i("IposListener: ", "------ onRequestWaitingUser ");
    }

    @Override
    public void onDoTradeResult(DoTradeResult result, Hashtable<String, String> decodeData) {
        Log.i("IposListener: ", "------ onDoTradeResult " + result.name());

        if (result == DoTradeResult.MCR) {
            Log.i("IposListener: ", "------Card Swiped");
            TransaccionEMVDepositRequest cardData = new TransaccionEMVDepositRequest();
            cardData.setEmvData(new EmvData());
            cardData.setImplicitData(new ImplicitData());
            cardData.setSwipeData(new SwipeData());
            //cardData.noSerie=prefs.loadData("SerialNumber");
            cardData.setIsEMVTransaction(false);
            cardData.cardHolderName = (decodeData.get("cardholderName"));
            cardData.maskedPAN = (decodeData.get("maskedPAN"));
            cardData.getSwipeData().setEncCardData(decodeData.get("encTrack1") + "|" + decodeData.get("encTrack2"));
            cardData.getSwipeData().setKsn(decodeData.get("trackksn"));
            cardData.getSwipeData().setTrack1Length(decodeData.get("track1Length"));
            cardData.getSwipeData().setTrack2Length(decodeData.get("track2Length"));
            cardData.getSwipeData().setTrack3Length(decodeData.get("track3Length"));
            cardData.getSwipeData().setCvv(null);
            enviaMensaje(Recursos.LECTURA_OK, cardData, null);
        } else if (result == DoTradeResult.ICC) {
            enviaMensaje(Recursos.LEYENDO);
            Log.i("IposListener: ", "------ICC Card Inserted.");
        } else if (result != DoTradeResult.BAD_SWIPE) {
            enviaMensaje(Recursos.SW_ERROR, null, context.getString(R.string.input_invalid));
            Log.i("IposListener: ", "------Bad Swipe. Please swipe again and press check card.");
        }
    }


    @Override
    public void onRequestTransactionResult(TransactionResult transactionResult) {
        Log.i("IposListener: ", "------onRequestTransactionResult");
        if (transactionResult != TransactionResult.APPROVED) {
            enviaMensaje(Recursos.SW_ERROR, null, context.getString(R.string.error_de_lectura));
        }
    }

    @Override
    public void onRequestBatchData(String tlv) {
        Log.d("IposListener: ", "------onRequestBatchData");
    }

    @Override
    public void onRequestTransactionLog(String tlv) {
        Log.i("IposListener: ", "------onRequestTransactionLog");

    }

    @Override
    public void onRequestSelectEmvApp(ArrayList<String> appList) {
        Log.d("IposListener: ", "------onRequestSelectEmvApp");
        String[] appNameList = new String[appList.size()];
        for (int i = 0; i < appNameList.length; ++i) {
            appNameList[i] = appList.get(i);
        }
        enviaMensaje(appNameList);
    }

    @Override
    public void onRequestSetAmount() {
        Log.i("IposListener: ", "------onRequestSetAmount");
        enviaMensaje(Recursos.REQUEST_AMOUNT);
    }


    @Override
    public void onRequestIsServerConnected() {
        Log.i("IposListener: ", "------Please insert/swipe/tap card now.");
        enviaMensaje(Recursos.REQUEST_IS_SERVER_CONNECTED);
    }

    @Override
    public void onRequestOnlineProcess(String tlv) {
        Log.i("IposListener: ", "------onRequestOnlineProcess");
        TransaccionEMVDepositRequest transaction = new TransaccionEMVDepositRequest();
        //transaction.noSerie = prefs.loadData("SerialNumber");
        transaction.setNoTransaction(Integer.toString(Utils.getTransactionSequence()));
        transaction.getEmvData().setTlv(tlv);
        transaction.setIsEMVTransaction(true);
        enviaMensaje(Recursos.LECTURA_OK, transaction, null);
    }

    @Override
    public void onRequestTime() {
        Log.i("IposListener: ", "------onRequestTime");
        enviaMensaje(Recursos.REQUEST_TIME);
    }

    @Override
    public void onRequestDisplay(Display displayMsg) {
        Log.i("IposListener: ", "------onRequestDisplay");
        Log.e("onRequestDisplay", displayMsg.name());
    }


    @Override
    public void onRequestFinalConfirm() {
        Log.i("IposListener: ", "------onRequestFinalConfirm");
        enviaMensaje(Recursos.REQUEST_FINAL_CONFIRM);
    }


    @Override
    public void onRequestSetPin() {
        Log.i("IposListener: ", "------onRequestSetPin");
        enviaMensaje(Recursos.REQUEST_PIN);
    }


    @Override
    public void onReturnReversalData(String tlv) {
        Log.i("OnReturnReversalDAta: ", "------ " + tlv);
    }

    @Override
    public void onReturnApduResult(boolean arg0, String arg1, int arg2) {
    }

    @Override
    public void onReturnPowerOffIccResult(boolean arg0) {
    }

    @Override
    public void onReturnPowerOnIccResult(boolean arg0, String arg1, String arg2, int arg3) {
    }

    @Override
    public void onReturnCustomConfigResult(boolean isSuccess, String result) {
    }

    @Override
    public void onGetCardNoResult(String arg0) {
    }

    @Override
    public void onQposIdResult(Hashtable<String, String> posIdTable) {
        Log.i("IposListener: ", "------ onQposIdResult");
        Log.e("--------GORRO", "onQposIdResult: " + posIdTable.toString());
        TransaccionEMVDepositRequest cardData = new TransaccionEMVDepositRequest();
        cardData.setNoSerie(posIdTable.get("posId"));
        //prefs.saveData("SerialNumber", cardData.noSerie);
        enviaMensaje(Recursos.READ_KSN, cardData, null);
    }

    @Override
    public void onQposInfoResult(Hashtable<String, String> posInfoData) {
        Log.i("IposListener: ", "------ onQposInfoResult");
        int batteryLevel = 0;
        String batteryporcentage="0";

        if (posInfoData.get("batteryLevel") != null) {
            batteryLevel = Integer.parseInt(posInfoData.get("batteryLevel").split(" ")[0]);
        }
        if (posInfoData.get("batteryPercentage") != null) {
            batteryporcentage = posInfoData.get("batteryPercentage").split("%")[0];
        }


        boolean isCharging = posInfoData.get("isCharging") == null ? false : Boolean.parseBoolean(posInfoData.get("isCharging"));
        sendBateryLevel(Recursos.READ_BATTERY_LEVEL, batteryLevel, isCharging,batteryporcentage);
        Log.i("IposListener: Bataca", "Bataca"+batteryLevel);
    }

    @Override
    public void onRequestCalculateMac(String arg0) {
    }

    @Override
    public void onRequestNoQposDetected() {
        //enviaMensaje(Recursos.DESCONECTADO);
    }

    @Override
    public void onError(Error errorState) {
        Log.i("IposListener: ", "------ errorState  " + errorState.name());
        Log.e("onErrorValidateService", errorState.name());
        if (errorState.name().equals("TIMEOUT") || errorState.name().equals("CMD_TIMEOUT")) {
            enviaMensaje(Recursos.SW_TIMEOUT);
        } else {
            enviaMensaje(Recursos.READ_KSN_ERROR, null, context.getString(R.string.input_invalid));
            enviaMensaje(Recursos.SW_ERROR, null, context.getString(R.string.input_invalid));

        }

    }

    @Override
    public void onRequestQposConnected() {
        enviaMensaje(Recursos.ENCENDIDO);
    }

    @Override
    public void onRequestQposDisconnected() {
        enviaMensaje(Recursos.DESCONECTADO);
    }

    @Override
    public void onRequestSignatureResult(byte[] arg0) {
    }

    @Override
    public void onRequestUpdateWorkKeyResult(UpdateInformationResult arg0) {
    }

    @Override
    public void onReturnGetPinResult(Hashtable<String, String> arg0) {
    }

    @Override
    public void onReturnSetSleepTimeResult(boolean arg0) {
    }

    @Override
    public void onReturnSetMasterKeyResult(boolean isSuccess) {
    }

    @Override
    public void onReturnBatchSendAPDUResult(
            LinkedHashMap<Integer, String> batchAPDUResult) {
    }

    @Override
    public void onBluetoothBondFailed() {
    }

    @Override
    public void onBluetoothBondTimeout() {
    }

    @Override
    public void onBluetoothBonded() {
    }

    @Override
    public void onBluetoothBonding() {
    }

    @Override
    public void onReturniccCashBack(Hashtable<String, String> arg0) {
    }

    @Override
    public void onLcdShowCustomDisplay(boolean arg0) {
    }

    @Override
    public void onUpdatePosFirmwareResult(UpdateInformationResult arg0) {
    }

    @Override
    public void onReturnDownloadRsaPublicKey(HashMap<String, String> arg0) {
    }

    @Override
    public void onGetPosComm(int arg0, String arg1, String arg2) {
    }

    @Override
    public void onPinKey_TDES_Result(String arg0) {
    }

    @Override
    public void onUpdateMasterKeyResult(boolean arg0, Hashtable<String, String> arg1) {
    }

    private void sendBateryLevel(int mensaje, int batery, boolean isCharging,String batteryPorcentage) {
        Log.i("IposListener: ", "------ sendBateryLevel");
        Intent intent = new Intent(Recursos.IPOS_READER_STATES);
        intent.putExtra(Recursos.MSJ, mensaje);
        intent.putExtra(Recursos.BATTERY_LEVEL, batery);
        intent.putExtra(Recursos.READER_IS_CHARGING, isCharging);
        intent.putExtra(Recursos.BATTERY_PORCENTAGE, batteryPorcentage);

        context.sendBroadcast(intent);
    }

    private void enviaMensaje(int mensaje) {
        enviaMensaje(mensaje, null, null);

    }

    private void enviaMensaje(int mensaje, TransaccionEMVDepositRequest transacion, String error) {
        Log.i("IposListener: ", "------ enviaMensaje " + error);
        Intent intent = new Intent(Recursos.IPOS_READER_STATES);
        intent.putExtra(Recursos.MSJ, mensaje);
        if (transacion != null) intent.putExtra(Recursos.TRANSACTION, transacion);
        if (error != null) intent.putExtra(Recursos.ERROR, error);
        context.sendBroadcast(intent);
    }

    private void enviaMensaje(String[] appList) {
        Log.i("IposListener: ", "------ enviaMensaje List");
        Intent intent = new Intent(Recursos.IPOS_READER_STATES);
        intent.putExtra(Recursos.MSJ, Recursos.REQUEST_SELECT_APP);
        intent.putExtra(Recursos.APP_LIST, appList);
        context.sendBroadcast(intent);
    }

    @Override
    public void onCbcMacResult(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onConfirmAmountResult(boolean arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onEmvICCExceptionData(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGetInputAmountResult(boolean arg0, String arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onReadBusinessCardResult(boolean arg0, String arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onReturnNFCApduResult(boolean arg0, String arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onReturnPowerOffNFCResult(boolean arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onReturnPowerOnNFCResult(boolean arg0, String arg1,
                                         String arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSetParamsResult(boolean arg0, Hashtable<String, Object> arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onWriteBusinessCardResult(boolean arg0) {
        // TODO Auto-generated method stub

    }
}