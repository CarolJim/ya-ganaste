package com.pagatodo.yaganaste.modules.register;

import android.content.Intent;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.register.CodigoVinculados.CodigosVinculadosFragment;
import com.pagatodo.yaganaste.modules.register.CorreoUsuario.RegistroCorreoFragment;
import com.pagatodo.yaganaste.modules.register.DatosNegocio.DatosNegocioEAFragment;
import com.pagatodo.yaganaste.modules.register.DatosPersonales.RegistroDatosPersonalesFragment;
import com.pagatodo.yaganaste.modules.register.PhysicalCode.NewLinkedCodeFragment;
import com.pagatodo.yaganaste.modules.register.PhysicalCode.PhysicalCodeFragment;
import com.pagatodo.yaganaste.modules.register.PhysicalCode.WritePlateQRFragment;
import com.pagatodo.yaganaste.modules.register.RegistroCompleto.RegistroCompletoFragment;
import com.pagatodo.yaganaste.modules.register.RegistroDomicilioPersonal.RegistroDomicilioPersonalFragment;
import com.pagatodo.yaganaste.modules.register.SeleccionaCP.SeleccionaCPFragment;
import com.pagatodo.yaganaste.modules.register.VincularCuenta.VincularCuentaFragment;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;

import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;

public class RegRouter implements RegContracts.Router {

    /**
     * NOTA: El id de framelayout del container es
     * R.id.container_register
     */

    private RegActivity activity;

    RegRouter(RegActivity activity) {
        this.activity = activity;
    }

    /**
     * Panatalla 01
     */

    @Override
    public void showUserData(Direction direction) {
        activity.loadFragment(RegistroCorreoFragment.newInstance(), R.id.fragment_container,
                direction, false);

        /*activity.loadFragment(AgregaQRFragmentisma.newInstance(), R.id.fragment_container,
                direction, false);
*/
    }

    /**
     * Panatalla 02
     */
    @Override
    public void showPersonalData(Direction direction) {
        activity.loadFragment(RegistroDatosPersonalesFragment.newInstance(activity),
                R.id.fragment_container, direction, false);
    }

    /**
     * Panatalla 03
     */
    @Override
    public void showPrsonalAddress(Direction direction) {
        activity.loadFragment(RegistroDomicilioPersonalFragment.newInstance(activity),
                R.id.fragment_container, direction, false);
    }

    @Override
    public void showPrsonalAddressSelectCP(Direction direction) {
        activity.loadFragment(SeleccionaCPFragment.newInstance(), R.id.fragment_container,
                direction, false);
    }

    /**
     * Panatalla 04
     */
    @Override
    public void showBusinessData(Direction direction) {
        activity.loadFragment(DatosNegocioEAFragment.newInstance(activity), R.id.fragment_container,
                direction, false);

    }

    /**
     * Panatalla 05
     */
    @Override
    public void showPhysicalCode(Direction direction) {
        activity.loadFragment(PhysicalCodeFragment.newInstance(), R.id.fragment_container,
                direction, false);
    }

    /**
     * Panatalla 06
     */
    @Override
    public void showScanQR() {
        Intent intent = new Intent(activity, ScannVisionActivity.class);
        intent.putExtra(ScannVisionActivity.QRObject, true);
        activity.startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
    }

    /**
     * Panatalla 06b
     */
    @Override
    public void showNewLinkedCode(String displayValue) {
        activity.loadFragment(NewLinkedCodeFragment.newInstance(displayValue, R.string.title_code_fragment),
                R.id.fragment_container, Direction.FORDWARD, false);
    }

    /**
     * pantalla 06c - Códigos vinculados
     */
    @Override
    public void showLinkedCodes() {
        activity.loadFragment(CodigosVinculadosFragment.newInstance(), R.id.fragment_container,
                Direction.FORDWARD, false);
    }

    /**
     * Pantalla 06d - Escribir Plate QR
     */
    @Override
    public void shosWritePlateQR() {
        activity.loadFragment(WritePlateQRFragment.newInstance(), R.id.fragment_container,
                Direction.NONE, false);
    }

    /**
     * Panatalla 07
     */
    @Override
    public void showDigitalCode() {
        activity.loadFragment(NewLinkedCodeFragment.newInstance("",
                R.string.title_code_digital_fragment), R.id.fragment_container,
                Direction.FORDWARD, false);
    }

    /**
     * Panatalla 08
     */
    @Override
    public void showSMSAndroid() {
        activity.loadFragment(VincularCuentaFragment.newInstance(), R.id.fragment_container,
                Direction.FORDWARD, false);
    }

    /**
     * Panatalla 09
     */
    @Override
    public void showWelcome() {
        activity.loadFragment(RegistroCompletoFragment.newInstance(), R.id.fragment_container,
                Direction.FORDWARD, false);
    }


}
