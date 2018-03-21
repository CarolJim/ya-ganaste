package com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.interfaces.IEnumTab;

public interface MovementsPresenter<T extends IEnumTab> extends TabPresenter {

    void getRemoteMovementsData(T data);

    void getRemoteMovementsData(T data, SwipyRefreshLayoutDirection direction, String lastId);

    void updateBalance();

    void sendReembolso(DataMovimientoAdq dataMovimientoAdq);
}
