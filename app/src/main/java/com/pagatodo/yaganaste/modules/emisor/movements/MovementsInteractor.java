package com.pagatodo.yaganaste.modules.emisor.movements;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ConsultarMovimientosRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarMovimientosMesResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarSaldoResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.utils.Recursos;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MovementsInteractor implements MovementsContracts.Interactor {

    private MovementsContracts.Listener listener;

    public MovementsInteractor(MovementsContracts.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void getMovements(String month, String anio) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        ConsultarMovimientosRequest request = new ConsultarMovimientosRequest();
        request.setAnio(anio);
        //request.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
        request.setMes(month);
        //request.setMes(String.valueOf(calendar.get(Calendar.MONTH)));
        request.setDireccion("");
        request.setIdMovimiento("");
        try {
            ApiAdtvo.consultarMovimientosMes(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            listener.onError(App.getInstance().getString(R.string.no_internet_access));
            //movementsManager.onFailed(Recursos.CODE_OFFLINE, Recursos.NO_ACTION, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        switch (dataSourceResult.getWebService()) {

            case CONSULTAR_MOVIMIENTOS_MES:
                ConsultarMovimientosMesResponse response = (ConsultarMovimientosMesResponse) dataSourceResult.getData();

                if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                    listener.onSuccessMovements(response.getData());//(response);
                } else {
                    listener.onError(response.getMensaje());
                }
                break;

            case CONSULTAR_SALDO:
                //validateBalanceResponse((ConsultarSaldoResponse) dataSourceResult.getData());
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {

    }
}
