package com.pagatodo.yaganaste.modules.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pagatodo.yaganaste.R;
<<<<<<< HEAD
import com.pagatodo.yaganaste.modules.components.StepBar;
=======
import com.pagatodo.yaganaste.interfaces.enums.Direction;
>>>>>>> dc94c8624ab7afced0b113bfaccb94abe5588267
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;

public class RegActivity extends LoaderActivity implements RegContracts.Presenter{

<<<<<<< HEAD
    private RegRouter router;
    private StepBar stepBar;

=======
    //private RegContracts.RegRouter router;

    private RegContracts.Router router;
>>>>>>> dc94c8624ab7afced0b113bfaccb94abe5588267

    public static Intent createIntent(Activity activity){
        return new Intent(activity,RegActivity.class);
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
    public boolean requiresTimer() {
        return false;
    }

    @Override
    public void initViews() {
        router.showUserData(Direction.FORDWARD);
       // router.showPrsonalAddress(Direction.FORDWARD);
    }

    public void showFragmentDatosPersonales(){
        router.showPersonalData(Direction.FORDWARD);
    }
    public void showFragmentDomicilioIngresaCP(){
        router.showPrsonalAddress(Direction.FORDWARD);
    }
<<<<<<< HEAD

    @Override
    public void nextStep() {
        stepBar.nextStep();
    }

    @Override
    public void backStep() {
        stepBar.backStep();
    }
=======
    public void showFragmentDomicilioSelectCP(){
        router.showPrsonalAddressSelectCP(Direction.FORDWARD);
    }
    public void showFragmentDatosNegocio(){
        router.showBusinessData(Direction.FORDWARD);
    }

>>>>>>> dc94c8624ab7afced0b113bfaccb94abe5588267
}
