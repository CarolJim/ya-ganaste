package com.pagatodo.yaganaste.modules.register;

import com.pagatodo.yaganaste.interfaces.enums.Direction;

public class RegContracts {

    public interface Presenter{
        void initViews();
    }

    public interface Listener{

    }

    public interface Iteractor{

    }

    public interface Router{
        void showUserData(Direction direction); //Pantalla 01
        void showPersonalData(Direction direction); //Pantalla 02
        void showPrsonalAddress(); //Pantalla 03
        void showBusinessData(); //Pantalla 04
        void showPhysicalCode(); //Pantalla 05
        void showScanQR(); //Pantalla Scan QR 06
        void showDigitalCode(); //Pantalla 07
        void showSMSAndroid(); //Pantalla 08
        void showWelcome(); //Pantalla 09
    }
}
