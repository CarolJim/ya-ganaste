package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarMovimientosMesResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataInfoAgente;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.interfaces.enums.MovementsColors;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui_wallet.patterns.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletInteractor;
import com.pagatodo.yaganaste.ui_wallet.interactors.WalletInteractorImpl;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletNotification;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletPresenter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IMovementsEmisorView;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IWalletView;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.ui_wallet.fragments.WalletTabFragment.ERROR_STATUS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_EMISOR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_ERROR_INFO_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OFFLINE;
import static com.pagatodo.yaganaste.utils.Recursos.COMPANY_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_BLOQUEADA;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_DOCUMENTACION;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE_RECHAZADO;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ESTATUS;
import static com.pagatodo.yaganaste.utils.Recursos.ID_USUARIO_ADQUIRIENTE;
import static com.pagatodo.yaganaste.utils.Recursos.STARBUCKS_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.TIPO_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.UPDATE_DATE_BALANCE_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

/**
 * Created by icruz on 12/12/2017.
 */

public class WalletPresenterImpl implements WalletPresenter, WalletNotification {

    private IWalletView walletView;
    //private IMovementsEmisorView movementsEmisorView;
    private WalletInteractor walletInteractor;

    public WalletPresenterImpl(IWalletView walletView) {
        this.walletView = walletView;
        this.walletInteractor = new WalletInteractorImpl(this);
    }

    @Override
    public void getWalletsCards(boolean error) {
        walletView.showProgress();
        walletInteractor.getWalletsCards(error, this);
    }

    @Override

    public void updateBalance(ElementWallet item) {
        if (item.isUpdate()) {
            walletView.showProgress();
            walletInteractor.getBalance(item.getTypeWallet());
        }
    }

    @Override
    public void getStatusAccount(String mTDC) {
        walletView.showProgress();
        if (!mTDC.isEmpty()) {
            EstatusCuentaRequest estatusCuentaRequest = new EstatusCuentaRequest(mTDC);
            walletInteractor.getStatusAccount(estatusCuentaRequest);
        } else {
            SingletonUser.getInstance().setCardStatusId(ESTATUS_CUENTA_BLOQUEADA);
            walletView.sendCardReported();
        }
    }

    @Override
    public void getInformacionAgente() {
        walletView.showProgress();
        walletInteractor.getInfoAgente();
    }


    @Override
    public void onSuccess(boolean error) {
        walletView.hideProgress();
        if (walletView != null) {
            walletView.hideProgress();
            walletView.getPagerAdapter(ContainerBuilder.getCardWalletAdapter(error));
        }
    }

    @Override
    public void onSuccesMovements(ConsultarMovimientosMesResponse response) {
        /*if (movementsEmisorView != null) {

            List<ItemMovements<MovimientosResponse>> movementsList = new ArrayList<>();
            String[] date;
            for (MovimientosResponse movimientosResponse : response.getData()) {
                date = movimientosResponse.getFechaMovimiento().split(" ");

                if (movimientosResponse.getIdTipoTransaccion() != 14) {
                    movementsList.add(new ItemMovements<>(movimientosResponse.getDescripcion(), movimientosResponse.getDetalle(),
                            movimientosResponse.getTotal(), date[0], date[1],
                            MovementsColors.getMovementColorByType(movimientosResponse.getTipoMovimiento()).getColor(),
                            movimientosResponse));
                } else {
                    movementsList.add(new ItemMovements<>(movimientosResponse.getDetalle(), movimientosResponse.getConcepto(),
                            movimientosResponse.getTotal(), date[0], date[1],
                            MovementsColors.getMovementColorByType(movimientosResponse.getTipoMovimiento()).getColor(),
                            movimientosResponse));
                }
            }
            movementsEmisorView.loadMovementsResult(movementsList);
            movementsEmisorView.hideProgress();
        }*/
    }


    @Override
    public void onSuccessResponse(GenericResponse response) {
        walletView.hideProgress();
        if (response instanceof EstatusCuentaResponse) {
            walletView.sendSuccessStatusAccount((EstatusCuentaResponse) response);
        }
    }

    @Override
    public void onSuccessInfoAgente(DataInfoAgente response) {
        walletView.hideProgress();
        Preferencias sp = App.getInstance().getPrefs();
        sp.saveDataBool(ES_AGENTE, response.isEsAgente());
        sp.saveDataBool(ES_AGENTE_RECHAZADO, response.isEsAgenteRechazado());
        sp.saveDataInt(ESTATUS_AGENTE, response.getEstatusAgente());
        sp.saveDataInt(ESTATUS_DOCUMENTACION, response.getEstatusDocumentacion());
        if (response.getIdEstatus() > 0)
            sp.saveDataInt(ID_ESTATUS, response.getIdEstatus());
        sp.saveData(ID_USUARIO_ADQUIRIENTE, response.getIdUsuarioAdquirente());
        sp.saveData(COMPANY_NAME, response.getNombreNegocio());
        sp.saveData(TIPO_AGENTE, response.getTipoAgente());
        RequestHeaders.setTokenAdq(response.getTokenSesionAdquirente());
        RequestHeaders.setIdCuentaAdq(response.getIdUsuarioAdquirente());
        walletView.sendSuccessInfoAgente();
    }

    @Override
    public void onFailed(int errorCode, int action, String error) {
        walletView.hideProgress();
        walletView.sendError(errorCode);
        /*switch (errorCode){
            case CODE_OFFLINE:

                break;
        }*/

        /*if (errorCode != CODE_ERROR_INFO_AGENTE) {
            if (walletView != null) {
                walletView.setError(error);
            } else if (this.movementsEmisorView != null) {
                movementsEmisorView.setError(error);
                movementsEmisorView.hideProgress();
            }
        } else if (errorCode == ERROR_STATUS){
            walletView.sendErrorStatus();
        } else {
            walletView.sendErrorInfoAgente();
            walletView.hideProgress();
        }*/

    }

    @Override
    public void onFailedSaldo(String error) {
        walletView.hideProgress();
        //walletView.finishProgressSaldo();
        walletView.setErrorSaldo(error);
    }

    @Override
    public void onSuccesSaldo(int typeWallet, String saldo) {
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
        walletView.hideProgress();
        //walletView.finishProgressSaldo();
        walletView.getSaldo(saldo);
    }
}