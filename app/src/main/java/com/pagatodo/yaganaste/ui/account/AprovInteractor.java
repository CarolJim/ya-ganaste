package com.pagatodo.yaganaste.ui.account;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActivacionAprovSofttokenRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.VerificarActivacionAprovSofttokenRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActivacionAprovSofttokenResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.VerificarActivacionAprovSofttokenResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAprovIteractor;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTIVACION_APROV_SOFTTOKEN;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VERIFICAR_ACTIVACION_APROV_SOFTTOKEN;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by flima on 22/03/2017.
 */

public class AprovInteractor implements IAprovIteractor,IRequestResult {

    private String TAG = AprovInteractor.class.getName();
    private IAccountManager accountManager;

    public AprovInteractor(IAccountManager accountManager){
        this.accountManager = accountManager;
    }


    @Override
    public void verifyActivationAprov(VerificarActivacionAprovSofttokenRequest request) {
        try {
            ApiAdtvo.verificarActivacionAprovSofttoken(request,this);
        } catch (OfflineException e) {
            accountManager.onError(VERIFICAR_ACTIVACION_APROV_SOFTTOKEN, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void activationAprov(ActivacionAprovSofttokenRequest request) {
        try {
            ApiAdtvo.activacionAprovSofttoken(request,this);
        } catch (OfflineException e) {
            accountManager.onError(ACTIVACION_APROV_SOFTTOKEN, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

        switch (dataSourceResult.getWebService()) {
            case VERIFICAR_ACTIVACION_APROV_SOFTTOKEN:
                processVerifyActivationAprov(dataSourceResult);
                break;

            case ACTIVACION_APROV_SOFTTOKEN:
                processActivationAprov(dataSourceResult);
                break;

            default:
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        accountManager.onError(error.getWebService(),error.getData().toString());
    }


    private void processVerifyActivationAprov(DataSourceResult response) {
        VerificarActivacionAprovSofttokenResponse data = (VerificarActivacionAprovSofttokenResponse) response.getData();
        if(data.getCodigoRespuesta() == CODE_OK) {
            accountManager.onSucces(response.getWebService(), "");
        }else{
            accountManager.onError(response.getWebService(),data.getMensaje());//Retornamos mensaje de error.
        }
    }

    private void processActivationAprov(DataSourceResult response) {
        ActivacionAprovSofttokenResponse data = (ActivacionAprovSofttokenResponse) response.getData();
        if(data.getCodigoRespuesta() == CODE_OK) {
            accountManager.onSucces(response.getWebService(), "Aprovisionamiento Exitoso");
        }else{
            accountManager.onError(response.getWebService(),data.getMensaje());//Retornamos mensaje de error.
        }
    }

}