package com.pagatodo.yaganaste.modules.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;

public class RegActivity extends LoaderActivity implements RegContracts.Presenter{

    //private RegContracts.RegRouter router;

    private RegContracts.Router router;

    public static Intent createIntent(Activity activity){
        return new Intent(activity,RegActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        router = new RegRouter(this);
        initViews();
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }

    @Override
    public void initViews() {
        router.showUserData(Direction.FORDWARD);
    }

    public void showFragmentDatosPersonales(){
        router.showPersonalData(Direction.FORDWARD);
    }
    public void showFragmentDomicilioIngresaCP(){
        router.showPrsonalAddress(Direction.FORDWARD);
    }
    public void showFragmentDomicilioSelectCP(){
        router.showPrsonalAddressSelectCP(Direction.FORDWARD);
    }
    public void showFragmentDatosNegocio(){
        router.showBusinessData(Direction.FORDWARD);
    }

}
