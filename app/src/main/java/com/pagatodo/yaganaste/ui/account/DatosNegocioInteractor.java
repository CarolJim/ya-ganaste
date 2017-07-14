package com.pagatodo.yaganaste.ui.account;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.SubGiro;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerSubgirosResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IDatosNegocioIteractor;
import com.pagatodo.yaganaste.interfaces.INegocioManager;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_SUBGIROS;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * @author jguerras
 */

public class DatosNegocioInteractor implements IDatosNegocioIteractor, IRequestResult {

    private INegocioManager iNegocioManager;

    public DatosNegocioInteractor(INegocioManager iNegocioManager) {
        this.iNegocioManager = iNegocioManager;
    }

    @Override
    public void getGiros() {
        try {
            ApiAdtvo.obtenerSubgiros(this);
        } catch (OfflineException e) {
            iNegocioManager.onError(OBTENER_SUBGIROS, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        switch (dataSourceResult.getWebService()) {
            case OBTENER_SUBGIROS:
                processSubGiros(dataSourceResult);
                break;

            default:
                break;
        }
    }

    private void processSubGiros(DataSourceResult result) {
        ObtenerSubgirosResponse data = (ObtenerSubgirosResponse) result.getData();

        if (data.getCodigoRespuesta() == CODE_OK) {
            List<SubGiro> subGiros = data.getData();
            if (subGiros != null) {
                iNegocioManager.onSucces(result.getWebService(), subGiros);
            } else {
                iNegocioManager.onError(result.getWebService(), "Ocurrio un Error al Consultar los SubGiros");//Retornamos mensaje de error.
            }
        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            iNegocioManager.onError(result.getWebService(), data.getMensaje());//Retornamos mensaje de error.
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {

        /**TODO Casos de Servicio fallido*/
        iNegocioManager.onError(error.getWebService(), error.getData().toString());
    }

}