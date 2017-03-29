package com.pagatodo.yaganaste.ui._controllers;

import android.os.Bundle;
import android.view.Window;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.account.login.LoginFragment;
import com.pagatodo.yaganaste.ui.account.register.AreYouWantGetPaymentsFragment;
import com.pagatodo.yaganaste.ui.account.register.AsociatePhoneAccountFragment;
import com.pagatodo.yaganaste.ui.account.register.Couchmark;
import com.pagatodo.yaganaste.ui.account.register.DatosNegocio;
import com.pagatodo.yaganaste.ui.account.register.DatosPersonalesFragment;
import com.pagatodo.yaganaste.ui.account.register.DatosUsuarioFragment;
import com.pagatodo.yaganaste.ui.account.register.Documentos;
import com.pagatodo.yaganaste.ui.account.register.DomicilioActualFragment;
import com.pagatodo.yaganaste.ui.account.register.DomicilioNegocio;
import com.pagatodo.yaganaste.ui.account.register.PinConfirmationFragment;
import com.pagatodo.yaganaste.ui.account.register.RegisterAddressFragment;
import com.pagatodo.yaganaste.ui.account.register.RegisterBasicInfoFragment;
import com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment;
import com.pagatodo.yaganaste.ui.account.register.RegistroNegocioCompletoFragment;
import com.pagatodo.yaganaste.ui.account.register.TienesTarjetaFragment;


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
        loadFragment(datosNegocioFragment, DIRECTION.FORDWARD, true);
        pref = App.getInstance().getPrefs();
    }

    @Override
    public void onEvent(String event, Object o) {
        switch (event){
            case EVENT_GO_BUSSINES_DATA:
                loadFragment(datosNegocioFragment, DIRECTION.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_DATA_BACK:
                loadFragment(datosNegocioFragment, DIRECTION.BACK, false);
                break;
            case EVENT_GO_BUSSINES_ADDRESS:
                loadFragment(domicilioNegocioFragment, DIRECTION.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_ADDRESS_BACK:
                loadFragment(domicilioNegocioFragment, DIRECTION.BACK, false);
                break;
            case EVENT_GO_BUSSINES_DOCUMENTS:
                loadFragment(documentosFragment, DIRECTION.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_COMPLETE:
                loadFragment(registroNegocioCompletoFragment, DIRECTION.FORDWARD, false);
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

