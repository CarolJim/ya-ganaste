package com.pagatodo.yaganaste.ui._controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DetailTransactionFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.GetSignatureFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.InsertDongleFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.RemoveCardFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.TransactionResultFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.SaldoUyuFragment;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_PAYMENT;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_RETRY_PAYMENT;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;




public class AdqActivityUSB extends LoaderActivity implements OnEventListener {
    //Nuevo diseño-flujo
    public final static String EVENT_GO_INSERT_DONGLE = "EVENT_GO_INSERT_DONGLE";
    public final static String EVENT_GO_INSERT_DONGLEUSB = "EVENT_GO_INSERT_DONGLEUSB";
    public final static String EVENT_GO_INSERT_DONGLE_CANCELATION = "EVENT_GO_INSERT_DONGLE_CANCELATION";
    public final static String EVENT_GO_TRANSACTION_RESULT = "EVENT_GO_TRANSACTION_RESULT";
    public final static String EVENT_GO_GET_BALANCE_RESULT = "EVENT_GO_GET_BALANCE_RESULT";
    public final static String EVENT_GO_REMOVE_CARD = "EVENT_GO_REMOVE_CARD";
    public final static String EVENT_GO_GET_SIGNATURE = "EVENT_GO_GET_SIGNATURE";
    public final static String EVENT_GO_DETAIL_TRANSACTION = "EVENT_GO_DETAIL_TRANSACTION";
    public final static String EVENT_GO_LOGIN_FRAGMENT = "EVENT_GO_LOGIN_FRAGMENT";
    public final static String TYPE_TRANSACTION = "TYPE_TRANSACTION";
    public static String KEY_TRANSACTION_DATA = "KEYTRANSACTIONDATA";
    private Preferencias pref;
    private int idTransactionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);*/
        setContentView(R.layout.activity_fragment_container);
        pref = App.getInstance().getPrefs();
        idTransactionType = getIntent().getExtras().getInt(TYPE_TRANSACTION);
        onEvent(EVENT_GO_INSERT_DONGLE, null);
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
                // AQUI
                 App.getInstance().getPrefs().saveDataInt(MODE_CONNECTION_DONGLE, QPOSService.CommunicationMode.USB_OTG_CDC_ACM.ordinal());
                loadFragment(InsertDongleFragment.newInstance(App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE),
                        idTransactionType), Direction.FORDWARD, false);
                break;
            case EVENT_GO_INSERT_DONGLEUSB:
                // AQUI
                App.getInstance().getPrefs().saveDataInt(MODE_CONNECTION_DONGLE, QPOSService.CommunicationMode.USB_OTG_CDC_ACM.ordinal());
                finish();
                onBackPressed();
                break;
            case EVENT_GO_INSERT_DONGLE_CANCELATION:
                // AQUI
                loadFragment(InsertDongleFragment.newInstance(App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE),
                        idTransactionType), Direction.FORDWARD, false);
                break;
            case EVENT_GO_TRANSACTION_RESULT:
                loadFragment(TransactionResultFragment.newInstance(TransactionAdqData.getCurrentTransaction().getPageResult()),
                        Direction.FORDWARD, false);
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
                onBackPressed();
                break;
            case EVENT_GO_LOGIN_FRAGMENT:
                setResult(Activity.RESULT_OK);
                finish();
                break;
            case EVENT_RETRY_PAYMENT:
                loadFragment(InsertDongleFragment.newInstance(App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE),
                        idTransactionType), Direction.FORDWARD, false);
                break;
            case EVENT_PAYMENT:
                finish();
                break;
            case EVENT_GO_GET_BALANCE_RESULT:
                loadFragment(SaldoUyuFragment.newInstance(data.toString()), Direction.FORDWARD, false);
                showBack(false);
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

