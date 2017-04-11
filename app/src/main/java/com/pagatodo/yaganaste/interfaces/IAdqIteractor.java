package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.adq.LoginAdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioFWSRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqIteractor {
    public void registerAdq();
    public void loginAdq(LoginAdqRequest request);
    public void registerDongle(String serial);
    public void getNeighborhoodByZipCode(String zipCode);
}
