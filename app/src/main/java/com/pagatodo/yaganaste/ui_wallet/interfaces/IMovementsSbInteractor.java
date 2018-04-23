package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ConsultarMovimientosRequest;
import com.pagatodo.yaganaste.interfaces.IRequestResult;

public interface IMovementsSbInteractor extends IRequestResult<DataSourceResult> {
    void getMovements();
}
