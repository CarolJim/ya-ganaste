package com.pagatodo.yaganaste.ui.account.login;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.interfaces.IBalanceView;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.ILoginContainerManager;
import com.pagatodo.yaganaste.ui.account.IQuickBalanceManager;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.YaGanasteCard;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.StringConstants.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.StringConstants.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_SESSION;
import static com.pagatodo.yaganaste.utils.StringConstants.NAME_USER;
import static com.pagatodo.yaganaste.utils.StringConstants.UPDATE_DATE;
import static com.pagatodo.yaganaste.utils.StringConstants.UPDATE_DATE_BALANCE_ADQ;
import static com.pagatodo.yaganaste.utils.StringConstants.USER_BALANCE;

/**
 * Created by Jordan on 06/07/2017.
 */

public class QuickBalanceAdquirenteFragment extends GenericFragment implements IBalanceView,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    View rootView;

    @BindView(R.id.txtUserName)
    StyleTextView txtUserName;
    @BindView(R.id.cardBalanceAdq)
    YaGanasteCard cardBalanceAdq;
    @BindView(R.id.txtSaldoPersonal)
    MontoTextView txtSaldoPersonal;
    @BindView(R.id.txtBalanceReembolsar)
    MontoTextView txtBalanceReembolsar;
    @BindView(R.id.txtDateLastUpdate)
    StyleTextView txtDateLastUpdate;
    @BindView(R.id.txtDateAdqLastUpdate)
    StyleTextView txtDateAdqLastUpdate;
    @BindView(R.id.btnGoToLogin)
    Button btnGoToLogin;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.imgArrowNext)
    ImageView imgArrowNext;
    @BindView(R.id.imgArrowBack)
    AppCompatImageView imgArrowBack;

    private boolean isBalance;
    private boolean isBalanceAdq;

    private AccountPresenterNew accountPresenter;
    private ILoginContainerManager loginContainerManager;
    private IQuickBalanceManager quickBalanceManager;

    public static QuickBalanceAdquirenteFragment newInstance() {
        QuickBalanceAdquirenteFragment fragment = new QuickBalanceAdquirenteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        loginContainerManager = ((QuickBalanceContainerFragment) getParentFragment()).getLoginContainerManager();
        quickBalanceManager = ((QuickBalanceContainerFragment) getParentFragment()).getQuickBalanceManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quick_balance_adquirente, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        if (accountPresenter != null) {
            accountPresenter.setIView(this);
        }
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        swipeContainer.setOnRefreshListener(this);
        btnGoToLogin.setOnClickListener(this);
        imgArrowBack.setOnClickListener(this);
        imgArrowNext.setOnClickListener(this);

        Preferencias preferencias = App.getInstance().getPrefs();
        if (preferencias.containsData(HAS_SESSION)) {
            String cardNumber = preferencias.loadData(CARD_NUMBER);

            cardBalanceAdq.setCardNumber(StringUtils.ocultarCardNumberFormat(cardNumber));
            cardBalanceAdq.setCardDate("03/21");

            if (Build.VERSION.SDK_INT >= 24) {
                txtUserName.setText(Html.fromHtml(getString(R.string.bienvenido_usuario,
                        preferencias.loadData(NAME_USER)),
                        Html.FROM_HTML_MODE_LEGACY, null, null));
            } else {
                txtUserName.setText(Html.fromHtml(getString(R.string.bienvenido_usuario, preferencias.loadData(NAME_USER))));
            }
            onRefresh();
            //setData(preferencias.loadData(StringConstants.USER_BALANCE), preferencias.loadData(UPDATE_DATE));
            //setDataAdq(preferencias.loadData(ADQUIRENTE_BALANCE), preferencias.loadData(UPDATE_DATE_BALANCE_ADQ));
        }
    }

    private void setData(String balance, String updateDate) {
        txtSaldoPersonal.setText(Utils.getCurrencyValue(balance));
        txtDateLastUpdate.setText(String.format(getString(R.string.last_date_update), updateDate));
    }

    private void setDataAdq(String balaceAdq, String dateLastUpdateAdq) {
        txtDateAdqLastUpdate.setText(String.format(getString(R.string.last_date_update), dateLastUpdateAdq));
        txtBalanceReembolsar.setText(Utils.getCurrencyValue(balaceAdq));
    }

    @Override
    public void onRefresh() {
        isBalance = false;
        isBalanceAdq = false;
        swipeContainer.setRefreshing(false);
        accountPresenter.updateBalance();
        accountPresenter.updateBalanceAdq();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoToLogin:
                loginContainerManager.loadLoginFragment();
                break;
            case R.id.imgArrowBack:
                backScreen(null, null);
                break;
            case R.id.imgArrowNext:
                nextScreen(null, null);
                break;
        }
    }

    @Override
    public void updateBalance() {
        Preferencias prefs = App.getInstance().getPrefs();
        setData(prefs.loadData(USER_BALANCE), prefs.loadData(UPDATE_DATE));
        isBalance = true;
        hideLoaderBalance();
    }

    @Override
    public void updateBalanceAdq() {
        Preferencias prefs = App.getInstance().getPrefs();
        setDataAdq(prefs.loadData(ADQUIRENTE_BALANCE), prefs.loadData(UPDATE_DATE_BALANCE_ADQ));
        isBalanceAdq = true;
        hideLoaderBalance();
    }

    private void hideLoaderBalance() {
        hideLoader();
    }

    @Override
    public void showLoader(String message) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        if (isBalance && isBalanceAdq && onEventListener != null) {
            onEventListener.onEvent(EVENT_HIDE_LOADER, null);
        }
    }

    @Override
    public void showError(Object error) {
        //throw new IllegalCallException("this method is not implemented yet");
    }


    @Override
    public void nextScreen(String event, Object data) {
        quickBalanceManager.nextPage();
    }

    @Override
    public void backScreen(String event, Object data) {
        quickBalanceManager.backPage();
    }
}
