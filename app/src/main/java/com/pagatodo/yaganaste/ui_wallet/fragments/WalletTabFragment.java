package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardViewHome;
import com.pagatodo.yaganaste.ui_wallet.WalletMainActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.CardWalletAdpater;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsWalletAdapter;
import com.pagatodo.yaganaste.ui_wallet.builder.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IWalletView;
import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletPresenter;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;

import com.pagatodo.yaganaste.ui_wallet.presenter.WalletPresenterImpl;
import com.pagatodo.yaganaste.ui_wallet.views.ItemOffsetDecoration;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_DOCUMENTACION;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ESTATUS;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

public class WalletTabFragment extends SupportFragment implements IWalletView,
        OnItemClickListener, IMyCardViewHome, ViewPager.OnPageChangeListener {

    public static final String ID_OPERATION = "ID_OPERATION";
    @BindView(R.id.progressGIF)
    ProgressLayout progressLayout;
    @BindView(R.id.viewpager_wallet)
    ViewPager viewPagerWallet;
    @BindView(R.id.rcv_elements)
    RecyclerView rcvOpciones;
    @BindView(R.id.txt_monto)
    StyleTextView txtSaldo;
    @BindView(R.id.viewPagerCountDots)
    LinearLayout pager_indicator;
    @BindView(R.id.tipo_saldo)
    StyleTextView tipoSaldo;
    @BindView(R.id.img_reload)
    ImageView imgReload;


    private WalletPresenter walletPresenter;
    private CardWalletAdpater cardWalletAdpater;
    private ElementsWalletAdapter elementsWalletAdapter;
    protected OnEventListener onEventListener;

    private int dotsCount;
    private ImageView[] dots;
    private int previous_pos = 0;
    private int pageCurrent;
    private String mTDC;
    private GridLayoutManager llm;
    private LinearLayoutManager linearLayoutManager;

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
        pageCurrent = 0;
        walletPresenter = new WalletPresenterImpl(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallet_main, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void initViews() {
        elementsWalletAdapter = new ElementsWalletAdapter(getActivity(),this);
        llm = new GridLayoutManager(getContext(), 3);
        linearLayoutManager = new LinearLayoutManager(getContext());
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        rcvOpciones.addItemDecoration(itemDecoration);
        rcvOpciones.setLayoutManager(llm);
        rcvOpciones.setHasFixedSize(true);

        imgReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                walletPresenter.updateBalance();
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
        progressLayout.setVisibility(View.GONE);
        cardWalletAdpater = (CardWalletAdpater) pagerAdapter;
        viewPagerWallet.setAdapter(pagerAdapter);
        viewPagerWallet.setCurrentItem(pageCurrent);
        viewPagerWallet.setOffscreenPageLimit(3);
        viewPagerWallet.addOnPageChangeListener(this);
        setUiPageViewController();
        walletPresenter.updateBalance();
    }

    private void setUiPageViewController() {
        dotsCount = cardWalletAdpater.getCount();
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_selected_dot_wallet));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(22, 0, 22, 0);
            pager_indicator.addView(dots[i], params);
        }
        dots[pageCurrent].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selected_dot_wallet));
    }

    private void selectDots(int lastPosition, int nextposition) {
        dots[lastPosition].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_selected_dot_wallet));
        dots[nextposition].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selected_dot_wallet));
    }

    private void updateOperations(int position) {
        int colums = 3;
        //boolean isAgente = App.getInstance().getPrefs().loadDataBoolean(ES_AGENTE, false);
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

        if (!cardWalletAdpater.getElemenWallet(position).isUpdate()){
            imgReload.setVisibility(View.INVISIBLE);
        } else {
            imgReload.setVisibility(View.VISIBLE);
        }

    }

    private void upDateSaldo(int position){
        txtSaldo.setText(cardWalletAdpater.getElemenWallet(position).getSaldo());
    }

    @Override
    public void onItemClick(ElementView elementView) {
        Intent intent = new Intent(getContext(), WalletMainActivity.class);
        intent.putExtra(ID_OPERATION, elementView.getIdOperacion());
        intent.putExtra("CURRENT_PAGE", pageCurrent);
        startActivity(intent);
    }

    @Override
    public void getSaldo() {
        cardWalletAdpater.updateSaldo(0, StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)));
        if (App.getInstance().getPrefs().loadDataBoolean(ES_AGENTE, false) && App.getInstance().getPrefs().loadDataInt(ID_ESTATUS) == IdEstatus.ADQUIRENTE.getId())
            cardWalletAdpater.updateSaldo(1, StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(ADQUIRENTE_BALANCE)));
        updateOperations(pageCurrent);
    }

    @Override
    public void onResume() {
        super.onResume();
        walletPresenter.getInformacionAgente();
        //checkDataCard();
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
        previous_pos = pageCurrent;
        selectDots(previous_pos, position);
        pageCurrent = position;
        updateOperations(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void checkDataCard() {
        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            //Verificamos el estado de bloqueo de la Card
            String f = SingletonUser.getInstance().getCardStatusId();
            if (f == null || f.isEmpty() || f.equals("0")) {
                UsuarioClienteResponse usuarioClienteResponse = SingletonUser.getInstance().getDataUser().getUsuario();
                if (usuarioClienteResponse.getCuentas().size() != 0) {
                    mTDC = usuarioClienteResponse.getCuentas().get(0).getTarjeta();
                    walletPresenter.getStatusAccount(mTDC);
                }
            } else {
                pager_indicator.removeAllViews();
                walletPresenter.getWalletsCards(false);
            }
        } else {
            showDialogMesage(getResources().getString(R.string.no_internet_access));
        }
    }
}

