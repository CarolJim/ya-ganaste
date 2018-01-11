package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.ui_wallet.fragments.PaymentFormFragment;

/**
 * Created by FranciscoManzo on 10/01/2018.
 */

public class PresenterPaymentFragment implements IPresenterPaymentFragment{
    IPaymentFromFragment iView;
    private CatalogsDbApi catalogsDbApi;
    public PresenterPaymentFragment(IPaymentFromFragment iView) {
        this.iView = iView;
        catalogsDbApi = new CatalogsDbApi(App.getContext());
    }

    @Override
    public ComercioResponse getComercioById(long idComercio) {
        return catalogsDbApi.getComercioById(idComercio);
    }
}
