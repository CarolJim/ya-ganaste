package com.pagatodo.yaganaste.ui.preferuser.iteractors;

import android.util.Log;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesIteractor;
import com.pagatodo.yaganaste.ui.preferuser.presenters.ListaOpcionesPresenter;

/**
 * Created by Francisco Manzo on 23/05/2017.
 */

public class ListaOpcionesIteractor implements IListaOpcionesIteractor, IRequestResult {

    ListaOpcionesPresenter listaOpcionesPresenter;

    public ListaOpcionesIteractor(ListaOpcionesPresenter listaOpcionesPresenter) {
        this.listaOpcionesPresenter = listaOpcionesPresenter;
    }

    @Override
    public void sendIteractorActualizarAvatar(ActualizarAvatarRequest avatarRequest) {
        try {
            ApiAdtvo.actualizarAvatar(avatarRequest, this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        Log.d("ListaOpcionesIteractor", "DataSource Sucess " + dataSourceResult);
    }

    @Override
    public void onFailed(DataSourceResult error) {
        Log.d("ListaOpcionesIteractor", "DataSource Fail " + error);
    }
}
