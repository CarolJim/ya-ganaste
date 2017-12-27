package com.pagatodo.yaganaste.ui_wallet;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.maintabs.fragments.HomeTabFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletMainActivity extends SupportFragmentActivity implements View.OnClickListener{

    @BindView(R.id.btn_back)
    AppCompatImageView back;

    int currentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_main);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
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

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
