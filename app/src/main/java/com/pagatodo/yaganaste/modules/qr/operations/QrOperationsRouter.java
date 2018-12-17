package com.pagatodo.yaganaste.modules.qr.operations;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.qr.QRWallet.AgregarQRFisico.AgregaQRFragment;
import com.pagatodo.yaganaste.modules.qr.QRWallet.EcribeQRPlate.ErcribePlateFragment;
import com.pagatodo.yaganaste.modules.qr.operations.DetailQr.DetailQrFragment;

import android.content.Intent;

import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;

import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;

public class QrOperationsRouter implements QrOperationsContracts.Router {

    private QrOperationActivity activity;

    /**
     * id conteiner  fragments R.id.fragment_container_qr
     */

    public QrOperationsRouter(QrOperationActivity activity) {
        this.activity = activity;
    }

    /* 02 -Movimientos*/
    @Override
    public void showMyMovementsQR(Direction direction) {

    }

    /*03 - generar QR*/
    @Override
    public void showGenerateQR(Direction direction) {

    }

    /*04 - Scan QR*/
    @Override
    public void showScanQR(Direction direction) {
        Intent intent = new Intent(activity, ScannVisionActivity.class);
        intent.putExtra(ScannVisionActivity.QRObject, true);
        activity.startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
    }

    /* 04b - Nombrar QR fisico */
    @Override
    public void showNameQrPhysical(Direction direction,String plate) {
        activity.loadFragment(AgregaQRFragment.newInstance(plate),R.id.fragment_container_qr,direction,false);
    }

    ///04c - Escribir Plate QR
    @Override
    public void showWritePlateQr(Direction direction) {
        activity.loadFragment(ErcribePlateFragment.newInstance(activity),R.id.fragment_container_qr,direction,false);

    }

    /*Pantalla 5*/
    @Override
    public void showDetailQR(Direction direction) {
        activity.loadFragment(DetailQrFragment.newInstance(),R.id.fragment_container_qr,direction,false);
    }

    // 05b - Editar QR
    @Override
    public void showEditQr(Direction direction) {

    }
}
