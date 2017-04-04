package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.account.register.DatosNegocio;
import com.pagatodo.yaganaste.ui.account.register.Documentos;
import com.pagatodo.yaganaste.ui.account.register.DomicilioNegocio;
import com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment.COMPLETE_MESSAGES.ADQ_REVISION;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity);
       // initFragments();
        loadFragment(Documentos.newInstance(), DIRECTION.FORDWARD, true);
        pref = App.getInstance().getPrefs();
    }

    @Override
    public void onEvent(String event, Object o) {
        switch (event){
            case EVENT_GO_BUSSINES_DATA:
                loadFragment(DatosNegocio.newInstance(), DIRECTION.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_DATA_BACK:
                loadFragment(DatosNegocio.newInstance(), DIRECTION.BACK, false);
                break;
            case EVENT_GO_BUSSINES_ADDRESS:
                loadFragment(DomicilioNegocio.newInstance(), DIRECTION.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_ADDRESS_BACK:
                loadFragment(DomicilioNegocio.newInstance(), DIRECTION.BACK, false);
                break;
            case EVENT_GO_BUSSINES_DOCUMENTS:
                loadFragment(Documentos.newInstance(), DIRECTION.FORDWARD, false);
                break;
            case EVENT_GO_BUSSINES_COMPLETE:
                loadFragment(RegisterCompleteFragment.newInstance(ADQ_REVISION), DIRECTION.FORDWARD, false);
                break;

            case EVENT_GO_MAINTAB:
                resetRegisterData();
                finish();
                break;
        }
    }

    private void initFragments(){
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

    private void resetRegisterData(){
        RegisterAgent.resetRegisterAgent();
    }
}

