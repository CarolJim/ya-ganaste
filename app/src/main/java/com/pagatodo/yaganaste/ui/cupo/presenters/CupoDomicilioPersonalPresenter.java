package com.pagatodo.yaganaste.ui.cupo.presenters;

import android.content.Context;
import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ConsultaSaldoCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarSaldoResponse;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAdqRegisterView;
import com.pagatodo.yaganaste.interfaces.IDocumentsPresenter;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.View;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui._manager.GenericPresenterMain;
import com.pagatodo.yaganaste.ui.account.AccountInteractorNew;
import com.pagatodo.yaganaste.ui.cupo.interactores.DomicilioPersonalInteractor;
import com.pagatodo.yaganaste.ui.cupo.interactores.interfaces.IDomicilioPersonalInteractor;
import com.pagatodo.yaganaste.ui.cupo.presenters.interfaces.IViewDomicilioPersonalPresenter;
import com.pagatodo.yaganaste.ui.cupo.view.IViewDomicilioPersonal;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.utils.UI;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;

/**
 * Created by Tato on 02/08/17.
 */

public class CupoDomicilioPersonalPresenter extends GenericPresenterMain<IPreferUserGeneric> implements IViewDomicilioPersonalPresenter, IAccountManager, IDocumentsPresenter {

    private Context context;
    private INavigationView iNavigationView;
    private String TAG = getClass().getSimpleName();
    private IDomicilioPersonalInteractor iteractor;

    public CupoDomicilioPersonalPresenter(INavigationView iNavigationView, Context context) {
        this.context = context;
        this.iNavigationView = iNavigationView;
        iteractor = new DomicilioPersonalInteractor(this, context, iNavigationView);
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
    public void onSuccesBalance(ConsultarSaldoResponse response) {

    }

    @Override
    public void onSuccesBalanceAdq(ConsultaSaldoCupoResponse response) {

    }

    @Override
    public void onSuccessDataPerson() {

    }

    @Override
    public void goToNextStepAccount(String event, Object data) {

    }
}
