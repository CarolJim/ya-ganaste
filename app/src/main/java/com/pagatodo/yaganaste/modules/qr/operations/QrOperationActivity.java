package com.pagatodo.yaganaste.modules.qr.operations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.data.QrItems;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.qrcode.Auxl;
import com.pagatodo.yaganaste.utils.qrcode.Qrlectura;

import java.util.Objects;

public class QrOperationActivity extends LoaderActivity {

    private static final String TAG_ID_FRAGMENT = "TAG_ID_FRAGMENT";
    private static final String TAG_QR_ITEM = "TAG_QR_ITEM";
    public static final int ID_ADD_QR = 101;
    public static final int ID_GENERATE_NEW_QR = 102;
    public static final int ID_MY_SALES_QR = 103;
    public static final int ID_DETAIL_QR = 104;
    private int idFragment;
    public static QrOperationsRouter router;

    public static Intent createIntent(Activity activity, int tag){
        Intent intent = new Intent(activity,QrOperationActivity.class);
        intent.putExtra(TAG_ID_FRAGMENT,tag);
        return intent;
    }

    public static Intent createIntent(Activity activity, QrItems item, int tag){
        Intent intent = new Intent(activity,QrOperationActivity.class);
        intent.putExtra(TAG_ID_FRAGMENT,tag);
        intent.putExtra(TAG_QR_ITEM,item);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        router = new QrOperationsRouter(this);
        if (getIntent().getExtras() != null){
            idFragment = getIntent().getExtras().getInt(TAG_ID_FRAGMENT);
        }
        initView();
    }

    private void initView(){
        switch (idFragment){
            case ID_ADD_QR:
                router.showScanQR(Direction.NONE);
                break;
            case ID_GENERATE_NEW_QR:
                router.showGenerateQR(Direction.NONE);
                break;
            case ID_MY_SALES_QR:
                router.showMyMovementsQR(Direction.NONE);
                break;
            case ID_DETAIL_QR:
                QrItems item = (QrItems) Objects.requireNonNull(getIntent().getExtras()).getSerializable(TAG_QR_ITEM);
                router.showDetailQR(Direction.NONE,item);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                    if (barcode.displayValue.contains("Pl")) {
                        Qrlectura myQr = new Gson().fromJson(barcode.displayValue, Qrlectura.class);
                        Auxl auxl = myQr.getAux();
                        String plate  = auxl.getPl();
                        router.showNameQrPhysical(Direction.NONE,plate);
                    /*    cardNumber.setText(myQr.getClabe());
                        receiverName.setText(myQr.getUserName());*/
                    }
                }else{
                    finish();
                }
            }else if (resultCode ==153){
                router.showWritePlateQr(Direction.NONE);
            }

        }
    }


    @Override
    public boolean requiresTimer() {
        return true;
    }


    public QrOperationsRouter getRouter() {
        return router;
    }
}

