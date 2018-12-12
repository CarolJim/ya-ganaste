package com.pagatodo.yaganaste.modules.QR;

import com.pagatodo.yaganaste.interfaces.enums.Direction;

public class QRContracts {

    public interface Presenter{
        void initViews();
    }

    public interface Listener{
        void onSuccessValidatePlate(String plate);
        void onErrorValidatePlate(String error);
    }

    public interface Iteractor{
        void onValidateQR(String plate);
    }

    public interface Router{
        void showMyQR(Direction direction);
        void showMyMovementsQR(Direction direction);
        void showGenerateQR(Direction direction);
        void showScanQR(Direction direction);
        void showDetailQR(Direction direction);
    }
}
