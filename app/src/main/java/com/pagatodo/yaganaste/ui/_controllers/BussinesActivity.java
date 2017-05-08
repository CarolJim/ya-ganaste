package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Window;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.adquirente.DatosNegocio;
import com.pagatodo.yaganaste.ui.adquirente.Documentos;
import com.pagatodo.yaganaste.ui.adquirente.DomicilioNegocio;
import com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;

import static com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment.COMPLETE_MESSAGES.ADQ_ACEPTADOS;
import static com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment.COMPLETE_MESSAGES.ADQ_REVISION;


public class BussinesActivity extends SupportFragmentActivity implements OnEventListener {

    private Preferencias pref;
    private AccountPresenterNew presenterAccount;

    //Nuevo diseño-flujo
    public final static String EVENT_GO_BUSSINES_DATA = "EVENT_GO_BUSSINES_DATA";
    public final static String EVENT_GO_BUSSINES_ADDRESS = "EVENT_GO_BUSSINES_ADDRESS";
    public final static String EVENT_GO_BUSSINES_DOCUMENTS = "EVENT_GO_BUSSINES_DOCUMENTS";
    public final static String EVENT_GO_BUSSINES_COMPLETE = "EVENT_GO_BUSSINES_COMPLETE";
    public final static String EVENT_GO_BUSSINES_DATA_BACK = "EVENT_GO_BUSSINES_DATA_BACK";
    public final static String EVENT_GO_BUSSINES_ADDRESS_BACK = "EVENT_GO_BUSSINES_ADDRESS_BACK";
    public final static String EVENT_DOC_CHECK = "EVENT_DOC_CHECK";

    private DatosNegocio datosNegocioFragment;
    private DomicilioNegocio domicilioNegocioFragment;
    private Documentos documentosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment_conainer);
        presenterAccount = new AccountPresenterNew(this);
       // initFragments();
        loadFragment(DatosNegocio.newInstance(), Direction.FORDWARD, true);
        pref = App.getInstance().getPrefs();
    }

    @Override
    public void onEvent(String event, Object o) {
        switch (event) {
            case EVENT_GO_BUSSINES_DATA:
                loadFragment(DatosNegocio.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_DATA_BACK:
                loadFragment(DatosNegocio.newInstance(), Direction.BACK, false);
                break;
            case EVENT_GO_BUSSINES_ADDRESS:
                loadFragment(DomicilioNegocio.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_ADDRESS_BACK:
                loadFragment(DomicilioNegocio.newInstance(), Direction.BACK, false);
                break;
            case EVENT_GO_BUSSINES_DOCUMENTS:
                loadFragment(Documentos.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_COMPLETE:
                loadFragment(RegisterCompleteFragment.newInstance(ADQ_REVISION), Direction.FORDWARD, false);
                break;

            case EVENT_GO_MAINTAB:

                resetRegisterData();
                finish();
                break;

            case EVENT_DOC_CHECK:
                resetRegisterData();

                presenterAccount.checkUpdateDocs();
                Intent i = new Intent(this,TabActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment instanceof DatosNegocio) {
            RegisterAgent.resetRegisterAgent();
            finish();
        } else if (currentFragment instanceof DomicilioNegocio) {
            onEvent(EVENT_GO_BUSSINES_DATA_BACK, null);
        } else if (currentFragment instanceof Documentos) {
            onEvent(EVENT_GO_BUSSINES_ADDRESS_BACK, null);
        } else if (currentFragment instanceof Documentos) {
            onEvent(EVENT_GO_BUSSINES_ADDRESS_BACK, null);
        } else if (currentFragment instanceof RegisterCompleteFragment) {

        } else {
            RegisterAgent.resetRegisterAgent();
            finish();
        }

    }

    private void initFragments() {
        datosNegocioFragment = DatosNegocio.newInstance();
        domicilioNegocioFragment = DomicilioNegocio.newInstance();
        documentosFragment = Documentos.newInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getCurrentFragment();
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    private void resetRegisterData() {
        RegisterAgent.resetRegisterAgent();
    }
}

