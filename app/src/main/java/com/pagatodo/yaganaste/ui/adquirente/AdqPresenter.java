package com.pagatodo.yaganaste.ui.adquirente;

import android.os.Handler;
import android.util.Log;

import com.pagatodo.yaganaste.data.model.webservice.request.adq.LoginAdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.IAccountView2;
import com.pagatodo.yaganaste.interfaces.IAdqIteractor;
import com.pagatodo.yaganaste.interfaces.IAdqAccountPresenter;
import com.pagatodo.yaganaste.interfaces.IAdqRegisterView;
import com.pagatodo.yaganaste.interfaces.IUploadDocumentsView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_COMPLETO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_GET_CARD;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;

/**
 * Created by flima on 22/03/2017.
 */

public class AdqPresenter extends DocumentsPresenter implements IAdqAccountPresenter, IAccountManager {
    private String TAG = AdqPresenter.class.getName();
    private IAdqIteractor adqIteractor;
    private IAccountView2 iAdqView;

    public AdqPresenter(IAccountView2 iAdqView) {
        this.iAdqView = iAdqView;
        adqIteractor = new AdqInteractor(this);
    }

    @Override
    public void goToNextStepAccount(String event) {
        iAdqView.hideLoader();
    }

    @Override
    public void getNeighborhoods(String zipCode) {
        adqIteractor.getNeighborhoodByZipCode(zipCode);
    }

    @Override
    public void createAdq() {
        iAdqView.showError("");
        adqIteractor.registerAdq();
    }

    @Override
    public void login() {

        LoginAdqRequest request = new LoginAdqRequest("","");
        adqIteractor.loginAdq(request);

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
    public void getStatusDocuments() {

    }

    @Override
    public void onError(WebService ws,Object error) {
        iAdqView.hideLoader();
        if(iAdqView instanceof IAdqRegisterView){
            if (ws == CREAR_AGENTE) {
                ((IAccountRegisterView) iAdqView).showError(error.toString());
            }else if(ws == OBTENER_COLONIAS_CP){
                ((IAccountRegisterView) iAdqView).showError(error.toString());
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
            }
        }else{
            Log.i(TAG,"La sesión se ha cerrado.");
        }


    }
}
