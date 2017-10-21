package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.Giros;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.SubGiro;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DatosNegocioFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DocumentosFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DomicilioNegocioFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.InformacionAdicionalFragment;

import java.util.List;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment.COMPLETE_MESSAGES.ADQ_REVISION;
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_PROCESS;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_PENDIENTE;


public class BussinesActivity extends LoaderActivity {

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
    public final static String EVENT_GO_BUSSINES_ADITIONAL_INFORMATION = "EVENT_GO_BUSSINES_INFORMACION_ADICIONAL";
    public final static String EVENT_GO_BUSSINES_ADITIONAL_INFORMATION_BACK = "EVENT_GO_BUSSINES_ADITIONAL_INFORMATION_BACK";


    private DataObtenerDomicilio domicilio;
    private List<Giros> girosComercio;
    private List<ColoniasResponse> listaColonias;


    public static Intent createIntent(Context from) {
        return new Intent(from, BussinesActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment_conainer);
        //presenterAccount = new AccountPresenterNew(this);

        setUpActionBar();
        setVisibilityPrefer(false);

        if (App.getInstance().getPrefs().containsData(ADQ_PROCESS)) {
            loadFragment(DocumentosFragment.newInstance(), Direction.FORDWARD);
            showBack(false);
        } else {
            loadFragment(DatosNegocioFragment.newInstance(girosComercio), Direction.FORDWARD, true);
            //loadFragment(InformacionAdicionalFragment.newInstance(), Direction.FORDWARD, true);
        }

        //pref = App.getInstance().getPrefs();

        System.gc();
    }

    @Override
    public void onEvent(String event, Object o) {
        super.onEvent(event, o);
        switch (event) {
            case EVENT_GO_BUSSINES_DATA:
                loadFragment(DatosNegocioFragment.newInstance(girosComercio), Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_DATA_BACK:
                loadFragment(DatosNegocioFragment.newInstance(girosComercio), Direction.BACK, false);
                RegisterAgent.resetBussinessAddress();
                listaColonias = null;
                break;
            case EVENT_GO_BUSSINES_ADDRESS:
                loadFragment(DomicilioNegocioFragment.newInstance(domicilio, listaColonias), Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_ADDRESS_BACK:
                //loadFragment(DomicilioNegocioFragment.newInstance(domicilio, listaColonias), Direction.BACK, false);
                resetRegisterData();
                finish();
                break;
            case EVENT_GO_BUSSINES_DOCUMENTS:
                loadFragment(DocumentosFragment.newInstance(), Direction.FORDWARD, false);
                showBack(false);
                break;
            case EVENT_GO_BUSSINES_ADITIONAL_INFORMATION:
                loadFragment(InformacionAdicionalFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_ADITIONAL_INFORMATION_BACK:
                RegisterAgent.getInstance().resetAditionalInformation();
                loadFragment(DomicilioNegocioFragment.newInstance(domicilio, listaColonias), Direction.BACK, false);
                break;
            case EVENT_GO_BUSSINES_COMPLETE:
                loadFragment(RegisterCompleteFragment.newInstance(ADQ_REVISION), Direction.FORDWARD, false);
                break;
            case EVENT_GO_MAINTAB:
                resetRegisterData();
                finish();
                break;
            case EVENT_DOC_CHECK:
                SingletonUser.getInstance().getDataUser().setEstatusDocumentacion(STATUS_DOCTO_PENDIENTE);
                SingletonUser.getInstance().getDataUser().setEsAgente(true);
                setResult(TabActivity.RESULT_ADQUIRENTE_SUCCESS);
                finish();
                break;

            case EVENT_SET_ADDRESS:
                this.domicilio = (DataObtenerDomicilio) o;
                break;

            case EVENT_SET_BUSINESS_LIST:
                this.girosComercio = (List<Giros>) o;
                break;
            case EVENT_SET_COLONIES_LIST:
                this.listaColonias = (List<ColoniasResponse>) o;
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if (!isLoaderShow) {
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment instanceof DatosNegocioFragment) {
                RegisterAgent.resetRegisterAgent();
                finish();
            } else if (currentFragment instanceof DomicilioNegocioFragment) {
                onEvent(EVENT_GO_BUSSINES_DATA_BACK, null);
            } else if (currentFragment instanceof InformacionAdicionalFragment) {
                onEvent(EVENT_GO_BUSSINES_ADITIONAL_INFORMATION_BACK, null);
            } else if (currentFragment instanceof DocumentosFragment) {
                ((DocumentosFragment) currentFragment).onBtnBack();
            } else if (currentFragment instanceof RegisterCompleteFragment) {

            } else {
                RegisterAgent.resetRegisterAgent();
                finish();
            }
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        setVisibilityPrefer(false);
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }
}

