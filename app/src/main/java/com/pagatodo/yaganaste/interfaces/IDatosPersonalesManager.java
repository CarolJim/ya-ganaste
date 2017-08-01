package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.db.Countries;

/**
 * Created by Jordan on 31/07/2017.
 */

public interface IDatosPersonalesManager extends ValidationForms {
    void onCountrySelected(Countries item);
}
