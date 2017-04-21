package com.pagatodo.yaganaste.ui.account;

import android.os.Handler;
import android.util.Log;

import com.pagatodo.yaganaste.data.model.webservice.request.adq.LoginAdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.IAccountView2;
import com.pagatodo.yaganaste.interfaces.IAdqAccountIteractor;
import com.pagatodo.yaganaste.interfaces.IAdqAccountPresenter;
import com.pagatodo.yaganaste.interfaces.IAdqRegisterView;
import com.pagatodo.yaganaste.interfaces.IUploadDocumentsView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui.adquirente.DocumentsPresenter;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountAdqPresenter extends DocumentsPresenter implements IAdqAccountPresenter, IAccountManager {
    private String TAG = AccountAdqPresenter.class.getName();
    private IAdqAccountIteractor adqIteractor;
    private IAccountView2 iAdqView;

    public AccountAdqPresenter(IAccountView2 iAdqView) {
        this.iAdqView = iAdqView;
        adqIteractor = new AccountAdqInteractor(this);
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
