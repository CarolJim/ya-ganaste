package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.maintabs.fragments.EnviosFromFragmentNewVersion;

import static com.pagatodo.yaganaste.ui_wallet.fragments.SendWalletFragment.MONTO;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;

public class EnvioFormularioWallet extends LoaderActivity implements OnEventListener {
    public final static String EVENT_GO_INSERT_DONGLE = "EVENT_GO_INSERT_DONGLE";
    private Preferencias pref;
    private Float monto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment_conainer);
        monto = (float) getIntent().getExtras().get(MONTO);
        pref = App.getInstance().getPrefs();
        onEvent(EVENT_GO_INSERT_DONGLE, null);
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case EVENT_GO_INSERT_DONGLE:
                loadFragment(EnviosFromFragmentNewVersion.newInstance(monto), Direction.FORDWARD, false);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONTACTS_CONTRACT) {
            getCurrentFragment().onActivityResult(requestCode, resultCode, data);
        }
    }
}
