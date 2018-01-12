package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.ServiciosInteractor;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IServiciosInteractor;

/**
 * Created by FranciscoManzo on 10/01/2018.
 */

public class PresenterPaymentFragment implements IPresenterPaymentFragment, IServiciosInteractor.OnValidationFinishListener {
    IPaymentFromFragment iView;
    IServiciosInteractor serviciosInteractor;

    private CatalogsDbApi catalogsDbApi;
    public PresenterPaymentFragment(IPaymentFromFragment iView) {
        this.iView = iView;
        catalogsDbApi = new CatalogsDbApi(App.getContext());
        serviciosInteractor = new ServiciosInteractor();
    }

    @Override
    public ComercioResponse getComercioById(long idComercio) {
        return catalogsDbApi.getComercioById(idComercio);
    }

    @Override
    public void validateFieldsCarrier(String referencia, String serviceImport, String concepto, int longitudReferencia) {
        serviciosInteractor.validateForms(referencia, serviceImport, concepto, longitudReferencia, this);
    }

    @Override
    public void onReferenciaError() {
        iView.onErrorValidateService(App.getContext().getResources().getString(R.string.txt_referencia_errornuevo));
    }

    @Override
    public void onReferenciaEmpty() {
        iView.onErrorValidateService(App.getContext().getResources().getString(R.string.txt_referencia_empty));
    }

    @Override
    public void onImporteError() {
        iView.onErrorValidateService(App.getContext().getResources().getString(R.string.txt_importe_error));
    }

    @Override
    public void onImporteEmpty() {
        iView.onErrorValidateService(App.getContext().getResources().getString(R.string.txt_importe_empty));
    }

    @Override
    public void onConceptEmpty() {
        iView.onErrorValidateService(App.getContext().getResources().getString(R.string.txt_concept_empty));
    }

    @Override
    public void onSuccess(Double i) {
        iView.onSuccessValidateService(i);
    }
}
