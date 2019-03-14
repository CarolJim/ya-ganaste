package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TypeRepaymentRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataResultAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneTiposReembolsoResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdq;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ITimeRepaymentIteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ITimeRepaymentPresenter;

import static com.pagatodo.yaganaste.utils.Recursos.CODE_ADQ_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_ERROR_SAME_REEMBOLSO_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.FIST_ADQ_REEMBOLSO;

/**
 * Created by Omar on 22/02/2018.
 */

public class TimeRepaymentIteractor implements IRequestResult, ITimeRepaymentIteractor {

    private ITimeRepaymentPresenter presenter;

    public TimeRepaymentIteractor(ITimeRepaymentPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getTypesRepayments() {
        try {
            ApiAdq.obtieneTiposReembolso(this);
        } catch (OfflineException e) {
            presenter.toPresenterErrorServer(App.getContext().getResources().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void updateTypeRepayment(int idTypeRepayment) {
        try {
            TypeRepaymentRequest request = new TypeRepaymentRequest(idTypeRepayment);
            ApiAdq.actualizaTipoReembolso(request, this);
        } catch (OfflineException e) {
            presenter.toPresenterErrorServer(App.getContext().getResources().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        switch (dataSourceResult.getWebService()) {
            case GET_TYPE_REPAYMENT:
                ObtieneTiposReembolsoResponse response = ((ObtieneTiposReembolsoResponse) dataSourceResult.getData());
                if (response.getResult().getId().equals(CODE_ADQ_OK)) {
                    presenter.onSuccessGetTypesRepayment(response);
                } else {
                    presenter.onFailedGetTypesRepayment(response.getResult().getMessage());
                }
                break;
            case UPDATE_TYPE_REPAYMENT:
                DataResultAdq result = ((DataResultAdq)dataSourceResult.getData());
                if(result.getId().equals(CODE_ADQ_OK)){
                    presenter.onSuccessUpdateTypeRepayment();
                    App.getInstance().getPrefs().saveDataBool(FIST_ADQ_REEMBOLSO, true);
                } else {

                    if(result.getId().equals(CODE_ERROR_SAME_REEMBOLSO_AGENTE)){
                        App.getInstance().getPrefs().saveDataBool(FIST_ADQ_REEMBOLSO, true);
                        presenter.onSuccessUpdateTypeRepayment();
                    }else
                    presenter.onFailedUpdateTypeRepayment(result.getMessage());


                }
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        switch (error.getWebService()) {
            case GET_TYPE_REPAYMENT:
                presenter.onFailedGetTypesRepayment(error.getData().toString());
                break;
            case UPDATE_TYPE_REPAYMENT:
                presenter.onFailedUpdateTypeRepayment(error.getData().toString());
                break;
        }
    }
}
