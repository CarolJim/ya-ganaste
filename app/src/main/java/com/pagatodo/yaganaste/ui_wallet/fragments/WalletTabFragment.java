package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardViewHome;
import com.pagatodo.yaganaste.ui.tarjeta.TarjetaUserPresenter;
import com.pagatodo.yaganaste.ui_wallet.WalletMainActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.CardWalletAdpater;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsWalletAdpater;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ElementView;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.ui_wallet.presenter.WalletPresenter;
import com.pagatodo.yaganaste.ui_wallet.presenter.WalletPresenterImpl;
import com.pagatodo.yaganaste.ui_wallet.views.ItemOffsetDecoration;
import com.pagatodo.yaganaste.ui_wallet.views.WalletView;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class WalletTabFragment extends SupportFragment implements WalletView,
        ElementsWalletAdpater.OnItemClickListener, IMyCardViewHome,
        ViewPager.OnPageChangeListener {

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


    private WalletPresenter walletPresenter;
    private TarjetaUserPresenter mPreferPresenter;
    private CardWalletAdpater cardWalletAdpater;
    private ElementsWalletAdpater elementsWalletAdpater;
    protected OnEventListener onEventListener;

    private int dotsCount;
    private ImageView[] dots;
    private int previous_pos = 0;
    private int pageCurrent;
    private String mTDC;

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
        GridLayoutManager llm = new GridLayoutManager(getContext(), 3);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        rcvOpciones.addItemDecoration(itemDecoration);
        rcvOpciones.setLayoutManager(llm);
        rcvOpciones.setHasFixedSize(true);
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
    public void completed() {
        progressLayout.setVisibility(View.GONE);
        cardWalletAdpater = new CardWalletAdpater();
        if (SingletonUser.getInstance().getCardStatusId().equalsIgnoreCase(Recursos.ESTATUS_CUENTA_BLOQUEADA)) {
            cardWalletAdpater.addCardItem(new ElementWallet().getCardyaganasteBloqueda(getContext()));
        } else {
            cardWalletAdpater.addCardItem(new ElementWallet().getCardyaganaste(getContext()));
        }

        if (SingletonUser.getInstance().getDataUser().isEsAgente() && SingletonUser.getInstance().getDataUser().getEstatusDocumentacion() == Recursos.CRM_DOCTO_APROBADO) {
            cardWalletAdpater.addCardItem(new ElementWallet().getCardLectorAdq(getContext()));
        } else {
            cardWalletAdpater.addCardItem(new ElementWallet().getCardLectorEmi(getContext()));
        }
        viewPagerWallet.setAdapter(cardWalletAdpater);
        viewPagerWallet.setCurrentItem(pageCurrent);
        viewPagerWallet.setOffscreenPageLimit(3);
        viewPagerWallet.addOnPageChangeListener(this);
        setUiPageViewController();
        updateOperations(pageCurrent);
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

    private void updateOperations(int psition) {
        pageCurrent = psition;
        if (psition == 0) {
            elementsWalletAdpater = new ElementsWalletAdpater(getContext(),this,ElementView.getListEmisor());

        }

        if (psition == 1) {
            if (SingletonUser.getInstance().getDataUser().isEsAgente() && SingletonUser.getInstance().getDataUser().getEstatusDocumentacion() == Recursos.CRM_DOCTO_APROBADO) {
                elementsWalletAdpater = new ElementsWalletAdpater(getContext(),this,ElementView.getListLectorAdq());
            } else {
                elementsWalletAdpater = new ElementsWalletAdpater(getContext(),this,ElementView.getListLectorEmi());
            }
        }
        rcvOpciones.setAdapter(elementsWalletAdpater);
        rcvOpciones.scheduleLayoutAnimation();
        txtSaldo.setText(cardWalletAdpater.getElemenWallet(psition).getSaldo());
        tipoSaldo.setText(cardWalletAdpater.getElemenWallet(psition).getTipoSaldo());
    }

    @Override
    public void onItemClick(ElementView elementView) {
        Intent intent = new Intent(getContext(), WalletMainActivity.class);
        intent.putExtra(ID_OPERATION, elementView.getIdOperacion());
        intent.putExtra("CURRENT_PAGE", pageCurrent);
        startActivity(intent);
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
        walletPresenter.getWalletsCards();

    }

    @Override
    public void sendErrorBloquearCuentaToView(String mensaje) {
        showDialogMesage("Error De Inicio De Sesión");
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
                if (usuarioClienteResponse.getCuentas().size() != 0 ) {
                    mTDC = usuarioClienteResponse.getCuentas().get(0).getTarjeta();
                    mPreferPresenter.toPresenterEstatusCuenta(mTDC);
                }
            } else {
                pager_indicator.removeAllViews();
                walletPresenter.getWalletsCards();
            }


        } else {
            showDialogMesage(getResources().getString(R.string.no_internet_access));
        }
    }

    private void showDialogMesage(final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        Utils.sessionExpired();
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }

}

