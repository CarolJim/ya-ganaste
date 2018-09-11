package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dspread.xpos.QPOSService;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.interfaces.IBalanceView;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.ILoginContainerManager;
import com.pagatodo.yaganaste.ui.account.login.LoginManagerContainerFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.CardWalletAdpater;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsWalletAdapter;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ICardBalance;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.Wallet;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.WalletBuilder;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.ui_wallet.views.BoardIndicationsView;
import com.pagatodo.yaganaste.ui_wallet.views.CustomDots;
import com.pagatodo.yaganaste.ui_wallet.views.ItemOffsetDecoration;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import eu.davidea.flipview.FlipView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_ADMIN_ADQ;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_BLOCK_CARD;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_CHECK_MONEY_CARD;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_VENTAS;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_PAYMENT;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_REWARDS;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_SECURE_CODE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_STORES;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ADMON_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_BALANCE_CLOSED_LOOP;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_BLOCK_CARD;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_GENERATE_TOKEN;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_PAYMENT_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_RECOMPENSAS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_SUCURSALES;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_VENTAS_ADQAFUERA;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_EMISOR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_SETTINGS;
import static com.pagatodo.yaganaste.utils.Recursos.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_STATUS;
import static com.pagatodo.yaganaste.utils.Recursos.CONNECTION_TYPE;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_BLOQUEADA;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_SHORTCUT_CHARGE;
import static com.pagatodo.yaganaste.utils.Recursos.HUELLA_FAIL;
import static com.pagatodo.yaganaste.utils.Recursos.ID_COMERCIOADQ;
import static com.pagatodo.yaganaste.utils.Recursos.IS_OPERADOR;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.NAME_USER;
import static com.pagatodo.yaganaste.utils.Recursos.NOM_COM;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.STARBUCKS_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.URL_PHOTO_USER;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

/**
 * Created by Omar on 13/02/2018.
 */

public class BalanceWalletFragment extends GenericFragment implements View.OnClickListener,
        ViewPager.OnPageChangeListener, IBalanceView, OnClickItemHolderListener,
        ICardBalance {

    private View rootView;
    private Preferencias prefs = App.getInstance().getPrefs();
    @BindView(R.id.img_profile_balance)
    CircleImageView crlProfileBalance;
    @BindView(R.id.txt_username_balance)
    StyleTextView txtUserNameBalance;
    @BindView(R.id.vp_balance)
    ViewPager vpBalace;
    @BindView(R.id.txt_balance_amount)
    MontoTextView txtAmountBalance;
    @BindView(R.id.txt_balance_type_payment)
    StyleTextView txtTypePaymentBalance;
    @BindView(R.id.txt_balance_card_desc)
    StyleTextView txtCardDescBalance;
    @BindView(R.id.txt_balance_card_desc2)
    StyleTextView txtCardDescBalance2;
    @BindView(R.id.img_refresh_balance)
    ImageView imgRefreshBalance;
    @BindView(R.id.rcv_balance_elements)
    RecyclerView rcvElementsBalance;
    @BindView(R.id.btn_balance_login)
    StyleButton btnLoginBalance;

    @BindView(R.id.board_indication)
    BoardIndicationsView board;
    @BindView(R.id.viewPagerCountDots)
    CustomDots pager_indicator;
    @BindView(R.id.chiandpin)
    ImageView chiandpin;

    private ILoginContainerManager loginContainerManager;
    private AccountPresenterNew accountPresenter;
    private CardWalletAdpater adapterBalanceCard;
    //private ElementsBalanceAdapter elementsBalanceAdpater;
    private ElementsWalletAdapter elementsWalletAdapter;
    private int pageCurrent;

    private String balanceEmisor, balanceAdq, balanceStarbucks, Status;

    public static BalanceWalletFragment newInstance() {
        BalanceWalletFragment balanceWalletFragment = new BalanceWalletFragment();
        Bundle args = new Bundle();
        balanceWalletFragment.setArguments(args);
        return balanceWalletFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginContainerManager = ((LoginManagerContainerFragment) getParentFragment()).getLoginContainerManager();
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        if (accountPresenter != null) {
            accountPresenter.setIView(this);
        }
        this.pageCurrent = 0;
        prefs.saveDataBool(HUELLA_FAIL, false);
        Status = App.getInstance().getPrefs().loadData(CARD_STATUS);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_balance_wallet, container, false);
            initViews();
        }
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnLoginBalance.setOnClickListener(this);
        imgRefreshBalance.setOnClickListener(this);
        txtUserNameBalance.setText("¡Hola " + prefs.loadData(NAME_USER) + "!");
        balanceEmisor = prefs.loadData(USER_BALANCE);
        balanceAdq = prefs.loadData(ADQUIRENTE_BALANCE);
        balanceStarbucks = prefs.loadData(STARBUCKS_BALANCE);
        elementsWalletAdapter = new ElementsWalletAdapter(getActivity(), this, true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(),
                R.dimen.item_offset);
        rcvElementsBalance.addItemDecoration(itemDecoration);
        rcvElementsBalance.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rcvElementsBalance.setHasFixedSize(true);
        if (prefs.loadData(CARD_NUMBER).isEmpty()) {
            Status = ESTATUS_CUENTA_BLOQUEADA;
        }
        setBalanceCards();
        board.setreloadOnclicklistener(view -> {
            if (adapterBalanceCard.getElemenWallet(this.pageCurrent) != null) {
                if (adapterBalanceCard.getElemenWallet(this.pageCurrent).getTypeWallet() == TYPE_EMISOR)
                    accountPresenter.updateBalance();
                else if (adapterBalanceCard.getElemenWallet(this.pageCurrent).getTypeWallet() != TYPE_SETTINGS) {
                    accountPresenter.updateBalanceAdq(adapterBalanceCard.getElemenWallet(this.pageCurrent));
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePhoto();
        //onRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_balance_login:
                loginContainerManager.loadLoginFragment();
                break;
        }
    }

    private void upDateSaldo(String saldo) {
        adapterBalanceCard.updateSaldo(this.pageCurrent, saldo);
        board.setTextMonto(saldo);
    }


    @Override
    public void updateBalance() {
        balanceEmisor = prefs.loadData(USER_BALANCE);
        hideLoader();
        upDateSaldo(StringUtils.getCurrencyValue(balanceEmisor));

    }

    @Override
    public void updateBalanceAdq() {
        balanceAdq = prefs.loadData(ADQUIRENTE_BALANCE);
        upDateSaldo(StringUtils.getCurrencyValue(balanceAdq));
        hideLoader();
        //checkStatusCard();
    }

    @Override
    public void updateBalanceStarbucks() {
        balanceStarbucks = prefs.loadData(STARBUCKS_BALANCE);
        hideLoader();
    }

    @Override
    public void updateStatus() {
        hideLoader();
        Status = App.getInstance().getPrefs().loadData(CARD_STATUS);
        setBalanceCards();
    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void showLoader(String message) {
        if (onEventListener != null) {
            onEventListener.onEvent(EVENT_SHOW_LOADER, message);
        }
    }

    @Override
    public void hideLoader() {
        if (onEventListener != null) {
            onEventListener.onEvent(EVENT_HIDE_LOADER, null);
        }
    }

    @Override
    public void showError(Object error) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        pager_indicator.selectDots(pageCurrent % adapterBalanceCard.getSize(), position % adapterBalanceCard.getSize());
        this.pageCurrent = position;
        adapterBalanceCard.resetFlip();
        setVisibilityBackItems(GONE);
        setVisibilityFrontItems(VISIBLE);

        if (prefs.loadDataBoolean(IS_OPERADOR, false)) {
            rcvElementsBalance.setLayoutManager(new GridLayoutManager(getContext(), 2));
            accountPresenter.updateBalance();
        } else {
            if (adapterBalanceCard.getElemenWallet(position) != null) {
                if (adapterBalanceCard.getElemenWallet(position).getTypeWallet() != TYPE_SETTINGS) {
                    if (adapterBalanceCard.getElemenWallet(position).getTypeWallet() == TYPE_EMISOR) {
                        rcvElementsBalance.setLayoutManager(new GridLayoutManager(getContext(), 1));
                        //    accountPresenter.updateBalance();
                        upDateSaldo(StringUtils.getCurrencyValue(balanceEmisor));
                    } else {
                        int idcomercio = adapterBalanceCard.getElemenWallet(position).getAgentes().getIdComercio();
                        boolean esUyU = adapterBalanceCard.getElemenWallet(position).getAgentes().isEsComercioUYU();
                        App.getInstance().getPrefs().saveData(NOM_COM, adapterBalanceCard.getElemenWallet(position).getAgentes().getNombreNegocio());
                        App.getInstance().getPrefs().saveData(ID_COMERCIOADQ, idcomercio + "");
                        if (esUyU) {
                            rcvElementsBalance.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        } else {
                            rcvElementsBalance.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        }
                        //   accountPresenter.updateBalanceAdq(adapterBalanceCard.getElemenWallet(position));
                        upDateSaldo(StringUtils.getCurrencyValue(balanceAdq));
                    }
                }
            }
        }
        updateOperations(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(Object item) {
        ElementView elementView = (ElementView) item;
        adapterBalanceCard.resetFlip();
        setVisibilityBackItems(GONE);
        setVisibilityFrontItems(VISIBLE);
        switch (elementView.getIdOperacion()) {

            case OPTION_BLOCK_CARD:
                nextScreen(EVENT_BLOCK_CARD, null);
                break;
            case OPTION_GENERATE_TOKEN:
                nextScreen(EVENT_SECURE_CODE, null);
                break;
            case OPTION_PAYMENT_ADQ:
                Bundle bundle = new Bundle();
                bundle.putString(CONNECTION_TYPE, Utils.getTypeConnection());
                FirebaseAnalytics.getInstance(App.getContext()).logEvent(EVENT_SHORTCUT_CHARGE, bundle);
                nextScreen(EVENT_PAYMENT, null);
                break;
            case OPTION_ADMON_ADQ:
                nextScreen(EVENT_ADMIN_ADQ, null);
                break;
            case OPTION_RECOMPENSAS:
                nextScreen(EVENT_REWARDS, null);
                break;
            case OPTION_SUCURSALES:
                nextScreen(EVENT_STORES, null);
                break;
            case OPTION_BALANCE_CLOSED_LOOP:
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if (!adapter.isEnabled()) {
                    Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enabler);
                } else {
                    nextScreen(EVENT_CHECK_MONEY_CARD, null);
                }
                break;
            case OPTION_VENTAS_ADQAFUERA:
                nextScreen(EVENT_GO_VENTAS, null);
                break;

            default:
                break;
        }
    }

    @Override
    public void onCardClick(View v, int position) {
        if (!((FlipView) v).isFlipped()) {
            ((FlipView) v).flip(true);
            if (prefs.loadDataBoolean(SHOW_BALANCE, true)) {
                setVisibilityBackItems(VISIBLE);
                setVisibilityFrontItems(GONE);
                if (adapterBalanceCard.getElemenWallet(this.pageCurrent).getTypeWallet() == TYPE_EMISOR)
                    accountPresenter.updateBalance();
                else if (adapterBalanceCard.getElemenWallet(this.pageCurrent).getTypeWallet() != TYPE_SETTINGS) {
                    accountPresenter.updateBalanceAdq(adapterBalanceCard.getElemenWallet(this.pageCurrent));
                }
            }
        } else {
            ((FlipView) v).flip(false);
            setVisibilityBackItems(GONE);
            setVisibilityFrontItems(VISIBLE);
        }
    }

    public void onRefresh(ElementWallet elementWallet) {
        if (!UtilsNet.isOnline(getActivity())) {
            UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_LONG);
        } else if (!prefs.loadData(CARD_NUMBER).isEmpty()) {
            //accountPresenter.updateBalance(elementWallet);
        }

    }

    /*private void checkStatusCard() {
        String f = SingletonUser.getInstance().getCardStatusId();
        if (f == null || f.isEmpty() || f.equals("0")) {
            String mTDC = prefs.loadData(CARD_NUMBER);
            accountPresenter.getEstatusCuenta(mTDC);
        } else {
            Status = f;
            App.getInstance().getPrefs().saveData(CARD_STATUS, f);
            setBalanceCards();
        }
    }*/

    private void setBalanceCards() {
        setVisibilityBackItems(GONE);
        setVisibilityFrontItems(VISIBLE);
        adapterBalanceCard = new CardWalletAdpater(false, this);
        if (prefs.containsData(IS_OPERADOR)) {
            rcvElementsBalance.setLayoutManager(new GridLayoutManager(getContext(), 2));
            chiandpin.setVisibility(VISIBLE);
            vpBalace.setVisibility(GONE);
            try {
                if (App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) != QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
                    chiandpin.setImageResource(R.mipmap.lector_front);
                    chiandpin.getLayoutParams().width = 400;
                }
            } catch (Exception e) {
                Log.d("Lector", "Sin opc de lector seleccionada");
            }

        }
        Wallet walletList = WalletBuilder.createWalletsBalance();
        adapterBalanceCard.addAllList(walletList.getList());
        adapterBalanceCard.notifyDataSetChanged();
        //adapterBalanceCard.setListener(this);

        vpBalace.setAdapter(adapterBalanceCard);
        vpBalace.setCurrentItem(this.pageCurrent);
        vpBalace.setOffscreenPageLimit(2);
        vpBalace.addOnPageChangeListener(this);
        pager_indicator.removeAllViews();
        updateOperations(this.pageCurrent);
        if (accountPresenter != null) {
            accountPresenter.updateBalance();
        }
        if (!prefs.containsData(IS_OPERADOR)) {
            setUiPageViewController();
        }
    }

    private void setUiPageViewController() {
        pager_indicator.setView(this.pageCurrent % adapterBalanceCard.getSize(), adapterBalanceCard.getSize());
    }


    private void updateOperations(final int position) {
        if (adapterBalanceCard.getElemenWallet(position) != null) {
            elementsWalletAdapter.setListOptions(adapterBalanceCard.getElemenWallet(position).getElementViews());
            elementsWalletAdapter.notifyDataSetChanged();

            rcvElementsBalance.setAdapter(elementsWalletAdapter);
            rcvElementsBalance.scheduleLayoutAnimation();

            board.setTextSaldo(adapterBalanceCard.getElemenWallet(position).getTipoSaldo());
            if (adapterBalanceCard.getElemenWallet(position).isUpdate()) {
                board.setReloadVisibility(View.VISIBLE);
            } else {
                board.setReloadVisibility(View.INVISIBLE);
            }
            txtCardDescBalance.setText(adapterBalanceCard.getElemenWallet(position).getTitleDesRes());
            txtCardDescBalance2.setText(adapterBalanceCard.getElemenWallet(position).getCardNumberRes());
        }
    }

    private void updatePhoto() {
        String mUserImage = prefs.loadData(URL_PHOTO_USER);

        Picasso.with(getContext())
                .load(StringUtils.procesarURLString(mUserImage))
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .error(R.mipmap.icon_user)
                .into(crlProfileBalance);
    }

    private void setVisibilityFrontItems(int visibility) {
        txtCardDescBalance.setVisibility(visibility);
        txtCardDescBalance2.setVisibility(visibility);
    }

    private void setVisibilityBackItems(int visibility) {
        board.setVisibility(visibility);
    }

}
