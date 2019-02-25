package com.pagatodo.yaganaste.modules.register;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.pagatodo.yaganaste.App;
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
    //Nuevo diseño-flujo
    public final static String EVENT_DATA_USER_BUSNES = "EVENT_DATA_USER_BUSNES";
    ImageView btn_back ;
    private String action = "";

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, RegActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        RegisterUserNew.getInstance().setqRs(new ArrayList<>());
        try {
            action = getIntent().getExtras().getString(EVENT_DATA_USER_BUSNES);
        }catch (Exception e){
            action="";
        }

        stepBar = findViewById(R.id.step_bar);
        router = new RegRouter(this);
        App aplicacion = new App();
        interactor = new RegInteractor(this,this);
        initViews();
    }

    @Override
    public void initViews() {
        if (action!=null) {
            if (action.equals(EVENT_DATA_USER_BUSNES)) {
                router.showBusinessData(Direction.FORDWARD);
                RegisterUserNew registerUser = RegisterUserNew.getInstance();
                registerUser.setRegincomplete(true);
            }
            else {
                //router.showPersonalData(Direction.FORDWARD);
                router.showUserData(Direction.FORDWARD);
            }

        }
        else
            router.showUserData(Direction.FORDWARD);

        btn_back = (ImageView) findViewById(R.id.btn_back);
       // router.showPrsonalAddress(Direction.FORDWARD);
    }

    public void backvisivility( boolean visivility){
        if (visivility)
            btn_back.setVisibility(View.VISIBLE);
        else
            btn_back.setVisibility(View.INVISIBLE);

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
                    if (data!=null) {
                        Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                        parserQR(barcode);
                    }
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
            /*backStep();
            router.showUserData(Direction.BACK);
            backStep();*/
        }else if (currentFragment instanceof VincularCuentaFragment) {
            /*backStep();
            router.showUserData(Direction.BACK);
            backStep();*/
        } else if (currentFragment instanceof RegistroDomicilioPersonalFragment) {
            backStep();
            router.showPersonalData(Direction.BACK);
            backStep();
        } else if (currentFragment instanceof DatosNegocioEAFragment){
            /*backStep();
            router.showPrsonalAddress(Direction.BACK);
            backStep();*/
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
    public void onErrorService(String error) {

    }

    @Override
    public void onSuccessValidatePlate(String plate) {
        router.showNewLinkedCode(plate);
    }

    @Override
    public void onErrorValidatePlate(String error) {
        UI.showErrorSnackBar(this,error,Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onSuccesupdateSession() {

    }

    @Override
    public void onSErrorupdateSession() {

    }

}
