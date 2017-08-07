package com.pagatodo.yaganaste.ui.adquirente.presenters;

import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.ui.adquirente.interactores.InfoAdicionalInteractor;
import com.pagatodo.yaganaste.ui.adquirente.interactores.interfaces.IinfoAdicionalInteractor;
import com.pagatodo.yaganaste.ui.adquirente.managers.InformationAdicionalManager;
import com.pagatodo.yaganaste.ui.adquirente.presenters.interfaces.IinfoAdicionalPresenter;

import java.util.ArrayList;

/**
 * Created by Jordan on 04/08/2017.
 */

public class InfoAdicionalPresenter implements IinfoAdicionalPresenter {

    InformationAdicionalManager informationAdicionalManager;
    IinfoAdicionalInteractor infoAdicionalInteractor;

    public InfoAdicionalPresenter(InformationAdicionalManager manager) {
        informationAdicionalManager = manager;
        infoAdicionalInteractor = new InfoAdicionalInteractor();
    }

    @Override
    public void getPaisesList() {
        ArrayList<Countries> arrayList = infoAdicionalInteractor.getPaisesList();
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }

        informationAdicionalManager.showDialogList(arrayList);

    }
}
