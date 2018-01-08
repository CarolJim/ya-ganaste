package com.pagatodo.yaganaste.ui_wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.BussinesActivity;
import com.pagatodo.yaganaste.ui._controllers.TarjetaActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.maintabs.fragments.HomeTabFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.DepositsFragment;
import com.pagatodo.yaganaste.ui.preferuser.presenters.MyDongleFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.fragments.WalletTabFragment.ID_OPERATION;
import static com.pagatodo.yaganaste.utils.Constants.REGISTER_ADQUIRENTE_CODE;

public class WalletMainActivity extends SupportFragmentActivity implements View.OnClickListener {

    private static final int PAGE_EMISOR = 0, PAGE_ADQ = 1;
    @BindView(R.id.btn_back)
    AppCompatImageView back;
    //@BindView(R.id.main_tab)
    //TabLayout mainTab;

    private int idOperation;
    private int currentPage;

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

            if (getIntent().getExtras() != null) {
                currentPage = getIntent().getIntExtra("CURRENT_PAGE", 0);
                idOperation = getIntent().getIntExtra(ID_OPERATION, 0);
            }
            getLoadFragment(idOperation);
            //loadFragment(MovementsGenericFragment.newInstance(), R.id.fragment_container); ;
        }

    }

    private void getLoadFragment(int idoperation) {
        switch (idoperation) {
            case 1:
                loadFragment(HomeTabFragment.newInstance(currentPage), R.id.fragment_container);
                break;
            case 2:
                loadFragment(DepositsFragment.newInstance(), R.id.fragment_container);
                break;
            case 3:
                switch (currentPage) {
                    case PAGE_EMISOR:
                        startActivity(new Intent(this, TarjetaActivity.class));
                        finish();
                        break;
                    case PAGE_ADQ:
                        loadFragment(MyDongleFragment.newInstance(), R.id.fragment_container);
                        break;
                }
                break;
            case 7:
                startActivity(BussinesActivity.createIntent(this));
                break;
            default:
                Toast.makeText(this, "Proximamente", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_ADQUIRENTE_CODE) {
            showMainTab();
        }

    }

    protected void showMainTab() {
        /*if (mainTab.getVisibility() == View.GONE) {
            mainTab.setVisibility(View.VISIBLE);
        }*/
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
