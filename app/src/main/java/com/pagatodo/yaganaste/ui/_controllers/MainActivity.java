package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.os.Bundle;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.PageResult;
import com.pagatodo.yaganaste.interfaces.Command;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.account.login.MainFragment;
import com.pagatodo.yaganaste.ui.adquirente.TransactionResultFragment;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.NO_SIM_CARD;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;

public class MainActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_conainer);
        initViews();
        String action = getIntent().getExtras().getString(SELECTION);
        if (action.equals(NO_SIM_CARD)) {
            loadFragment(TransactionResultFragment.newInstance(getPageResultNiSIM()));
        } else if (action.equals(MAIN_SCREEN)) {
            loadFragment(MainFragment.newInstance(), true);
        }
    }

    private void initViews() {
        changeToolbarVisibility(false);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * Construimos el PageResult a mostrar en caso de no tener tarjeta SIM
     */
    private PageResult getPageResultNiSIM() {
        PageResult pageResult = new PageResult();
        pageResult.setIdResurceIcon(R.drawable.warning_canvas);
        pageResult.setTitle(getString(R.string.no_sim_titulo));
        pageResult.setMessage(getString(R.string.no_sim_mensaje));
        pageResult.setDescription(getString(R.string.no_sim_desc));
        pageResult.setNamerBtnPrimary(getString(R.string.entendido_titulo));
        pageResult.setActionBtnPrimary(new Command() {
            @Override
            public void action(Context context, Object[] params) {
                finish();
            }
        });

        return pageResult;
    }

}

