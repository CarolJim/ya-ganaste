package com.pagatodo.yaganaste.ui_wallet;

import android.os.Bundle;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.maintabs.fragments.HomeTabFragment;

public class WalletMainActivity extends SupportFragmentActivity{


    int currentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            if (getIntent().getExtras() != null){
                currentPage = getIntent().getIntExtra("CURRENT_PAGE",0);
            }
            loadFragment(HomeTabFragment.newInstance(currentPage),R.id.fragment_container);
            //loadFragment(MovementsGenericFragment.newInstance(), R.id.fragment_container);
        }

    }

    @Override
    public boolean requiresTimer() {

        return false;
    }

}
