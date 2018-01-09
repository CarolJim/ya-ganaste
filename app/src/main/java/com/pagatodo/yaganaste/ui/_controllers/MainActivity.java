package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.account.login.MainFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.GO_TO_LOGIN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.IS_FROM_TIMER;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_SESSION;

public class MainActivity extends ToolBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_conainer);
        initViews();

        String action = getIntent().getExtras().getString(SELECTION);
        if (action.equals(MAIN_SCREEN)) {
            Preferencias prefs = App.getInstance().getPrefs();
            if (prefs.containsData(HAS_SESSION) && !RequestHeaders.getTokenauth().isEmpty()) {
                Intent intent = new Intent(this, AccountActivity.class);
                intent.putExtra(SELECTION, GO_TO_LOGIN);
                intent.putExtra(IS_FROM_TIMER, getIntent().getExtras().getBoolean(IS_FROM_TIMER, false));
                startActivity(intent);
                finish();
            } else {
                loadFragment(MainFragment.newInstance(), true);
                if (getIntent().getExtras().getBoolean(IS_FROM_TIMER, false)) {
                    UI.createSimpleCustomDialog(getString(R.string.app_name), getString(R.string.close_sesion_bodynuevo),
                            this.getSupportFragmentManager(), CustomErrorDialog.class.getSimpleName());
                }
            }
        }

    }

    private void initViews() {
        changeToolbarVisibility(false);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }
}

