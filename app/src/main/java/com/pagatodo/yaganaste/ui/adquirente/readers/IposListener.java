package com.pagatodo.yaganaste.ui.adquirente.readers;

import android.bluetooth.BluetoothDevice;
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
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
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
        //enviaMensaje(Recursos.ENCENDIDO);
        Log.i("IposListener: ", "------ onRequestWaitingUser ");
    }

    @Override
    public void onDoTradeResult(DoTradeResult result, Hashtable<String, String> decodeData) {
        Log.i("IposListener: ", "------ onDoTradeResult " + result.name());

        if (result == DoTradeResult.MCR) {
            Log.i("IposListener: ", "------Card Swiped");
            TransactionAdqData.getCurrentTransaction().setSwipedCard(true);
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
    public void onFinishMifareCardResult(boolean b) {
        Log.i("IposListener: ", "------ onFinishMifareCardResult");
    }

    @Override
    public void onVerifyMifareCardResult(boolean b) {
        Log.i("IposListener: ", "------ onVerifyMifareCardResult");
    }

    @Override
    public void onReadMifareCardResult(Hashtable<String, String> hashtable) {
        Log.i("IposListener: ", "------ onReadMifareCardResult");
    }

    @Override
    public void onWriteMifareCardResult(boolean b) {
        Log.i("IposListener: ", "------ onWriteMifareCardResult");
    }

    @Override
    public void onOperateMifareCardResult(Hashtable<String, String> hashtable) {
        Log.i("IposListener: ", "------ onOperateMifareCardResult");
    }

    @Override
    public void getMifareCardVersion(Hashtable<String, String> hashtable) {
        Log.i("IposListener: ", "------ getMifareCardVersion");
    }

    @Override
    public void getMifareReadData(Hashtable<String, String> hashtable) {
        Log.i("IposListener: ", "------ getMifareReadData");
    }

    @Override
    public void getMifareFastReadData(Hashtable<String, String> hashtable) {
        Log.i("IposListener: ", "------ getMifareFastReadData");
    }

    @Override
    public void writeMifareULData(String s) {
        Log.i("IposListener: ", "------ writeMifareULData");
    }

    @Override
    public void verifyMifareULData(Hashtable<String, String> hashtable) {
        Log.i("IposListener: ", "------ verifyMifareULData");
    }

    @Override
    public void transferMifareData(String s) {
        Log.i("IposListener: ", "------ transferMifareData");
    }


    @Override
    public void onRequestTransactionResult(TransactionResult transactionResult) {
        Log.i("IposListener: ", "------onRequestTransactionResult: " + transactionResult.name());
        if (transactionResult == TransactionResult.APPROVED) {
            enviaMensaje(Recursos.ONLINE_PROCESS_SUCCESS, null, context.getString(R.string.success_request_online_emv));
        } else if (transactionResult == TransactionResult.DECLINED) {
            enviaMensaje(Recursos.ONLINE_PROCESS_FAILED, null, context.getString(R.string.error_de_lectura));
        } else if (transactionResult == TransactionResult.TERMINATED) {
            enviaMensaje(Recursos.ONLINE_PROCESS_FAILED, null, context.getString(R.string.error_de_tarjeta));
        } else if (transactionResult == TransactionResult.CANCEL) {
            enviaMensaje(Recursos.SW_ERROR, null, context.getString(R.string.error_de_transaccion));
        }
    }

    @Override
    public void onRequestBatchData(String tlv) {
        Log.d("IposListener: ", "------onRequestBatchData\nTLV: " + tlv);
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
        if (displayMsg.ordinal() == Display.INPUT_PIN_ING.ordinal()) {
            enviaMensaje(Recursos.REQUEST_PIN);
        }
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
        Log.i("IposListener: ", "------ onReturnApduResult");
    }

    @Override
    public void onReturnPowerOffIccResult(boolean arg0) {
        Log.i("IposListener: ", "------ onReturnPowerOffIccResult");
    }

    @Override
    public void onReturnPowerOnIccResult(boolean arg0, String arg1, String arg2, int arg3) {
        Log.i("IposListener: ", "------ onReturnPowerOnIccResult");
    }

    @Override
    public void onReturnCustomConfigResult(boolean isSuccess, String result) {
        Log.i("IposListener: ", "------ onReturnCustomConfigResult");
    }

    @Override
    public void onGetCardNoResult(String arg0) {
        Log.i("IposListener: ", "------ onGetCardNoResult");
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
    public void onQposKsnResult(Hashtable<String, String> hashtable) {
        Log.i("IposListener: ", "------ onQposKsnResult");
    }

    @Override
    public void onQposIsCardExist(boolean b) {
        Log.i("IposListener: ", "------ onQposIsCardExist");
    }

    @Override
    public void onRequestDeviceScanFinished() {
        Log.i("IposListener: ", "------ onRequestDeviceScanFinished");
        enviaMensaje(Recursos.SW_TIMEOUT);
    }

    @Override
    public void onQposInfoResult(Hashtable<String, String> posInfoData) {
        Log.i("IposListener: ", "------ onQposInfoResult");
        int batteryLevel = 0;
        String batteryporcentage = "0";

        if (posInfoData.get("batteryLevel") != null) {
            batteryLevel = Integer.parseInt(posInfoData.get("batteryLevel").split(" ")[0]);
        }
        if (posInfoData.get("batteryPercentage") != null) {
            batteryporcentage = posInfoData.get("batteryPercentage").split("%")[0];
        }


        boolean isCharging = posInfoData.get("isCharging") == null ? false : Boolean.parseBoolean(posInfoData.get("isCharging"));
        sendBateryLevel(batteryLevel, isCharging, batteryporcentage);
        Log.i("IposListener: Bataca", "Bataca" + batteryLevel);
    }

    @Override
    public void onQposGenerateSessionKeysResult(Hashtable<String, String> hashtable) {
        Log.i("IposListener: ", "------ onQposGenerateSessionKeysResult");
    }

    @Override
    public void onQposDoSetRsaPublicKey(boolean b) {
        Log.i("IposListener: ", "------ onQposDoSetRsaPublicKey");
    }

    @Override
    public void onSearchMifareCardResult(Hashtable<String, String> hashtable) {
        Log.i("IposListener: ", "------ onSearchMifareCardResult");
    }

    @Override
    public void onRequestCalculateMac(String arg0) {
        Log.i("IposListener: ", "------ onRequestCalculateMac");
    }

    @Override
    public void onRequestNoQposDetected() {
        Log.i("IposListener: ", "------ onRequestNoQposDetected");
        enviaMensaje(Recursos.SW_TIMEOUT);
    }

    @Override
    public void onError(Error errorState) {
        Log.i("IposListener: ", "------ errorState  " + errorState.name());
        Log.e("onErrorValidateService", errorState.name());
        if (errorState.name().equals("WS_TIMEOUT") || errorState.name().equals("CMD_TIMEOUT")) {
            enviaMensaje(Recursos.SW_TIMEOUT);
        } else if (errorState.equals("DEVICE_BUSY")) {
            enviaMensaje(Recursos.SW_DEVICE_BUSY);
        } else {
            enviaMensaje(Recursos.READ_KSN_ERROR, null, context.getString(R.string.input_invalid));
            enviaMensaje(Recursos.SW_ERROR, null, context.getString(R.string.input_invalid));

        }

    }

    @Override
    public void onRequestQposConnected() {
        Log.i("IposListener: ", "------ onRequestQposConnected");
        enviaMensaje(Recursos.ENCENDIDO);
    }

    @Override
    public void onRequestQposDisconnected() {
        Log.i("IposListener: ", "------ onRequestQposDisconnected");
        enviaMensaje(Recursos.DESCONECTADO, null, context.getString(R.string.error_de_lector_comm));
    }

    @Override
    public void onRequestSignatureResult(byte[] arg0) {
        Log.i("IposListener: ", "------ onRequestSignatureResult");
    }

    @Override
    public void onRequestUpdateWorkKeyResult(UpdateInformationResult arg0) {
        Log.i("IposListener: ", "------ onRequestUpdateWorkKeyResult");
    }

    @Override
    public void onReturnGetPinResult(Hashtable<String, String> arg0) {
        Log.i("IposListener: ", "------ onReturnGetPinResult");
    }

    @Override
    public void onReturnSetSleepTimeResult(boolean arg0) {
        Log.i("IposListener: ", "------ onReturnSetSleepTimeResult");
    }

    @Override
    public void onReturnSetMasterKeyResult(boolean isSuccess) {
        Log.i("IposListener: ", "------ onReturnSetMasterKeyResult");
    }

    @Override
    public void onRequestUpdateKey(String s) {
        Log.i("IposListener: ", "------ onRequestUpdateKey");
    }

    @Override
    public void onReturnUpdateIPEKResult(boolean b) {
        Log.i("IposListener: ", "------ onReturnUpdateIPEKResult");
    }

    @Override
    public void onReturnUpdateEMVResult(boolean b) {
        Log.i("IposListener: ", "------ onReturnUpdateEMVResult");
        if (b) {
            enviaMensaje(Recursos.CONFIG_READER_OK);
        } else {
            enviaMensaje(Recursos.CONFIG_READER_OK_ERROR, null, context.getString(R.string.input_invalid));
        }
    }

    @Override
    public void onReturnGetQuickEmvResult(boolean b) {
        Log.i("IposListener: ", "------ onReturnGetQuickEmvResult");
    }

    @Override
    public void onReturnGetEMVListResult(String s) {
        Log.i("IposListener: ", "------ onReturnGetEMVListResult");
    }

    @Override
    public void onReturnUpdateEMVRIDResult(boolean b) {
        Log.i("IposListener: ", "------ onReturnUpdateEMVRIDResult");
    }

    @Override
    public void onDeviceFound(BluetoothDevice bluetoothDevice) {
        Log.i("IposListener: ", "------ onDeviceFound");
        if (bluetoothDevice.getName() != null && !bluetoothDevice.getName().equals("")) {
            Intent intent = new Intent(Recursos.IPOS_READER_STATES);
            intent.putExtra(Recursos.MSJ, Recursos.EMV_DETECTED);
            context.sendBroadcast(intent);
        }
    }

    @Override
    public void onReturnBatchSendAPDUResult(
            LinkedHashMap<Integer, String> batchAPDUResult) {
        Log.i("IposListener: ", "------ onReturnBatchSendAPDUResult");
    }

    @Override
    public void onBluetoothBondFailed() {
        Log.i("IposListener: ", "------ onBluetoothBondFailed");
    }

    @Override
    public void onBluetoothBondTimeout() {
        Log.i("IposListener: ", "------ onBluetoothBondTimeout");
    }

    @Override
    public void onBluetoothBonded() {
        Log.i("IposListener: ", "------ onBluetoothBonded");
    }

    @Override
    public void onWaitingforData(String s) {
        Log.i("IposListener: ", "------ onWaitingforData");
    }

    @Override
    public void onBluetoothBonding() {
        Log.i("IposListener: ", "------ onBluetoothBonding");
    }

    @Override
    public void onReturniccCashBack(Hashtable<String, String> arg0) {
        Log.i("IposListener: ", "------ onReturniccCashBack");
    }

    @Override
    public void onLcdShowCustomDisplay(boolean arg0) {
        Log.i("IposListener: ", "------ onLcdShowCustomDisplay");
    }

    @Override
    public void onUpdatePosFirmwareResult(UpdateInformationResult arg0) {
        Log.i("IposListener: ", "------ onUpdatePosFirmwareResult");
    }

    @Override
    public void onBluetoothBoardStateResult(boolean b) {
        Log.i("IposListener: ", "------ onBluetoothBoardStateResult");
    }

    @Override
    public void onReturnDownloadRsaPublicKey(HashMap<String, String> arg0) {
        Log.i("IposListener: ", "------ onReturnDownloadRsaPublicKey");
    }

    @Override
    public void onGetPosComm(int arg0, String arg1, String arg2) {
        Log.i("IposListener: ", "------ onGetPosComm");
    }

    @Override
    public void onPinKey_TDES_Result(String arg0) {
        Log.i("IposListener: ", "------ onPinKey_TDES_Result");
    }

    @Override
    public void onUpdateMasterKeyResult(boolean arg0, Hashtable<String, String> arg1) {
        Log.i("IposListener: ", "------ onUpdateMasterKeyResult");
    }

    private void sendBateryLevel(int batery, boolean isCharging, String batteryPorcentage) {
        Log.i("IposListener: ", "------ sendBateryLevel");
        Intent intent = new Intent(Recursos.IPOS_READER_STATES);
        intent.putExtra(Recursos.MSJ, Recursos.READ_BATTERY_LEVEL);
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
        Log.i("IposListener: ", "------ onCbcMacResult");
    }

    @Override
    public void onConfirmAmountResult(boolean arg0) {
        Log.i("IposListener: ", "------ onConfirmAmountResult");
    }

    @Override
    public void onEmvICCExceptionData(String arg0) {
        Log.i("IposListener: ", "------ onConfirmAmountResult");
    }

    @Override
    public void onGetInputAmountResult(boolean arg0, String arg1) {
        Log.i("IposListener: ", "------ onGetInputAmountResult");
    }

    @Override
    public void onReadBusinessCardResult(boolean arg0, String arg1) {
        Log.i("IposListener: ", "------ onReadBusinessCardResult");
    }

    @Override
    public void onReturnNFCApduResult(boolean arg0, String arg1, int arg2) {
        Log.i("IposListener: ", "------ onReturnNFCApduResult");
    }

    @Override
    public void onReturnPowerOffNFCResult(boolean arg0) {
        Log.i("IposListener: ", "------ onReturnPowerOffNFCResult");
    }

    @Override
    public void onReturnPowerOnNFCResult(boolean arg0, String arg1,
                                         String arg2, int arg3) {
        Log.i("IposListener: ", "------ onReturnPowerOnNFCResult");
    }

    @Override
    public void onSetParamsResult(boolean arg0, Hashtable<String, Object> arg1) {
        Log.i("IposListener: ", "------ onSetParamsResult");
    }

    @Override
    public void onWriteBusinessCardResult(boolean arg0) {
        Log.i("IposListener: ", "------ onWriteBusinessCardResult");
    }

    @Override
    public void onSetBuzzerResult(boolean arg0) {
        Log.i("IposListener: ", "------ onSetBuzzerResult");
    }

    @Override
    public void onQposDoTradeLog(boolean b) {
        Log.i("IposListener: ", "------ onQposDoTradeLog");
    }

    @Override
    public void onQposDoGetTradeLogNum(String s) {
        Log.i("IposListener: ", "------ onQposDoGetTradeLogNum");
    }

    @Override
    public void onQposDoGetTradeLog(String s, String s1) {
        Log.i("IposListener: ", "------ onQposDoGetTradeLog");
    }

    @Override
    public void onSetSleepModeTime(boolean arg0) {
        Log.i("IposListener: ", "------ onSetSleepModeTime");
    }

    @Override
    public void onGetSleepModeTime(String s) {
        Log.i("IposListener: ", "------ onGetSleepModeTime");
    }

    @Override
    public void onGetShutDownTime(String s) {
        Log.i("IposListener: ", "------ onGetShutDownTime");
    }

    @Override
    public void onEncryptData(String s) {
        Log.i("IposListener: ", "------ onEncryptData");
    }

    @Override
    public void onAddKey(boolean b) {
        Log.i("IposListener: ", "------ onAddKey");
    }

    @Override
    public void onSetManagementKey(boolean arg0) {
        Log.i("IposListener: ", "------ onSetManagementKey");
    }
}