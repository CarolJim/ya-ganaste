package com.pagatodo.yaganaste.ui.account;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.SubGiro;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDomicilioResponse;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAdqAccountIteractor;
import com.pagatodo.yaganaste.interfaces.IAdqAccountPresenter;
import com.pagatodo.yaganaste.interfaces.IAdqRegisterView;
import com.pagatodo.yaganaste.interfaces.IDatosNegView;
import com.pagatodo.yaganaste.interfaces.IDatosNegocioIteractor;
import com.pagatodo.yaganaste.interfaces.IDatosNegocioPresenter;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.INegocioManager;
import com.pagatodo.yaganaste.interfaces.IUploadDocumentsView;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui.adquirente.DocumentsPresenter;
import com.pagatodo.yaganaste.utils.UI;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;

/**
 * Created by flima on 22/03/2017.
 */

public class DatosNegocioPresenter implements IDatosNegocioPresenter, INegocioManager {

    private IDatosNegocioIteractor adqIteractor;

    private IDatosNegView datosNegView;
    private Context context;


    public DatosNegocioPresenter(Context context, IDatosNegView datosNegView) {
        this.context = context;
        this.datosNegView = datosNegView;
        adqIteractor = new DatosNegocioInteractor(this);
    }


    @Override
    public void getGiros() {
        datosNegView.showLoader(context.getString(R.string.obteniendo_giros));
        adqIteractor.getGiros();
    }


    @Override
    public void onError(WebService ws,String error) {
        datosNegView.hideLoader();
        datosNegView.showError(new ErrorObject(error, ws));
    }

    @Override
    public void onSucces(WebService ws,Object data) {
        datosNegView.hideLoader();
        datosNegView.setGiros((List<SubGiro>) data);
    }

}