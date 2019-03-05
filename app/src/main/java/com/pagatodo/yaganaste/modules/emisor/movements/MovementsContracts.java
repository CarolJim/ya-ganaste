package com.pagatodo.yaganaste.modules.emisor.movements;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.modules.management.apis.ListenerLauncher;

import java.util.List;

public class MovementsContracts {

    interface Listener extends ListenerLauncher {
        void onSuccessMovements(List<MovimientosResponse> list);
    }

    interface Interactor extends IRequestResult {
        void getMovements(String month, String anio);
    }
}
