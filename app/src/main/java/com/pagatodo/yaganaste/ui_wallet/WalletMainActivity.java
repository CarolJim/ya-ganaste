package com.pagatodo.yaganaste.ui_wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;
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
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.DepositsDataFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.DepositsFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyChangeNip;
import com.pagatodo.yaganaste.ui.preferuser.presenters.MyDongleFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.AdministracionFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.MovementsEmisorFragment;

import java.security.Provider;

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

    public final static String EVENT_GO_NIP_CHANGE = "EVENT_GO_NIP_CHANGE";
    private static final int PAGE_EMISOR = 0, PAGE_ADQ = 1;
    public static final int REQUEST_CHECK_SETTINGS = 91;


    @BindView(R.id.toolbar_wallet)
    Toolbar toolbar;
    @BindView(R.id.btn_back)
    AppCompatImageView back;

    private int idOperation;
    private int currentPage;

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_main);
        this.init();
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
            //loadFragment(MovementsGenericFragment.newInstance(), R.id.fragment_container);
        }

    }

    public void showToolbarShadow(){
            ///android:background="@color/colorAccent"
        toolbar.setBackgroundResource(R.drawable.bacgraund_tooblar);

    }

    private void init(){
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public static void showToolBarMenu() {
        //getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        switch (idOperation){
            case 2:
                getMenuInflater().inflate(R.menu.menu_wallet, menu);
                /*MenuItem edit_item = menu.add(0, R.menu.menu_wallet, 0, R.string.item_menu_share);
                edit_item.setIcon(R.drawable.ic_share_toolbar);
                edit_item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);

                menu.add(Menu.NONE, MENU_ITEM_ITEM1, Menu.NONE, "Item name");

                MenuItem delete_item = menu.add(0, MenuItem_DeleteId, 1, R.string.edit);
                delete_item.setIcon(R.drawable.delete);
                delete_item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);*/
                //return super.onCreateOptionsMenu(menu);
                return true;
                default:
                    return false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    private void getLoadFragment(int idoperation) {
        switch (idoperation) {
            case 1:
                loadFragment(HomeTabFragment.newInstance(currentPage), R.id.fragment_container);
                //loadFragment(MovementsEmisorFragment.newInstance(), R.id.fragment_container);
                break;
            case 2:
                loadFragment(DepositsDataFragment.newInstance(), R.id.fragment_container);
                break;
            case 3:
                switch (currentPage) {
                    case PAGE_EMISOR:
                        loadFragment(AdministracionFragment.newInstance(), R.id.fragment_container);
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
        } else {
            if (data != null) {
                getCurrentFragment().onActivityResult(requestCode, resultCode, data);
            }
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

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
        /* Fragment actualFragment = mainViewPagerAdapter.getItem(mainViewPager.getCurrentItem());
        if (!disableBackButton) {
            if (actualFragment instanceof PaymentsTabFragment) {
                PaymentsTabFragment paymentsTabFragment = (PaymentsTabFragment) actualFragment;
                if (paymentsTabFragment.isOnForm) {
                    paymentsTabFragment.onBackPresed(paymentsTabFragment.getCurrenTab());
                } else {
                    goHome();
                }
            } else if (actualFragment instanceof DepositsFragment) {
                imageNotification.setVisibility(View.GONE);
                imageshare.setVisibility(View.VISIBLE);
                ((DepositsFragment) actualFragment).getDepositManager().onBtnBackPress();
            } else if (actualFragment instanceof GetMountFragment) {
                goHome();
            } else if (actualFragment instanceof HomeTabFragment) {
                showDialogOut();
            } else if (actualFragment instanceof WalletTabFragment) {
                showDialogOut();
            } else if (actualFragment instanceof SendWalletFragment) {
                goHome();
            } else {
                goHome();
            }
        }
    }*/

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
            case EVENT_GO_NIP_CHANGE:
                loadFragment(MyChangeNip.newInstance(), R.id.fragment_container, Direction.FORDWARD, true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            getCurrentFragment().onRequestPermissionsResult(requestCode,permissions,grantResults);

        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
