package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.room_db.entities.CountryF;
import com.pagatodo.yaganaste.data.room_db.entities.Paises;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Guerra on 29/06/2017.
 */

public interface IRenapoView extends INavigationView {
    void onValidateUserDataSuccess();

    void onHomonimiaError();

    void onHomonimiaErrorSecond();


    void showDialogList(List<Paises> paises);

    void showDialogListCountryF(List<CountryF> paises);


}
