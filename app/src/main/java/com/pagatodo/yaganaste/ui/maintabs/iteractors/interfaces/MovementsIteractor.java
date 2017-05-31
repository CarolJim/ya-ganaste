package com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.net.IRequestResult;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public interface MovementsIteractor<T> extends IRequestResult<DataSourceResult> {

    void getMovements(T request);
    void getBalance();
}
