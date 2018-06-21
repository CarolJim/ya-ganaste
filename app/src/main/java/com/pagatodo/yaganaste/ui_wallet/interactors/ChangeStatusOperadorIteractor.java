package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ChangeStatusOperadorRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ChangeStatusOperadorResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IChangeStatusOperador;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.ApiStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IChangeIteractorStatusOperador;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CHANGE_STATUS_OPERADOR;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.LOGINSTARBUCKS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_DATOS_PERSONA;

public class ChangeStatusOperadorIteractor implements IChangeIteractorStatusOperador , IRequestResult {

    IChangeStatusOperador iChangeStatusOperador;

    public ChangeStatusOperadorIteractor(IChangeStatusOperador iChangeStatusOperador) {
        this.iChangeStatusOperador = iChangeStatusOperador;
    }
    @Override
    public void changeStatus(ChangeStatusOperadorRequest request) {
        try {
            ApiAdtvo.changestatusoperador(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            iChangeStatusOperador.onError(CHANGE_STATUS_OPERADOR, App.getContext().getString(R.string.no_internet_access));
        }
    }
    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        switch (dataSourceResult.getWebService()) {
            case CHANGE_STATUS_OPERADOR:
                changestatus(dataSourceResult);
                break;
        }
    }
    private void changestatus(DataSourceResult dataSourceResult) {
        GenericResponse response =(GenericResponse)dataSourceResult.getData();

        if (response.getMensaje()=="El operador a sido bloqueado"){
            iChangeStatusOperador.onSucces(CHANGE_STATUS_OPERADOR, response.getMensaje().toString());
        }
        if (response.getMensaje()=="El operador a sido desbloqueado"){
            iChangeStatusOperador.onSucces(CHANGE_STATUS_OPERADOR, response.getMensaje().toString());
        }else {
            iChangeStatusOperador.onError(CHANGE_STATUS_OPERADOR, response.getMensaje().toString());
        }
    }
    @Override
    public void onFailed(DataSourceResult error) {
        if (error != null && error.getWebService() == CHANGE_STATUS_OPERADOR) {
            iChangeStatusOperador.onError(CHANGE_STATUS_OPERADOR, error.getData().toString());
        }
    }
}
