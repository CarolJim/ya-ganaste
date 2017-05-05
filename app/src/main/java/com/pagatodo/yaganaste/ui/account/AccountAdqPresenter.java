package com.pagatodo.yaganaste.ui.account;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerDocumentosRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDocumentosResponse;
import com.pagatodo.yaganaste.interfaces.IAccountAddressRegisterView;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDomicilioResponse;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.IAdqAccountIteractor;
import com.pagatodo.yaganaste.interfaces.IAdqAccountPresenter;
import com.pagatodo.yaganaste.interfaces.IAdqRegisterView;
import com.pagatodo.yaganaste.interfaces.IProgressView;
import com.pagatodo.yaganaste.interfaces.IUploadDocumentsView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui.adquirente.DocumentsPresenter;
import com.pagatodo.yaganaste.utils.UI;

import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOCUMENTOS;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountAdqPresenter extends DocumentsPresenter implements IAdqAccountPresenter, IAccountManager {
    private String TAG = AccountAdqPresenter.class.getName();
    private IAdqAccountIteractor adqIteractor;
    private INavigationView iAdqView;
    Context context;



    public AccountAdqPresenter(INavigationView iAdqView, Context ctx) {
        this.iAdqView = iAdqView;
        context = ctx;
        adqIteractor = new AccountAdqInteractor(this,ctx);
    }

    @Override
    public void goToNextStepAccount(String event,Object data) {
        iAdqView.hideLoader();
    }

    @Override
    public void getNeighborhoods(String zipCode) {
        adqIteractor.getNeighborhoodByZipCode(zipCode);
    }

    @Override
    public void getEstatusDocs(View view) {
        Log.e(TAG,"AccountAdqPresenter ");
        adqIteractor.getEstatusDocs(view);
    }

    @Override
    public void getClientAddress() {
        adqIteractor.getClientAddress();
    }

    @Override
    public void createAdq() {
        iAdqView.showError("");
        adqIteractor.registerAdq();
    }

    @Override
    public void setListaDocs(View view) {
        adqIteractor.setListDocuments(view);
    }

    @Override
    public void uploadDocuments(Object documents) {

        iAdqView.showLoader("Subiendo Documentos...");

        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(iAdqView instanceof IUploadDocumentsView)
                    ((IUploadDocumentsView) iAdqView).documentsUploaded("Ejecución Éxitosa");
            }
        }, DELAY_MESSAGE_PROGRESS);
    }



    @Override
    public void onError(WebService ws,Object error) {
        iAdqView.hideLoader();
        if(iAdqView instanceof IAdqRegisterView){
            if (ws == CREAR_AGENTE) {
                ((IAccountAddressRegisterView) iAdqView).showError(error.toString());
            }else if(ws == OBTENER_COLONIAS_CP){
                ((IAccountAddressRegisterView) iAdqView).showError(error.toString());
            }else if(ws == OBTENER_DOCUMENTOS){
                Log.e(TAG,"ws Error ");
                ((IUploadDocumentsView) iAdqView).showError(error.toString());
            }
        }else {

            iAdqView.showError(error);

        }
    }

    @Override
    public void onSucces(WebService ws,Object data) {

        if(iAdqView instanceof IAdqRegisterView){
            if (ws == CREAR_AGENTE) {
                ((IAdqRegisterView) iAdqView).agentCreated("");
            }else if(ws == OBTENER_COLONIAS_CP){
                ((IAdqRegisterView) iAdqView).setNeighborhoodsAvaliables((List<ColoniasResponse>) data);
            } else if (ws == OBTENER_DOMICILIO) {
                ((IAdqRegisterView) iAdqView).setCurrentAddress((ObtenerDomicilioResponse) data);
            }
        }else{
            Log.i(TAG,"La sesión se ha cerrado.");
        }


    }

    @Override
    public void showGaleryError() {
        UI.showToastShort("La aplicacion no pudo acceder a su imagen intente con otra galeria", App.getContext());
    }
}
