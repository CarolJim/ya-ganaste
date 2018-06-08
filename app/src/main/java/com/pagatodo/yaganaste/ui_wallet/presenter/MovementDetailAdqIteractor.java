package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.DetalleMovimientoRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataResultAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ResumenMovimientosAdqResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.net.ApiAdq;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringUtils;

public class MovementDetailAdqIteractor implements IRequestResult {

    private MovementDetailAdqPresenter presenter;

    public MovementDetailAdqIteractor(MovementDetailAdqPresenter presenter) {
        this.presenter = presenter;
    }

    public void getDetailFromService(DataMovimientoAdq data) {
        try {
            DetalleMovimientoRequest request = new DetalleMovimientoRequest();
            request.setIdTransaction(data.getIdTransaction());
            request.setNoSecUnicoPT(data.getNoSecUnicoPT());
            ApiAdq.obtenerDetalleMovimiento(request, this);
        } catch (OfflineException e) {
            presenter.onErrorTicket(App.getContext().getString(R.string.load_detail_movimientos_error));
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        DataResultAdq dataResultAdq = ((ResumenMovimientosAdqResponse) dataSourceResult.getData()).getResult();
        if (dataResultAdq != null) {
            if (dataResultAdq.getId().equals(String.valueOf(Recursos.INVALID_TOKEN))) {
                presenter.onErrorTicket(dataResultAdq.getMessage());
            } else if (dataResultAdq.getId().equals(Recursos.CODE_ADQ_OK)) {
                DataMovimientoAdq movimiento = ((ResumenMovimientosAdqResponse) dataSourceResult.getData()).getMovimientos().get(0);
                presenter.completeDetailMov(movimiento);
            } else {
                presenter.onErrorTicket(dataResultAdq.getMessage());
            }
        } else {
            presenter.onErrorTicket(App.getInstance().getString(R.string.error_respuesta));
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        presenter.onErrorTicket(error.getData().toString());
    }
}
