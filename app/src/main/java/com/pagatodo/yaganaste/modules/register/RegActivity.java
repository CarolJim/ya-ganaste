package com.pagatodo.yaganaste.modules.register;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.components.StepBar;
import com.pagatodo.yaganaste.modules.register.PhysicalCode.NewLinkedCodeFragment;
import com.pagatodo.yaganaste.modules.register.PhysicalCode.PhysicalCodeFragment;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;

import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;

public class RegActivity extends LoaderActivity implements RegContracts.Presenter {

    public static final int RESULT_CODE_KEYBOARD = 153;

    private RegRouter router;
    private StepBar stepBar;


    public static Intent createIntent(Activity activity) {
        return new Intent(activity, RegActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        stepBar = findViewById(R.id.step_bar);
        router = new RegRouter(this);
        initViews();
    }

    @Override
    public void initViews() {
        //router.showUserData(Direction.FORDWARD);
        //router.showPrsonalAddress(Direction.FORDWARD);
        //router.showPhysicalCode();
        // router.showPhysicalCode();


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

    public void showFragmentDatosNegocio() {
        router.showBusinessData(Direction.FORDWARD);
    }

    public void showFragmentviculaQr() {
        router.showQRVincualteData(Direction.FORDWARD);
    }

    public RegRouter getRouter() {
        return router;
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
        /*switch (resultCode){
            case RESULT_CODE_KEYBOARD:
                router.shosWritePlateQR();
                break;
                default:
                    PhysicalCodeFragment childFragment = (PhysicalCodeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                    childFragment.onActivityResult(requestCode,resultCode,data);
                    break;
        }*/


        //Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
        //router.showNewLinkedCode("");
        // loadFragment(NewLinkedCodeFragment.newInstance(""),R.id.container_register,Direction.NONE,false);
        /*if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    //Barcode barcode = data.getParcelableExtra(ScannVisionActivity.BarcodeObject);
                    router.showNewLinkedCode("");
                    //referenceNumber.setText(barcode.displayValue);
                    //Ocultamos el mensaje de error si esta visible
                    //editReferError.setVisibilityImageError(false);

                }
            }
        }*/
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {

            case "START":
                loadFragment(NewLinkedCodeFragment.newInstance("", R.string.title_code_fragment), R.id.fragment_container, Direction.NONE, true);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backStep();
    }
}
