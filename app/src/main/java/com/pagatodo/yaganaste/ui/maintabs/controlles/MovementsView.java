package com.pagatodo.yaganaste.ui.maintabs.controlles;

import java.util.List;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public interface MovementsView<T> {

    void loadMovementsResult(List<T> movements);
    void showError(String error);

}