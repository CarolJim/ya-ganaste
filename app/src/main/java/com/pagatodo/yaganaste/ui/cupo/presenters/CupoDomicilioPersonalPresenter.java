package com.pagatodo.yaganaste.ui.cupo.presenters;

import android.content.Context;
import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.data.model.webservice.response.cupo.DataEstadoSolicitud;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IDocumentsPresenter;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui._manager.GenericPresenterMain;
import com.pagatodo.yaganaste.ui.cupo.interactores.CupoInteractor;
import com.pagatodo.yaganaste.ui.cupo.interactores.interfaces.ICupoInteractor;
import com.pagatodo.yaganaste.ui.cupo.presenters.interfaces.IViewDomicilioPersonalPresenter;
import com.pagatodo.yaganaste.ui.cupo.view.IViewCupoComprobantes;
import com.pagatodo.yaganaste.ui.cupo.view.IViewDomicilioPersonal;
import com.pagatodo.yaganaste.ui.cupo.view.IViewStatusRegisterCupo;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.utils.UI;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CARGA_DOCUMENTOS_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTA_STATUS_REGISTRO_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREA_SOLICITUD_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;

/**
 * Created by Tato on 02/08/17.
 */

public class CupoDomicilioPersonalPresenter extends GenericPresenterMain<IPreferUserGeneric> implements IViewDomicilioPersonalPresenter, IAccountManager, IDocumentsPresenter {

    private Context context;
    private INavigationView iNavigationView;
    private String TAG = getClass().getSimpleName();
    private ICupoInteractor iteractor;

    public CupoDomicilioPersonalPresenter(INavigationView iNavigationView, Context context) {
        this.context = context;
        this.iNavigationView = iNavigationView;
        iteractor = new CupoInteractor(this, context, iNavigationView);
    }


    @Override
    public void setIView(IPreferUserGeneric iPreferUserGeneric) {
        super.setIView(iPreferUserGeneric);
    }

    @Override
    public void getNeighborhoods(String zipCode) {
        Log.d(TAG, "getNeighborhoods zip code: " + zipCode);
        iNavigationView.showLoader(context.getString(R.string.obteniendo_colonias));
        iteractor.getNeighborhoodByZipCode(zipCode);
    }




    @Override
    public void onSucces(WebService ws, Object msgSuccess) {
        iNavigationView.hideLoader();
        if (ws == OBTENER_COLONIAS_CP) {
            ((IViewDomicilioPersonal) iNavigationView).setNeighborhoodsAvaliables((List<ColoniasResponse>) msgSuccess);
        } else if (ws == CARGA_DOCUMENTOS_CUPO) {
            ((IViewCupoComprobantes) iNavigationView).setResponseDocuments();
        } else if (ws == CREA_SOLICITUD_CUPO) {
            ((IViewDomicilioPersonal) iNavigationView).setResponseCreaSolicitudCupo();
        } else if (ws == CONSULTA_STATUS_REGISTRO_CUPO) {
            ((IViewStatusRegisterCupo) iNavigationView).setResponseEstadoCupo((DataEstadoSolicitud) msgSuccess );
        }
    }

    @Override
    public void onError(WebService ws, Object error) {
        iNavigationView.hideLoader();
        iNavigationView.showError(new ErrorObject(error.toString(), ws));
    }

    @Override
    public void hideLoader() {
        iNavigationView.hideLoader();
    }

    @Override
    public void onSuccesBalance() {

    }

    @Override
    public void onSuccesBalanceAdq() {

    }


    @Override
    public void getClientAddress() {
        iNavigationView.showLoader(context.getString(R.string.obteniendo_domicilio));
        iteractor.getClientAddress();
    }


    @Override
    public void showGaleryError() {
        UI.showToastShort(App.getContext().getResources().getString(R.string.adq_error_open_image), App.getContext());
    }

    @Override
    public void getEstatusDocs() {
        //No se muestra el loader porque se maneja desde el fragment debido a que el mismo puede
        // interferir con otros servicios
        // NO MOVER :)
        iteractor.getEstatusDocs();
    }

    @Override
    public void sendDocumentos(ArrayList<DataDocuments> data) {
        iNavigationView.showLoader(App.getContext().getResources().getString(R.string.adq_upload_documents));
        iteractor.sendDocuments(data);
    }

    @Override
    public void createCupoSolicitud() {
        iNavigationView.showLoader(context.getString(R.string.solicitud_cupo));
        iteractor.createSolicitudCupo();
    }

    @Override
    public void getEstadoSolicitudCupo() {
        iNavigationView.showLoader(context.getString(R.string.obteniendo_domicilio));
        iteractor.getEstadoSolicitudCupo();
    }


    @Override
    public void onSuccessDataPerson() {

    }

    @Override
    public void goToNextStepAccount(String event, Object data) {

    }
}
