package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ConsultarMovimientosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarMovimientosMesResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.interfaces.enums.MovementsColors;
import com.pagatodo.yaganaste.ui_wallet.builder.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletInteractor;
import com.pagatodo.yaganaste.ui_wallet.interactors.WalletInteractorImpl;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletNotification;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletPresenter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IMovementsEmisorView;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IWalletView;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.StringConstants;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.utils.StringConstants.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.StringConstants.UPDATE_DATE_BALANCE_ADQ;
import static com.pagatodo.yaganaste.utils.StringConstants.USER_BALANCE;

/**
 * Created by icruz on 12/12/2017.
 */

public class WalletPresenterImpl implements WalletPresenter, WalletNotification {

    private IWalletView walletView;
    private IMovementsEmisorView movementsEmisorView;
    private WalletInteractor walletInteractor;

    public WalletPresenterImpl(IWalletView walletView) {
        this.walletView = walletView;
        this.walletInteractor = new WalletInteractorImpl(this);
    }

    public WalletPresenterImpl(IMovementsEmisorView movementsEmisorView) {
        this.movementsEmisorView = movementsEmisorView;
        this.walletInteractor = new WalletInteractorImpl(this);
    }

    @Override
    public void getWalletsCards(boolean error) {
        walletView.showProgress();
        walletInteractor.getWalletsCards(error, this);
    }

    @Override
    public void updateBalance() {
        walletView.showProgress();
        walletInteractor.getBalance();
    }

    @Override
    public void getStatusAccount(String mTDC) {
        //mView.showLoader("Obteniendo Estatus de Tarjeta");
        walletView.showProgress();
        EstatusCuentaRequest estatusCuentaRequest = new EstatusCuentaRequest(mTDC);
        walletInteractor.getStatusAccount(estatusCuentaRequest);
    }

    @Override
    public void getRemoteMovementsData() {
        movementsEmisorView.showProgress();
        ConsultarMovimientosRequest request = new ConsultarMovimientosRequest();
        //if (data.getYear() == -1) {
        request.setAnio("2018");
        request.setMes("1");
        //} else {
        //request.setAnio(String.valueOf(data.getYear()));
        request.setMes("1");
        //}
        request.setDireccion("");
        request.setIdMovimiento("");
        this.walletInteractor.getMovements(request);

    }

    @Override
    public void onSuccessADQ(String response) {
        walletView.hideProgress();
        App.getInstance().getPrefs().saveData(ADQUIRENTE_BALANCE, response);
        App.getInstance().getPrefs().saveData(UPDATE_DATE_BALANCE_ADQ, DateUtil.getTodayCompleteDateFormat());
        walletView.getSaldo();
    }

    @Override
    public void onSuccess(boolean error) {
        if (walletView != null) {
            walletView.getPagerAdapter(ContainerBuilder.getCardWalletAdapter(error));
            //walletView.completed(error);
        }
    }



    @Override
    public void onSuccessEmisor(String responds) {
        App.getInstance().getPrefs().saveData(USER_BALANCE, responds);
        walletView.getSaldo();
    }

    @Override
    public void onSuccesMovements(ConsultarMovimientosMesResponse response) {
        if (movementsEmisorView != null) {

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
        }
    }


    @Override
    public void onSuccessResponse(GenericResponse response) {
        if (response instanceof EstatusCuentaResponse){
            walletView.hideProgress();
            walletView.sendSuccessStatusAccount((EstatusCuentaResponse)response);
        }
    }

    @Override
    public void onFailed(int errorCode, int action, String error) {

        if (walletView != null){
            walletView.setError(error);
            walletView.hideProgress();
        } else if (this.movementsEmisorView != null){
            movementsEmisorView.setError(error);
            movementsEmisorView.hideProgress();
        }
    }
}
