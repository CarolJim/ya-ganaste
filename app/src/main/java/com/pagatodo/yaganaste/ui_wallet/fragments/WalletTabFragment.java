package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EmisorResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDocumentosResponse;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardViewHome;
import com.pagatodo.yaganaste.ui_wallet.WalletMainActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.CardWalletAdpater;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsWalletAdapter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IWalletView;
import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletPresenter;
import com.pagatodo.yaganaste.ui_wallet.patterns.Wallet;
import com.pagatodo.yaganaste.ui_wallet.patterns.WalletBuilder;
import com.pagatodo.yaganaste.ui_wallet.patterns.factories.PresenterFactory;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.ui_wallet.views.BoardIndicationsView;
import com.pagatodo.yaganaste.ui_wallet.views.CustomDots;
import com.pagatodo.yaganaste.ui_wallet.views.CutomWalletViewPage;
import com.pagatodo.yaganaste.ui_wallet.views.ItemOffsetDecoration;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.TabActivity.EVENT_LOGOUT;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.PICK_WALLET_TAB_REQUEST;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.RESULT_ADQUIRENTE_SUCCESS;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.RESULT_CODE_SELECT_DONGLE;
import static com.pagatodo.yaganaste.ui_wallet.patterns.factories.PresenterFactory.TypePresenter.WALLETPRESENTER;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ERROR_ADDRESS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ERROR_ADDRESS_DOCS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_BUSINESS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_STATUS;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_ERROR_INFO_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OFFLINE;
import static com.pagatodo.yaganaste.utils.Recursos.FOLIOADQ;
import static com.pagatodo.yaganaste.utils.Recursos.ID_COMERCIOADQ;
import static com.pagatodo.yaganaste.utils.Recursos.IS_OPERADOR;

public class WalletTabFragment extends SupportFragment implements IWalletView,
        OnItemClickListener, IMyCardViewHome, ViewPager.OnPageChangeListener, View.OnClickListener {

    public static final String ITEM_OPERATION = "ITEM_OPERATION";
    public static final int ERROR_STATUS = 12;

    @BindView(R.id.progressGIF)
    ProgressLayout progressLayout;
    @BindView(R.id.pager_container)
    CutomWalletViewPage pageContainer;
    @BindView(R.id.rcv_elements)
    RecyclerView rcvOpciones;
    @BindView(R.id.viewPagerCountDots)
    CustomDots pager_indicator;
    @BindView(R.id.board_indication)
    BoardIndicationsView board;
    private Preferencias prefs = App.getInstance().getPrefs();
    private WalletPresenter walletPresenter;
    private CardWalletAdpater cardWalletAdpater;
    private ElementsWalletAdapter elementsWalletAdapter;
    protected OnEventListener onEventListener;
    private ViewPager viewPagerWallet;
    private boolean isBegin;
    private int pageCurrent;
    private GridLayoutManager llm;
    private ElementView element;

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
        walletPresenter = (WalletPresenter) PresenterFactory.newInstace(this).getPresenter(WALLETPRESENTER);
        cardWalletAdpater = new CardWalletAdpater(true);
        isBegin = true;

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallet_layout, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void initViews() {
        viewPagerWallet = pageContainer.getViewPager();
        viewPagerWallet.setOffscreenPageLimit(3);
        viewPagerWallet.setPageMargin(15);
        viewPagerWallet.addOnPageChangeListener(this);
        viewPagerWallet.setAdapter(cardWalletAdpater);
        elementsWalletAdapter = new ElementsWalletAdapter(getActivity(), this, false);
        llm = new GridLayoutManager(getContext(), 3);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(),
                R.dimen.item_offset);
        rcvOpciones.addItemDecoration(itemDecoration);
        rcvOpciones.setLayoutManager(llm);
        rcvOpciones.setHasFixedSize(true);
        board.setreloadOnclicklistener(this);
        checkDataCard();
    }

    @Override
    public void showProgress() {
        board.setVisibility(View.INVISIBLE);
        progressLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        if (!prefs.containsData(IS_OPERADOR)) {
            board.setVisibility(View.VISIBLE);
        } else {
            board.setVisibility(View.INVISIBLE);
            pager_indicator.setVisibility(View.INVISIBLE);
        }
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void setError(String message) {
        UI.showErrorSnackBar(getActivity(), message, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void getPagerAdapter(ArrayList<ElementWallet> elementWallets) {
        cardWalletAdpater.addAllList(elementWallets);
        viewPagerWallet.setAdapter(cardWalletAdpater);
        pager_indicator.setView(0, cardWalletAdpater.getSize());
        updateOperations(0);
        //walletPresenter.updateBalance(cardWalletAdpater.getElemenWallet(0));
        viewPagerWallet.setCurrentItem(cardWalletAdpater.getSize() > 2 ? cardWalletAdpater.getCount() / 2 : 0);
        if (Utils.isDeviceOnline()) {
            String f = SingletonUser.getInstance().getCardStatusId();
            if (f == null || f.isEmpty() || f.equals("0")) {
                EmisorResponse usuarioClienteResponse = SingletonUser.getInstance().getDataUser().getEmisor();
                if (usuarioClienteResponse.getCuentas().size() != 0) {
                    walletPresenter.getStatusAccount(usuarioClienteResponse.getCuentas().get(0).getTarjetas().get(0).getNumero());
                }
            }
        }
        if (!prefs.containsData(IS_OPERADOR)) {
            board.setVisibility(View.VISIBLE);
        }
    }


    private void updateOperations(final int position) {
        int colums = 3;
        if (cardWalletAdpater.getElemenWallet(position).getElementViews().size() <= 1) {
            colums = 1;
        }
        if (cardWalletAdpater.getElemenWallet(position).getElementViews().size() == 2) {
            colums = 2;
        }
        elementsWalletAdapter.setListOptions(cardWalletAdpater.getElemenWallet(position).getElementViews());
        elementsWalletAdapter.notifyDataSetChanged();
        llm.setSpanCount(colums);
        rcvOpciones.setLayoutManager(llm);
        upDateSaldo(position);
        rcvOpciones.setAdapter(elementsWalletAdapter);
        rcvOpciones.scheduleLayoutAnimation();
        board.setTextSaldo(cardWalletAdpater.getElemenWallet(position).getTipoSaldo());
        if (cardWalletAdpater.getElemenWallet(position).isUpdate()) {
            board.setReloadVisibility(View.VISIBLE);
        } else {
            board.setReloadVisibility(View.INVISIBLE);
        }
    }

    private void upDateSaldo(int position) {
        board.setTextMonto(cardWalletAdpater.getElemenWallet(position).getSaldo());
    }

    private void upDateSaldo(String saldo) {
        cardWalletAdpater.updateSaldo(this.pageCurrent, saldo);
        board.setTextMonto(saldo);
    }

    @Override
    public void onItemClick(ElementView itemOperation) {
        this.element = itemOperation;
        if (itemOperation.getIdOperacion() == 12) {  // Error en documentación
            walletPresenter.getStatusDocuments();
        } else {
            goToWalletMainActivity();
        }
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
        App.getInstance().getPrefs().saveData(CARD_STATUS, statusId);

        //cardWalletAdpater.changeStatusCard(pageCurrent);
        //cardWalletAdpater.notifyDataSetChanged();
        if (!isBegin) {
            //if (!prefs.containsData(IS_OPERADOR)) {
              //  walletPresenter.updateBalance(cardWalletAdpater.getElemenWallet(this.pageCurrent));
            //}
            Wallet walletList = WalletBuilder.createWalletsEsencials(false);
            cardWalletAdpater.addAllList(walletList.getList());
            //viewPagerWallet.clearOnPageChangeListeners();
            viewPagerWallet.setAdapter(cardWalletAdpater);
            viewPagerWallet.setCurrentItem(pageCurrent);
            //viewPagerWallet.addOnPageChangeListener(this);
        }
    }

    @Override
    public void sendSuccessEstatusDocs(ObtenerDocumentosResponse response) {
        String folio = "";
        try {
            folio = new DatabaseManager().getFolioAgente(element.getIdComercio());
            prefs.saveData(FOLIOADQ, folio);

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        walletPresenter.getInfoComercio(folio);
        if (response.getIdEstatus() > 0 && !response.hasErrorInDocs()) { // Error en domicilio
            element.setIdOperacion(OPTION_ERROR_ADDRESS);
            UI.showAlertDialog(getActivity(), "Por favor revisa los siguientes datos", response.getMotivo(), "Revisar datos", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    goToWalletMainActivity();
                }
            });
        } else if (response.getIdEstatus() > 0 && response.hasErrorInDocs()) { // Error en domicilio y documentos
            element.setIdOperacion(OPTION_ERROR_ADDRESS_DOCS);
            UI.showAlertDialog(getActivity(), "Por favor revisa los siguientes datos", response.getMotivo(), "Revisar datos", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    goToWalletMainActivity();
                }
            });
        } else {
            goToWalletMainActivity();
        }
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
        pager_indicator.selectDots(this.pageCurrent % cardWalletAdpater.getSize(), position % cardWalletAdpater.getSize());
        this.pageCurrent = position;
        cardWalletAdpater.resetFlip();
        updateOperations(position);

        if (!prefs.containsData(IS_OPERADOR)) {
            walletPresenter.updateBalance(cardWalletAdpater.getElemenWallet(position));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void checkDataCard() {
        boolean isOnline = Utils.isDeviceOnline();
        if (isOnline) {
            pager_indicator.removeAllViews();
            walletPresenter.getWalletsCards(false);
        } else {
            showDialogMesage(getResources().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void setErrorSaldo(String errorSaldo) {
        UI.showErrorSnackBar(getActivity(), errorSaldo, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void sendError(int codeError) {
        switch (codeError) {
            case CODE_OFFLINE:
                showDialogMesage(getResources().getString(R.string.no_internet_access));
                break;
            case ERROR_STATUS:
                //walletPresenter.getWalletsCards(false);
                if (Utils.isDeviceOnline()) {
                    if (!isBegin) {
                        if (!prefs.containsData(IS_OPERADOR)) {
                            walletPresenter.updateBalance(cardWalletAdpater.getElemenWallet(this.pageCurrent));
                        }
                    }
                }
                break;
            case CODE_ERROR_INFO_AGENTE:
                onEventListener.onEvent(EVENT_LOGOUT, null);
                break;
        }
    }


    @Override
    public void onClick(View view) {
        if (!prefs.containsData(IS_OPERADOR)) {
            if (elementsWalletAdapter.getItemCount() > 0) {
                walletPresenter.updateBalance(cardWalletAdpater.getElemenWallet(this.pageCurrent));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PICK_WALLET_TAB_REQUEST) {
            isBegin = false;
            boolean isOnline = Utils.isDeviceOnline();
            if (isOnline) {
                //String f = SingletonUser.getInstance().getCardStatusId();
                //if (f == null || f.isEmpty() || f.equals("0")) {
                if (!prefs.containsData(IS_OPERADOR)) {
                    if (cardWalletAdpater.getElemenWallet(pageCurrent).getTypeWallet() == TYPE_EMISOR) {
                        EmisorResponse usuarioClienteResponse = SingletonUser.getInstance().getDataUser().getEmisor();

                        if (usuarioClienteResponse.getCuentas().size() != 0) {
                            walletPresenter.getStatusAccount(usuarioClienteResponse.getCuentas().get(0).getTarjetas().get(0).getNumero());
                        }
                    } else if (cardWalletAdpater.getElemenWallet(pageCurrent).getTypeWallet() == TYPE_ADQ || cardWalletAdpater.getElemenWallet(pageCurrent).getTypeWallet() == TYPE_BUSINESS) {
                        walletPresenter.updateBalance(cardWalletAdpater.getElemenWallet(this.pageCurrent));
                    }
                }
            }
        } else if (resultCode == RESULT_CODE_SELECT_DONGLE || resultCode == RESULT_ADQUIRENTE_SUCCESS) {
            checkDataCard();
        }
    }

    private void goToWalletMainActivity() {
        Intent intent = new Intent(getContext(), WalletMainActivity.class);
        intent.putExtra(ITEM_OPERATION, element);
        startActivityForResult(intent, PICK_WALLET_TAB_REQUEST);
    }
}

