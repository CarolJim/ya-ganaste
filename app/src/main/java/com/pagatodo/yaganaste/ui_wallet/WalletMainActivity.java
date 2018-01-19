package com.pagatodo.yaganaste.ui_wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.AdqActivity;
import com.pagatodo.yaganaste.ui._controllers.BussinesActivity;
import com.pagatodo.yaganaste.ui._controllers.TarjetaActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DetailTransactionFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.GetMountFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.GetSignatureFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.InsertDongleFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.RemoveCardFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.TransactionResultFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.HomeTabFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.DepositsFragment;
import com.pagatodo.yaganaste.ui.preferuser.presenters.MyDongleFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_RETRY_PAYMENT;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_DETAIL_TRANSACTION;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_GET_SIGNATURE;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_INSERT_DONGLE;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_LOGIN_FRAGMENT;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_TRANSACTION_RESULT;
import static com.pagatodo.yaganaste.ui_wallet.fragments.WalletTabFragment.ID_OPERATION;
import static com.pagatodo.yaganaste.utils.Constants.REGISTER_ADQUIRENTE_CODE;

public class WalletMainActivity extends LoaderActivity implements View.OnClickListener {

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
            case 6:
                loadFragment(GetMountFragment.newInstance(), R.id.fragment_container);
                break;
            case 7:
                startActivity(BussinesActivity.createIntent(this));
                finish();
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
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event){
            case EVENT_GO_INSERT_DONGLE:
                loadFragment(InsertDongleFragment.newInstance(),  R.id.fragment_container, Direction.FORDWARD, false);
                break;
            case EVENT_GO_TRANSACTION_RESULT:
                loadFragment(TransactionResultFragment.newInstance(TransactionAdqData.getCurrentTransaction().getPageResult()),
                        R.id.fragment_container, Direction.FORDWARD, false);
                showBack(false);
                break;
            case AdqActivity.EVENT_GO_REMOVE_CARD:
                loadFragment(RemoveCardFragment.newInstance(), R.id.fragment_container,Direction.FORDWARD, false);
                showBack(false);
                break;
            case EVENT_GO_GET_SIGNATURE:
                loadFragment(GetSignatureFragment.newInstance(), R.id.fragment_container,Direction.FORDWARD, false);
                showBack(false);
                break;
            case EVENT_GO_DETAIL_TRANSACTION:
                loadFragment(DetailTransactionFragment.newInstance(), R.id.fragment_container,Direction.FORDWARD, false);
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
                loadFragment(InsertDongleFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD, false);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }

}
