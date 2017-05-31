package com.pagatodo.yaganaste.ui.maintabs.controlles;

import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.interfaces.IProgressView;

import java.util.List;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public interface MovementsView<T, G extends IEnumTab> extends IProgressView<String>, TabsView<G>{

    void loadMovementsResult(List<T> movements);
    void updateBalance();

}