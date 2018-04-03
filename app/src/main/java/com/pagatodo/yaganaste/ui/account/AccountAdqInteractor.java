package com.pagatodo.yaganaste.ui.account;

import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CargaDocumentosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearAgenteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerDocumentosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarEstatusUsuarioRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CargaDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CrearAgenteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataEstatusUsuario;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDomicilioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarEstatusUsuarioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAdqAccountIteractor;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.net.RequestHeaders;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CARGA_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO_PRINCIPAL;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_ESTATUS_USUARIO;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_SESSION_EXPIRED;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_PENDIENTE;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.TIPO_AGENTE;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountAdqInteractor implements IAdqAccountIteractor, IRequestResult {

    INavigationView iSessionExpired;
    private IAccountManager accountManager;

    public AccountAdqInteractor(IAccountManager accountManager, INavigationView iSessionExpired) {
        this.accountManager = accountManager;
        this.iSessionExpired = iSessionExpired;
    }

    /***
     * Metodo par recuperar las colonias por codigo postal
     * @param zipCode
     */
    @Override
    public void getNeighborhoodByZipCode(String zipCode) {
        ObtenerColoniasPorCPRequest request = new ObtenerColoniasPorCPRequest(zipCode);
        try {
            ApiAdtvo.obtenerColoniasPorCP(request, this);
        } catch (OfflineException e) {
            accountManager.onError(OBTENER_COLONIAS_CP, App.getInstance().getString(R.string.no_internet_access));
        }
    }


    @Override
    public void getEstatusDocs() {
        try {
            ApiAdtvo.obtenerDocumentos(this);
        } catch (OfflineException e) {
            accountManager.onError(OBTENER_DOCUMENTOS, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void getClientAddress() {
        try {
            ApiAdtvo.obtenerDomicilioPrincipal(this);
        } catch (OfflineException e) {
            accountManager.onError(OBTENER_DOMICILIO_PRINCIPAL, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    /***
     * Envio de documentos
     * @param docs
     */
    @Override
    public void sendDocuments(ArrayList<DataDocuments> docs) {

        try {
            CargaDocumentosRequest cargaDocumentosRequest = new CargaDocumentosRequest();
            cargaDocumentosRequest.setDocumentos(docs);
            ApiAdtvo.cargaDocumentos(cargaDocumentosRequest, this);
        } catch (OfflineException e) {
            accountManager.onError(CARGA_DOCUMENTOS, App.getInstance().getString(R.string.no_internet_access));
        }

    }

    /***
     * Envio de documentos pendientes y actualizacion
     * @param data
     */
    @Override
    public void sendDocumentsPendientes(ArrayList<DataDocuments> data) {

        try {
            CargaDocumentosRequest cargaDocumentosRequest = new CargaDocumentosRequest();
            cargaDocumentosRequest.setDocumentos(data);
            ApiAdtvo.actualizarDocumentos(cargaDocumentosRequest, this);
        } catch (OfflineException e) {
            accountManager.onError(ACTUALIZAR_DOCUMENTOS, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void registerAdq() {

        RegisterAgent registerAgent = RegisterAgent.getInstance();
        CrearAgenteRequest request = new CrearAgenteRequest();
        request.setNombreComercio(registerAgent.getNombre());
        // TODO: 16/05/2017
        request.setGiro(registerAgent.getGiro().getIdGiro());
        request.setSubGiro(registerAgent.getSubGiros().getIdSubgiro());
        request.setNumeroTelefono(registerAgent.getTelefono());
        request.setTipoAgente(App.getInstance().getPrefs().loadDataInt(TIPO_AGENTE));
        request.setCuestionario(registerAgent.getCuestionario());

        DataObtenerDomicilio dataObtenerDomicilio = new DataObtenerDomicilio();
        dataObtenerDomicilio.setCp(registerAgent.getCodigoPostal());
        dataObtenerDomicilio.setCalle(registerAgent.getCalle());
        dataObtenerDomicilio.setColonia(registerAgent.getColonia());
        dataObtenerDomicilio.setEstado(registerAgent.getEstadoDomicilio());
        dataObtenerDomicilio.setIdColonia(registerAgent.getIdColonia());
        dataObtenerDomicilio.setNumeroExterior(registerAgent.getNumExterior());
        dataObtenerDomicilio.setNumeroInterior(registerAgent.getNumInterior());
        dataObtenerDomicilio.setIdEstado(registerAgent.getIdEstado());

        request.setDomicilioNegocio(dataObtenerDomicilio);

        //   onSuccess(new DataSourceResult(CREAR_AGENTE, WS, null));

        try {
            ApiAdtvo.crearAgente(request, this);
        } catch (OfflineException e) {
            accountManager.onError(CREAR_AGENTE, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

        // if (16 == CODE_SESSION_EXPIRED) {
        if (((GenericResponse) dataSourceResult.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            iSessionExpired.errorSessionExpired(dataSourceResult);
        } else {
            switch (dataSourceResult.getWebService()) {

                case OBTENER_COLONIAS_CP:
                    processNeighborhoods(dataSourceResult);
                    break;
                case CREAR_AGENTE:
                    processAgentCreated(dataSourceResult);
                    break;
                case CARGA_DOCUMENTOS:
                    processSendDocuments(dataSourceResult);
                    break;
                case ACTUALIZAR_DOCUMENTOS:
                    processSendDocumentsPendientes(dataSourceResult);
                    break;
                case OBTENER_DOCUMENTOS:
                    processStatusDocuments(dataSourceResult);
                    break;
                case VALIDAR_ESTATUS_USUARIO:
                    processEstatusUsuario(dataSourceResult);
                    break;
                case OBTENER_DOMICILIO:
                    break;
                case OBTENER_DOMICILIO_PRINCIPAL:
                    processAddress(dataSourceResult);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Cambios el estatis del Singleton
     *
     * @param response
     */
    private void processEstatusUsuario(DataSourceResult response) {
        try {
            ValidarEstatusUsuarioResponse data = (ValidarEstatusUsuarioResponse) response.getData();
            DataEstatusUsuario userStatus = data.getData();
            SingletonUser user = SingletonUser.getInstance();
            App.getInstance().getPrefs().saveDataBool(ES_AGENTE, userStatus.isEsAgente());
            App.getInstance().getPrefs().saveDataInt(ESTATUS_AGENTE, CRM_PENDIENTE);
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.d("AccountAdqInteractor", "Exception " + e);
        }

    }

    private void processSendDocumentsPendientes(DataSourceResult response) {
        CargaDocumentosResponse data = (CargaDocumentosResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            accountManager.onSucces(ACTUALIZAR_DOCUMENTOS, data.getMensaje());
        } else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            iSessionExpired.errorSessionExpired(response);
        } else {
            accountManager.onError(ACTUALIZAR_DOCUMENTOS, data.getMensaje());
        }
    }

    /***
     * Metodo para procesar la respuesta cuando se envian los documentos
     *
     * @param response
     */
    private void processSendDocuments(DataSourceResult response) {

        CargaDocumentosResponse data = (CargaDocumentosResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            actualizaEstatusUsuario();
            accountManager.onSucces(CARGA_DOCUMENTOS, "Envio de documentos");
        } else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            iSessionExpired.errorSessionExpired(response);
        } else {
            accountManager.onError(CARGA_DOCUMENTOS, data.getMensaje());
        }
    }

    private void actualizaEstatusUsuario() {
        String usuario = SingletonUser.getInstance().getDataUser().getUsuario().getNombreUsuario();
        RequestHeaders.setUsername(usuario); // Seteamos el usuario en el Header
        ValidarEstatusUsuarioRequest request = new ValidarEstatusUsuarioRequest(usuario);
        try {
            ApiAdtvo.validarEstatusUsuario(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(VALIDAR_ESTATUS_USUARIO, App.getContext().getString(R.string.no_internet_access));
        }
    }


    /**
     * @param error
     **/

    @Override
    public void onFailed(DataSourceResult error) {
        /**TODO Casos de Servicio fallido*/
        accountManager.onError(error.getWebService(), error.getData().toString());
    }

    /**
     * Metodo para procesar la respuesta con los estatus de los documentos
     *
     * @param response {respuesta del servicio}
     */
    private void processStatusDocuments(DataSourceResult response) {

        ObtenerDocumentosRequest data = (ObtenerDocumentosRequest) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            List<EstatusDocumentosResponse> listaDocumentos = data.getData();
            if (listaDocumentos != null && listaDocumentos.size() > 0) {
                accountManager.onSucces(response.getWebService(), listaDocumentos);

            } else {
                accountManager.onError(response.getWebService(), "No se pudo recuperar el estatus de los documentos");
            }
        } else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            iSessionExpired.errorSessionExpired(response);
        } else {
            accountManager.onError(response.getWebService(), "error " + data.getMensaje());


        }

    }

    /**
     * Método para procesar la respuesta con la lista de colonias en el código postal.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processNeighborhoods(DataSourceResult response) {
        ObtenerColoniasPorCPResponse data = (ObtenerColoniasPorCPResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            List<ColoniasResponse> listaColonias = data.getData();
            if (listaColonias != null && !listaColonias.isEmpty()) {
                accountManager.onSucces(response.getWebService(), listaColonias);
            } else {
                accountManager.onError(response.getWebService(), "Verifica tu Código Postal");//Retornamos mensaje de error.
            }
        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(OBTENER_COLONIAS_CP, data.getMensaje());//Retornamos mensaje de error.
        }
    }

    /**
     * Método para procesar la respuesta con la lista de colonias en el código postal.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processAgentCreated(DataSourceResult response) {

        CrearAgenteResponse data = (CrearAgenteResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            accountManager.onSucces(CREAR_AGENTE, null);
        } else {
            accountManager.onError(CREAR_AGENTE, data.getMensaje());//Retornamos mensaje de error.
        }
    }

    /**
     * Metodo para procesas la respuesta de domicilio
     *
     * @param result
     */
    private void processAddress(DataSourceResult result) {
        ObtenerDomicilioResponse data = (ObtenerDomicilioResponse) result.getData();

        if (data.getCodigoRespuesta() == CODE_OK) {
            DataObtenerDomicilio domicilio = data.getData();
            if (domicilio != null) {
                accountManager.onSucces(result.getWebService(), domicilio);
            } else {
                accountManager.onError(result.getWebService(), "Ocurrio un Error al Consultar el Domicilio");//Retornamos mensaje de error.
            }
        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(result.getWebService(), data.getMensaje());//Retornamos mensaje de error.
        }
    }

}