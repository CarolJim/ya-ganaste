package com.pagatodo.yaganaste.modules.register;

import com.pagatodo.yaganaste.interfaces.enums.Direction;

public class RegContracts {

    public interface Presenter{
        void initViews();
        void nextStep();
        void backStep();
    }

    public interface Listener{

    }

    public interface Iteractor{

    }

    public interface Router{
        void showUserData(Direction direction); //Pantalla 01
        void showPersonalData(Direction direction); //Pantalla 02
        void showPrsonalAddress(Direction direction); //Pantalla 03
        void showPrsonalAddressSelectCP(Direction direction); //Pantalla 03
        void showBusinessData(Direction direction); //Pantalla 04
        void showQRVincualteData(Direction direction); //Pantalla 06b
        void showPhysicalCode(); //Pantalla 05
        void showScanQR(); //Pantalla Scan QR 06
        void showDigitalCode(); //Pantalla 07
        void showSMSAndroid(); //Pantalla 08
        void showWelcome(); //Pantalla 09
    }
}
