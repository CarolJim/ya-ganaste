package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.SubGiro;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment;
import com.pagatodo.yaganaste.ui.adquirente.DatosNegocio;
import com.pagatodo.yaganaste.ui.adquirente.Documentos;
import com.pagatodo.yaganaste.ui.adquirente.DomicilioNegocio;
<<<<<<< HEAD
=======
import com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import java.util.List;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
>>>>>>> e43a2f2642b94963e3d487d3107a17d1648880d3

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment.COMPLETE_MESSAGES.ADQ_REVISION;


public class BussinesActivity extends LoaderActivity {

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

    public final static String EVENT_SET_ADDRESS = "EVENT_SET_ADDRESS";
    public final static String EVENT_SET_BUSINESS_LIST = "EVENT_SET_BUSINESS_LIST";
    public final static String EVENT_SET_COLONIES_LIST = "EVENT_SET_COLONIES_LIST";



    private DatosNegocio datosNegocioFragment;
    private DomicilioNegocio domicilioNegocioFragment;
    private Documentos documentosFragment;

    private DataObtenerDomicilio domicilio;
    private List<SubGiro> girosComercio;
    private List<ColoniasResponse> listaColonias;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment_conainer);
        presenterAccount = new AccountPresenterNew(this);

       // initFragments();
        loadFragment(DatosNegocio.newInstance(girosComercio), Direction.FORDWARD, true);

        pref = App.getInstance().getPrefs();
    }

    @Override
    public void onEvent(String event, Object o) {
        super.onEvent(event, o);
        switch (event) {
            case EVENT_GO_BUSSINES_DATA:
                loadFragment(DatosNegocio.newInstance(girosComercio), Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_DATA_BACK:
                loadFragment(DatosNegocio.newInstance(girosComercio), Direction.BACK, false);
                RegisterAgent.resetBussinessAddress();
                listaColonias = null;
                break;
            case EVENT_GO_BUSSINES_ADDRESS:
                loadFragment(DomicilioNegocio.newInstance(domicilio, listaColonias), Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_ADDRESS_BACK:
                loadFragment(DomicilioNegocio.newInstance(domicilio, listaColonias), Direction.BACK, false);
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
                presenterAccount.checkUpdateDocs();

                Intent i = new Intent(this, TabActivity.class);
                startActivity(i);

                finish();
                break;

            case EVENT_SET_ADDRESS:
                this.domicilio = (DataObtenerDomicilio) o;
                break;

            case EVENT_SET_BUSINESS_LIST:
                this. girosComercio = (List<SubGiro>) o;
                break;
            case EVENT_SET_COLONIES_LIST:
                this.listaColonias = (List<ColoniasResponse>) o;
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
        datosNegocioFragment = DatosNegocio.newInstance(girosComercio);
        domicilioNegocioFragment = DomicilioNegocio.newInstance(domicilio, listaColonias);
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

