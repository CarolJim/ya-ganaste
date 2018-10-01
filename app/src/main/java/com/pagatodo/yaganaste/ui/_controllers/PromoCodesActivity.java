package com.pagatodo.yaganaste.ui._controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.account.login.LoginManagerContainerFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.PromocionesDetalleFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.PromocionesFragment;

public class PromoCodesActivity  extends LoaderActivity  implements OnEventListener {

    private PromocionesDetalleFragment containerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_codes);
        showToolbarHelp(true);
        containerFragment = PromocionesDetalleFragment.newInstance();
        loadFragment(containerFragment);
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }



}
