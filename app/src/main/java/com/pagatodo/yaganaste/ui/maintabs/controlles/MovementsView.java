package com.pagatodo.yaganaste.ui.maintabs.controlles;

import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.interfaces.IProgressView;

import java.util.List;

public interface MovementsView<T, G extends IEnumTab> extends IProgressView<String>, TabsView<G> {

    void loadMovementsResult(List<T> movements);

    void updateBalance();

    void loadReembolso();
}