package com.pagatodo.yaganaste.ui.account;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CargaDocumentosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearAgenteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerDocumentosRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CargaDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CrearAgenteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDomicilioResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAdqAccountIteractor;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.utils.customviews.UploadDocumentView;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.R.id.itemWeNeedSmFilesAddressBack;
import static com.pagatodo.yaganaste.R.id.itemWeNeedSmFilesAddressFront;
import static com.pagatodo.yaganaste.R.id.itemWeNeedSmFilesIFEBack;
import static com.pagatodo.yaganaste.R.id.itemWeNeedSmFilesIFEfront;
import static com.pagatodo.yaganaste.interfaces.enums.DataSource.WS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CARGA_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO_PRINCIPAL;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_DOM_BACK;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_DOM_FRONT;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_ID_BACK;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_ID_FRONT;
import static com.pagatodo.yaganaste.utils.Recursos.SEND_DOCUMENTS;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_APROBADO;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_PENDIENTE;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_RECHAZADO;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountAdqInteractor implements IAdqAccountIteractor, IRequestResult {

    private String TAG = AccountAdqInteractor.class.getSimpleName();
    private IAccountManager accountManager;

    private Context context;

    Drawable mDrawable = null;
    private Preferencias pref;

    public AccountAdqInteractor(IAccountManager accountManager, Context ctx) {
        this.accountManager = accountManager;
        context = ctx;
    }

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
    public void setListDocuments(View view, List<EstatusDocumentosResponse> mListaDocumentos) {

        Log.e(TAG,"documetos size" + mListaDocumentos.size());
        if (mListaDocumentos != null && mListaDocumentos.size() > 0) {
            Log.e(TAG,"interactor " + mListaDocumentos.get(0).getEstatus());
            for (EstatusDocumentosResponse estatusDocs : mListaDocumentos) {

                int tipoDoc = estatusDocs.getTipoDocumento();

                switch (estatusDocs.getIdEstatus()) {
                    case STATUS_DOCTO_APROBADO:
                        mDrawable = ContextCompat.getDrawable(context, R.drawable.done_1_canvas);
                        break;
                    case STATUS_DOCTO_PENDIENTE:
                        mDrawable = ContextCompat.getDrawable(context, R.drawable.clock_canvas);
                        break;
                    case STATUS_DOCTO_RECHAZADO:
                        mDrawable = ContextCompat.getDrawable(context, R.drawable.warning_1_canvas);
                        break;
                    default:
                        mDrawable = ContextCompat.getDrawable(context, R.drawable.clock_canvas);
                        break;
                }
                if (tipoDoc == DOC_ID_FRONT) {
                    UploadDocumentView IFEfront = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesIFEfront);
                    IFEfront.setStatusImage(mDrawable);
                } else if (tipoDoc == DOC_ID_BACK) {
                    UploadDocumentView IFEback = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesIFEBack);
                    IFEback.setStatusImage(mDrawable);
                } else if (tipoDoc == DOC_DOM_FRONT) {
                    UploadDocumentView Addressfront = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesAddressFront);
                    Addressfront.setStatusImage(mDrawable);
                } else if (tipoDoc == DOC_DOM_BACK) {
                    UploadDocumentView Addressback = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesAddressBack);
                    Addressback.setStatusImage(mDrawable);
                }
            }
        } else {
            UploadDocumentView IFEfront = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesIFEfront);
            IFEfront.setVisibilityStatus(false);
            UploadDocumentView IFEBack = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesIFEBack);
            IFEBack.setVisibilityStatus(false);
            UploadDocumentView Addressfront = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesAddressFront);
            Addressfront.setVisibilityStatus(false);
            UploadDocumentView Addressback = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesAddressBack);
            Addressback.setVisibilityStatus(false);
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

    @Override
    public void sendDocuments(ArrayList<DataDocuments> docs) {
        Log.e(TAG, "sendDocuments");
        try {
            CargaDocumentosRequest cargaDocumentosRequest = new CargaDocumentosRequest();
            cargaDocumentosRequest.setDocumentos(docs);
            ApiAdtvo.cargaDocumentos(cargaDocumentosRequest, this);
            accountManager.hideLoader();
        } catch (OfflineException e) {
            accountManager.onError(CARGA_DOCUMENTOS, App.getInstance().getString(R.string.no_internet_access));
        }

    }

    @Override
    public void registerAdq() {

        RegisterAgent registerAgent = RegisterAgent.getInstance();
        CrearAgenteRequest request = new CrearAgenteRequest();
        request.setNombreComercio(registerAgent.getNombre());
        // TODO: 16/05/2017  
        request.setSubGiro(registerAgent.getGiro().getIdSubgiro());
        request.setNumeroTelefono(registerAgent.getTelefono());
        request.setCuestionario(registerAgent.getCuestionario());
        DataObtenerDomicilio dataObtenerDomicilio = new DataObtenerDomicilio();
        request.setTipoAgente(SingletonUser.getInstance().getDataUser().getUsuario().getTipoAgente());

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

            case OBTENER_DOCUMENTOS:
                processStatusDocuments(dataSourceResult);
                break;

            case OBTENER_DOMICILIO:

            case OBTENER_DOMICILIO_PRINCIPAL:
                processAddress(dataSourceResult);
                break;

            default:
                break;
        }
    }

    private void processSendDocuments(DataSourceResult response) {

        CargaDocumentosResponse data = (CargaDocumentosResponse) response.getData();
        Log.e("ProcessStatusDocuments", "codigoRespuesta: " + data.getCodigoRespuesta());

        if (data.getCodigoRespuesta() == CODE_OK) {

            pref = App.getInstance().getPrefs();
            if (!pref.containsData(SEND_DOCUMENTS)) {
                pref.saveDataBool(SEND_DOCUMENTS, true);
            }

            accountManager.onSucces(CARGA_DOCUMENTOS, "Envio de documentos");

        } else {
            Log.e(TAG, " mensaje error " + data.getMensaje());
            accountManager.onError(CARGA_DOCUMENTOS, "error" + data.getMensaje());
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

        //TODO REVISAR
        ObtenerDocumentosRequest data = (ObtenerDocumentosRequest) response.getData();
        Log.e("ProcessStatusDocuments", "codigoRespuesta: " + data.getCodigoRespuesta());
        if (data.getCodigoRespuesta() == CODE_OK) {
            List<EstatusDocumentosResponse> listaDocumentos = data.getData();
            if (listaDocumentos != null && listaDocumentos.size() > 0) {

                Log.e(TAG,"documentoEstatus " + listaDocumentos.get(0).getEstatus());

                accountManager.onSucces(response.getWebService(), listaDocumentos);

            } else {
                accountManager.onError(response.getWebService(), "No se pudo recuperar el estatus de los documentos");
            }

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
            accountManager.onError(response.getWebService(), data.getMensaje());//Retornamos mensaje de error.
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