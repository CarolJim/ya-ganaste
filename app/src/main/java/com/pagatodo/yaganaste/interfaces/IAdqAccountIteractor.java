package com.pagatodo.yaganaste.interfaces;

import android.view.View;

import com.pagatodo.yaganaste.data.model.webservice.request.adq.LoginAdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioFWSRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerDocumentosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDocumentosResponse;

import java.util.List;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqAccountIteractor {
    public void registerAdq();
    public void getNeighborhoodByZipCode(String zipCode);
    public void getEstatusDocs(View view);
    public void setListDocuments(View view);
    public void getClientAddress();
}
