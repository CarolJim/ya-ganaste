package com.pagatodo.yaganaste.ui.adquirente.interactores;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearAgenteRequest;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.ui.adquirente.interactores.interfaces.IinfoAdicionalInteractor;
import com.pagatodo.yaganaste.ui.adquirente.presenters.interfaces.IinfoAdicionalPresenter;

import java.util.ArrayList;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;

/**
 * Created by Jordan on 04/08/2017.
 */

public class InfoAdicionalInteractor implements IinfoAdicionalInteractor {

    IinfoAdicionalPresenter infoAdicionalPresenter;

    public InfoAdicionalInteractor(IinfoAdicionalPresenter adicionalPresenter) {
        infoAdicionalPresenter = adicionalPresenter;
    }

    @Override
    public ArrayList<Countries> getPaisesList() {
        CatalogsDbApi api = new CatalogsDbApi(App.getContext());
        return api.getPaisesList();
    }

    @Override
    public void registrarAdquirente() {
        CrearAgenteRequest request = new CrearAgenteRequest(RegisterAgent.getInstance(),
                SingletonUser.getInstance().getDataUser().getUsuario().getTipoAgente());
        try {
            ApiAdtvo.crearAgente(request, this);
        } catch (OfflineException e) {
            infoAdicionalPresenter.onWSError(CREAR_AGENTE, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onSuccess(DataSourceResult result) {
        if (result.getWebService() == CREAR_AGENTE) {
            infoAdicionalPresenter.onSuccessCreateUsuarioAdquirente(result);
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        infoAdicionalPresenter.onWSError(error.getWebService(), error);
    }
}
