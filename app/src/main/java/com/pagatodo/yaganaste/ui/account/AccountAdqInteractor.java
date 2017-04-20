package com.pagatodo.yaganaste.ui.account;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.LoginAdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.RegistroDongleRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearAgenteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAdqAccountIteractor;
import com.pagatodo.yaganaste.net.ApiAdq;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.DataSource.WS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountAdqInteractor implements IAdqAccountIteractor,IRequestResult {

    private String TAG = AccountAdqInteractor.class.getName();
    private IAccountManager accountManager;
    public AccountAdqInteractor(IAccountManager accountManager){
        this.accountManager = accountManager;
    }

    @Override
    public void getNeighborhoodByZipCode(String zipCode) {
        ObtenerColoniasPorCPRequest request = new ObtenerColoniasPorCPRequest(zipCode);
        try{
            ApiAdtvo.obtenerColoniasPorCP(request,this );
        } catch (OfflineException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void registerAdq() {
        RegisterAgent registerAgent = RegisterAgent.getInstance();
        CrearAgenteRequest request = new CrearAgenteRequest();
        request.setNombreComercio(registerAgent.getNombre());
        request.setGiro(registerAgent.getGiro());
        request.setNumeroTelefono(registerAgent.getTelefono());
        request.setCuestionario(registerAgent.getCuestionario());
        onSuccess(new DataSourceResult(CREAR_AGENTE, WS,null));
       /*
        try {
            ApiAdtvo.crearAgente(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void loginAdq(LoginAdqRequest request) {
        try {
            ApiAdq.loginAdq(request,this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

        switch (dataSourceResult.getWebService()) {

            case OBTENER_COLONIAS_CP:
                processNeighborhoods(dataSourceResult);
                break;
            case CREAR_AGENTE:
                processAgentCreated(dataSourceResult);
                break;

            default:
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        /**TODO Casos de Servicio fallido*/
        accountManager.onError(error.getWebService(),error.getData().toString());
    }

    /**
     * Método para procesar la respuesta con la lista de colonias en el código postal.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processNeighborhoods(DataSourceResult response) {
        ObtenerColoniasPorCPResponse data = (ObtenerColoniasPorCPResponse) response.getData();
        if(data.getCodigoRespuesta() == CODE_OK){
            List<ColoniasResponse> listaColonias = data.getData();
            if(listaColonias != null && listaColonias.size() > 0){
                accountManager.onSucces(response.getWebService(),listaColonias);
            }else{
                accountManager.onError(response.getWebService(),"Verifica tu Código Postal");//Retornamos mensaje de error.
            }
        }else{
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(),data.getMensaje());//Retornamos mensaje de error.
        }
    }

    /**
     * Método para procesar la respuesta con la lista de colonias en el código postal.
     * @param  response {@link DataSourceResult} respuesta del servicio
     * */
    private void processAgentCreated(DataSourceResult response) {

        accountManager.onSucces(response.getWebService(),null);
        /*
        if(data.getCodigoRespuesta() == CODE_OK){
            List<ColoniasResponse> listaColonias = data.getData();
            if(listaColonias != null && listaColonias.size() > 0){
                accountManager.onSucces(response.getWebService(),listaColonias);
            }else{
                accountManager.onError(response.getWebService(),"Verifica tu Código Postal");//Retornamos mensaje de error.
            }
        }else{
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(),data.getMensaje());//Retornamos mensaje de error.
        }*/
    }

}