package com.pagatodo.yaganaste.ui.adquirente;

import android.util.Log;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.Card;
import com.pagatodo.yaganaste.data.model.MessageValidation;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.LoginAdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.RegistroDongleRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearAgenteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioFWSRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.IniciarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarEstatusUsuarioRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarFormatoContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarCuentaDisponibleRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ConsultaAsignacionTarjetaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.CrearClienteRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CrearUsuarioFWSInicioSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataEstatusUsuario;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesion;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataUsuarioFWSInicioSesion;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.IniciarSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerNumeroSMSResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarEstatusUsuarioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarFormatoContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.VerificarActivacionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarCuentaDisponibleResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarNIPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarAsignacionTarjetaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.CrearClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataConsultarAsignacion;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataCuentaDisponible;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IAccountIteractorNew;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAdqIteractor;
import com.pagatodo.yaganaste.interfaces.enums.DataSource;
import com.pagatodo.yaganaste.interfaces.enums.TypeLogin;
import com.pagatodo.yaganaste.net.Api;
import com.pagatodo.yaganaste.net.ApiAdq;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.DataSource.WS;
import static com.pagatodo.yaganaste.interfaces.enums.TypeLogin.LOGIN_AFTER_REGISTER;
import static com.pagatodo.yaganaste.interfaces.enums.TypeLogin.LOGIN_NORMAL;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_COMPLETO;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.DEVICE_ALREADY_ASSIGNED;
import static com.pagatodo.yaganaste.utils.Recursos.ERROR_LOGIN;

/**
 * Created by flima on 22/03/2017.
 */

public class AdqInteractor implements IAdqIteractor,IRequestResult {

    private String TAG = AdqInteractor.class.getName();
    private IAccountManager accountManager;
    public AdqInteractor(IAccountManager accountManager){
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
    public void registerDongle(String serial) {
        RegistroDongleRequest request = new RegistroDongleRequest(serial);
        try {
            ApiAdq.registroDongle(request,this);
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