package com.pagatodo.yaganaste.ui.preferuser.presenters;

import android.graphics.Bitmap;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DesasociarDispositivoRequest;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferDesasociarView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserIteractor;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.ui.preferuser.iteractors.PreferUserIteractor;

/**
 * Created by Francisco Manzo on 08/06/2017.
 * Presenter general para la actividad PreferUserActivity
 * Para gestionar todos los eventos de los fragmentos
 */

public class PreferUserPresenter implements IPreferUserPresenter {

    PreferUserActivity mView;
    IPreferUserIteractor iPreferUserIteractor;
    IPreferDesasociarView iPreferDesasociarView;
    IListaOpcionesView iListaOpcionesView;

    public PreferUserPresenter(PreferUserActivity mView) {
        this.mView = mView;

        iPreferUserIteractor = new PreferUserIteractor(this);
    }

    /**
     * Se encarga de hacer SET del View que interactura con el Presenter. Esto funciona porque recibimos
     * una Interfase IPreferUserGeneric, y a su vez si es instancia de alguna de sus herederas haemos
     * SET de la View correspondiente. Este proceso es un Cast del tipo Downcasting
     * @param iPreferUserGeneric
     */
    public void setIView(IPreferUserGeneric iPreferUserGeneric) {
        // Set de  instancia de IListaOpcionesView
        if (iPreferUserGeneric instanceof IListaOpcionesView) {
            iListaOpcionesView = (IListaOpcionesView) iPreferUserGeneric;
        }

        // Set de instancia de IPreferDesasociarView
        if (iPreferUserGeneric instanceof IPreferDesasociarView) {
            this.iPreferDesasociarView = (IPreferDesasociarView) iPreferUserGeneric;
        }
    }

    /**
     * Se encarga de enviar la peticion al Iteractor para Desasociar. Msotramos el Loader mientras
     * tanto. Arma la peticion del Request para enviaro an Iteractor
     */
    @Override
    public void DesasociarToPresenter() {
        mView.showLoader("Procesando. Por favor, espere . . .");
        DesasociarDispositivoRequest desasociarRequest = new DesasociarDispositivoRequest();
        iPreferUserIteractor.desasociarToIteracto(desasociarRequest);
    }

    /**
     * Exito en la conexion al servidor y procedimiento de Desasociar. Cerrarmos el Loader y enviamos
     * el control a la vista
     *
     * @param mensaje
     */
    @Override
    public void sendSuccessPresenter(String mensaje) {
        mView.hideLoader();
        iPreferDesasociarView.sendSuccessView(mensaje);
    }

    /**
     * Exito en la conexion al servidor pero error en codigo de respuesta de procedimiento de
     * Desasociar. Cerrarmos el Loader y enviamos
     * el control a la vista
     *
     * @param mensaje
     */
    @Override
    public void sendErrorPresenter(String mensaje) {
        mView.hideLoader();
        iPreferDesasociarView.sendErrorView(mensaje);
    }

    /**
     * Error en la conexion al servidor y procedimiento de Desasociar. Cerrarmos el Loader y enviamos
     * el control a la vista
     *
     * @param error
     */
    @Override
    public void sendErrorServerPresenter(String error) {
        mView.hideLoader();
        iPreferDesasociarView.sendErrorServerView(error);
    }

    @Override
    public void getImagenURLPresenter(String mUserImage) {
        iPreferUserIteractor.getImagenURLiteractor(mUserImage);
    }

    @Override
    public void sendImageBitmapPresenter(Bitmap bitmap) {
        iListaOpcionesView.sendImageBitmapView(bitmap);
    }
}
