package com.pagatodo.yaganaste.ui.adquirente.managers;

import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.interfaces.enums.Error;

import java.util.ArrayList;

/**
 * Created by Jordan on 04/08/2017.
 */

public interface InformationAdicionalManager extends ValidationForms, INavigationView {
    void onIsMexaYesCheck();

    void onIsMexaNoCheck();

    void onHasFamiliarYesCheck();

    void onHasFamiliarNoCheck();

    void showDialogList(ArrayList<Countries> paises);

    void onSuccessCreateAgente();

    void onErrorCreateAgente(ErrorObject error);
}
