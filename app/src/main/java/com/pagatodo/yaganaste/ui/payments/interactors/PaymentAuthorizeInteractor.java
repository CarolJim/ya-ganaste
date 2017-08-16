package com.pagatodo.yaganaste.ui.payments.interactors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarFormatoContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarFormatoContraseniaResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.ui.payments.interactors.interfaces.IPaymentAuthorizeInteractor;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentAuthorizePresenter;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_FORMATO_CONTRASENIA;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by Jordan on 14/08/2017.
 */

public class PaymentAuthorizeInteractor implements IPaymentAuthorizeInteractor {
    private IPaymentAuthorizePresenter presenter;

    public PaymentAuthorizeInteractor(IPaymentAuthorizePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void validatePasswordFormat(String password) {
        ValidarFormatoContraseniaRequest request = new ValidarFormatoContraseniaRequest(password);
        try {
            ApiAdtvo.validarFormatoContrasenia(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            presenter.onError(VALIDAR_FORMATO_CONTRASENIA, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onSuccess(DataSourceResult result) {
        ValidarFormatoContraseniaResponse data = (ValidarFormatoContraseniaResponse) result.getData();
        if (data.getAccion() == CODE_OK) {
            presenter.onSuccess(result.getWebService(), data.getCodigoRespuesta());
        } else {
            presenter.onError(result.getWebService(), data.getMensaje());//Retornamos mensaje de error.
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        presenter.onError(error.getWebService(), error.getData().toString());
    }

}
