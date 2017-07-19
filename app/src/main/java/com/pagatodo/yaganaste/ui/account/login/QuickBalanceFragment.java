package com.pagatodo.yaganaste.ui.account.login;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.interfaces.IBalanceView;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.ILoginContainerManager;
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.YaGanasteCard;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.StringConstants.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_SESSION;
import static com.pagatodo.yaganaste.utils.StringConstants.NAME_USER;
import static com.pagatodo.yaganaste.utils.StringConstants.UPDATE_DATE;

/**
 * @author Juan Guerra on 15/06/2017.
 */

public class QuickBalanceFragment extends GenericFragment implements IBalanceView,
        SwipeRefreshLayout.OnRefreshListener, INavigationView<Void, Void>, View.OnClickListener {

    @BindView(R.id.cardSaldo)
    YaGanasteCard cardSaldo;
    @BindView(R.id.txt_name_user)
    StyleTextView txtNameUser;
    @BindView(R.id.txt_saldo)
    MontoTextView txtSaldo;
    @BindView(R.id.txt_date_updated)
    StyleTextView txtDateUpdated;
    @BindView(R.id.btnGoToLogin)
    Button goToLogin;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    private View mRootView;
    private AccountPresenterNew accountPresenter;
    private ILoginContainerManager loginContainerManager;

    public static QuickBalanceFragment newInstance() {

        Bundle args = new Bundle();

        QuickBalanceFragment fragment = new QuickBalanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quick_balance, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        loginContainerManager = ((QuickBalanceContainerFragment) getParentFragment()).getLoginContainerManager();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            if (accountPresenter != null) {
                accountPresenter.setIView(this);
            }
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mRootView = view;
        if (accountPresenter != null) {
            accountPresenter.setIView(this);
        }
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, mRootView);

        swipeContainer.setOnRefreshListener(this);
        goToLogin.setOnClickListener(this);

        Preferencias preferencias = App.getInstance().getPrefs();
        if (preferencias.containsData(HAS_SESSION)) {

            String cardNumber = preferencias.loadData(CARD_NUMBER);

            cardSaldo.setCardNumber(cardNumber);
            cardSaldo.setCardDate("02/21");


            if (Build.VERSION.SDK_INT >= 24) {
                txtNameUser.setText(Html.fromHtml(getString(R.string.bienvenido_usuario,
                        preferencias.loadData(NAME_USER)),
                        Html.FROM_HTML_MODE_LEGACY, null, null));
            } else {
                txtNameUser.setText(Html.fromHtml(getString(R.string.bienvenido_usuario, preferencias.loadData(NAME_USER))));
            }


            setData(preferencias.loadData(StringConstants.USER_BALANCE), preferencias.loadData(UPDATE_DATE));
        }
    }

    private void setData(String balance, String updateDate) {
        txtSaldo.setText(Utils.getCurrencyValue(balance));
        txtDateUpdated.setText(String.format(getString(R.string.last_date_update), updateDate));
    }

    @Override
    public void updateBalance(String saldo) {
        hideLoader();
        setData(saldo, App.getInstance().getPrefs().loadData(UPDATE_DATE));
    }

    @Override
    public void updateBalanceAdq(String saldoAdq) {

    }

    @Override
    public void onRefresh() {
        swipeContainer.setRefreshing(false);
        accountPresenter.updateBalance();
    }

    @Override
    public void showLoader(String message) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void showError(Void error) {
        throw new IllegalCallException("this method is not implemented yet");
    }

    @Override
    public void nextScreen(String event, Void data) {

    }

    @Override
    public void backScreen(String event, Void data) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoToLogin:
                loginContainerManager.loadLoginFragment();
                break;
        }
    }
}