package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.adquirente.DetailTransactionFragment;
import com.pagatodo.yaganaste.ui.adquirente.GetSignatureFragment;
import com.pagatodo.yaganaste.ui.adquirente.InsertDongleFragment;
import com.pagatodo.yaganaste.ui.adquirente.RemoveCardFragment;
import com.pagatodo.yaganaste.ui.adquirente.TransactionResultFragment;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;


public class AdqActivity extends SupportFragmentActivity implements OnEventListener{
    private Preferencias pref;

    public static String KEY_TRANSACTION_DATA = "KEYTRANSACTIONDATA";
    //Nuevo diseño-flujo
    public final static String EVENT_GO_INSERT_DONGLE = "EVENT_GO_INSERT_DONGLE";
    public final static String EVENT_GO_TRANSACTION_RESULT = "EVENT_GO_TRANSACTION_RESULT";
    public final static String EVENT_GO_REMOVE_CARD = "EVENT_GO_REMOVE_CARD";
    public final static String EVENT_GO_GET_SIGNATURE = "EVENT_GO_GET_SIGNATURE";
    public final static String EVENT_GO_DETAIL_TRANSACTION = "EVENT_GO_DETAIL_TRANSACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment_conainer);
        pref = App.getInstance().getPrefs();
        onEvent(EVENT_GO_INSERT_DONGLE,null);
        //App.getInstance().initEMVListener();

    }

    @Override
    public void onEvent(String event, Object data) {
        switch (event){
            case EVENT_GO_INSERT_DONGLE:
                loadFragment(InsertDongleFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_GO_TRANSACTION_RESULT:
                loadFragment(TransactionResultFragment.newInstance(TransactionAdqData.getCurrentTransaction().getPageResult()), Direction.FORDWARD, false);
                break;
            case EVENT_GO_REMOVE_CARD:
                loadFragment(RemoveCardFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_GO_GET_SIGNATURE:
                loadFragment(GetSignatureFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_GO_DETAIL_TRANSACTION:
                loadFragment(DetailTransactionFragment.newInstance(), Direction.FORDWARD, false);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

}

