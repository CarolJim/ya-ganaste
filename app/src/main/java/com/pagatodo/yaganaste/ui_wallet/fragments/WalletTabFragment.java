package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardViewHome;
import com.pagatodo.yaganaste.ui_wallet.WalletMainActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.CardWalletAdpater;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsWalletAdapter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IWalletView;
import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletPresenter;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.presenter.WalletPresenterImpl;
import com.pagatodo.yaganaste.ui_wallet.views.CustomDots;
import com.pagatodo.yaganaste.ui_wallet.views.CutomWalletViewPage;
import com.pagatodo.yaganaste.ui_wallet.views.ItemOffsetDecoration;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.TabActivity.EVENT_LOGOUT;
import static com.pagatodo.yaganaste.ui_wallet.adapters.CardWalletAdpater.LOOPS_COUNT;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_ERROR_INFO_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OFFLINE;

public class WalletTabFragment extends SupportFragment implements IWalletView,
        OnItemClickListener, IMyCardViewHome, ViewPager.OnPageChangeListener {

    public static final String ITEM_OPERATION = "ITEM_OPERATION";
    public static final int ERROR_STATUS = 12;

    @BindView(R.id.progressGIF)
    ProgressLayout progressLayout;
    @BindView(R.id.pager_container)
    CutomWalletViewPage pageContainer;
    @BindView(R.id.rcv_elements)
    RecyclerView rcvOpciones;
    @BindView(R.id.txt_monto)
    StyleTextView txtSaldo;
    @BindView(R.id.viewPagerCountDots)
    CustomDots pager_indicator;
    @BindView(R.id.tipo_saldo)
    StyleTextView tipoSaldo;
    @BindView(R.id.img_reload)
    ImageView imgReload;


    private WalletPresenter walletPresenter;
    private CardWalletAdpater cardWalletAdpater;
    private ElementsWalletAdapter elementsWalletAdapter;
    protected OnEventListener onEventListener;
    private ViewPager viewPagerWallet;
    private int pageCurrent;
    private GridLayoutManager llm;

    public static WalletTabFragment newInstance() {
        return new WalletTabFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventListener) {
            this.onEventListener = (OnEventListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageCurrent = LOOPS_COUNT / 2 + 1;
        walletPresenter = new WalletPresenterImpl(this);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallet_main, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void initViews() {
        viewPagerWallet = pageContainer.getViewPager();
        elementsWalletAdapter = new ElementsWalletAdapter(getActivity(),this);
        llm = new GridLayoutManager(getContext(), 3);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(),
                R.dimen.item_offset);
        rcvOpciones.addItemDecoration(itemDecoration);
        rcvOpciones.setLayoutManager(llm);
        rcvOpciones.setHasFixedSize(true);
        imgReload.setOnClickListener(view -> {
            if (elementsWalletAdapter.getItemCount() > 0) {
                walletPresenter.updateBalance(cardWalletAdpater.getElemenWallet(pageCurrent));
            }
        });
    }

    @Override
    public void showProgress() {
        progressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void setError(String message) {
        UI.showErrorSnackBar(getActivity(), message, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void getPagerAdapter(PagerAdapter pagerAdapter) {
        cardWalletAdpater = (CardWalletAdpater) pagerAdapter;
        pageCurrent = (LOOPS_COUNT / 2) - ((LOOPS_COUNT / 2) % cardWalletAdpater.getSize()) ;
        viewPagerWallet.setAdapter(pagerAdapter);
        viewPagerWallet.setCurrentItem(pageCurrent);
        viewPagerWallet.setOffscreenPageLimit(3);
        viewPagerWallet.setPageMargin(15);
        viewPagerWallet.addOnPageChangeListener(this);
        setUiPageViewController();
        updateOperations(pageCurrent);
        walletPresenter.updateBalance(cardWalletAdpater.getElemenWallet(pageCurrent));
    }

    private void setUiPageViewController() {
        pager_indicator.setView(pageCurrent,cardWalletAdpater.getSize());
    }

    private void updateOperations(final int position) {
        int colums = 3;
        pageCurrent = position;
        if (cardWalletAdpater.getElemenWallet(position).getElementViews().size() <= 1) {
            colums = 1;
        }

        elementsWalletAdapter.setListOptions(cardWalletAdpater.getElemenWallet(position).getElementViews());
        elementsWalletAdapter.notifyDataSetChanged();
        llm.setSpanCount(colums);
        rcvOpciones.setLayoutManager(llm);
        upDateSaldo(position);
        rcvOpciones.setAdapter(elementsWalletAdapter);
        rcvOpciones.scheduleLayoutAnimation();
        tipoSaldo.setText(cardWalletAdpater.getElemenWallet(position).getTipoSaldo());

        if (cardWalletAdpater.getElemenWallet(position).isUpdate()){
            imgReload.setVisibility(View.VISIBLE);
        } else {
            imgReload.setVisibility(View.INVISIBLE);

        }
    }

    private void upDateSaldo(int position){
        txtSaldo.setText(cardWalletAdpater.getElemenWallet(position).getSaldo());
    }

    private void upDateSaldo(String saldo){
        cardWalletAdpater.updateSaldo(pageCurrent, saldo);
        txtSaldo.setText(saldo);
    }

    @Override
    public void onItemClick(ElementView itemOperation) {
        Intent intent = new Intent(getContext(), WalletMainActivity.class);
        intent.putExtra(ITEM_OPERATION, itemOperation);
        startActivity(intent);
    }

    @Override
    public void getSaldo(String saldo) {
        upDateSaldo(StringUtils.getCurrencyValue(saldo));
    }

    @Override
    public void sendCardReported() {
        pager_indicator.removeAllViews();
        walletPresenter.getWalletsCards(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        walletPresenter.getInformacionAgente();
    }

    @Override
    public void showLoader(String s) {
        showProgress();
    }

    @Override
    public void hideLoader() {
        hideProgress();
    }

    @Override
    public void sendSuccessStatusAccount(EstatusCuentaResponse response) {
        String statusId = response.getData().getStatusId();
        SingletonUser.getInstance().setCardStatusId(statusId);
        pager_indicator.removeAllViews();
        walletPresenter.getWalletsCards(false);
    }

    @Override
    public void sendSuccessInfoAgente() {
        checkDataCard();
    }

    @Override
    public void sendSuccessBloquearCuentaToView(BloquearCuentaResponse response) {

    }

    @Override
    public void sendSuccessEstatusCuentaToView(EstatusCuentaResponse response) {
        String statusId = response.getData().getStatusId();
        SingletonUser.getInstance().setCardStatusId(statusId);
        pager_indicator.removeAllViews();
        walletPresenter.getWalletsCards(false);
    }

    @Override
    public void sendErrorBloquearCuentaToView(String mensaje) {
        walletPresenter.getWalletsCards(true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //previous_pos = pageCurrent;
        pager_indicator.selectDots(pageCurrent % cardWalletAdpater.getSize(), position % cardWalletAdpater.getSize());
        pageCurrent = position;
        updateOperations(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void checkDataCard() {
        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            String f = SingletonUser.getInstance().getCardStatusId();
            if (f == null || f.isEmpty() || f.equals("0")) {
                UsuarioClienteResponse usuarioClienteResponse = SingletonUser.getInstance().getDataUser().getUsuario();
                if (usuarioClienteResponse.getCuentas().size() != 0) {
                    walletPresenter.getStatusAccount(usuarioClienteResponse.getCuentas().get(0).getTarjeta());
                }
            } else {
                pager_indicator.removeAllViews();
                walletPresenter.getWalletsCards(false);
            }
        } else {
            showDialogMesage(getResources().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void setErrorSaldo(String errorSaldo) {
        UI.showErrorSnackBar(getActivity(),errorSaldo,Snackbar.LENGTH_SHORT);
    }

    @Override
    public void sendError(int codeError) {
        switch (codeError){
            case CODE_OFFLINE:
                showDialogMesage(getResources().getString(R.string.no_internet_access));
                break;
            case ERROR_STATUS:
                walletPresenter.getWalletsCards(false);
                break;
            case CODE_ERROR_INFO_AGENTE:
                onEventListener.onEvent(EVENT_LOGOUT,null);
                break;
        }
    }
}

