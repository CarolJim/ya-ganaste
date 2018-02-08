package com.pagatodo.yaganaste.ui.account;

import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAdqAccountIteractor;
import com.pagatodo.yaganaste.interfaces.IAdqAccountPresenter;
import com.pagatodo.yaganaste.interfaces.IAdqRegisterView;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.IUploadDocumentsView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui.adquirente.presenters.DocumentsPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.utils.UI;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CARGA_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO_PRINCIPAL;


/**
 * Created by flima on 22/03/2017.
 */

public class AccountAdqPresenter extends DocumentsPresenter implements IAdqAccountPresenter,
        IAccountManager {
    private static final String TAG = AccountAdqPresenter.class.getName();
    private IAdqAccountIteractor adqIteractor;
    private INavigationView iAdqView;


    public AccountAdqPresenter(INavigationView iAdqView) {
        this.iAdqView = iAdqView;
        adqIteractor = new AccountAdqInteractor(this, iAdqView);
    }

    @Override
    public void setIView(IPreferUserGeneric iPreferUserGeneric) {
        super.setIView(iPreferUserGeneric);
    }

    @Override
    public void goToNextStepAccount(String event, Object data) {
        iAdqView.hideLoader();
    }

    @Override
    public void getNeighborhoods(String zipCode) {
        iAdqView.showLoader(App.getContext().getString(R.string.obteniendo_colonias));
        adqIteractor.getNeighborhoodByZipCode(zipCode);
    }

    @Override
    public void getEstatusDocs() {
        //No se muestra el loader porque se maneja desde el fragment debido a que el mismo puede
        // interferir con otros servicios
        // NO MOVER :)
        adqIteractor.getEstatusDocs();

    }


    @Override
    public void getClientAddress() {
        iAdqView.showLoader(App.getContext().getString(R.string.obteniendo_domicilio));
        adqIteractor.getClientAddress();
    }


    @Override
    public void createAdq() {
        iAdqView.showLoader(App.getContext().getString(R.string.procesando_solicitud));
        adqIteractor.registerAdq();
    }

    /*envio de documentos */
    @Override
    public void sendDocumentos(ArrayList<DataDocuments> docs) {
        iAdqView.showLoader(App.getContext().getResources().getString(R.string.adq_upload_documents));
        adqIteractor.sendDocuments(docs);
    }

    @Override
    public void sendDocumentosPendientes(ArrayList<DataDocuments> data) {

        iAdqView.showLoader(App.getContext().getResources().getString(R.string.adq_upgrade_documents));
        adqIteractor.sendDocumentsPendientes(data);
    }

    @Override
    public void onError(WebService ws, Object error) {
        iAdqView.hideLoader();
        iAdqView.showError(new ErrorObject(error.toString(), ws));
    }

    @Override
    public void onForcedUpdate() {
    }

    @Override
    public void onWarningUpdate() {

    }

    @Override
    public void hideLoader() {
        iAdqView.hideLoader();
    }

    @Override
    public void onSuccesBalance() {

    }

    @Override
    public void onSuccesChangePass6(DataSourceResult dataSourceResult) {

    }


    @Override
    public void onSuccesBalanceAdq() {

    }

    @Override
    public void onSuccesBalanceCupo() {

    }

    @Override
    public void onSuccessDataPerson() {

    }

    @Override
    public void onSuccesStateCuenta() {

    }

    @Override
    public void onSucces(WebService ws, Object data) {
        iAdqView.hideLoader();
        if (iAdqView instanceof IAdqRegisterView) {
            if (ws == CREAR_AGENTE) {
                ((IAdqRegisterView) iAdqView).agentCreated("");
            } else if (ws == OBTENER_COLONIAS_CP) {
                ((IAdqRegisterView) iAdqView).setNeighborhoodsAvaliables((List<ColoniasResponse>) data);
            } else if (ws == OBTENER_DOMICILIO || ws == OBTENER_DOMICILIO_PRINCIPAL) {
                ((IAdqRegisterView) iAdqView).setCurrentAddress((DataObtenerDomicilio) data);
            }
        } else if (iAdqView instanceof IUploadDocumentsView) {
            if (ws == OBTENER_DOCUMENTOS) {
                ((IUploadDocumentsView) iAdqView).setDocumentosStatus((List<EstatusDocumentosResponse>) data);
            } else if (ws == CARGA_DOCUMENTOS) {
                ((IUploadDocumentsView) iAdqView).documentsUploaded("");
            } else if (ws == ACTUALIZAR_DOCUMENTOS) {
                ((IUploadDocumentsView) iAdqView).documentosActualizados(App.getContext().getResources().getString(R.string.adq_upgrade_documents));
            }
        } else {
            Log.i(TAG, "La sesión se ha cerrado.");
        }
    }

    @Override
    public void showGaleryError() {
        UI.showToastShort(App.getContext().getResources().getString(R.string.adq_error_open_image), App.getContext());
    }
}