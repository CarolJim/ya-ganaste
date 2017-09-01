package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarTitularCuentaResponse;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.EnviosInteractor;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IEnviosInteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.EnviosManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IEnviosPresenter;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_TITULAR_CUENTA;

/**
 * Created by Jordan on 20/04/2017.
 */

public class EnviosPresenter implements IEnviosPresenter, IEnviosInteractor.OnValidationFinishListener {

    IEnviosInteractor enviosInteractor;
    EnviosManager enviosManager;

    public EnviosPresenter(EnviosManager enviosManager) {
        this.enviosManager = enviosManager;
        enviosInteractor = new EnviosInteractor(this);
    }


    @Override
    public void validateForms(TransferType type, String number, int typeCard, String importe, String name,
                              String concept, String reference) {
        enviosInteractor.validateForms(type, number, typeCard, importe, name, concept, reference);
    }

    @Override
    public void getTitularName(String cuenta) {
        enviosManager.showLoader("");
        cuenta = cuenta.replaceAll(" ", "");
        enviosInteractor.getTitularName(cuenta);
    }

    @Override
    public void onTypeError() {
        enviosManager.onError(App.getContext().getString(R.string.txt_tipo_envio_error));
    }

    @Override
    public void onNumberEmpty() {
        enviosManager.onError(App.getContext().getString(R.string.txt_referencia_envio_empty));
    }


    @Override
    public void onNumberCABLEEmpty() {
        enviosManager.onError(App.getContext().getString(R.string.txt_referencia_envio_empty_clabe));
    }

    @Override
    public void onNumberCreditCardEmpty() {
        enviosManager.onError(App.getContext().getString(R.string.txt_referencia_envio_empty_creditc));
    }

    @Override
    public void onNumberCellPhoneEmpty() {
        enviosManager.onError(App.getContext().getString(R.string.txt_referencia_envio_empty_telefono));
    }

    @Override
    public void onSuccess(WebService ws, Object success) {
        enviosManager.hideLoader();
        if (ws == CONSULTAR_TITULAR_CUENTA) {
            enviosManager.setTitularName(((ConsultarTitularCuentaResponse) success).getData());
        }
    }

    @Override
    public void onError(WebService ws, Object error) {
        enviosManager.hideLoader();
        if(ws == CONSULTAR_TITULAR_CUENTA){
            enviosManager.onFailGetTitulaName();
        }
        enviosManager.showError(error.toString());
    }

    @Override
    public void onNumberError() {
        enviosManager.onError(App.getContext().getString(R.string.txt_referencia_envio_error));
    }

    @Override
    public void onNumberErrorCABLE() {
        enviosManager.onError(App.getContext().getString(R.string.txt_referencia_envio_error_clabe));
    }

    @Override
    public void onNumberErrorCreditCard() {
        enviosManager.onError(App.getContext().getString(R.string.txt_referencia_envio_error_creditc));
    }

    @Override
    public void onNumberErrorCellPhone() {
        enviosManager.onError(App.getContext().getString(R.string.txt_referencia_envio_error_telefono));
    }

    @Override
    public void onImporteEmpty() {
        enviosManager.onError(App.getContext().getString(R.string.txt_importe_empty));
    }

    @Override
    public void onImporteError() {
        enviosManager.onError(App.getContext().getString(R.string.txt_importe_error));
    }

    @Override
    public void onNameEmpty() {
        enviosManager.onError(App.getContext().getString(R.string.txt_name_empty));
    }

    @Override
    public void onNameError() {
        enviosManager.onError(App.getContext().getString(R.string.txt_name_error));
    }

    @Override
    public void onConceptEmpty() {
        enviosManager.onError(App.getContext().getString(R.string.txt_concept_empty));
    }

    @Override
    public void onConceptError() {
        enviosManager.onError(App.getContext().getString(R.string.txt_concept_error));
    }

    @Override
    public void onReferenceEmpty() {
        enviosManager.onError(App.getContext().getString(R.string.txt_referencia_number_empty));
    }

    @Override
    public void onReferenceShort() {
        enviosManager.onError(App.getContext().getString(R.string.txt_referencia_number_short));
    }

    @Override
    public void onReferenceInvalid() {
        enviosManager.onError(App.getContext().getString(R.string.txt_referencia_number_invalid));
    }

    @Override
    public void onReferenceError() {
        enviosManager.onError(App.getContext().getString(R.string.txt_referencia_number_error));
    }

    @Override
    public void onSuccess(Double monto) {
        enviosManager.onSuccess(monto);
    }

}
