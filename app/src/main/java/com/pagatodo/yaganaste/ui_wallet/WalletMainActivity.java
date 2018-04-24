package com.pagatodo.yaganaste.ui_wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.interfaces.enums.EstatusMovimientoAdquirente;
import com.pagatodo.yaganaste.ui._controllers.AdqActivity;
import com.pagatodo.yaganaste.ui._controllers.BussinesActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DetailTransactionFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DocumentosFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.GetMountFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.GetSignatureFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.InsertDongleFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.RemoveCardFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.TransactionResultFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DetailsAdquirenteFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DetailsEmisorFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PersonalAccountFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.DepositsDataFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyCardReportaTarjetaFragment;
import com.pagatodo.yaganaste.ui.preferuser.MyChangeNip;
import com.pagatodo.yaganaste.ui.preferuser.presenters.MyDongleFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.AdminCardsFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.AdministracionFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.LoginStarbucks;
import com.pagatodo.yaganaste.ui_wallet.fragments.RewardsStarbucksFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.MapStarbucksFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.TimeRepaymentFragment;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_MAINTAB;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_RETRY_PAYMENT;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_DETAIL_TRANSACTION;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_GET_SIGNATURE;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_INSERT_DONGLE;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_INSERT_DONGLE_CANCELATION;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_LOGIN_FRAGMENT;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_TRANSACTION_RESULT;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.REQUEST_CODE_FAVORITES;
import static com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment.RESULT_CANCEL_OK;
import static com.pagatodo.yaganaste.ui_wallet.fragments.WalletTabFragment.ITEM_OPERATION;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ADMON_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ADMON_EMISOR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ADMON_STARBUCK;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_DEPOSITO;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_MVIMIENTOS_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_MVIMIENTOS_EMISOR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_MVIMIENTOS_STARBUCKS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_RECOMPENSAS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_SETTINGSCARD;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_SUCURSALES;
import static com.pagatodo.yaganaste.utils.Constants.REGISTER_ADQUIRENTE_CODE;

public class WalletMainActivity extends LoaderActivity implements View.OnClickListener {

    public final static String EVENT_GO_NIP_CHANGE = "EVENT_GO_NIP_CHANGE";
    public final static String EVENT_GO_CONFIG_REPAYMENT = "EVENT_GO_CONFIG_REPAYMENT";
    public final static String EVENT_GO_CONFIG_REPAYMENT_BACK = "EVENT_GO_CONFIG_REPAYMENT_BACK";
    public final static String EVENT_GO_CARD_REPORT = "EVENT_GO_CARD_REPORD";
    public final static String EVENT_GO_DETAIL_EMISOR = "EVENT_GO_DETAIL_EMISOR";
    public final static String EVENT_GO_DETAIL_ADQ = "EVENT_GO_DETAIL_ADQ";
    public final static String EVENT_GO_TO_FINALIZE_SUCCESS = "FINALIZAR_CANCELACION_SUCCESS";
    public final static String EVENT_GO_TO_FINALIZE_ERROR = "FINALIZAR_CANCELACION_ERROR";
    public final static String EVENT_GO_TO_LOGIN_STARBUCKS = "EVENT_GO_TO_LOGIN_STARBUCKS";
    public final static String EVENT_GO_TO_REGISTER_STARBUCKS = "EVENT_GO_TO_REGISTER_STARBUCKS";
    private static final int ACTION_SHARE = 0, ACTION_CANCEL_CHARGE = 1;
    public static final int REQUEST_CHECK_SETTINGS = 91, MY_PERMISSIONS_REQUEST_PHONE = 100;

    @BindView(R.id.toolbar_wallet)
    Toolbar toolbar;

    private Menu menu;
    private ElementView itemOperation;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_main);
        this.init();
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            if (getIntent().getExtras() != null) {
                itemOperation = (ElementView) getIntent().getSerializableExtra(ITEM_OPERATION);
                getLoadFragment(itemOperation.getIdOperacion());
            }
            //loadFragment(MovementsGenericFragment.newInstance(), R.id.fragment_container);
        }
    }

    public void showToolbarShadow() {
        ///android:background="@color/colorAccent"
        toolbar.setBackgroundResource(R.drawable.background_toolbar);
    }

    private void init() {
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
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_wallet, menu);
        if (itemOperation.getIdOperacion() == OPTION_DEPOSITO) {
            menu.getItem(ACTION_SHARE).setVisible(true);
        } else if (itemOperation.getIdOperacion() == OPTION_MVIMIENTOS_ADQ && getCurrentFragment() instanceof DetailsAdquirenteFragment) {
            menu.getItem(ACTION_CANCEL_CHARGE).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getCurrentFragment() instanceof TimeRepaymentFragment) {
                    onEvent(EVENT_GO_CONFIG_REPAYMENT_BACK, null);
                } else {
                    onBackPressed();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getLoadFragment(int idoperation) {
        switch (idoperation) {
            case OPTION_MVIMIENTOS_EMISOR:
                loadFragment(PersonalAccountFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_MVIMIENTOS_ADQ:
                loadFragment(PaymentsFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_MVIMIENTOS_STARBUCKS:
                finish();
                break;
            case OPTION_DEPOSITO:
                loadFragment(DepositsDataFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_ADMON_EMISOR:
                loadFragment(AdministracionFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_ADMON_ADQ:
                loadFragment(MyDongleFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_ADMON_STARBUCK:
                finish();
                break;
            case 6:
                loadFragment(GetMountFragment.newInstance(), R.id.fragment_container);
                break;
            case 7:
                startActivity(BussinesActivity.createIntent(this));
                finish();
                break;
            case 12:
                loadFragment(DocumentosFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_RECOMPENSAS:
                loadFragment(RewardsStarbucksFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_SUCURSALES:
                loadFragment(MapStarbucksFragment.newInstance(), R.id.fragment_container);
                break;
            case OPTION_SETTINGSCARD:
                loadFragment(AdminCardsFragment.newInstance(), R.id.fragment_container);
                break;
            default:
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
        if (requestCode == REQUEST_CODE_FAVORITES) {
            switch (itemOperation.getIdOperacion()) {
                case OPTION_MVIMIENTOS_EMISOR:
                    loadFragment(PersonalAccountFragment.newInstance(), R.id.fragment_container);
                    break;
                case OPTION_MVIMIENTOS_ADQ:
                    loadFragment(PaymentsFragment.newInstance(), R.id.fragment_container);
                    break;
            }
        }
        if (requestCode == DocumentosFragment.REQUEST_TAKE_PHOTO || requestCode == DocumentosFragment.SELECT_FILE_PHOTO) {
            getCurrentFragment().onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case EVENT_GO_INSERT_DONGLE:
                loadFragment(InsertDongleFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD, false);
                break;
            case EVENT_GO_INSERT_DONGLE_CANCELATION:
                loadFragment(InsertDongleFragment.newInstance(true, (DataMovimientoAdq) data), R.id.fragment_container, Direction.FORDWARD, true);
                menu.getItem(ACTION_CANCEL_CHARGE).setVisible(false);
                break;
            case EVENT_GO_TRANSACTION_RESULT:
                loadFragment(TransactionResultFragment.newInstance(TransactionAdqData.getCurrentTransaction().getPageResult()),
                        R.id.fragment_container, Direction.FORDWARD, false);
                showBack(false);
                break;
            case AdqActivity.EVENT_GO_REMOVE_CARD:
                loadFragment(RemoveCardFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD, false);
                showBack(false);
                break;
            case EVENT_GO_GET_SIGNATURE:
                loadFragment(GetSignatureFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD, false);
                showBack(false);
                break;
            case EVENT_GO_DETAIL_TRANSACTION:
                loadFragment(DetailTransactionFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD, false);
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
            case EVENT_GO_CARD_REPORT:
                loadFragment(MyCardReportaTarjetaFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD, true);
                break;
            case EVENT_GO_CONFIG_REPAYMENT:
                loadFragment(TimeRepaymentFragment.newInstance(), R.id.fragment_container, Direction.FORDWARD, false);
                break;
            case EVENT_GO_CONFIG_REPAYMENT_BACK:
                loadFragment(MyDongleFragment.newInstance(), R.id.fragment_container, Direction.BACK, false);
                break;
            case EVENT_GO_DETAIL_EMISOR:
                loadFragment(DetailsEmisorFragment.newInstance((MovimientosResponse) data), R.id.fragment_container, Direction.FORDWARD, true);
                break;
            case EVENT_GO_DETAIL_ADQ:
                loadFragment(DetailsAdquirenteFragment.newInstance((DataMovimientoAdq) data), R.id.fragment_container, Direction.FORDWARD, true);
                if (((DataMovimientoAdq) data).getEstatus().equals(EstatusMovimientoAdquirente.POR_REEMBOLSAR.getId()))
                    menu.getItem(ACTION_CANCEL_CHARGE).setVisible(true);
                break;
            case EVENT_GO_TO_FINALIZE_SUCCESS:
                setResult(RESULT_CANCEL_OK);
                this.finish();
                break;
            case EVENT_GO_TO_FINALIZE_ERROR:
                setResult(-1);
                this.finish();
                break;
            case EVENT_GO_TO_LOGIN_STARBUCKS:
                loadFragment(LoginStarbucks.newInstance(), R.id.fragment_container, Direction.FORDWARD);
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
            getCurrentFragment().onRequestPermissionsResult(requestCode, permissions, grantResults);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getCurrentFragment();
        if (fragment instanceof LoginStarbucks) {
            loadFragment(AdminCardsFragment.newInstance(), R.id.fragment_container, Direction.BACK);
        } else {
            super.onBackPressed();
        }
    }
}
