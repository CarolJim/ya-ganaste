package com.pagatodo.yaganaste.modules.register;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUserNew;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.components.StepBar;
import com.pagatodo.yaganaste.modules.register.CodigoVinculados.CodigosVinculadosFragment;
import com.pagatodo.yaganaste.modules.register.CorreoUsuario.RegistroCorreoFragment;
import com.pagatodo.yaganaste.modules.register.DatosNegocio.DatosNegocioEAFragment;
import com.pagatodo.yaganaste.modules.register.DatosPersonales.RegistroDatosPersonalesFragment;
import com.pagatodo.yaganaste.modules.register.PhysicalCode.NewLinkedCodeFragment;
import com.pagatodo.yaganaste.modules.register.PhysicalCode.PhysicalCodeFragment;
import com.pagatodo.yaganaste.modules.register.PhysicalCode.WritePlateQRFragment;
import com.pagatodo.yaganaste.modules.register.RegistroDomicilioPersonal.RegistroDomicilioPersonalFragment;
import com.pagatodo.yaganaste.modules.register.VincularCuenta.VincularCuentaFragment;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.utils.UI;

import java.util.ArrayList;

public class RegActivity extends LoaderActivity implements RegContracts.Presenter, RegContracts.Listener {

    public static final int RESULT_CODE_KEYBOARD = 153;

    private RegRouter router;
    private StepBar stepBar;
    private RegInteractor interactor;


    public static Intent createIntent(Activity activity) {
        return new Intent(activity, RegActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        RegisterUserNew.getInstance().setqRs(new ArrayList<>());
        stepBar = findViewById(R.id.step_bar);
        router = new RegRouter(this);
        interactor = new RegInteractor(this,this);
        initViews();
    }

    @Override
    public void initViews() {
        router.showUserData(Direction.FORDWARD);
    }

    public void showFragmentDatosPersonales() {
        router.showPersonalData(Direction.FORDWARD);
    }

    public void showFragmentDomicilioIngresaCP() {
        router.showPrsonalAddress(Direction.FORDWARD);
    }

    @Override
    public void nextStep() {
        stepBar.nextStep();
    }

    @Override
    public void backStep() {
        stepBar.backStep();
    }

    public void showFragmentDomicilioSelectCP() {
        router.showPrsonalAddressSelectCP(Direction.FORDWARD);
    }

    public RegRouter getRouter() {
        return router;
    }

    public RegInteractor getInteractor() {
        return interactor;
    }

    @Override
    public void hideStepBar() {
        stepBar.setVisibility(View.GONE);
    }

    @Override
    public void showStepBar() {
        stepBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_CODE_KEYBOARD:
                router.shosWritePlateQR();
                break;
                default:
                    Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                    parserQR(barcode);
                    break;
        }
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }



    @Override
    public void onBackPressed() {


        Fragment currentFragment = getCurrentFragment(R.id.fragment_container);
        if (currentFragment instanceof RegistroCorreoFragment) {
            finish();

        } else if (currentFragment instanceof RegistroDatosPersonalesFragment) {
            backStep();
            router.showUserData(Direction.BACK);
            backStep();
        } else if (currentFragment instanceof RegistroDomicilioPersonalFragment) {
            backStep();
            router.showPersonalData(Direction.BACK);
            backStep();
        } else if (currentFragment instanceof DatosNegocioEAFragment){
            backStep();
            router.showPrsonalAddress(Direction.BACK);
            backStep();
        } else if (currentFragment instanceof PhysicalCodeFragment){
            backStep();
            router.showBusinessData(Direction.BACK);
            backStep();

        } else if (currentFragment instanceof NewLinkedCodeFragment ||
                currentFragment instanceof CodigosVinculadosFragment ||
                currentFragment instanceof WritePlateQRFragment ) {
            backStep();
            router.showPhysicalCode(Direction.BACK);
            //backStep();
        } else if (currentFragment instanceof VincularCuentaFragment){
            backStep();
            backStep();
            router.showBusinessData(Direction.BACK);
            backStep();
        } else {
            super.onBackPressed();
        }
    }

    public void parserQR(Barcode barcode){
        try {
            JsonElement jelement = new JsonParser().parse(barcode.displayValue);
            JsonObject jobject = jelement.getAsJsonObject();
            jobject = jobject.getAsJsonObject("Aux");
            String plate = jobject.get("Pl").getAsString();
            interactor.onValidateQr(plate);
        }catch (JsonParseException e){
            e.printStackTrace();
            onErrorValidatePlate("QR Invalido");
        } catch (NullPointerException e){
            onErrorValidatePlate("QR Invalido");
        }
    }


    @Override
    public void onSuccessValidatePlate(String plate) {
        router.showNewLinkedCode(plate);
    }

    @Override
    public void onErrorValidatePlate(String error) {
        UI.showErrorSnackBar(this,error,Snackbar.LENGTH_SHORT);
    }
}
