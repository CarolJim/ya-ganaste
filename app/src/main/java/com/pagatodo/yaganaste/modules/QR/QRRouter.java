package com.pagatodo.yaganaste.modules.QR;

import com.pagatodo.yaganaste.interfaces.enums.Direction;

public class QRRouter implements QRContracts.Router {

    private QRActivity activity;

    public QRRouter(QRActivity activity) {
        this.activity = activity;
    }

    /*
    * Id framelayout:fragment_containerQR
    * */

    /*Pantalla 1*/
    @Override
    public void showMyQR(Direction direction) {

    }

    /*Pantalla 2*/
    @Override
    public void showMyMovementsQR(Direction direction) {

    }

    /*Pantalla 3*/
    @Override
    public void showGenerateQR(Direction direction) {

    }

    /*Pantalla 4*/
    @Override
    public void showScanQR(Direction direction) {

    }

    /*Pantalla 5*/
    @Override
    public void showDetailQR(Direction direction) {

    }
}
