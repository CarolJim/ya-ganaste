package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.IBalanceView;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.ILoginContainerManager;
import com.pagatodo.yaganaste.ui.account.login.LoginManagerContainerFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.BalanceWalletAdpater;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsBalanceAdapter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ICardBalance;
import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.ui_wallet.views.ItemOffsetDecoration;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import eu.davidea.flipview.FlipView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_BLOCK_CARD;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_QUICK_PAYMENT;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_SECURE_CODE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_BLOQUEADA;
import static com.pagatodo.yaganaste.utils.Recursos.GENERO;
import static com.pagatodo.yaganaste.utils.Recursos.HUELLA_FAIL;
import static com.pagatodo.yaganaste.utils.Recursos.NAME_USER;
import static com.pagatodo.yaganaste.utils.Recursos.URL_PHOTO_USER;
import static com.pagatodo.yaganaste.utils.Recursos.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.COMPANY_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

/**
 * Created by Omar on 13/02/2018.
 */

public class BalanceWalletFragment extends GenericFragment implements View.OnClickListener,
        ViewPager.OnPageChangeListener, IBalanceView, OnItemClickListener,
        ICardBalance {

    private View rootView;
    private Preferencias prefs = App.getInstance().getPrefs();
    @BindView(R.id.img_profile_balance)
    CircleImageView crlProfileBalance;
    @BindView(R.id.txt_username_balance)
    StyleTextView txtUserNameBalance;
    @BindView(R.id.lyt_dots_indicator_balance)
    LinearLayout lytDotsIndicatorBalance;
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

    private ILoginContainerManager loginContainerManager;
    private AccountPresenterNew accountPresenter;
    private BalanceWalletAdpater balanceWalletAdpater;
    private ElementsBalanceAdapter elementsBalanceAdpater;
    private int dotsCount, previous_pos = 0, pageCurrent;
    private ImageView[] dots;
    private String balanceEmisor, balanceAdq, Status;

    public static final BalanceWalletFragment newInstance() {
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
        pageCurrent = 0;
        prefs.saveDataBool(HUELLA_FAIL, false);
        Status = App.getInstance().getStatusId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        rcvElementsBalance.addItemDecoration(itemDecoration);
        rcvElementsBalance.setLayoutManager(llm);
        rcvElementsBalance.setHasFixedSize(true);
        setBalanceCards();
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePhoto();
        onRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_balance_login:
                loginContainerManager.loadLoginFragment();
                break;
            case R.id.img_refresh_balance:
                onRefresh();
                break;
        }
    }

    @Override
    public void updateBalance() {
        balanceEmisor = prefs.loadData(USER_BALANCE);
        hideLoader();
        if (!RequestHeaders.getTokenAdq().isEmpty()) {
            accountPresenter.updateBalanceAdq();
        } else {
            checkStatusCard();
        }
    }

    @Override
    public void updateBalanceAdq() {
        balanceAdq = prefs.loadData(ADQUIRENTE_BALANCE);
        hideLoader();
        checkStatusCard();
    }

    @Override
    public void updateBalanceCupo() {
    }

    @Override
    public void updateStatus() {
        hideLoader();
        Status = App.getInstance().getStatusId();
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
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_selected_dot_wallet));
        }
        dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selected_dot_wallet));
        int pos = position + 1;
        if (pos == dotsCount && previous_pos == (dotsCount - 1)) {
        } else if (pos == (dotsCount - 1) && previous_pos == dotsCount) {
        }
        previous_pos = pos;
        balanceWalletAdpater.restartFlippers();
        setVisibilityBackItems(GONE);
        setVisibilityFrontItems(VISIBLE);
        updateOperations(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(ElementView elementView) {
        balanceWalletAdpater.restartFlippers();
        setVisibilityBackItems(GONE);
        setVisibilityFrontItems(VISIBLE);
        switch (elementView.getIdOperacion()) {
            case 1:
                nextScreen(EVENT_BLOCK_CARD, null);
                break;
            case 2:
                nextScreen(EVENT_SECURE_CODE, null);
                break;
            case 3:
                nextScreen(EVENT_QUICK_PAYMENT, null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCardClick(View v, int position) {
        if (v instanceof CardView) {
            if (position != pageCurrent) {
                vpBalace.setCurrentItem(position);
            }
        } else if (v instanceof FlipView) {
            if (position == pageCurrent) {
                if (!((FlipView) v).isFlipped()) {
                    ((FlipView) v).flip(true);
                    setVisibilityBackItems(VISIBLE);
                    setVisibilityFrontItems(GONE);
                } else {
                    ((FlipView) v).flip(false);
                    setVisibilityBackItems(GONE);
                    setVisibilityFrontItems(VISIBLE);
                }
            } else {
                vpBalace.setCurrentItem(position);
            }
        }
    }

    public void onRefresh() {
        if (!UtilsNet.isOnline(getActivity())) {
            UI.showErrorSnackBar(getActivity(), getString(R.string.no_internet_access), Snackbar.LENGTH_LONG);
        } else {
            accountPresenter.updateBalance();
        }
    }

    private void checkStatusCard() {
        String f = SingletonUser.getInstance().getCardStatusId();
        if (f == null || f.isEmpty() || f.equals("0")) {
            String mTDC = prefs.loadData(CARD_NUMBER);
            accountPresenter.getEstatusCuenta(mTDC);
        } else {
            Status = f;
            App.getInstance().setStatusId(f);
            setBalanceCards();
        }
    }

    private void setBalanceCards() {
        setVisibilityBackItems(GONE);
        setVisibilityFrontItems(VISIBLE);
        balanceWalletAdpater = new BalanceWalletAdpater(this);
        if (Status.equals(ESTATUS_CUENTA_BLOQUEADA)) {
            balanceWalletAdpater.addCardItem(new ElementWallet().getCardBalanceEmiBloqueda());
        } else {
            balanceWalletAdpater.addCardItem(new ElementWallet().getCardBalanceEmi());
        }
        if (!RequestHeaders.getTokenAdq().isEmpty()) {
            balanceWalletAdpater.addCardItem(new ElementWallet().getCardBalanceAdq());
        }
        vpBalace.setAdapter(balanceWalletAdpater);
        vpBalace.setCurrentItem(pageCurrent);
        vpBalace.setOffscreenPageLimit(3);
        vpBalace.addOnPageChangeListener(this);
        setUiPageViewController();
        updateOperations(pageCurrent);
    }

    private void setUiPageViewController() {
        lytDotsIndicatorBalance.removeAllViews();
        dotsCount = balanceWalletAdpater.getCount();
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_selected_dot_wallet));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(22, 0, 22, 0);
            lytDotsIndicatorBalance.addView(dots[i], params);
        }
        dots[pageCurrent].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selected_dot_wallet));
    }

    private void updateOperations(int position) {
        pageCurrent = position;
        if (position == 0) {
            txtAmountBalance.setText(Utils.getCurrencyValue(balanceEmisor));
            txtCardDescBalance.setText(getString(R.string.tarjeta_yg));
            txtCardDescBalance2.setText(StringUtils.ocultarCardNumberFormat(prefs.loadData(CARD_NUMBER)));
            elementsBalanceAdpater = new ElementsBalanceAdapter(getContext(), this, ElementView.getListEmisorBalance());
        }
        if (position == 1) {
            txtAmountBalance.setText(Utils.getCurrencyValue(balanceAdq));
            txtCardDescBalance.setText(prefs.loadData(COMPANY_NAME));
            txtCardDescBalance2.setText(getString(R.string.cobros_con_tarjeta));
            elementsBalanceAdpater = new ElementsBalanceAdapter(getContext(), this, ElementView.getListAdqBalance());
        }
        rcvElementsBalance.setAdapter(elementsBalanceAdpater);
        rcvElementsBalance.scheduleLayoutAnimation();
        txtTypePaymentBalance.setText(balanceWalletAdpater.getElemenWallet(position).getTipoSaldo());
    }

    private void updatePhoto() {

        String mUserImage = prefs.loadData(URL_PHOTO_USER);
       /* Picasso.with(getContext()).load(StringUtils.procesarURLString(mUserImage))
                .placeholder(R.mipmap.icon_user).error(R.mipmap.icon_user)
                .into(crlProfileBalance);
        */

        if (prefs.loadData(GENERO)=="H"||prefs.loadData(GENERO)=="h") {
            Picasso.with(getContext()).load(StringUtils.procesarURLString(mUserImage))
                    .placeholder(R.mipmap.icon_user).error(R.drawable.avatar_el)
                    .into(crlProfileBalance);
        }else if (prefs.loadData(GENERO)=="M"||prefs.loadData(GENERO)=="m"){
            Picasso.with(getContext()).load(StringUtils.procesarURLString(mUserImage))
                    .placeholder(R.mipmap.icon_user).error(R.drawable.avatar_ella)
                    .into(crlProfileBalance);
        }else {
            Picasso.with(getContext()).load(StringUtils.procesarURLString(mUserImage))
                    .placeholder(R.mipmap.icon_user).error(R.mipmap.icon_user)
                    .into(crlProfileBalance);
        }
    }

    private void setVisibilityFrontItems(int visibility) {
        txtCardDescBalance.setVisibility(visibility);
        txtCardDescBalance2.setVisibility(visibility);
    }

    private void setVisibilityBackItems(int visibility) {
        txtTypePaymentBalance.setVisibility(visibility);
        txtAmountBalance.setVisibility(visibility);
        imgRefreshBalance.setVisibility(visibility);
    }
}
