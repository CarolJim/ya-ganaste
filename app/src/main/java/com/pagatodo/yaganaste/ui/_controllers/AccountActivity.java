package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.account.login.LoginFragment;
import com.pagatodo.yaganaste.ui.account.register.AsignarNIPFragment;
import com.pagatodo.yaganaste.ui.account.register.AsociatePhoneAccountFragment;
import com.pagatodo.yaganaste.ui.account.register.Couchmark;
import com.pagatodo.yaganaste.ui.account.register.DatosPersonalesFragment;
import com.pagatodo.yaganaste.ui.account.register.DatosUsuarioFragment;
import com.pagatodo.yaganaste.ui.account.register.DomicilioActualFragment;
import com.pagatodo.yaganaste.ui.account.register.LandingFragment;
import com.pagatodo.yaganaste.ui.account.register.PermisosFragment;
import com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment;
import com.pagatodo.yaganaste.ui.account.register.TienesTarjetaFragment;

import static com.pagatodo.yaganaste.ui._controllers.MainActivity.GO_TO_LOGIN;
import static com.pagatodo.yaganaste.ui._controllers.MainActivity.GO_TO_REGISTER;
import static com.pagatodo.yaganaste.ui._controllers.MainActivity.SELECTION;


public class AccountActivity extends SupportFragmentActivity implements OnEventListener{
    private Preferencias pref;

    public final static String EVENT_GO_LOGIN = "EVENT_GO_LOGIN";
    public final static String EVENT_GO_GET_CARD = "EVENT_GO_GET_CARD";
    public final static String EVENT_GO_BASIC_INFO = "EVENT_GO_BASIC_INFO";
    public final static String EVENT_GO_REGISTER_ADDRESS = "EVENT_GO_REGISTER_ADDRESS";
    public final static String EVENT_GO_GET_PAYMENTS = "EVENT_GO_GET_PAYMENTS";
    public final static String EVENT_GO_ASOCIATE_PHONE = "EVENT_GO_ASOCIATE_PHONE";
    public final static String EVENT_GO_PIN_CONFIRMATION = "EVENT_GO_PIN_CONFIRMATION";
    public final static String EVENT_GO_MAIN_TAB_ACTIVITY = "EVENT_GO_MAIN_TAB_ACTIVITY";
    //Nuevo diseño-flujo
    public final static String EVENT_DATA_USER = "EVENT_GO_DATA_USER";
    public final static String EVENT_DATA_USER_BACK = "EVENT_GO_DATA_USER_BACK";
    public final static String EVENT_PERSONAL_DATA = "EVENT_GO_PERSONAL_DATA";
    public final static String EVENT_PERSONAL_DATA_BACK = "EVENT_GO_PERSONAL_DATA_BACK";
    public final static String EVENT_ADDRESS_DATA = "EVENT_GO_ADDRESS_DATA";
    public final static String EVENT_ADDRESS_DATA_BACK = "EVENT_GO_ADDRESS_DATA_BACK";
    public final static String EVENT_GO_ASSIGN_PIN = "EVENT_GO_ASSIGN_PIN";
    public final static String EVENT_COUCHMARK = "EVENT_GO_COUCHMARK";
    public final static String EVENT_GO_REGISTER_COMPLETE = "EVENT_GO_REGISTER_COMPLETE";
    public final static String EVENT_GO_MAINTAB = "EVENT_GO_MAINTAB";

    private DatosUsuarioFragment datosUsuarioFragment;
    private DatosPersonalesFragment datosPersonalesFragment;
    private DomicilioActualFragment domicilioActualFragment;
    private TienesTarjetaFragment tienesTarjetaFragment;

    private PermisosFragment permisosFragment;

    private String action="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity);
        action = getIntent().getExtras().getString(SELECTION);
        pref = App.getInstance().getPrefs();

        switch (action) {
            case GO_TO_LOGIN:
                loadFragment(LoginFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case GO_TO_REGISTER:
                loadFragment(DatosUsuarioFragment.newInstance(), Direction.FORDWARD, false);
                break;

        }

        Intent intent = new Intent(this, LandingFragment.class);
        startActivity(intent);

/*
        /*Validamos Permisos
        ValidatePermissions.checkPermissions(this, new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);*/
    }

    @Override
    public void onEvent(String event, Object o) {

        switch (event){

            case EVENT_GO_LOGIN:
                loadFragment(LoginFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case EVENT_DATA_USER:
                loadFragment(DatosUsuarioFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_DATA_USER_BACK:
                loadFragment(DatosUsuarioFragment.newInstance(), Direction.BACK, false);
                break;

            case EVENT_PERSONAL_DATA:
                loadFragment(DatosPersonalesFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case EVENT_PERSONAL_DATA_BACK:
                loadFragment(DatosPersonalesFragment.newInstance(), Direction.BACK, false);
                break;

            case EVENT_ADDRESS_DATA:
                loadFragment(DomicilioActualFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case EVENT_ADDRESS_DATA_BACK:
                loadFragment(DomicilioActualFragment.newInstance(), Direction.BACK, false);
                break;
            case EVENT_GO_GET_CARD:
                loadFragment(TienesTarjetaFragment.newInstance(), Direction.FORDWARD, true);
                break;
            case EVENT_GO_ASSIGN_PIN:
                loadFragment(AsignarNIPFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case EVENT_GO_ASOCIATE_PHONE:
                loadFragment(AsociatePhoneAccountFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case EVENT_GO_REGISTER_COMPLETE:
                loadFragment(RegisterCompleteFragment.newInstance(), Direction.FORDWARD, false);
                break;


            case EVENT_COUCHMARK:
                loadFragment(Couchmark.newInstance(), Direction.FORDWARD, false);
                break;

            case EVENT_GO_MAINTAB:
                resetRegisterData();
                Intent intent = new Intent(AccountActivity.this, TabActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void initFragments(){
        datosUsuarioFragment = DatosUsuarioFragment.newInstance();
        datosPersonalesFragment = DatosPersonalesFragment.newInstance();
        domicilioActualFragment = DomicilioActualFragment.newInstance();
        tienesTarjetaFragment = TienesTarjetaFragment.newInstance();
    }

    @Override
    public void onBackPressed() {

        Fragment currentFragment = getCurrentFragment();
        if(currentFragment instanceof LoginFragment){
            finish();
        }else if(currentFragment instanceof DatosUsuarioFragment){
            resetRegisterData();// Eliminamos la información de registro almacenada.
            finish();
        }else if(currentFragment instanceof DatosPersonalesFragment){
            onEvent(EVENT_DATA_USER_BACK,null);
        }else if(currentFragment instanceof DomicilioActualFragment){
            onEvent(EVENT_PERSONAL_DATA_BACK,null);
        }else if(currentFragment instanceof TienesTarjetaFragment){
            resetRegisterData();// Eliminamos la información de registro almacenada.
            finish();
        }  else if(currentFragment instanceof AsignarNIPFragment){
            resetRegisterData();// Eliminamos la información de registro almacenada.
            finish();
        }else if(currentFragment instanceof AsociatePhoneAccountFragment){
            resetRegisterData();// Eliminamos la información de registro almacenada.
            finish();
        }else{
            resetRegisterData();// Eliminamos la información de registro almacenada.
            super.onBackPressed();
        }
    }

    private void resetRegisterData(){
        RegisterUser.resetRegisterUser();
    }

}

