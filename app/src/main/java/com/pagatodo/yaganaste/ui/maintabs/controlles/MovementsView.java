package com.pagatodo.yaganaste.ui.maintabs.controlles;

import com.pagatodo.yaganaste.interfaces.IProgressView;

import java.util.List;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public interface MovementsView<T> extends IProgressView<String>{

    void loadMovementsResult(List<T> movements);

}