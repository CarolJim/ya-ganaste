package com.pagatodo.yaganaste.ui.account;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
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
import com.pagatodo.yaganaste.ui.adquirente.DocumentsPresenter;
import com.pagatodo.yaganaste.utils.UI;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO_PRINCIPAL;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_DOC_CHECK;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;


/**
 * Created by flima on 22/03/2017.
 */

public class AccountAdqPresenter extends DocumentsPresenter implements IAdqAccountPresenter, IAccountManager {
    private static final String TAG = AccountAdqPresenter.class.getName();
    private IAdqAccountIteractor adqIteractor;
    private INavigationView iAdqView;
    Context context;


    public AccountAdqPresenter(INavigationView iAdqView, Context ctx) {
        this.iAdqView = iAdqView;
        context = ctx;
        adqIteractor = new AccountAdqInteractor(this, ctx);
    }

    @Override
    public void goToNextStepAccount(String event, Object data) {
        iAdqView.hideLoader();
    }

    @Override
    public void getNeighborhoods(String zipCode) {
        iAdqView.showLoader(context.getString(R.string.obteniendo_colonias));
        adqIteractor.getNeighborhoodByZipCode(zipCode);
    }

    @Override
    public void getEstatusDocs() {
        iAdqView.showLoader(context.getString(R.string.recuperando_docs_estatus));
        adqIteractor.getEstatusDocs();
    }


    @Override
    public void getClientAddress() {
        iAdqView.showLoader(context.getString(R.string.obteniendo_domicilio));
        adqIteractor.getClientAddress();
    }


    @Override
    public void createAdq() {
        iAdqView.showLoader(context.getString(R.string.procesando_solicitud));
        adqIteractor.registerAdq();
    }

    /*envio de documentos */
    @Override
    public void sendDocumentos(ArrayList<DataDocuments> docs) {
        iAdqView.showLoader("Subiendo Documentos ");
        adqIteractor.sendDocuments(docs);
        ((IUploadDocumentsView) iAdqView).documentsUploaded("Ejecución Éxitosa");

    }

    @Override
    public void sendDocumentosPendientes(ArrayList<DataDocuments> data) {
        iAdqView.showLoader("Actualizando Documentos ");
        adqIteractor.sendDocumentsPendientes(data);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (iAdqView instanceof IUploadDocumentsView)
                    ((IUploadDocumentsView) iAdqView).documentosActualizados("Actualizando Documentos ");
            }
        }, DELAY_MESSAGE_PROGRESS);
    }
    @Override
    public void onError(WebService ws, Object error) {
        iAdqView.hideLoader();
        iAdqView.showError(new ErrorObject(error.toString(), ws));
    }
    @Override
    public void hideLoader() {
        iAdqView.hideLoader();
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
                ((IUploadDocumentsView) iAdqView).setDocumentosStatus( (List<EstatusDocumentosResponse>) data);
            }
        } else {
            Log.i(TAG, "La sesión se ha cerrado.");

        }
    }
    @Override
    public void showGaleryError() {
        UI.showToastShort("La aplicacion no pudo acceder a su imagen intente con otra galeria", App.getContext());
    }
    public void setEstatusDocs(View rootview, List<EstatusDocumentosResponse> data) {
        adqIteractor.setListDocuments(rootview, data);
    }
}