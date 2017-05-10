package com.pagatodo.yaganaste.ui.maintabs.iteractors;

import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ConsultarMovimientosRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarMovimientosMesResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.MovementsIteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.MovementsManager;
import com.pagatodo.yaganaste.utils.Recursos;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public class AccountMovementsIteractorImp implements MovementsIteractor<ConsultarMovimientosRequest> {

    private MovementsManager<ConsultarMovimientosMesResponse> movementsManager;

    public AccountMovementsIteractorImp(MovementsManager<ConsultarMovimientosMesResponse> movementsManager) {
        this.movementsManager = movementsManager;
    }

    @Override
    public void getMovements(ConsultarMovimientosRequest request) {
        try {

            ApiAdtvo.consultarMovimientosMes(request, this);

        } catch (OfflineException e) {
            movementsManager.onFailed(Recursos.CODE_OFFLINE, Recursos.NO_ACTION, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        validateResponse((ConsultarMovimientosMesResponse)dataSourceResult.getData());
    }

    private void validateResponse(ConsultarMovimientosMesResponse response){
        if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
            movementsManager.onSuccesResponse(response);
        } else {
            //movementsManager.onSuccesResponse(response);
            movementsManager.onFailed(response.getCodigoRespuesta(), response.getAccion(), response.getMensaje());
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        // TODO: 28/03/2017 Verificar los codigos de error y accion para este caso
        movementsManager.onFailed(0, Recursos.NO_ACTION, error.getData().toString());
    }


}
