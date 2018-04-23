package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ConsultarMovimientosRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultaMovimientosSBResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarMovimientosMesResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IMovementsSbInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IMovementsView;
import com.pagatodo.yaganaste.utils.Recursos;

public class MovementsInteractorImpl implements IMovementsSbInteractor {

    private IMovementsView iMovementsView;

    public MovementsInteractorImpl(IMovementsView iMovementsView) {
        this.iMovementsView = iMovementsView;

    }

    @Override
    public void getMovements() {
        try {
            iMovementsView.showProgress();
            ApiAdtvo.consultarMovimientosSb(this);
        } catch (OfflineException e) {
            e.printStackTrace();
            iMovementsView.onFailed(App.getInstance().getString(R.string.no_internet_access));
            iMovementsView.hideProgress();
        }
    }

    @Override
    public void onSuccess(DataSourceResult result) {
        switch (result.getWebService()) {
            case CONSULTAR_MOV_SB:
                validateResponse((ConsultaMovimientosSBResponse) result.getData());
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        iMovementsView.hideProgress();
        if (error.getWebService() == WebService.CONSULTAR_MOVIMIENTOS_MES) {
            iMovementsView.onFailed(error.getData().toString());
        }
    }

    public interface MovementsNotification {
        void onSuccess(ConsultaMovimientosSBResponse response);
        void onFailed(String error);
    }

    private void validateResponse(ConsultaMovimientosSBResponse response) {
        if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
            iMovementsView.loadMovementsResult(response.getMovimientos());
        } else {
            iMovementsView.onFailed(response.getMensaje());
        }
        iMovementsView.hideProgress();
    }
}
