package com.pagatodo.yaganaste.modules.register;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.register.CorreoUsuario.RegistroCorreoFragment;
import com.pagatodo.yaganaste.modules.register.DatosPersonales.RegistroDatosPersonalesFragment;

public class RegRouter implements RegContracts.Router {

    /**
     * NOTA: El id de framelayout del container es
     * R.id.container_register
     */

    private RegActivity activity;

    public RegRouter(RegActivity activity) {
        this.activity = activity;
    }

    /**
     * Panatalla 01
     */

    @Override
    public void showUserData(Direction direction) {
       // activity.loadFragment(RegisterPhoneNumber.newInstance(), R.id.container_register_wallet, direction, false);
        activity.loadFragment(RegistroCorreoFragment.newInstance(activity), R.id.container_register,false);

    }

    /**
     * Panatalla 02
     */
    @Override
    public void showPersonalData (Direction direction) {
        // activity.loadFragment(RegisterPhoneNumber.newInstance(), R.id.container_register_wallet, direction, false);
        activity.loadFragment(RegistroDatosPersonalesFragment.newInstance(activity), R.id.container_register,false);


    }
     /** Panatalla 03
     */
    @Override
    public void showPrsonalAddress() {

    }

    /**
     * Panatalla 04
     */
    @Override
    public void showBusinessData() {

    }

    /**
     * Panatalla 05
     */
    @Override
    public void showPhysicalCode() {

    }

    /**
     * Panatalla 06
     */
    @Override
    public void showScanQR() {

    }

    /**
     * Panatalla 07
     */
    @Override
    public void showDigitalCode() {

    }

    /**
     * Panatalla 08
     */
    @Override
    public void showSMSAndroid() {

    }

    /**
     * Panatalla 09
     */
    @Override
    public void showWelcome() {

    }
}
