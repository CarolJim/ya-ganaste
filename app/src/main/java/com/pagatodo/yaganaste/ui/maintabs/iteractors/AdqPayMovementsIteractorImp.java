package com.pagatodo.yaganaste.ui.maintabs.iteractors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ResumenMovimientosMesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ConsultaSaldoCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataResultAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneDatosCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ResumenMovimientosAdqResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdq;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.MovementsIteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.MovementsManager;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringUtils;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public class AdqPayMovementsIteractorImp implements MovementsIteractor<ResumenMovimientosMesRequest> {

    MovementsManager<ResumenMovimientosAdqResponse, String> movementsManager;

    public AdqPayMovementsIteractorImp(MovementsManager<ResumenMovimientosAdqResponse, String> movementsManager) {
        this.movementsManager = movementsManager;
    }

    @Override
    public void getMovements(ResumenMovimientosMesRequest request) {
        try {
            ApiAdq.resumenMovimientosMes(request, this);
        } catch (OfflineException e) {
            movementsManager.onFailed(Recursos.CODE_OFFLINE, Recursos.NO_ACTION, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void getBalance() {
        try {
            ApiAdq.consultaSaldoCupo(this);
        } catch (OfflineException e) {
            //No-op
        }
    }

    @Override
    public void getDatosCupo() {
        try {
            ApiAdq.obtieneDatosCupo(this);
        } catch (OfflineException e) {
            //No-op
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

        switch (dataSourceResult.getWebService()) {

            case CONSULTA_MOVIMIENTOS_MES_ADQ:
                validateResponse((ResumenMovimientosAdqResponse) dataSourceResult.getData());
                break;

            case CONSULTAR_SALDO_ADQ:
                validateBalanceResponse((ConsultaSaldoCupoResponse) dataSourceResult.getData());
                break;

            case OBTIENE_DATOS_CUPO:
                validateDataCupo((ObtieneDatosCupoResponse) dataSourceResult.getData());
                break;
        }
    }

    private void validateDataCupo(ObtieneDatosCupoResponse response) {
        if (response.getResult().getId().equals(Recursos.CODE_ADQ_OK)) {
            movementsManager.onSuccessDataCupo(response);
        } else {
            movementsManager.onFailed(0, Recursos.NO_ACTION, response.getResult().getMessage());
        }
    }

    private void validateResponse(ResumenMovimientosAdqResponse response) {
        DataResultAdq dataResultAdq = response.getResult();
        if (dataResultAdq != null) {
            if (dataResultAdq.getId().equals(String.valueOf(Recursos.INVALID_TOKEN))) {
                movementsManager.onFailed(Recursos.INVALID_TOKEN, Recursos.GO_LOGIN, dataResultAdq.getMessage());
            } else if (dataResultAdq.getId().equals(Recursos.CODE_ADQ_OK)) {
                movementsManager.onSuccesResponse(response);
            } else {
                movementsManager.onFailed(StringUtils.getIntValue(dataResultAdq.getId()), Recursos.NO_ACTION, dataResultAdq.getMessage());
            }
        } else {
            movementsManager.onFailed(Recursos.INCORRECT_FORMAT, Recursos.NO_ACTION, App.getInstance().getString(R.string.error_respuesta));
        }
    }


    private void validateBalanceResponse(ConsultaSaldoCupoResponse response) {
        movementsManager.onSuccesBalance(response.getSaldo());
    }


    @Override
    public void onFailed(DataSourceResult error) {
        // TODO: 28/03/2017 Verificar los codigos de error y accion para este caso
        movementsManager.onFailed(0, Recursos.NO_ACTION, error.getData().toString());
    }
}
