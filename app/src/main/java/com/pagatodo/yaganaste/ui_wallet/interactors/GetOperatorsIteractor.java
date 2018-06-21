package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.GetoperadoresResponse;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IgetlistOperatorsiteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.Igetlistiteractor;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.GET_OPERADOR;
import static com.pagatodo.yaganaste.utils.Recursos.AGENTE_NUMBER;

public class GetOperatorsIteractor  implements Igetlistiteractor  , IRequestResult {

    IgetlistOperatorsiteractor igetlistOperatorspresenter;


    public GetOperatorsIteractor(IgetlistOperatorsiteractor igetlistOperatorspresenter) {
        this.igetlistOperatorspresenter = igetlistOperatorspresenter;
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        switch (dataSourceResult.getWebService()) {
            case GET_OPERADOR:
                GetoperadoresResponse data = (GetoperadoresResponse) dataSourceResult.getData();
                List<Operadores> list = data.getData();
                new DatabaseManager().insertOperadores(list,App.getInstance().getPrefs().loadData(AGENTE_NUMBER));
                igetlistOperatorspresenter.onSucces(dataSourceResult.getWebService(),null);
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {

    }

    @Override
    public void getlistoperadores() {
        try {
            ApiAdtvo.getoperadores(this);
        } catch (OfflineException e) {
            e.printStackTrace();
            igetlistOperatorspresenter.onError(GET_OPERADOR, App.getContext().getString(R.string.no_internet_access));
        }

    }
}
