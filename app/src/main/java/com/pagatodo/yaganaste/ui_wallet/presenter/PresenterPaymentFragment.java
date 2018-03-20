package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.ServiciosInteractor;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IServiciosInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IPaymentFromFragment;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IPresenterPaymentFragment;

import java.util.concurrent.ExecutionException;

/**
 * Created by FranciscoManzo on 10/01/2018.
 */

public class PresenterPaymentFragment implements IPresenterPaymentFragment, IServiciosInteractor.OnValidationFinishListener {
    IPaymentFromFragment iView;
    IServiciosInteractor serviciosInteractor;

    public PresenterPaymentFragment() {
    }

    public PresenterPaymentFragment(IPaymentFromFragment iView) {
        this.iView = iView;
        serviciosInteractor = new ServiciosInteractor();
    }

    @Override
    public Comercio getComercioById(int idComercio) {
        try {
            return new DatabaseManager().getComercioById(idComercio);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Comercio();
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
