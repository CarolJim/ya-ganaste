package com.pagatodo.yaganaste.ui.adquirente.interactores.interfaces;

import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.interfaces.enums.SpinnerPLD;
import com.pagatodo.yaganaste.net.IRequestResult;

import java.util.ArrayList;

/**
 * Created by Jordan on 04/08/2017.
 */

public interface IinfoAdicionalInteractor extends IRequestResult {
    ArrayList<Countries> getPaisesList();

    void registrarAdquirente();
    void setSpinner(SpinnerPLD sp);



}
