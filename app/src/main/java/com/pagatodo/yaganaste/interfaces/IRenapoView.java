package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.db.Countries;

import java.util.ArrayList;

/**
 * @author Juan Guerra on 29/06/2017.
 */

public interface IRenapoView extends INavigationView {
    void onValidateUserDataSuccess();

    void showDialogList(ArrayList<Countries> paises);
}
