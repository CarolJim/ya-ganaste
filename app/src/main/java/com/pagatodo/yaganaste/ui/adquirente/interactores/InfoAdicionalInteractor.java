package com.pagatodo.yaganaste.ui.adquirente.interactores;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.ui.adquirente.interactores.interfaces.IinfoAdicionalInteractor;

import java.util.ArrayList;

/**
 * Created by Jordan on 04/08/2017.
 */

public class InfoAdicionalInteractor implements IinfoAdicionalInteractor {

    public InfoAdicionalInteractor(){
    }

    @Override
    public ArrayList<Countries> getPaisesList() {
        CatalogsDbApi api = new CatalogsDbApi(App.getContext());
        return api.getPaisesList();
    }
}
