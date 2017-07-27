package com.pagatodo.yaganaste.ui.cupo.managers;

import android.view.View;

/**
 * Created by Jordan on 26/07/2017.
 */

public interface CupoBaseManager extends View.OnClickListener {
    void showError(Object error);
    void showLoader();
    void hideLoader();
}
