package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.Ventas;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.GetResumenDiaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.GetoperadoresResponse;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.net.ApiAdq;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IVentasDiarias;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.GET_OPERADOR;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.GET_RESUMENDIA;
import static com.pagatodo.yaganaste.utils.Recursos.AGENTE_NUMBER;

public class VentasDiariasIteractor implements IVentasDiariasIteractor, IRequestResult {

    IVentasDiarias iVentasDiariaspresenter;

    public VentasDiariasIteractor(IVentasDiarias iVentasDiariaspresenter) {
        this.iVentasDiariaspresenter = iVentasDiariaspresenter;
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        switch (dataSourceResult.getWebService()) {
            case GET_RESUMENDIA:
                GetResumenDiaResponse data = (GetResumenDiaResponse) dataSourceResult.getData();
                if (!data.getResult().getId().equals("0")) {
                    Ventas ventas = Ventas.getInstance();
                    ventas.setMontoventas(data.getSaldo());
                    ventas.setTicketp(data.getTicketPromedio());
                    ventas.setCobrosr(data.getNumeroCobros());
                    iVentasDiariaspresenter.onSucces(GET_RESUMENDIA, "Succes");
                } else {
                    iVentasDiariaspresenter.onError(GET_RESUMENDIA, data.getResult().getMessage());
                }
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        switch (error.getWebService()) {
            case GET_RESUMENDIA:
                break;
        }
    }


    @Override
    public void obtenerSaldo() {

    }

    @Override
    public void obtenerResumendia(String fecha) {
        try {
            ApiAdq.getresumendia(fecha, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            iVentasDiariaspresenter.onError(GET_RESUMENDIA, App.getContext().getString(R.string.no_internet_access));
        }
    }
}

interface IVentasDiariasIteractor {

    void obtenerSaldo();

    void obtenerResumendia(String fecha);

}