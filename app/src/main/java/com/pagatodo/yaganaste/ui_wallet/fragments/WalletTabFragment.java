package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DatosNegocioFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DocumentosFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.StatusRegisterAdquirienteFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardViewHome;
import com.pagatodo.yaganaste.ui.tarjeta.TarjetaUserPresenter;
import com.pagatodo.yaganaste.ui_wallet.WalletMainActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.CardWalletAdpater;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsWalletAdapter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.WalletPresenterImpl;
import com.pagatodo.yaganaste.ui_wallet.views.ItemOffsetDecoration;
import com.pagatodo.yaganaste.ui_wallet.views.WalletView;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_PROCESS;
import static com.pagatodo.yaganaste.utils.StringConstants.ADQUIRENTE_BALANCE;

/**
 *
 */
public class WalletTabFragment extends SupportFragment implements WalletView,
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
    @BindView(R.id.txt_anuncio)
    StyleTextView anuncio;


    private WalletPresenter walletPresenter;
    private TarjetaUserPresenter mPreferPresenter;
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
        mPreferPresenter = new TarjetaUserPresenter(this);
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
        llm = new GridLayoutManager(getContext(), 3);
        linearLayoutManager = new LinearLayoutManager(getContext());
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        rcvOpciones.addItemDecoration(itemDecoration);
        rcvOpciones.setLayoutManager(llm);
        rcvOpciones.setHasFixedSize(true);
        imgReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataCard();
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
    public void setError() {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void completed(boolean error) {
        progressLayout.setVisibility(View.GONE);
        cardWalletAdpater = new CardWalletAdpater();

        if (error) {
            cardWalletAdpater.addCardItem(new ElementWallet().getCardyaganaste(getContext()));
        } else if (SingletonUser.getInstance().getCardStatusId().equalsIgnoreCase(Recursos.ESTATUS_CUENTA_BLOQUEADA)) {
            cardWalletAdpater.addCardItem(new ElementWallet().getCardyaganasteBloqueda(getContext()));
            if (SingletonUser.getInstance().getDataUser().isEsAgente() && SingletonUser.getInstance().getDataUser().getEstatusDocumentacion() == Recursos.CRM_DOCTO_APROBADO) {
                cardWalletAdpater.addCardItem(new ElementWallet().getCardLectorAdq(getContext()));

            } else {
                cardWalletAdpater.addCardItem(new ElementWallet().getCardLectorEmi(getContext()));
            }
        } else {
            cardWalletAdpater.addCardItem(new ElementWallet().getCardyaganaste(getContext()));
            if (SingletonUser.getInstance().getDataUser().isEsAgente() && SingletonUser.getInstance().getDataUser().getEstatusDocumentacion() == Recursos.CRM_DOCTO_APROBADO) {
                cardWalletAdpater.addCardItem(new ElementWallet().getCardLectorAdq(getContext()));

            } else {
                cardWalletAdpater.addCardItem(new ElementWallet().getCardLectorEmi(getContext()));
            }
        }

        viewPagerWallet.setAdapter(cardWalletAdpater);
        viewPagerWallet.setCurrentItem(pageCurrent);
        viewPagerWallet.setOffscreenPageLimit(3);
        viewPagerWallet.addOnPageChangeListener(this);
        setUiPageViewController();

        if (SingletonUser.getInstance().getDataUser().isEsAgente() && SingletonUser.getInstance().getDataUser().getEstatusDocumentacion() == Recursos.CRM_DOCTO_APROBADO) {
            walletPresenter.updateBalance();
        } else {
            updateOperations(pageCurrent);
        }
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

    private void updateOperations(int position) {
        pageCurrent = position;
        String setMont = "";
        if (position == 0) {
            llm.setSpanCount(3);
            rcvOpciones.setLayoutManager(llm);
            elementsWalletAdapter = new ElementsWalletAdapter(getActivity(), this, ElementView.getListEmisor(), 0);
            txtSaldo.setVisibility(View.VISIBLE);
            txtSaldo.setText(cardWalletAdpater.getElemenWallet(position).getSaldo());
            anuncio.setVisibility(View.GONE);
        }
        if (position == 1) {

            int Idestatus = SingletonUser.getInstance().getDataUser().getIdEstatus();
            llm.setSpanCount(1);
            rcvOpciones.setLayoutManager(llm);
            txtSaldo.setVisibility(View.GONE);
            anuncio.setVisibility(View.VISIBLE);
            anuncio.setText(cardWalletAdpater.getElemenWallet(position).getSaldo());
            if (SingletonUser.getInstance().getDataUser().isEsAgente()
                    && Idestatus == IdEstatus.I7.getId()) {
                elementsWalletAdapter = new ElementsWalletAdapter(getActivity(), this, ElementView.getListEstadoRevisando(), 2);

            } else if (SingletonUser.getInstance().getDataUser().isEsAgente()
                    && Idestatus == IdEstatus.I8.getId()) {
                elementsWalletAdapter = new ElementsWalletAdapter(getActivity(), this, ElementView.getListEstadoRevisando(), 2);
            } else if (SingletonUser.getInstance().getDataUser().isEsAgente() &&
                    Idestatus == IdEstatus.I9.getId()) {
                elementsWalletAdapter = new ElementsWalletAdapter(getActivity(), this, ElementView.getListEstadoError(), 2);
            } else if (SingletonUser.getInstance().getDataUser().isEsAgente() &&
                    Idestatus == IdEstatus.I10.getId()) {
                elementsWalletAdapter = new ElementsWalletAdapter(getActivity(), this, ElementView.getListEstadoRechazado(), 2);
            } else if (SingletonUser.getInstance().getDataUser().isEsAgente() &&
                    Idestatus == IdEstatus.I11.getId()) {
                elementsWalletAdapter = new ElementsWalletAdapter(getActivity(), this, ElementView.getListEstadoAprobado(), 2);

            } else if (SingletonUser.getInstance().getDataUser().isEsAgente() &&
                    Idestatus == IdEstatus.I13.getId()) {
                elementsWalletAdapter = new ElementsWalletAdapter(getActivity(), this, ElementView.getListEstadoRechazado(), 2);
            } else if (SingletonUser.getInstance().getDataUser().isEsAgente() && SingletonUser.getInstance().getDataUser().getEstatusDocumentacion() == Recursos.CRM_DOCTO_APROBADO) {
                llm.setSpanCount(3);
                rcvOpciones.setLayoutManager(llm);
                elementsWalletAdapter = new ElementsWalletAdapter(getActivity(), this, ElementView.getListLectorAdq(),0);
                txtSaldo.setVisibility(View.VISIBLE);
                txtSaldo.setText(cardWalletAdpater.getElemenWallet(position).getSaldo());
                anuncio.setVisibility(View.GONE);
            } else {
                elementsWalletAdapter = new ElementsWalletAdapter(getActivity(), this, ElementView.getListLectorEmi(), 1);
            }
        }

        rcvOpciones.setAdapter(elementsWalletAdapter);
        rcvOpciones.scheduleLayoutAnimation();

        tipoSaldo.setText(cardWalletAdpater.getElemenWallet(position).getTipoSaldo());
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
        cardWalletAdpater.updateSaldo(1, Utils.getCurrencyValue(App.getInstance().getPrefs().loadData(ADQUIRENTE_BALANCE)));
        //cardWalletAdpater.updateSaldo(1,Utils.getCurrencyValue(App.getInstance().getPrefs().loadData(ADQUIRENTE_BALANCE)));
        updateOperations(pageCurrent);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkDataCard();
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
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_selected_dot_wallet));
        }
        dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selected_dot_wallet));
        int pos = position + 1;
        if (pos == dotsCount && previous_pos == (dotsCount - 1)) {
        } else if (pos == (dotsCount - 1) && previous_pos == dotsCount) {
        }
        previous_pos = pos;
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
                    mPreferPresenter.toPresenterEstatusCuenta(mTDC);
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

