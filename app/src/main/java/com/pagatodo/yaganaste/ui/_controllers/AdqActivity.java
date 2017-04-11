package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.account.register.DatosNegocio;
import com.pagatodo.yaganaste.ui.account.register.Documentos;
import com.pagatodo.yaganaste.ui.account.register.DomicilioNegocio;
import com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment;
import com.pagatodo.yaganaste.ui.adquirente.DetailTransactionFragment;
import com.pagatodo.yaganaste.ui.adquirente.GetSignatureFragment;
import com.pagatodo.yaganaste.ui.adquirente.InsertDongleFragment;
import com.pagatodo.yaganaste.ui.adquirente.TransactionResultFragment;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment.COMPLETE_MESSAGES.ADQ_REVISION;


public class AdqActivity extends SupportFragmentActivity implements OnEventListener{
    private Preferencias pref;

    //Nuevo diseño-flujo
    public final static String EVENT_GO_INSERT_DONGLE = "EVENT_GO_INSERT_DONGLE";
    public final static String EVENT_GO_TRANSACTION_RESULT = "EVENT_GO_TRANSACTION_RESULT";
    public final static String EVENT_GO_GET_SIGNATURE = "EVENT_GO_GET_SIGNATURE";
    public final static String EVENT_GO_DETAIL_TRANSACTION = "EVENT_GO_DETAIL_TRANSACTION";

    private DatosNegocio datosNegocioFragment;
    private DomicilioNegocio domicilioNegocioFragment;
    private Documentos documentosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity);
        pref = App.getInstance().getPrefs();
        App.getInstance().initEMVListener();
        onEvent(EVENT_GO_INSERT_DONGLE,null);
    }

    @Override
    public void onEvent(String event, Object o) {
        switch (event){
            case EVENT_GO_INSERT_DONGLE:
                loadFragment(InsertDongleFragment.newInstance(), DIRECTION.FORDWARD, false);
                break;
            case EVENT_GO_TRANSACTION_RESULT:
                loadFragment(TransactionResultFragment.newInstance(), DIRECTION.FORDWARD, false);
                break;
            case EVENT_GO_GET_SIGNATURE:
                loadFragment(GetSignatureFragment.newInstance(), DIRECTION.FORDWARD, false);
                break;
            case EVENT_GO_DETAIL_TRANSACTION:
                loadFragment(DetailTransactionFragment.newInstance(), DIRECTION.FORDWARD, false);
                break;

            case EVENT_GO_MAINTAB:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getCurrentFragment();
        fragment.onActivityResult(requestCode, resultCode, data);
    }

}

