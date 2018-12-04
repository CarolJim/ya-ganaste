package com.pagatodo.yaganaste.modules.register;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.register.CorreoUsuario.RegistroCorreoFragment;
import com.pagatodo.yaganaste.modules.register.DatosNegocio.DatosNegocioEAFragment;
import com.pagatodo.yaganaste.modules.register.DatosPersonales.RegistroDatosPersonalesFragment;
import com.pagatodo.yaganaste.modules.register.RegistroDomicilioPersonal.RegistroDomicilioPersonalFragment;
import com.pagatodo.yaganaste.modules.register.SeleccionaCP.SeleccionaCPFragment;

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
    public void showPrsonalAddress(Direction direction) {
        activity.loadFragment(RegistroDomicilioPersonalFragment.newInstance(activity), R.id.container_register,false);
    }
    @Override
    public void showPrsonalAddressSelectCP(Direction direction) {
        activity.loadFragment(SeleccionaCPFragment.newInstance(activity), R.id.container_register,false);
    }

    /**
     * Panatalla 04
     */
    @Override
    public void showBusinessData(Direction direction) {
        activity.loadFragment(DatosNegocioEAFragment.newInstance(activity), R.id.container_register,false);

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
