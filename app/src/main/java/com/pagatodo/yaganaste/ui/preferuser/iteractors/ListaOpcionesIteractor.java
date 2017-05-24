package com.pagatodo.yaganaste.ui.preferuser.iteractors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarAvatarResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesIteractor;
import com.pagatodo.yaganaste.ui.preferuser.presenters.ListaOpcionesPresenter;
import com.pagatodo.yaganaste.utils.BitmapDownload;
import com.pagatodo.yaganaste.utils.Recursos;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

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
    public void getImagenURLiteractor(String mUserImage) {
        BitmapDownload bitmapDownload = new BitmapDownload(mUserImage, this);
        bitmapDownload.execute();
    }

    @Override
    public void sendToIteractorBitmap(Bitmap bitmap) {
        listaOpcionesPresenter.sendImageBitmapPresenter(bitmap);
    }

    @Override
    public void onSuccess(DataSourceResult result) {
        ActualizarAvatarResponse response = (ActualizarAvatarResponse) result.getData();
        if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
            Log.d("ListaOpcionesIteractor", "DataSource Sucess " + response.getMensaje());

            updateAvatarResponse(response.getData().getImagenAvatarURL());
            // Linea para simular el error y comprobar el Duialog y el ShowProgress
            //listaOpcionesPresenter.sendErrorPresenter(response.getMensaje());
        } else {
            Log.d("ListaOpcionesIteractor", "DataSource Sucess with Error " + response.getMensaje());
            listaOpcionesPresenter.sendErrorPresenter(response.getMensaje());
        }
    }

    private void updateAvatarResponse(String imagenAvatarURL) {
        SingletonUser.getInstance().getDataUser().getUsuario().setImagenAvatarURL(imagenAvatarURL);
        listaOpcionesPresenter.sucessUpdateAvatar();
    }

    @Override
    public void onFailed(DataSourceResult error) {
        Log.d("ListaOpcionesIteractor", "DataSource Fail " + error);
       listaOpcionesPresenter.sendErrorPresenter(error.getData().toString());
    }
}
