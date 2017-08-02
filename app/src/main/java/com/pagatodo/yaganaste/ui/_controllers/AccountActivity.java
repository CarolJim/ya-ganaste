package com.pagatodo.yaganaste.ui._controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.FrameLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.login.LoginManagerContainerFragment;
import com.pagatodo.yaganaste.ui.account.login.RecoveryFragment;
import com.pagatodo.yaganaste.ui.account.register.AsignarNIPFragment;
import com.pagatodo.yaganaste.ui.account.register.AsociatePhoneAccountFragment;
import com.pagatodo.yaganaste.ui.account.register.ConfirmarNIPFragment;
import com.pagatodo.yaganaste.ui.account.register.Couchmark;
import com.pagatodo.yaganaste.ui.account.register.DatosPersonalesFragment;
import com.pagatodo.yaganaste.ui.account.register.DatosUsuarioFragment;
import com.pagatodo.yaganaste.ui.account.register.DomicilioActualFragment;
import com.pagatodo.yaganaste.ui.account.register.PermisosFragment;
import com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment;
import com.pagatodo.yaganaste.ui.account.register.TienesTarjetaFragment;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.UI;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.GO_TO_LOGIN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.GO_TO_REGISTER;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment.COMPLETE_MESSAGES.EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.COUCHMARK_EMISOR;


public class AccountActivity extends LoaderActivity implements OnEventListener {
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
    public final static String EVENT_GO_CONFIRM_PIN = "EVENT_GO_CONFIRM_PIN";
    public final static String EVENT_GO_CONFIRM_PIN_BACK = "EVENT_GO_CONFIRM_PIN_BACK";
    public final static String EVENT_COUCHMARK = "EVENT_GO_COUCHMARK";
    public final static String EVENT_GO_REGISTER_COMPLETE = "EVENT_GO_REGISTER_COMPLETE";
    public final static String EVENT_GO_MAINTAB = "EVENT_GO_MAINTAB";
    public final static String EVENT_RECOVERY_PASS = "EVENT_RECOVERY_PASS";
    public final static String EVENT_RECOVERY_PASS_BACK = "EVENT_RECOVERY_PASS_BACK";
    FrameLayout container;
    private String TAG = getClass().getSimpleName();
    private Preferencias pref;
    private LoginManagerContainerFragment loginContainerFragment;
    private AccountPresenterNew presenterAccount;

    private String action = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment_conainer);
        action = getIntent().getExtras().getString(SELECTION);
        pref = App.getInstance().getPrefs();
        resetRegisterData();
        presenterAccount = new AccountPresenterNew(this);
        loginContainerFragment = LoginManagerContainerFragment.newInstance();// LoginContainerFragment.newInstance();

        container = (FrameLayout) findViewById(R.id.container);

        switch (action) {
            case GO_TO_LOGIN:
                loadFragment(loginContainerFragment, Direction.FORDWARD, false);
                break;

            case GO_TO_REGISTER:
                loadFragment(DatosUsuarioFragment.newInstance(), Direction.FORDWARD, false);

                // TODO: 28/04/2017
                resetRegisterData();
                break;
        }
/*
        /*Validamos Permisos
        ValidatePermissions.checkPermissions(this, new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);*/
    }

    public AccountPresenterNew getPresenter() {

        return this.presenterAccount;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVisibilityPrefer(false);
    }

    @Override
    public void onEvent(String event, Object o) {
        super.onEvent(event, o);
        Log.e(TAG, "onEvent - - " + event);
        switch (event) {
            case EVENT_GO_LOGIN:
                loadFragment(loginContainerFragment, Direction.FORDWARD, false);
                break;

            case EVENT_RECOVERY_PASS:
                //loadFragment(Documentos.newInstance(), Direction.FORDWARD, false);
                //loadFragment(RecoveryFragment.newInstance(), Direction.FORDWARD, false);
                loginContainerFragment.loadRecoveryFragment();
                break;

            case EVENT_RECOVERY_PASS_BACK:
                loadFragment(loginContainerFragment, Direction.BACK, false);
                break;

            case EVENT_DATA_USER:
                loadFragment(DatosUsuarioFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_DATA_USER_BACK:
                loadFragment(DatosUsuarioFragment.newInstance(), Direction.BACK, false);
                break;

            case EVENT_PERSONAL_DATA:
                RegisterUser registerUser = RegisterUser.getInstance();
                registerUser.setGenero("");
                registerUser.setNombre("");
                registerUser.setApellidoMaterno("");
                registerUser.setApellidoPaterno("");
                registerUser.setFechaNacimiento("");
                registerUser.setFechaNacimientoToShow("");
                registerUser.setLugarNacimiento("");
                loadFragment(DatosPersonalesFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case EVENT_PERSONAL_DATA_BACK:
                registerUser = RegisterUser.getInstance();
                registerUser.setCalle("");
                registerUser.setNumExterior("");
                registerUser.setNumInterior("");
                registerUser.setCodigoPostal("");
                registerUser.setEstadoDomicilio("");
                registerUser.setColonia("");
                registerUser.setIdColonia("");
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
            case EVENT_GO_CONFIRM_PIN:
                loadFragment(ConfirmarNIPFragment.newInstance(o.toString()), Direction.FORDWARD, false);
                break;
            case EVENT_GO_CONFIRM_PIN_BACK:
                loadFragment(AsignarNIPFragment.newInstance(), Direction.BACK, false);
                break;
            case EVENT_GO_ASOCIATE_PHONE:
                loadFragment(AsociatePhoneAccountFragment.newInstance(), Direction.FORDWARD, false);
                break;

            case EVENT_GO_REGISTER_COMPLETE:
                pref.clearPreference(COUCHMARK_EMISOR);
                pref.clearPreference(COUCHMARK_ADQ);
                loadFragment(RegisterCompleteFragment.newInstance(EMISOR), Direction.FORDWARD, false);
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

    @Override
    public void onBackPressed() {
        if (!isLoaderShow) {
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment instanceof LoginManagerContainerFragment) {
                loginContainerFragment.onBackActions();
                //finish();
            } else if (currentFragment instanceof DatosUsuarioFragment) {
                resetRegisterData();// Eliminamos la información de registro almacenada.
                showDialogOut();
            } else if (currentFragment instanceof DatosPersonalesFragment) {
                onEvent(EVENT_DATA_USER_BACK, null);
            } else if (currentFragment instanceof DomicilioActualFragment) {
                onEvent(EVENT_PERSONAL_DATA_BACK, null);
            } else if (currentFragment instanceof TienesTarjetaFragment) {

                if (((TienesTarjetaFragment) currentFragment).isCustomKeyboardVisible()) {
                    ((TienesTarjetaFragment) currentFragment).hideKeyboard();
                } else {
                    resetRegisterData();// Eliminamos la información de registro almacenada.
                    showDialogOut();
                }
            } else if (currentFragment instanceof AsignarNIPFragment) {
                if (((AsignarNIPFragment) currentFragment).isCustomKeyboardVisible()) {
                    ((AsignarNIPFragment) currentFragment).hideKeyboard();
                } else {
                    resetRegisterData();// Eliminamos la información de registro almacenada.
                    showDialogOut();
                }
            } else if (currentFragment instanceof ConfirmarNIPFragment) {
                if (((ConfirmarNIPFragment) currentFragment).isCustomKeyboardVisible()) {
                    ((ConfirmarNIPFragment) currentFragment).hideKeyboard();
                } else {
                    onEvent(EVENT_GO_CONFIRM_PIN_BACK, null);
                }
            } else if (currentFragment instanceof AsociatePhoneAccountFragment) {
                resetRegisterData();// Eliminamos la información de registro almacenada.
                showDialogOut();
            } else if (currentFragment instanceof RecoveryFragment) {
                onEvent(EVENT_RECOVERY_PASS_BACK, null);
            } else {
                resetRegisterData();// Eliminamos la información de registro almacenada.
                super.onBackPressed();
            }
        }
    }

    private void showDialogOut() {
        UI.createSimpleCustomDialog("", getString(R.string.desea_cacelar), getSupportFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        finish();
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                }, true, true);
    }

    /*Se eliminan los datos almacenados en Memoria*/
    private void resetRegisterData() {
        RegisterUser.resetRegisterUser();
        Card.resetCardData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PAYMENTS_ADQUIRENTE && resultCode == Activity.RESULT_OK) {
            loginContainerFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}

