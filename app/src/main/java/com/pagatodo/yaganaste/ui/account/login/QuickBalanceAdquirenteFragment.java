package com.pagatodo.yaganaste.ui.account.login;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IBalanceView;
import com.pagatodo.yaganaste.interfaces.IPurseView;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.CardBackAdquiriente;
import com.pagatodo.yaganaste.ui.account.CardBackAdquirienteDongle;
import com.pagatodo.yaganaste.ui.account.CardCover;
import com.pagatodo.yaganaste.ui.account.CardCoverAdquiriente;
import com.pagatodo.yaganaste.ui.account.CardCoverAdquirienteDongle;
import com.pagatodo.yaganaste.ui.account.ILoginContainerManager;
import com.pagatodo.yaganaste.ui.account.IQuickBalanceManager;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
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
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener , IPurseView {
    private View transparentBG;
    private AlphaAnimation fading;
    View rootView;
    View rootViewfragment;

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

    private AccountPresenterNew accountPresenter;
    private AccountPresenterNew accountPresenterdongle;
    private ILoginContainerManager loginContainerManager;
    private IQuickBalanceManager quickBalanceManager;
    private Preferencias prefs = App.getInstance().getPrefs();
    private String Status;

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
        accountPresenterdongle = ((AccountActivity) getActivity()).getPresenter();


        loginContainerManager = ((QuickBalanceContainerFragment) getParentFragment()).getLoginContainerManager();
        quickBalanceManager = ((QuickBalanceContainerFragment) getParentFragment()).getQuickBalanceManager();
        accountPresenter.setPurseReference(this);
        Status = App.getInstance().getStatusId();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

                rootViewfragment = inflater.inflate(R.layout.fragment_quick_balance_adquirente, container, false);

        return rootViewfragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        if (accountPresenter != null) {
            accountPresenter.setIView(this);
        }
        initViews();
        final View couchMark = view.findViewById(R.id.llsaldocontenedor);
        couchMark.setVisibility(View.VISIBLE);
        couchMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountPresenter.flipCard(R.id.llsaldo, CardBackAdquiriente.newInstance(accountPresenter,Status));
            }
        });

        final View couchMarkdongle = view.findViewById(R.id.llsaldocontenedordongle);
        couchMarkdongle.setVisibility(View.VISIBLE);
        couchMarkdongle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountPresenter.flipCarddongle(R.id.llsaldodongle, CardBackAdquirienteDongle.newInstance(accountPresenter));
            }
        });
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        swipeContainer.setOnRefreshListener(this);
        btnGoToLogin.setOnClickListener(this);
        imgArrowBack.setOnClickListener(this);
        imgArrowNext.setOnClickListener(this);

        // Se ocultan los Shebrons por nueva version de Inicio de Session
        imgArrowBack.setVisibility(View.GONE);
        imgArrowNext.setVisibility(View.GONE);
        txtSaldoPersonal.setText(Utils.getCurrencyValue(prefs.loadData(USER_BALANCE)));
        txtBalanceReembolsar.setText(Utils.getCurrencyValue(prefs.loadData(ADQUIRENTE_BALANCE)));

        if (prefs.containsData(HAS_SESSION)) {
            String cardNumber = prefs.loadData(CARD_NUMBER);

            cardBalanceAdq.setCardNumber(StringUtils.ocultarCardNumberFormat(cardNumber));
          //  cardBalanceAdq.setCardDate("03/21");

            if (Build.VERSION.SDK_INT >= 24) {
                txtUserName.setText(Html.fromHtml(getString(R.string.bienvenido_usuario,
                        prefs.loadData(NAME_USER)),
                        Html.FROM_HTML_MODE_LEGACY, null, null));
            } else {
                txtUserName.setText(Html.fromHtml(getString(R.string.bienvenido_usuario, prefs.loadData(NAME_USER))));
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        Status = "2";
        accountPresenter.loadCardCover(R.id.llsaldo, CardCoverAdquiriente.newInstance(accountPresenter,Status));
        accountPresenter.loadCardCover(R.id.llsaldodongle, CardCoverAdquirienteDongle.newInstance(accountPresenter));
    }

    @Override
    public void onRefresh() {

        if (!UtilsNet.isOnline(getActivity())){
            UI.createSimpleCustomDialog("", getString(R.string.no_internet_access), getActivity().getSupportFragmentManager(), new DialogDoubleActions() {
                @Override
                public void actionConfirm(Object... params) {
                    // Toast.makeText(getContext(), "Click CERRAR SESSION", Toast.LENGTH_SHORT).show();
                    swipeContainer.setRefreshing(false);
                }

                @Override
                public void actionCancel(Object... params) {

                }
            }, true, false);
        }else {
            swipeContainer.setRefreshing(false);
            accountPresenter.updateBalance();
        }


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
        setData(prefs.loadData(USER_BALANCE), prefs.loadData(UPDATE_DATE));
        accountPresenter.updateBalanceAdq();
    }

    @Override
    public void updateBalanceAdq() {
        setDataAdq(prefs.loadData(ADQUIRENTE_BALANCE), prefs.loadData(UPDATE_DATE_BALANCE_ADQ));
        hideLoader();
        String f = SingletonUser.getInstance().getCardStatusId();
        if (f == null || f.isEmpty() || f.equals("0")) {
            String mTDC = prefs.loadData(CARD_NUMBER);
            accountPresenter.geEstatusCuenta(mTDC);
        }
    }

    @Override
    public void updateBalanceCupo() {

    }

    @Override
    public void updateStatus() {
        hideLoader();
        Status = App.getInstance().getStatusId();
        accountPresenter.loadCardCover(R.id.llsaldo, CardCoverAdquiriente.newInstance(accountPresenter,Status));
    }

    @Override
    public void showLoader(String message) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        if (onEventListener != null) {
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

    @Override
    public void flipCard(int container, Fragment fragment, boolean isBackShown) {

        if(isBackShown)
        {
            accountPresenter.loadCardCover(R.id.llsaldo, CardCoverAdquiriente.newInstance(accountPresenter,Status));
         return;
        }
            getActivity().getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in,
                            R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in,
                            R.animator.card_flip_left_out)
                    .replace(container, fragment)
                    .addToBackStack(null)
                    .commit();
        AccountPresenterNew.isBackShown=true;
            // TrackingUtils.doAnalyticsTracking(MONEDERO_PAGAR_LBL);
    }

    @Override
    public void flipCarddongle(int container, Fragment fragment, boolean isBackShowndongle) {
        if(isBackShowndongle)
        {
            accountPresenter.loadCardCoverdongle(R.id.llsaldodongle, CardCoverAdquirienteDongle.newInstance(accountPresenter));
       return;
        }
            getActivity().getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in,
                            R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in,
                            R.animator.card_flip_left_out)
                    .replace(container, fragment)
                    .addToBackStack(null)
                    .commit();
        AccountPresenterNew.isBackShowndongle=true;
        // TrackingUtils.doAnalyticsTracking(MONEDERO_PAGAR_LBL);
    }

    @Override
    public void loadCardCover(int container, Fragment fragment) {
        getActivity().getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                .replace(container, fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void changeBGVisibility(final boolean  isVisible) {
        float start = isVisible ? 1.0f : 0.0f;
        float end = isVisible ? 0.0f : 1.0f;

        fading = new AlphaAnimation(start, end);
        fading.setDuration(getResources().getInteger(R.integer.card_flip_time_full));
        fading.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if(!isVisible)
                    transparentBG.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(isVisible)
                    transparentBG.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//       transparentBG.startAnimation(fading);
    }

    @Override
    public boolean isAnimationAble() {
        if(fading == null)
            return true;

        if(fading.hasStarted())
        {
            if(fading.hasEnded())
                return true;
            else
                return false;
        }
        else
            return true;
    }
}
