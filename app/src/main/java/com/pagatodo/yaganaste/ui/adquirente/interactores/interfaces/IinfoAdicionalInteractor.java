package com.pagatodo.yaganaste.ui.adquirente.interactores.interfaces;

import com.pagatodo.yaganaste.data.room_db.entities.Paises;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.interfaces.enums.SpinnerPLD;

import java.util.List;

/**
 * Created by Jordan on 04/08/2017.
 */

public interface IinfoAdicionalInteractor extends IRequestResult {
    List<Paises> getPaisesList();

    void registrarAdquirente();
    void setSpinner(SpinnerPLD sp);
    void setPaises();


}
