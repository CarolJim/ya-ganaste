package com.pagatodo.yaganaste.ui.maintabs.iteractors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ReembolsoDataRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ConsultarMovimientosRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarMovimientosMesResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarSaldoResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.MovementsIteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.MovementsManager;
import com.pagatodo.yaganaste.utils.Recursos;

/**
 * @author Juan Guerra on 28/03/2017.
 */
public class AccountMovementsIteractorImp implements MovementsIteractor<ConsultarMovimientosRequest> {

    private MovementsManager<ConsultarMovimientosMesResponse, ConsultarSaldoResponse> movementsManager;

    public AccountMovementsIteractorImp(MovementsManager<ConsultarMovimientosMesResponse, ConsultarSaldoResponse> movementsManager) {
        this.movementsManager = movementsManager;
    }

    @Override
    public void getMovements(ConsultarMovimientosRequest request) {
        try {
            ApiAdtvo.consultarMovimientosMes(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            movementsManager.onFailed(Recursos.CODE_OFFLINE, Recursos.NO_ACTION, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void getBalance() {
        try {
            ApiTrans.consultarSaldo(this);
        } catch (OfflineException e) {
            //No-op
        }
    }

    @Override
    public void getDatosCupo() {

    }

    @Override
    public void sendRemmbolso(ReembolsoDataRequest request) {

    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        switch (dataSourceResult.getWebService()) {

            case CONSULTAR_MOVIMIENTOS_MES:
                validateResponse((ConsultarMovimientosMesResponse) dataSourceResult.getData());
                break;

            case CONSULTAR_SALDO:
                validateBalanceResponse((ConsultarSaldoResponse) dataSourceResult.getData());
                break;
        }

    }

    private void validateResponse(ConsultarMovimientosMesResponse response) {
        if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
            movementsManager.onSuccesResponse(response);
        } else {
            movementsManager.onFailed(response.getCodigoRespuesta(), response.getAccion(), response.getMensaje());
        }
    }

    private void validateBalanceResponse(ConsultarSaldoResponse response) {
        if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
            movementsManager.onSuccesBalance(response);
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        if (error.getWebService() == WebService.CONSULTAR_MOVIMIENTOS_MES) {
            movementsManager.onFailed(0, Recursos.NO_ACTION, error.getData().toString());
        }
    }
}
