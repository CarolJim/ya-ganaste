package com.pagatodo.yaganaste.ui.maintabs.iteractors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ResumenMovimientosMesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataResultAdq;
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

    MovementsManager<ResumenMovimientosAdqResponse> movementsManager;

    public AdqPayMovementsIteractorImp(MovementsManager<ResumenMovimientosAdqResponse> movementsManager) {
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
    public void onSuccess(DataSourceResult dataSourceResult) {
        validateResponse((ResumenMovimientosAdqResponse) dataSourceResult.getData());
    }

    private void validateResponse(ResumenMovimientosAdqResponse response){
        DataResultAdq dataResultAdq = response.getResult();
        if (dataResultAdq != null){
            if (dataResultAdq.getId().equals(String.valueOf(Recursos.INVALID_TOKEN))) {
                movementsManager.onFailed(Recursos.INVALID_TOKEN, Recursos.GO_LOGIN, "");
            } else if (dataResultAdq.getId().equals(String.valueOf(Recursos.CODE_OK))){
                movementsManager.onSuccesResponse(response);
            } else {
                movementsManager.onFailed(StringUtils.getIntValue(dataResultAdq.getId()), Recursos.NO_ACTION, dataResultAdq.getMessage());
            }
        } else {
            movementsManager.onFailed(Recursos.INCORRECT_FORMAT, Recursos.NO_ACTION, App.getInstance().getString(R.string.error_respuesta));
        }
    }
    @Override
    public void onFailed(DataSourceResult error) {
        // TODO: 28/03/2017 Verificar los codigos de error y accion para este caso
        movementsManager.onFailed(0, Recursos.NO_ACTION, error.getData().toString());
    }
}
