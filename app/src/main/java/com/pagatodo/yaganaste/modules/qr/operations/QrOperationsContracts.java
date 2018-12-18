package com.pagatodo.yaganaste.modules.qr.operations;

import com.pagatodo.yaganaste.interfaces.enums.Direction;

public class QrOperationsContracts {

    public interface Presenter{
        void initViews();
    }

    public interface Listener{

    }

    public interface Iteractor{

    }

    public interface Router{
        void showMyMovementsQR(Direction direction); //02 -Movimientos
        void showGenerateQR(Direction direction); //03 - generar QR
        void showScanQR(Direction direction); //04 - Scan QR
        void showNameQrPhysical(Direction direction,String plate); //04b - Nombrar QR fisico
        void showWritePlateQr(Direction direction); //04c - Escribir Plate QR
        void showDetailQR(Direction direction); //05 -Detalle QR
        void showEditQr(Direction direction); // 05b - Editar QR
    }
}
