package com.pagatodo.yaganaste.ui.account.login;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IBalanceView;
import com.pagatodo.yaganaste.interfaces.IPurseView;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.CardBack;
import com.pagatodo.yaganaste.ui.account.CardCover;
import com.pagatodo.yaganaste.ui.account.ILoginContainerManager;
import com.pagatodo.yaganaste.ui.account.IQuickBalanceManager;
import com.pagatodo.yaganaste.utils.Recursos;
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
import static com.pagatodo.yaganaste.utils.Recursos.FLIP_TIMER;
import static com.pagatodo.yaganaste.utils.StringConstants.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_SESSION;
import static com.pagatodo.yaganaste.utils.StringConstants.LAST_NAME;
import static com.pagatodo.yaganaste.utils.StringConstants.NAME_USER;
import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;
import static com.pagatodo.yaganaste.utils.StringConstants.UPDATE_DATE;
import static com.pagatodo.yaganaste.utils.StringConstants.USER_BALANCE;
import static com.pagatodo.yaganaste.utils.Utils.setDurationScale;

/**
 * @author Juan Guerra on 15/06/2017.
 */

public class QuickBalanceFragment extends GenericFragment implements IBalanceView,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, IPurseView {
    private View transparentBG;
    private AlphaAnimation fading;
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
    @BindView(R.id.imgArrowBack)
    AppCompatImageView imgArrowBack;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    private View mRootView;
    private AccountPresenterNew accountPresenter;
    private ILoginContainerManager loginContainerManager;
    private IQuickBalanceManager quickBalanceManager;
    private Preferencias preferencias;
    View rootView;
    boolean clickFlip = false;
    private Handler flipTimmer;
    private Runnable runnableTimmer;
    boolean pauseback=true;
    private String Status;

    public static QuickBalanceFragment newInstance() {

        Bundle args = new Bundle();

        QuickBalanceFragment fragment = new QuickBalanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_quick_balance, container, false);
        preferencias = App.getInstance().getPrefs();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        loginContainerManager = ((QuickBalanceContainerFragment) getParentFragment()).getLoginContainerManager();
        quickBalanceManager = ((QuickBalanceContainerFragment) getParentFragment()).getQuickBalanceManager();
        accountPresenter.setPurseReference(this);
        // quickBalanceManager Obtenemos la referencia a la interfase desde  QuickBalanceContainerFragment
        Status = App.getInstance().getStatusId();
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

        //final View couchMark = view.findViewById(R.id.purse_fragment_ll_couch_mark);
        final View couchMark = view.findViewById(R.id.llsaldocontenedor);
        couchMark.setVisibility(View.VISIBLE);
        couchMark.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoToLogin:
                if (flipTimmer != null) {
                    flipTimmer.removeCallbacks(runnableTimmer);
                    flipTimmer = null;
                    pauseback=false;
                }
                loginContainerManager.loadLoginFragment();
                break;
            case R.id.imgArrowBack:
                backScreen(null, null);
                break;

            case R.id.llsaldocontenedor:
                doFlip();
                break;
        }
    }

    public void onClick(int id) {
        if (id == R.id.llsaldocontenedor) {
            try {
                doFlip();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            if (accountPresenter.isBackShown() && pauseback==true){
                accountPresenter.flipCard(R.id.llsaldo, CardBack.newInstance(accountPresenter,Status));
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    private void doFlip() {
        accountPresenter.flipCard(R.id.llsaldo, CardBack.newInstance(accountPresenter,Status));

        if (flipTimmer != null) {
            flipTimmer.removeCallbacks(runnableTimmer);
            flipTimmer = null;
        }
        if (accountPresenter.isBackShown()) {
            flipTimmer = new Handler();
            runnableTimmer = new Runnable() {
                @Override
                public void run() {
                    onClick(R.id.llsaldocontenedor);
                }
            };
            flipTimmer.postDelayed(runnableTimmer, FLIP_TIMER);
        }
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, mRootView);

        swipeContainer.setOnRefreshListener(this);
        goToLogin.setOnClickListener(this);
        // Se oculta el Shebron
        imgArrowBack.setVisibility(View.GONE);
        imgArrowBack.setOnClickListener(this);
        txtSaldo.setText(Utils.getCurrencyValue(preferencias.loadData(USER_BALANCE)));

        if (App.getInstance().getPrefs().containsData(HAS_SESSION)) {
            String cardNumber = preferencias.loadData(CARD_NUMBER);
//                    Utils.getCurrencyValue(cardNumber))
            cardSaldo.setCardNumber(StringUtils.ocultarCardNumberFormat(cardNumber));
            cardSaldo.setCardDate("02/21");


            String name = StringUtils.getFirstName(preferencias.loadData(NAME_USER)) + SPACE
                    + StringUtils.getFirstName(preferencias.loadData(LAST_NAME));

            if (Build.VERSION.SDK_INT >= 24) {
                txtNameUser.setText(Html.fromHtml(getString(R.string.bienvenido_usuario,
                        name),
                        Html.FROM_HTML_MODE_LEGACY, null, null));
            } else {
                txtNameUser.setText(Html.fromHtml(getString(R.string.bienvenido_usuario, name)));
            }

            //onRefresh();
            accountPresenter.updateBalance();
            //setData(preferencias.loadData(StringConstants.USER_BALANCE), preferencias.loadData(UPDATE_DATE));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setData(String balance, String updateDate) {
        //txtSaldo.setText(Utils.getCurrencyValue(balance));
        txtDateUpdated.setText(String.format(getString(R.string.last_date_update), updateDate));
    }

    @Override
    public void updateBalance() {
        hideLoader();
        setData(preferencias.loadData(USER_BALANCE), preferencias.loadData(UPDATE_DATE));
        //Consulta status de la tarjeta
        String f = SingletonUser.getInstance().getCardStatusId();
        if (f == null || f.isEmpty() || f.equals("0")) {
            //UsuarioClienteResponse usuarioClienteResponse = SingletonUser.getInstance().getDataUser().getUsuario();
            //String mTDC = usuarioClienteResponse.getCuentas().get(0).getTarjeta();
            String mTDC = preferencias.loadData(CARD_NUMBER);
            accountPresenter.geEstatusCuenta(mTDC);
        }
    }

    @Override
    public void updateStatus() {
        hideLoader();
        Status = App.getInstance().getStatusId();
        accountPresenter.loadCardCover(R.id.llsaldo, CardCover.newInstance(accountPresenter,Status));
    }

    @Override
    public void updateBalanceAdq() {

    }

    @Override
    public void updateBalanceCupo() {

    }

    @Override
    public void onRefresh() {
        if (!UtilsNet.isOnline(getActivity())) {
            swipeContainer.setRefreshing(false);
            UI.createSimpleCustomDialog("", getString(R.string.no_internet_access), getActivity().getSupportFragmentManager(), new DialogDoubleActions() {
                @Override
                public void actionConfirm(Object... params) {
                    // Toast.makeText(getContext(), "Click CERRAR SESSION", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void actionCancel(Object... params) {

                }
            }, true, false);

        } else {
            swipeContainer.setRefreshing(false);
            accountPresenter.updateBalance();
        }
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
    public void showError(Object error) {
        //throw new IllegalCallException("this method is not implemented yet");
        Log.d("Aqui","Hay Error");
    }

    @Override
    public void nextScreen(String event, Object data) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Status = "2";
        try {
            //accountPresenter.loadCardCover(R.id.llsaldo, CardCover.newInstance(accountPresenter,Status));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String cardNumber = preferencias.loadData(CARD_NUMBER);
//                    Utils.getCurrencyValue(cardNumber))
       //cardSaldo.setCardNumber(StringUtils.ocultarCardNumberFormat(cardNumber));
    }

    @Override
    public void backScreen(String event, Object data) {
        quickBalanceManager.backPage();
    }

    @Override
    public void flipCard(int container, Fragment fragment, boolean isBackShown) {
        setDurationScale(1);
        if (isBackShown) {
            try {
                getActivity().getFragmentManager().popBackStack();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        // TrackingUtils.doAnalyticsTracking(MONEDERO_PAGAR_LBL);
    }

    @Override
    public void flipCarddongle(int container, Fragment fragment, boolean isBackShown) {

    }

    @Override
    public void loadCardCover(int container, Fragment fragment) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeBGVisibility(final boolean isVisible) {
        float start = isVisible ? 1.0f : 0.0f;
        float end = isVisible ? 0.0f : 1.0f;

        fading = new AlphaAnimation(start, end);
        fading.setDuration(getResources().getInteger(R.integer.card_flip_time_full));
        fading.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (!isVisible)
                    transparentBG.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isVisible)
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
        if (fading == null)
            return true;

        if (fading.hasStarted()) {
            if (fading.hasEnded())
                return true;
            else
                return false;
        } else
            return true;
    }

}