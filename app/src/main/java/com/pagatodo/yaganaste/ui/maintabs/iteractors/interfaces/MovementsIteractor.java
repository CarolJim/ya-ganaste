package com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ReembolsoDataRequest;
import com.pagatodo.yaganaste.data.room_db.entities.Agentes;
import com.pagatodo.yaganaste.interfaces.IRequestResult;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public interface MovementsIteractor<T> extends IRequestResult<DataSourceResult> {

    void getMovements(T request);

    void getBalance();

    void getDatosCupo();

    void sendRemmbolso(ReembolsoDataRequest request);
}
