package com.pagatodo.yaganaste.ui._controllers;

import android.os.Bundle;
import android.view.Window;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.account.register.DatosNegocio;
import com.pagatodo.yaganaste.ui.account.register.Documentos;
import com.pagatodo.yaganaste.ui.account.register.DomicilioNegocio;
import com.pagatodo.yaganaste.ui.account.register.RegistroNegocioCompletoFragment;


public class BussinesActivity extends SupportFragmentActivity implements OnEventListener{
    private Preferencias pref;

    //Nuevo diseño-flujo
    public final static String EVENT_GO_BUSSINES_DATA = "EVENT_GO_BUSSINES_DATA";
    public final static String EVENT_GO_BUSSINES_ADDRESS = "EVENT_GO_BUSSINES_ADDRESS";
    public final static String EVENT_GO_BUSSINES_DOCUMENTS = "EVENT_GO_BUSSINES_DOCUMENTS";
    public final static String EVENT_GO_BUSSINES_COMPLETE = "EVENT_GO_BUSSINES_COMPLETE";
    public final static String EVENT_GO_BUSSINES_DATA_BACK = "EVENT_GO_BUSSINES_DATA_BACK";
    public final static String EVENT_GO_BUSSINES_ADDRESS_BACK = "EVENT_GO_BUSSINES_ADDRESS_BACK";

    private DatosNegocio datosNegocioFragment;
    private DomicilioNegocio domicilioNegocioFragment;
    private Documentos documentosFragment;
    private RegistroNegocioCompletoFragment registroNegocioCompletoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity);
        initFragments();
        loadFragment(datosNegocioFragment, Direction.FORDWARD, true);
        pref = App.getInstance().getPrefs();
    }

    @Override
    public void onEvent(String event, Object o) {
        switch (event){
            case EVENT_GO_BUSSINES_DATA:
                loadFragment(datosNegocioFragment, Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_DATA_BACK:
                loadFragment(datosNegocioFragment, Direction.BACK, false);
                break;
            case EVENT_GO_BUSSINES_ADDRESS:
                loadFragment(domicilioNegocioFragment, Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_ADDRESS_BACK:
                loadFragment(domicilioNegocioFragment, Direction.BACK, false);
                break;
            case EVENT_GO_BUSSINES_DOCUMENTS:
                loadFragment(documentosFragment, Direction.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_COMPLETE:
                loadFragment(registroNegocioCompletoFragment, Direction.FORDWARD, false);
                break;
        }
    }

    private void initFragments(){
        datosNegocioFragment = DatosNegocio.newInstance();
        domicilioNegocioFragment = DomicilioNegocio.newInstance();
        documentosFragment = Documentos.newInstance();
        registroNegocioCompletoFragment = RegistroNegocioCompletoFragment.newInstance();
    }
}

