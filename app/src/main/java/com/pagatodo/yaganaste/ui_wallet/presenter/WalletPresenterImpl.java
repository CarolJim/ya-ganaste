package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.support.annotation.Nullable;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarSaldoResponse;
import com.pagatodo.yaganaste.ui_wallet.interactors.WalletInteractorImpl;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IWalletView;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletNotification;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletPresenter;
import com.pagatodo.yaganaste.ui_wallet.patterns.Wallet;
import com.pagatodo.yaganaste.ui_wallet.patterns.WalletBuilder;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.utils.DateUtil;

import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_EMISOR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_BLOQUEADA;
import static com.pagatodo.yaganaste.utils.Recursos.STARBUCKS_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.UPDATE_DATE_BALANCE_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

/**
 * Created by icruz on 12/12/2017.
 */

public class WalletPresenterImpl implements WalletPresenter, WalletNotification {

    private IWalletView walletView;
    private WalletInteractor walletInteractor;

    public WalletPresenterImpl(IWalletView walletView) {
        this.walletView = walletView;
        this.walletInteractor = new WalletInteractorImpl(this);
    }

    @Override
    public void getWalletsCards(boolean error) {
        //walletView.showProgress();
        walletInteractor.getWalletsCards(error, this);
    }

    @Override

    public void updateBalance(ElementWallet item) {
        if (item.isUpdate()) {
            walletView.showProgress();
            walletInteractor.getBalance(item.getTypeWallet(), item.getAgentes());
        } else {
            onSuccesSaldo(item.getTypeWallet(), "");
        }
    }

    @Override
    public void getStatusAccount(String mTDC) {
        walletView.showProgress();
        if (!mTDC.isEmpty()) {
            EstatusCuentaRequest estatusCuentaRequest = new EstatusCuentaRequest(mTDC.replace(" ", ""));
            walletInteractor.getStatusAccount(estatusCuentaRequest);
        } else {
            SingletonUser.getInstance().setCardStatusId(ESTATUS_CUENTA_BLOQUEADA);
            walletView.sendCardReported();
        }
    }

    @Override
    public void getStatusDocuments() {
        walletView.showProgress();
        walletInteractor.getEstatusDocumentos();
    }

    @Override
    public void getInfoComercio(String folio) {
        walletInteractor.getInfoComercio(folio);
    }
/*
    @Override
    public void getInformacionAgente() {
        walletView.showProgress();
        walletInteractor.getInfoAgente();
    }*/

    @Override
    public void onSuccess(@Nullable Integer typeWallet, @Nullable Object result) {
        walletView.hideProgress();
        if (SingletonUser.getInstance().getDataUser().getUsuario().getRoles().get(0).getIdRol() == 129) {
            switch (typeWallet) {
                case TYPE_ADQ:
                    App.getInstance().getPrefs().saveData(UPDATE_DATE_BALANCE_ADQ, DateUtil.getTodayCompleteDateFormat());
                    break;
            }
        } else if (SingletonUser.getInstance().getDataUser().getControl().getEsCliente()) {
            String saldo = ((ConsultarSaldoResponse) result).getData().getSaldo();
            switch (typeWallet) {
                case TYPE_STARBUCKS:
                    App.getInstance().getPrefs().saveData(STARBUCKS_BALANCE, saldo);
                    break;
                case TYPE_EMISOR:
                    App.getInstance().getPrefs().saveData(USER_BALANCE, saldo);
                    break;
                case TYPE_ADQ:
                    App.getInstance().getPrefs().saveData(ADQUIRENTE_BALANCE, saldo);
                    App.getInstance().getPrefs().saveData(UPDATE_DATE_BALANCE_ADQ, DateUtil.getTodayCompleteDateFormat());
                    break;
            }
            walletView.getSaldo(saldo);
        }
    }

    @Override
    public void onSuccess(boolean error) {
        if (walletView != null) {
            //walletView.hideProgress();
            //CardWalletAdpater adapter = new CardWalletAdpater(true);
            Wallet walletList = WalletBuilder.createWalletsEsencials(error);
            //adapter.addAllList(walletList.getList());
            //adapter.notifyDataSetChanged();
            walletView.getPagerAdapter(walletList.getList());
        }
    }

    @Override
    public void onSuccessResponse(GenericResponse response) {
        walletView.hideProgress();
        if (response instanceof EstatusCuentaResponse) {
            walletView.sendSuccessStatusAccount((EstatusCuentaResponse) response);
        } else if (response instanceof ObtenerDocumentosResponse){
            walletView.sendSuccessEstatusDocs((ObtenerDocumentosResponse) response);
        }
    }

    @Override
    public void onFailed(int errorCode, int action, String error) {
        walletView.hideProgress();
        walletView.sendError(errorCode);
    }

    @Override
    public void onFailedSaldo(String error) {
        walletView.hideProgress();
        //walletView.finishProgressSaldo();
        walletView.setErrorSaldo(error);
    }

    @Override
    public void onSuccesSaldo(int typeWallet, String saldo) {
        walletView.hideProgress();
        if (saldo!=null && !saldo.equals("")) {
            switch (typeWallet) {
                case TYPE_STARBUCKS:
                    App.getInstance().getPrefs().saveData(STARBUCKS_BALANCE, saldo);
                    break;
                case TYPE_EMISOR:
                    App.getInstance().getPrefs().saveData(USER_BALANCE, saldo);
                    break;
                case TYPE_ADQ:
                    App.getInstance().getPrefs().saveData(ADQUIRENTE_BALANCE, saldo);
                    App.getInstance().getPrefs().saveData(UPDATE_DATE_BALANCE_ADQ, DateUtil.getTodayCompleteDateFormat());
                    break;
            }
            walletView.getSaldo(saldo);
        }
    }
}