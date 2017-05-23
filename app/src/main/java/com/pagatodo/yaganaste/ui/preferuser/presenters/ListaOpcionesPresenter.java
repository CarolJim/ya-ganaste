package com.pagatodo.yaganaste.ui.preferuser.presenters;

import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesIteractor;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.ui.preferuser.iteractors.ListaOpcionesIteractor;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.pagatodo.yaganaste.utils.customviews.UploadDocumentView;

/**
 * Created by Francisco Manzo on 22/05/2017.
 */

public class ListaOpcionesPresenter implements IListaOpcionesPresenter {

    IListaOpcionesView mView;
    IListaOpcionesIteractor iListaOpcionesIteractor;

    public ListaOpcionesPresenter(IListaOpcionesView mView) {
        this.mView = mView;
        iListaOpcionesIteractor = new ListaOpcionesIteractor(this);
    }

    @Override
    public void openMenuPhoto(int i) {
        // Toast.makeText(App.getContext(), "CLick openMenuPhoto", Toast.LENGTH_SHORT).show();
        CameraManager.getInstance().createPhoto(1);
    }

    @Override
    public void sendPresenterActualizarAvatar(ActualizarAvatarRequest avatarRequest) {
        iListaOpcionesIteractor.sendIteractorActualizarAvatar(avatarRequest);
    }

    @Override
    public void sucessUpdateAvatar() {
        mView.sucessUpdateAvatar();
    }

    @Override
    public void sendErrorPresenter(String mensaje) {
        mView.sendErrorView(mensaje);
    }
}
