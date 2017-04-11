package com.pagatodo.yaganaste.ui.adquirente;

import android.util.Log;

import com.pagatodo.yaganaste.data.model.webservice.request.adq.LoginAdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.IAccountView2;
import com.pagatodo.yaganaste.interfaces.IAdqAccountPresenter;
import com.pagatodo.yaganaste.interfaces.IAdqIteractor;
import com.pagatodo.yaganaste.interfaces.IAdqRegisterView;
import com.pagatodo.yaganaste.interfaces.IDocumentsPresenter;
import com.pagatodo.yaganaste.interfaces.enums.WebService;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;

/**
 * Created by flima on 22/03/2017.
 */

public abstract class DocumentsPresenter implements IDocumentsPresenter {
    private String TAG = DocumentsPresenter.class.getName();

    public DocumentsPresenter() {
    }

    @Override
    public abstract void uploadDocuments(Object documents);

    @Override
    public abstract void getStatusDocuments();
}
