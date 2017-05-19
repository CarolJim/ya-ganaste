package com.pagatodo.yaganaste.ui.account;

import android.content.Context;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.SubGiro;
import com.pagatodo.yaganaste.interfaces.IDatosNegView;
import com.pagatodo.yaganaste.interfaces.IDatosNegocioIteractor;
import com.pagatodo.yaganaste.interfaces.IDatosNegocioPresenter;
import com.pagatodo.yaganaste.interfaces.INegocioManager;
import com.pagatodo.yaganaste.interfaces.enums.WebService;

import java.util.List;

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
    public void onError(WebService ws, String error) {
        datosNegView.hideLoader();
        datosNegView.showError(new ErrorObject(error, ws));
    }

    @Override
    public void onSucces(WebService ws, Object data) {
        datosNegView.hideLoader();
        datosNegView.setGiros((List<SubGiro>) data);
    }

}