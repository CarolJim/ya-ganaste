package com.pagatodo.yaganaste.modules.payments.paymentContent;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarFavoritosResponse;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;

import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

public class PaymentContentInteractor implements PaymentContentContracts.Interactor {

    private PaymentContentContracts.Listener listener;

    public PaymentContentInteractor(PaymentContentContracts.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void getFavorites() {
        try {
            ApiAdtvo.consultarFavoritos(this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(DataSourceResult result) {
        switch (result.getWebService()) {
            case OBTENER_FAVORITOS:
                //mPresenter.onSuccessWSFavorites(dataSourceResult, typeDataFav);
                ConsultarFavoritosResponse response = (ConsultarFavoritosResponse) result.getData();
                if (response.getCodigoRespuesta() == CODE_OK) {
                    try {
                        if (response.getData().size() > 0) {
                            new DatabaseManager().insertListFavorites(response.getData());
                        }
                        this.listener.onFavoritesSuccess();
                    } catch (Exception e) {
                        e.printStackTrace();
                        this.listener.onError("No se encontraron favoritos");
                    }
                } else {
                    this.listener.onError(response.getMensaje());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        this.listener.onError("No se encontraron favoritos");
    }
}
