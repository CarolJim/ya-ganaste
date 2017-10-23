package com.pagatodo.yaganaste.ui._controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.Window;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DetailTransactionFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.GetSignatureFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.InsertDongleFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.RemoveCardFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.TransactionResultFragment;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_RETRY_PAYMENT;


public class AdqActivity extends LoaderActivity implements OnEventListener {
    //Nuevo diseño-flujo
    public final static String EVENT_GO_INSERT_DONGLE = "EVENT_GO_INSERT_DONGLE";
    public final static String EVENT_GO_TRANSACTION_RESULT = "EVENT_GO_TRANSACTION_RESULT";
    public final static String EVENT_GO_REMOVE_CARD = "EVENT_GO_REMOVE_CARD";
    public final static String EVENT_GO_GET_SIGNATURE = "EVENT_GO_GET_SIGNATURE";
    public final static String EVENT_GO_DETAIL_TRANSACTION = "EVENT_GO_DETAIL_TRANSACTION";
    public final static String EVENT_GO_LOGIN_FRAGMENT = "EVENT_GO_LOGIN_FRAGMENT";
    public static String KEY_TRANSACTION_DATA = "KEYTRANSACTIONDATA";
    private Preferencias pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment_conainer);
        pref = App.getInstance().getPrefs();
        onEvent(EVENT_GO_INSERT_DONGLE, null);
        //App.getInstance().initEMVListener();

    }

    /**
     * Sobre escribimos el metodo del PAdre ToolBar para no tener el boton que nos abre esta+
     * activitydad
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case EVENT_GO_INSERT_DONGLE:
                loadFragment(InsertDongleFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_GO_TRANSACTION_RESULT:
                loadFragment(TransactionResultFragment.newInstance(TransactionAdqData.getCurrentTransaction().getPageResult()), Direction.FORDWARD, false);
                showBack(false);
                break;
            case EVENT_GO_REMOVE_CARD:
                loadFragment(RemoveCardFragment.newInstance(), Direction.FORDWARD, false);
                showBack(false);
                break;
            case EVENT_GO_GET_SIGNATURE:
                loadFragment(GetSignatureFragment.newInstance(), Direction.FORDWARD, false);
                showBack(false);
                break;
            case EVENT_GO_DETAIL_TRANSACTION:
                loadFragment(DetailTransactionFragment.newInstance(), Direction.FORDWARD, false);
                showBack(false);
                break;
            case EVENT_GO_MAINTAB:
                finish();
                break;
            case EVENT_GO_LOGIN_FRAGMENT:
                setResult(Activity.RESULT_OK);
                finish();
                break;
            case EVENT_RETRY_PAYMENT:
                loadFragment(InsertDongleFragment.newInstance(), Direction.FORDWARD, false);
                break;
        }
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getCurrentFragment();
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (!isLoaderShow && getCurrentFragment() instanceof InsertDongleFragment) {
            super.onBackPressed();
        }
    }
}

