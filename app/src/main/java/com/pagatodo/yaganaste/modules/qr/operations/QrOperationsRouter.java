package com.pagatodo.yaganaste.modules.qr.operations;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.qr.operations.DetailQr.DetailQrFragment;

import java.text.DecimalFormat;

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

    }

    /* 04b - Nombrar QR fisico */
    @Override
    public void showNameQrPhysical(Direction direction) {

    }

    ///04c - Escribir Plate QR
    @Override
    public void showWritePlateQr(Direction direction) {

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
