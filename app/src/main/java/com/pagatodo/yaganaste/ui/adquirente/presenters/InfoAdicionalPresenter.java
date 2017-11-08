package com.pagatodo.yaganaste.ui.adquirente.presenters;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CrearAgenteResponse;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui.adquirente.interactores.InfoAdicionalInteractor;
import com.pagatodo.yaganaste.ui.adquirente.interactores.interfaces.IinfoAdicionalInteractor;
import com.pagatodo.yaganaste.ui.adquirente.managers.InformationAdicionalManager;
import com.pagatodo.yaganaste.ui.adquirente.presenters.interfaces.IinfoAdicionalPresenter;

import java.util.ArrayList;

import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by Jordan on 04/08/2017.
 */

public class InfoAdicionalPresenter implements IinfoAdicionalPresenter {

    InformationAdicionalManager informationAdicionalManager;
    IinfoAdicionalInteractor infoAdicionalInteractor;

    public InfoAdicionalPresenter(InformationAdicionalManager manager) {
        informationAdicionalManager = manager;
        infoAdicionalInteractor = new InfoAdicionalInteractor(this);
    }

    @Override
    public void getPaisesList() {
        ArrayList<Countries> arrayList = infoAdicionalInteractor.getPaisesList();
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        informationAdicionalManager.showDialogList(arrayList);
    }

    @Override
    public void createUsuarioAdquirente() {
        informationAdicionalManager.showLoader("");
        infoAdicionalInteractor.registrarAdquirente();
    }

    @Override
    public void onSuccessCreateUsuarioAdquirente(Object success) {
        informationAdicionalManager.hideLoader();
        CrearAgenteResponse data = (CrearAgenteResponse) ((DataSourceResult) success).getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            informationAdicionalManager.onSuccessCreateAgente();
        } else {
            informationAdicionalManager.onSuccessCreateAgente();
            //informationAdicionalManager.onErrorCreateAgente(new ErrorObject(data.getMensaje(), ((DataSourceResult) success).getWebService()));
        }
    }

    @Override
    public void onWSError(WebService ws, Object error) {
        informationAdicionalManager.hideLoader();
        informationAdicionalManager.showError(new ErrorObject(error.toString(), ws));
    }

}
